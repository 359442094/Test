package com.test.start.test.checkword.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 检查参数工具类
 * @author CJ
 * @date 2019/10/17
 */
@Component
@Slf4j
public class CheckUtil {

    /**
     * 检查字符串是否全部为数字
     * @param str
     * @return true:全部为数字 false:非全部为数字
     */
    public static boolean checkIsNumber(String str){
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+$");

    public static boolean checkIsChinese(String str) {
        return pattern.matcher(str).matches();
    }

    /**
     * 检查是否为空
     * @param num 统计数据
     * @return
     */
    public static int checkNumIsNull(Integer num){
        if(StringUtils.isEmpty(num)){
            return  0;
        }else {
            return num;
        }
    }
    /**
     * byte数组转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流判断图片类型
     * @return jpg/png/gif/bmp
     */
    public static boolean checkImageFile(MultipartFile teachersFile) throws IOException {
        if (!checkImage((FileInputStream) teachersFile.getInputStream())) {
            String fileName = teachersFile.getOriginalFilename();
            log.error(fileName+"图片格式错误");
            //throw new ServiceException(ExceptionConstant.EXCEPTION_FILEERROR,fileName+"文件格式非法");
            return false;
        }else{
            return true;
        }
    }

    /**
     * 根据文件流判断图片类型
     * @param fis
     * @return jpg/png/gif/bmp
     */
    public static Boolean checkImage(FileInputStream fis) {
        boolean flag=false;
        //读取文件的前几个字节来判断图片格式
        byte[] b = new byte[4];
        try {
            fis.read(b, 0, b.length);
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains("FFD8FF")) {
                flag = true;
            } else if (type.contains("89504E47")) {
                flag = true;
            } else if (type.contains("47494638")) {
                flag = true;
            } else if (type.contains("424D")) {
                flag = true;
            }else{
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 依据后缀名判断读取的是否为Excel文件
     * @param filePath
     * @return
     */
    public static  boolean checkExcel(String filePath) {
        if (filePath.matches("^.+\\.(?i)(xls)$") || filePath.matches("^.+\\.(?i)(xlsx)$")) {
            return true;
        }else{
            //throw new ServiceException(ExceptionConstant.EXCEPTION_FILEERROR,filePath+"文件格式非法");
            return false;
        }
    }

    /**
     * 判断是否是字母
     * @param str 传入字符串
     * @return 是字母返回true，否则返回false
     */
    public static boolean isAlpha(String str) {
        if(str==null) {
            return false;
        }
        return str.matches("[a-zA-Z]+");
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     * @author fenggaopan 2015年7月21日 上午9:49:40
     * @param cardNum 待检验的原始卡号
     * @return 返回是否包含
     */
    public static boolean isAlphaOne(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

}
