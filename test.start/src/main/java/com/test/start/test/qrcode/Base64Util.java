package com.test.start.test.qrcode;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {

    // 字符串编码
    private static final String UTF_8 = "UTF-8";

    /**
     * 加密字符串
     *
     * @param inputData
     * @return
     */
    public static String decodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * 解密加密后的字符串
     *
     * @param inputData
     * @return
     */
    public static String encodeData(String inputData) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes(UTF_8)), UTF_8);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Base64Util.encodeData("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFoAAABfAQAAAABAHu0cAAAC/0lEQVR42mP4jwAHGAY150BQXdeMmXaMjCDO/TCTOV+bXN7XgjgX3Zc82T63tt0VzCkNK9NR5MqFcMrqRBvu/zYFc+6HRsjPnWK2HaznQCgEgE37f2jr9zscKyaC7fkYN3W5w9zrawxBnL/HrzD3aW+xuAjiHAqw6Bf9ErKQHcR52N+x7GHP14hgEOdM7pslsRcO6ymDOHdP36lPVujP3Aji3OK2Cd8wVW67OYhz88JakwPWP4ykQZzjiYkhkjrTjMCWvjq7qHiCR3/FUbBzEgz4g1O+bNQGcS7svZ0dZrfH9ziIc8Js+aZHurfywTKnN6f1Kfy6xfkcxPkT8EhQYenRCaogzuNJkfzzJqzkbAVxjjHJzM7c7rfLGuyfbUEMF0xfV64FeyG454uoRNATsMzt1R8bjiz+mXkYxHnEF8RQnck3ZS+I8+bcm0Ypsb3RB0Gcy6zn32ZqerUtB3Fu3PXkfvNZUdodxHkiMvc6gxHTB16w0VdO7Y+trtAE++dn4PRjVxdc3AoOqoc3at1jfirLgmVOcLw62rhIJAEcIA8+JC9KOW3J+xDE+cJZPemp3Raz+SDOwS0+k1cHW0qAA/GddHhya33fP28Q50irql4b2/r4THAEe+7+GpOk9gwcIG+vHHoUonPkZDnYtBKLNTuqF+/vBnGu/o+/xrf8VU4h2Dlhx1dr1JjfaQe7YGrUrcVhP26DA/E2+yM75xa+tWAX3Gj4LGqxMs8anChO8Pn2zbOfNPUyiPN99kz78/orZoNN+5yx6epjnrjaThDn+kOVLrbaO1Wl4CgpEvY9nfX40WpwEtsc/N3mV+LJdBDnk2SPh8sREVNwLLxe82u6xg1/27sgzhXl68tkN4lsaQZ77kDF//v1X5gjwQmJWZf9wAuuHG5wcrmt3Ru73KoSHD8HinveiMYdvugM1lM1ZU/srlpWcOq9GBt66MCpV1vAei5GaT02iZGymg7m+MXlurEtX5oP1pN6crmQ3VsJsMyB6pdlWsxC6dmDvXBA4gAA3S4z6GIBLw8AAAAASUVORK5CYII="));
        String enStr = Base64Util.encodeData("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFoAAABfAQAAAABAHu0cAAAC/0lEQVR42mP4jwAHGAY150BQXdeMmXaMjCDO/TCTOV+bXN7XgjgX3Zc82T63tt0VzCkNK9NR5MqFcMrqRBvu/zYFc+6HRsjPnWK2HaznQCgEgE37f2jr9zscKyaC7fkYN3W5w9zrawxBnL/HrzD3aW+xuAjiHAqw6Bf9ErKQHcR52N+x7GHP14hgEOdM7pslsRcO6ymDOHdP36lPVujP3Aji3OK2Cd8wVW67OYhz88JakwPWP4ykQZzjiYkhkjrTjMCWvjq7qHiCR3/FUbBzEgz4g1O+bNQGcS7svZ0dZrfH9ziIc8Js+aZHurfywTKnN6f1Kfy6xfkcxPkT8EhQYenRCaogzuNJkfzzJqzkbAVxjjHJzM7c7rfLGuyfbUEMF0xfV64FeyG454uoRNATsMzt1R8bjiz+mXkYxHnEF8RQnck3ZS+I8+bcm0Ypsb3RB0Gcy6zn32ZqerUtB3Fu3PXkfvNZUdodxHkiMvc6gxHTB16w0VdO7Y+trtAE++dn4PRjVxdc3AoOqoc3at1jfirLgmVOcLw62rhIJAEcIA8+JC9KOW3J+xDE+cJZPemp3Raz+SDOwS0+k1cHW0qAA/GddHhya33fP28Q50irql4b2/r4THAEe+7+GpOk9gwcIG+vHHoUonPkZDnYtBKLNTuqF+/vBnGu/o+/xrf8VU4h2Dlhx1dr1JjfaQe7YGrUrcVhP26DA/E2+yM75xa+tWAX3Gj4LGqxMs8anChO8Pn2zbOfNPUyiPN99kz78/orZoNN+5yx6epjnrjaThDn+kOVLrbaO1Wl4CgpEvY9nfX40WpwEtsc/N3mV+LJdBDnk2SPh8sREVNwLLxe82u6xg1/27sgzhXl68tkN4lsaQZ77kDF//v1X5gjwQmJWZf9wAuuHG5wcrmt3Ru73KoSHD8HinveiMYdvugM1lM1ZU/srlpWcOq9GBt66MCpV1vAei5GaT02iZGymg7m+MXlurEtX5oP1pN6crmQ3VsJsMyB6pdlWsxC6dmDvXBA4gAA3S4z6GIBLw8AAAAASUVORK5CYII=");
        System.out.println(Base64Util.decodeData(enStr));
    }
}