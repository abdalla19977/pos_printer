import 'package:flutter/material.dart';
import 'package:pos_printer/printer.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            appBar: AppBar(
              title: const Text('Printer example'),
            ),
            body: Column(children: [
              FilledButton(
                  onPressed: () {
                    PosPrinterPlugin.printText(
                        "Hello World\nHello World\nHello World\n\n\n\n\n\n",
                        30,
                        true,
                        true);

                    PosPrinterPlugin.start();
                  },
                  child: Text("Click me"))
            ])));
  }
}
