package com.test.common.excel;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.test.model.domain.Test;
import com.test.model.domain.User;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public void startExport(OutputStream outputStream,List<Object[]> dataList){
        try{
            // 创建工作簿对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 创建工作表
            HSSFSheet sheet = workbook.createSheet(title);
            // 加载数据
            autoContent(dataList,workbook,sheet);
            //指定列自动适应
            // 写入
            String savePath = "D:/";
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString() +".xls";
            saveWorkbook(outputStream,workbook,savePath,fileName);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void excelImportStudents(String fileName){

        try {
            Workbook wb = readExcel(fileName); // 获得excel文件对象workbook
            Sheet sheet = wb.getSheetAt(0); // 获取指定工作表<这里获取的是第一个>
            Row row;
            Cell cell=null;
            // 循环输出表格中的内容
            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if(!isRowEmpty(row)){
                    if(!StringUtils.isEmpty(row.getCell(1))){
                        System.out.print("姓名"+row.getCell(1).getStringCellValue() + "\t");
                    }
                    if(!StringUtils.isEmpty(row.getCell(3))){
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        //student.setStudentPhone();
                        DecimalFormat df = new DecimalFormat("#");
                        //System.out.print("手机:"+df.format(row.getCell(3).getNumericCellValue()));
                        System.out.println("row.getCell(3).getStringCellValue():"+row.getCell(3).getStringCellValue());
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isRowEmpty(Row row){
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
                return false;
            }
        }
        return true;
    }

   /* public Workbook readExcel(String fileName){
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

    public XiaoYiCourse excelImport(String fileName,InputStream inputStream){

        try {
            XiaoYiCourse course=new XiaoYiCourse();
            Workbook wb = readExcel(fileName,inputStream); // 获得excel文件对象workbook
            Sheet s1 = wb.getSheetAt(0); // 获取指定工作表<这里获取的是第一个>
            course.setCourseName(getValue(s1,1,1));
            //定价
            course.setPrice(new BigDecimal(getValue(s1,1,4)));
            //课时数
            course.setClassHour(getValue(s1,1,6));
            //课程开始时间
            course.setStartDate(getDateValue(s1, 1, 7));
            //课程结束时间
            course.setEndDate(getDateValue(s1, 1, 8));
            //机构介绍
            course.setTeacherInfo(getValue(s1,2,1));
            DecimalFormat df = new DecimalFormat("#");
            Cell value = s1.getRow(3).getCell(1);
            //账号
            course.setAccount(df.format(value.getNumericCellValue()));
            //教师姓名
            course.setTeacherName(getValue(s1,3,4));
            //课程有效期
            course.setExpirationDuration(getValue(s1,3,5));
            //课程亮点1
            //课程亮点2
            //课程亮点3
            String totalFeature = getValue(s1,4,2) +"|" + getValue(s1,4,4) +"|" + getValue(s1,4,6);
            course.setCourseFeature(totalFeature);
            //难度
            course.setClassDifficult(getValue(s1,5,2));
            //适合年级
            course.setClassInfo(getValue(s1,6,2));
            //学习目标
            course.setCourseObj(getValue(s1,7,2));
            //配套讲义
            course.setTextBook(getValue(s1,8,2));
            //学习内容
            course.setCourseContent(getValue(s1,9,1));
            List<XiaoYiCourseDetail> evidenceDetail=new ArrayList<>();
            XiaoYiCourseDetail detail=null;
            //遍历行row
            for(int rowNum = 13; rowNum<=s1.getLastRowNum();rowNum++){
                //获取每一行
                Row row = s1.getRow(rowNum);
                if(row == null){
                    continue;
                }
                detail=new XiaoYiCourseDetail();
                Cell cell1 = s1.getRow(rowNum).getCell(0);
                //课节数:"+getValue(s1,rowNum,0));
                if(!StringUtils.isEmpty(cell1.getNumericCellValue())){
                    detail.setLessonNum((int) cell1.getNumericCellValue());
                }
                //课节名称:"+getValue(s1,rowNum,1));
                if(!StringUtils.isEmpty(getValue(s1,rowNum,1))){
                    detail.setLessonName(getValue(s1,rowNum,1));
                }
                Cell cell = s1.getRow(rowNum).getCell(3);
                //开始日期:"+cell);
                if(!StringUtils.isEmpty(cell.getDateCellValue())){
                    detail.setLessonDate(cell.getDateCellValue());
                }
                //开始时间:"+getDateValue(s1,rowNum,4));
                if(!StringUtils.isEmpty(getDateValue(s1,rowNum,4))){
                    detail.setStartTime(getDateValue(s1,rowNum,4));
                }
                //结束时间:"+getDateValue(s1,rowNum,6));
                if(!StringUtils.isEmpty(getDateValue(s1,rowNum,6))){
                    detail.setEndTime(getDateValue(s1,rowNum,6));
                }
                if(!StringUtils.isEmpty(detail.getLessonName())&&detail.getLessonDate()!=null&&detail.getStartTime()!=null&&detail.getEndTime()!=null){
                    //detail:"+detail);
                    evidenceDetail.add(detail);
                }
            }

            course.setXiaoYiCourseDetails(evidenceDetail);
            return course;
        } catch (IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
*/
    public Date getDateValue(Sheet s, int row, int cell){
        Cell value = s.getRow(row).getCell(cell);
        if (value.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(value)) {
                return value.getDateCellValue();
            }
        }
        return null;
    }

    public void excelExport(OutputStream outputStream,List<Test> users){
        String title = "张翠山的发言记录";
        String[] rowsName = new String[]{"序号","时间","用户","备注"};
        List<Object[]>  dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        Calendar calendar=Calendar.getInstance();
        String format = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
        for (int i = 0; i < users.size(); i++) {
            objs = new Object[]{
                    i,users.get(i).getName(),users.get(i).getPwd(),"工作一定要认真，态度要端正"
            };
            dataList.add(objs);
        }
        ExcelUtil ex = new ExcelUtil(title, rowsName, dataList);
        ex.startExport(outputStream,dataList);
    }

    public String getValue(Sheet s, int row, int cell){
        Cell value = s.getRow(row).getCell(cell);
        if(!StringUtils.isEmpty(value)){
            return value.toString();
        }
        return null;
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
                return wb = WorkbookFactory.create(is);//new XSSFWorkbook(is);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public Workbook readExcel(String fileName, InputStream inputStream){
        Workbook wb = null;
        if(fileName==null){
            return null;
        }
        String extString = fileName.substring(fileName.lastIndexOf("."));
        try {
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(inputStream);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(inputStream);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public void autoContent(List<Object[]> dataList,HSSFWorkbook workbook, HSSFSheet sheet){
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
            HSSFCell cellRowName = rowRowName.createCell(n);                //创建列头对应个数的单元格
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
                HSSFCell cell = null;   //设置单元格的数据类型
                if(j == 0){
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(i+1);

                }else{
                    cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        //设置单元格的值
                        cell.setCellValue(obj[j].toString());
                    }
                }
                // 必须在单元格设值以后进行
                // 调整每一列宽度
                sheet.autoSizeColumn((short) j);
                // 解决自动设置列宽中文失效的问题
                sheet.setColumnWidth(j, sheet.getColumnWidth(j) * 17 / 10);

                //设置单元格样式
                cell.setCellStyle(this.getStyle(workbook));
            }
        }
    }

    /**
     * 写入文件
     * @param workbook 工作薄对象
     * @param savePath 保存地址
     * @param fileName 文件名称
     * */
    public void saveWorkbook(OutputStream out,HSSFWorkbook workbook, String savePath, String fileName){
        if(workbook !=null){
            try
            {
                //FileOutputStream out = new FileOutputStream(savePath+fileName);
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
    public void autoColumnStyle(HSSFSheet sheet, int size){
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                System.out.println("rowNum:"+rowNum);
                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        System.out.println("currentCell.getStringCellValue():"+currentCell.getStringCellValue());
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            System.out.println("columnNum："+columnNum+"\tcolumnWidth:"+columnWidth);
            sheet.setColumnWidth(columnNum, columnWidth * 256);
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
