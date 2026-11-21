extends Node2D

signal return_to_menu_requested

const ENEMY_SCENE: PackedScene = preload("res://scenes/enemy.tscn")
const EXPLOSION_SCENE: PackedScene = preload("res://scenes/explosion.tscn")
const MAIN_MUSIC: AudioStreamOggVorbis = preload("res://assets/original_audio/main.ogg")
const BOSS_MUSIC: AudioStreamOggVorbis = preload("res://assets/original_audio/boss.ogg")
const GROUND_TOP_RATIO: float = 0.92
const PLAYER_SPRITE_HALF_HEIGHT: float = 44.8

@onready var player: Player = $Player
@onready var enemies_root: Node2D = $Enemies
@onready var projectiles_root: Node2D = $Projectiles
@onready var gameplay_hud: GameplayHUD = $GameplayHUD
@onready var pause_menu: GameplayPauseMenu = $GameplayPauseMenu
@onready var music_player: AudioStreamPlayer = $GameplayMusic

var selected_level: int = 1
var score: int = 0
var _game_over: bool = false
var _level_finished: bool = false
var _is_paused: bool = false
var _level_director: LevelDirector
var _level_definition: LevelDefinition
var _active_dynamic_groups: Array[Dictionary] = []
var _aggregate_enemy_limit: int = 10
var _current_music_id: int = -1

func _ready() -> void:
	randomize()
	pause_menu.resume_requested.connect(_resume_game)
	pause_menu.restart_requested.connect(_restart_level)
	pause_menu.main_menu_requested.connect(_return_to_menu)

	_level_definition = LevelTemplateLibrary.get_campaign_level(selected_level)
	if _level_definition == null:
		push_error("No extracted source template is available for campaign level %d." % selected_level)
		_game_over = true
		gameplay_hud.show_center_message("LEVEL DATA MISSING")
		return

	_level_director = LevelDirector.new()
	_level_director.name = "LevelDirector"
	add_child(_level_director)
	_level_director.level_begun.connect(_on_level_begun)
	_level_director.event_started.connect(_on_level_event_started)
	_level_director.level_completed.connect(_on_level_completed)
	_level_director.start_level(_level_definition)

	_active_dynamic_groups = _level_definition.initial_dynamic_spawn_groups.duplicate(true)
	_aggregate_enemy_limit = maxi(1, _level_definition.initial_aggregate_enemy_limit)
	_set_music(1)
	gameplay_hud.set_completion_percent(0.0)
	gameplay_hud.show_center_message("GET READY")
	ProfileManager.record_level_started(selected_level)

	var viewport_size := get_viewport_rect().size
	player.global_position = Vector2(90.0, viewport_size.y * GROUND_TOP_RATIO - PLAYER_SPRITE_HALF_HEIGHT)
	player.configure(projectiles_root)
	player.health_changed.connect(_on_health_changed)
	player.shields_changed.connect(_on_shields_changed)
	player.bombs_changed.connect(_on_bombs_changed)
	player.bomb_triggered.connect(_on_bomb_triggered)
	player.died.connect(_on_player_died)
	gameplay_hud.set_player_state(player.health, player.bombs, score)
	gameplay_hud.set_shields(player.shields)
	_update_score()

func _unhandled_input(event: InputEvent) -> void:
	if event.is_action_pressed("pause") and not _game_over and not _level_finished and not _is_paused:
		_pause_game()
		get_viewport().set_input_as_handled()

func _pause_game() -> void:
	if _game_over or _level_finished or _is_paused:
		return
	_is_paused = true
	get_tree().paused = true
	pause_menu.open_menu()

func _resume_game() -> void:
	pause_menu.close_menu()
	_is_paused = false
	get_tree().paused = false

func _restart_level() -> void:
	get_tree().paused = false
	get_tree().reload_current_scene()

func _return_to_menu() -> void:
	get_tree().paused = false
	return_to_menu_requested.emit()

func _process(delta: float) -> void:
	if _game_over or _level_finished or _is_paused or _level_director == null or not _level_director.has_begun:
		return
	_update_dynamic_spawns(delta)
	_check_hits()
	_level_director.set_active_enemy_count(_get_active_enemy_count())
	var longest_time := maxf(1.0, _level_definition.get_last_trigger_time())
	var progress_ratio := clampf(_level_director.level_time / longest_time, 0.0, 1.0)
	# A mild ease-out keeps the HUD percentage from racing ahead early in the level.
	var completion := minf(99.0, pow(progress_ratio, 1.65) * 99.0)
	gameplay_hud.set_completion_percent(completion)

func _on_level_begun() -> void:
	gameplay_hud.show_center_message("")

func _on_level_event_started(level_event: LevelEventData, _event_index: int) -> void:
	_active_dynamic_groups = level_event.dynamic_spawn_groups.duplicate(true)
	if level_event.aggregate_enemy_limit > 0:
		_aggregate_enemy_limit = level_event.aggregate_enemy_limit
	if level_event.music_id > 0:
		_set_music(level_event.music_id)

	for spawn_data in level_event.scripted_spawns:
		var type_id := int(spawn_data.get("enemy_type", 0))
		if type_id <= 0:
			continue
		var viewport_size := get_viewport_rect().size
		var source_x := float(spawn_data.get("x", 0.0))
		var source_y := float(spawn_data.get("y", 0.0))
		var spawn_position := Vector2(
			clampf(viewport_size.x * 0.5 + source_x, 48.0, viewport_size.x - 48.0),
			clampf(viewport_size.y * 0.5 - source_y, 48.0, viewport_size.y - 48.0)
		)
		_spawn_enemy(type_id, spawn_position)
	_level_director.set_active_enemy_count(_get_active_enemy_count())

