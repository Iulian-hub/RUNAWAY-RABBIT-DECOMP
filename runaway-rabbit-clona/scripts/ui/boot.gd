extends Control
const SPLASH_SCENE: PackedScene = preload("res://ui/splash_screen.tscn")
const MENU_SCENE: PackedScene = preload("res://ui/main_menu.tscn")
const PROFILE_SCENE: PackedScene = preload("res://ui/profile_select.tscn")
const INSTRUCTIONS_SCENE: PackedScene = preload("res://ui/instructions.tscn")
const LEVEL_SELECT_SCENE: PackedScene = preload("res://ui/level_select.tscn")
const GAME_SCENE: PackedScene = preload("res://scenes/main.tscn")
const MENU_MUSIC: AudioStreamOggVorbis = preload("res://assets/original_audio/menu.ogg")
var _active_screen: Node
var _music_player: AudioStreamPlayer

func _ready() -> void:
	_music_player = AudioStreamPlayer.new()
	_music_player.name = "BackgroundMusic"
	_music_player.volume_db = -7.0
	var stream := MENU_MUSIC.duplicate() as AudioStreamOggVorbis
	stream.loop = true
	_music_player.stream = stream
	add_child(_music_player)
	_show_splash()

func _show_splash() -> void:
	_clear_screen()
	var splash := SPLASH_SCENE.instantiate() as Control
	add_child(splash)
	_active_screen = splash
	splash.finished.connect(_after_splash)

func _after_splash() -> void:
	if ProfileManager.has_active_profile():
		_show_menu()
	else:
		_show_profiles()

func _show_profiles() -> void:
	_clear_screen()
	var profiles := PROFILE_SCENE.instantiate() as Control
	add_child(profiles)
	_active_screen = profiles
	profiles.profile_chosen.connect(_show_menu)
	profiles.back_requested.connect(_show_menu)

func _show_menu() -> void:
	_clear_screen()
	var menu := MENU_SCENE.instantiate() as Control
	add_child(menu)
	_active_screen = menu
	menu.start_requested.connect(_show_level_select)
	menu.profile_requested.connect(_show_profiles)
	menu.instructions_requested.connect(_show_instructions)
	menu.quit_requested.connect(_quit_game)
	menu.music_enabled_changed.connect(_set_music_enabled)
	_set_music_enabled(menu.get_music_enabled())

func _show_instructions() -> void:
	_clear_screen()
	var instructions := INSTRUCTIONS_SCENE.instantiate() as Control
	add_child(instructions)
	_active_screen = instructions
	instructions.back_requested.connect(_show_menu)

func _show_level_select() -> void:
	_clear_screen()
	var selection := LEVEL_SELECT_SCENE.instantiate() as Control
	add_child(selection)
	_active_screen = selection
	selection.level_selected.connect(_start_game)
	selection.back_requested.connect(_show_menu)

func _start_game(level_number: int) -> void:
	_music_player.stop()
	_clear_screen()
	var game := GAME_SCENE.instantiate()
	game.set("selected_level", level_number)
	add_child(game)
	_active_screen = game
	game.return_to_menu_requested.connect(_show_menu)

func _set_music_enabled(enabled: bool) -> void:
	if enabled:
		if not _music_player.playing:
			_music_player.play()
	else:
		_music_player.stop()

func _quit_game() -> void:
	get_tree().quit()

func _clear_screen() -> void:
	if is_instance_valid(_active_screen):
		_active_screen.queue_free()
	_active_screen = null
