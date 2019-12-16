#version 150

varying vec4 passColor;

out vec4 fragColor;
void main() {
     fragColor = passColor;
}