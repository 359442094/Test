package com.test.start.test.fileView.test;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;
 
/**
 * pdf文件转图片(icepdf技术)
 */
public class PDF2IMGTest {
	public static void main(String[] args) {
		String filePath = "C:\\phpstudy_pro\\WWW\\conver\\test123.pdf";
		Document document = new Document();
 
		try {
			document.setFile(filePath);
			float scale = 1.5f;// 缩放比例（大图）
			//float scale =2.0f;
			//float scale = 0.2f;// 缩放比例（小图）
			float rotation = 0f;// 旋转角度
			for (int i = 0; i < document.getNumberOfPages(); i++) {
				BufferedImage image = (BufferedImage) document.getPageImage(i,
						GraphicsRenderingHints.SCREEN,
						org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX,
						rotation, scale);
                BufferedImage bufferedImage = getImage(image);
                //RenderedImage rendImage = image;
                RenderedImage rendImage = bufferedImage;
				try {
					File file = new File("C:\\phpstudy_pro\\WWW\\conver\\Paper\\" + i + ".jpg");
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
		System.out.println("======================完成============================");
	}

	public static BufferedImage getImage(BufferedImage image) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image
				.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        return bufferedImage;
	}


	public static void saveImage(BufferedImage image, String format,
								 String filePath) {
		try {

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}