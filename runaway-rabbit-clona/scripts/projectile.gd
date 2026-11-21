extends Area2D
class_name Projectile

const WEAPON_SHEET: Texture2D = preload("res://assets/original_gameplay/weapons.png")
const PLAYER_CARROT_REGION := Rect2(192.0, 192.0, 64.0, 64.0)
const ENEMY_PROJECTILE_REGION := Rect2(128.0, 192.0, 64.0, 64.0)

@export var speed: float = 900.0
@export var lifetime: float = 2.0

var direction: Vector2 = Vector2.UP
var is_enemy_shot: bool = false
var _remaining_life: float = 0.0

@onready var sprite: Sprite2D = $Sprite2D

func _ready() -> void:
	sprite.scale = Vector2(0.62, 0.62)

func _set_original_projectile(region: Rect2, size: Vector2) -> void:
	var atlas := AtlasTexture.new()
	atlas.atlas = WEAPON_SHEET
	atlas.region = region
	sprite.texture = atlas
	sprite.scale = size

func launch_player(start_position: Vector2) -> void:
	_set_original_projectile(PLAYER_CARROT_REGION, Vector2(0.62, 0.62))
	global_position = start_position
	direction = Vector2.UP
	_remaining_life = lifetime
	rotation = 0.0

func launch_enemy(start_position: Vector2) -> void:
	is_enemy_shot = true
	speed = 390.0
	lifetime = 3.0
	_set_original_projectile(ENEMY_PROJECTILE_REGION, Vector2(0.55, 0.55))
	global_position = start_position
	direction = Vector2.DOWN
	_remaining_life = lifetime
	rotation = PI

func _process(delta: float) -> void:
	global_position += direction * speed * delta
	_remaining_life -= delta
	if _remaining_life <= 0.0:
		queue_free()
