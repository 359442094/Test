package com.test.start.test.fileView;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * @author CJ
 * @date 2020/5/11
 */
public class JacobTest {

    public static void word2pdf(String source, String target) {
        ComThread.InitSTA();
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", source, false, true).toDispatch();
            File tofile = new File(target);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", target, 17);
            Dispatch.call(doc, "Close", false);
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (app != null) {
                app.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

    public static int Ex2PDF(String inputFile, String pdfFile) {
        // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件(生成的PDF图片糊的一塌糊涂)
        final int xlTypePDF = 0;
        ComThread.InitSTA(true);
        ActiveXComponent ax = new ActiveXComponent("Excel.Application");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        long date = calendar.getTimeInMillis();
        ax.setProperty("Visible", new Variant(false));
        ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
        try {
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.invoke(excels, "Open", Dispatch.Method, new Object[] { inputFile, new Variant(false), new Variant(false) },
                    new int[9]).toDispatch();
            File tofile = new File(pdfFile);
            if (tofile.exists()) {
                tofile.delete();
            }
            // 转换格式
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[] {
                    new Variant(0), // PDF格式=0
                    pdfFile, new Variant(xlTypePDF), Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING, Variant.VT_MISSING,
                    new Variant(false), Variant.VT_MISSING }, new int[1]);

            // 这里放弃使用SaveAs
            /*
             * Dispatch.invoke(excel,"SaveAs",Dispatch.Method,new Object[]{
             * outFile, new Variant(57), new Variant(false), new Variant(57),
             * new Variant(57), new Variant(false), new Variant(true), new
             * Variant(57), new Variant(true), new Variant(true), new
             * Variant(true) },new int[1]);
             */
            Calendar calendar2=Calendar.getInstance();
            calendar2.setTime(new Date());
            long date2 = calendar2.getTimeInMillis();
            int time = (int) ((date2 - date) / 1000);
            Dispatch.call(excel, "Close", new Variant(false));

            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (ax != null) {
                ax.invoke("Quit", new Variant[] {});
                ax = null;
            }
            ComThread.Release();
        }
    }

    /***
     * ppt转化成PDF
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    private static int ppt2PDF(String inputFile, String pdfFile) {
        try {
            ComThread.InitSTA(true);
            ActiveXComponent app = new ActiveXComponent("KWPP.Application");
//            app.setProperty("Visible", false);
            System.out.println("开始转化PPT为PDF...");
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(new Date());
            long date = calendar.getTimeInMillis();
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true, // ReadOnly
                    //    false, // Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();
            Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{
                    pdfFile,new Variant(32)},new int[1]);
            System.out.println("PPT");
            Dispatch.call(ppt, "Close");
            Calendar calendar2=Calendar.getInstance();
            calendar2.setTime(new Date());
            long date2 = calendar2.getTimeInMillis();
            int time = (int) ((date2 - date) / 1000);
            app.invoke("Quit");
            return time;
        } catch (Exception e) {
            // TODO: handle exception
            return -1;
        }
    }

    //jacob
    public static void main(String[] args) {
        //String wordFile = "C:\\phpstudy_pro\\WWW\\conver\\test3.doc";
        /*String wordFile = "C:\\\\phpstudy_pro\\\\WWW\\\\conver\\\\test3.docx";
        String pdfFile = "C:\\phpstudy_pro\\WWW\\conver\\test3.pdf";
        word2pdf(wordFile,pdfFile);*/

        //excel
        String wordFile = "C:\\phpstudy_pro\\WWW\\conver\\test.xlsx";
        String pdfFile = "C:\\phpstudy_pro\\WWW\\conver\\test3.pdf";
        int ex2PDF = Ex2PDF(wordFile,pdfFile);

        /*String wordFile = "C:\\\\phpstudy_pro\\\\WWW\\\\conver\\\\test.pptx";
        String pdfFile = "C:\\phpstudy_pro\\WWW\\conver\\test3.pdf";
        int ex2PDF = Ex2PDF(wordFile,pdfFile);*/
        System.out.println("转换成功");
    }
}
