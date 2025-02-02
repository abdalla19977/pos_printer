package com.cashier.pos_printer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.cashier.pos_printer.printing.Printer
import com.cashier.pos_printer.printing.senraise.SenraisePrinterHelper
import com.cashier.pos_printer.printing.sunmi.SunmiPrintHelper
import com.cashier.pos_printer.printing.telpo.TelpoPrintHelper
import com.cashier.pos_printer.utils.DeviceFeaturesUtils
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** PosPrinterPlugin */
class PosPrinterPlugin : FlutterPlugin, PosPrinter, ActivityAware {

    private lateinit var printer: Printer
    private lateinit var context: Context

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        PosPrinter.setUp(flutterPluginBinding.binaryMessenger, this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        PosPrinter.setUp(binding.binaryMessenger, null)
    }


    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        init(binding.activity)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        if (::context.isInitialized)
            printer.deInitPrinter(context)
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        init(binding.activity)
    }

    override fun onDetachedFromActivity() {
        if (::context.isInitialized)
            printer.deInitPrinter(context)
    }

    private fun init(context: Context) {
        printer = when {
            DeviceFeaturesUtils.isSenraise() -> SenraisePrinterHelper()
            DeviceFeaturesUtils.isSunmi() -> SunmiPrintHelper()
            DeviceFeaturesUtils.isTelpo() -> TelpoPrintHelper()
            else -> SunmiPrintHelper()
        }
        printer.init(context)
    }

    override fun start() {
        printer.start()
    }

    override fun printText(text: String, textSize: Double, isBold: Boolean, isItalic: Boolean) {
        printer.printText(
            text = text,
            textSize = textSize.toFloat(),
            isBold = isBold,
            isItalic = isItalic
        )
    }

    override fun printTable(
        texts: List<String>,
        width: List<Long>,
        align: List<Long>,
        fontSize: Long,
    ) {
        printer.printTable(
            texts.toTypedArray(),
            width.map { it.toInt() }.toIntArray(),
            align.map { it.toInt() }.toIntArray(),
            fontSize.toInt(),
        )
    }

    override fun printBitmap(bitmap: ByteArray) {
        val decodedBitmap: Bitmap = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        printer.printBitmap(decodedBitmap)
    }

    override fun feedPaper(lines: Long) {
        printer.feedPaper(lines.toInt())
    }

    override fun setAlign(align: Alignments) {
        printer.setAlign(align.raw)
    }

    override fun getPrinterSize(): PrinterSize {
        return when (printer.getPrinterSize()) {
            com.cashier.pos_printer.printing.numnum.PrinterSize.SIZE_58 -> PrinterSize.SIZE_58MM
            com.cashier.pos_printer.printing.numnum.PrinterSize.SIZE_80 -> PrinterSize.SIZE_80MM
        }
    }

    override fun sendTextToLcdDigital(text: String) {
        printer.sendTextToLcdDigital(text)
    }

    override fun sendImageLcdDigital(bitmap: ByteArray) {
        val decodedBitmap: Bitmap = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        printer.sendImageToLcdDigital(decodedBitmap)
    }

    override fun openDrawer() {
        printer.openDrawer()
    }

    override fun cutPaper() {
        printer.cutPaper()
    }

    override fun escPosCommandExe(commands: ByteArray) {
        printer.escPosCommandExe(commands)
    }

    override fun getPrinterStatus(): PrinterStatus {
        return printer.getStatus()
    }

    override fun deInitPrinter() {
        if (::context.isInitialized)
            printer.deInitPrinter(context)
    }

    override fun getDeviceManufacture(): DeviceManufacture {
        return when {
            DeviceFeaturesUtils.isSenraise() -> DeviceManufacture.SENRAISE
            DeviceFeaturesUtils.isSunmi() -> DeviceManufacture.SUNMI
            DeviceFeaturesUtils.isTelpo() -> DeviceManufacture.TELPO
            else -> DeviceManufacture.UNKNOWN
        }
    }

    override fun release() {
        printer.release()
    }

}
