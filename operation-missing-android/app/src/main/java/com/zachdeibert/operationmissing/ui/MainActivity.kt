package com.zachdeibert.operationmissing.ui

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private lateinit var glView: GLSurfaceView
    private lateinit var renderer: Renderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        glView = GLSurfaceView(this)
        glView.setEGLContextClientVersion(2)
        renderer = Renderer(glView)
        glView.setRenderer(renderer)
        setContentView(glView)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!super.onTouchEvent(event) && event != null) {
            renderer.onTouchEvent(event)
        }
        return true
    }
}
