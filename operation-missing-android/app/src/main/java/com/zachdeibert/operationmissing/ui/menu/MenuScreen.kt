package com.zachdeibert.operationmissing.ui.menu

import android.opengl.GLES20
import android.opengl.Matrix
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Renderer
import com.zachdeibert.operationmissing.ui.Screen

class MenuScreen : Screen {
    override lateinit var clearColor: Color
    lateinit var triangle: Triangle

    override fun destroy(renderer: Renderer) {
    }

    override fun init(renderer: Renderer) {
        clearColor = Color(renderer.surface.context, R.color.menuBackground)
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
    }

    override fun render(renderer: Renderer) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        triangle = Triangle(renderer.surface.context)
        triangle.draw()
        val mvp = FloatArray(16)
        Matrix.setIdentityM(mvp, 0)
        renderer.text.default.drawString("Hello, world!", 0, "Hello, world!".length - 1, -1f, 0f, 1f, 0f, mvp, 0.4f, 0.1f, 0.4f)
    }
}
