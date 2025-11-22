package space.rungeun.android_escpos_thermalprinter_photobox.constants


enum class count(val value: Int) {
    change(1),
    max(6),
    min(1);

    override fun toString() = value.toString()
}