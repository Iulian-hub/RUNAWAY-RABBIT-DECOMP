extends Control

signal finished

const DISPLAY_TIME: float = 4.5

var _elapsed: float = 0.0
var _finished: bool = false
var _title: Label
var _subtitle: Label
var _prompt: Label


func _ready() -> void:
	set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	_build_screen()
	_title.modulate.a = 0.0
	_subtitle.modulate.a = 0.0
	_prompt.modulate.a = 0.0
	var tween := create_tween()
	tween.set_parallel(true)
	tween.tween_property(_title, "modulate:a", 1.0, 1.0).set_delay(0.25)
	tween.tween_property(_subtitle, "modulate:a", 1.0, 1.0).set_delay(1.0)
	tween.tween_property(_prompt, "modulate:a", 0.9, 0.7).set_delay(2.1)


func _process(delta: float) -> void:
	if _finished:
		return
	_elapsed += delta
	if _elapsed >= DISPLAY_TIME:
		_finish()


func _unhandled_input(event: InputEvent) -> void:
	if _finished:
		return
	if event is InputEventKey and event.pressed and not event.echo:
		_finish()
	elif event is InputEventMouseButton and event.pressed:
		_finish()
	elif event is InputEventJoypadButton and event.pressed:
		_finish()


func _build_screen() -> void:
	var background := ColorRect.new()
	background.color = Color("#050b19")
	background.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	add_child(background)
	_add_stars()

	var glow := ColorRect.new()
	glow.color = Color(0.04, 0.24, 0.38, 0.22)
	glow.position = Vector2(0, 390)
	glow.size = Vector2(1280, 330)
	add_child(glow)

	var center := CenterContainer.new()
	center.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	add_child(center)

	var stack := VBoxContainer.new()
	stack.alignment = BoxContainer.ALIGNMENT_CENTER
	stack.add_theme_constant_override("separation", 14)
	center.add_child(stack)

	var eyebrow := Label.new()
	eyebrow.text = "AN ARCADE SPACE ADVENTURE"
	eyebrow.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	eyebrow.add_theme_font_size_override("font_size", 16)
	eyebrow.add_theme_color_override("font_color", Color("#6bd8ed"))
	stack.add_child(eyebrow)

	_title = Label.new()
	_title.text = "MIGHTY RODENT"
	_title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	_title.add_theme_font_size_override("font_size", 64)
	_title.add_theme_color_override("font_color", Color("#f1f7ff"))
	_title.add_theme_color_override("font_shadow_color", Color("#087aab"))
	_title.add_theme_constant_override("shadow_offset_x", 3)
	_title.add_theme_constant_override("shadow_offset_y", 4)
	stack.add_child(_title)

	_subtitle = Label.new()
	_subtitle.text = "THE GALAXY IS NOT READY."
	_subtitle.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	_subtitle.add_theme_font_size_override("font_size", 19)
	_subtitle.add_theme_color_override("font_color", Color("#9eb6ca"))
	stack.add_child(_subtitle)

	var spacer := Control.new()
	spacer.custom_minimum_size.y = 56
	stack.add_child(spacer)

	_prompt = Label.new()
	_prompt.text = "PRESS ANY KEY TO CONTINUE"
	_prompt.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	_prompt.add_theme_font_size_override("font_size", 15)
	_prompt.add_theme_color_override("font_color", Color("#70dff2"))
	stack.add_child(_prompt)

	var version := Label.new()
	version.text = "RECONSTRUCTION BUILD  ·  GODOT 4"
	version.set_anchors_and_offsets_preset(Control.PRESET_BOTTOM_WIDE)
	version.offset_left = 0
	version.offset_top = -34
	version.offset_right = 0
	version.offset_bottom = -10
	version.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	version.add_theme_font_size_override("font_size", 11)
	version.add_theme_color_override("font_color", Color("#52677e"))
	add_child(version)


func _add_stars() -> void:
	var viewport_size := get_viewport_rect().size
	for index in range(95):
		var star := ColorRect.new()
		var x := fposmod(float(index * 173 + 31), viewport_size.x)
		var y := fposmod(float(index * 97 + 17), viewport_size.y)
		var star_size := 2.0 if index % 9 == 0 else 1.0
		star.position = Vector2(x, y)
		star.size = Vector2(star_size, star_size)
		star.color = Color(0.58, 0.79, 1.0, 0.38)
		add_child(star)


func _finish() -> void:
	if _finished:
		return
	_finished = true
	var tween := create_tween()
	tween.tween_property(self, "modulate:a", 0.0, 0.35)
	tween.tween_callback(func() -> void:
		finished.emit()
	)
