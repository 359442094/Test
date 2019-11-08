package com.test.start.test;

import com.test.start.test.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLEncoder;
@Slf4j
public class TestFile {
    public static void main(String[] args) throws IOException {
        String readPath = "D:\\WindowsApache\\files\\student.xlsx";
        OutputStream outputStream=new FileOutputStream("D:\\student.xlsx");
        try{
            InputStream inputStream=new FileInputStream(readPath);
            byte bys[]=new byte[1024];
            while (inputStream.read(bys)!=-1){
                outputStream.write(bys);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (FileNotFoundException e){
            log.error("该文件不存在 readPath:"+readPath);
            //throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"该文件不存在 readPath:"+readPath);
        } catch (IOException e) {
            log.error("文件操作异常 readPath:"+readPath);
            //throw new ServiceException(ExceptionConstant.EXCEPTION_FileError,"文件操作异常 readPath:"+readPath);
        }

    }
}
