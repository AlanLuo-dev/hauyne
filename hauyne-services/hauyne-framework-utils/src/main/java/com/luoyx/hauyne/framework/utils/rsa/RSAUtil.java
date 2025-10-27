package com.luoyx.hauyne.framework.utils.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗英雄
 * <p>
 * RSA+Base64加密解密工具类 RSA 一般用在数据传输过程中的加密和解密 先用RSA加密后是字节数组 再用BASE64加密 成字符串进行传输 测试
 * RSA1024产生的公钥字节长度在160-170之间 私钥长度在630-640之间 经过base64加密后长度 公钥字节产度在210-220之间
 * 私钥长度在840-850之间 所以数据库设计时如果存公钥长度设计varchar(256) 私钥长度varchar(1024)
 */
public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    private static final String PUBLIC_KEY = "rsa_public_key";
    private static final String PRIVATE_KEY = "rsa_private_key";
    private static final String ENCODING = "UTF-8";

    /**
     * 加密
     * 用公钥加密
     *
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String content, String base64PublicKeyStr) throws Exception {
        byte[] inputBytes = content.getBytes(ENCODING);
        byte[] outputBytes = encryptByPublicKey(inputBytes, base64PublicKeyStr);
        return Base64.encodeBase64String(outputBytes);
    }

    /**
     * 加密
     * <p>
     * 用私钥加密
     *
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String content, String base64PrivateKeyStr) throws Exception {
        byte[] inputBytes = content.getBytes(ENCODING);
        byte[] outputBytes = encryptByPrivateKey(inputBytes, base64PrivateKeyStr);
        return Base64.encodeBase64String(outputBytes);
    }

    /**
     * 解密
     * 用公钥解密
     *
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String content, String base64PublicKeyStr) throws Exception {
        byte[] inputBytes = Base64.decodeBase64(content);
        byte[] outputBytes = decryptByPublicKey(inputBytes, base64PublicKeyStr);
        return new String(outputBytes, ENCODING);
    }

    /**
     * 解密
     * 用私钥解密
     *
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content, String base64PrivateKeyStr) throws Exception {
        byte[] inputBytes = Base64.decodeBase64(content);
        byte[] outputBytes = decryptByPrivateKey(inputBytes, base64PrivateKeyStr);
        return new String(outputBytes, ENCODING);

    }

    /**
     * 加密
     * 用公钥加密
     *
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] content, String base64PublicKeyStr) throws Exception {
        // 对公钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKeyStr);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(content);

    }

    /**
     * 加密
     * 用私钥加密
     *
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] content, String base64PrivateKeyStr) throws Exception {
        // 对密钥解密
        byte[] privateKeyBytes = Base64.decodeBase64(base64PrivateKeyStr);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(content);

    }

    /**
     * 解密
     * 用公钥解密
     *
     * @param content
     * @param base64PublicKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] content, String base64PublicKeyStr) throws Exception {
        // 对密钥解密
        byte[] publicKeyBytes = Base64.decodeBase64(base64PublicKeyStr);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(content);

    }

    /**
     * 解密
     * 用私钥解密
     *
     * @param content
     * @param base64PrivateKeyStr
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] content, String base64PrivateKeyStr) throws Exception {
        // 对密钥解密
        byte[] privateKeyBytes = Base64.decodeBase64(base64PrivateKeyStr);

        // 取得私钥  for PKCS#8
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 指定 OAEP 填充和 SHA-256 哈希
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        OAEPParameterSpec oaepParams = new OAEPParameterSpec(
                "SHA-256",                   // 哈希算法
                "MGF1",                      // Mask 生成函数
                MGF1ParameterSpec.SHA256,    // MGF1 哈希
                PSource.PSpecified.DEFAULT
        );
        cipher.init(Cipher.DECRYPT_MODE, priKey, oaepParams);

        return cipher.doFinal(content);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getBase64PrivateKeyStr(Map keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getBase64PublicKeyStr(Map keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, RSAKey> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024); // 初始化RSA1024安全些
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate(); // 私钥
        Map<String, RSAKey> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    private static void test() throws Exception {
        String pubKey =
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgDxka1U8SWI1vBY7pA1UiCVnr" +
                        "dtSgE+PpyTqe2YSEWCgkYQ2YsohZwsaUao7nya7QnBgRiPKEHgS/Eey+L9iwo32S" +
                        "n5fUwb0nJ1+JeXRA6JsDEKpONJojIbF2nfgHLWsNn4bzn5Webc6WZLx0GyLTQuZG" +
                        "adFVuVq2dQqEsrq7HwIDAQAB";

        String str = "123456";
        String content = encryptByPublicKey(str, pubKey);
        System.out.println(content);

        String priKeyPkcs1 = "MIICXQIBAAKBgQDgDxka1U8SWI1vBY7pA1UiCVnrdtSgE+PpyTqe2YSEWCgkYQ2YsohZwsaUao7nya7QnBgRiPKEHgS/Eey+L9iwo32Sn5fUwb0nJ1+JeXRA6JsDEKpONJojIbF2nfgHLWsNn4bzn5Webc6WZLx0GyLTQuZGadFVuVq2dQqEsrq7HwIDAQABAoGAYtaGLo4WWXNywJzlE+kCbwdNAU/kL9FWYtT/5P7zNCZnXtTpWIi5GU+QpfvzmlAfq6qP+3w77wgG8/qGQsd8gGu3mydi0ImmD9sJdhhsJuWZhCMM+qmvSmvG/gvIr+bdEmPhpCQpa3BLveUkFDA/OnwfTVL6ruwZayMzuToB6WECQQD63Gx9DZVhYoSxR7qSmiGf/TjfOJusTcrmc27Z5X5MS36a3Ux9Z+c9EaYFldZ3cPzP521ugNVvZdovKqFKIcQ5AkEA5KYeKBVlkrLaamSEu5WAX3DqJ6iDRqEjzMoVad5B1I7kJHO+NijUxNHaWaSqLOHuk37X+EAjSTozzwOkKwbqFwJBAM1NhhAWBNHtcdEwddWzBJ/N+jRdPLIX/Fz7zZXQRruj8VpGkGn1lf6ZqfjaNuoLcyunKB0OnR6NCbIePl/QIKkCQGgEQjfN9BVWlBJOhCuqCWphvcQo3v+kktq5HCC7YYtHLfZ/SQrubEzVgtXBGUGtzpD+5VUkKGlJtwP4Dhkc3iUCQQCwiFKuQe/OdlkYk1L4mb0H0fzy+/6mYxyUqpTXUw/6BVDOyowvzieh9oh2ZhnQS7YPBWz5ZXzwUH4YVwGqxiwA";
        String priKeyPkcs8Openssl = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOAPGRrVTxJYjW8FjukDVSIJWet21KAT4+nJOp7ZhIRYKCRhDZiyiFnCxpRqjufJrtCcGBGI8oQeBL8R7L4v2LCjfZKfl9TBvScnX4l5dEDomwMQqk40miMhsXad+Actaw2fhvOflZ5tzpZkvHQbItNC5kZp0VW5WrZ1CoSyursfAgMBAAECgYBi1oYujhZZc3LAnOUT6QJvB00BT+Qv0VZi1P/k/vM0Jmde1OlYiLkZT5Cl+/OaUB+rqo/7fDvvCAbz+oZCx3yAa7ebJ2LQiaYP2wl2GGwm5ZmEIwz6qa9Ka8b+C8iv5t0SY+GkJClrcEu95SQUMD86fB9NUvqu7BlrIzO5OgHpYQJBAPrcbH0NlWFihLFHupKaIZ/9ON84m6xNyuZzbtnlfkxLfprdTH1n5z0RpgWV1ndw/M/nbW6A1W9l2i8qoUohxDkCQQDkph4oFWWSstpqZIS7lYBfcOonqINGoSPMyhVp3kHUjuQkc742KNTE0dpZpKos4e6Tftf4QCNJOjPPA6QrBuoXAkEAzU2GEBYE0e1x0TB11bMEn836NF08shf8XPvNldBGu6PxWkaQafWV/pmp+No26gtzK6coHQ6dHo0Jsh4+X9AgqQJAaARCN830FVaUEk6EK6oJamG9xCje/6SS2rkcILthi0ct9n9JCu5sTNWC1cEZQa3OkP7lVSQoaUm3A/gOGRzeJQJBALCIUq5B7852WRiTUviZvQfR/PL7/qZjHJSqlNdTD/oFUM7KjC/OJ6H2iHZmGdBLtg8FbPllfPBQfhhXAarGLAA=";
        String priKeyPkcs8New = "MIICdwIBADANBgsqhkiG9w0BDAoBAgSCAmEwggJdAgEAAoGBAOAPGRrVTxJYjW8FjukDVSIJWet21KAT4+nJOp7ZhIRYKCRhDZiyiFnCxpRqjufJrtCcGBGI8oQeBL8R7L4v2LCjfZKfl9TBvScnX4l5dEDomwMQqk40miMhsXad+Actaw2fhvOflZ5tzpZkvHQbItNC5kZp0VW5WrZ1CoSyursfAgMBAAECgYBi1oYujhZZc3LAnOUT6QJvB00BT+Qv0VZi1P/k/vM0Jmde1OlYiLkZT5Cl+/OaUB+rqo/7fDvvCAbz+oZCx3yAa7ebJ2LQiaYP2wl2GGwm5ZmEIwz6qa9Ka8b+C8iv5t0SY+GkJClrcEu95SQUMD86fB9NUvqu7BlrIzO5OgHpYQJBAPrcbH0NlWFihLFHupKaIZ/9ON84m6xNyuZzbtnlfkxLfprdTH1n5z0RpgWV1ndw/M/nbW6A1W9l2i8qoUohxDkCQQDkph4oFWWSstpqZIS7lYBfcOonqINGoSPMyhVp3kHUjuQkc742KNTE0dpZpKos4e6Tftf4QCNJOjPPA6QrBuoXAkEAzU2GEBYE0e1x0TB11bMEn836NF08shf8XPvNldBGu6PxWkaQafWV/pmp+No26gtzK6coHQ6dHo0Jsh4+X9AgqQJAaARCN830FVaUEk6EK6oJamG9xCje/6SS2rkcILthi0ct9n9JCu5sTNWC1cEZQa3OkP7lVSQoaUm3A/gOGRzeJQJBALCIUq5B7852WRiTUviZvQfR/PL7/qZjHJSqlNdTD/oFUM7KjC/OJ6H2iHZmGdBLtg8FbPllfPBQfhhXAarGLAA=";
        String priKey = priKeyPkcs8New;

        String output = decryptByPrivateKey(content, priKey);
        System.out.println(output);
    }

    // === Testing ===
    public static void main(String[] args) throws Exception {
        final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANabv7cO8/ipivSJEXP1A+id8Hka4a5ewvkivnSxrQTK9Uz6//R+ZbfrHMrcj7a/VT1l3oH0LQgGRgmVunAn0/gEU0SqO61WHn96Qzl1xkXIeklZ3lxMBQScEbl0GM9vmTM+ZodD3FB6SnwfYvtd5nYZ9+dNVruVrQafGoAx4wyJAgMBAAECgYAX2IJ64qx1KtGHzlskGUtv42y+6B+/ckQTzqp+6OciqzKKdTFPh2PiBbvTRqKpWaUtB06r/eerBpdtpTdsEuDYb/vQ8UqYH7hwPKZ6aVKrwM1yS90ww3GGowY33RX7kwOvI5tWo0lcJhp8GtklxVbg4XcmsM4Hu4Q487VgHkHUoQJBAOm3SzkhQIUIxWkTkje2xTgay708UqHf8KpyJRWrvk2q4Z4osQZgwiXgx292E1cxR/CtuFNtJc5NvsZ6drY05GUCQQDrEhAu9Rxe4Y7buaKb4wc6D15ZFS8eJZE3sVY9LLWxq97DhUN7ARheKe1PfGNVXLjfHHntuTFSPJnmmvsVGWtVAkAy/TRCIIkM/R7kj8qEsTFRzjbI8FIu0saUyRZiJff8xd03PjVw6McysFmSbbZGfY/uaFggi12GJtwKPUmM8vkpAkEAlH3RmUgextTnQGeQj7anHwcMS2u1Wu7SQAMW/gfbMsPmMU5iZTY45WrIzNg/i3HKGq5LW18MB+3eLi0ihJ6NDQJAQ1IbeiZ5V+TuE3TSWh+gANi41gldAjstbx4KWFvrFtfqUgLsATgIYqDy6jpZ3ecoqRB5OhH+BjJz+heULJXp2g==";

        final String encryptedData = "RoKQNqxFdXucb5eShuxKUAvYSPDfcKz24ftlm/iVHbUYFBMQmFWUlW1+oy6naeyAp0VKj3TB9ymPGIA9pESmEJFVHgwpNn1vq/w3hzS9MFXaVGALJQnURJvO/5iumB+mznv4sLg1sNYoa0EwM7qP0mNIk7r+taE8p9fiNM49Mc4=";

        final String decryptedData = decryptByPrivateKey(encryptedData, privateKey);
        System.out.println(decryptedData);
    }

}