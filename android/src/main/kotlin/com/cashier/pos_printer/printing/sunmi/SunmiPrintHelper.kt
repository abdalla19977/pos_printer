package com.cashier.pos_printer.printing.sunmi

import android.content.Context
import android.graphics.Bitmap
import android.os.RemoteException
import com.cashier.pos_printer.PrinterStatus
import com.cashier.pos_printer.printing.Printer
import com.cashier.pos_printer.printing.numnum.PrinterSize
import com.sunmi.peripheral.printer.ExceptionConst
import com.sunmi.peripheral.printer.InnerPrinterCallback
import com.sunmi.peripheral.printer.InnerPrinterException
import com.sunmi.peripheral.printer.InnerPrinterManager
import com.sunmi.peripheral.printer.InnerResultCallback
import com.sunmi.peripheral.printer.SunmiPrinterService
import com.sunmi.peripheral.printer.WoyouConsts


/**
 * migrated to kotlin from original example below
 * https://github.com/shangmisunmi/SunmiPrinterDemo/blob/master/app/src/main/java/com/sunmi/printerhelper/utils/SunmiPrintHelper.java
 *
 * @author abdalla atheer
 */

class SunmiPrintHelper : Printer {

    companion object {
        private var NoSunmiPrinter = 0x00000000
        private var CheckSunmiPrinter = 0x00000001
        private var FoundSunmiPrinter = 0x00000002
        private var LostSunmiPrinter = 0x00000003
    }


    override var leftAlignment: Int = 0
    override var centerAlignment: Int = 1
    override var rightAlignment: Int = 2

    /**
     * sunmiPrinter means checking the printer connection status
     */
    private var sunmiPrinter = CheckSunmiPrinter

    /**
     * SunmiPrinterService for API
     */
    private var sunmiPrinterService: SunmiPrinterService? = null


    private val innerPrinterCallback: InnerPrinterCallback = object : InnerPrinterCallback() {
        override fun onConnected(service: SunmiPrinterService) {
            sunmiPrinterService = service
            checkSunmiPrinterService(service)
        }

        override fun onDisconnected() {
            sunmiPrinterService = null
            sunmiPrinter = LostSunmiPrinter
        }
    }

    override fun start() {}

    /**
     * init sunmi print service
     */
    override fun init(context: Context) {
        try {
            val ret = InnerPrinterManager.getInstance().bindService(
                context,
                innerPrinterCallback
            )
            if (!ret) {
                sunmiPrinter = NoSunmiPrinter
            }
            initPrinter()
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }


    override fun printTable(
        texts: Array<String>,
        width: IntArray?,
        align: IntArray?,
        fontSize: Int
    ) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.printColumnsString(texts, width, align, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Check the printer connection,
     * like some devices do not have a printer but need to be connected to the cash drawer through a print service
     */
    private fun checkSunmiPrinterService(service: SunmiPrinterService) {
        var ret = false
        try {
            ret = InnerPrinterManager.getInstance().hasPrinter(service)
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
        sunmiPrinter =
            if (ret) FoundSunmiPrinter else NoSunmiPrinter
    }

    /**
     * Some conditions can cause interface calls to fail
     * For example: the version is too low、device does not support
     * You can see [ExceptionConst]
     * So you have to handle these exceptions
     */
    private fun handleRemoteException(e: RemoteException) {
        e.printStackTrace()
    }


    /**
     * Initialize the printer
     * All style settings will be restored to default
     */
    private fun initPrinter() {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.printerInit(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }


    override fun feedPaper(lines: Int) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.autoOutPaper(null)
        } catch (e: RemoteException) {
            print3Line()
        }
    }

    private fun print3Line() {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.lineWrap(3, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }


    /**
     * Get printing distance since boot
     * Get printing distance through interface callback since 1.0.8(printerlibrary)
     */
    fun getPrinterDistance(callback: InnerResultCallback?) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.getPrintedLength(callback)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }


    override fun setAlign(align: Int) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.setAlignment(align, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }


    override fun printText(
        text: String, textSize: Float, isBold: Boolean, isItalic: Boolean
    ) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            try {
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_BOLD,
                    if (isBold) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_ILALIC,
                    if (isItalic) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
            } catch (e: RemoteException) {
                if (isBold) {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOn(), null)
                } else {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOff(), null)
                }
            }

            sunmiPrinterService!!.printTextWithFont(text, null, textSize, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }


    override fun printBitmap(bitmap: Bitmap) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.printBitmap(bitmap, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun getPrinterSize(): PrinterSize {
        return if (sunmiPrinterService == null) {
            PrinterSize.SIZE_58
        } else try {
            if (sunmiPrinterService!!.printerPaper == 1) PrinterSize.SIZE_58 else PrinterSize.SIZE_80
        } catch (e: RemoteException) {
            handleRemoteException(e)
            PrinterSize.SIZE_58
        }
    }

    override fun cutPaper() {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.cutPaper(null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun sendTextToLcdDigital(text: String) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.sendLCDDigital(text, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun sendImageToLcdDigital(bitmap: Bitmap) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.sendLCDBitmap(bitmap, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun escPosCommandExe(byteArray: ByteArray) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.sendRAWData(byteArray, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun openDrawer() {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            sunmiPrinterService!!.openDrawer(null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun getStatus(): PrinterStatus {
        return when (sunmiPrinterService?.updatePrinterState()) {
            1 -> PrinterStatus.NORMAL
            4 -> PrinterStatus.OUT_OF_PAPER
            5 -> PrinterStatus.OVER_HEATING
            6 -> PrinterStatus.COVER_OPEN
            else -> PrinterStatus.GENERAL_ERROR
        }
    }

    override fun release() {}

    /**
     * deInit sunmi print service
     */
    override fun deInitPrinter(context: Context?) {
        try {
            if (sunmiPrinterService != null) {
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback)
                sunmiPrinterService = null
                sunmiPrinter = LostSunmiPrinter
            }
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }


}