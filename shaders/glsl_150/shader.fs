#version 150

uniform sampler2D sampler;

in vec2 tex_coords;
in vec4 passColor;
out vec4 FragColor;

void main() {
     FragColor = 0.5 * texture(sampler, tex_coords) + 0.5 * passColor;
}