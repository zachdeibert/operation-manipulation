package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import com.zachdeibert.operationmissing.math.BezierCurve
import com.zachdeibert.operationmissing.math.Vector
import com.zachdeibert.operationmissing.ui.Color
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer
import kotlin.random.Random

const val MILLIS_PER_CONTROL_UNIT: Long = 10000

val random = Random(System.currentTimeMillis())

class BackgroundSprite(text: String, colorId: Int, alpha: Float) : Component {
    private val text: String = text
    private val colorId: Int = colorId
    private var start: BezierCurve
    private var end: BezierCurve
    private var startTime: Long = 0
    private var endTime: Long = 0
    private val alpha = alpha
    private lateinit var color: Color

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

    override fun onTouchEvent(event: MotionEvent) {
    }

    override fun init(context: Context) {
        color = Color(context, colorId)
        color.A = alpha
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        var t: Float = (System.currentTimeMillis() - startTime).toFloat() / (endTime - startTime).toFloat()
        if (t > 1) {
            t = 1f
        }
        val startP = start.evaluate(t)
        val endP = end.evaluate(t)
        renderer.text.default.drawString(text, 0, text.length - 1, startP[0], startP[1] - 0.5f, endP[0], endP[1] - 0.5f, mvp, color)
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
