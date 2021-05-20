package com.test.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.test.common.annoation.ShowLogger;
import com.test.common.util.ExcelUtil;
import groovy.util.logging.Log4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.helper.DataUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.standard.expression.EachUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j
@Api(tags = "excel处理")
@Controller
public class ExcelController {

    private List<EasyUser> data() {
        List<EasyUser> list = new ArrayList<EasyUser>();
        for (int i = 0; i < 10; i++) {
            EasyUser data = new EasyUser();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @RequestMapping(path = "/test/easyDownload",method = RequestMethod.GET)
    @ApiOperation(value = "下载")
    @ShowLogger(info = "下载")
    public void download(HttpServletResponse response) throws IOException {
        List<EasyUser> datas = data();
        String fileName = "easy.xlsx";
        // 这里注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        EasyExcel.write(response.getOutputStream(), EasyUser.class).sheet("模板").doWrite(datas);
    }

    @ShowLogger(info = "导入")
    @RequestMapping(path = "/test/easyUpload",method = RequestMethod.POST)
    @ApiOperation(value = "导入")
    @ResponseBody
    public Object upload(MultipartFile file) throws IOException {
        List<Object> objects = EasyExcel.read(file.getInputStream(), EasyUser.class, new SyncReadListener()).sheet().doReadSync();
        return objects;
    }

}
