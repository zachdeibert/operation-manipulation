package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.view.MotionEvent
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer

class Title : Component {
    private lateinit var str: String
    private lateinit var color: Color

    override fun init(context: Context) {
        str = context.resources.getString(R.string.app_name)
        color = Color(context, R.color.foregroundPrimary)
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        renderer.text.default.drawString(str, 0, str.length - 1, -1f, 0.5f, 1f, 0.5f, mvp, color)
    }

    override fun onTouchEvent(event: MotionEvent) {
    }
}
