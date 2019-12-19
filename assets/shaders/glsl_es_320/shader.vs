#version 320 es

in vec3 position;
in vec2 texture;
in vec4 color;

out mediump vec2 tex_coords;
out mediump vec4 passColor;

uniform mediump mat4 modelview;
uniform mediump mat4 projection;

void main() {
    tex_coords = texture;
    passColor = color;
    gl_Position = projection * modelview * vec4(position, 1);
}