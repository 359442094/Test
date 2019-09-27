package com.test.start.test;

import com.test.common.dto.Return;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //excelImport();
        //excelExport();
        test();
    }

    public static void test(){
        Return<Object> objectReturn=new Return<Object>("200","测试信息","data");
        System.out.println("objectReturn:"+objectReturn);
    }

    public static void excelImport(){
        String path = "C:\\Users\\EDZ\\Downloads\\2019.xlsx";
        ExcelUtil ex = new ExcelUtil();
        ex.excelImport(path);
    }

    public static void excelExport(){
        String title = "张翠山的发言记录";
        String[] rowsName = new String[]{"序号","时间","发言人","类型","消息"};
        List<HashMap<String, Object>> listMap = new ArrayList<>();
        List<Object[]>  dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        Calendar calendar=Calendar.getInstance();
        String format = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
        for(int i=1;i<=5;i++){
            objs = new Object[]{
                    i,format,"张翠山","文本","工作一定要认真，态度要端正"
            };
            dataList.add(objs);
        }
        ExcelUtil ex = new ExcelUtil(title, rowsName, dataList);
        ex.excelExport();
    }

}
