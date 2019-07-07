package com.zachdeibert.operationmissing.ui.shaders

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.zachdeibert.operationmissing.R
import java.nio.FloatBuffer

class TextShader(context: Context) : Shader(context, R.raw.text_vertex, R.raw.text_fragment) {
    val uStart: Int
    val uEnd: Int
    val uHeight: Int
    val uMVPMatrix: Int
    val aChar: Int
    val uColor: Int
    val uTexture: Int

    override fun enable() {
        super.enable()
        GLES20.glEnableVertexAttribArray(aChar)
    }

    override fun disable() {
        super.disable()
        GLES20.glDisableVertexAttribArray(aChar)
    }

    fun setStart(x: Float, y: Float) {
        GLES20.glUniform2f(uStart, x, y)
    }

    fun setEnd(x: Float, y: Float) {
        GLES20.glUniform2f(uEnd, x, y)
    }

    fun setHeight(x: Float, y: Float) {
        GLES20.glUniform2f(uHeight, x, y)
    }

    fun setMVPMatrix(matrix: FloatArray) {
        GLES20.glUniformMatrix4fv(uMVPMatrix, 1, false, matrix, 0)
    }

    fun uploadVerts(data: FloatBuffer) {
        data.position(0)
        GLES20.glVertexAttribPointer(aChar, 4, GLES20.GL_FLOAT, false, 4 * 4, data)
    }

    fun setColor(r: Float, g: Float, b: Float, a: Float) {
        GLES20.glUniform4f(uColor, r, g, b, a)
    }

    fun setTexture(id: Int) {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id)
        GLES20.glUniform1i(uTexture, 0)
    }

    init {
        uStart = GLES20.glGetUniformLocation(id, "uStart")
        uEnd = GLES20.glGetUniformLocation(id, "uEnd")
        uHeight = GLES20.glGetUniformLocation(id, "uHeight")
        uMVPMatrix = GLES20.glGetUniformLocation(id, "uMVPMatrix")
        aChar = GLES20.glGetAttribLocation(id, "aChar")
        uColor = GLES20.glGetUniformLocation(id, "uColor")
        uTexture = GLES20.glGetUniformLocation(id, "uTexture")
    }
}
