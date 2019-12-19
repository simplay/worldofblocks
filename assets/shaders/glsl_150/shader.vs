#version 150

#define MAX_LIGHTS 10

in vec3 vertices;
in vec2 textures;
in vec4 colors;

out vec2 tex_coords;
out vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;

uniform int pointLightCount;
uniform vec3 pointLightRadiances[MAX_LIGHTS];
uniform vec4 pointLightPositions[MAX_LIGHTS];

void main() {
    vec4 vertexPosition = projection * modelview * vec4(vertices, 1);
    tex_coords = textures;

    float contribution = 0;
    for (int k = 0; k < pointLightCount; k++) {
        vec4 lightPosition = projection * modelview * pointLightPositions[0];
        vec4 lightDir = lightPosition - vec4(vertices, 1);
        contribution += max(-dot(lightDir.xyz, vec3(1,0,0)), 0.0);
    }
    passColor = normalize(vec4(vec3(contribution), 1) + colors);

    gl_Position = vertexPosition;
}