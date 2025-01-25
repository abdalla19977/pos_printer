package com.cashier.pos_printer.printing.telpo

import android.content.Context
import android.graphics.Bitmap
import com.appy.cashier.common.printing.Printer
import com.appy.cashier.common.printing.Printer.Companion.SIZE_58
import com.cashier.pos_printer.utils.wait
import com.common.apiutil.lcd.SimpleSubLcd
import com.common.apiutil.moneybox.MoneyBox
import com.common.apiutil.printer.ThermalPrinter
import com.common.apiutil.printer.UsbThermalPrinter
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

    override fun printText(text: String, textSize: Float, isBold: Boolean) {
        usbThermalPrinter.setGray(5)
        usbThermalPrinter.setBold(isBold)
        usbThermalPrinter.setTextSize(textSize.toInt())
        usbThermalPrinter.addString(text)
    }

    override fun printTable(texts: Array<String>, width: IntArray?, align: IntArray?) {
        if (texts.size == 3) {
            val mappedText = arrayOf(texts[0], texts[2] + " * " + texts[1])
            usbThermalPrinter.addColumnsString(mappedText, intArrayOf(1, 3), intArrayOf(0, 0), 21)
        } else if (texts.size == 2) {
            usbThermalPrinter.addColumnsString(texts, intArrayOf(1, 3), intArrayOf(0, 0), 21)
        }
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

    override fun getPrinterSize(): String {
        return SIZE_58
    }

    override fun sendLcdDigital(text: String) {
//        simpleSubLcd.show(CanvasUtils.generateImageFromNumber(text))
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

    override fun openDrawer() {
        MoneyBox.boxControl(1, 1)
        wait(500) {
            MoneyBox.boxControl(1, 0)
        }
    }
}