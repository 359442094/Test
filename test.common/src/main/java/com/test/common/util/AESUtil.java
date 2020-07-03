package com.test.common.util;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
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



    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
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



    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

   private static String password = "12345678";

   public static String encryptStart(String content){
       byte[] encryptResult = AESUtil.encrypt(content, password);
       String encryptResultStr = AESUtil.parseByte2HexStr(encryptResult);
       return encryptResultStr;
   }

   public static String decryptStart(String encryptResultStr){
       String result = "";
       try {
           byte[] decryptFrom = AESUtil.parseHexStr2Byte(encryptResultStr);
           byte[] decryptResult = AESUtil.decrypt(decryptFrom, password);
           result = new String(decryptResult);
       }catch (Exception e){
           result = "";
       }
       return result;
   }

    public static void main(String[] args) {
        String content = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFoAAABfAQAAAABAHu0cAAAC/0lEQVR42mP4jwAHGAY150BQXdeMmXaMjCDO/TCTOV+bXN7XgjgX3Zc82T63tt0VzCkNK9NR5MqFcMrqRBvu/zYFc+6HRsjPnWK2HaznQCgEgE37f2jr9zscKyaC7fkYN3W5w9zrawxBnL/HrzD3aW+xuAjiHAqw6Bf9ErKQHcR52N+x7GHP14hgEOdM7pslsRcO6ymDOHdP36lPVujP3Aji3OK2Cd8wVW67OYhz88JakwPWP4ykQZzjiYkhkjrTjMCWvjq7qHiCR3/FUbBzEgz4g1O+bNQGcS7svZ0dZrfH9ziIc8Js+aZHurfywTKnN6f1Kfy6xfkcxPkT8EhQYenRCaogzuNJkfzzJqzkbAVxjjHJzM7c7rfLGuyfbUEMF0xfV64FeyG454uoRNATsMzt1R8bjiz+mXkYxHnEF8RQnck3ZS+I8+bcm0Ypsb3RB0Gcy6zn32ZqerUtB3Fu3PXkfvNZUdodxHkiMvc6gxHTB16w0VdO7Y+trtAE++dn4PRjVxdc3AoOqoc3at1jfirLgmVOcLw62rhIJAEcIA8+JC9KOW3J+xDE+cJZPemp3Raz+SDOwS0+k1cHW0qAA/GddHhya33fP28Q50irql4b2/r4THAEe+7+GpOk9gwcIG+vHHoUonPkZDnYtBKLNTuqF+/vBnGu/o+/xrf8VU4h2Dlhx1dr1JjfaQe7YGrUrcVhP26DA/E2+yM75xa+tWAX3Gj4LGqxMs8anChO8Pn2zbOfNPUyiPN99kz78/orZoNN+5yx6epjnrjaThDn+kOVLrbaO1Wl4CgpEvY9nfX40WpwEtsc/N3mV+LJdBDnk2SPh8sREVNwLLxe82u6xg1/27sgzhXl68tkN4lsaQZ77kDF//v1X5gjwQmJWZf9wAuuHG5wcrmt3Ru73KoSHD8HinveiMYdvugM1lM1ZU/srlpWcOq9GBt66MCpV1vAei5GaT02iZGymg7m+MXlurEtX5oP1pN6crmQ3VsJsMyB6pdlWsxC6dmDvXBA4gAA3S4z6GIBLw8AAAAASUVORK5CYII=";

        //加密
        String encryptStart = encryptStart(content);
        System.out.println(encryptStart);
        //解密
        String decryptStart = decryptStart(encryptStart);
        System.out.println(decryptStart);

    }

}