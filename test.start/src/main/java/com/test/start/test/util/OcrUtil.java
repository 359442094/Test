package com.test.start.test.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralBasicOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.IDCardOCRResponse;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author chenjie
 * @date 2020-10-10
 */
public class OcrUtil {

    //图片转化成base64字符串
    public static String ImageToBase64(String img) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = img;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        //返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    public static void testOcr(){
        String img = "D:\\WindowsApache\\images\\test_front.png";//图片地址
        //test_back.png
        try {
            Credential cred = new Credential("AKIDxg6PR86tviC1nBACQOkt74k9GBsI9SDl", "h7G8i00GhyyLSHq8E4fzDAvPcePzJ2Uf");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            OcrClient client = new OcrClient(cred, "ap-shanghai", clientProfile);

            IDCardOCRRequest req = new IDCardOCRRequest();
            req.setImageBase64(ImageToBase64(img));

            IDCardOCRResponse resp = client.IDCardOCR(req);

            System.out.println(IDCardOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        String img = "D:\\WindowsApache\\images\\test_front.png";
        Map<String,String> map=new HashMap<>();
        map.put("webankAppId","TIDAB1Go1");
        map.put("version","1.0.0");
        map.put("nonce","0e758acca2e849daa1c5361167dc72ef");
        map.put("sign","0106E43E9910A8EA5EEE4E722F43C2048EB2DCB2");
        map.put("orderNo", UUID.randomUUID().toString());
        map.put("usreId","ed6a81a8f0d54aff836b0b8bffc9ca4111");
        map.put("cardType","0");
        map.put("idcardStr","1");//ImageToBase64(img)
        String post = HttpClientUtil.doPost("https://ida.webank.com/api/paas/idcardocrapp", map);
        System.out.println("post:"+post);
    }


}
