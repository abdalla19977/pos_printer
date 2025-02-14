// Autogenerated from Pigeon (v22.7.4), do not edit directly.
// See also: https://pub.dev/packages/pigeon
@file:Suppress("UNCHECKED_CAST", "ArrayInDataClass")

package com.cashier.pos_printer

import android.util.Log
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MessageCodec
import io.flutter.plugin.common.StandardMethodCodec
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

private fun wrapResult(result: Any?): List<Any?> {
  return listOf(result)
}

private fun wrapError(exception: Throwable): List<Any?> {
  return if (exception is FlutterError) {
    listOf(
      exception.code,
      exception.message,
      exception.details
    )
  } else {
    listOf(
      exception.javaClass.simpleName,
      exception.toString(),
      "Cause: " + exception.cause + ", Stacktrace: " + Log.getStackTraceString(exception)
    )
  }
}

/**
 * Error class for passing custom error details to Flutter via a thrown PlatformException.
 * @property code The error code.
 * @property message The error message.
 * @property details The error details. Must be a datatype supported by the api codec.
 */
class FlutterError (
  val code: String,
  override val message: String? = null,
  val details: Any? = null
) : Throwable()

enum class Alignments(val raw: Int) {
  LEFT(0),
  CENTER(1),
  RIGHT(2);

  companion object {
    fun ofRaw(raw: Int): Alignments? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class PrinterSize(val raw: Int) {
  SIZE_58MM(0),
  SIZE_80MM(1);

  companion object {
    fun ofRaw(raw: Int): PrinterSize? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class PrinterStatus(val raw: Int) {
  NORMAL(0),
  OUT_OF_PAPER(1),
  OVER_HEATING(2),
  COVER_OPEN(3),
  GENERAL_ERROR(4);

  companion object {
    fun ofRaw(raw: Int): PrinterStatus? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}

enum class DeviceManufacture(val raw: Int) {
  SUNMI(0),
  SENRAISE(1),
  TELPO(2),
  UNKNOWN(3);

  companion object {
    fun ofRaw(raw: Int): DeviceManufacture? {
      return values().firstOrNull { it.raw == raw }
    }
  }
}
private open class PosPrinterPigeonCodec : StandardMessageCodec() {
  override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
    return when (type) {
      129.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          Alignments.ofRaw(it.toInt())
        }
      }
      130.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          PrinterSize.ofRaw(it.toInt())
        }
      }
      131.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          PrinterStatus.ofRaw(it.toInt())
        }
      }
      132.toByte() -> {
        return (readValue(buffer) as Long?)?.let {
          DeviceManufacture.ofRaw(it.toInt())
        }
      }
      else -> super.readValueOfType(type, buffer)
    }
  }
  override fun writeValue(stream: ByteArrayOutputStream, value: Any?)   {
    when (value) {
      is Alignments -> {
        stream.write(129)
        writeValue(stream, value.raw)
      }
      is PrinterSize -> {
        stream.write(130)
        writeValue(stream, value.raw)
      }
      is PrinterStatus -> {
        stream.write(131)
        writeValue(stream, value.raw)
      }
      is DeviceManufacture -> {
        stream.write(132)
        writeValue(stream, value.raw)
      }
      else -> super.writeValue(stream, value)
    }
  }
}

