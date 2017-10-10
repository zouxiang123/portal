package com.xtt.platform.util.secret;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密算法
 * 
 * @author wolf.yansl
 *
 */
public class SecretUtil {
    /**
     * md5加密
     */
    private static final String MD5 = "MD5";

    /**
     * MD系列加密
     * 
     * @param str
     *            加密字符
     * @param type
     *            加密类型
     * @return 32位加密算法
     */
    private static String md(String str, String type) {
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * MD5加密
     * 
     * @param str
     * @return
     */
    public static String secretMD5(String str) {
        return SecretUtil.md(str, SecretUtil.MD5);
    }

    /** base64加密 */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /** base64解密 */
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        /*System.out.println(getFromBase64("eWFuc2xh"));
        System.out.println(secretMD5(getFromBase64("eWFuc2xh") + "php_front_site_1"));
        System.out.println("e6838f474e72bd1c00a35ac26890afa9");
        */
        System.out.println(secretMD5("123456"));

    }

}
