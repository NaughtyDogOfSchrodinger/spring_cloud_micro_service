package com.jianghu.mscore.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

public class AuthUtil {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public AuthUtil() {
    }

    public static String getAuthorization(String uri, String method, String date, String clientId, String secret) {
        String stringToSign = method + " " + uri + "\n" + date;
        String signature = getSignature(stringToSign, secret);
        return "MWS " + clientId + ":" + signature;
    }

    public static String getSignature(String data, String secret) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(rawHmac);
        } catch (Exception var7) {
            throw new RuntimeException("Failed to generate HMAC : " + var7.getMessage());
        }
    }
}

