#version 320 es

out mediump vec4 FragColor;

uniform sampler2D sampler;

in mediump vec2 tex_coords;
in mediump vec4 passColor;

void main() {
    FragColor = 0.5 * texture2D(sampler, tex_coords) + 0.5 * passColor;
}