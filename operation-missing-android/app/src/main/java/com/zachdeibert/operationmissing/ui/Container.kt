package com.zachdeibert.operationmissing.ui

import android.content.Context
import android.view.MotionEvent

open class Container : Component {
    val children = mutableListOf<Component>()

    override fun init(context: Context) {
        for (child in children) {
            child.init(context)
        }
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
        for (child in children) {
            child.onResize(renderer, width, height)
        }
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        for (child in children) {
            child.render(renderer, mvp)
        }
    }

    override fun onTouchEvent(event: MotionEvent) {
        for (child in children) {
            child.onTouchEvent(event)
        }
    }
}
