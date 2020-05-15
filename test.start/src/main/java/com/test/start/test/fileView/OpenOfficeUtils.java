package com.test.start.test.fileView;

import java.io.File;
import java.net.ConnectException;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

/**
 * 任意文件转化为html
 * 
 * @author Mr.F
 * 
 */
public class OpenOfficeUtils {

    /**
     * (只支持xls、ppt、doc)
     * 任意文件转任意文件
     * */
    public  static void FileConverPDF(String sourceFile, String outFile) {

        File SourceFile = new File(sourceFile);
        File HtmlFile = new File(outFile);

        /*
         * 转换成pdf文件
         * 项目执行需要启动OpenOffice服务，在系统命令窗口执行命令。
         *  cd C:\Program Files (x86)\OpenOffice.org 4\program
         *  soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
         */
        if(SourceFile.exists()) {
            //if(!HtmlFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                try {
                    connection.connect();
                    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                    HtmlFile.createNewFile();
                    converter.convert(SourceFile, HtmlFile);
                    connection.disconnect();  
                    System.out.println("第二步：转换为HTML格式   路径" + HtmlFile.getPath());
                } catch (java.net.ConnectException e) {
                //} catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("OpenOffice服务未启动");
                    try {
                        throw e;
                    } catch (ConnectException e1) {
                        e1.printStackTrace();
                    }
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("读取文件失败");
                    throw e;
                } catch (Exception e){
                    e.printStackTrace();
                    try {
                        throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            /*} else {
                System.out.println("已转换为HTML，无需再次转换");
            }*/
        } else {
            System.out.println("要转换的文件不存在");
        }
    }

    public static void PDFConverWSF(String pdfPath,String wsfPath) {
        try {
            /**
             * SWFTools_HOME在系统中的安装目录
             * 1：window需要指定到 pdf2swf.exe 文件
             * 2：linux则xxx/xxx/xxx/pdf2swf即可
             */
            String SWFTools_HOME ="C:\\Program Files (x86)\\SWFTools\\pdf2swf.exe";
            String[] cmd = new String[7];
            cmd[0] = SWFTools_HOME;
            cmd[1] = "-i";
            cmd[2] = pdfPath;
            cmd[3] = "-o";
            cmd[4] = wsfPath;
            cmd[5] = "-s flashversion=9";
            //没有此命令的话 生成的swf文件不能打印成功
            cmd[6] = "-T 9";
            Process pro =Runtime.getRuntime().exec(cmd);
            pro.waitFor();
            pro.exitValue();
        } catch (Exception e) {
            System.out.println("pdf转换swf失败"+e.getMessage());
            throw new RuntimeException("pdf转换swf失败"+e.getMessage());
        }
    }

    public static void main(String[] args) {
        /*long currentTimeMillis = System.currentTimeMillis();
        String sourcePath="C:\\phpstudy_pro\\WWW\\upload\\test.xls";
        String outPath="C:\\phpstudy_pro\\WWW\\conver\\testOpenOffice.pdf";
        FileConverPDF(sourcePath, outPath);*/

        String sourcePath="C:\\phpstudy_pro\\WWW\\conver\\Paper.pdf";
        String outPath="C:\\phpstudy_pro\\WWW\\conver\\Paper1.swf";
        PDFConverWSF(sourcePath, outPath);
    }
}
