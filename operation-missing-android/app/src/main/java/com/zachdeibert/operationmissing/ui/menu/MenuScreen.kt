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
        var str = renderer.surface.context.resources.getString(R.string.app_name)
        renderer.text.default.drawString(str, 0, str.length - 1, -1f, 0.5f, 1f, 0.5f, ident4, 1f, 1f, 1f, 1f)
        str = renderer.surface.context.resources.getString(R.string.mode_singleplayer)
        renderer.text.default.drawString(str, 0, str.length - 1, -0.7f, 0f, 0.7f, 0f, ident4, 1f, 1f, 1f, 1f)
        str = renderer.surface.context.resources.getString(R.string.mode_multiplayer)
        renderer.text.default.drawString(str, 0, str.length - 1, -0.7f, -0.3f, 0.7f, -0.3f, ident4, 1f, 1f, 1f, 1f)
        str = renderer.surface.context.resources.getString(R.string.mode_options)
        renderer.text.default.drawString(str, 0, str.length - 1, -0.5f, -0.6f, 0.5f, -0.6f, ident4, 1f, 1f, 1f, 1f)
        str = renderer.surface.context.resources.getString(R.string.mode_credits)
        renderer.text.default.drawString(str, 0, str.length - 1, -0.7f, -0.9f, -0.1f, -0.9f, ident4, 1f, 1f, 1f, 1f)
        str = renderer.surface.context.resources.getString(R.string.mode_rate)
        renderer.text.default.drawString(str, 0, str.length - 1, 0.2f, -0.9f, 0.6f, -0.9f, ident4, 1f, 1f, 1f, 1f)
    }

    init {
        children.add(Background())
    }
}
