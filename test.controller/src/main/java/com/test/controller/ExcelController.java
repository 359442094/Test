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
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @RequestMapping(value = "/test/testExcel", method = RequestMethod.GET)
    @ResponseBody
    public String downTemplate(HttpServletResponse response) {
        List<NewLeads> leadsList = new ArrayList<>();
        NewLeads newLeads=new NewLeads();
        newLeads.setCreateTime(new Date());
        newLeads.setName("zs");
        newLeads.setPhone("15211134400");
        leadsList.add(newLeads);

        ExcelUtil<NewLeads> util = new ExcelUtil<>(NewLeads.class);
        return util.exportExcel(leadsList, "leads数据");
    }

}
