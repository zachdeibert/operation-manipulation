package com.zachdeibert.operationmissing.ui

import android.content.Context
import com.zachdeibert.operationmissing.ui.shaders.TextShader

class TextRenderer(context: Context) {
    val shader = TextShader(context)
    val default = Font(256f, shader, context)
}