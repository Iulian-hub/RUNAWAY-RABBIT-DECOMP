shader_type canvas_item;

uniform float scroll_offset = 0.0;

void fragment() {
	vec2 scrolling_uv = UV;
	scrolling_uv.x = fract(scrolling_uv.x + scroll_offset);
	COLOR = texture(TEXTURE, scrolling_uv) * COLOR;
}
