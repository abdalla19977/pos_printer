package recieptservice.com.recieptservice;


interface PrinterInterface {
    void printEpson(in byte []data);
    String getServiceVersion();
    void printText(String text);
    void printBitmap(in Bitmap pic);
    void printBarCode(String data, int symbology, int height, int width);
    void printQRCode(String data, int modulesize, int errorlevel);
    void setAlignment(int alignment);
    void setTextSize(float textSize);
    void nextLine(int line);
    void printTableText(in String[] text,in int []weight,in int []alignment);
    void setTextBold(boolean bold);
}