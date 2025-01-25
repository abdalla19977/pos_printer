package com.cashier.pos_printer

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** PosPrinterPlugin */
class PosPrinterPlugin : FlutterPlugin, PosPrinter, ActivityAware {


    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        PosPrinter.setUp(flutterPluginBinding.binaryMessenger, this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        PosPrinter.setUp(binding.binaryMessenger, null)
    }


    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        //init here
    }

    override fun onDetachedFromActivityForConfigChanges() {
        //de init here
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        //init here
    }

    override fun onDetachedFromActivity() {
        //de init here
    }

    override fun init(grayLevel: Long): Long {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun printText(text: String, textSize: Double, isBold: Boolean, isItalic: Boolean) {
        TODO("Not yet implemented")
    }

    override fun printTable(
        texts: List<String>,
        width: List<Long>,
        align: List<Long>,
        fontSize: Long
    ) {
        TODO("Not yet implemented")
    }

    override fun printBitmap(bitmap: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun feedPaper(lines: Long) {
        TODO("Not yet implemented")
    }

    override fun setAlign(align: Alignments) {
        TODO("Not yet implemented")
    }

    override fun getPrinterSize(): PrinterSize {
        TODO("Not yet implemented")
    }

    override fun sendTextToLcdDigital(text: String) {
        TODO("Not yet implemented")
    }

    override fun sendImageLcdDigital(bitmap: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun printBarcode(data: String, symbology: Long, width: Long, height: Long) {
        TODO("Not yet implemented")
    }

    override fun printQrCode(data: String) {
        TODO("Not yet implemented")
    }

    override fun getSecondaryScreenSize(): SecondaryScreenSize {
        TODO("Not yet implemented")
    }

    override fun openDrawer() {
        TODO("Not yet implemented")
    }

    override fun cutPaper() {
        TODO("Not yet implemented")
    }

    override fun escPosCommandExe(commands: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getPrinterStatus(): PrinterStatus {
        TODO("Not yet implemented")
    }

    override fun deInitPrinter() {
        TODO("Not yet implemented")
    }

    override fun isTelpo(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSunmi(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSenraise(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPos(): Boolean {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

}
