shader_type canvas_item;

uniform sampler2D layer_texture : source_color, repeat_enable;
uniform float scroll_speed = -0.04;

void fragment() {
	vec2 uv = UV;
	uv.x = fract(uv.x + TIME * scroll_speed);
	COLOR = texture(layer_texture, uv);
}
