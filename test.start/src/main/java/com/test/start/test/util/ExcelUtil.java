package com.test.start.test.util;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.test.start.test.XiaoYiCourse;
import com.test.start.test.XiaoYiCourseDetail;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

public class ExcelUtil {

    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName ;

    private List<Object[]>  dataList = new ArrayList<Object[]>();

    public ExcelUtil(){}

    //构造方法，传入要导出的数据
    public ExcelUtil(String title, String[] rowName, List<Object[]>  dataList){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
    }

    /*
     * 导出数据
     * */
    public void excelExport(){
        try{
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建工作表
            HSSFSheet sheet = workbook.createSheet(title);
            // 加载数据
            autoContent(workbook,sheet);
            // 让列宽随着导出的列长自动适应
            autoColumnStyle(sheet,rowName.length);
            // 写入
            String savePath = "D:/";
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls";
            saveWorkbook(workbook,savePath,fileName);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public XiaoYiCourse excelImport(String fileName){

        try {
            XiaoYiCourse course=new XiaoYiCourse();
            Workbook wb = readExcel(fileName); // 获得excel文件对象workbook
            Sheet s1 = wb.getSheetAt(0); // 获取指定工作表<这里获取的是第一个>
            course.setCourseName(getValue(s1,1,1));
            //System.out.println("kcname:"+getValue(s1,1,1));
            System.out.println("定假:"+getValue(s1,1,4));
            course.setPrice(new BigDecimal(getValue(s1,1,4)));
            System.out.println("课时数:"+getValue(s1,1,6));
            course.setClassHour(getValue(s1,1,6));
            System.out.println("课程开始时间:"+getValue(s1,1,7));
            course.setStartDate(getDateValue(s1, 1, 7));
            System.out.println("课程结束时间:"+getValue(s1,1,8));
            course.setEndDate(getDateValue(s1, 1, 8));
            System.out.println("机构介绍:"+getValue(s1,2,1));
            course.setTeacherInfo(getValue(s1,2,1));
            DecimalFormat df = new DecimalFormat("#");
            Cell value = s1.getRow(3).getCell(1);
            System.out.println("账号:"+df.format(value.getNumericCellValue()));
            course.setAccount(df.format(value.getNumericCellValue()));
            System.out.println("教师姓名:"+getValue(s1,3,4));
            course.setTeacherName(getValue(s1,3,4));
            System.out.println("课程有效期:"+getValue(s1,3,5));
            course.setExpirationDuration(getValue(s1,3,5));
            System.out.println("课程亮点1:"+getValue(s1,4,2));
            System.out.println("课程亮点2:"+getValue(s1,4,4));
            System.out.println("课程亮点3:"+getValue(s1,4,6));
            String totalFeature = getValue(s1,4,2) +"|" + getValue(s1,4,4) +"|" + getValue(s1,4,6);
            course.setCourseFeature(totalFeature);
            System.out.println("难度:"+getValue(s1,5,2));
            course.setClassDifficult(getValue(s1,5,2));
            System.out.println("适合年级:"+getValue(s1,6,2));
            course.setClassInfo(getValue(s1,6,2));
            System.out.println("学习目标:"+getValue(s1,7,2));
            course.setCourseObj(getValue(s1,7,2));
            System.out.println("配套讲义:"+getValue(s1,8,2));
            course.setTextBook(getValue(s1,8,2));
            System.out.println("学习内容:"+getValue(s1,9,1));
            course.setCourseContent(getValue(s1,9,1));
            List<XiaoYiCourseDetail> details=new ArrayList<>();
            XiaoYiCourseDetail detail=null;
            //遍历行row
            for(int rowNum = 13; rowNum<=s1.getLastRowNum();rowNum++){
                //获取每一行
                Row row = s1.getRow(rowNum);
                if(row == null){
                    continue;
                }
                detail=new XiaoYiCourseDetail();
                System.out.print("课节数:"+getValue(s1,rowNum,0));
                System.out.print("课节名称:"+getValue(s1,rowNum,1));
                if(!StringUtils.isEmpty(getValue(s1,rowNum,1))){
                    detail.setLessonName(getValue(s1,rowNum,1));
                }
                Cell cell = s1.getRow(rowNum).getCell(3);
                System.out.print("开始日期:"+cell);
                if(!StringUtils.isEmpty(cell.getDateCellValue())){
                    detail.setLessonDate(cell.getDateCellValue());
                }
                System.out.print("开始时间:"+getDateValue(s1,rowNum,4));
                if(!StringUtils.isEmpty(getDateValue(s1,rowNum,4))){
                    detail.setStartTime(getDateValue(s1,rowNum,4));
                }
                System.out.println("结束时间:"+getDateValue(s1,rowNum,6));
                if(!StringUtils.isEmpty(getDateValue(s1,rowNum,6))){
                    detail.setEndTime(getDateValue(s1,rowNum,6));
                }
                if(!StringUtils.isEmpty(detail.getLessonName())&&detail.getLessonDate()!=null&&detail.getStartTime()!=null&&detail.getEndTime()!=null){
                    System.out.println("detail:"+detail);
                    details.add(detail);
                }
            }

            course.setXiaoYiCourseDetails(details);
            return course;
        } catch (IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Date getDateValue(Sheet s,int row,int cell){
        Cell value = s.getRow(row).getCell(cell);
        if (value.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(value)) {
                return value.getDateCellValue();
            }
        }/*else if(value.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING){
            return getValue(s,row,cell);
        }*/
        return null;
    }

    public String getValue(Sheet s,int row,int cell){
        Cell value = s.getRow(row).getCell(cell);
        if(!StringUtils.isEmpty(value)){
            return value.toString();
        }
        return null;
    }

    public void excelImport(InputStream inputStream){

        try {

            Workbook wb = readExcel(inputStream); // 获得excel文件对象workbook

            Sheet s = wb.getSheetAt(0); // 获取指定工作表<这里获取的是第一个>
            //获取标题
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

    public String getCellFormatValue(Cell cell){
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

    public Workbook readExcel(InputStream inputStream){
        Workbook wb = null;
        try {
            return new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public Workbook readExcel(String fileName){
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

    public void autoContent(HSSFWorkbook workbook,HSSFSheet sheet){
        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);
        rowm.setHeight((short) (25 * 35)); //设置高度

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (rowName.length-1)));
        cellTiltle.setCellStyle(this.getColumnTopStyle(workbook));
        cellTiltle.setCellValue(title);
        HSSFRow rowRowName = sheet.createRow(1);                // 在索引2的位置创建行(最顶端的行开始的第二行)

        rowRowName.setHeight((short) (25 * 25)); //设置高度

        // 将列头设置到sheet的单元格中
        for(int n=0;n<this.rowName.length;n++){
            HSSFCell  cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
            cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);                //设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
            cellRowName.setCellValue(text);                                    //设置列头单元格的值
            cellRowName.setCellStyle(this.getColumnTopStyle(workbook));                        //设置列头单元格样式
        }

        //将查询出的数据设置到sheet对应的单元格中
        for(int i=0;i<dataList.size();i++){

            Object[] obj = dataList.get(i);//遍历每个对象
            HSSFRow row = sheet.createRow(i+2);//创建所需的行数

            row.setHeight((short) (25 * 20)); //设置高度

            for(int j=0; j<obj.length; j++){
                HSSFCell  cell = null;   //设置单元格的数据类型
                if(j == 0){
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(i+1);
                }else{
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        cell.setCellValue(obj[j].toString());                        //设置单元格的值
                    }
                }
                cell.setCellStyle(this.getStyle(workbook));                                    //设置单元格样式
            }
        }
    }

    /**
     * 写入文件
     * @param workbook 工作薄对象
     * @param savePath 保存地址
     * @param fileName 文件名称
     * */
    public void saveWorkbook(HSSFWorkbook workbook,String savePath,String fileName){
        if(workbook !=null){
            try
            {
                FileOutputStream out = new FileOutputStream(savePath+fileName);
                workbook.write(out);
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 让列宽随着导出的列长自动适应
     * @param sheet 工作表对象
     * @param columnNum 工作表列头数组长度
     * */
    public void autoColumnStyle(HSSFSheet sheet,int columnNum){
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if(colNum == 0){
                sheet.setColumnWidth(colNum, (columnWidth-2) * 128);
            }else{
                sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
            }
        }
    }

    /**
     * 列头单元格样式
     * @param workbook 工作薄对象
     * */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        //设置单元格背景颜色
        style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        return style;

    }

    /**
     * 列数据信息单元格样式
     * @param workbook 工作薄对象
     * */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;
    }
}
