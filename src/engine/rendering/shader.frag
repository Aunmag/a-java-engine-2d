#version 120

uniform sampler2D sampler;
varying vec2 texturesCoordinates;

void main() {
    gl_FragColor = texture2D(sampler, texturesCoordinates);
}
