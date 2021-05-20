package com.test.controller;

import com.test.common.annoation.RequestLimit;
import com.test.common.annoation.ShowLogger;
import com.test.common.dto.CheckFieldRequest;
import com.test.common.dto.WithdrawalReportCallbackReqeust;
import com.test.common.exception.Result;
import com.test.common.util.FileUtil;
import com.test.common.util.RedisUtil;
import com.test.common.videoApi.*;
import com.test.common.dto.RechargeOrderSubmitCallBackRequest;
import com.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Api(tags = {"测试视图接口"})
@Controller
@SuppressWarnings(value = "all")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MergeRecordFileAPI mergeRecordFileAPI;
    @Autowired
    private MergeRecordFileCallBackAPI mergeRecordFileCallBackAPI;
    @Autowired
    private FileForwardSaveAPI fileForwardSaveAPI;
    @Autowired
    private FileForwardSaveCallBackAPI fileForwardSaveCallBackAPI;
    @Autowired
    private SettingLiveCallBackAPI settingLiveCallBackAPI;
    @Autowired
    private StartLiveCallBackAPI startLiveCallBackAPI;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private FileUtil fileUtil;

    @Value(value = "${file.rootPath}")
    private String rootPath;
    @Value(value = "${file.urlPrefix}")
    private String urlPrefix;
    @Value(value = "${file.imgPath}")
    private String imgPath;
    @Value(value = "${file.caseImage}")
    private String caseImage;
    @Value(value = "${file.excelTemplate}")
    private String excelTemplate;
    @Value(value = "${file.studentTemplate}")
    private String studentTemplate;

    //星启天充值话费接口-异步回调
    @ApiOperation(value = "京东金融回调接口-异步回调",notes = "京东金融回调接口-异步回调")
    @ShowLogger(info = "京东金融回调接口-异步回调")
    @RequestMapping(path = "/withdrawalReportCallback",method = RequestMethod.GET)
    public Object withdrawalReportCallback(WithdrawalReportCallbackReqeust request){
        return request;
    }

    /**
     * 1秒内只能访问1次(可以拦截，缺少全局异常处理)
     * */
    @RequestLimit(count=1,time=600)
    @ApiOperation(value = "测试限流",notes = "测试限流")
    @RequestMapping(path = "/test/limit",method = RequestMethod.POST)
    @ResponseBody
    public Result limit() {
        return new Result("200","请求成功");
    }

    @ApiOperation(value = "测试同时返回json/xml",notes = "测试同时返回json/xml")
    @PostMapping(value = "/test/testResult")
    public Map<String,String> testResultJson(){
        Map<String,String> map=new HashMap<>();
        map.put("1","1value");
        map.put("2","2value");
        map.put("3","3value");
        return map;
    }

    @ApiOperation(value = "上传html生成图片",notes = "上传html生成图片")
    @RequestMapping(path = "/test/upload",method = RequestMethod.POST)
    @ResponseBody
    public boolean upload(String data,String fileName, HttpServletResponse response) throws Exception{
        //设置跨域
        response.setHeader("Access-Control-Allow-Origin","*");
        boolean flag=true;
        Base64 base64 = new Base64();
        byte[] b = base64.decode(data.substring("data:image/png;base64,".length()).getBytes());
        String oldPath="C:\\phpstudy_pro\\WWW\\files\\"+fileName+".png";
        File file = new File(oldPath);
        InputStream in = new ByteArrayInputStream(b);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            flag=false;
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 下载html生成的图片
     */
    @ApiOperation(value = "下载html生成的图片",notes = "下载html生成的图片")
    @ShowLogger(info = "下载html生成的图片")
    @RequestMapping(path = "/test/download/{fileName}",method = RequestMethod.GET,produces = "application/octet-stream")
    @ResponseBody
    public String download(@PathVariable String fileName,HttpServletResponse response) throws IOException {
        try {
            String read="C:/phpstudy_pro/WWW/files/"+fileName+".png";
            FileUtil.download(read,"test.png",request,response);
        }catch (Exception e){
            return "下载失败:"+e.getMessage();
        }
        return "下载成功";
    }

    @RequestMapping(value = "/htmltest", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String htmlGenerator() throws Exception{
        PrintScreen4DJNativeSwingUtils.printUrlScreen2jpg("C:/phpstudy_pro/WWW/files/test.png", "https://www.baidu.com", 0,0,1400, 900);
        return null;
    }

    /**
     * 下载直播课程填写模板
     */
    @ApiOperation(value = "下载直播课程填写模板",notes = "下载直播课程填写模板")
    @ShowLogger(info = "下载直播课程填写模板")
    @RequestMapping(path = "/test/file/download/excelTemplate",method = RequestMethod.GET,produces = "application/octet-stream")
    public void downloadExcelTemplate() throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/template.xlsx";
        log.info("直播课程填写模板位置:"+rootPath + imgPath + excelTemplate);
        FileUtil.download(rootPath + imgPath + excelTemplate,"直播课程填写模板.xlsx",request,response);
    }

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @ShowLogger(info = "上传图片")
    @RequestMapping(path = "/test/file/upload/file",method = RequestMethod.POST)
    public void uploadExcelTemplate(@RequestParam(value = "file")MultipartFile file) throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/template.xlsx";
        log.info("上传图片");
        FileUtil.upload(file);
    }

    @ShowLogger(info = "测试")
    @ApiOperation(value = "测试",notes = "测试")
    @RequestMapping(path = "/test/test",method = RequestMethod.GET)
    @ResponseBody
    public CheckFieldRequest check(CheckFieldRequest request) throws IOException {
        return request;
    }

    @ShowLogger(info = "测试")
    @ApiOperation(value = "测试",notes = "测试")
    @RequestMapping(path = "/test/test1",method = RequestMethod.GET)
    @ResponseBody
    public Object check(HttpServletRequest request) throws IOException {
        //String ipAddr = IPUtil.getIpAddr(request);
        return null;
    }

    @ResponseBody
    @ShowLogger(info = "合并直播录制文件")
    @RequestMapping(path = "/xiaoyi/mergeRecordFile",method = RequestMethod.GET)
    @ApiOperation(value = "合并直播录制文件",notes = "合并直播录制文件")
    public MergeRecordFileResponse mergeRecordFile(MergeRecordFileRequest request){
        return mergeRecordFileAPI.process(request);
    }

    @ResponseBody
    @ShowLogger(info = "合并直播录制文件回调函数")
    @RequestMapping(path = "/xiaoyi/mergeRecordFileCallBack",method = RequestMethod.GET)
    @ApiOperation(value = "合并直播录制文件回调函数",notes = "合并直播录制文件回调函数")
    public MergeRecordFileCallBackResopnse mergeRecordFileCallBack(
            MergeRecordFileCallBackRequest request){
        return mergeRecordFileCallBackAPI.process(request);
    }

    @ResponseBody
    @ShowLogger(info = "异步批量转存录制文件到点播")
    //@RequestMapping(value = "${xiaoYiService.startRecordCallBack}",method = RequestMethod.POST)
    @RequestMapping(path = "/xiaoyi/fileForwardSave",method = RequestMethod.POST)
    @ApiOperation(value = "异步批量转存录制文件到点播",notes = "异步批量转存录制文件到点播")
    public FileForwardSaveResponse fileForwardSave(FileForwardSaveRequest request){
        return fileForwardSaveAPI.process(request);
    }

    @ResponseBody
    @ShowLogger(info = "异步批量转存录制文件到点播回调函数")
    @RequestMapping(path = "/xiaoyi/fileForwardSaveCallBack",method = RequestMethod.GET)
    @ApiOperation(value = "异步批量转存录制文件到点播回调函数",notes = "异步批量转存录制文件到点播回调函数")
    public FileForwardSaveCallBackResopnse fileForwardSaveCallBack(
            FileForwardSaveCallBackRequest request
    ){
        return fileForwardSaveCallBackAPI.process(request);
    }

    @ResponseBody
    @ShowLogger(info = "设置录制回调通知url")
    @RequestMapping(path = "/xiaoyi/settingLiveCallBack",method = RequestMethod.GET)
    @ApiOperation(value = "设置录制回调通知url",notes = "设置录制回调通知url")
    public SettingLiveCallBackResponse settingLiveCallBack(SettingLiveCallBackRequest request){
        return settingLiveCallBackAPI.process(request);
    }

    @ResponseBody
    @ShowLogger(info = "开始录制回调函数")
    @RequestMapping(path = "/xiaoyi/startLiveCallBack",method = RequestMethod.GET)
    @ApiOperation(value = "开始录制回调函数",notes = "开始录制回调函数")
    public StartLiveCallBackRequest fileForwardSaveCallBack(StartLiveCallBackRequest request){
        return startLiveCallBackAPI.process(request);
    }

}
