package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.view.MotionEvent
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer

class Title : Component {
    private lateinit var str: String

    override fun init(context: Context) {
        str = context.resources.getString(R.string.app_name)
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        renderer.text.default.drawString(str, 0, str.length - 1, -1f, 0.5f, 1f, 0.5f, mvp, 1f, 1f, 1f, 1f)
    }

    override fun onTouchEvent(event: MotionEvent) {
    }
}
