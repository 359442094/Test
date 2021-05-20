package com.test.controller.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class EasyUser implements Serializable {

    private static final long serialVersionUID = 509539254519838528L;

    @ColumnWidth(value = 17)
    @ExcelProperty(value = "字符串标题")
    private String string;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "日期标题",format = "yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(value = 20)
    private Date date;
    @ExcelProperty(value = "数字标题")
    @ColumnWidth(value = 17)
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

}
