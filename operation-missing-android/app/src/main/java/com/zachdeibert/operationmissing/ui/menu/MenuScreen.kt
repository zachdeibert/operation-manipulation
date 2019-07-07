package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Container
import com.zachdeibert.operationmissing.ui.Renderer
import com.zachdeibert.operationmissing.ui.Screen

class MenuScreen : Container(), Screen {
    override lateinit var clearColor: Color

    override fun init(context: Context) {
        clearColor = Color(context, R.color.backgroundPrimary)
        super.init(context)
    }

    override fun render(renderer: Renderer) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        super.render(renderer)
    }

    init {
        children.add(Background())
    }
}
