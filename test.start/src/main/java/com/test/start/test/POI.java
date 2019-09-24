package com.test.start.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class POI {

    public static Workbook readExcel(String fileName){
        Workbook wb = null;
        if(fileName==null){
            return null;
        }
        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static void startRead(String fileName){

        try {
            Workbook wb = readExcel(fileName); // 获得excel文件对象workbook

            Sheet s = wb.getSheetAt(0); // 获取指定工作表<这里获取的是第一个>

            Cell cell = s.getRow(1).getCell(1);
            String kcName="";
            if(!StringUtils.isEmpty(cell)){
                kcName = cell.toString();
            }
            System.out.println(kcName);
            /*
            //循环行  sheet.getPhysicalNumberOfRows()是获取表格的总行数
            for (int i = 0; i < s.getPhysicalNumberOfRows(); i++) {
                System.out.println("第"+(i+1)+"行内容:");

                Row row = s.getRow(i); // 取出第i行  getRow(index) 获取第(index+1)行

                for (int j = 0;j < row.getPhysicalNumberOfCells(); j++) {  // getPhysicalNumberOfCells() 获取当前行的总列数
                    String value1 = getCellFormatValue(row.getCell(j)); //getCell 获取单元格中的内容
                    System.out.print(value1+"");
                }

                System.out.println("");
            }
            */
        } catch (IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getCellFormatValue(Cell cell){
        String cellValue = "";
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }
        return cellValue;
    }

    public static void main(String[] args) {
        startRead("C:\\Users\\EDZ\\Downloads\\2019.xlsx");
    }
}
