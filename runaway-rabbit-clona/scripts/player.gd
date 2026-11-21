extends CharacterBody2D
class_name Player

signal health_changed(value: int)
signal shields_changed(value: int)
signal bombs_changed(value: int)
signal bomb_triggered
signal died

const PROJECTILE_SCENE: PackedScene = preload("res://scenes/projectile.tscn")
const PLAYER_SPRITE: Texture2D = preload("res://assets/original_player/hamsteranim.png")
const SPRITE_FRAME_SIZE := Vector2i(128, 128)
const SPRITE_FRAME_COUNT := 8

@export var horizontal_speed: float = 480.0
@export var fire_interval: float = 0.18
@export var max_health: int = 3
@export var max_shields: int = 3
@export var starting_bombs: int = 3

var health: int
var shields: int
var bombs: int
var projectile_parent: Node2D
var _fire_timer: float = 0.0
var _damage_flash: float = 0.0
var _is_dead: bool = false

@onready var sprite: AnimatedSprite2D = $AnimatedSprite2D

func _ready() -> void:
	health = max_health
	shields = max_shields
	bombs = starting_bombs
	_setup_original_animation()
	health_changed.emit(health)
	shields_changed.emit(shields)
	bombs_changed.emit(bombs)

func _setup_original_animation() -> void:
	var frames := SpriteFrames.new()
	frames.add_animation("idle")
	frames.set_animation_speed("idle", 8.0)
	frames.set_animation_loop("idle", true)
	for frame_index in range(SPRITE_FRAME_COUNT):
		var atlas := AtlasTexture.new()
		atlas.atlas = PLAYER_SPRITE
		atlas.region = Rect2(
			float(frame_index * SPRITE_FRAME_SIZE.x),
			0.0,
			float(SPRITE_FRAME_SIZE.x),
			float(SPRITE_FRAME_SIZE.y)
		)
		frames.add_frame("idle", atlas)
	sprite.sprite_frames = frames
	sprite.animation = "idle"
	sprite.play()
	sprite.scale = Vector2(0.55, 0.55)

func configure(projectile_root: Node2D) -> void:
	projectile_parent = projectile_root

func _physics_process(delta: float) -> void:
	if _is_dead:
		return
	_fire_timer = maxf(0.0, _fire_timer - delta)
	_damage_flash = maxf(0.0, _damage_flash - delta)
	var axis: float = Input.get_axis("move_left", "move_right")
	var target_x: float = get_global_mouse_position().x
	if not is_zero_approx(axis):
		target_x = global_position.x + axis * horizontal_speed * delta
	var viewport_width: float = get_viewport_rect().size.x
	target_x = clampf(target_x, 42.0, viewport_width - 42.0)
	velocity = Vector2(move_toward(global_position.x, target_x, horizontal_speed * delta) - global_position.x, 0.0) / maxf(delta, 0.0001)
	move_and_slide()
	if Input.is_action_pressed("fire"):
		_fire()
	if Input.is_action_just_pressed("bomb") and bombs > 0:
		bombs -= 1
		bombs_changed.emit(bombs)
		bomb_triggered.emit()
	if _damage_flash > 0.0:
		sprite.modulate = Color(1.0, 0.45, 0.45)
	else:
		sprite.modulate = Color.WHITE

func _fire() -> void:
	if _fire_timer > 0.0 or projectile_parent == null:
		return
	var direction: Vector2 = get_global_mouse_position() - global_position
	if direction.length_squared() < 1.0:
		direction = Vector2.RIGHT
	var projectile := PROJECTILE_SCENE.instantiate() as Projectile
	projectile_parent.add_child(projectile)
	projectile.launch(global_position, direction.normalized())
	_fire_timer = fire_interval

func take_damage(amount: int = 1) -> void:
	if _is_dead:
		return
	_damage_flash = 0.2
	if shields > 0:
		shields = maxi(0, shields - amount)
		shields_changed.emit(shields)
		return
	health = maxi(0, health - amount)
	health_changed.emit(health)
	if health == 0:
		_is_dead = true
		died.emit()
