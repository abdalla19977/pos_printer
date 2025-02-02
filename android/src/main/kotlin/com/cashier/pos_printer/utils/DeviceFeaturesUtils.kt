package com.cashier.pos_printer.utils

import android.os.Build


object DeviceFeaturesUtils {

    fun isSunmi() = Build.BRAND == "SUNMI"

    fun isSenraise() = Build.BRAND.contains("POSH5") || Build.MODEL in
            listOf(
                "POS-OS01",
                "POSP-OS01",
                "H10",
                "H10S"
            )

    fun isTelpo() = Build.BRAND == "qti" || Build.MODEL in listOf("M8")

}