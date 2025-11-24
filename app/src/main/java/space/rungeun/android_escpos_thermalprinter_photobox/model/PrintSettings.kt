package space.rungeun.android_escpos_thermalprinter_photobox.model

object PrintSettings {
    var printCount: Int = 1        // 인쇄 매수 (영수증 장수)
        private set

    var frameCount: Int = 1        // 프레임 개수 (촬영 횟수)
        private set

    fun setPrintCount(count: Int) {
        printCount = count.coerceAtLeast(1)
    }

    fun setFrameCount(count: Int) {
        frameCount = count.coerceAtLeast(1)
    }

    fun reset() {
        printCount = 1
        frameCount = 1
    }
}