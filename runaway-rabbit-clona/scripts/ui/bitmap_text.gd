extends Control
class_name BitmapText
## Renderer for the original two-sheet 8x8 Blitz3D bitmap font atlas.

const CHARACTER_SET := "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890~`!@#$%^&*()-+_=[]{}\\|;:'\",.<>/?"

@export var atlas_01: Texture2D
@export var atlas_02: Texture2D
@export var text: String = "":
	set(value):
		text = value
		queue_redraw()
@export_range(1.0, 100.0, 0.5) var font_height: float = 27.0
@export var font_color: Color = Color.WHITE
@export var centered_horizontal: bool = false
@export var centered_vertical: bool = false
@export var space_width_ratio: float = 0.3
@export var line_spacing: float = 1.0

var _glyphs: Dictionary = {}
var _cell_size: float = 0.0

func _ready() -> void:
	_build_glyph_map()
	queue_redraw()

func _build_glyph_map() -> void:
	_glyphs.clear()
	if atlas_01 == null or atlas_02 == null:
		return
	var textures: Array[Texture2D] = [atlas_01, atlas_02]
	var images: Array[Image] = [atlas_01.get_image(), atlas_02.get_image()]
	_cell_size = float(atlas_01.get_width()) / 8.0
	for char_index in range(CHARACTER_SET.length()):
		var sheet_index := int(char_index / 64)
		if sheet_index >= textures.size():
			break
		var atlas := textures[sheet_index]
		var cell_index := char_index % 64
		var cell_x := (cell_index % 8) * int(_cell_size)
		var cell_y := int(cell_index / 8) * int(_cell_size)
		var image: Image = images[sheet_index]
		var min_x := int(_cell_size) - 1
		var max_x := 0
		for x in range(int(_cell_size)):
			var has_ink := false
			for y in range(int(_cell_size)):
				if image.get_pixel(cell_x + x, cell_y + y).a > 0.01:
					has_ink = true
					break
			if has_ink:
				min_x = mini(min_x, x)
				max_x = maxi(max_x, x)
		if max_x < min_x:
			continue
		var character := CHARACTER_SET.substr(char_index, 1)
		_glyphs[character] = {
			"texture": atlas,
			"region": Rect2(cell_x + min_x, cell_y + 2, max_x - min_x + 1, _cell_size - 4),
			"width_ratio": float(max_x - min_x + 1) / _cell_size
		}

func _measure_line(value: String) -> float:
	var width := 0.0
	for index in range(value.length()):
		var character := value.substr(index, 1)
		if character == " ":
			width += font_height * space_width_ratio
		elif _glyphs.has(character):
			width += font_height * float(_glyphs[character]["width_ratio"])
	return width

func _draw() -> void:
	if _glyphs.is_empty():
		return
	var lines := text.split("\n", true)
	var total_height := float(lines.size()) * font_height * line_spacing
	var y := -total_height * 0.5 if centered_vertical else 0.0
	for line in lines:
		var line_width := _measure_line(line)
		var x := -line_width * 0.5 if centered_horizontal else 0.0
		for index in range(line.length()):
			var character := line.substr(index, 1)
			if character == " ":
				x += font_height * space_width_ratio
				continue
			if not _glyphs.has(character):
				continue
			var glyph: Dictionary = _glyphs[character]
			var glyph_width := font_height * float(glyph["width_ratio"])
			draw_texture_rect_region(
				glyph["texture"],
				Rect2(x, y, glyph_width, font_height),
				glyph["region"],
				font_color
			)
			x += glyph_width
		y += font_height * line_spacing
