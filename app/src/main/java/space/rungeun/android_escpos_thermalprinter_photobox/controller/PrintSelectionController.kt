package space.rungeun.android_escpos_thermalprinter_photobox.controller

import space.rungeun.android_escpos_thermalprinter_photobox.constants.count
import space.rungeun.android_escpos_thermalprinter_photobox.model.Counter

class PrintSelectionController(var currentCount :Int) {


    fun run(change: Int){
        currentCount = Counter.change(currentCount, change)
    }


}