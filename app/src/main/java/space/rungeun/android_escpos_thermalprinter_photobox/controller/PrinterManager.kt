package space.rungeun.android_escpos_thermalprinter_photobox.controller

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.Log
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.usb.UsbConnection
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.rungeun.android_escpos_thermalprinter_photobox.model.Photo

object PrinterManager {

    private const val PRINTER_DPI = 203
    private const val PAPER_WIDTH_MM = 72f
    private const val TARGET_WIDTH_PX = 472
    private const val MAX_IMAGE_HEIGHT = 240
    private const val DEFAULT_BRIGHTNESS = 20f
    private const val DEFAULT_CONTRAST = 1.3f

    suspend fun printPhotos(
        context: Context,
        photos: List<Photo>,
        onProgress: (String) -> Unit = {}
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val printer = connectPrinter(context, onProgress) ?:
            return@withContext Result.failure(Exception("프린터를 찾을 수 없습니다"))

            val printContent = generatePrintContent(printer, photos, onProgress)

            onProgress("인쇄 중...")
            printer.printFormattedTextAndCut(printContent)

            onProgress("인쇄 완료!")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("PrinterManager", "Print error", e)
            Result.failure(e)
        }
    }

    private fun connectPrinter(
        context: Context,
        onProgress: (String) -> Unit
    ): EscPosPrinter? {
        onProgress("프린터 연결 중...")
        val connection = UsbPrintersConnections.selectFirstConnected(context) ?: return null
        return EscPosPrinter(connection, PRINTER_DPI, PAPER_WIDTH_MM, 47)
    }

    private fun generatePrintContent(
        printer: EscPosPrinter,
        photos: List<Photo>,
        onProgress: (String) -> Unit
    ): String = buildString {
        append(getHeader())

        photos.forEachIndexed { index, photo ->
            onProgress("사진 ${index + 1}/${photos.size} 처리 중...")
            append(processPhoto(printer, photo))
            append("[L]\n")
        }

        append(getFooter("https://github.com/rungeun/Android-ESCPOS-ThermalPrinter-Photobox"))
    }

    private fun processPhoto(printer: EscPosPrinter, photo: Photo): String {
        val scaledBitmap = scaleToTargetWidth(photo.picture)
        val enhancedBitmap = enhanceBrightnessAndContrast(scaledBitmap)
        val result = convertToEscPosImage(printer, enhancedBitmap)

        cleanupBitmaps(photo.picture, scaledBitmap, enhancedBitmap)
        return result
    }

    private fun scaleToTargetWidth(bitmap: Bitmap): Bitmap {
        val targetHeight = (bitmap.height * TARGET_WIDTH_PX / bitmap.width)
        return Bitmap.createScaledBitmap(bitmap, TARGET_WIDTH_PX, targetHeight, true)
    }

    private fun convertToEscPosImage(printer: EscPosPrinter, bitmap: Bitmap): String {
        return if (bitmap.height > MAX_IMAGE_HEIGHT) {
            splitAndConvertImage(printer, bitmap)
        } else {
            convertSingleImage(printer, bitmap)
        }
    }

    private fun splitAndConvertImage(printer: EscPosPrinter, bitmap: Bitmap): String = buildString {
        val slices = calculateSliceCount(bitmap.height)

        for (i in 0 until slices) {
            val slice = createSlice(bitmap, i)
            append("[C]<img>")
            append(PrinterTextParserImg.bitmapToHexadecimalString(printer, slice))
            append("</img>\n")
            slice.recycle()
        }
    }

    private fun calculateSliceCount(height: Int): Int {
        return (height + MAX_IMAGE_HEIGHT - 1) / MAX_IMAGE_HEIGHT
    }

    private fun createSlice(bitmap: Bitmap, sliceIndex: Int): Bitmap {
        val startY = sliceIndex * MAX_IMAGE_HEIGHT
        val sliceHeight = minOf(MAX_IMAGE_HEIGHT, bitmap.height - startY)
        return Bitmap.createBitmap(bitmap, 0, startY, bitmap.width, sliceHeight)
    }

    private fun convertSingleImage(printer: EscPosPrinter, bitmap: Bitmap): String {
        return "[C]<img>${PrinterTextParserImg.bitmapToHexadecimalString(printer, bitmap)}</img>\n"
    }

    private fun enhanceBrightnessAndContrast(
        bitmap: Bitmap,
        brightness: Float = DEFAULT_BRIGHTNESS,
        contrast: Float = DEFAULT_CONTRAST
    ): Bitmap {
        val colorMatrix = createColorMatrix(brightness, contrast)
        return applyColorMatrix(bitmap, colorMatrix)
    }

    private fun createColorMatrix(brightness: Float, contrast: Float): ColorMatrix {
        return ColorMatrix(floatArrayOf(
            contrast, 0f, 0f, 0f, brightness,
            0f, contrast, 0f, 0f, brightness,
            0f, 0f, contrast, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        ))
    }

    private fun applyColorMatrix(bitmap: Bitmap, colorMatrix: ColorMatrix): Bitmap {
        val paint = Paint().apply {
            colorFilter = ColorMatrixColorFilter(colorMatrix)
        }
        val config = bitmap.config ?: Bitmap.Config.ARGB_8888
        val result = Bitmap.createBitmap(bitmap.width, bitmap.height, config)
        Canvas(result).drawBitmap(bitmap, 0f, 0f, paint)
        return result
    }

    private fun cleanupBitmaps(original: Bitmap, scaled: Bitmap, enhanced: Bitmap) {
        if (scaled != original) scaled.recycle()
        if (enhanced != scaled) enhanced.recycle()
    }

    private fun getHeader(): String = buildString {
        val dateTime = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date())

        append("[C]<u><font size='big'>CE&IE</font></u>\n")
        append("[L]\n")
        append("[C]$dateTime\n")
        append("[C]============================\n")
        append("[L]\n")
    }

    private fun getFooter(qrId: String): String = buildString {
        append("[L]\n")
        append("[C]============================\n")
        append("[L]\n")
        append("[C]contact me: me@rungeun.space\n")
        append("[R]<qrcode>$qrId</qrcode>\n")
        append("[L]\n")
    }
}