#version 320 es

out mediump vec4 FragColor;
in mediump vec4 vertexColor;

void main() {
    FragColor = vertexColor;
}