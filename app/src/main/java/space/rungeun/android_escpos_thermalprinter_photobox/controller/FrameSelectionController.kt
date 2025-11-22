package space.rungeun.android_escpos_thermalprinter_photobox.controller

import space.rungeun.android_escpos_thermalprinter_photobox.model.Counter

class FrameSelectionController(var currentCount: Int) {

    fun run(change: Int) {
        currentCount = Counter.change(currentCount, change)
    }
}