extends Control
signal start_requested
signal quit_requested
signal music_enabled_changed(enabled: bool)
const SETTINGS_PATH: String = "user://settings.cfg"
const FOREST_SKY: Texture2D = preload("res://assets/original_background/forest/forestsky.jpg")
const FOREST_FAR: Texture2D = preload("res://assets/original_background/forest/forestbkg2.png")
const FOREST_MID: Texture2D = preload("res://assets/original_background/forest/forestbkg1.png")
const FOREST_GROUND: Texture2D = preload("res://assets/original_background/forest/forestground.png")
const BASIC_FLYER: Texture2D = preload("res://assets/original_enemies/basicflyer.png")
const ADVANCED_FLYER: Texture2D = preload("res://assets/original_enemies/advancedflyer.png")
const SAUCER: Texture2D = preload("res://assets/original_enemies/sauceranim.png")
const DIVE_FIGHTER: Texture2D = preload("res://assets/original_enemies/divefighter.png")
const KAMIKAZE: Texture2D = preload("res://assets/original_enemies/kamikaze.png")
const ZIPJET: Texture2D = preload("res://assets/original_enemies/zipjet.png")
const MENU_ENEMY_SCRIPT = preload("res://scripts/ui/menu_enemy.gd")
const PARALLAX_SHADER: Shader = preload("res://shaders/menu_parallax.gdshader")
const INSTRUCTIONS_TEXTURE: Texture2D = preload("res://assets/original_ui/instructions.jpg")
const MENU_BORDER_TEXTURE: Texture2D = preload("res://assets/original_ui/menu/menuborder.png")
var _content: VBoxContainer
var _enemy_layer: Node2D
var _enemy_spawn_timer: float = 0.6
var _enemy_frame_sets: Array[SpriteFrames] = []
var _music_enabled: bool = true
var _sound_enabled: bool = true

func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	randomize()
	_load_settings()
	_build_shell()
	_build_enemy_animations()
	_show_home()

func _process(delta: float) -> void:
	_enemy_spawn_timer -= delta
	if _enemy_spawn_timer <= 0.0:
		if _enemy_layer.get_child_count() < 6:
			_spawn_menu_enemy()
		_enemy_spawn_timer = randf_range(0.85, 1.45)

func get_music_enabled() -> bool:
	return _music_enabled

func _build_shell() -> void:
	_add_animated_background_layer(FOREST_SKY, -0.042, Color(1, 1, 1, 1))
	_add_animated_background_layer(FOREST_FAR, -0.12, Color(1, 1, 1, 0.92))
	_add_animated_background_layer(FOREST_MID, -0.33, Color(1, 1, 1, 0.96))
	_add_animated_background_layer(FOREST_GROUND, -0.60, Color(1, 1, 1, 1), 32.0)
	_enemy_layer = Node2D.new()
	_enemy_layer.name = "LiveMenuEnemies"
	_enemy_layer.position = Vector2.ZERO
	add_child(_enemy_layer)
	var glow := ColorRect.new()
	glow.color = Color(0.03, 0.25, 0.36, 0.22)
	glow.mouse_filter = Control.MOUSE_FILTER_IGNORE
	glow.size = Vector2(1280, 170)
	add_child(glow)
	var header := VBoxContainer.new()
	header.set_anchors_and_offsets_preset(Control.PRESET_TOP_WIDE)
	header.offset_top = 38
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
	panel.position = Vector2(-220, -205)
	panel.size = Vector2(440, 500)
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
	footer.offset_top = -34
	footer.offset_bottom = -10
	footer.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	footer.add_theme_font_size_override("font_size", 11)
	footer.add_theme_color_override("font_color", Color("#52677e"))
	add_child(footer)

