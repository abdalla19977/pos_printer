package com.cashier.pos_printer.utils

import android.os.Handler
import android.os.Looper

fun wait(duration: Long = 300L, block: () -> Unit) {
    Handler(Looper.getMainLooper()).apply {
        removeCallbacks(block)
        postDelayed({ block() }, duration)
    }
}
