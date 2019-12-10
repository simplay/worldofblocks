#version 150

in vec3 vertices;
in vec2 textures;
in vec4 colors;

out vec2 tex_coords;
out vec4 passColor;

void main() {
    tex_coords = textures;
    passColor = colors;
    gl_Position = vec4(vertices, 1);
}
