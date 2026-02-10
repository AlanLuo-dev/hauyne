package com.luoyx.hauyne.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;

/**
 * MD5工具类
 *
 * @author Alan.Luo
 */
@Slf4j
public class Md5Utils {

    public Md5Utils() {
    }

    public static String getMD5(byte[] bytes) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] str = new char[32];

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] tmp = md.digest();
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }
        } catch (Exception e) {
            log.error("程序异常：{}",e.getMessage(),e);
        }
        return new String(str);
    }

    public static String getMD5(String value, String encode) {
        String result = "";

        try {
            result = getMD5(value.getBytes(encode));
        } catch (UnsupportedEncodingException e) {
            log.error("程序异常：{}",e.getMessage(),e);
        }

        return result;
    }
}
