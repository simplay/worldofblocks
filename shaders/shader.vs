#version 150

in vec3 vertices;
in vec2 textures;
in vec4 colors;

out vec2 tex_coords;
out vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;

void main() {
    tex_coords = textures;
    passColor = colors;
    gl_Position =  projection * modelview * vec4(vertices, 1);
}
