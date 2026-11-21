extends CanvasLayer
class_name GameplayHUD
## Recreates the original loadgui.bb / dohud.bb gameplay HUD layout.

const DESIGN_SIZE := Vector2(640.0, 480.0)
@onready var content: Control = $Content
@onready var lives_text: BitmapText = $Content/Player1LivesText
@onready var score_text: BitmapText = $Content/Player1Score
@onready var bombs_bar: TextureRect = $Content/Player1Bombs
@onready var shield_bar: TextureRect = $Content/Player1Shields
@onready var bombs_source_texture: Texture2D = bombs_bar.texture
@onready var shield_source_texture: Texture2D = shield_bar.texture
@onready var beam_bar: ColorRect = $Content/Player1BeamLaser
@onready var completion_text: BitmapText = $Content/CompletionText
@onready var center_message: BitmapText = $Content/CenterMessage

func _ready() -> void:
	_update_design_scale()
	get_viewport().size_changed.connect(_update_design_scale)
	$Content/Player2Lives.visible = false
	$Content/Player2LivesText.visible = false
	$Content/Player2Score.visible = false
	$Content/Player2Bombs.visible = false
	$Content/Player2Shields.visible = false
	$Content/Player2BeamLaser.visible = false
	shield_bar.visible = false
	beam_bar.visible = false
	center_message.text = ""

func _update_design_scale() -> void:
	if not is_instance_valid(content):
		return
	var viewport_size := get_viewport().get_visible_rect().size
	content.scale = Vector2(viewport_size.x / DESIGN_SIZE.x, viewport_size.y / DESIGN_SIZE.y)

func set_player_state(lives: int, bombs: int, score: int) -> void:
	lives_text.text = str(maxi(0, lives))
	score_text.text = "$" + str(maxi(0, score)).pad_zeros(7)
	_set_meter(bombs_bar, bombs_source_texture, bombs, 24.0, 24.0)

func set_lives(value: int) -> void:
	lives_text.text = str(maxi(0, value))

func set_bombs(value: int) -> void:
	_set_meter(bombs_bar, bombs_source_texture, value, 24.0, 24.0)

func set_score(value: int) -> void:
	score_text.text = "$" + str(maxi(0, value)).pad_zeros(7)

func set_shields(value: int) -> void:
	_set_meter(shield_bar, shield_source_texture, value, 24.0, 16.0)
	shield_bar.visible = value > 0

func set_beam_charge(value: float) -> void:
	var ratio := clampf(value, 0.01, 1.0)
	beam_bar.size.y = 96.0 * ratio
	beam_bar.position.y = 126.0 + 96.0 - beam_bar.size.y
	beam_bar.color = Color(1.0, 1.0, 0.2) if ratio >= 1.0 else Color(0.2, 1.0, 0.2)
	beam_bar.visible = value > 0.0

func set_completion_percent(value: float) -> void:
	completion_text.text = "%d%% COMPLETED" % clampi(roundi(value), 0, 100)

func show_center_message(value: String) -> void:
	center_message.text = value

func _set_meter(bar: TextureRect, source_texture: Texture2D, count: int, unit_width: float, height: float) -> void:
	var safe_count := clampi(count, 0, 4)
	if source_texture == null:
		return
	if safe_count == 0:
		bar.visible = false
		return
	# TextureRect has no region_enabled/region_rect properties in Godot 4.
	# AtlasTexture provides the equivalent source-image crop.
	var cropped_texture := AtlasTexture.new()
	cropped_texture.atlas = source_texture
	cropped_texture.region = Rect2(
		0.0,
		0.0,
		source_texture.get_width() * float(safe_count) / 4.0,
		source_texture.get_height()
	)
	bar.texture = cropped_texture
	bar.size = Vector2(unit_width * safe_count, height)
	bar.visible = true
