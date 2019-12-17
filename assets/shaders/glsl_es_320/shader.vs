#version 320 es

in vec3 vertices;
in vec2 textures;
in vec4 colors;


out mediump vec2 tex_coords;
out mediump vec4 passColor;

uniform mediump mat4 modelview;
uniform mediump mat4 projection;

void main() {
    tex_coords = textures;
    passColor = colors;
    gl_Position = projection * modelview * vec4(vertices, 1);
}