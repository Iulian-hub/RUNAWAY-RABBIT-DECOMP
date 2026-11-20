extends AnimatedSprite2D

var travel_direction: float = -1.0
var travel_speed: float = 100.0
var vertical_phase: float = 0.0
var _age: float = 0.0
var _origin_y: float = 0.0

func _ready() -> void:
	_origin_y = position.y
	centered = true

func _process(delta: float) -> void:
	_age += delta
	position.x += travel_direction * travel_speed * delta
	position.y = _origin_y + sin(_age * 1.8 + vertical_phase) * 12.0
	rotation = sin(_age * 1.4 + vertical_phase) * 0.045
	var width := get_viewport_rect().size.x
	if position.x < -110.0 or position.x > width + 110.0:
		queue_free()
