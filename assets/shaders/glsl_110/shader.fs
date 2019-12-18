#version 110

uniform sampler2D sampler;

varying vec2 tex_coords;
varying vec4 passColor;

void main() {
     gl_FragColor = 0.5 * texture2D(sampler, tex_coords) + 0.5 * passColor;
}
