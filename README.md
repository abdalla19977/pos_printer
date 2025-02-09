# POS Printer Package

[![pub package](https://img.shields.io/pub/v/pos_printer_helper.svg)](https://pub.dev/packages/pos_printer_helper)

A Flutter package that provides a unified interface for thermal printer operations across multiple POS device manufacturers including Telpo, Senraise, and Sunmi.

## Features

- Compatibility with multiple POS device manufacturers
- Text printing with customizable formatting (bold, italic, size)
- Table printing with customizable column widths and alignments
- Image printing support
- LCD display support for compatible devices (e.g. Sunmi D3 Mini)
- Cash drawer control
- Paper cutting
- ESC/POS command execution
- Printer status monitoring
- Automatic manufacturer detection

## Supported Devices

- Telpo
- Senraise
- Sunmi

## Tested on

- Telpo(M8)
- Senraise(H10s)
- Sunmi(V2,V3 Mix)

## Installation

```dart
flutter pub add pos_printer_helper
```

``` gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        
        //add this line
        maven { url "https://jitpack.io" }
    }
}
```

## Getting Started

First, import the package:

```dart
import 'package:pos_printer/pos_printer.dart';
```

## Usage

### Basic Printing

```dart
// Print text
PosPrinterPlugin.printText("Hello World!", 27.0, true, false); // bold text
PosPrinterPlugin.printText("Normal text", 27.0, false, false); // normal text

// Feed paper
PosPrinterPlugin.feedPaper(3);

// Start printing session (required for Telpo printers)
PosPrinterPlugin.start();

// Release printer
PosPrinterPlugin.release();
```

### Table Printing

```dart
// Print a table row
PosPrinterPlugin.printTable(
  ["Item", "Qty", "Price"],  // texts
  [2, 1, 1],                 // column widths
  [0, 1, 2],                 // alignments (0: left, 1: center, 2: right)
  27                         // font size
);
```

### Image Printing

```dart
// Print a bitmap image
final Uint8List imageBytes = await loadImage();
PosPrinterPlugin.printBitmap(imageBytes);
```

### LCD Display (Sunmi D3 Mini 58mm)

```dart
// Display text on 7-segment display
PosPrinterPlugin.sendTextToLcdDigital("12.50");

### LCD Display (Sunmi D3 Mini 80/ Telpo M8)

// Display image on LCD
final Uint8List lcdImage = await loadLcdImage();
PosPrinterPlugin.sendImageLcdDigital(lcdImage);
```

### Printer Control

```dart
// Set alignment
PosPrinterPlugin.setAlign(Alignments.center);

// Open cash drawer
PosPrinterPlugin.openDrawer();

// Cut paper
PosPrinterPlugin.cutPaper();

// Get printer status
Future<PrinterStatus> status = PosPrinterPlugin.getPrinterStatus();

// Get printer size
Future<PrinterSize> size = PosPrinterPlugin.getPrinterSize();

// Get device manufacturer
Future<DeviceManufacture> manufacturer = PosPrinterPlugin.getDeviceManufacture();
```

## Cleanup

Make sure to properly release resources when done:

```dart
// Release printer after printing
PosPrinterPlugin.release();

// De-initialize printer when app is closing
PosPrinterPlugin.deInitPrinter();
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
