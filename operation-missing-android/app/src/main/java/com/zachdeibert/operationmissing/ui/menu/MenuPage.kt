package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.view.MotionEvent
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer

class MenuPage(title: Int) : Component {
    private val titleId = title
    private lateinit var title: String
    private lateinit var color: Color

    override fun init(context: Context) {
        title = context.resources.getString(titleId)
        color = Color(context, R.color.foregroundPrimary)
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        renderer.text.default.drawString(title, 0, title.length - 1, -0.8f, -0.3f, 0.8f, -0.3f, mvp, color)
    }

    override fun onTouchEvent(event: MotionEvent) {
    }
}
