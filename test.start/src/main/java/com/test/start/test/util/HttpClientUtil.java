package com.test.start.test.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 请求外部接口工具类
 * @author CJ
 * @date 2019/10/12
 */
public class HttpClientUtil {
    /**
     * 带参数的get请求
     * @param url
     * @param param
     * @return String
     */
    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的get请求
     * @param url
     * @return String
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * 使用httpclint 发送文件
     * @param file
     *            上传的文件
     * @return 响应结果
     */
    public static String uploadFile(String url , MultipartFile file, String fileParamName, Map<String,String>headerParams, Map<String,String>otherParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost(url);
            //添加header
            for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            for (Map.Entry<String, String> e : otherParams.entrySet()) {
                builder.addTextBody(e.getKey(), e.getValue());// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 带File参数的post请求
     * @param url
     * @param writeToken
     * @param file
     * @param jsonrpc
     * @return
     */
    public static String uploadFile(String url,String cataid,String writeToken,File file,String jsonrpc) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));
            FileBody _filedata = new FileBody(file);
            StringBody _jsonrpc = new StringBody(jsonrpc, contentType);
            StringBody _writetoken = new StringBody(writeToken, ContentType.TEXT_PLAIN);
            // 创建参数列表
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("writetoken", _writetoken)
                    .addPart("Filedata", _filedata)
                    .addPart("JSONRPC", _jsonrpc)
                    .addPart("cataid", new StringBody(cataid))
                    .build();
            // 执行http请求
            httpPost.setEntity(reqEntity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 带File参数的post请求
     * @param url
     * @param file
     * @return
     */
    public static String uploadFile(String url,File file,Map<String,String> map) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            ContentType contentType = ContentType.create("text/plain", Charset.forName("UTF-8"));

            if(file!=null){
                entityBuilder.addPart("Filedata",new FileBody(file));
            }

            for (Map.Entry<String, String> e : map.entrySet()) {
                // 类似浏览器表单提交，对应input的name和value
                entityBuilder.addPart(e.getKey(),new StringBody(e.getValue(),contentType));
            }
            // 创建参数列表
            HttpEntity reqEntity = entityBuilder.build();
            // 执行http请求
            httpPost.setEntity(reqEntity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 带参数的post请求
     * @param url
     * @param param
     * @return String
     */
    public static String doPostContentType(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                //httpPost.addHeader("Content-type","application/x-www-form-urlencoded;");
                //httpPost.setHeader("Accept", "application/json");

                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }

                System.out.println("-----------------------");
                paramList.stream().forEach(System.out::println);
                System.out.println("-----------------------");

                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 带参数的post请求
     * @param url
     * @param param
     * @return String
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
               /* httpPost.addHeader("Content-type","application/json;");
                httpPost.setHeader("Accept", "text/html;charset=UTF-8;");*/

                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }

                System.out.println("-----------------------");
                paramList.stream().forEach(System.out::println);
                System.out.println("-----------------------");

                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPostFormData(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                httpPost.addHeader("Content-type","application/form-data;");
                //httpPost.setHeader("Accept", "application/json");

                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }

                System.out.println("-----------------------");
                paramList.stream().forEach(System.out::println);
                System.out.println("-----------------------");

                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, Consts.UTF_8);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(response!=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 不带参数的post请求
     * @param url
     * @return String
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 传送Json类型的post请求
     * @param url
     * @param json
     * @return String
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 使用httpclint 发送文件
     * @param file
     * 调用示例： String json = HttpClientUtil.uploadFile(this.uploadUrl, file, "file", map);
     *            上传的文件
     * @return 响应结果
     */
    public static String uploadFile(String url , MultipartFile file, String fileParamName,Map<String,String>otherParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            String fileName = file.getOriginalFilename();
            HttpPost httpPost = new HttpPost(url);
            //添加header
            /*for (Map.Entry<String, String> e : headerParams.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }*/
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            //加上此行代码解决返回中文乱码问题
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 文件流
            builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName+".png");
            for (Map.Entry<String, String> e : otherParams.entrySet()) {
                // 类似浏览器表单提交，对应input的name和value
                builder.addTextBody(e.getKey(), e.getValue());
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
