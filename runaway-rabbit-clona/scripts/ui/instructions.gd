extends Control
signal back_requested

func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)

func _on_back_pressed() -> void:
	back_requested.emit()
