#version 330 core
layout (location = 0) in vec3 vertices; // the position variable has attribute position 0

uniform mat4 modelview;
uniform mat4 projection;

out vec4 vertexColor; // specify a color output to the fragment shader

void main() {
gl_Position = projection * modelview * vec4(vertices, 1);
vertexColor = vec4(0.5, 0.0, 0.0, 1.0); // set the output variable to a dark-red color
}