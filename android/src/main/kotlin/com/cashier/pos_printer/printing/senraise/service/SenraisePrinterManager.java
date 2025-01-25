package com.cashier.pos_printer.printing.senraise.service;

import android.content.Context;
import android.content.Intent;

public class SenraisePrinterManager {
    private SenraisePrinterManager() {
    }

    public static SenraisePrinterManager getInstance() {
        return SingletonContainer.instance;
    }

    public boolean bindService(Context mContext, SenraisePrinterCallback callback) throws SenraisePrinterException {
        if (mContext != null && callback != null) {
            Intent intent = new Intent();
            intent.setClassName(
                    "recieptservice.com.recieptservice",
                    "recieptservice.com.recieptservice.service.PrinterService"
            );
            return mContext.getApplicationContext().bindService(intent, callback, 1);
        } else {
            throw new SenraisePrinterException("parameter must be not null!");
        }
    }

    public void unBindService(Context mContext, SenraisePrinterCallback callback) throws SenraisePrinterException {
        if (mContext != null && callback != null) {
            mContext.getApplicationContext().unbindService(callback);
        } else {
            throw new SenraisePrinterException("parameter must be not null!");
        }
    }


    private static class SingletonContainer {
        private static SenraisePrinterManager instance = new SenraisePrinterManager();

        private SingletonContainer() {
        }
    }
}
