package com.zachdeibert.operationmissing.ui

import android.content.Context
import android.graphics.*
import android.opengl.GLES20
import android.opengl.GLUtils
import com.zachdeibert.operationmissing.R
import com.zachdeibert.operationmissing.ui.shaders.TextShader
import com.zachdeibert.operationmissing.util.exportBitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.sqrt

class Font : Iterable<Glyph> {
    private val glyphs: MutableMap<Char, Glyph> = mutableMapOf()
    private val texture: Int
    private val shader: TextShader
    private val height: Float
    private val textureWidth: Float

    override fun iterator(): Iterator<Glyph> {
        return glyphs.values.iterator()
    }

    fun measureString(str: CharSequence, offset: Int, end: Int, dx: Float, dy: Float): FloatArray {
        var width: Float = 0f
        for (i in offset..end) {
            var glyph = glyphs[str[i]]
            if (glyph == null) {
                glyph = glyphs.iterator().next().value
            }
            width += glyph.endX - glyph.startX
        }
        val mag: Float = sqrt(dx * dx + dy * dy)
        return floatArrayOf(width, height * mag / width)
    }

    fun drawString(str: CharSequence, offset: Int, end: Int, startX: Float, startY: Float, endX: Float, endY: Float, mvp: FloatArray, color: Color) {
        val bounds = measureString(str, offset, end, endX - startX, endY - startY)
        shader.enable()
        shader.setStart(startX, startY)
        shader.setEnd(endX, endY)
        shader.setHeight(0f, bounds[1]) // TODO Fix normal
        shader.setMVPMatrix(mvp)
        shader.setColor(color.R, color.G, color.B, color.A)
        shader.setTexture(texture)
        val data = ByteBuffer.allocateDirect(4 * 4 * 2 * (end - offset + 2)).order(ByteOrder.nativeOrder()).asFloatBuffer()
        var prev: Glyph? = null
        var xPos: Float = 0f
        var counter: Float = 0f
        var polarity = false
        for (i in offset..end) {
            val cur = glyphs[str[i]]
            var p = (prev?.endX ?: 0) / textureWidth
            var n = (cur?.startX ?: 0) / textureWidth
            if (polarity) {
                val t = p
                p = n
                n = t
            }
            polarity = !polarity
            data.put(floatArrayOf(p, n, xPos, counter, p, n, xPos, counter + 1))
            counter += 2
            xPos += ((cur?.endX ?: 0) - (cur?.startX ?: 0)) / bounds[0]
            prev = cur
        }
        var p = (prev?.endX ?: 0) / textureWidth
        var n = 0f
        if (polarity) {
            val t = p
            p = n
            n = t
        }
        data.put(floatArrayOf(p, n, 1f, counter, p, n, 1f, counter + 1))
        shader.uploadVerts(data)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 2 * (end - offset + 2))
    }

    constructor(size: Float, shader: TextShader, context: Context) {
        val paint = Paint()
        paint.textSize = size
        paint.isAntiAlias = true
        paint.setARGB(0xFF, 0x00, 0x00, 0x00)
        val dictionary = context.resources.getString(R.string.all_chars).toCharArray()
        var totalWidth = 0
        for (i in 0..dictionary.size-1) {
            val width = paint.measureText(dictionary, i, 1)
            val newWidth = (totalWidth + width + 1f).toInt()
            glyphs[dictionary[i]] = Glyph(dictionary[i], totalWidth, newWidth)
            totalWidth = newWidth + 10
        }
        val metrics = paint.fontMetrics
        height = metrics.bottom - metrics.top
        val bmp = Bitmap.createBitmap(totalWidth, (height + 1f - Float.MIN_VALUE).toInt(), Bitmap.Config.ARGB_8888)
        bmp.eraseColor(android.graphics.Color.WHITE)
        val canvas = Canvas(bmp)
        for (i in 0..dictionary.size-1) {
            val glyph = glyphs[dictionary[i]]
            if (glyph != null) {
                canvas.drawText(dictionary, i, 1, glyph.startX.toFloat(), -metrics.top, paint)
            }
        }
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        texture = textures[0]
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT)
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0)
        exportBitmap("font-sprites", bmp, context)
        textureWidth = bmp.width.toFloat()
        bmp.recycle()
        this.shader = shader
    }
}
