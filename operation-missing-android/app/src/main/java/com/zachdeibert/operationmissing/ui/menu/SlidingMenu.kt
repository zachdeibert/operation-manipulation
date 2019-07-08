package com.zachdeibert.operationmissing.ui.menu

import android.content.Context
import android.opengl.Matrix
import android.view.MotionEvent
import com.zachdeibert.operationmissing.ui.Component
import com.zachdeibert.operationmissing.ui.Renderer
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round
import kotlin.math.sign

const val SLIDING_RESTORE_SPEED_PER_MILLI = 0.004f

open class SlidingMenu(background: Component?, foreground: Component?, pages: Array<Component>) : Component {
    private val background = background
    private val foreground = foreground
    private val pages = pages
    private var scroll: Float = 0f
    private var width: Int = 0
    private var startScroll: Float? = null
    private var lastX: Float = 0f
    private var lastRender: Long = 0

    override fun init(context: Context) {
        foreground?.init(context)
        background?.init(context)
        for (page in pages) {
            page.init(context)
        }
    }

    override fun onResize(renderer: Renderer, width: Int, height: Int) {
        foreground?.onResize(renderer, width, height)
        background?.onResize(renderer, width, height)
        for (page in pages) {
            page.onResize(renderer, width, height)
        }
        this.width = width
    }

    override fun render(renderer: Renderer, mvp: FloatArray) {
        val renderTime = System.currentTimeMillis()
        background?.render(renderer, mvp)
        val left = ((floor(scroll).toInt() % pages.size) + pages.size) % pages.size
        val right = ((ceil(scroll).toInt() % pages.size) + pages.size) % pages.size
        if (left == right) {
            pages[left].render(renderer, mvp)
        } else {
            var render = true
            if (startScroll == null) {
                val target = round(scroll)
                val dir = sign(target - scroll)
                scroll += dir * SLIDING_RESTORE_SPEED_PER_MILLI * (renderTime - lastRender)
                if (sign(target - scroll) != dir) {
                    scroll = target
                    pages[((floor(scroll).toInt() % pages.size) + pages.size) % pages.size].render(renderer, mvp)
                    render = false
                }
            }
            if (render) {
                val frac = scroll - floor(scroll)
                val m = FloatArray(16)
                Matrix.translateM(m, 0, mvp, 0, -2 * frac, 0f, 0f)
                pages[left].render(renderer, m)
                Matrix.translateM(m, 0, 2f, 0f, 0f)
                pages[right].render(renderer, m)
            }
        }
        foreground?.render(renderer, mvp)
        lastRender = renderTime
    }

    override fun onTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startScroll = scroll
                lastX = event.x
            }
            MotionEvent.ACTION_UP -> {
                if (startScroll != null) {
                    startScroll = null
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (startScroll != null) {
                    val dx = lastX - event.x
                    lastX = event.x
                    scroll += dx / width
                }
            }
            MotionEvent.ACTION_OUTSIDE -> {
                if (startScroll != null) {
                    if (event.x > width / 2) {
                        scroll = ceil(scroll)
                    } else {
                        scroll = floor(scroll)
                    }
                    startScroll = null
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                val startScroll = startScroll
                if (startScroll != null) {
                    scroll = startScroll
                    this.startScroll = null
                }
            }
        }
        // TODO Pass through to children
    }
}