func _build_enemy_animations() -> void:
	_enemy_frame_sets = [
		_create_enemy_frames(BASIC_FLYER, 2, 7.0),
		_create_enemy_frames(ADVANCED_FLYER, 2, 7.0),
		_create_enemy_frames(SAUCER, 4, 10.0),
		_create_enemy_frames(DIVE_FIGHTER, 1, 1.0),
		_create_enemy_frames(KAMIKAZE, 2, 8.0),
		_create_enemy_frames(ZIPJET, 2, 8.0)
	]

func _create_enemy_frames(atlas: Texture2D, frame_count: int, animation_fps: float) -> SpriteFrames:
	var frames := SpriteFrames.new()
	frames.set_animation_loop("default", true)
	frames.set_animation_speed("default", animation_fps)
	for frame_index in range(frame_count):
		var frame := AtlasTexture.new()
		frame.atlas = atlas
		frame.region = Rect2(Vector2(frame_index * 128, 0), Vector2(128, 128))
		frames.add_frame("default", frame)
	return frames

func _spawn_menu_enemy() -> void:
	if _enemy_frame_sets.is_empty():
		return
	var direction := 1.0 if randf() < 0.5 else -1.0
	var viewport_size := get_viewport_rect().size
	var enemy = MENU_ENEMY_SCRIPT.new() as AnimatedSprite2D
	enemy.sprite_frames = _enemy_frame_sets[randi_range(0, _enemy_frame_sets.size() - 1)]
	enemy.set("travel_direction", direction)
	enemy.set("travel_speed", randf_range(75.0, 135.0))
	enemy.set("vertical_phase", randf_range(0.0, TAU))
	enemy.scale = Vector2.ONE * randf_range(0.42, 0.62)
	enemy.position = Vector2(-70.0 if direction > 0.0 else viewport_size.x + 70.0, randf_range(145.0, viewport_size.y - 90.0))
	enemy.flip_h = direction < 0.0
	enemy.z_index = 1
	_enemy_layer.add_child(enemy)
	enemy.play("default")

func _add_animated_background_layer(texture: Texture2D, scroll_speed: float, tint: Color, bottom_height: float = 0.0) -> void:
	var layer := TextureRect.new()
	layer.texture = texture
	layer.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	layer.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_COVERED
	if bottom_height > 0.0:
		layer.stretch_mode = TextureRect.STRETCH_SCALE
		layer.set_anchors_and_offsets_preset(Control.PRESET_BOTTOM_WIDE)
		layer.offset_top = -bottom_height
		layer.offset_bottom = 0.0
	else:
		layer.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	layer.mouse_filter = Control.MOUSE_FILTER_IGNORE
	layer.modulate = tint
	var material := ShaderMaterial.new()
	material.shader = PARALLAX_SHADER
	material.set_shader_parameter("layer_texture", texture)
	material.set_shader_parameter("scroll_speed", scroll_speed)
	layer.material = material
	add_child(layer)

func _panel_style() -> StyleBoxTexture:
	var style := StyleBoxTexture.new()
	style.texture = MENU_BORDER_TEXTURE
	style.texture_margin_left = 24.0
	style.texture_margin_top = 24.0
	style.texture_margin_right = 24.0
	style.texture_margin_bottom = 24.0
	style.content_margin_left = 34.0
	style.content_margin_right = 34.0
	style.content_margin_top = 28.0
	style.content_margin_bottom = 28.0
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
	start.grab_focus()
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
	_add_page_heading("INSTRUCTIONS", "ORIGINAL SOURCE ARTWORK")
	var artwork := TextureRect.new()
	artwork.texture = INSTRUCTIONS_TEXTURE
	artwork.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	artwork.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT
	artwork.custom_minimum_size = Vector2(340, 230)
	artwork.size_flags_horizontal = Control.SIZE_SHRINK_CENTER
	artwork.mouse_filter = Control.MOUSE_FILTER_IGNORE
	_content.add_child(artwork)
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
		music_enabled_changed.emit(_music_enabled)
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
	back.grab_focus()

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
