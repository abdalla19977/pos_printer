import 'package:flutter/material.dart';
import 'package:pos_printer_helper/pos_printer_helper.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Future<PrinterStatus>? _printerStatus;

  @override
  void initState() {
    super.initState();
    _updatePrinterStatus();
  }

  void _updatePrinterStatus() {
    setState(() {
      _printerStatus = PosPrinterPlugin.getStatus();
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            appBar: AppBar(
              title: const Text('Printer example'),
            ),
            body: Center(
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    FutureBuilder<PrinterStatus>(
                      future: _printerStatus,
                      builder: (context, snapshot) {
                        if (snapshot.connectionState ==
                            ConnectionState.waiting) {
                          return const CircularProgressIndicator();
                        }
                        return Text('${snapshot.data}');
                      },
                    ),
                    FilledButton(
                        onPressed: () {
                          PosPrinterPlugin.printText(
                              "Hello World\nHello World\nHello World\n\n\n\n\n\n",
                              30,
                              true,
                              true);
                          PosPrinterPlugin.start();
                          _updatePrinterStatus();
                        },
                        child: Text("Click me"))
                  ]),
            )));
  }
}
