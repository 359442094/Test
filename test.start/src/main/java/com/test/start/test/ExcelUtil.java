package com.test.start.test;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

    public void excelImport(){

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

    public void excelImport(String fileName){

        try {
            Workbook wb = readExcel(fileName); // 获得excel文件对象workbook

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
