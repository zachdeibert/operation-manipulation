package com.zachdeibert.operationmissing.math

import java.lang.IllegalArgumentException
import kotlin.math.sqrt

class BezierCurve(points: Array<FloatArray>) {
    val points: Array<FloatArray> = points

    override fun toString(): String {
        val str = StringBuilder()
        var firstPoint = true
        str.append("BezierCurve {")
        for (point in points) {
            if (firstPoint) {
                firstPoint = false
            } else {
                str.append(", ")
            }
            str.append("(")
            var firstDim = true
            for (dim in point) {
                if (firstDim) {
                    firstDim = false
                } else {
                    str.append(", ")
                }
                str.append(dim)
            }
            str.append(")")
        }
        str.append("}")
        return str.toString()
    }

    private fun interpolate(start: FloatArray, end: FloatArray, t: Float): FloatArray {
        if (t < 0 || t > 1) {
            throw IllegalArgumentException("t must be between 0 and 1")
        }
        if (start.size != end.size) {
            throw IllegalArgumentException("Two points must have the same dimensionality")
        }
        val res = FloatArray(start.size)
        for (i in 0..start.size-1) {
            res[i] = start[i] + (end[i] - start[i]) * t
        }
        return res
    }

    fun evaluate(t: Float): FloatArray {
        if (t < 0 || t > 1) {
            throw IllegalArgumentException("t must be between 0 and 1")
        }
        if (points.size == 0) {
            return FloatArray(0)
        }
        var p = points
        while (p.size > 1) {
            p = Array<FloatArray>(p.size - 1) { i: Int -> interpolate(p[i], p[i + 1], t) }
        }
        return p[0]
    }

    fun controlDistance(): Float {
        var dist = 0f
        for (i: Int in 0..points.size-2) {
            var dist2 = 0f
            for (j: Int in 0..points[0].size-1) {
                val diff = points[i + 1][j] - points[i][j]
                dist2 += diff * diff
            }
            dist += sqrt(dist2)
        }
        return dist
    }

    init {
        val size = points[0].size
        for (arr: FloatArray in points) {
            if (arr.size != size) {
                throw IllegalArgumentException("All control points must have the same dimensionality")
            }
        }
    }
}
