package com.test.controller;

import com.test.common.annoation.ShowLogger;
import com.test.common.util.ExcelUtil;
import com.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author CJ
 * @date 2020/1/2
 */
@Slf4j
@Api(tags = {"测试视图接口"})
@Controller
public class ExcelController {

    @Autowired
    private UserService userService;

    /*@ShowLogger(info = "下载excel")
    @ApiOperation(value = "下载excel",notes = "下载excel")
    @RequestMapping(path = "/test/testMyExcel",method = RequestMethod.GET)
    public void testExcel(HttpServletResponse response) throws IOException {
        List<Test> tests = userService.tests();
        ExcelUtil excelUtil=new ExcelUtil();
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("excel.xlsx", "UTF-8"));
        excelUtil.excelExport(response.getOutputStream(),tests);
    }*/

    @ShowLogger(info = "下载easyexcel")
    @ApiOperation(value = "下载easyexcel",notes = "下载easyexcel")
    @RequestMapping(value = "/test/exportExcel", method = RequestMethod.GET)
    //@ResponseBody
    public void downTemplate(HttpServletResponse response) {
        List<NewLeads> leadsList = new ArrayList<>();
        NewLeads newLeads=new NewLeads();
        newLeads.setCreateTime(new Date());
        newLeads.setOrgName("zs");
        newLeads.setOrgId("123");
        leadsList.add(newLeads);
        //导出操作
        ExcelUtil.exportExcel(leadsList,"花名册","草帽一伙",NewLeads.class,"海贼王.xls",response);
    }

    @ShowLogger(info = "读取easyexcel")
    @ApiOperation(value = "读取easyexcel",notes = "读取easyexcel")
    @RequestMapping(value = "/test/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public Object importUser(@RequestParam MultipartFile file){
        //String filePath = "F:\\海贼王.xls";
        //本地方式:解析excel，
        //List<NewLeads> personList = ExcelUtil.importExcel(filePath,1,1,NewLeads.class);
        //file文件导入方式: 也可以使用MultipartFile
        List<NewLeads> newLeads = ExcelUtil.importExcel(file, 1, 1, NewLeads.class);
        log.info("导入数据一共【"+newLeads.size()+"】行");
        return newLeads;
    }

}
