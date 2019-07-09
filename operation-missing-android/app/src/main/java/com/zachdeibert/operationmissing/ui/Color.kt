package com.zachdeibert.operationmissing.ui

import android.content.Context
import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat

class Color {
    var R: Float
    var G: Float
    var B: Float
    var A: Float

    constructor(r: Float, g: Float, b: Float, a: Float = 1f) {
        R = r
        G = g
        B = b
        A = a
    }

    constructor(context: Context, id: Int) {
        val color = ResourcesCompat.getColor(context.resources, id, context.theme)
        R = Color.red(color) / 256f
        G = Color.green(color) / 256f
        B = Color.blue(color) / 256f
        A = Color.alpha(color) / 256f
    }
}