func _update_dynamic_spawns(delta: float) -> void:
	if _active_dynamic_groups.is_empty() or _get_active_enemy_count() >= _aggregate_enemy_limit:
		return
	var viewport_size := get_viewport_rect().size
	var source_y_min := -50.0
	var source_y_max := 275.0
	var spawn_y_min := viewport_size.y * 0.5 - source_y_max
	var spawn_y_max := viewport_size.y * 0.5 - source_y_min

	for group_data in _active_dynamic_groups:
		if _get_active_enemy_count() >= _aggregate_enemy_limit:
			break
		var type_id := int(group_data.get("enemy_type", 0))
		var interval := maxf(1.0, float(group_data.get("source_field_23", 240.0)))
		var type_limit := maxi(0, int(group_data.get("source_field_24", 0)))
		if type_id <= 0 or type_limit <= 0 or _count_enemies_of_type(type_id) >= type_limit:
			continue
		# Exact probability structure from controlenemies.bb: rand(1, interval / fl.Field1) == 2,
		# with the original minimum denominator of 2 and frame-factor cap of 3.
		var source_frame_factor := minf(delta * 60.0, 3.0)
		var denominator := maxi(2, int(interval / maxf(source_frame_factor, 0.001)))
		if randi_range(1, denominator) == 2:
			_spawn_enemy(type_id, Vector2(viewport_size.x + 36.0, randf_range(spawn_y_min, spawn_y_max)))

func _spawn_enemy(type_id: int, spawn_position: Vector2) -> void:
	var enemy := ENEMY_SCENE.instantiate() as Enemy
	enemy.configure_enemy_type(type_id)
	enemy.configure_projectile_parent(projectiles_root)
	enemies_root.add_child(enemy)
	enemy.global_position = spawn_position

func _get_active_enemy_count() -> int:
	var count := 0
	for enemy_node in get_tree().get_nodes_in_group("enemies"):
		if is_instance_valid(enemy_node) and not enemy_node.is_queued_for_deletion():
			count += 1
	return count

func _count_enemies_of_type(type_id: int) -> int:
	var count := 0
	for enemy_node in get_tree().get_nodes_in_group("enemies"):
		if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
			continue
		if int(enemy_node.get_meta("enemy_type_id", 0)) == type_id:
			count += 1
	return count

func _set_music(music_id: int) -> void:
	var profile := ProfileManager.get_active_profile()
	if not bool(profile.get("music_enabled", true)):
		music_player.stop()
		_current_music_id = -1
		return
	if music_player.playing and _current_music_id == music_id:
		return
	var source: AudioStreamOggVorbis = BOSS_MUSIC if music_id == 3 else MAIN_MUSIC
	var stream := source.duplicate() as AudioStreamOggVorbis
	stream.loop = true
	music_player.stream = stream
	music_player.volume_db = -6.0 if music_id == 3 else -9.1
	music_player.play()
	_current_music_id = music_id

func _check_hits() -> void:
	var enemies := get_tree().get_nodes_in_group("enemies")
	var ground_impact_y := get_viewport_rect().size.y * GROUND_TOP_RATIO

	for projectile_node in projectiles_root.get_children():
		if not is_instance_valid(projectile_node) or projectile_node.is_queued_for_deletion():
			continue
		var projectile := projectile_node as Projectile
		if projectile == null:
			continue

		if projectile.is_enemy_shot:
			if projectile.global_position.distance_squared_to(player.global_position) < 1800.0:
				_spawn_explosion(projectile.global_position, 0.65)
				projectile.queue_free()
				player.take_damage()
			elif projectile.global_position.y >= ground_impact_y:
				_spawn_explosion(projectile.global_position, 0.60)
				projectile.queue_free()
			continue

		for enemy_node in enemies:
			if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
				continue
			var enemy := enemy_node as Enemy
			if projectile.global_position.distance_squared_to(enemy.global_position) < 2600.0:
				_spawn_explosion(enemy.global_position, 0.95)
				projectile.queue_free()
				enemy.queue_free()
				score += 100
				_update_score()
				break

	for enemy_node in enemies:
		if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
			continue
		var enemy := enemy_node as Enemy
		if player.global_position.distance_squared_to(enemy.global_position) < 3000.0:
			_spawn_explosion(enemy.global_position, 0.85)
			enemy.queue_free()
			player.take_damage()

func _spawn_explosion(world_position: Vector2, size: float = 1.0) -> void:
	var effect := EXPLOSION_SCENE.instantiate() as ExplosionEffect
	effect.effect_scale = size
	effect.global_position = world_position
	projectiles_root.add_child(effect)

func _on_health_changed(value: int) -> void:
	gameplay_hud.set_lives(value)

func _on_shields_changed(value: int) -> void:
	gameplay_hud.set_shields(value)

func _on_bombs_changed(value: int) -> void:
	gameplay_hud.set_bombs(value)

func _on_bomb_triggered() -> void:
	var cleared_enemies := _get_active_enemy_count()
	for enemy in get_tree().get_nodes_in_group("enemies"):
		enemy.queue_free()
	for projectile in projectiles_root.get_children():
		projectile.queue_free()
	score += 250 * cleared_enemies
	_update_score()
	if _level_director != null:
		_level_director.set_active_enemy_count(0)

func _on_player_died() -> void:
	_game_over = true
	if _level_director != null:
		_level_director.stop_level()
	gameplay_hud.show_center_message("GAME OVER")
	ProfileManager.record_run(score)

func _on_level_completed() -> void:
	_level_finished = true
	gameplay_hud.set_completion_percent(100.0)
	gameplay_hud.show_center_message("LEVEL COMPLETE")
	ProfileManager.complete_level(selected_level, score)

func _update_score() -> void:
	gameplay_hud.set_score(score)
