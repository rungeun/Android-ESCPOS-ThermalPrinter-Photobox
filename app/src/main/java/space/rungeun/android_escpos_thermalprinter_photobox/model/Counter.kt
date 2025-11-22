package space.rungeun.android_escpos_thermalprinter_photobox.model

import space.rungeun.android_escpos_thermalprinter_photobox.constants.count

object Counter {

    fun change(currentValue: Int, change: Int): Int {
        val changedValue = currentValue + change

        if (changedValue >= count.min.value && changedValue <= count.max.value)
            return changedValue
        return currentValue
    }
}