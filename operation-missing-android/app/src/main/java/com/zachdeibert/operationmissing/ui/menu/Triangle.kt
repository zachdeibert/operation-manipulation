package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.opengl.GLES20
import com.zachdeibert.operationmissing.ui.shaders.ShapeShader
import com.zachdeibert.operationmissing.ui.shaders.TextShader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

// number of coordinates per vertex in this array
const val COORDS_PER_VERTEX = 2
var triangleCoords = floatArrayOf(     // in counterclockwise order:
    0.0f, 0.622008459f,      // top
    -0.5f, -0.311004243f,    // bottom left
    0.5f, -0.311004243f      // bottom right
)

class Triangle(context: Context) {

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }

    private var mProgram: Int = 0

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    init {
        mProgram = ShapeShader(context).id
        positionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition")
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor")
    }


    fun draw() {
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(
            positionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )
        GLES20.glUniform4fv(mColorHandle, 1, color, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}
