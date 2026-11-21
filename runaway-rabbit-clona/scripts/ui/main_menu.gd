extends Control

signal start_requested
signal profile_requested
signal instructions_requested
signal quit_requested
signal music_enabled_changed(enabled: bool)

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
const MENU_ENEMY_LANES: Array[float] = [175.0, 300.0, 425.0, 550.0]

@onready var _enemy_layer: Node2D = $MenuEnemies
@onready var _home_page: VBoxContainer = $MenuPanel/MenuContent/HomePage
@onready var _scores_page: VBoxContainer = $MenuPanel/MenuContent/ScoresPage
@onready var _options_page: VBoxContainer = $MenuPanel/MenuContent/OptionsPage
@onready var _music_toggle: CheckButton = $MenuPanel/MenuContent/OptionsPage/MusicToggle
@onready var _sound_toggle: CheckButton = $MenuPanel/MenuContent/OptionsPage/SoundToggle
@onready var _players_option: OptionButton = $MenuPanel/MenuContent/OptionsPage/PlayersOption

var _enemy_spawn_timer: float = 1.0
var _next_enemy_direction: float = 1.0
var _enemy_frame_sets: Array[SpriteFrames] = []
var _music_enabled: bool = true
var _sound_enabled: bool = true

func _ready() -> void:
	randomize()
	_load_settings()
	_refresh_profile_display()
	_build_enemy_animations()
	_connect_controls()
	_music_toggle.button_pressed = _music_enabled
	_sound_toggle.button_pressed = _sound_enabled
	_players_option.clear()
	_players_option.add_item("1 PLAYER", 1)
	_players_option.add_item("2 PLAYERS", 2)
	var active_profile := ProfileManager.get_active_profile()
	_players_option.select(1 if int(active_profile.get("players", 1)) == 2 else 0)
	_show_home()

func _process(delta: float) -> void:
	_enemy_spawn_timer -= delta
	if _enemy_spawn_timer <= 0.0:
		if _enemy_layer.get_child_count() < 4:
			_spawn_menu_enemy()
		_enemy_spawn_timer = randf_range(1.25, 1.9)

func get_music_enabled() -> bool:
	return _music_enabled

func _connect_controls() -> void:
	$MenuPanel/MenuContent/HomePage/ProfileButton.pressed.connect(func() -> void: profile_requested.emit())
	$MenuPanel/MenuContent/HomePage/StartButton.pressed.connect(func() -> void:
		if ProfileManager.has_active_profile():
			start_requested.emit()
	)
	$MenuPanel/MenuContent/HomePage/InstructionsButton.pressed.connect(func() -> void: instructions_requested.emit())
	$MenuPanel/MenuContent/HomePage/ScoresButton.pressed.connect(_show_scores)
	$MenuPanel/MenuContent/HomePage/OptionsButton.pressed.connect(_show_options)
	$MenuPanel/MenuContent/HomePage/QuitButton.pressed.connect(func() -> void: quit_requested.emit())
	$MenuPanel/MenuContent/ScoresPage/ScoresBackButton.pressed.connect(_show_home)
	$MenuPanel/MenuContent/OptionsPage/OptionsBackButton.pressed.connect(_show_home)
	_music_toggle.toggled.connect(_on_music_toggled)
	_sound_toggle.toggled.connect(_on_sound_toggled)
	_players_option.item_selected.connect(_on_players_selected)
	for button in $MenuPanel/MenuContent.get_children():
		for control in button.get_children():
			if control is CheckButton:
				_style_check_button(control)
			elif control is Button:
				_style_button(control)

func _show_home() -> void:
	_home_page.visible = true
	_scores_page.visible = false
	_options_page.visible = false
	$MenuPanel/MenuContent/HomePage/StartButton.grab_focus()

func _show_scores() -> void:
	var profile := ProfileManager.get_active_profile()
	$MenuPanel/MenuContent/ScoresPage/EmptyScores.text = "BEST SCORE: %07d\nTOTAL SCORE: %07d\nCAMPAIGN PROGRESS: %02d / 29\nCASH: $%d" % [int(profile.get("best_score", 0)), int(profile.get("total_score", 0)), int(profile.get("current_level", 1)), int(profile.get("cash", 0))]
	_home_page.visible = false
	_scores_page.visible = true
	_options_page.visible = false
	$MenuPanel/MenuContent/ScoresPage/ScoresBackButton.grab_focus()

func _show_options() -> void:
	_home_page.visible = false
	_scores_page.visible = false
	_options_page.visible = true
	$MenuPanel/MenuContent/OptionsPage/OptionsBackButton.grab_focus()

func _style_button(button: Button) -> void:
	button.add_theme_font_size_override("font_size", 17)
	button.add_theme_color_override("font_color", Color("#dceaf5"))
	button.add_theme_color_override("font_hover_color", Color("#ffffff"))
	button.add_theme_color_override("font_pressed_color", Color("#70dff2"))
	button.add_theme_stylebox_override("normal", _button_style("#142b40", "#28516a"))
	button.add_theme_stylebox_override("hover", _button_style("#1b4059", "#54bad3"))
	button.add_theme_stylebox_override("pressed", _button_style("#0c263b", "#70dff2"))
	button.add_theme_stylebox_override("focus", _button_style("#142b40", "#70dff2"))

func _button_style(fill: String, border: String) -> StyleBoxFlat:
	var style := StyleBoxFlat.new()
	style.bg_color = Color(fill)
	style.border_color = Color(border)
	style.set_border_width_all(1)
	style.set_corner_radius_all(5)
	style.content_margin_top = 8
	style.content_margin_bottom = 8
	return style

func _style_check_button(button: CheckButton) -> void:
	button.add_theme_font_size_override("font_size", 17)
	button.add_theme_color_override("font_color", Color("#dceaf5"))
	button.add_theme_color_override("font_hover_color", Color("#70dff2"))

func _on_music_toggled(value: bool) -> void:
	_music_enabled = value
	_save_settings()
	music_enabled_changed.emit(_music_enabled)

func _on_sound_toggled(value: bool) -> void:
	_sound_enabled = value
	_save_settings()

func _on_players_selected(index: int) -> void:
	ProfileManager.set_active_players(_players_option.get_item_id(index))

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

func _refresh_profile_display() -> void:
	var profile := ProfileManager.get_active_profile()
	var label: Label = $MenuPanel/MenuContent/HomePage/CurrentProfileLabel
	var start_button: Button = $MenuPanel/MenuContent/HomePage/StartButton
	if profile.is_empty():
		label.text = "ACTIVE PROFILE: NONE — CREATE OR SELECT ONE"
		start_button.disabled = true
	else:
		label.text = "PROFILE: %s  ·  LEVEL %02d" % [profile["name"], profile["current_level"]]
		start_button.disabled = false

func _load_settings() -> void:
	var profile := ProfileManager.get_active_profile()
	if profile.is_empty():
		_music_enabled = true
		_sound_enabled = true
		return
	_music_enabled = bool(profile.get("music_enabled", true))
	_sound_enabled = bool(profile.get("sound_enabled", true))

func _save_settings() -> void:
	ProfileManager.set_active_preference("music_enabled", _music_enabled)
	ProfileManager.set_active_preference("sound_enabled", _sound_enabled)
