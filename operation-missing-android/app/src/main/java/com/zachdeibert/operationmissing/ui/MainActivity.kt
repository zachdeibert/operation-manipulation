package com.zachdeibert.operationmissing.ui

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private lateinit var glView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        glView = GLSurfaceView(this)
        glView.setEGLContextClientVersion(2)
        glView.setRenderer(Renderer(glView))
        glView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        setContentView(glView)
    }
}
