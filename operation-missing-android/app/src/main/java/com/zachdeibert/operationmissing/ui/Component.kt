package com.zachdeibert.operationmissing.ui

interface Component {
    fun onResize(renderer: Renderer, width: Int, height: Int)
    fun render(renderer: Renderer)
}
