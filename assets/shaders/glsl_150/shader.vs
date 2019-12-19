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

    vec3 contribution = vec3(0);
    for (int k = 0; k < pointLightCount; k++) {
        vec4 lightPosition = projection * modelview * pointLightPositions[k];
        vec3 lightColor = pointLightRadiances[k];

        vec4 lightDir = lightPosition - vec4(vertices, 1);
        contribution += lightColor * max(-dot(lightDir.xyz, vec3(1,0,0)), 0.0);
    }
    passColor = normalize(vec4(contribution, 1) + colors);

    gl_Position = vertexPosition;
}