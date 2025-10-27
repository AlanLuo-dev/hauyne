package com.luoyx.hauyne.framework.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SignUtils {

    public static String hmacSHA256(String secret, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(hash); // Apache Commons Codec
        } catch (Exception e) {
            throw new RuntimeException("签名失败", e);
        }
    }
}

