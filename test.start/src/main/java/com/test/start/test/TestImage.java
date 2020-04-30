package com.test.start.test;

import com.test.controller.PrintScreen4DJNativeSwingUtils;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * @author CJ
 * @date 2020/4/29
 */
public class TestImage {

    public static void test2(){
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String htmlstr="<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title></title>\n" +
                "\t\t<!--<link rel=\"stylesheet\" href=\"http://localhost/test/link_img_download.css\" />-->\n" +
                "\t\t<script type=\"application/javascript\">\n" +
                "\t\t\t\n" +
                "\t\t\twindow.onload=function(){\n" +
                "\t\t\t\tvar code = document.getElementById(\"left_container\");\n" +
                "\t\t\t\t//获取x、y\n" +
                "\t\t\t\tvar obj= getAbsPosition(code);\n" +
                "\t\t\t\t//获取高度\n" +
                "\t\t\t\tvar height = document.getElementById(\"left_container\").offsetHeight;\n" +
                "\t\t\t\t//获取宽度\n" +
                "\t\t\t\tvar width = document.getElementById(\"left_container\").offsetWidth;\n" +
                "\t\t\t\tconsole.info(obj);\n" +
                "\t\t\t\tconsole.info(\"height:\"+height);\n" +
                "\t\t\t\tconsole.info(\"width:\"+width);\n" +
                "\t\t\t\t//window.alert(\"x:\"+obj.x+\"\\ty:\"+obj.y);\n" +
                "\t\t\t}\n" +
                "\t\t\t//获取元素绝对位置\n" +
                "function getAbsPosition(element)\n" +
                "{\n" +
                "    var abs={x:0,y:0}\n" +
                "\n" +
                "    //如果浏览器兼容此方法\n" +
                "    if (document.documentElement.getBoundingClientRect) \n" +
                "    {             \n" +
                "        //注意，getBoundingClientRect()是jQuery对象的方法\n" +
                "        //如果不用jQuery对象，可以使用else分支。\n" +
                "        abs.x = element.getBoundingClientRect().left;         \n" +
                "        abs.y = element.getBoundingClientRect().top;\n" +
                "\n" +
                "        abs.x += window.screenLeft +  \n" +
                "                    document.documentElement.scrollLeft -            \n" +
                "                    document.documentElement.clientLeft;\n" +
                "        abs.y += window.screenTop +  \n" +
                "                    document.documentElement.scrollTop -  \n" +
                "                    document.documentElement.clientTop;\n" +
                "\n" +
                "    } \n" +
                "\n" +
                "    //如果浏览器不兼容此方法\n" +
                "    else\n" +
                "    {\n" +
                "        while(element!=document.body)\n" +
                "        {\n" +
                "            abs.x+=element.offsetLeft;\n" +
                "            abs.y+=element.offsetTop;\n" +
                "            element=element.offsetParent;\n" +
                "        }\n" +
                "\n" +
                "     //计算想对位置\n" +
                "     abs.x += window.screenLeft + \n" +
                "            document.body.clientLeft - document.body.scrollLeft;\n" +
                "     abs.y += window.screenTop + \n" +
                "            document.body.clientTop - document.body.scrollTop;\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    return abs;\n" +
                "}\n" +
                "\t\t</script>\n" +
                "\t</head>\n" +
                "\n" +
                "\t<body style=\"display: block;margin: 8px;\">\n" +
                "\t<div id=\"left_container\">\n" +
                "            \n" +
                "    <div class=\"linkImgDownLoad\" style=\"position:fixed;\n" +
                "    top:0px;\n" +
                "    left:0px;\n" +
                "    background:  #27274A;\n" +
                "    width:375px;\n" +
                "    height:600px;\n" +
                "    display:inline-block;\n" +
                "    z-index:-100;\">\n" +
                "        <div class=\"content\" style=\"background: #FFFFFF;\n" +
                "    border-radius: 6px;\n" +
                "    width:305px;\n" +
                "    height:514px;\n" +
                "    margin:40px 35px;\">\n" +
                "            <div class=\"logo\" style=\"position: absolute;\n" +
                "    left: 0;\n" +
                "    right: 0;\n" +
                "    margin: auto;\n" +
                "    top: 62px;\n" +
                "    text-align: center;\">\n" +
                "            \t<img style=\" max-height: 50px;\" class=\"icon-logo logo-ksx\" src=\"http://localhost/test/logo.png\">\n" +
                "            </div>\n" +
                "            <div class=\"title currentThemeBackgroundColor\" style=\"width:335px;\n" +
                "    height:127px;\n" +
                "    background: #1A8CFE;\n" +
                "    position:absolute;\n" +
                "    top:118px;\n" +
                "    left:20px;\">\n" +
                "                <div class=\"title_container\" style=\" position: relative;\n" +
                "    top: 50%;\n" +
                "    left: 50%;\n" +
                "    transform: translate(-50%, -50%);\n" +
                "    text-align: center;\">\n" +
                "                    <div class=\"invite\" style=\" opacity: 0.8;\n" +
                "    font-size: 16px;\n" +
                "    color: #FFFFFF;\"><div class=\"white_line\"></div>邀请你参加<span class=\"activity_type\">考试</span><div class=\"white_line\"></div></div>\n" +
                "                    <div class=\"title_content\" style=\" font-size: 18px;\n" +
                "    color: #FFFFFF;\n" +
                "    font-weight:bold;\n" +
                "    max-width: 235px;\n" +
                "    display: inline-block;\n" +
                "    margin-top:15px;\">考试示例</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"time\" style=\"position:absolute;;\n" +
                "    top:264px;\n" +
                "    left:70px;background: #F7F8FA;\n" +
                "    border-radius: 2px;\n" +
                "    font-size: 10px;\n" +
                "    color:  #656577;\n" +
                "    width:235px;\n" +
                "    height:20px;\n" +
                "    margin-bottom:6px;\n" +
                "    padding-left: 32px;\n" +
                "    line-height: 20px;\">\n" +
                "                <div class=\"start_time\">开始时间：<span>2019-07-03 09:36:10</span></div>\n" +
                "                <div class=\"end_time\">结束时间：<span>2019-07-06 09:36:10</span></div>\n" +
                "                <div class=\"exam_time\">考试时长：<span style=\"color: red;\">60分钟</span></div>\n" +
                "            </div>\n" +
                "            <!--<div class=\"qr_code\">\n" +
                "                <div class=\"code_img\"><canvas width=\"150\" height=\"150\"></canvas></div>\n" +
                "                <div class=\"tip\">识别二维码参加<span class=\"activity_type\">考试</span></div>\n" +
                "            </div>-->\n" +
                "        </div>\n" +
                "        <!--<div class=\"half_circle left\"></div>\n" +
                "        <div class=\"half_circle right\"></div>\n" +
                "        <div class=\"left_triangle\"></div>\n" +
                "        <div class=\"right_triangle\"></div>-->\n" +
                "    </div>\n" +
                "\n" +
                "        </div>\n" +
                "\t</body>\n" +
                "\t\n" +
                "</html>\n";

        imageGenerator.loadHtml(htmlstr);
        //imageGenerator.loadUrl("http://www.icbc.com.cn/icbc/");
        /*Dimension dimension=new Dimension(305,514);
        imageGenerator.setSize(dimension);*/
        BufferedImage bufferedImage = getGrayPicture(imageGenerator.getBufferedImage());
        imageGenerator.saveAsImage("C:/phpstudy_pro/WWW/files/test.png");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            String base64Img = Base64.encodeBase64String(outputStream.toByteArray());
            String res = "data:image/jpg;base64," + base64Img;
            System.out.println("res:"+res);
            //modelAndView.addObject("imageres", res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void test1() throws InterruptedException {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        String htmlstr = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title></title>\n" +
                "\t\t<!--<link rel=\"stylesheet\" href=\"http://localhost/test/link_img_download.css\" />-->\n" +
                "\t\t\n" +
                "\t</head>\n" +
                "\n" +
                "\t<body style=\"display: block;margin: 8px;\">\n" +
                "\t<div class=\"left_container\" style=\"width: 1px;\">\n" +
                "            \n" +
                "    <div class=\"linkImgDownLoad\" style=\"position:fixed;\n" +
                "    top:0px;\n" +
                "    left:0px;\n" +
                "    background:  #27274A;\n" +
                "    width:375px;\n" +
                "    height:600px;\n" +
                "    display:inline-block;\n" +
                "    z-index:-100;\">\n" +
                "        <div class=\"content\" style=\"background: #FFFFFF;\n" +
                "    border-radius: 6px;\n" +
                "    width:305px;\n" +
                "    height:514px;\n" +
                "    margin:40px 35px;\">\n" +
                "            <div class=\"logo\" style=\"position: absolute;\n" +
                "    left: 0;\n" +
                "    right: 0;\n" +
                "    margin: auto;\n" +
                "    top: 62px;\n" +
                "    text-align: center;\">\n" +
                "            \t<img style=\" max-height: 50px;\" class=\"icon-logo logo-ksx\" src=\"http://localhost/test/logo.png\">\n" +
                "            </div>\n" +
                "            <div class=\"title currentThemeBackgroundColor\" style=\"width:335px;\n" +
                "    height:127px;\n" +
                "    background: #1A8CFE;\n" +
                "    position:absolute;\n" +
                "    top:118px;\n" +
                "    left:20px;\">\n" +
                "                <div class=\"title_container\" style=\" position: relative;\n" +
                "    top: 50%;\n" +
                "    left: 50%;\n" +
                "    transform: translate(-50%, -50%);\n" +
                "    text-align: center;\">\n" +
                "                    <div class=\"invite\" style=\" opacity: 0.8;\n" +
                "    font-size: 16px;\n" +
                "    color: #FFFFFF;\"><div class=\"white_line\"></div>邀请你参加<span class=\"activity_type\">考试</span><div class=\"white_line\"></div></div>\n" +
                "                    <div class=\"title_content\" style=\" font-size: 18px;\n" +
                "    color: #FFFFFF;\n" +
                "    font-weight:bold;\n" +
                "    max-width: 235px;\n" +
                "    display: inline-block;\n" +
                "    margin-top:15px;\">考试示例</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"time\" style=\"position:absolute;;\n" +
                "    top:264px;\n" +
                "    left:70px;background: #F7F8FA;\n" +
                "    border-radius: 2px;\n" +
                "    font-size: 10px;\n" +
                "    color:  #656577;\n" +
                "    width:235px;\n" +
                "    height:20px;\n" +
                "    margin-bottom:6px;\n" +
                "    padding-left: 32px;\n" +
                "    line-height: 20px;\">\n" +
                "                <div class=\"start_time\">开始时间：<span>2019-07-03 09:36:10</span></div>\n" +
                "                <div class=\"end_time\">结束时间：<span>2019-07-06 09:36:10</span></div>\n" +
                "                <div class=\"exam_time\">考试时长：<span style=\"color: red;\">60分钟</span></div>\n" +
                "            </div>\n" +
                "            <!--<div class=\"qr_code\">\n" +
                "                <div class=\"code_img\"><canvas width=\"150\" height=\"150\"></canvas></div>\n" +
                "                <div class=\"tip\">识别二维码参加<span class=\"activity_type\">考试</span></div>\n" +
                "            </div>-->\n" +
                "        </div>\n" +
                "        <!--<div class=\"half_circle left\"></div>\n" +
                "        <div class=\"half_circle right\"></div>\n" +
                "        <div class=\"left_triangle\"></div>\n" +
                "        <div class=\"right_triangle\"></div>-->\n" +
                "    </div>\n" +
                "\n" +
                "        </div>\n" +
                "\t</body>\n" +
                "\t\n" +
                "</html>\n";
        // 加载html源码内容
        /*String htmlstr = "<table style=\"width: 700px;font-size:16px;font-family: 'Microsoft YaHei';\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "    <tr >\n" +
                "      <th  style=\"background-color: #f2f2f2;height: 30px;width:  700px; border:1px solid #e6e6e6;border-top:none;text-align: center;color: #333333;\" colspan=\"2\">收据</th>\n" +
                "    </tr>\n" +
                "    <tr style=\"width:  700px;\">\n" +
                "      <td style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;height: 30px;\" >日期</td>\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;border-left: 0px;height: 30px;\">"+new Date()+"</td>\n" +
                "    </tr>\n" +
                "    <tr style=\"width:  700px;\">\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;height: 30px;\">交易编号</td>\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;border-left: 0px;height: 30px;\">1</td>\n" +
                "    </tr>\n" +
                "    <tr style=\"width:  700px;\">\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;height: 30px;\">交易类型</td>\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;border-left: 0px;height: 30px;\">捐赠</td>\n" +
                "    </tr>\n" +
                "    <tr style=\"width:  700px;\">\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;height: 30px;\">交易金额</td>\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;border-left: 0px;height: 30px;\">100</td>\n" +
                "    </tr>\n" +
                "   <tr style=\"width:  700px;\">\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;height: 30px;\">付款人</td>\n" +
                "      <td  style=\"border:1px solid #e6e6e6;border-top:none;text-align: center;color: #666666;border-left: 0px;height: 30px;\">cj</td>\n" +
                "    </tr>" +
                "</table>";*/
        imageGenerator.loadHtml(htmlstr);
        //imageGenerator.loadUrl("http://localhost:8020/test/TestImage.html?__hbt=1588131411437");
        // 生成图片字符流
        //Thread.sleep(5000);
        imageGenerator.getBufferedImage();
        // 保存到本地
        imageGenerator.saveAsImage("C:/phpstudy_pro/WWW/files/test.png");

        //把图片转成网页，只是简单的img 引用
        imageGenerator.saveAsHtmlWithMap("C:/phpstudy_pro/WWW/files/test.html", "C:/phpstudy_pro/WWW/files/testdownload.png");
        System.out.println("生成成功");
    }

    public static void main(String[] args) throws Exception {
        //test2();
        test1();
        //test3();
        //test4();
    }

    public static void test4(){

    }

    public static void test3(){
        String path="C:/phpstudy_pro/WWW/files/test.png";
        String url="http://192.168.0.139/test/%E5%BD%92%E6%A1%A3/%E8%80%83%E8%AF%95%E4%BA%91%E5%B9%B3%E5%8F%B0.html";
        int x=0;
        int y=0;
        int w=600;
        int h=375;
        PrintScreen4DJNativeSwingUtils.printUrlScreen2jpg(path, url,x,y,w, h);
    }

    public static BufferedImage getGrayPicture(BufferedImage originalImage)
    {
        BufferedImage grayPicture;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();

        grayPicture = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        ColorConvertOp cco = new ColorConvertOp(ColorSpace
                .getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(originalImage, grayPicture);
        return grayPicture;
    }
}
