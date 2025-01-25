package com.cashier.pos_printer.printing.senraise.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import recieptservice.com.recieptservice.PrinterInterface;


public abstract class SenraisePrinterCallback implements ServiceConnection {
    public SenraisePrinterCallback() {
    }

    protected abstract void onConnected(PrinterInterface var1);

    protected abstract void onDisconnected();

    public void onServiceConnected(ComponentName name, IBinder service) {
        if (service != null) {
            PrinterInterface proxy = PrinterInterface.Stub.asInterface(service);
            this.onConnected(proxy);
        }

    }

    public void onServiceDisconnected(ComponentName name) {
        this.onDisconnected();
    }
}