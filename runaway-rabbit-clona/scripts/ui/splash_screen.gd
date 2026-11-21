extends Control

signal finished

const DISPLAY_TIME: float = 4.5

var _elapsed: float = 0.0
var _finished: bool = false

func _ready() -> void:
	modulate.a = 0.0
	var tween := create_tween()
	tween.tween_property(self, "modulate:a", 1.0, 0.8)

func _process(delta: float) -> void:
	if _finished:
		return
	_elapsed += delta
	if _elapsed >= DISPLAY_TIME:
		_finish()

func _input(event: InputEvent) -> void:
	if _finished:
		return
	if event is InputEventKey and event.pressed and not event.echo:
		_finish()
	elif event is InputEventMouseButton and event.pressed:
		_finish()
	elif event is InputEventJoypadButton and event.pressed:
		_finish()

func _finish() -> void:
	if _finished:
		return
	_finished = true
	var tween := create_tween()
	tween.tween_property(self, "modulate:a", 0.0, 0.35)
	tween.tween_callback(func() -> void:
		finished.emit()
	)
