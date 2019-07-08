package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import android.view.MotionEvent
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Container
import com.zachdeibert.operationmissing.ui.Renderer
import com.zachdeibert.operationmissing.ui.Screen

class MenuScreen : SlidingMenu(Background(), Title(), arrayOf(
    MenuPage(R.string.mode_singleplayer),
    MenuPage(R.string.mode_multiplayer),
    MenuPage(R.string.mode_options),
    MenuPage(R.string.mode_rate),
    MenuPage(R.string.mode_credits)
)), Screen {
    override lateinit var clearColor: Color

    override fun init(context: Context) {
        clearColor = Color(context, R.color.backgroundPrimary)
        super.init(context)
    }
}
