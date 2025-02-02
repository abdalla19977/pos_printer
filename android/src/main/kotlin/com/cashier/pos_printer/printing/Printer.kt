package com.cashier.pos_printer.printing

import android.content.Context
import android.graphics.Bitmap
import com.cashier.pos_printer.PrinterStatus
import com.cashier.pos_printer.printing.numnum.PrinterSize

/**
 * @Author: abdalla atheer
 * @Email: abdallahatheer.us@gmail.com
 * @Date: 8/9/2022
 */
interface Printer {

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
     * @param isItalic to set text to italic mode
     * @param textSize 27f is preferred
     */
    fun printText(text: String, textSize: Float, isBold: Boolean, isItalic: Boolean)

    /**
     * Print a row of a table
     * @param width determine the width of text
     * @param align determine the alignment of text in the row
     */
    fun printTable(texts: Array<String>, width: IntArray?, align: IntArray?, fontSize: Int)

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
    fun getPrinterSize(): PrinterSize

    /**
     * Cut paper
     */
    fun cutPaper()

    /**
     * Send Number to display on LCD
     */
    fun sendTextToLcdDigital(text: String)

    /**
     * Send Image to display on LCD
     */
    fun sendImageToLcdDigital(bitmap: Bitmap)

    /**
     * send list of esc/pos command
     */
    fun escPosCommandExe(byteArray: ByteArray)

    /**
     * open cash drawer
     */
    fun openDrawer()

    /**
     * get printer head status like paper out or over heating
     */
    fun getStatus(): PrinterStatus

    /**
     * release printer and screen
     */
    fun release()

    /**
     * de init printer when app closes
     */
    fun deInitPrinter(context: Context?)
}