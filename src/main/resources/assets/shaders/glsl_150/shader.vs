#version 150

#define MAX_LIGHTS 10

in vec3 position;
in vec2 texture;
in vec4 color;
in vec3 normal;

out vec2 tex_coords;
out vec4 passColor;

uniform mat4 modelview;
uniform mat4 projection;
uniform mat4 transformation;

uniform int pointLightCount;
uniform vec3 pointLightRadiances[MAX_LIGHTS];
uniform vec4 pointLightPositions[MAX_LIGHTS];

uniform vec3 sunRadiance;
uniform vec4 sunDirection;

void main() {
    vec4 vertexPosition =  projection * modelview * transformation * vec4(position, 1);
    tex_coords = texture;

    vec3 contribution = vec3(0);
    for (int k = 0; k < pointLightCount; k++) {
        vec4 lightPosition = projection * modelview * pointLightPositions[k];
        vec3 lightColor = pointLightRadiances[k];

        vec4 lightDir = lightPosition - vec4(position, 1);
        contribution += lightColor * max(dot(lightDir.xyz, normal), 0.0);
    }

    vec3 sunContribution = sunRadiance * sunDirection.xyz;
    passColor = normalize(vec4(sunContribution, 0) + vec4(contribution, 0) + color);

    gl_Position = vertexPosition;
}