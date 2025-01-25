package com.appy.cashier.common.printing

import android.content.Context
import android.graphics.Bitmap
import com.appy.cashier.common.printing.sunmi.SunmiPrintHelper

/**
 * @Author: abdalla atheer
 * @Email: abdallahatheer.us@gmail.com
 * @Date: 8/9/2022
 */
interface Printer {

    companion object{
        const val SIZE_58 = "58"
        const val SIZE_80 = "80"
    }

    var leftAlignment: Int
    var centerAlignment: Int
    var rightAlignment: Int

    /**
     * initialize the printer preferably inside application or a content Provider
     */
    fun init(context: Context)


    /**
     * start printing
     * used in case of printers only working with commit mode
     * like telpo printers
     */
    fun start()

    /**
     * print text
     * @param isBold to set text to bold
     * @param textSize 27f is preferred
     */
    fun printText(text: String, textSize: Float, isBold: Boolean)

    /**
     * Print a row of a table
     * @param width determine the width of text
     * @param align determine the alignment of text in the row
     */
    fun printTable(texts: Array<String>, width: IntArray?, align: IntArray?)

    /**
     * Print an image
     */
    fun printBitmap(bitmap: Bitmap)

    /**
     * Due to the distance between the paper hatch and the print head,
     * the paper needs to be fed out automatically
     */
    fun feedPaper(lines: Int)

    /**
     * Set printer alignment
     */
    fun setAlign(align: Int)

    /**
     * Get printer head size 58mm or 80mm
     */
    fun getPrinterSize(): String

    /**
     * Send Number to display on LCD
     */
    fun sendLcdDigital(text:String)

    /**
     * open cash drawer
     */
    fun openDrawer()

    /**
     * release printer and screen
     */
    fun release()
}