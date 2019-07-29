package com.jianghu.mscore.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);
    private static MessageDigest messageDigest = null;

    public MD5Utils() {
    }

    public static String getMD5(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        messageDigest.update(bytes, 0, bytes.length);
        BigInteger i = new BigInteger(1, messageDigest.digest());
        return String.format("%1$032X", i);
    }

    public static String getMD5(File file) throws IOException {
        FileInputStream is = null;

        String var3;
        try {
            is = new FileInputStream(file);
            String var2 = getMD5((InputStream)is);
            return var2;
        } catch (FileNotFoundException var7) {
            logger.error(var7.getMessage(), var7);
            var3 = "";
        } finally {
            if (is != null) {
                is.close();
            }

        }

        return var3;
    }

    public static String getMD5(InputStream is) {
        byte[] buffer = new byte[4096];

        int numRead;
        try {
            while((numRead = is.read(buffer)) > 0) {
                messageDigest.update(buffer, 0, numRead);
            }
        } catch (IOException var4) {
            logger.error(var4.getMessage(), var4);
            return "";
        }

        BigInteger i = new BigInteger(1, messageDigest.digest());
        return String.format("%1$032X", i);
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            logger.error("初始化失败: " + var1.getMessage(), var1);
        }

    }
}

