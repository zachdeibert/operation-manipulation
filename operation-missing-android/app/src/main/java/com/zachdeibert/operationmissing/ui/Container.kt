package com.zachdeibert.operationmissing.ui

import android.content.Context

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

    override fun render(renderer: Renderer) {
        for (child in children) {
            child.render(renderer)
        }
    }
}
