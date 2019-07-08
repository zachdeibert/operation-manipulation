package com.zachdeibert.operationmissing.ui

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.zachdeibert.operationmissing.ui.menu.MenuScreen
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class Renderer(surface: GLSurfaceView) : GLSurfaceView.Renderer {
    private var _screen: Screen = MenuScreen()
    var screen: Screen
        get() = _screen
        set(value) {
            _screen = value
            _screen.init(surface.context)
        }
    var surface: GLSurfaceView = surface
    private lateinit var _text: TextRenderer
    val text: TextRenderer
        get() = _text

    override fun onDrawFrame(gl: GL10?) {
        screen.render(this, floatArrayOf(1f, 0f, 0f, 0f,
                                                 0f, 1f, 0f, 0f,
                                                 0f, 0f, 1f, 0f,
                                                 0f, 0f, 0f, 1f))
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        screen.onResize(this, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        _text = TextRenderer(surface.context)
        screen.init(surface.context)
        var color = screen.clearColor
        GLES20.glClearColor(color.R, color.G, color.B, color.A)
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
    }

    fun onTouchEvent(event: MotionEvent) {
        screen.onTouchEvent(event)
    }
}
