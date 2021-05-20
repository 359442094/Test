package com.test.controller.easyexcel;

import com.test.common.annoation.ShowLogger;
import com.test.common.util.EasyExcelUtil;
import groovy.util.logging.Log4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String sheetName = "模板名称";
        EasyExcelUtil.writeDownload(response,EasyUser.class,fileName,sheetName,datas);
    }

    @ShowLogger(info = "导入")
    @RequestMapping(path = "/test/easyUpload",method = RequestMethod.POST)
    @ApiOperation(value = "导入")
    @ResponseBody
    public List<EasyUser> upload(MultipartFile file) throws IOException {
        return EasyExcelUtil.read(file, EasyUser.class);
    }

}
