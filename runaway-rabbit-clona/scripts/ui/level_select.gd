extends Control
signal level_selected(level_number: int)
signal back_requested
const SETTINGS_PATH: String = "user://settings.cfg"
const MAP_TEXTURE: Texture2D = preload("res://assets/original_ui/mappic.jpg")
const LEVEL_ICON: Texture2D = preload("res://assets/original_ui/mapbutton.png")
const BOSS_ICON: Texture2D = preload("res://assets/original_ui/mapbutton2.png")
const LOCKED_ICON: Texture2D = preload("res://assets/original_ui/mapbuttongrey.png")
const LOCKED_BOSS_ICON: Texture2D = preload("res://assets/original_ui/mapbutton2grey.png")
const POINTER_TEXTURE: Texture2D = preload("res://assets/original_ui/mappointer.png")
const MAP_POINTS: Array[Vector2] = [
	Vector2(68, 270), Vector2(84, 255), Vector2(104, 237), Vector2(147, 235),
	Vector2(178, 226), Vector2(215, 219), Vector2(252, 224), Vector2(308, 236),
	Vector2(332, 245), Vector2(361, 233), Vector2(383, 220), Vector2(415, 220),
	Vector2(442, 233), Vector2(466, 250), Vector2(478, 267), Vector2(502, 283),
	Vector2(530, 291), Vector2(556, 285), Vector2(567, 267), Vector2(560, 245),
	Vector2(563, 218), Vector2(565, 178), Vector2(575, 158), Vector2(578, 135),
	Vector2(575, 114), Vector2(566, 94), Vector2(542, 82), Vector2(514, 87),
	Vector2(493, 102)
]
const BOSS_LEVELS: Array[int] = [3, 7, 13, 21, 29]
const MAP_ORIGIN: Vector2 = Vector2(160.0, 0.0)
const MAP_SCALE: float = 1.5
var _unlocked_level: int = 1
var _detail_label: Label
var _pointer: TextureRect

func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	_load_progress()
	_build_map()
	_update_selection(_unlocked_level)

func _build_map() -> void:
	var background := TextureRect.new()
	background.texture = MAP_TEXTURE
	background.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	background.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT
	background.position = MAP_ORIGIN
	background.size = Vector2(960.0, 720.0)
	background.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(background)

	var shade := ColorRect.new()
	shade.color = Color(0.015, 0.025, 0.055, 0.16)
	shade.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	shade.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(shade)

	var title := Label.new()
	title.text = "SELECT LEVEL"
	title.position = Vector2(36, 24)
	title.add_theme_font_size_override("font_size", 28)
	title.add_theme_color_override("font_color", Color("#eff8ff"))
	add_child(title)
	var subtitle := Label.new()
	subtitle.text = "CAMPAIGN MAP  ·  29 STAGES"
	subtitle.position = Vector2(38, 60)
	subtitle.add_theme_font_size_override("font_size", 12)
	subtitle.add_theme_color_override("font_color", Color("#70d8ed"))
	add_child(subtitle)

	for index in range(MAP_POINTS.size()):
		var level_number := index + 1
		var boss_stage := BOSS_LEVELS.has(level_number)
		var unlocked := level_number <= _unlocked_level
		var marker := TextureButton.new()
		if unlocked:
			marker.texture_normal = BOSS_ICON if boss_stage else LEVEL_ICON
		else:
			marker.texture_normal = LOCKED_BOSS_ICON if boss_stage else LOCKED_ICON
		marker.texture_hover = marker.texture_normal
		marker.texture_pressed = marker.texture_normal
		marker.ignore_texture_size = true
		var marker_size := Vector2(34, 34) if boss_stage else Vector2(28, 28)
		marker.custom_minimum_size = marker_size
		marker.size = marker_size
		var point: Vector2 = MAP_ORIGIN + MAP_POINTS[index] * MAP_SCALE
		marker.position = point - marker_size * 0.5
		marker.disabled = not unlocked
		marker.tooltip_text = "LEVEL %02d%s" % [level_number, " · BOSS" if boss_stage else ""] if unlocked else "LOCKED"
		marker.pressed.connect(_on_level_pressed.bind(level_number))
		add_child(marker)
		var number := Label.new()
		number.text = str(level_number)
		number.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
		number.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
		number.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
		number.add_theme_font_size_override("font_size", 10 if boss_stage else 9)
		number.add_theme_color_override("font_color", Color("#ffffff") if unlocked else Color("#9ba5b2"))
		number.mouse_filter = Control.MOUSE_FILTER_IGNORE
		marker.add_child(number)

	_pointer = TextureRect.new()
	_pointer.texture = POINTER_TEXTURE
	_pointer.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	_pointer.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_CENTERED
	_pointer.size = Vector2(40, 40)
	_pointer.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(_pointer)

	var info_panel := PanelContainer.new()
	info_panel.position = Vector2(30, 590)
	info_panel.size = Vector2(520, 92)
	info_panel.add_theme_stylebox_override("panel", _panel_style())
	add_child(info_panel)
	_detail_label = Label.new()
	_detail_label.add_theme_font_size_override("font_size", 15)
	_detail_label.add_theme_color_override("font_color", Color("#e8f5ff"))
	info_panel.add_child(_detail_label)

	var back := Button.new()
	back.text = "BACK TO MENU"
	back.position = Vector2(1030, 635)
	back.custom_minimum_size = Vector2(210, 48)
	back.pressed.connect(func() -> void: back_requested.emit())
	add_child(back)
	var progress := Label.new()
	progress.text = "UNLOCKED: %02d / 29" % _unlocked_level
	progress.position = Vector2(1032, 600)
	progress.add_theme_font_size_override("font_size", 13)
	progress.add_theme_color_override("font_color", Color("#d5e8f5"))
	add_child(progress)

func _panel_style() -> StyleBoxTexture:
	var style := StyleBoxTexture.new()
	style.texture = preload("res://assets/original_ui/menu/menuborder.png")
	style.texture_margin_left = 24.0
	style.texture_margin_top = 24.0
	style.texture_margin_right = 24.0
	style.texture_margin_bottom = 24.0
	style.content_margin_left = 22.0
	style.content_margin_right = 22.0
	style.content_margin_top = 14.0
	style.content_margin_bottom = 14.0
	return style

func _on_level_pressed(level_number: int) -> void:
	level_selected.emit(level_number)

func _update_selection(level_number: int) -> void:
	var boss_stage := BOSS_LEVELS.has(level_number)
	_detail_label.text = "LEVEL %02d  ·  %s" % [level_number, "BOSS ENCOUNTER" if boss_stage else "CAMPAIGN STAGE"]
	var point: Vector2 = MAP_ORIGIN + MAP_POINTS[level_number - 1] * MAP_SCALE
	_pointer.position = point + Vector2(-15, -43)

func _load_progress() -> void:
	var config := ConfigFile.new()
	if config.load(SETTINGS_PATH) == OK:
		_unlocked_level = clampi(int(config.get_value("campaign", "unlocked_level", 1)), 1, 29)
