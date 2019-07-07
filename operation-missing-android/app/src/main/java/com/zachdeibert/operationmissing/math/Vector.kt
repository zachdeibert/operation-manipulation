package com.zachdeibert.operationmissing.math

import java.lang.IllegalArgumentException
import kotlin.math.sqrt

class Vector(data: FloatArray) {
    var data: FloatArray = data
    val size: Int
        get() = data.size

    fun magnitude(): Float {
        var mag2 = 0f
        for (v in data) {
            mag2 += v * v
        }
        return sqrt(mag2)
    }

    fun normalize(): Vector {
        return this / magnitude()
    }

    fun dot(other: Vector): Float {
        if (size != other.size) {
            throw IllegalArgumentException("Vectors must have the same dimensionality")
        }
        var res = 0f
        for (i in 0..size-1) {
            res += data[i] * other.data[i]
        }
        return res
    }

    fun projectOnto(dir: Vector): Vector {
        val d = dir.normalize()
        return d * dot(d)
    }

    operator fun plus(other: Vector): Vector {
        if (size != other.size) {
            throw IllegalArgumentException("Vectors must have the same dimensionality")
        }
        val new = FloatArray(size)
        for (i in 0..size-1) {
            new[i] = data[i] + other.data[i]
        }
        return Vector(new)
    }

    operator fun minus(other: Vector): Vector {
        if (size != other.size) {
            throw IllegalArgumentException("Vectors must have the same dimensionality")
        }
        val new = FloatArray(size)
        for (i in 0..size-1) {
            new[i] = data[i] - other.data[i]
        }
        return Vector(new)
    }

    operator fun times(scalar: Float): Vector {
        val new = FloatArray(size)
        for (i in 0..size-1) {
            new[i] = data[i] * scalar
        }
        return Vector(new)
    }

    operator fun div(scalar: Float): Vector {
        val new = FloatArray(size)
        for (i in 0..size-1) {
            new[i] = data[i] / scalar
        }
        return Vector(new)
    }
}