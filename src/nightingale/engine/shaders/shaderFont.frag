#version 120

uniform sampler2D sampler;
varying vec2 texturesCoordinates;
uniform vec4 colour;

void main() {
    gl_FragColor = texture2D(sampler, texturesCoordinates) * colour;
}
