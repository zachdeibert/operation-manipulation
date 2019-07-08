package com.zachdeibert.operationmissing.ui

import android.content.Context
import android.view.MotionEvent

interface Component {
    fun init(context: Context)
    fun onResize(renderer: Renderer, width: Int, height: Int)
    fun render(renderer: Renderer, mvp: FloatArray)
    fun onTouchEvent(event: MotionEvent)
}
