package com.jianghu.mscore.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptos {
    private static final String AES = "AES";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final int DEFAULT_AES_KEYSIZE = 128;
    private static final int DEFAULT_IVSIZE = 16;
    private static SecureRandom random = new SecureRandom();

    public Cryptos() {
    }

    public static byte[] aesEncrypt(byte[] input, byte[] key) {
        return aes(input, key, 1);
    }

    public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, 1);
    }

    public static String aesDecrypt(byte[] input, byte[] key) throws UnsupportedEncodingException {
        byte[] decryptResult = aes(input, key, 2);
        return new String(decryptResult, "UTF-8");
    }

    public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) throws UnsupportedEncodingException {
        byte[] decryptResult = aes(input, key, iv, 2);
        return new String(decryptResult, "UTF-8");
    }

    private static byte[] aes(byte[] input, byte[] key, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException var5) {
            throw new RuntimeException(var5);
        }
    }

    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static byte[] generateAesKey() {
        return generateAesKey(128);
    }

    public static byte[] generateAesKey(int keysize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keysize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] generateIV() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return bytes;
    }
}

