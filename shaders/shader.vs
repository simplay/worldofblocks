#version 150

in vec3 vertices;
in vec4 colors;
out vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;

void main() {
    passColor = colors;
    gl_Position = projection * modelview * vec4(vertices, 1);
}
