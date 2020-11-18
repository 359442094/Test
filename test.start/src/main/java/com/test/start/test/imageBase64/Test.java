package com.test.start.test.imageBase64;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Objects;

/**
 * @author chenjie
 * @date 2020-09-23
 */
public class Test {

    /**
     * 生成指定路径的base64
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream("D:\\WindowsApache\\images\\test_front.png");
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        Writer file=new FileWriter("D:\\WindowsApache\\images\\base64.txt");
        file.write(encoder.encode(Objects.requireNonNull(data)));
        file.flush();
        file.close();
        //System.out.println(encoder.encode(Objects.requireNonNull(data)));
    }

}
