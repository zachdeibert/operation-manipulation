package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.graphics.Color
import android.util.Log
import com.zachdeibert.operationmissing.math.BezierCurve
import com.zachdeibert.operationmissing.math.Vector
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer
import kotlin.random.Random

const val MILLIS_PER_CONTROL_UNIT: Long = 10000

val random = Random(System.currentTimeMillis())
val ident4 = floatArrayOf(1f, 0f, 0f, 0f,
                          0f, 1f, 0f, 0f,
                          0f, 0f, 1f, 0f,
                          0f, 0f, 0f, 1f)

class BackgroundSprite(text: String, colorId: Int, alpha: Float) : Component {
    private val text: String = text
    private val colorId: Int = colorId
    private var start: BezierCurve
    private var end: BezierCurve
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var r: Float = 0f
    private var g: Float = 0f
    private var b: Float = 0f
    private val a: Float = alpha

    private fun random(): Float = random.nextFloat() * 2f - 1f

    private fun resetTimer() {
        startTime = System.currentTimeMillis()
        val dist = (start.controlDistance() + end.controlDistance()) / 2f
        endTime = startTime + (MILLIS_PER_CONTROL_UNIT * dist).toLong()
    }

    private fun projectNewPath(path: BezierCurve): BezierCurve {
        val cur = Vector(path.points[2])
        val control = Vector(path.points[1])
        val next = Vector(floatArrayOf(random(), random()))
        val dir = cur - control
        val short = next - cur
        val newControl = short.projectOnto(dir) + cur
        return BezierCurve(arrayOf(cur.data, newControl.data, next.data))
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
    }

    override fun init(context: Context) {
        val color = context.resources.getColor(colorId)
        r = Color.red(color) / 255f
        g = Color.green(color) / 255f
        b = Color.blue(color) / 255f
    }

    override fun render(renderer: Renderer) {
        var t: Float = (System.currentTimeMillis() - startTime).toFloat() / (endTime - startTime).toFloat()
        if (t > 1) {
            t = 1f
        }
        val startP = start.evaluate(t)
        val endP = end.evaluate(t)
        renderer.text.default.drawString(text, 0, text.length - 1, startP[0], startP[1], endP[0], endP[1], ident4, r, g, b, a)
        Log.d("BackgroundSprite", String.format("(%f, %f) -> (%f, %f)", startP[0], startP[1], endP[0], endP[1]))
        if (t >= 1f) {
            start = projectNewPath(start)
            end = projectNewPath(end)
            resetTimer()
        }
    }

    init {
        start = BezierCurve(arrayOf(floatArrayOf(random(), random()),
                                    floatArrayOf(random(), random()),
                                    floatArrayOf(random(), random())))
        end = BezierCurve(arrayOf(floatArrayOf(random(), random()),
                                  floatArrayOf(random(), random()),
                                  floatArrayOf(random(), random())))
        resetTimer()
    }
}
