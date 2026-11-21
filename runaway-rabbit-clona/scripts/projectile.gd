extends Area2D
class_name Projectile

const WEAPON_SHEET: Texture2D = preload("res://assets/original_gameplay/weapons.png")
const CARROT_REGION := Rect2(192.0, 192.0, 64.0, 64.0)

@export var speed: float = 900.0
@export var lifetime: float = 2.0

var direction: Vector2 = Vector2.UP
var is_enemy_shot: bool = false
var _remaining_life: float = 0.0

@onready var sprite: Sprite2D = $Sprite2D

func _ready() -> void:
	var carrot := AtlasTexture.new()
	carrot.atlas = WEAPON_SHEET
	carrot.region = CARROT_REGION
	sprite.texture = carrot
	sprite.scale = Vector2(0.62, 0.62)

func launch(start_position: Vector2, travel_direction: Vector2) -> void:
	global_position = start_position
	direction = travel_direction.normalized()
	_remaining_life = lifetime
	rotation = direction.angle() - PI / 2.0

func launch_enemy(start_position: Vector2) -> void:
	is_enemy_shot = true
	speed = 390.0
	lifetime = 3.0
	launch(start_position, Vector2.DOWN)

func _process(delta: float) -> void:
	global_position += direction * speed * delta
	_remaining_life -= delta
	if _remaining_life <= 0.0:
		queue_free()
