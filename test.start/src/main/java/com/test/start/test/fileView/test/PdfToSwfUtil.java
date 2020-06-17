package com.test.start.test.fileView.test;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

import com.flagstone.transform.FSBounds;
import com.flagstone.transform.FSColorTable;
import com.flagstone.transform.FSDefineObject;
import com.flagstone.transform.FSDefineShape3;
import com.flagstone.transform.FSMovie;
import com.flagstone.transform.FSPlaceObject2;
import com.flagstone.transform.FSSetBackgroundColor;
import com.flagstone.transform.FSShowFrame;
import com.flagstone.transform.FSSolidLine;
import com.flagstone.transform.util.FSImageConstructor;
/*import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;*/

public class PdfToSwfUtil {

    public static final String SWF_FILE = "C:\\phpstudy_pro\\WWW\\2.swf";

    public static final String IMAGE_SOURCE_FILE = "C:\\phpstudy_pro\\WWW\\2.png";



    public static void main(String[] args) throws Exception {
        //String pdf = "C:\\phpstudy_pro\\WWW\\conver\\test123.pdf";
        String image = "C:\\phpstudy_pro\\WWW\\conver\\Paper\\";

        String swf = "C:\\phpstudy_pro\\WWW\\conver\\testPapeCreee9.swf";
        image2Swf(image, swf, 1f);
    }


    /**
     *      * 
     *      * @param sourceIMG 图片文件夹（多个图片），或者图片绝对路径（单个图片）
     *      * @param targetSWF 保存swf路径
     *      * @param frameRate 每张图片帧率 一般1秒1帧 0.1开始 越大越快
     *      * @throws DataFormatException
     *      * @throws IOException
     *      
     */
    public static void image2Swf(final String sourceIMG, final String targetSWF, final float frameRate)
            throws DataFormatException, IOException {
        FSMovie movie = new FSMovie();

        File file = new File(sourceIMG);
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
        movie.encodeToFile(targetSWF);
    }
}