# POS Printer Package

A Flutter package that provides a unified interface for thermal printer operations across multiple POS device manufacturers including Telpo, Senraise, and Sunmi.

## Features

- Compatibility with multiple POS device manufacturers
- Text printing with customizable formatting (bold, italic, size)
- Table printing with customizable column widths and alignments
- Image printing support
- LCD display support for compatible devices (e.g., Sunmi D3 Mini)(Coming Soon)
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

Add this to your package's `pubspec.yaml` file:

```yaml
dependencies:
  pos_printer: ^1.0.0
```

## Getting Started

First, import the package:

```dart
import 'package:pos_printer/pos_printer.dart';
```

Initialize the printer:

```dart
final printer = PosPrinter();
```

## Usage

### Basic Printing

```dart
// Print text
printer.printText("Hello World!", 27.0, true, false); // bold text
printer.printText("Normal text", 27.0, false, false); // normal text

// Feed paper
printer.feedPaper(3);

// Start printing session (required for Telpo printers)
printer.start();

// Release printer
printer.release();
```

### Table Printing

```dart
// Print a table row
printer.printTable(
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
printer.printBitmap(imageBytes);
```

### LCD Display (Sunmi D3 Mini 58mm)

```dart
// Display text on 7-segment display
printer.sendTextToLcdDigital("12.50");

### LCD Display (Sunmi D3 Mini 80/ Telpo M8)

// Display image on LCD
final Uint8List lcdImage = await loadLcdImage();
printer.sendImageLcdDigital(lcdImage);
```

### Printer Control

```dart
// Set alignment
printer.setAlign(Alignments.center);

// Open cash drawer
printer.openDrawer();

// Cut paper
printer.cutPaper();

// Get printer status
PrinterStatus status = printer.getPrinterStatus();

// Get printer size
PrinterSize size = printer.getPrinterSize();

// Get device manufacturer
DeviceManufacture manufacturer = printer.getDeviceManufacture();
```

## Cleanup

Make sure to properly release resources when done:

```dart
// Release printer after printing
printer.release();

// De-initialize printer when app is closing
printer.deInitPrinter();
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
