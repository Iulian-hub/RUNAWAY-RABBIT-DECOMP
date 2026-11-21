extends Node2D
class_name ExplosionEffect

const EXPLOSION_SHEET: Texture2D = preload("res://assets/original_gameplay/explosions.png")

@export_range(0.25, 2.0, 0.05) var effect_scale: float = 1.0

@onready var sprite: AnimatedSprite2D = $AnimatedSprite2D

func _ready() -> void:
	var frames := SpriteFrames.new()
	frames.add_animation("burst")
	frames.set_animation_speed("burst", 12.0)
	frames.set_animation_loop("burst", false)

	# Original Blitz3D effect: 4x4 atlas, with seven visible frames.
	for frame_index in range(7):
		var atlas := AtlasTexture.new()
		atlas.atlas = EXPLOSION_SHEET
		atlas.region = Rect2(
			float((frame_index % 4) * 64),
			float((frame_index / 4) * 64),
			64.0,
			64.0
		)
		frames.add_frame("burst", atlas)

	sprite.sprite_frames = frames
	sprite.animation = "burst"
	sprite.scale = Vector2.ONE * effect_scale
	sprite.animation_finished.connect(queue_free)
	sprite.play()
