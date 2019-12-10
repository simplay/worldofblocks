#version 150

uniform sampler2D sampler;

in vec2 tex_coords;
in vec4 passColor;

void main() {
     gl_FragColor = 0.5 * texture2D(sampler, tex_coords) + 0.5 * passColor;
}