package com.test.common.excel;

import com.test.model.domain.User;
import org.apache.commons.lang.time.DateFormatUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TestExcel {
    public static void main(String[] args) throws FileNotFoundException {
        //excelImport();
        //excelExport(excelExport);
        test();
    }

    public static void test() throws FileNotFoundException {
        String path = "D:\\WindowsApache\\files\\studentData.xlsx";
        ExcelUtil ex = new ExcelUtil();
        ex.excelImportStudents(path);
    }

    public static void excelImport() throws FileNotFoundException {


        //InputStream inputStream=new FileInputStream(path);

    }



}
