extends CanvasLayer
## Multi-speed horizontal parallax for the original forest artwork.

@export var sky_speed: float = 0.001
@export var far_forest_speed: float = 0.008
@export var mid_forest_speed: float = 0.018
@export var ground_speed: float = 0.040

@onready var sky: TextureRect = $Layers/Sky
@onready var far_forest: TextureRect = $Layers/FarForest
@onready var mid_forest: TextureRect = $Layers/MidForest
@onready var ground: TextureRect = $Layers/ForestGround

func _process(delta: float) -> void:
	_advance_layer(sky, sky_speed, delta)
	_advance_layer(far_forest, far_forest_speed, delta)
	_advance_layer(mid_forest, mid_forest_speed, delta)
	_advance_layer(ground, ground_speed, delta)

func _advance_layer(layer: TextureRect, speed: float, delta: float) -> void:
	var shader_material := layer.material as ShaderMaterial
	if shader_material == null:
		return
	var offset := float(shader_material.get_shader_parameter("scroll_offset"))
	offset = fposmod(offset + speed * delta, 1.0)
	shader_material.set_shader_parameter("scroll_offset", offset)
