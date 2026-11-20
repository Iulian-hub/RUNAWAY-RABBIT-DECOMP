extends Control

signal start_requested
signal quit_requested

const SETTINGS_PATH: String = "user://settings.cfg"

var _content: VBoxContainer
var _page_title: Label
var _music_enabled: bool = true
var _sound_enabled: bool = true


func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	_load_settings()
	_build_shell()
	_show_home()


func _build_shell() -> void:
	var background := ColorRect.new()
	background.color = Color("#07101f")
	background.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	add_child(background)

	var top_glow := ColorRect.new()
	top_glow.color = Color(0.03, 0.25, 0.36, 0.22)
	top_glow.position = Vector2(0, 0)
	top_glow.size = Vector2(1280, 170)
	add_child(top_glow)

	var header := VBoxContainer.new()
	header.set_anchors_and_offsets_preset(Control.PRESET_TOP_WIDE)
	header.offset_left = 0
	header.offset_top = 38
	header.offset_right = 0
	header.offset_bottom = 140
	header.alignment = BoxContainer.ALIGNMENT_CENTER
	header.add_theme_constant_override("separation", 2)
	add_child(header)

	var logo := Label.new()
	logo.text = "MIGHTY RODENT"
	logo.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	logo.add_theme_font_size_override("font_size", 42)
	logo.add_theme_color_override("font_color", Color("#edf7ff"))
	logo.add_theme_color_override("font_shadow_color", Color("#087aab"))
	logo.add_theme_constant_override("shadow_offset_x", 2)
	logo.add_theme_constant_override("shadow_offset_y", 3)
	header.add_child(logo)

	var tagline := Label.new()
	tagline.text = "DEFEND THE GALAXY"
	tagline.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	tagline.add_theme_font_size_override("font_size", 13)
	tagline.add_theme_color_override("font_color", Color("#6bd8ed"))
	header.add_child(tagline)

	var panel := PanelContainer.new()
	panel.set_anchors_preset(Control.PRESET_CENTER)
	panel.position = Vector2(-205, -205)
	panel.size = Vector2(410, 410)
	panel.add_theme_stylebox_override("panel", _panel_style())
	add_child(panel)

	_content = VBoxContainer.new()
	_content.alignment = BoxContainer.ALIGNMENT_CENTER
	_content.add_theme_constant_override("separation", 11)
	_content.size_flags_horizontal = Control.SIZE_EXPAND_FILL
	_content.size_flags_vertical = Control.SIZE_EXPAND_FILL
	panel.add_child(_content)

	var footer := Label.new()
	footer.text = "MIGHTY RODENT  ·  RECONSTRUCTION PROJECT"
	footer.set_anchors_preset(Control.PRESET_BOTTOM_WIDE)
	footer.offset_left = 0
	footer.offset_top = -34
	footer.offset_right = 0
	footer.offset_bottom = -10
	footer.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	footer.add_theme_font_size_override("font_size", 11)
	footer.add_theme_color_override("font_color", Color("#52677e"))
	add_child(footer)


func _panel_style() -> StyleBoxFlat:
	var style := StyleBoxFlat.new()
	style.bg_color = Color("#0c1b2d")
	style.border_color = Color("#1e526b")
	style.set_border_width_all(2)
	style.set_corner_radius_all(8)
	style.content_margin_left = 34
	style.content_margin_right = 34
	style.content_margin_top = 28
	style.content_margin_bottom = 28
	return style


func _make_button(label_text: String) -> Button:
	var button := Button.new()
	button.text = label_text
	button.custom_minimum_size = Vector2(330, 43)
	button.size_flags_horizontal = Control.SIZE_SHRINK_CENTER
	button.add_theme_font_size_override("font_size", 17)
	button.add_theme_color_override("font_color", Color("#dceaf5"))
	button.add_theme_color_override("font_hover_color", Color("#ffffff"))
	button.add_theme_color_override("font_pressed_color", Color("#70dff2"))
	button.add_theme_stylebox_override("normal", _button_style("#142b40", "#28516a"))
	button.add_theme_stylebox_override("hover", _button_style("#1b4059", "#54bad3"))
	button.add_theme_stylebox_override("pressed", _button_style("#0c263b", "#70dff2"))
	button.add_theme_stylebox_override("focus", _button_style("#142b40", "#70dff2"))
	return button


func _button_style(fill: String, border: String) -> StyleBoxFlat:
	var style := StyleBoxFlat.new()
	style.bg_color = Color(fill)
	style.border_color = Color(border)
	style.set_border_width_all(1)
	style.set_corner_radius_all(5)
	style.content_margin_top = 8
	style.content_margin_bottom = 8
	return style


