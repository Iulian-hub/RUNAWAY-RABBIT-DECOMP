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
@onready var bomb_icons: Array[TextureRect] = [
	$Content/Player1Bombs/Bomb1,
	$Content/Player1Bombs/Bomb2,
	$Content/Player1Bombs/Bomb3
]
@onready var shield_icons: Array[TextureRect] = [
	$Content/Player1Shields/Shield1,
	$Content/Player1Shields/Shield2,
	$Content/Player1Shields/Shield3
]
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
	shield_bar.visible = true
	bombs_bar.visible = true
	beam_bar.visible = false
	center_message.text = ""
	_set_icons(shield_icons, shield_source_texture, 3)
	_set_icons(bomb_icons, bombs_source_texture, 3)

func _update_design_scale() -> void:
	if not is_instance_valid(content):
		return
	var viewport_size := get_viewport().get_visible_rect().size
	content.scale = Vector2(viewport_size.x / DESIGN_SIZE.x, viewport_size.y / DESIGN_SIZE.y)

func set_player_state(lives: int, bombs: int, score: int) -> void:
	lives_text.text = str(maxi(0, lives))
	score_text.text = "$" + str(maxi(0, score)).pad_zeros(7)
	set_bombs(bombs)

func set_lives(value: int) -> void:
	lives_text.text = str(maxi(0, value))

func set_bombs(value: int) -> void:
	_set_icons(bomb_icons, bombs_source_texture, value)

func set_score(value: int) -> void:
	score_text.text = "$" + str(maxi(0, value)).pad_zeros(7)

func set_shields(value: int) -> void:
	_set_icons(shield_icons, shield_source_texture, value)

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

func _set_icons(icons: Array[TextureRect], source_texture: Texture2D, count: int) -> void:
	if source_texture == null:
		return
	var safe_count := clampi(count, 0, icons.size())
	for index in range(icons.size()):
		var icon := icons[index]
		icon.visible = index < safe_count
		if index >= safe_count:
			continue
		var frame := AtlasTexture.new()
		frame.atlas = source_texture
		frame.region = Rect2(
			float(index * 16),
			0.0,
			16.0,
			64.0
		)
		icon.texture = frame
