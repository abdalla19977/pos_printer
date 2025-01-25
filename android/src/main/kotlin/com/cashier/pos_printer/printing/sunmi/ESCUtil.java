package com.cashier.pos_printer.printing.sunmi;

//常用指令封装
public class ESCUtil {

    public static final byte ESC = 0x1B;// Escape
    public static final byte FS = 0x1C;// Text delimiter
    public static final byte GS = 0x1D;// Group separator
    public static final byte DLE = 0x10;// data link escape
    public static final byte EOT = 0x04;// End of transmission
    public static final byte ENQ = 0x05;// Enquiry character
    public static final byte SP = 0x20;// Spaces
    public static final byte HT = 0x09;// Horizontal list
    public static final byte LF = 0x0A;//Print and wrap (horizontal orientation)
    public static final byte CR = 0x0D;// Home key
    public static final byte FF = 0x0C;// Carriage control (print and return to the standard mode (in page mode))
    public static final byte CAN = 0x18;// Canceled (cancel print data in page mode)

    //初始化打印机
    public static byte[] init_printer() {
        byte[] result = new byte[2];
        result[0] = ESC;
        result[1] = 0x40;
        return result;
    }

    //打印浓度指令
    public static byte[] setPrinterDarkness(int value) {
        byte[] result = new byte[9];
        result[0] = GS;
        result[1] = 40;
        result[2] = 69;
        result[3] = 4;
        result[4] = 0;
        result[5] = 5;
        result[6] = 5;
        result[7] = (byte) (value >> 8);
        result[8] = (byte) value;
        return result;
    }


    /**
     * 横向两个二维码 sunmi自定义指令
     *
     * @param code1:      二维码数据
     * @param code2:      二维码数据
     * @param modulesize: 二维码块大小(单位:点, 取值 1 至 16 )
     * @param errorlevel: 二维码纠错等级(0 至 3)
     *                    0 -- 纠错级别L ( 7%)
     *                    1 -- 纠错级别M (15%)
     *                    2 -- 纠错级别Q (25%)
     *                    3 -- 纠错级别H (30%)
     */

    /**
     * 跳指定行数
     */
    public static byte[] nextLine(int lineNum) {
        byte[] result = new byte[lineNum];
        for (int i = 0; i < lineNum; i++) {
            result[i] = LF;
        }

        return result;
    }

    // ------------------------style set-----------------------------
    //设置默认行间距
    public static byte[] setDefaultLineSpace() {
        return new byte[]{0x1B, 0x32};
    }

    //设置行间距
    public static byte[] setLineSpace(int height) {
        return new byte[]{0x1B, 0x33, (byte) height};
    }

    // ------------------------underline-----------------------------
    //设置下划线1点
    public static byte[] underlineWithOneDotWidthOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 1;
        return result;
    }

    //设置下划线2点
    public static byte[] underlineWithTwoDotWidthOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 2;
        return result;
    }

    //取消下划线
    public static byte[] underlineOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 45;
        result[2] = 0;
        return result;
    }

    // ------------------------bold-----------------------------

    /**
     * 字体加粗
     */
    public static byte[] boldOn() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0xF;
        return result;
    }

    /**
     * 取消字体加粗
     */
    public static byte[] boldOff() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 69;
        result[2] = 0;
        return result;
    }

    // ------------------------character-----------------------------
    /*
     *单字节模式开启
     */
    public static byte[] singleByte() {
        byte[] result = new byte[2];
        result[0] = FS;
        result[1] = 0x2E;
        return result;
    }

    /*
     *单字节模式关闭
     */
    public static byte[] singleByteOff() {
        byte[] result = new byte[2];
        result[0] = FS;
        result[1] = 0x26;
        return result;
    }

    /**
     * 设置单字节字符集
     */
    public static byte[] setCodeSystemSingle(byte charset) {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 0x74;
        result[2] = charset;
        return result;
    }

    /**
     * 设置多字节字符集
     */
    public static byte[] setCodeSystem(byte charset) {
        byte[] result = new byte[3];
        result[0] = FS;
        result[1] = 0x43;
        result[2] = charset;
        return result;
    }

    // ------------------------Align-----------------------------

    /**
     * 居左
     */
    public static byte[] alignLeft() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 0;
        return result;
    }

    /**
     * 居中对齐
     */
    public static byte[] alignCenter() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 1;
        return result;
    }

    /**
     * 居右
     */
    public static byte[] alignRight() {
        byte[] result = new byte[3];
        result[0] = ESC;
        result[1] = 97;
        result[2] = 2;
        return result;
    }

    //切刀
    public static byte[] cutter() {
        return new byte[]{0x1d, 0x56, 0x01};
    }

    //走纸到黑标
    public static byte[] gogogo() {
        return new byte[]{0x1C, 0x28, 0x4C, 0x02, 0x00, 0x42, 0x31};
    }

}