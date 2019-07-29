package com.jianghu.mscore.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.Validate;

public class Digests {
    private static SecureRandom random = new SecureRandom();
    private static final String HMACSHA1 = "HmacSHA1";
    private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

    public Digests() {
    }

    public static byte[] hmacSha1(byte[] input, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
            return mac.doFinal(input);
        } catch (GeneralSecurityException var4) {
            throw new RuntimeException(var4);
        }
    }

    public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
        byte[] actual = hmacSha1(input, key);
        return Arrays.equals(expected, actual);
    }

    public static byte[] generateHmacSha1Key() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
            keyGenerator.init(160);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", (long)numBytes);
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    public static byte[] hash(byte[] input, DigestsType type) {
        return digest(input, type.getName(), (byte[])null, 1);
    }

    public static byte[] hash(byte[] input, byte[] salt, DigestsType type) {
        return digest(input, type.getName(), salt, 1);
    }

    public static byte[] hash(byte[] input, byte[] salt, int iterations, DigestsType type) {
        return digest(input, type.getName(), salt, iterations);
    }

    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for(int i = 1; i < iterations; ++i) {
                digest.reset();
                result = digest.digest(result);
            }

            return result;
        } catch (GeneralSecurityException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static byte[] md5(InputStream input, DigestsType type) throws IOException {
        return digest(input, type.getName());
    }

    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8192;
            byte[] buffer = new byte[bufferLength];

            for(int read = input.read(buffer, 0, bufferLength); read > -1; read = input.read(buffer, 0, bufferLength)) {
                messageDigest.update(buffer, 0, read);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException var6) {
            throw new RuntimeException(var6);
        }
    }
}

