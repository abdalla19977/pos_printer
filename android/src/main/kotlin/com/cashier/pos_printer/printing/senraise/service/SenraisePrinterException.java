package com.cashier.pos_printer.printing.senraise.service;

import android.os.RemoteException;

public class SenraisePrinterException extends RemoteException {
    public SenraisePrinterException(String message) {
        super(message);
    }
}