#version 150

#define MAX_LIGHTS 2

in vec3 vertices;
in vec2 textures;
in vec4 colors;

out vec2 tex_coords;
out vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;

uniform vec3 lightRadiances[MAX_LIGHTS];
uniform vec4 lightDirections[MAX_LIGHTS];

void main() {
    vec4 lightDir = vec4(vec4(lightDirections[0].xyz, 1) - vec4(vertices, 1));
    float l = max(dot(lightDir.xyz, vec3(1,0,0)), 0.0);

    tex_coords = textures;
    passColor = normalize(colors + vec4(l, l, l, 0));
    gl_Position = projection * modelview * vec4(vertices, 1);
}