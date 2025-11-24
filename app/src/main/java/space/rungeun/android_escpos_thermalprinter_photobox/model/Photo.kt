package space.rungeun.android_escpos_thermalprinter_photobox.model

import android.graphics.Bitmap

data class Photo(
    var count: Int = 1,
    val frameNumber: Int,
    val picture: Bitmap,
    val video: ByteArray? = null, // 미구현
    val timestamp: Long = System.currentTimeMillis()
) {
    fun incrementCount() {
        count++
    }
}