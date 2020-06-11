package com.test.start.test.qrcode;
 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.xiaoleilu.hutool.lang.Base64;

/**
 * 生成二维码图片
 * 生成二维码二进制内容
 */
public class QRCodeGenerator {
	
	private static final String QR_CODE_IMAGE_PATH = "C:\\phpstudy_pro\\WWW\\test\\MyQRCode.png";

	// 编码格式
    private static final String CHARSET = "utf-8";

    /**
     * 返回二进制图片内容
     * 前端直接显示在 img标签的src属性中
     * @param text
     * @param width
     * @param height
     * @param filePath
     * @throws WriterException
     * @throws IOException
     */
    public static void getQrCodeViewImage(String text, int width, int height,String filePath) throws WriterException, IOException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        //生成二进制文件
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        byte[] bytes = pngOutputStream.toByteArray();

        System.out.println(new String("data:image/png;base64,"+Base64.encode(bytes)));
    }

    /**
     * 保存图片至本地
     * @param text
     * @param width
     * @param height
     * @param filePath
     * @throws WriterException
     * @throws IOException
     */
    public static void getQrCodeSaveImage(String text, int width, int height,String filePath) throws WriterException, IOException {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        //生成图片
        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        System.out.println("生成成功");
    }
	
	public static void main(String[] args) {
        try {
            getQrCodeViewImage("https://www.baidu.com/", 140, 100, QR_CODE_IMAGE_PATH);

            getQrCodeSaveImage("https://www.baidu.com/", 100, 100, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
		
	}
	
 
}