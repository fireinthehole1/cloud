package com.learn.spring.cloud.core;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author scott
 * @date 2019/7/15 - 11:26
 */
public class RSAUtil {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return Base64.encode(encryptedData);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey, String cipherStr) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherStr);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return Base64.encode(encryptedData);
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey, String cipherStr) throws Exception {
        Cipher cipher = Cipher.getInstance(cipherStr);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }


    /**
     * 签名
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encode(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decode(sign));
    }


    public static String paramSort(Map<String, Object> requestParam) {
        // 将需要签名的参数内容按参数名的字典顺序进行排序，并拼接为字符串
        StringBuilder sb = new StringBuilder();
        requestParam.entrySet().stream().sorted(
                Comparator.comparing(Map.Entry::getKey)).forEach(entry ->
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        String paramStr = sb.toString().substring(0, sb.length() - 1);
        return paramStr;
    }


    public static void genKeyPair(Map<Integer, String>  map) throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encode(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encode((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        map.put(0,publicKeyString);  //0表示公钥
        map.put(1,privateKeyString);  //1表示私钥
    }

    public static void main(String[] args) {
        try {
             Map<Integer, String> platform = new HashMap<Integer, String>();
             Map<Integer, String> app = new HashMap<Integer, String>();
             genKeyPair(platform);
             genKeyPair(app);
             Map<String, Object> requestParam = new HashMap<>(16);
             requestParam.put("phone", "13629711009");
             String data = RSAUtil.paramSort(requestParam);
            //参数
             // RSA加密
             String encryptData = encrypt(data,getPublicKey(platform.get(0)));
             System.out.println("加密后内容:" + encryptData);
             System.out.println(URLEncoder.encode(encryptData, "utf-8"));

             // RSA解密
             String decryptData = decrypt(encryptData, getPrivateKey(platform.get(1)));
             System.out.println("解密后内容:" + decryptData);

             // RSA签名
             String sign = sign(encryptData,  getPrivateKey(app.get(1)));
             System.out.println("签名内容:" + sign);
             System.out.println(URLEncoder.encode(sign, "utf-8"));

             // RSA验签
             boolean result = verify(encryptData, getPublicKey(app.get(0)),sign);
             System.out.println("验签结果:" + result);
            /**
             * 加密后内容:NlPePFu0Y1rtjaZDza853Oi5FDAp2xY4mLlEjrB+CrYDM/QUHX5GLXxKvSHeUrfqYjBoJhxfgzIGah1zoisXDjOucdVOn6DuwtIx19tuo3WuuLYZMrXryRpuayWaI1SJDjDiC1rfQcadLNuIyVqJ8jqfpJO5KsbobSXFNrxLQ+I=
             * NlPePFu0Y1rtjaZDza853Oi5FDAp2xY4mLlEjrB%2BCrYDM%2FQUHX5GLXxKvSHeUrfqYjBoJhxfgzIGah1zoisXDjOucdVOn6DuwtIx19tuo3WuuLYZMrXryRpuayWaI1SJDjDiC1rfQcadLNuIyVqJ8jqfpJO5KsbobSXFNrxLQ%2BI%3D
             * 解密后内容:phone=13629711009
             * 签名内容:UqkQhuaYVAUBvdgRuYxV86JKd2XJO0iwnvxXvW9li8Z+QDAnSQOTKpzxT9RHyDzM+gHYRJPExiDufcQ+NJSfTYCAjBbZq/jNylzVA8IyO0pqMdOC2xoXVRufAgL2R15BPxGj0ELtnnNz0xyPgDKI1ORx1GSgXBXKB7nL03DyluU=
             * UqkQhuaYVAUBvdgRuYxV86JKd2XJO0iwnvxXvW9li8Z%2BQDAnSQOTKpzxT9RHyDzM%2BgHYRJPExiDufcQ%2BNJSfTYCAjBbZq%2FjNylzVA8IyO0pqMdOC2xoXVRufAgL2R15BPxGj0ELtnnNz0xyPgDKI1ORx1GSgXBXKB7nL03DyluU%3D
             * 验签结果:true
             */
            // RSA解密
            //String encryptData = "MzcxUJp14SgBqUeEqcI7ConzSOqjCnZv+/pHuACvvVZ10MJIjatxrosY8o8P5pB/qLDiYXsrMrw7gmoxLrzAY2ZrgAvPohmk3JePqm6TVbFjbb6mxm39su51N/qEfiiEQXw8qjNV/0eiIUJa5oiDCy70yWP8P9MLldM+4kMd43M=";
            //String decryptData = decrypt(encryptData, getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALOivNGk4h0Rbqs+Jhyn39ac9GntQXyyn2Wr+IDoLJQZaurizRVn6DXH/LwMhN7YmSZJvK2UT154/GXhpDnk9SrUf67Z4476rJAiHRlDIjvH1vKMgcBXRAd7QWJE30y+NE7nkCqDIubziY2+MEb+NX9Wi9VwvdC/6UErl7/sT7YJAgMBAAECgYBtv7dsvjgHWmcq6EWQrF/tVudc8D9aWCdajr68wxy5Pu47o2V49orAkPawTVXAcMTgx5wm0L3NEk3iUQFs/Z1rDG4z4DHIzjf9DyUbyRXp9+ClMlbvEUlKPcI8460NWhTB/DcRQPhH9JrYQhHncIo8J0ZbhWEvyGMR01zcIRH8AQJBAPl8VZPZzojWwyvcV6doa5gZ0HjZWIK6bj46X/0JTSzuoJjIxi+TEN00AlcbktExjX7AvIztx/pyyx1fK7MVi9ECQQC4U388fPvUFkdDra9B5cgefCRB0Z9gYEyFbO5/1N3QlLsjzp68+Ea8pv5aljP7L4tu7BIDFev258r5AK0yiey5AkEAovUSlMNEw168Fs8Stfdfp/bck8MrrCAH2D+a6RWFQl8PqRwjT3nZHxNUn/8Ts+6RBITx43+BCtamUyYRziS5sQJAXf4d9K9bDxRpNKa9puHQgVMWfgWxusiAXM1mdQMMNU+Sejjjk0MbdxnEvFa5nQ3qkR6KNM6Z6+bZBs6Egy/36QJAJdaC0Zbkzk/DvEXjLjHpqSRIypf9L+S5sm7gZyMoNfRVaJa7ra/M6LA+kWOyehNsu5db4l+VtebMkuWAOQariA=="));
            //decryptData = decryptData.trim();
            //System.out.println("解密后内容:" + decryptData);

            //String sign = sign(data, getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAICdaF2tNg7Bxyq3EwNFxebzfLom+jbUORJnUoJ71TekaHgj1lCe3ouEmN1+SQBX6/WvG2mCV5Wm1Q6DNSBfawYy/lIVRig1MJ3BeVE/3517SNnMB+Tz10ruRevRiZ2355QCNfBYUfRODvsr2gNDm6foXGEdY79t1bWLvkB1QQm7AgMBAAECgYAUFYCIsFWytDvfxbroVerzh65Ae3mzOiy3XP8bdS70g2305NPAvy5NZyVf4SqRLSmhsfh732jB6811eLPBHGVf1ywd7d7v/W7qay+tpK+stBb2y7gWy65pVy0U/17X9q9V84wus2LFCTbKhpEtCdp2RbN3THDie1vWRhnGiexwoQJBANH3ytXeuBYP5Zh3qWGvI3jeAKvPm+qphLRaKPa5FgODurqzszkG5X+bfAYC/xt7jy72nlv0ZOaAJlULo46dz8kCQQCcz8TItnzeqtqguWSE9EGurTcDh+tJYuFAnULEtKFhqLkjbyvfeBLb9qFIqvNFZ691XTsUFC1YmU7tdBw7qbdjAkBybIqCYNyLUWB65YNOM8auALX26635hSZHrvax9g20Hp6tTm72PfblwtMk548k6AIQwt9sF1TBdMbvA+e/OetZAkA1BmjDejnVTa7qJGaiMro3J+lvbzmXsjD7GXJnc7EShew82C3cvgyahtZY7T2f3YTOSf1dN8lxqT2wO9AEOMqDAkEAmLxBun+kyylY7CUKD3FYyV8sesTEgyW5AtBVyzh6iuWyHLyIz9AoiYUyiXk8bkOy86whUU6uwsc2hcFKPS+vqw=="));
           // System.out.println("签名内容:" + sign);

            //boolean result = verify(data, getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAnWhdrTYOwccqtxMDRcXm83y6Jvo21DkSZ1KCe9U3pGh4I9ZQnt6LhJjdfkkAV+v1rxtpgleVptUOgzUgX2sGMv5SFUYoNTCdwXlRP9+de0jZzAfk89dK7kXr0Ymdt+eUAjXwWFH0Tg77K9oDQ5un6FxhHWO/bdW1i75AdUEJuwIDAQAB"), sign);
           // System.out.print("验签结果:" + result);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}
