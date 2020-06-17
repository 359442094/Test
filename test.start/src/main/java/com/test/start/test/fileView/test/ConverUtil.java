package com.test.start.test.fileView.test;

import com.flagstone.transform.*;
import com.flagstone.transform.util.FSImageConstructor;
import lombok.extern.slf4j.Slf4j;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * 1.pdf文件转换多个图片
 * 2.图片转SWF
 * @author CJ
 * @date 2020/6/16
 */
@Slf4j
public class ConverUtil {

    /**
     * pdf文件转换多个图片
     * @param pdfPath pdf文件路径
     * @param savePath 保存的图片文件夹 如:C:\phpstudy_pro\WWW\conver\Paper\
     */
    public static void pdf2images(String pdfPath,String savePath){
        log.info("converUtil pdf2images start");
        Document document = new Document();
        try {
            document.setFile(pdfPath);
            // 缩放比例（大图）
            float scale = 2.5f;
            // 缩放比例（小图）
            // float scale = 0.2f;
            // 旋转角度
            float rotation = 0f;
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = (BufferedImage) document.getPageImage(i,
                        GraphicsRenderingHints.SCREEN,
                        org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX,
                        rotation, scale);
                RenderedImage rendImage = image;
                try {
                    File imageFile=new File(savePath);
                    if(!imageFile.exists()){
                        imageFile.mkdir();
                    }
                    File file = new File(savePath + i + ".jpg");
                    // 这里png作用是：格式是jpg但有png清晰度
                    ImageIO.write(rendImage, "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image.flush();
            }
            document.dispose();
        } catch (PDFException e1) {
            e1.printStackTrace();
        } catch (PDFSecurityException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("converUtil pdf2images end");
    }

    /**
     * 图片转SWF
     * @param imagePath 图片所在地址(因为包含多个)
     * @param swfPath 保存的swf文件路径
     * @param frameRate 每张图片帧率 一般1秒1帧 0.1开始 越大越快
     * @throws Exception
     */
    public static void images2Swf(String imagePath,String swfPath,float frameRate) throws Exception {
        log.info("converUtil image2Swf start");
        FSMovie movie = new FSMovie();

        File file = new File(imagePath);
        int i = 1;
        for (File f : file.listFiles()) {
            //获取图片基本属性
            FSImageConstructor imageGenerator = new FSImageConstructor(f.getAbsolutePath());

            //获取图片和画布id
            int imageId = movie.newIdentifier();

            int shapeId = movie.newIdentifier();

            //获取到图片格式
            FSDefineObject image = imageGenerator.defineImage(imageId);

            image.setIdentifier(imageId);

            imageGenerator.defineImage(imageId);
            //加入图片
            movie.add(image);
            //设置swf画布样式、位置
            FSDefineShape3 shape = imageGenerator.defineEnclosingShape
                    //x ,y       设置画布边框大小颜色
                            (shapeId, imageId, 0, 0, new FSSolidLine(10, FSColorTable.white()));
            //加入swf模型
            movie.add(shape);
            //得到画布
            FSBounds bounds = shape.getBounds();
            //设置画布到容器
            movie.setFrameSize(bounds);
            //设置每张图片1秒一帧
            movie.setFrameRate(frameRate);
            //设置容器背景颜色
            movie.add(new FSSetBackgroundColor(FSColorTable.white()));

            //在每一帧上添加一个图片,并且设置上下距离为0
            movie.add(new FSPlaceObject2(shapeId, i, 0, 0));
            //显示动画
            movie.add(new FSShowFrame());
            i += 2;
        }
        //在每一帧上添加一个图片,并且设置上下距离为0，这个地方需要在Flash中最后多添加一帧加入空白帧，否则显示不正常。
        movie.add(new FSPlaceObject2(-1, i, 0, 0));
        //输出路径
        movie.encodeToFile(swfPath);
        log.info("converUtil image2Swf end");
    }

}
