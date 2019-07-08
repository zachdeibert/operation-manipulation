package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.opengl.GLES20
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Container
import com.zachdeibert.operationmissing.ui.Renderer

const val NUM_BACKGROUND_SPRITES = 12
const val BACKGROUND_SPRITE_ALPHA = 0.1f

class Background : Container() {
    override fun render(renderer: Renderer, mvp: FloatArray) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        super.render(renderer, mvp)
    }

    override fun init(context: Context) {
        val chars = context.resources.getText(R.string.background_chars)
        val colors = intArrayOf(R.color.backgroundAccent1,
                                R.color.backgroundAccent2,
                                R.color.backgroundAccent3,
                                R.color.backgroundAccent4)
        for (i in 1..NUM_BACKGROUND_SPRITES) {
            children.add(BackgroundSprite(String(charArrayOf(chars[random.nextInt(chars.length)])), colors[random.nextInt(colors.size)], BACKGROUND_SPRITE_ALPHA))
        }
    }
}
