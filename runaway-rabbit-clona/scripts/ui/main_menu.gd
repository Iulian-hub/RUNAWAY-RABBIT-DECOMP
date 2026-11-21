extends Control
signal start_requested
signal instructions_requested
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
const MENU_ENEMY_LANES: Array[float] = [175.0, 300.0, 425.0, 550.0]
@onready var _content: VBoxContainer = $MenuPanel/MenuContent
@onready var _enemy_layer: Node2D = $MenuEnemies
var _enemy_spawn_timer: float = 1.0
var _next_enemy_direction: float = 1.0
var _enemy_frame_sets: Array[SpriteFrames] = []
var _music_enabled: bool = true
var _sound_enabled: bool = true

func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	randomize()
	_load_settings()
	_build_enemy_animations()
	_show_home()

func _process(delta: float) -> void:
	_enemy_spawn_timer -= delta
	if _enemy_spawn_timer <= 0.0:
		if _enemy_layer.get_child_count() < 4:
			_spawn_menu_enemy()
		_enemy_spawn_timer = randf_range(1.25, 1.9)

func get_music_enabled() -> bool:
	return _music_enabled


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

func _find_available_enemy_lane() -> float:
	var occupied_lanes: Array[float] = []
	for child in _enemy_layer.get_children():
		occupied_lanes.append(float(child.get("lane_y")))
	var available_lanes: Array[float] = []
	for lane_y in MENU_ENEMY_LANES:
		if not occupied_lanes.has(lane_y):
			available_lanes.append(lane_y)
	if available_lanes.is_empty():
		return -1.0
	return available_lanes[randi_range(0, available_lanes.size() - 1)]

func _spawn_menu_enemy() -> void:
	if _enemy_frame_sets.is_empty():
		return
	var lane_y := _find_available_enemy_lane()
	if lane_y < 0.0:
		return
	var direction := _next_enemy_direction
	_next_enemy_direction *= -1.0
	var viewport_size := get_viewport_rect().size
	var enemy = MENU_ENEMY_SCRIPT.new() as AnimatedSprite2D
	enemy.sprite_frames = _enemy_frame_sets[randi_range(0, _enemy_frame_sets.size() - 1)]
	enemy.set("travel_direction", direction)
	enemy.set("travel_speed", randf_range(75.0, 135.0))
	enemy.set("lane_y", lane_y)
	enemy.scale = Vector2.ONE * randf_range(0.76, 0.88)
	enemy.position = Vector2(-75.0 if direction > 0.0 else viewport_size.x + 75.0, lane_y)
	enemy.flip_h = direction < 0.0
	_enemy_layer.add_child(enemy)
	enemy.play("default")

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
	instructions.pressed.connect(func() -> void: instructions_requested.emit())
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
