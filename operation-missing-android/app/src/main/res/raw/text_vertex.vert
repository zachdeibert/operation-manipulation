precision mediump float;

uniform vec2 uStart;
uniform vec2 uEnd;
uniform vec2 uHeight;
uniform mat4 uMVPMatrix;

attribute vec4 aChar;

varying float vPos;
varying vec2 vTexCoordA;
varying vec2 vTexCoordB;

void main() {
    vec2 d = uEnd - uStart;
    vec2 pos = uStart + d * aChar.z;
    int i = int(aChar.w + 0.5);
    vPos = float(i / 2);
    if ((i / 2) * 2 == i) {
        pos += uHeight;
        vTexCoordA.y = 0.0f;
        vTexCoordB.y = 0.0f;
    } else {
        vTexCoordA.y = 1.0f;
        vTexCoordB.y = 1.0f;
    }
    vTexCoordA.x = aChar.x;
    vTexCoordB.x = aChar.y;
    gl_Position = uMVPMatrix * vec4(pos, 0, 1);
}
