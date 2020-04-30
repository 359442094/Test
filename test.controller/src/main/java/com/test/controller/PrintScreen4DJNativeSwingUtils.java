package com.test.controller;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author CJ
 * @date 2020/4/29
 */
@SuppressWarnings(value = "all")
@Getter
@Setter
public class PrintScreen4DJNativeSwingUtils extends JPanel {

    private static final long serialVersionUID = 1L;
    // 行分隔符
     public  static final String LS = System.getProperty("line.separator", "/n");
    // 文件分割符
     public  static final String FS = System.getProperty("file.separator", "//");
    // 当网页超出目标大小时 截取
    final static public int maxWidth = 2000;
    final static public int maxHeight = 1400;

    private String url;
    private String file;
    private int width;
    private int height;

    /**
     * @param file
     *            预生成的图片全路径
     * @param url
     *            网页地址
     * @param width
     *            打开网页宽度 ，0 = 全屏
     * @param height
     *            打开网页高度 ，0 = 全屏
     * @return boolean
     */
    public PrintScreen4DJNativeSwingUtils(String file,String url,int x,int y,int width,int height,String withResult) throws InterruptedException {
        super(new BorderLayout());
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        final JWebBrowser webBrowser = new JWebBrowser(null);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        Thread.sleep(5000);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout());
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            // 监听加载进度
            @Override
            public void loadingProgressChanged(WebBrowserEvent e) {
                // 当加载完毕时
                if (e.getWebBrowser().getLoadingProgress() == 100) {
                    String result = (String) webBrowser.executeJavascriptWithResult(withResult);
                    int index = result == null ? -1 : result.indexOf(":");
                    NativeComponent nativeComponent = webBrowser.getNativeComponent();
                    Dimension originalSize = nativeComponent.getSize();
                    Dimension imageSize = new Dimension(Integer.parseInt(result.substring(0, index)),
                            Integer.parseInt(result.substring(index + 1)));
                    imageSize.width = Math.max(originalSize.width, imageSize.width + 50);
                    imageSize.height = Math.max(originalSize.height, imageSize.height + 50);
                    nativeComponent.setSize(imageSize);
                    BufferedImage image = new BufferedImage(imageSize.width, imageSize.height,
                            BufferedImage.TYPE_INT_RGB);
                    nativeComponent.paintComponent(image);
                    nativeComponent.setSize(originalSize);
                    // 截图部分图形
                    if(width !=0 && height!=0){
                        imageSize.width=width;
                        imageSize.height=height;
                    }
                    System.out.println("imageSize.width:"+imageSize.width);
                    System.out.println("imageSize.height:"+imageSize.height);
                    image = image.getSubimage(x,y, imageSize.width, imageSize.height);
                    try {
                        // 输出图像
                        ImageIO.write(image, "png", new File(file));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    // 退出操作
                    System.exit(0);
                }
            }
        });
        add(panel, BorderLayout.SOUTH);
    }

    // 以javascript脚本获得网页全屏后大小
    public static String getScreenWidthHeight() {
        StringBuffer jsDimension = new StringBuffer();
        jsDimension.append("var width = 0;").append(LS);
        jsDimension.append("var height = 0;").append(LS);
        jsDimension.append("if(document.documentElement) {").append(LS);
        jsDimension.append("  width = Math.max(width, document.documentElement.scrollWidth);").append(LS);
        jsDimension.append("  height = Math.max(height, document.documentElement.scrollHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(self.innerWidth) {").append(LS);
        jsDimension.append("  width = Math.max(width, self.innerWidth);").append(LS);
        jsDimension.append("  height = Math.max(height, self.innerHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(document.body.scrollWidth) {").append(LS);
        jsDimension.append("  width = Math.max(width, document.body.scrollWidth);").append(LS);
        jsDimension.append("  height = Math.max(height, document.body.scrollHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("return width + ':' + height;");
        return jsDimension.toString();
    }

    public static boolean printUrlScreen2jpg(String file,String url,int x,int y,int width,int height) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                String withResult = "var width = " + width + ";var height = " + height + ";return width +':' + height;";
                if (width == 0 || height == 0){
                    withResult = getScreenWidthHeight();
                }
                JFrame frame = new JFrame("网页截图");
                // 加载指定页面，最大保存为640x480的截图
                frame.getContentPane().add(new PrintScreen4DJNativeSwingUtils(file,url,x,y,width,height,withResult),
                        BorderLayout.CENTER);
                frame.setSize(640, 480);
                // 仅初始化，但不显示
                frame.invalidate();

                frame.pack();
                frame.setVisible(false);
            }
        });
        NativeInterface.runEventPump();

        return true;
    }
}
