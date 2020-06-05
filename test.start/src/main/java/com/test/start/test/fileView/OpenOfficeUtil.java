package com.test.start.test.fileView;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author CJ
 * @date 2020/5/11
 */
@Slf4j
public class OpenOfficeUtil {

    /**
     * 根据环境执行windows或者linux的启动OpenOffice服务命令
     *
     * @param active
     * @throws IOException
     */
    public static void openService(String active, String openOfficeService) {
        try {
            Process pro = null;

            if ("dev".equals(active)) {
                //执行window 启动OpenOffice服务命令
                //String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
                pro = Runtime.getRuntime().exec(openOfficeService);
            } else {
                //执行linux 启动OpenOffice服务命令
                //String commandStr = "/opt/openoffice4/program/soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
                String[] cmd = new String[]{"/bin/sh", "-c", openOfficeService};
                pro = Runtime.getRuntime().exec(cmd);
            }
            log.info("启动OpenOffice服务成功");

            if (!StringUtils.isEmpty(pro)) {
                pro.waitFor();
                pro.exitValue();
            }

        } catch (Exception e) {
            log.error("启动OpenOffice服务失败:" + e.getMessage());
            throw new RuntimeException("启动OpenOffice服务失败:" + e.getMessage());
        }

    }

    public static void stopService(String active){
        try {
            Process process = null;
            //显示进程
            if ("dev".equals(active)) {
                //执行window 关闭OpenOffice服务命令
                process=Runtime.getRuntime().exec("tasklist");
                Scanner in=new Scanner(process.getInputStream());
                while (in.hasNextLine()) {
                    String processString=in.nextLine();
                    if (processString.contains("soffice.exe")) {
                        //关闭soffice进程的命令
                        String cmd="taskkill /f /im soffice.exe";
                        process = Runtime.getRuntime().exec(cmd);
                        log.info("openoffice正常关闭.......");
                    }
                }
            }else{
                //执行linux 关闭OpenOffice服务命令
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * xls、ppt、doc文件转换成PDF文件
     * (只支持xls、ppt、doc)
     * 任意文件转任意文件(暂时只转pdf)
     *
     * @param sourceFile 源文件位置
     * @param outFile    转换后的文件位置
     */
    public static void FileConverPDF(String sourceFile, String outFile) {

        File SourceFile = new File(sourceFile);
        File HtmlFile = new File(outFile);

        /*
         * 转换成pdf文件
         * 项目执行需要启动OpenOffice服务，在系统命令窗口执行命令。(windows)
         *  cd C:\Program Files (x86)\OpenOffice.org 4\program
         *  soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
         */
        if (SourceFile.exists()) {
            //if(!HtmlFile.exists()) {
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
            try {
                connection.connect();
                DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                converter.convert(SourceFile, HtmlFile);
                HtmlFile.createNewFile();
                connection.disconnect();
                log.info("转换成功,路径:[" + HtmlFile.getPath() + "]");
            } catch (java.net.ConnectException e) {
                log.error("OpenOffice服务未启动");
                e.printStackTrace();
                throw new RuntimeException("OpenOffice服务未启动:"+e);
            } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                log.error("读取文件失败");
                e.printStackTrace();
                throw new RuntimeException("读取文件失败:"+e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            /*} else {
                System.out.println("已转换为HTML，无需再次转换");
            }*/
        } else {
            log.info("要转换的文件不存在 sourceFile:"+sourceFile);
            throw new RuntimeException("要转换的文件不存在 sourceFile:"+sourceFile);
        }
    }

    /**
     * 根据环境执行windows或者linux的SWFTools转换命令
     * PDF文件转换SWF文件
     *
     * @param active       dev:开发环境
     *                     sit:测试环境
     *                     prod:正式环境
     * @param swfToolsHome SWFTools在系统中的安装目录
     * @param pdfPath      PDF文件的路径
     * @param swfPath      SWF文件的路径
     */
    public static void PDFConverSWF(String active, String swfToolsFile,String swfToolsHome, String pdfPath, String swfPath) {
        try {
            /**
             * SWFTools_HOME在系统中的安装目录
             * 1：window需要指定到 pdf2swf.exe 文件
             * 2：linux则/usr/local/swftools-0.9.1/src/pdf2swf即可
             */
            Process pro = null;
            if ("dev".equals(active)) {
                //执行window pdf转换swf命令
                String[] cmd = new String[7];
                cmd[0] = swfToolsHome;
                cmd[1] = "-i";
                cmd[2] = pdfPath;
                cmd[3] = "-o";
                cmd[4] = swfPath;
                //指定flash版本
                cmd[5] = "-s flashversion=9";
                //没有此命令的话 生成的swf文件不能打印成功
                cmd[6] = "-T 9";
                //log.info("windows执行pdf转换swf命令:"+cmd.toString());
                pro = Runtime.getRuntime().exec(cmd);
            } else {
                //linux SWFTools中文工具包
                //String languagedir = "/usr/share/xpdf/xpdf-chinese-simplified";
                String languagedir = swfToolsFile;
                //执行linux pdf转换swf命令
                String commandStr = swfToolsHome + " -o " + swfPath
                        + " -T -z -t -f " + pdfPath + " -s languagedir="
                        + languagedir + " -s flashversion=9";
                log.info("linux执行pdf转换swf命令:" + commandStr);
                String[] cmd = new String[]{"/bin/sh", "-c", commandStr};
                pro = Runtime.getRuntime().exec(cmd);
            }
            if (!StringUtils.isEmpty(pro)) {
                pro.waitFor();
                pro.exitValue();
            }
        } catch (Exception e) {
            log.error("pdf转换swf失败" + e.getMessage());
            throw new RuntimeException("pdf转换swf失败" + e.getMessage());
        }
    }

}