/** Generated interface from Pigeon that represents a handler of messages from Flutter. */
interface PosPrinter {
  /**
   * start printing
   * used in case of printers only working with commit mode
   * like telpo printers
   */
  fun start()
  /**
   * print text
   * [text] string to be printed
   * [isBold] to set text to bold
   * [isItalic] to set text to italic
   * [textSize] 27f is preferred
   */
  fun printText(text: String, textSize: Double, isBold: Boolean, isItalic: Boolean)
  /**
   * Print a row of a table
   * [texts] text table to be printed
   * [width] determine the width of text
   * [align] determine the alignment of text in the row
   * [fontSize] fontSize of the printed text
   */
  fun printTable(texts: List<String>, width: List<Long>, align: List<Long>, fontSize: Long)
  /** Print an image */
  fun printBitmap(bitmap: ByteArray)
  /**
   * Due to the distance between the paper hatch and the print head,
   * the paper needs to be fed out manually
   */
  fun feedPaper(lines: Long)
  /** Set printer alignment */
  fun setAlign(align: Alignments)
  /** Get printer head size 58mm or 80mm */
  fun getPrinterSize(): PrinterSize
  /** Send String to display on 7 segments display for sunmi d3 mini */
  fun sendTextToLcdDigital(text: String)
  /** Send Image to display on LCD */
  fun sendImageLcdDigital(bitmap: ByteArray)
  /** open cash drawer for supported device */
  fun openDrawer()
  /**cut the paper after printing */
  fun cutPaper()
  /**send esc/pos [commands] as bytes */
  fun escPosCommandExe(commands: ByteArray)
  /**return the current status of the printer */
  fun getPrinterStatus(): PrinterStatus
  /**release printer after quitting app */
  fun deInitPrinter()
  /**
   * return pos manufacture sunmi,senraise,telpo and
   * unknown in case of unsupported brand
   */
  fun getDeviceManufacture(): DeviceManufacture
  /**
   * release printer after printing complete
   * works with telpo and pos that support commit mode
   */
  fun release()

  companion object {
    /** The codec used by PosPrinter. */
    val codec: MessageCodec<Any?> by lazy {
      PosPrinterPigeonCodec()
    }
    /** Sets up an instance of `PosPrinter` to handle messages through the `binaryMessenger`. */
    @JvmOverloads
    fun setUp(binaryMessenger: BinaryMessenger, api: PosPrinter?, messageChannelSuffix: String = "") {
      val separatedMessageChannelSuffix = if (messageChannelSuffix.isNotEmpty()) ".$messageChannelSuffix" else ""
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.start$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.start()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.printText$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val textArg = args[0] as String
            val textSizeArg = args[1] as Double
            val isBoldArg = args[2] as Boolean
            val isItalicArg = args[3] as Boolean
            val wrapped: List<Any?> = try {
              api.printText(textArg, textSizeArg, isBoldArg, isItalicArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.printTable$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val textsArg = args[0] as List<String>
            val widthArg = args[1] as List<Long>
            val alignArg = args[2] as List<Long>
            val fontSizeArg = args[3] as Long
            val wrapped: List<Any?> = try {
              api.printTable(textsArg, widthArg, alignArg, fontSizeArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.printBitmap$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val bitmapArg = args[0] as ByteArray
            val wrapped: List<Any?> = try {
              api.printBitmap(bitmapArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.feedPaper$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val linesArg = args[0] as Long
            val wrapped: List<Any?> = try {
              api.feedPaper(linesArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.setAlign$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val alignArg = args[0] as Alignments
            val wrapped: List<Any?> = try {
              api.setAlign(alignArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.getPrinterSize$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              listOf(api.getPrinterSize())
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.sendTextToLcdDigital$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val textArg = args[0] as String
            val wrapped: List<Any?> = try {
              api.sendTextToLcdDigital(textArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.sendImageLcdDigital$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val bitmapArg = args[0] as ByteArray
            val wrapped: List<Any?> = try {
              api.sendImageLcdDigital(bitmapArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.openDrawer$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.openDrawer()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.cutPaper$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.cutPaper()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.escPosCommandExe$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { message, reply ->
            val args = message as List<Any?>
            val commandsArg = args[0] as ByteArray
            val wrapped: List<Any?> = try {
              api.escPosCommandExe(commandsArg)
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.getPrinterStatus$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              listOf(api.getPrinterStatus())
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.deInitPrinter$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.deInitPrinter()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.getDeviceManufacture$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              listOf(api.getDeviceManufacture())
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
      run {
        val channel = BasicMessageChannel<Any?>(binaryMessenger, "dev.flutter.pigeon.pos_printer.PosPrinter.release$separatedMessageChannelSuffix", codec)
        if (api != null) {
          channel.setMessageHandler { _, reply ->
            val wrapped: List<Any?> = try {
              api.release()
              listOf(null)
            } catch (exception: Throwable) {
              wrapError(exception)
            }
            reply.reply(wrapped)
          }
        } else {
          channel.setMessageHandler(null)
        }
      }
    }
  }
}
