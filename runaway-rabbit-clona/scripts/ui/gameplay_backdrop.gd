extends CanvasLayer
## Multi-speed horizontal parallax for the original ice artwork.

@export var sky_speed: float = 0.001
@export var far_ice_speed: float = 0.008
@export var mid_ice_speed: float = 0.018
@export var ground_speed: float = 0.040

@onready var sky: TextureRect = $Layers/Sky
@onready var far_ice: TextureRect = $Layers/FarIce
@onready var mid_ice: TextureRect = $Layers/MidIce
@onready var ice_ground: TextureRect = $Layers/IceGround

func _process(delta: float) -> void:
	_advance_layer(sky, sky_speed, delta)
	_advance_layer(far_ice, far_ice_speed, delta)
	_advance_layer(mid_ice, mid_ice_speed, delta)
	_advance_layer(ice_ground, ground_speed, delta)

func _advance_layer(layer: TextureRect, speed: float, delta: float) -> void:
	if layer == null:
		return
	var shader_material := layer.material as ShaderMaterial
	if shader_material == null:
		return
	var stored_offset: Variant = shader_material.get_shader_parameter("scroll_offset")
	var offset: float = 0.0
	if stored_offset is float or stored_offset is int:
		offset = stored_offset
	offset = fposmod(offset + speed * delta, 1.0)
	shader_material.set_shader_parameter("scroll_offset", offset)
