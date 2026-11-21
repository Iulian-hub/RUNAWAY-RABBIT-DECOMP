extends Area2D
class_name Enemy

const FRAME_SIZE := Vector2i(128, 128)
const LEVEL_ONE_ENEMY_TYPES := {
	1: {"path": "res://assets/original_enemies/hangglider4.png", "frames": 4, "speed": 10.0},
	2: {"path": "res://assets/original_enemies/basicflyer.png", "frames": 2, "speed": 10.0},
	7: {"path": "res://assets/original_enemies/alientub.png", "frames": 1, "speed": 5.0},
	12: {"path": "res://assets/original_enemies/pogo.png", "frames": 6, "speed": 2.0},
	13: {"path": "res://assets/original_enemies/energysphere.png", "frames": 2, "speed": 5.0}
}
const FALLBACK_ANIMATIONS: Array[Dictionary] = [
	{"path": "res://assets/original_enemies/basicflyer.png", "frames": 2, "speed": 10.0},
	{"path": "res://assets/original_enemies/advancedflyer.png", "frames": 2, "speed": 10.0},
	{"path": "res://assets/original_enemies/divefighter.png", "frames": 1, "speed": 5.0},
	{"path": "res://assets/original_enemies/zipjet.png", "frames": 2, "speed": 5.0},
	{"path": "res://assets/original_enemies/kamikaze.png", "frames": 2, "speed": 7.0},
	{"path": "res://assets/original_enemies/sauceranim.png", "frames": 4, "speed": 5.0}
]

@export var minimum_speed: float = 150.0
@export var maximum_speed: float = 245.0

var speed: float
var enemy_type_id: int = 0
@onready var sprite: AnimatedSprite2D = $AnimatedSprite2D

func configure_enemy_type(type_id: int) -> void:
	enemy_type_id = type_id

func _ready() -> void:
	add_to_group("enemies")
	set_meta("enemy_type_id", enemy_type_id)
	speed = randf_range(minimum_speed, maximum_speed)
	_setup_original_animation()

func _setup_original_animation() -> void:
	var definition: Dictionary
	if LEVEL_ONE_ENEMY_TYPES.has(enemy_type_id):
		definition = LEVEL_ONE_ENEMY_TYPES[enemy_type_id]
	else:
		definition = FALLBACK_ANIMATIONS[randi_range(0, FALLBACK_ANIMATIONS.size() - 1)]
	var texture := load(str(definition["path"])) as Texture2D
	if texture == null:
		push_warning("Could not load original enemy sprite: %s" % definition["path"])
		return
	var frame_count := int(definition["frames"])
	var frames := SpriteFrames.new()
	frames.add_animation("fly")
	frames.set_animation_speed("fly", float(definition["speed"]))
	frames.set_animation_loop("fly", true)
	for frame_index in range(frame_count):
		var atlas := AtlasTexture.new()
		atlas.atlas = texture
		atlas.region = Rect2(
			float(frame_index * FRAME_SIZE.x),
			0.0,
			float(FRAME_SIZE.x),
			float(FRAME_SIZE.y)
		)
		frames.add_frame("fly", atlas)
	sprite.sprite_frames = frames
	sprite.animation = "fly"
	sprite.scale = Vector2(0.70, 0.70)
	sprite.play()

func _process(delta: float) -> void:
	global_position.x -= speed * delta
	if global_position.x < -48.0:
		queue_free()
