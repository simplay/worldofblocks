#version 110

varying vec3 vertices;
varying vec2 textures;
varying vec4 colors;

varying vec2 tex_coords;
varying vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;

void main() {
    tex_coords = textures;
    passColor = colors;
    gl_Position = projection * modelview * vec4(vertices, 1);
}
