package com.zachdeibert.operationmissing.ui

import android.content.Context

interface Component {
    fun init(context: Context)
    fun onResize(renderer: Renderer, width: Int, height: Int)
    fun render(renderer: Renderer)
}
