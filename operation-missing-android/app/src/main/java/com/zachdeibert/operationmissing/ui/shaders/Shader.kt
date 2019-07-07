package com.zachdeibert.operationmissing.ui.shaders

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.zachdeibert.operationmissing.util.readRawResource
import java.nio.charset.Charset

const val TAG = "Shader"

open class Shader {
    val id: Int

    open fun enable() {
        GLES20.glUseProgram(id)
    }

    open fun disable() {
    }

    constructor(context: Context, vertId: Int, fragId: Int) {
        id = GLES20.glCreateProgram()
        if (id != 0) {
            val vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
            GLES20.glShaderSource(vertShader, readRawResource(context, vertId).toString(Charset.forName("UTF-8")))
            GLES20.glCompileShader(vertShader)
            val status = IntArray(1)
            GLES20.glGetShaderiv(vertShader, GLES20.GL_COMPILE_STATUS, status, 0)
            if (status[0] == 0) {
                Log.e(TAG, String.format("Vertex shader compile error: %s", GLES20.glGetShaderInfoLog(vertShader)))
                GLES20.glDeleteShader(vertShader)
            } else {
                GLES20.glAttachShader(id, vertShader)
            }
            val fragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
            GLES20.glShaderSource(fragShader, readRawResource(context, fragId).toString(Charset.forName("UTF-8")))
            GLES20.glCompileShader(fragShader)
            GLES20.glGetShaderiv(fragShader, GLES20.GL_COMPILE_STATUS, status, 0)
            if (status[0] == 0) {
                Log.e(TAG, String.format("Fragment shader compile error: %s", GLES20.glGetShaderInfoLog(fragShader)))
                GLES20.glDeleteShader(fragShader)
            } else {
                GLES20.glAttachShader(id, fragShader)
            }
            GLES20.glLinkProgram(id)
            GLES20.glGetProgramiv(id, GLES20.GL_LINK_STATUS, status, 0)
            if (status[0] == 0) {
                Log.e(TAG, String.format("Shader link error: %s", GLES20.glGetProgramInfoLog(id)))
                GLES20.glDeleteProgram(id)
            }
        } else {
            Log.e(TAG, "Unable to create shader program")
        }
    }
}
