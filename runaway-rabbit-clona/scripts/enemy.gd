extends Area2D
class_name Enemy

@export var minimum_speed: float = 150.0
@export var maximum_speed: float = 245.0

var speed: float


func _ready() -> void:
	add_to_group("enemies")
	speed = randf_range(minimum_speed, maximum_speed)


func _process(delta: float) -> void:
	global_position.x -= speed * delta
	rotation += delta * 0.8
	if global_position.x < -48.0:
		queue_free()


func _draw() -> void:
	draw_colored_polygon(
		PackedVector2Array([
			Vector2(0, -17),
			Vector2(16, -5),
			Vector2(12, 13),
			Vector2(-12, 13),
			Vector2(-16, -5)
		]),
		Color(1.0, 0.48, 0.28)
	)
	draw_circle(Vector2(4, -3), 3.0, Color(1.0, 0.9, 0.65))
