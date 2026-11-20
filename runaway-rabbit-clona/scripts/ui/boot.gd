extends Control

const SPLASH_SCENE: PackedScene = preload("res://ui/splash_screen.tscn")
const MENU_SCENE: PackedScene = preload("res://ui/main_menu.tscn")
const GAME_SCENE: PackedScene = preload("res://scenes/main.tscn")

var _active_screen: Node


func _ready() -> void:
	_show_splash()


func _show_splash() -> void:
	_clear_screen()
	var splash := SPLASH_SCENE.instantiate() as Control
	add_child(splash)
	_active_screen = splash
	splash.finished.connect(_show_menu)


func _show_menu() -> void:
	_clear_screen()
	var menu := MENU_SCENE.instantiate() as Control
	add_child(menu)
	_active_screen = menu
	menu.start_requested.connect(_start_game)
	menu.quit_requested.connect(_quit_game)


func _start_game() -> void:
	_clear_screen()
	var game := GAME_SCENE.instantiate()
	add_child(game)
	_active_screen = game


func _quit_game() -> void:
	get_tree().quit()


func _clear_screen() -> void:
	if is_instance_valid(_active_screen):
		_active_screen.queue_free()
	_active_screen = null
