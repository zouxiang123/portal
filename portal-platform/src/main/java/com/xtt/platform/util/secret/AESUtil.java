package com.xtt.platform.util.secret;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AESUtil.java
 * 
 * @author Tik
 * @date: 2016年08月02日
 */
public class AESUtil {

    private final static String CIPHER_KEY = "xtt-HDIS";

    public static void main(String[] args) {
        String content = "上次介绍了《JAVA实现AES加密》，中间提到近些年DES使用越来越少，原因就在于其使用56位密钥，比较容易被破解，近些年来逐渐被AES替代，AES已经变成目前对称加密中最流行算法之一；AES可以使用128、192、和256位密钥，并且用128位分组加密和解密数据。本文就简单介绍如何通过JAVA实现AES加密。";
        String password = "xtt-HD";
        System.out.println("密　钥：" + password);
        System.out.println("加密前：" + content);
        // 加密
        String encryptResult = encrypt(content, password);
        System.out.println("加密后：" + encryptResult);
        // 解密
        String decryptResult = decrypt(encryptResult, password);
        System.out.println("解密后：" + decryptResult);
    }

    /**
     * 加密
     * 
     * @param content
     *            待加密内容
     * @param key
     *            加密的密钥
     * @return
     */
    public static String encrypt(String content, String key) {
        try {

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // kgen.init(128, new SecureRandom(key.getBytes()));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] byteRresult = cipher.doFinal(byteContent);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteRresult.length; i++) {
                String hex = Integer.toHexString(byteRresult[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex.toUpperCase());
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param key
     *            解密的密钥
     * @return
     */
    public static String decrypt(String content, String key) {
        if (content.length() < 1)
            return null;
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        byte[] byteRresult = new byte[content.length() / 2];
        for (int i = 0; i < content.length() / 2; i++) {
            int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
            byteRresult[i] = (byte) (high * 16 + low);
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            // kgen.init(128, new SecureRandom(key.getBytes()));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] result = cipher.doFinal(byteRresult);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String content) {
        return encrypt(content, CIPHER_KEY);
    }

    public static String decrypt(String content) {
        return decrypt(content, CIPHER_KEY);
    }

}