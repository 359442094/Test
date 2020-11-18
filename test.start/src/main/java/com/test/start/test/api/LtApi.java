package com.test.start.test.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.start.test.util.HttpClientUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 联通api
 * @author chenjie
 * @date 2020-09-17
 */
public class LtApi {

    public static void getToken(){
        String url = "https://www.gzuni.com/apps/kingcard/b2i/api/get_token/";
        Map<String,String> map = new HashMap<>();
        map.put("source","yunfen");
        map.put("password","nb9rg5rw");
        String json = HttpClientUtil.doPostJson(url, JSONObject.toJSONString(map));
        System.out.println("json:"+json);
        JSONObject object = JSONObject.parseObject(json);
        System.out.println("data:"+object.getString("data"));
    }

    public static void queryList(){
        String url = "https://www.gzuni.com/apps/kingcard/b2i/cz_api/";
        Map<String,String> map = new HashMap<>();
        map.put("source","yunfen");
        map.put("token","MTYwMDk5NzA3Ni40MTQ1MzY3OmFlMjdmMGU1ZjEwOWNlNDczYzBhMDkzZTBjYzkwMWE4NjFhNzNkMmM=");
        map.put("chnlcode","5112042796");
        map.put("serial_number","17502004704");
        String json = HttpClientUtil.doPostJson(url, JSONObject.toJSONString(map));
        System.out.println("json:"+json);
        JSONObject object = JSONObject.parseObject(json);
        System.out.println("data:"+object.getString("data"));
    }

    public static void main(String[] args) {
        //getToken();
        queryList();
    }

    //MTYwMDc0MDQ3OS41MDY3NTkyOjk3NDQ1NDEzMzYzMjZmOWU3YzRhOGJiOTY0NWQzNTM5MmZjNzNmMTU=


}
