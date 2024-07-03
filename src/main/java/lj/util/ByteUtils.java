package lj.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 字节工具类
 */
public class ByteUtils {

    /**
     * 计算校验和
     * @param bytes
     * @return
     */
    public static byte makeCheckSum(byte[] bytes)
    {
        int total=0;
        for(byte b:bytes)
            total=total+b;
        byte ret=(byte)(total%256);
        return ret;
    }

    // 求校验和
    public static String makeChecksum(String data) {
        if (data == null || data.equals("")) {
            return "";
        }
        int total = 0;
        int len = data.length();
        int num = 0;
        while (num < len) {
            String s = data.substring(num, num + 2);
            total += Integer.parseInt(s, 16);
            num = num + 2;
        }
        /**
         * 用256求余最大是255，即16进制的FF
         */
        int mod = total % 256;
        String hex = Integer.toHexString(mod);
        len = hex.length();
        // 如果不够校验位的长度，补0,这里用的是两位校验
        if (len < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    // 16进制字符串转bytes数组
    public static byte[] hexStringToBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    /**
     * 字节->16进制字符串
     *
     * @param b
     */
    public static String byteToHexString(byte b) {
        String temp = Integer.toHexString(b & 0xFF);
        if (temp.length() == 1) {
            temp = "0" + temp;
        }
        return temp;
    }

    /**
     * 自己数组转成16进制字符串
     *
     * @param buffer
     * @return
     */
    public static String bytesToHexString(byte[] buffer) {
        String str = "";
        for (int i = 0; i < buffer.length; i++) {
            str = str + byteToHexString(buffer[i]);
        }
        return str;

    }


    /**
     * int
     * @param data
     * @return
     */
    public static byte[] intToBytes(int data)  
    {  
        byte[] bytes = new byte[4];  
        bytes[0] = (byte) (data & 0xff);  
        bytes[1] = (byte) ((data & 0xff00) >> 8);  
        bytes[2] = (byte) ((data & 0xff0000) >> 16);  
        bytes[3] = (byte) ((data & 0xff000000) >> 24);  
        return bytes;  
    }  


    /**
     * 字节数组-》整形
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes,ByteOrder byteOrder) {
        return ByteBuffer.wrap(bytes).order(byteOrder).getInt();
    }

    /**
     * 字节数组转整形
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getInt();
    }


    /**
     * 字节转无符号整数
     *
     * @param b
     * @return
     */
     public static int byteToUnsignedInt(byte b) {
        int ret = (int) (b & 0xff);
        return ret;
    }


/*    public static long bytesToLong(byte bytes[])
    {
        long toal=0;
        for(int i=bytes.length-1,j=0;i>=0;--i,++j)
        {
            int b=byteToUnsignedInt(bytes[i]);
            if(b<=0)
                break;
            toal=toal+b*(long)Math.pow(256, j);
        }
        return toal;
    }*/
}
