package com.zachdeibert.operationmissing.util

import android.content.Context
import java.io.ByteArrayOutputStream

fun readRawResource(context: Context, id: Int): ByteArray {
    val input = context.resources.openRawResource(id)
    val output = ByteArrayOutputStream()
    input.copyTo(output, 4096)
    return output.toByteArray()
}
