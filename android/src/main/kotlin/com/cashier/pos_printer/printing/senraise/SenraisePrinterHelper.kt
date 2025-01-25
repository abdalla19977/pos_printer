package com.cashier.pos_printer.printing.senraise

import android.content.Context
import android.graphics.Bitmap
import com.appy.cashier.common.printing.Printer
import com.appy.cashier.common.printing.Printer.Companion.SIZE_58
import com.cashier.pos_printer.printing.senraise.service.SenraisePrinterCallback
import com.cashier.pos_printer.printing.senraise.service.SenraisePrinterException
import com.cashier.pos_printer.printing.senraise.service.SenraisePrinterManager
import recieptservice.com.recieptservice.PrinterInterface

/**
 * @Author: abdalla atheer
 * @Email: abdallahatheer.us@gmail.com
 * @Date: 8/9/2022
 */
class SenraisePrinterHelper : Printer {


    /**
     * senraise means checking the printer connection status
     */

    /**
     * PrinterInterface for API
     */
    private var senraisePrinterService: PrinterInterface? = null


    private val innerPrinterCallback: SenraisePrinterCallback = object : SenraisePrinterCallback() {
        override fun onConnected(service: PrinterInterface) {
            senraisePrinterService = service
        }

        override fun onDisconnected() {
            senraisePrinterService = null
        }
    }
    override var leftAlignment: Int = 0
    override var centerAlignment: Int = 1
    override var rightAlignment: Int = 2


    override fun init(context: Context) {
        try {
            SenraisePrinterManager.getInstance().bindService(
                context,
                innerPrinterCallback
            )
        } catch (e: SenraisePrinterException) {
            e.printStackTrace()
        }
    }

    override fun start() {

    }

    override fun printText(text: String, textSize: Float, isBold: Boolean) {
        senraisePrinterService?.setTextSize(textSize)
        senraisePrinterService?.setTextBold(isBold)
        senraisePrinterService?.printText(text)
    }

    override fun printTable(texts: Array<String>, width: IntArray?, align: IntArray?) {
        senraisePrinterService?.printTableText(texts, width, align)
    }

    override fun printBitmap(bitmap: Bitmap) {
        senraisePrinterService?.printBitmap(bitmap)
    }

    override fun feedPaper(lines: Int) {
        senraisePrinterService?.nextLine(lines)
    }

    override fun setAlign(align: Int) {
        senraisePrinterService?.setAlignment(align)
    }

    override fun getPrinterSize(): String {
        return SIZE_58
    }

    override fun sendLcdDigital(text: String) {}

    override fun openDrawer() {}
    override fun release() {}

}