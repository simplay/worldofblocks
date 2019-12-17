#version 320 es
out mediump vec4 FragColor;

in mediump vec4 vertexColor; // the input variable from the vertex shader (same name and same type)

void main()
{
    FragColor = vertexColor;
}