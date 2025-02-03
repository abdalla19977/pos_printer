import 'dart:typed_data';

import 'package:pos_printer_helper/src/pos_printer.g.dart';

class PosPrinterPlugin {
  static final PosPrinter _api = PosPrinter();

  /// start printing
  /// used in case of printers only working with commit mode
  /// like telpo printers
  static start() async {
    await _api.start();
  }

  /// print text
  /// [text] string to be printed
  /// [isBold] to set text to bold
  /// [isItalic] to set text to italic
  /// [textSize] 27f is preferred
  static printText(
      String text, double textSize, bool isBold, bool isItalic) async {
    await _api.printText(text, textSize, isBold, isItalic);
  }

  /// Print a row of a table
  /// [texts] text table to be printed
  /// [width] determine the width of text
  /// [align] determine the alignment of text in the row
  /// [fontSize] fontSize of the printed text
  static printTable(List<String> texts, List<int> width, List<int> align,
      int fontSize) async {
    await _api.printTable(texts, width, align, fontSize);
  }

  /// Print an image
  static printBitmap(Uint8List bitmap) async {
    await _api.printBitmap(bitmap);
  }

  /// Due to the distance between the paper hatch and the print head,
  /// the paper needs to be fed out manually
  static feedPaper(int lines) async {
    await _api.feedPaper(lines);
  }

  /// Set printer alignment
  static setAlign(Alignments align) async {
    await _api.setAlign(align);
  }

  /// Get printer head size 58mm or 80mm
  static Future<PrinterSize> getPrinterSize() async {
    return await _api.getPrinterSize();
  }

  /// Send String to display on 7 segments display for sunmi d3 mini
  static sendTextToLcdDigital(String text) async {
    await _api.sendTextToLcdDigital(text);
  }

  /// Send Image to display on LCD
  static sendImageLcdDigital(Uint8List bitmap) async {
    await _api.sendImageLcdDigital(bitmap);
  }

  /// open cash drawer for supported device
  static openDrawer() async {
    await _api.openDrawer();
  }

  ///cut the paper after printing
  static cutPaper() async {
    await _api.cutPaper();
  }

  ///send esc/pos [commands] as bytes
  static escPosCommandExe(Uint8List commands) async {
    await _api.escPosCommandExe(commands);
  }

  ///return the current status of the printer
  static Future<PrinterStatus> getPrinterStatus() async {
    return await _api.getPrinterStatus();
  }

  ///release printer after quitting app
  static deInitPrinter() async {
    await _api.deInitPrinter();
  }

  ///return pos manufacture sunmi,senraise,telpo and
  ///unknown in case of unsupported brand
  static Future<DeviceManufacture> getDeviceManufacture() async {
    return await _api.getDeviceManufacture();
  }

  /// release printer after printing complete
  /// works with telpo and pos that support commit mode
  static release() async {
    await _api.release();
  }
}
