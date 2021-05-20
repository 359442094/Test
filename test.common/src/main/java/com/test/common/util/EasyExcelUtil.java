package com.test.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * easyexcel 封装工具类
 * @author chenjie
 * @version 1.0
 * @date 2021/5/20 17:12
 */
public class EasyExcelUtil {

    public static void writeDownload(HttpServletResponse response, Class myClass, String fileName,String sheetName,List datas) throws IOException {
        // 这里注意 使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        EasyExcel.write(response.getOutputStream(),myClass).sheet(sheetName).doWrite(datas);
    }

    public static List read(MultipartFile file,Class myClass) throws IOException {
        return EasyExcel.read(file.getInputStream(), myClass, new SyncReadListener()).sheet().doReadSync();
    }

}
