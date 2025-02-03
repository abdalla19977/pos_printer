package com.cashier.pos_printer.printing.senraise

import android.content.Context
import android.graphics.Bitmap
import com.cashier.pos_printer.PrinterStatus
import com.cashier.pos_printer.printing.Printer
import com.cashier.pos_printer.printing.numnum.PrinterSize
import com.github.senraise_printer.SenraisePrinterHelper
import com.github.senraise_printer.service.SenraisePrinterException

class SenraisePrinterHelper : Printer {

    private val senraisePrinterService = SenraisePrinterHelper()

    override var leftAlignment: Int = 0
    override var centerAlignment: Int = 1
    override var rightAlignment: Int = 2


    override fun init(context: Context) {
        try {
            senraisePrinterService.init(context)
        } catch (e: SenraisePrinterException) {
            e.printStackTrace()
        }
    }

    override fun start() {

    }

    override fun printText(text: String, textSize: Float, isBold: Boolean, isItalic: Boolean) {
        senraisePrinterService.printText(text,textSize,isBold)
    }

    override fun printTable(
        texts: Array<String>,
        width: IntArray?,
        align: IntArray?,
        fontSize: Int
    ) {
        senraisePrinterService.printTable(texts, width, align)
    }

    override fun printBitmap(bitmap: Bitmap) {
        senraisePrinterService.printBitmap(bitmap)
    }

    override fun feedPaper(lines: Int) {
        senraisePrinterService.feedPaper(lines)
    }

    override fun setAlign(align: Int) {
        senraisePrinterService.setAlign(align)
    }

    override fun getPrinterSize(): PrinterSize {
        return PrinterSize.SIZE_58
    }

    override fun escPosCommandExe(byteArray: ByteArray) {
        senraisePrinterService.printEpson(byteArray)
    }

    override fun getStatus(): PrinterStatus {
        return PrinterStatus.NORMAL
    }

    override fun deInitPrinter(context: Context?) {
        try {
            if (context != null) {
                senraisePrinterService.deInit(context)
            }
        } catch (e: SenraisePrinterException) {
            e.printStackTrace()
        }
    }

    override fun release() {}

    override fun cutPaper() {}

    override fun openDrawer() {}

    override fun sendTextToLcdDigital(text: String) {}

    override fun sendImageToLcdDigital(bitmap: Bitmap) {}

}