//package com.cashier.pos_printer.printing
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.Application
//import com.appy.cashier.common.printing.Printer
//import com.appy.cashier.common.printing.Printer.Companion.SIZE_80
//import com.cashier.pos_printer.printing.senraise.SenraisePrinterHelper
//import com.appy.cashier.common.printing.sunmi.SunmiPrintHelper
//import com.cashier.pos_printer.printing.telpo.TelpoPrintHelper
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//
//@SuppressLint("StaticFieldLeak")
//object PrintingUtils {
//
//    private lateinit var context: Activity
//    private lateinit var printer: Printer
//
//
//    fun init(context: Activity) {
//        PrintingUtils.context = context
//        printer = when {
//            isSenraise() -> SenraisePrinterHelper()
//            isSunmi() -> SunmiPrintHelper()
//            isTelpo() -> TelpoPrintHelper()
//            else -> SunmiPrintHelper()
//        }
//        printer.init(context)
//        linesSeparator = if (printer is TelpoPrintHelper) "" else "\n"
//
//        //delay init to fix a problem where the printer is not ready
//        wait {
//            printerConfig = getPrinterConfig(printer.getPrinterSize())
//        }
//    }
//
//    fun printInvoice(order: OrderPrintingData) {
//
//        openDrawer()
//
//        printer.setAlign(printer.centerAlignment)
//
//        ImageCache.logo?.let {
//            printer.printBitmap(it)
//            printer.printText(linesSeparator, 52F, true)
//        }
//
//        if (!order.user?.userName.isNullOrBlank()) {
//            printer.printText(
//                order.user?.userName + linesSeparator,
//                printerConfig.extraLargeFont,
//                true
//            )
//        }
//
//        if (!order.user?.userNumber.isNullOrBlank()) {
//            printer.printText(
//                "${order.user?.userNumber}$linesSeparator",
//                printerConfig.mediumFont,
//                true
//            )
//        }
//
//        if (!order.user?.location.isNullOrBlank()) {
//            printer.printText(
//                "${order.user?.location}$linesSeparator",
//                printerConfig.mediumFont,
//                false
//            )
//        }
//
//        printer.printText(
//            "${context.getString(R.string.order_num)} : #${order.orderNumber}$linesSeparator",
//            printerConfig.extraLargeFont,
//            true
//        )
//
//        order.customer?.let {
//            if (!it.name.isNullOrBlank())
//                printer.printText(
//                    "${context.getString(R.string.invoice_customer_name)} : ${it.name}$linesSeparator",
//                    printerConfig.mediumFont,
//                    false
//                )
//
//            if (!it.phone.isNullOrBlank())
//                printer.printText(
//                    "${context.getString(R.string.invoice_customer_phone)}: ${it.phone}$linesSeparator",
//                    printerConfig.mediumFont,
//                    false
//                )
//
//            if (!it.location.isNullOrBlank())
//                printer.printText(
//                    "${context.getString(R.string.invoice_customer_location)} : ${it.location}$linesSeparator",
//                    printerConfig.mediumFont,
//                    false
//                )
//        }
//
//        if (order.driver != null) {
//            printer.printText(
//                "اسم السائق : ${order.driver.name}$linesSeparator",
//                printerConfig.mediumFont,
//                false
//            )
//        }
//
//
//        printer.printText(
//            "${context.getString(R.string.invoice_order_type)} : ${
//                context.getString(
//                    order.orderType.typeName
//                )
//            }$linesSeparator", printerConfig.mediumFont,
//            false
//        )
//
//        if (order.tableNumber != 0)
//            printer.printText(
//                "${context.getString(R.string.tables_num)}:${order.tableNumber}$linesSeparator",
//                printerConfig.mediumFont,
//                false
//            )
//
//        if (printer !is TelpoPrintHelper) {
//            printer.printTable(
//                arrayOf(
//                    context.getString(R.string.total),
//                    context.getString(R.string.item_name),
//                    context.getString(R.string.count)
//                ),
//                intArrayOf(1, 2, 1),
//                intArrayOf(
//                    printer.leftAlignment,
//                    printer.rightAlignment,
//                    printer.rightAlignment
//                )
//            )
//        }
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//        order.cartItems.forEach { orderItem ->
//            printer.printTable(
//                orderItem.getItemForPrint(),
//                orderItem.getWeights(),
//                intArrayOf(printer.leftAlignment, printer.rightAlignment, printer.rightAlignment)
//            )
//        }
//        printer.setAlign(printer.centerAlignment)
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        printer.printText(
//            "${context.getString(R.string.total_net)}: ${FormattingUtils.formatPrice(order.cartItems.sumOf { it.count * it.price })}$linesSeparator",
//            printerConfig.mediumFont,
//            false
//        )
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        printer.printText(
//            "${context.getString(R.string.order_id)} : #${order.orderId}$linesSeparator",
//            printerConfig.mediumFont,
//            false
//        )
//
//        val currentDate = sdf.format(Date())
//
//        printer.printText(currentDate + linesSeparator, printerConfig.mediumFont, false)
//        printer.printText(printerConfig.separator + linesSeparator, printerConfig.smallFont, false)
//        if (order.localAuth?.status != Status.ACTIVE_PRO) {
//            printer.printText("كاشير للحلول البرمجية\n07700011454", printerConfig.smallFont, false)
//        }
//
//        printer.feedPaper(4)
//
//        if (isV3Mix()) {
//            printer.feedPaper(4)
//        }
//
//        printer.start()
//
//
//        sendLcdDigital(SCREEN_LCD_RESET_CODE)
//
//        printer.release()
//    }
//
//
//    fun printReport(
//        orderItemsDailyCount: List<Product>,
//        orderCount: String,
//        orderSum: String,
//        type: ReportType
//    ) {
//        printer.setAlign(printer.centerAlignment)
//
//        printer.printText(
//            context.getString(type.title) + linesSeparator,
//            printerConfig.extraLargeFont,
//            true
//        )
//
//        val sdf = SimpleDateFormat(type.dateFormat, Locale.ENGLISH)
//
//        val currentDate = if (type == ReportType.LAST_MONTH) {
//            val cal: Calendar = Calendar.getInstance()
//            cal.add(Calendar.MONTH, -1)
//            sdf.format(cal.time)
//        } else {
//            sdf.format(Date())
//        }
//
//        printer.printText(currentDate + linesSeparator, printerConfig.mediumFont, false)
//
//
//        printer.printText(
//            "${context.getString(R.string.brief)}$linesSeparator",
//            printerConfig.largeFont,
//            true
//        )
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        printer.printText(
//            "${context.getString(R.string.order_count)} : $orderCount$linesSeparator",
//            printerConfig.mediumFont, false
//        )
//
//        printer.printText(
//            "${context.getString(R.string.total)} : $orderSum$linesSeparator",
//            printerConfig.mediumFont, false
//        )
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        orderItemsDailyCount.forEach { order ->
//            printer.printTable(
//                order.getItemForPrint(),
//                order.getWeights(),
//                intArrayOf(
//                    printer.leftAlignment,
//                    printer.rightAlignment
//                )
//            )
//        }
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        printer.feedPaper(4)
//        if (isV3Mix()) {
//            printer.feedPaper(4)
//        }
//        printer.start()
//    }
//
//    fun printExpensesReport(
//        expenses: List<Expense>,
//        type: ReportType,
//    ) {
//        printer.setAlign(printer.centerAlignment)
//
//        printer.printText(
//            context.getString(type.title) + linesSeparator,
//            printerConfig.largeFont,
//            true
//        )
//
//        val currentDate = sdf.format(Date())
//        printer.printText(currentDate + linesSeparator, printerConfig.mediumFont, false)
//
//        printer.printText(
//            "${context.getString(R.string.brief)}$linesSeparator",
//            printerConfig.largeFont,
//            true
//        )
//
//        printer.printText(
//            "${
//                context.getString(R.string.total_expenses) + ":" + FormattingUtils.formatPrice(
//                    expenses.sumOf { it.amount })
//            }$linesSeparator",
//            printerConfig.largeFont,
//            true
//        )
//
//        printer.printText(printerConfig.separator, 27f, false)
//
//        expenses.forEach { expense ->
//            printer.printTable(
//                expense.getItemForPrint(),
//                expense.getWeights(),
//                intArrayOf(
//                    printer.leftAlignment,
//                    printer.rightAlignment
//                )
//            )
//        }
//
//        printer.printText(printerConfig.separator + linesSeparator, 27f, false)
//
//        printer.feedPaper(4)
//        if (isV3Mix()) {
//            printer.feedPaper(4)
//        }
//        printer.start()
//        printer.release()
//    }
//
//    fun is80mm() = printer.getPrinterSize() == SIZE_80
//
//    fun sendLcdDigital(text: String) {
//        if (this::printer.isInitialized) printer.sendLcdDigital(text)
//    }
//
//    fun openDrawer() {
//        if (this::printer.isInitialized) printer.openDrawer()
//    }
//
//    fun release() {
//        if (this::printer.isInitialized) printer.release()
//    }
//
//
//}