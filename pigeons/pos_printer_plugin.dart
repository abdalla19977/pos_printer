import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/src/pos_printer.g.dart',
  kotlinOptions: KotlinOptions(
    package: 'com.cashier.pos_printer',
  ),
  kotlinOut: 'android/src/main/kotlin/com/cashier/pos_printer/PosPrinter.kt',
  dartPackageName: 'pos_printer',
))
enum Alignments { left, right, center }

enum PrinterSize { size_58mm, size_80mm }

enum PrinterStatus { outOfPaper, overHeating, coverOpen, generalError }

class SecondaryScreenSize {
  final int width;
  final int hight;
  SecondaryScreenSize({
    required this.width,
    required this.hight,
  });
}

@HostApi()
abstract class PosPrinter {
  /// initialize the printer
  /// [grayLevel] works only with telpo value between 1 and 5
  ///
  /// Returns `0` in case of success and `1` in case of failure
  int init(int grayLevel);

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
  /// []
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

  /// print a barcode
  /// [symbology] between 0 and 8 works with sunmi and senraise
  /// 0 → UPC-A
  /// 1 → UPC-E
  /// 2 → JAN13 (EAN13)
  /// 3 → JAN8 (EAN8)
  /// 4 → CODE39
  /// 5 → ITF
  /// 6 → CODABAR
  /// 7 → CODE 93
  /// 8 → CODE128
  /// [height] between 1 – 255 default 162
  /// [width] between 2 – 6 default 2
  void printBarcode(String data, int symbology, int width, int height);

  /// print a printQrCode
  void printQrCode(String data);

  ///return the size of the secondary display
  ///width and height in pixels
  SecondaryScreenSize getSecondaryScreenSize();

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

  bool isTelpo();
  
  bool isSunmi();

  bool isSenraise();

  bool isPos();

  /// release printer after printing complete
  /// works with telpo and pos that support commit mode
  void release();
}
