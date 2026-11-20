extends Control

signal finished

const DISPLAY_TIME: float = 4.5
const LOGO_TEXTURE: Texture2D = preload("res://assets/original_ui/jaggedlogo.jpg")

var _elapsed: float = 0.0
var _finished: bool = false
var _prompt: Label


func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	_build_screen()
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


func _build_screen() -> void:
	var artwork := TextureRect.new()
	artwork.texture = LOGO_TEXTURE
	artwork.expand_mode = TextureRect.EXPAND_IGNORE_SIZE
	artwork.stretch_mode = TextureRect.STRETCH_KEEP_ASPECT_COVERED
	artwork.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	artwork.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(artwork)

	var shade := ColorRect.new()
	shade.color = Color(0.0, 0.015, 0.04, 0.28)
	shade.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	shade.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(shade)

	_prompt = Label.new()
	_prompt.text = "PRESS ANY KEY TO CONTINUE"
	_prompt.set_anchors_preset(Control.PRESET_BOTTOM_WIDE)
	_prompt.offset_left = 0
	_prompt.offset_top = -78
	_prompt.offset_right = 0
	_prompt.offset_bottom = -42
	_prompt.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	_prompt.add_theme_font_size_override("font_size", 16)
	_prompt.add_theme_color_override("font_color", Color("#ffffff"))
	_prompt.add_theme_color_override("font_shadow_color", Color("#07101f"))
	_prompt.add_theme_constant_override("shadow_offset_x", 2)
	_prompt.add_theme_constant_override("shadow_offset_y", 2)
	add_child(_prompt)

	var version := Label.new()
	version.text = "GODOT RECONSTRUCTION"
	version.set_anchors_preset(Control.PRESET_BOTTOM_WIDE)
	version.offset_left = 0
	version.offset_top = -30
	version.offset_right = 0
	version.offset_bottom = -10
	version.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	version.add_theme_font_size_override("font_size", 10)
	version.add_theme_color_override("font_color", Color("#c0d0dc"))
	add_child(version)


func _finish() -> void:
	if _finished:
		return
	_finished = true
	var tween := create_tween()
	tween.tween_property(self, "modulate:a", 0.0, 0.35)
	tween.tween_callback(func() -> void:
		finished.emit()
	)
