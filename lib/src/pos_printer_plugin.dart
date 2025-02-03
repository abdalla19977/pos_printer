import 'package:pos_printer/src/pos_printer.g.dart';

class PosPrinterPlugin {
  static final PosPrinter _api = PosPrinter();

  static Future<void> start() async {
    await _api.start();
    return;
  }

  static Future<void> printText(
      String text, double textSize, bool isBold, bool isItalic) async {
    await _api.printText(text, textSize, isBold, isItalic);
    return;
  }

  static Future<PrinterStatus> getStatus() async {
    return await _api.getPrinterStatus();
  }

  // static setAlign(Alignments alignment) async {
  //   _api.setAlign(alignment);
  // }

  // static printText(String data) async {
  //   await _api.printText(data);
  // }

  // static printBitmap(Uint8List data) async {
  //   await _api.printBitmap(data);
  // }

  // static printQr(String data, {int size = 300}) async {
  //   await _api.printQr(data, size: size);
  // }

  // static printWithFeed(int feedPixels) async {
  //   await _api.printWithFeed(feedPixels);
  // }

  // static setLedStatus(LedStatus status) async {
  //   _api.setLedStatus(status);
  // }

  // static startBuzzer(int times, int onTime, int offTime, int mode) async {
  //   _api.startBuzzer(times, onTime, offTime, mode);
  // }

  // static setBuzzerVolume(int volume) async {
  //   _api.setBuzzerVolume(volume);
  // }
}
