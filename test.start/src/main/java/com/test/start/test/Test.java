package com.test.start.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.test.start.test.bean.APINode;
import com.test.start.test.bean.Data;
import com.test.start.test.bean.LiveCourseLesson;
import com.test.start.test.util.HttpClientUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * @author CJ
 * @date 2019/10/18
 */
public class Test {
    public static final String TYPE_JPG = "jpg";
    public static final String TYPE_GIF = "gif";
    public static final String TYPE_PNG = "png";
    public static final String TYPE_BMP = "bmp";
    public static final String TYPE_UNKNOWN = "unknown";

    /**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流判断图片类型
     * @param fis
     * @return jpg/png/gif/bmp
     */
    public static Boolean isImagesType(FileInputStream fis) {
        boolean flag = false;
        //读取文件的前几个字节来判断图片格式
        byte[] b = new byte[4];
        try {
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                flag = true;
            } else if (type.contains("89504E47")) {
                flag = true;
            } else if (type.contains("47494638")) {
                flag = true;
            } else if (type.contains("424D")) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static void testGson() {
        String json =
                "{"
                        + "'title': 'title 1',"
                        + "'id' : 1,"
                        + "'children' : 'true',"
                        + "'groups' : [{"
                        + "'title' : 'itle 11',"
                        + "'id' : 2,"
                        + "'children' : 'true',"
                        + "'groups' : [{"
                        + "'title' : 'itle 111',"
                        + "'id' : 3,"
                        + "'children': 'false',"
                        + "'groups':[]"
                        + "}]"
                        + "}]"
                        + "}";

        // Now do the magic.
        Data data = new Gson().fromJson(json, Data.class);
        String string = JSON.toJSONString(data);
        // Show it.
        System.out.println(string);
    }

    public static void main(String args[]) throws IOException {
        String url="http://localhost:7999/course/callback/record/info";
        RecordCallbackRequest request=new RecordCallbackRequest();
        request.setCourseID(1);
        request.setCmd("Record");
        String json = JSONObject.toJSONString(request);

        System.out.println("request:"+json);
        String result = HttpClientUtil.doPostJson(url, json);
        System.out.println("result:"+result);
    }

}