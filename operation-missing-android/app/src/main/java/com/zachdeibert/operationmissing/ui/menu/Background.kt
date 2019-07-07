package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.Container

const val NUM_BACKGROUND_SPRITES = 12
const val BACKGROUND_SPRITE_ALPHA = 0.1f

class Background : Container() {
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
