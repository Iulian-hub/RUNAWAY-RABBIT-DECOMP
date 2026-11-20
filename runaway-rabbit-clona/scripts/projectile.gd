extends Area2D
class_name Projectile

@export var speed: float = 900.0
@export var lifetime: float = 1.8

var direction: Vector2 = Vector2.RIGHT
var _remaining_life: float


func launch(start_position: Vector2, travel_direction: Vector2) -> void:
	global_position = start_position
	direction = travel_direction.normalized()
	_remaining_life = lifetime
	rotation = direction.angle()


func _process(delta: float) -> void:
	global_position += direction * speed * delta
	_remaining_life -= delta
	if _remaining_life <= 0.0:
		queue_free()


func _draw() -> void:
	draw_circle(Vector2.ZERO, 4.5, Color(0.55, 0.95, 1.0))
	draw_line(Vector2(-10, 0), Vector2(-2, 0), Color(0.2, 0.65, 1.0, 0.8), 3.0)
