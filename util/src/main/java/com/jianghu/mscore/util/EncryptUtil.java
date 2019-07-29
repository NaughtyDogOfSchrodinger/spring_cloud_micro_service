package com.jianghu.mscore.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密解密工具类
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.06.20
 */
public class EncryptUtil {
    private static final Logger log = LoggerFactory.getLogger(EncryptUtil.class);
    /**
     * The constant KEY.
     */
    public static final String KEY = "LmMGStGtOpF4xNyvYt54EQ==";

    /**
     * The type Encrypt util.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.06.20
     */
    public EncryptUtil() {
    }

    /**
     * 加密字符串
     *
     * @param xmlStr the xml str
     * @return the string
     * @since 2019.06.20
     */
    public static String encrypt(String xmlStr) {
        Object var1 = null;

        byte[] encrypt;
        try {
            encrypt = xmlStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException var12) {
            log.debug("xmlStr解析失败!" + var12.getMessage());
            return null;
        }

        Object var2 = null;

        byte[] md5Hasn;
        try {
            md5Hasn = md5hash(encrypt, 0, encrypt.length);
        } catch (Exception var11) {
            log.debug("MD5Hash加密失败!");
            return null;
        }

        byte[] totalByte = addMD5(md5Hasn, encrypt);
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV("LmMGStGtOpF4xNyvYt54EQ==", key, iv);
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        byte[] temp = null;

        try {
            temp = desCbcEncrypt(totalByte, deskey, ivParam);
        } catch (Exception var10) {
            log.debug("DES_CBC_Encrypt加密失败!");
        }
        return Base64.encodeBase64String(temp);
    }

    /**
     * 解密字符串
     *
     * @param xmlStr the xml str
     * @return the string
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static String decrypt(String xmlStr) throws Exception {
        Object var2 = null;

        byte[] encBuf = Base64.decodeBase64(xmlStr);

        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV("LmMGStGtOpF4xNyvYt54EQ==", key, iv);
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        Object var7 = null;

        byte[] temp;
        try {
            temp = desCbcDecrypt(encBuf, deskey, ivParam);
        } catch (Exception var10) {
            log.debug("DES_CBC_Decrypt加密失败!" + var10.getMessage());
            return null;
        }

        Object var8 = null;

        try {
            byte[] md5Hash = md5hash(temp, 16, temp.length - 16);

            for(int i = 0; i < md5Hash.length; ++i) {
                if (md5Hash[i] != temp[i]) {
                    throw new Exception("MD5校验错误。");
                }
            }
        } catch (Exception var12) {
            log.debug("MD5Hash加密失败!" + var12.getMessage());
            return null;
        }

        return new String(temp, 16, temp.length - 16, "utf-8");
    }

    /**
     * Triple des cbc encrypt byte [ ].
     *
     * @param sourceBuf the source buf
     * @param deskey    the deskey
     * @param ivParam   the iv param
     * @return the byte [ ]
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static byte[] tripleDesCbcEncrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        encrypt.init(1, deskey, ivParam);
        byte[] cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        return cipherByte;
    }

    /**
     * Triple des cbc decrypt byte [ ].
     *
     * @param sourceBuf the source buf
     * @param deskey    the deskey
     * @param ivParam   the iv param
     * @return the byte [ ]
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static byte[] tripleDesCbcDecrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");
        decrypt.init(2, deskey, ivParam);
        byte[] cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        return cipherByte;
    }

    /**
     * Des cbc encrypt byte [ ].
     *
     * @param sourceBuf the source buf
     * @param deskey    the deskey
     * @param ivParam   the iv param
     * @return the byte [ ]
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static byte[] desCbcEncrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encrypt.init(1, deskey, ivParam);
        byte[] cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        return cipherByte;
    }

    /**
     * Des cbc decrypt byte [ ].
     *
     * @param sourceBuf the source buf
     * @param deskey    the deskey
     * @param ivParam   the iv param
     * @return the byte [ ]
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static byte[] desCbcDecrypt(byte[] sourceBuf, SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {
        Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decrypt.init(2, deskey, ivParam);
        byte[] cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);
        return cipherByte;
    }

    /**
     * Md 5 hash byte [ ].
     *
     * @param buf    the buf
     * @param offset the offset
     * @param length the length
     * @return the byte [ ]
     * @throws Exception the exception
     * @since 2019.06.20
     */
    public static byte[] md5hash(byte[] buf, int offset, int length) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(buf, offset, length);
        return md.digest();
    }

    /**
     * Byte 2 hex string.
     *
     * @param inStr the in str
     * @return the string
     * @since 2019.06.20
     */
    public static String byte2hex(byte[] inStr) {
        StringBuffer out = new StringBuffer(inStr.length * 2);

        for(int n = 0; n < inStr.length; ++n) {
            String stmp = Integer.toHexString(inStr[n] & 255);
            if (stmp.length() == 1) {
                out.append("0" + stmp);
            } else {
                out.append(stmp);
            }
        }

        return out.toString();
    }

    /**
     * Add md 5 byte [ ].
     *
     * @param md5Byte  the md 5 byte
     * @param bodyByte the body byte
     * @return the byte [ ]
     * @since 2019.06.20
     */
    public static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {
        int length = bodyByte.length + md5Byte.length;
        byte[] resutlByte = new byte[length];

        for(int i = 0; i < length; ++i) {
            if (i < md5Byte.length) {
                resutlByte[i] = md5Byte[i];
            } else {
                resutlByte[i] = bodyByte[i - md5Byte.length];
            }
        }

        return resutlByte;
    }

    /**
     * Gets key iv.
     *
     * @param encryptKey the encrypt key
     * @param key        the key
     * @param iv         the iv
     */
    public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {
        byte[] buf = Base64.decodeBase64(encryptKey);
        int i;
        for(i = 0; i < key.length; ++i) {
            key[i] = buf[i];
        }

        for(i = 0; i < iv.length; ++i) {
            iv[i] = buf[i + 8];
        }

    }
}

