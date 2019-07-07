package com.zachdeibert.operationmissing.util

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

const val TAG = "BitmapExport"

fun exportBitmap(name: String, bitmap: Bitmap, context: Context) {
    val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), String.format("%s.png", name))
    if (file.exists()) {
        file.delete()
    }
    Log.i(TAG, String.format("Saving image to %s", file.absolutePath))
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
    out.flush()
    out.close()
    MediaScannerConnection.scanFile(context.applicationContext, arrayOf(file.absolutePath), arrayOf("image/png")) { _: String, uri: Uri ->
        Log.i(TAG, String.format("Scanned %s", uri))
    }
}
