package com.jd.utils;
import java.security.MessageDigest;

public class MD5Util {

	public static String token = "";

    public static String md5AndSha1(String text) throws Exception {
        return SHA1(token + Md5(text) + token);
    }

    public static String Md5(String plainText) throws Exception {
        StringBuffer buf = new StringBuffer("");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte b[] = md.digest();
        int i;
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) i += 256;
            if (i < 16) buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    private static String SHA1(String inStr) throws Exception {
        MessageDigest md = null;
        String outStr = null;

        md = MessageDigest.getInstance("SHA-1");     // 选择SHA-1，也可以选择MD5
        byte[] digest = md.digest(inStr.getBytes());       // 返回的是byet[]，要转化为String存储比较方便
        outStr = bytetoString(digest);

        return outStr;
    }

    private static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";

        for (int i = 1; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            } else {
                str += tempStr;
            }
        }
        return str.toLowerCase();
    }

}
