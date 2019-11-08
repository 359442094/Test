package com.test.start.test.util;

import java.io.*;

public class FileUtil {

    private static String readPath="D:/WindowsApache/files/case.png";
    private static String savePath="D:/case.png";

    public static void witer(){
        try{
            InputStream inputStream=new FileInputStream(readPath);
            OutputStream outputStream=new FileOutputStream(savePath);
            byte bys[]=new byte[1024];
            while (inputStream.read(bys)!=-1){
                outputStream.write(bys);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (FileNotFoundException e){
            System.out.println("该文件不存在");
        } catch (IOException e) {
            System.out.println("文件操作异常");
        }

    }

}
