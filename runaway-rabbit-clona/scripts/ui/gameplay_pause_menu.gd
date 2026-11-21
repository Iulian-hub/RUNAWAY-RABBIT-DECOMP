extends CanvasLayer
class_name GameplayPauseMenu

signal resume_requested
signal restart_requested
signal main_menu_requested

func _ready() -> void:
	process_mode = Node.PROCESS_MODE_ALWAYS
	visible = false
	$Overlay/CenterContainer/PausePanel/PauseContent/ResumeButton.pressed.connect(func() -> void: resume_requested.emit())
	$Overlay/CenterContainer/PausePanel/PauseContent/RestartButton.pressed.connect(func() -> void: restart_requested.emit())
	$Overlay/CenterContainer/PausePanel/PauseContent/MainMenuButton.pressed.connect(func() -> void: main_menu_requested.emit())

func open_menu() -> void:
	visible = true
	$Overlay/CenterContainer/PausePanel/PauseContent/ResumeButton.grab_focus()

func close_menu() -> void:
	visible = false

func _unhandled_input(event: InputEvent) -> void:
	if visible and event.is_action_pressed("pause"):
		resume_requested.emit()
		get_viewport().set_input_as_handled()
