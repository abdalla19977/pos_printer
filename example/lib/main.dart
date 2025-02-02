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
              title: const Text('Plugin example app'),
            ),
            body: Column(children: [
              Text("${PosPrinterPlugin.getStatus}"),
              FilledButton(
                  onPressed: () {
                    PosPrinterPlugin.printText(
                        "abdallaa\nabdallaa\nabdallaa\n", 30, true, true);
                    PosPrinterPlugin.start();
                  },
                  child: Text("Click me"))
            ])));
  }
}