func _clear_content() -> void:
	for child in _content.get_children():
		child.queue_free()


func _show_home() -> void:
	_clear_content()
	_add_page_heading("MAIN MENU", "SELECT YOUR NEXT MOVE")
	_add_spacer(8)

	var start := _make_button("START GAME")
	start.pressed.connect(func() -> void: start_requested.emit())
	_content.add_child(start)

	var instructions := _make_button("INSTRUCTIONS")
	instructions.pressed.connect(_show_instructions)
	_content.add_child(instructions)

	var scores := _make_button("HIGH SCORES")
	scores.pressed.connect(_show_scores)
	_content.add_child(scores)

	var options := _make_button("OPTIONS")
	options.pressed.connect(_show_options)
	_content.add_child(options)

	var quit := _make_button("EXIT GAME")
	quit.pressed.connect(func() -> void: quit_requested.emit())
	_content.add_child(quit)


func _show_instructions() -> void:
	_clear_content()
	_add_page_heading("INSTRUCTIONS", "CONTROLS")
	_add_text("MOUSE", "Aim and position your ship horizontally.")
	_add_text("LEFT MOUSE BUTTON", "Fire the primary weapon.")
	_add_text("RIGHT MOUSE BUTTON", "Use a bomb.")
	_add_text("A / D", "Fine horizontal movement.")
	_add_text("ESC", "Reserved for pause/menu navigation.")
	_add_back_button()


func _show_scores() -> void:
	_clear_content()
	_add_page_heading("HIGH SCORES", "LOCAL SCORE TABLE")
	_add_text("NO SCORES YET", "The original score table will be connected in a later milestone.")
	_add_back_button()


func _show_options() -> void:
	_clear_content()
	_add_page_heading("OPTIONS", "PREFERENCES ARE SAVED LOCALLY")

	var music := CheckButton.new()
	music.text = "MUSIC"
	music.button_pressed = _music_enabled
	music.toggled.connect(func(value: bool) -> void:
		_music_enabled = value
		_save_settings()
	)
	_style_check_button(music)
	_content.add_child(music)

	var sound := CheckButton.new()
	sound.text = "SOUND EFFECTS"
	sound.button_pressed = _sound_enabled
	sound.toggled.connect(func(value: bool) -> void:
		_sound_enabled = value
		_save_settings()
	)
	_style_check_button(sound)
	_content.add_child(sound)

	_add_spacer(8)
	_add_back_button()


func _style_check_button(button: CheckButton) -> void:
	button.custom_minimum_size = Vector2(330, 42)
	button.add_theme_font_size_override("font_size", 17)
	button.add_theme_color_override("font_color", Color("#dceaf5"))
	button.add_theme_color_override("font_hover_color", Color("#70dff2"))


func _add_page_heading(title_text: String, subtitle_text: String) -> void:
	var title := Label.new()
	title.text = title_text
	title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	title.add_theme_font_size_override("font_size", 25)
	title.add_theme_color_override("font_color", Color("#f1f7ff"))
	_content.add_child(title)

	var subtitle := Label.new()
	subtitle.text = subtitle_text
	subtitle.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	subtitle.add_theme_font_size_override("font_size", 11)
	subtitle.add_theme_color_override("font_color", Color("#6bd8ed"))
	_content.add_child(subtitle)


func _add_text(heading: String, detail: String) -> void:
	var block := VBoxContainer.new()
	block.add_theme_constant_override("separation", 2)
	var title := Label.new()
	title.text = heading
	title.add_theme_font_size_override("font_size", 14)
	title.add_theme_color_override("font_color", Color("#70dff2"))
	block.add_child(title)
	var description := Label.new()
	description.text = detail
	description.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
	description.custom_minimum_size.x = 320
	description.add_theme_font_size_override("font_size", 13)
	description.add_theme_color_override("font_color", Color("#b5c8d8"))
	block.add_child(description)
	_content.add_child(block)


func _add_spacer(height: float) -> void:
	var spacer := Control.new()
	spacer.custom_minimum_size.y = height
	_content.add_child(spacer)


func _add_back_button() -> void:
	var back := _make_button("BACK")
	back.pressed.connect(_show_home)
	_content.add_child(back)


func _load_settings() -> void:
	var config := ConfigFile.new()
	if config.load(SETTINGS_PATH) != OK:
		return
	_music_enabled = bool(config.get_value("audio", "music_enabled", true))
	_sound_enabled = bool(config.get_value("audio", "sound_enabled", true))


func _save_settings() -> void:
	var config := ConfigFile.new()
	config.set_value("audio", "music_enabled", _music_enabled)
	config.set_value("audio", "sound_enabled", _sound_enabled)
	config.save(SETTINGS_PATH)
