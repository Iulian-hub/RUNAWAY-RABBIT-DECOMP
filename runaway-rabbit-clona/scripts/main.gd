extends Node2D

signal return_to_menu_requested

const ENEMY_SCENE: PackedScene = preload("res://scenes/enemy.tscn")
@onready var player: Player = $Player
@onready var enemies_root: Node2D = $Enemies
@onready var projectiles_root: Node2D = $Projectiles
@onready var gameplay_hud: GameplayHUD = $GameplayHUD
@onready var pause_menu: GameplayPauseMenu = $GameplayPauseMenu
var selected_level: int = 1
var score: int = 0
var _spawn_timer: float = 0.8
var _game_over: bool = false
var _is_paused: bool = false

func _ready() -> void:
	randomize()
	pause_menu.resume_requested.connect(_resume_game)
	pause_menu.restart_requested.connect(_restart_level)
	pause_menu.main_menu_requested.connect(_return_to_menu)
	gameplay_hud.set_completion_percent(0.0)
	ProfileManager.record_level_started(selected_level)
	var viewport_size := get_viewport_rect().size
	player.global_position = Vector2(90.0, viewport_size.y * 0.78)
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
	if event.is_action_pressed("pause") and not _game_over and not _is_paused:
		_pause_game()
		get_viewport().set_input_as_handled()

func _pause_game() -> void:
	if _game_over or _is_paused:
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
	if _game_over or _is_paused:
		return
	_spawn_timer -= delta
	if _spawn_timer <= 0.0:
		_spawn_enemy()
		# Spawn pacing will be driven by authored level events, not a global difficulty ramp.
		_spawn_timer = randf_range(0.75, 1.35)
	_check_hits()

func _spawn_enemy() -> void:
	var enemy := ENEMY_SCENE.instantiate() as Enemy
	enemies_root.add_child(enemy)
	var viewport_size := get_viewport_rect().size
	# Source parity: doenemiesend.bb uses the default Blitz3D spawn band Y=-50..275.
	# Convert its center-origin, upward-positive Y coordinates to Godot's top-origin,
	# downward-positive screen coordinates: screen_y = viewport_center_y - source_y.
	var source_spawn_y_min := -50.0
	var source_spawn_y_max := 275.0
	var spawn_y_min := viewport_size.y * 0.5 - source_spawn_y_max
	var spawn_y_max := viewport_size.y * 0.5 - source_spawn_y_min
	enemy.global_position = Vector2(viewport_size.x + 36.0, randf_range(spawn_y_min, spawn_y_max))

func _check_hits() -> void:
	var enemies := get_tree().get_nodes_in_group("enemies")
	var projectiles := projectiles_root.get_children()
	for projectile_node in projectiles:
		if not is_instance_valid(projectile_node) or projectile_node.is_queued_for_deletion():
			continue
		var projectile := projectile_node as Projectile
		for enemy_node in enemies:
			if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
				continue
			var enemy := enemy_node as Enemy
			if projectile.global_position.distance_squared_to(enemy.global_position) < 1800.0:
				projectile.queue_free()
				enemy.queue_free()
				score += 100
				_update_score()
				break
	for enemy_node in enemies:
		if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
			continue
		var enemy := enemy_node as Enemy
		if player.global_position.distance_squared_to(enemy.global_position) < 2200.0:
			enemy.queue_free()
			player.take_damage()

func _on_health_changed(value: int) -> void:
	gameplay_hud.set_lives(value)

func _on_shields_changed(value: int) -> void:
	gameplay_hud.set_shields(value)

func _on_bombs_changed(value: int) -> void:
	gameplay_hud.set_bombs(value)

func _on_bomb_triggered() -> void:
	var cleared_enemies := get_tree().get_nodes_in_group("enemies").size()
	for enemy in get_tree().get_nodes_in_group("enemies"):
		enemy.queue_free()
	for projectile in projectiles_root.get_children():
		projectile.queue_free()
	score += 250 * cleared_enemies
	_update_score()

func _on_player_died() -> void:
	_game_over = true
	gameplay_hud.show_center_message("GAME OVER")
	ProfileManager.record_run(score)

func _update_score() -> void:
	gameplay_hud.set_score(score)

func _draw() -> void:
	var size := get_viewport_rect().size
	draw_rect(Rect2(Vector2.ZERO, size), Color(0.025, 0.045, 0.095))
	for index in range(72):
		var x := fposmod(float(index * 173 + 29), size.x)
		var y := fposmod(float(index * 97 + 41), size.y)
		var radius := 1.0 if index % 5 == 0 else 0.55
		draw_circle(Vector2(x, y), radius, Color(0.55, 0.75, 1.0, 0.42))
