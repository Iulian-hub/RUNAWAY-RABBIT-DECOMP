extends AnimatedSprite2D

var travel_direction: float = -1.0
var travel_speed: float = 100.0
var lane_y: float = 300.0

func _ready() -> void:
	centered = true
	position.y = lane_y
	rotation = 0.0

func _process(delta: float) -> void:
	position.x += travel_direction * travel_speed * delta
	position.y = lane_y
	rotation = 0.0
	var width := get_viewport_rect().size.x
	if position.x < -120.0 or position.x > width + 120.0:
		queue_free()
