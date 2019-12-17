#version 320 es

layout (location = 0) in vec3 vertices;
uniform mediump mat4 modelview;
uniform mediump mat4 projection;
out mediump vec4 vertexColor;

void main() {
    gl_Position = projection * modelview * vec4(vertices, 1);
    vertexColor = vec4(0.5, 0.0, 0.0, 1.0);
}