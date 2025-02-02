package com.cashier.pos_printer.printing.telpo

import android.content.Context
import android.graphics.Bitmap
import com.cashier.pos_printer.PrinterStatus
import com.cashier.pos_printer.printing.Printer
import com.cashier.pos_printer.printing.numnum.PrinterSize
import com.cashier.pos_printer.utils.wait
import com.common.apiutil.lcd.SimpleSubLcd
import com.common.apiutil.moneybox.MoneyBox
import com.common.apiutil.printer.ThermalPrinter
import com.common.apiutil.printer.UsbThermalPrinter
import com.common.apiutil.printer.UsbThermalPrinter.STATUS_NO_PAPER
import com.common.apiutil.printer.UsbThermalPrinter.STATUS_OK
import com.common.apiutil.printer.UsbThermalPrinter.STATUS_OVER_HEAT
import com.common.apiutil.util.SDKUtil

class TelpoPrintHelper : Printer {

    override var leftAlignment: Int = ThermalPrinter.ALGIN_LEFT
    override var centerAlignment: Int = ThermalPrinter.ALGIN_MIDDLE
    override var rightAlignment: Int = ThermalPrinter.ALGIN_RIGHT

    private lateinit var usbThermalPrinter: UsbThermalPrinter
    private lateinit var simpleSubLcd: SimpleSubLcd


    override fun init(context: Context) {
        SDKUtil.getInstance(context).initSDK()
        simpleSubLcd = SimpleSubLcd(context)
        simpleSubLcd.init()
        usbThermalPrinter = UsbThermalPrinter(context)
        usbThermalPrinter.start(0)
    }

    override fun printText(text: String, textSize: Float, isBold: Boolean, isItalic: Boolean) {
        usbThermalPrinter.setGray(5)
        usbThermalPrinter.setBold(isBold)
        usbThermalPrinter.setItalic(isItalic)
        usbThermalPrinter.setTextSize(textSize.toInt())
        usbThermalPrinter.addString(text)
    }

    override fun printTable(
        texts: Array<String>,
        width: IntArray?,
        align: IntArray?,
        fontSize: Int
    ) {
        usbThermalPrinter.addColumnsString(texts, width, align, fontSize)
    }

    override fun printBitmap(bitmap: Bitmap) {
        try {
            usbThermalPrinter.printLogo(bitmap, true)
        } catch (_: Exception) {
        }
    }


    override fun feedPaper(lines: Int) {
        usbThermalPrinter.addString("\n".repeat(lines))
    }

    override fun setAlign(align: Int) {
        usbThermalPrinter.setAlgin(align)
    }

    override fun getPrinterSize(): PrinterSize {
        return PrinterSize.SIZE_58
    }

    override fun cutPaper() {
        usbThermalPrinter.paperCut()
    }

    override fun sendTextToLcdDigital(text: String) {}

    override fun sendImageToLcdDigital(bitmap: Bitmap) {
        simpleSubLcd.show(bitmap)
    }

    override fun escPosCommandExe(byteArray: ByteArray) {
        usbThermalPrinter.EscPosCommandExe(byteArray)
    }

    override fun start() {
        try {
            usbThermalPrinter.printString()
        } catch (_: Exception) {

        }
    }

    override fun release() {
        usbThermalPrinter.stop()
    }

    override fun deInitPrinter(context: Context?) {
        usbThermalPrinter.stop()
    }

    override fun openDrawer() {
        MoneyBox.boxControl(1, 1)
        wait(500) {
            MoneyBox.boxControl(1, 0)
        }
    }

    override fun getStatus(): PrinterStatus {
        return when (usbThermalPrinter.checkStatus()) {
            STATUS_OK -> PrinterStatus.NORMAL
            STATUS_NO_PAPER -> PrinterStatus.OUT_OF_PAPER
            STATUS_OVER_HEAT -> PrinterStatus.OVER_HEATING
            else -> PrinterStatus.GENERAL_ERROR
        }
    }
}