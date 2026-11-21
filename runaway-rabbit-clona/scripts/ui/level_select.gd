extends Control

signal level_selected(level_number: int)
signal back_requested

const SETTINGS_PATH: String = "user://settings.cfg"
const MAP_POINTS: Array[Vector2] = [
	Vector2(68, 270),
	Vector2(84, 255),
	Vector2(131, 220),
	Vector2(147, 235),
	Vector2(178, 226),
	Vector2(215, 219),
	Vector2(263, 199),
	Vector2(308, 236),
	Vector2(332, 245),
	Vector2(361, 233),
	Vector2(383, 220),
	Vector2(415, 220),
	Vector2(416, 282),
	Vector2(466, 250),
	Vector2(478, 267),
	Vector2(502, 283),
	Vector2(530, 291),
	Vector2(556, 285),
	Vector2(567, 267),
	Vector2(560, 245),
	Vector2(519, 220),
	Vector2(565, 178),
	Vector2(575, 158),
	Vector2(578, 135),
	Vector2(575, 114),
	Vector2(566, 94),
	Vector2(542, 82),
	Vector2(514, 87),
	Vector2(500, 86)
]
const BOSS_LEVELS: Array[int] = [3, 7, 13, 21, 29]
const DESIGN_SIZE: Vector2 = Vector2(1280.0, 720.0)
const MAP_SCALE: float = 2.0
const LEVEL_ICON: Texture2D = preload("res://assets/original_ui/mapbutton.png")
const BOSS_ICON: Texture2D = preload("res://assets/original_ui/mapbutton2.png")
const LOCKED_ICON: Texture2D = preload("res://assets/original_ui/mapbuttongrey.png")
const LOCKED_BOSS_ICON: Texture2D = preload("res://assets/original_ui/mapbutton2grey.png")

@onready var _map_canvas: Control = $MapCanvas
@onready var _markers: Control = $MapCanvas/Markers
@onready var _detail_label: Label = $MapCanvas/InfoPanel/DetailLabel
@onready var _progress_label: Label = $MapCanvas/ProgressLabel
@onready var _pointer: TextureRect = $MapCanvas/Pointer
@onready var _back_button: Button = $MapCanvas/BackButton

var _unlocked_level: int = 1

func _ready() -> void:
	_load_progress()
	_update_canvas_scale()
	_map_canvas.resized.connect(_update_canvas_scale)
	_back_button.pressed.connect(func() -> void: back_requested.emit())
	for index in range(_markers.get_child_count()):
		var level_number := index + 1
		var marker := _markers.get_child(index) as TextureButton
		var boss_stage := BOSS_LEVELS.has(level_number)
		var unlocked := level_number <= _unlocked_level
		var icon: Texture2D = (BOSS_ICON if boss_stage else LEVEL_ICON) if unlocked else (LOCKED_BOSS_ICON if boss_stage else LOCKED_ICON)
		marker.texture_normal = icon
		marker.texture_hover = icon
		marker.texture_pressed = icon
		marker.disabled = not unlocked
		marker.tooltip_text = "LEVEL %02d%s" % [level_number, " · BOSS" if boss_stage else ""] if unlocked else "LOCKED"
		marker.pressed.connect(_on_level_pressed.bind(level_number))
	_update_selection(_unlocked_level)

func _update_canvas_scale() -> void:
	var viewport_size := get_viewport_rect().size
	var uniform_scale := minf(viewport_size.x / DESIGN_SIZE.x, viewport_size.y / DESIGN_SIZE.y)
	_map_canvas.scale = Vector2.ONE * uniform_scale

func _on_level_pressed(level_number: int) -> void:
	level_selected.emit(level_number)

func _update_selection(level_number: int) -> void:
	var boss_stage := BOSS_LEVELS.has(level_number)
	_detail_label.text = "LEVEL %02d  ·  %s" % [level_number, "BOSS ENCOUNTER" if boss_stage else "CAMPAIGN STAGE"]
	var point: Vector2 = MAP_POINTS[level_number - 1] * MAP_SCALE
	_pointer.position = point + Vector2(-15.0, -43.0)
	_progress_label.text = "UNLOCKED: %02d / 29" % _unlocked_level

func _load_progress() -> void:
	var config := ConfigFile.new()
	if config.load(SETTINGS_PATH) == OK:
		_unlocked_level = clampi(int(config.get_value("campaign", "unlocked_level", 1)), 1, 29)
