extends Node2D

const ENEMY_SCENE: PackedScene = preload("res://scenes/enemy.tscn")

@onready var player: Player = $Player
@onready var enemies_root: Node2D = $Enemies
@onready var projectiles_root: Node2D = $Projectiles
@onready var score_label: Label = $HUD/ScoreLabel
@onready var health_label: Label = $HUD/HealthLabel
@onready var bomb_label: Label = $HUD/BombLabel
@onready var game_over_label: Label = $HUD/GameOverLabel

var score: int = 0
var _spawn_timer: float = 0.8
var _elapsed: float = 0.0
var _game_over: bool = false


func _ready() -> void:
	randomize()
	var viewport_size := get_viewport_rect().size
	player.global_position = Vector2(90.0, viewport_size.y * 0.78)
	player.configure(projectiles_root)
	player.health_changed.connect(_on_health_changed)
	player.bombs_changed.connect(_on_bombs_changed)
	player.bomb_triggered.connect(_on_bomb_triggered)
	player.died.connect(_on_player_died)
	_on_health_changed(player.health)
	_on_bombs_changed(player.bombs)
	_update_score()


func _process(delta: float) -> void:
	if _game_over:
		return

	_elapsed += delta
	_spawn_timer -= delta
	if _spawn_timer <= 0.0:
		_spawn_enemy()
		var difficulty := minf(_elapsed / 100.0, 0.65)
		_spawn_timer = randf_range(0.75, 1.35) - difficulty * 0.35

	_check_hits()


func _spawn_enemy() -> void:
	var enemy := ENEMY_SCENE.instantiate() as Enemy
	enemies_root.add_child(enemy)
	var viewport_size := get_viewport_rect().size
	enemy.global_position = Vector2(viewport_size.x + 36.0, randf_range(75.0, viewport_size.y - 75.0))
	enemy.speed += minf(_elapsed * 0.35, 90.0)


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
			if projectile.global_position.distance_squared_to(enemy.global_position) < 420.0:
				projectile.queue_free()
				enemy.queue_free()
				score += 100
				_update_score()
				break

	for enemy_node in enemies:
		if not is_instance_valid(enemy_node) or enemy_node.is_queued_for_deletion():
			continue
		var enemy := enemy_node as Enemy
		if player.global_position.distance_squared_to(enemy.global_position) < 1150.0:
			enemy.queue_free()
			player.take_damage()


func _on_health_changed(value: int) -> void:
	health_label.text = "HULL  %d" % value


func _on_bombs_changed(value: int) -> void:
	bomb_label.text = "BOMBS  %d" % value


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
	game_over_label.visible = true


func _update_score() -> void:
	score_label.text = "SCORE  %07d" % score


func _draw() -> void:
	var size := get_viewport_rect().size
	draw_rect(Rect2(Vector2.ZERO, size), Color(0.025, 0.045, 0.095))
	for index in range(72):
		var x := fposmod(float(index * 173 + 29), size.x)
		var y := fposmod(float(index * 97 + 41), size.y)
		var radius := 1.0 if index % 5 == 0 else 0.55
		draw_circle(Vector2(x, y), radius, Color(0.55, 0.75, 1.0, 0.42))
