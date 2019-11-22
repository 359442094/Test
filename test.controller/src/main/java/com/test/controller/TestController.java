package com.test.controller;

import com.test.common.annoation.CheckMethod;
import com.test.common.annoation.ShowLogger;
import com.test.common.dto.CheckFieldRequest;
import com.test.common.excel.ExcelUtil;
import com.test.common.util.FileUtil;
import com.test.common.util.IpUtil;
import com.test.common.util.RedisUtil;
import com.test.common.videoApi.*;
import com.test.model.domain.User;
import com.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.List;

@Slf4j
@Api(tags = {"测试视图接口"})
@Controller
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

    /**
     * 下载直播课程填写模板
     */
    @ApiOperation(value = "下载直播课程填写模板",notes = "下载直播课程填写模板")
    @ShowLogger(info = "下载直播课程填写模板")
    @RequestMapping(path = "/test/file/download/excelTemplate",method = RequestMethod.GET,produces = "application/octet-stream")
    public void downloadExcelTemplate() throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/template.xlsx";
        log.info("直播课程填写模板位置:"+rootPath + imgPath + excelTemplate);
        fileUtil.download(rootPath + imgPath + excelTemplate,"直播课程填写模板.xlsx",request,response);
    }

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @ShowLogger(info = "上传图片")
    @RequestMapping(path = "/test/file/upload/file",method = RequestMethod.POST)
    public void uploadExcelTemplate(@RequestParam(value = "file")MultipartFile file) throws IOException {
        //String read="http://120.132.68.197:8080/xy_temp/template.xlsx";
        log.info("上传图片");
        fileUtil.upload(file);
    }

    @ShowLogger(info = "下载excel")
    @ApiOperation(value = "下载excel",notes = "下载excel")
    @RequestMapping(path = "/test/testExcel",method = RequestMethod.GET)
    public void testExcel(HttpServletResponse response) throws IOException {
        List<User> users = userService.users();
        ExcelUtil excelUtil=new ExcelUtil();
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("excel.xlsx", "UTF-8"));
        excelUtil.excelExport(response.getOutputStream(),users);
    }

    @ShowLogger(info = "测试")
    @ApiOperation(value = "测试",notes = "测试")
    @RequestMapping(path = "/test/test",method = RequestMethod.GET)
    @ResponseBody
    @CheckMethod(parms = CheckFieldRequest.class)
    public CheckFieldRequest check(CheckFieldRequest request) throws IOException {
        return request;
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
