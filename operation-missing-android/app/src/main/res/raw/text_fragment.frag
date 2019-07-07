precision mediump float;

uniform vec3 uColor;
uniform sampler2D uTexture;

varying float vPos;
varying vec2 vTexCoordA;
varying vec2 vTexCoordB;

void main() {
    vec2 texCoord;
    int i = int(vPos);
    if ((i / 2) * 2 != i) {
        texCoord = vTexCoordA;
    } else {
        texCoord = vTexCoordB;
    }
    gl_FragColor = vec4(uColor, 1.0f - texture2D(uTexture, texCoord).x);
}
