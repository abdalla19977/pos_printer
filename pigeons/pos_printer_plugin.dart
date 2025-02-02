import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/src/pos_printer.g.dart',
  kotlinOptions: KotlinOptions(
    package: 'com.cashier.pos_printer',
  ),
  kotlinOut: 'android/src/main/kotlin/com/cashier/pos_printer/PosPrinter.kt',
  dartPackageName: 'pos_printer',
))
enum Alignments { left, center, right }

enum PrinterSize { size_58mm, size_80mm }

enum PrinterStatus { normal, outOfPaper, overHeating, coverOpen, generalError }

enum DeviceManufacture { sunmi, senraise, telpo, unknown }

@HostApi()
abstract class PosPrinter {
  /// start printing
  /// used in case of printers only working with commit mode
  /// like telpo printers
  void start();

  /// print text
  /// [text] string to be printed
  /// [isBold] to set text to bold
  /// [isItalic] to set text to italic
  /// [textSize] 27f is preferred
  void printText(String text, double textSize, bool isBold, bool isItalic);

  /// Print a row of a table
  /// [texts] text table to be printed
  /// [width] determine the width of text
  /// [align] determine the alignment of text in the row
  /// [fontSize] fontSize of the printed text
  void printTable(
      List<String> texts, List<int> width, List<int> align, int fontSize);

  /// Print an image
  void printBitmap(Uint8List bitmap);

  /// Due to the distance between the paper hatch and the print head,
  /// the paper needs to be fed out manually
  void feedPaper(int lines);

  /// Set printer alignment
  void setAlign(Alignments align);

  /// Get printer head size 58mm or 80mm
  PrinterSize getPrinterSize();

  /// Send String to display on 7 segments display for sunmi d3 mini
  void sendTextToLcdDigital(String text);

  /// Send Image to display on LCD
  void sendImageLcdDigital(Uint8List bitmap);

  /// open cash drawer for supported device
  void openDrawer();

  ///cut the paper after printing
  void cutPaper();

  ///send esc/pos [commands] as bytes
  void escPosCommandExe(Uint8List commands);

  ///return the current status of the printer
  PrinterStatus getPrinterStatus();

  ///release printer after quitting app
  void deInitPrinter();

  ///return pos manufacture sunmi,senraise,telpo and
  ///unknown in case of unsupported brand
  DeviceManufacture getDeviceManufacture();

  /// release printer after printing complete
  /// works with telpo and pos that support commit mode
  void release();
}
