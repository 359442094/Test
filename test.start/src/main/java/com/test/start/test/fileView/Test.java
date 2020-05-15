package com.test.start.test.fileView;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import java.io.*;

/**
 * @author CJ
 * @date 2020/5/11
 */
public class Test {
    // 将word格式的文件转换为pdf格式
    public static void Word2Pdf(String srcPath, String desPath) {
        OpenOfficeConnection connection = null;
        Process p = null;
        try {
            // 源文件目录
            File inputFile = new File(srcPath);
            if (!inputFile.exists()) {
                System.out.println("源文件不存在！");
                return;
            }
            // 输出文件目录
            File outputFile = new File(desPath);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().exists();
            }
            /*// 调用openoffice服务线程

            String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
            p = Runtime.getRuntime().exec(command);

            // 连接openoffice服务
            connection = new SocketOpenOfficeConnection(
                    "127.0.0.1", 8100);
            connection.connect();*/

            // 转换word到pdf
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);

            System.out.println("转换完成！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                // 关闭连接
                connection.disconnect();
            }
            if (p != null) {
                // 关闭进程
                p.destroy();
            }
        }

    }

    public static void main(String[] args) {

        String srcPath = "C:"+File.separatorChar+"phpstudy_pro"+File.separatorChar+"WWW"+File.separatorChar+"conver"+File.separatorChar+"test.xls";
        String desPath = "C:\\phpstudy_pro\\WWW\\conver\\test.pdf";
        Word2Pdf(srcPath, desPath);
    }

}
