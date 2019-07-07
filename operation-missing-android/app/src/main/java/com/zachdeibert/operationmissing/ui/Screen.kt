package com.zachdeibert.operationmissing.ui

interface Screen : Component {
    val clearColor: Color
    fun init(renderer: Renderer)
    fun destroy(renderer: Renderer)
}
