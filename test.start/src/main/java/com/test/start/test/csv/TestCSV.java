package com.test.start.test.csv;
 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TestCSV {
    /**
     * 测试输出到csv
     * @throws Exception
     */
    public void testWrite() throws Exception {
        FileOutputStream fos = new FileOutputStream("/Users/weijinhui/Downloads/test.csv");
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
 
        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡", "性别");
        CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
 
        // 第二种方式设置头部信息
//        csvPrinter = CSVFormat.DEFAULT.withHeader("姓名", "年龄", "家乡", "性别").print(osw);
 
        for (int i = 0; i < 10; i++) {
            csvPrinter.printRecord("张三", 20, "上海", "男");
        }
        csvPrinter.flush();
        csvPrinter.close();
    }

    public static void main(String[] args) throws IOException {
        testRead();
    }

    /**
     * 测试读取
     * @throws IOException
     */
    public static int testRead() throws IOException {
        InputStream is = new FileInputStream("C:\\Users\\chenjie\\Desktop\\test.csv");
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        Reader reader = new BufferedReader(isr);
        CSVParser parser = CSVFormat.EXCEL.withHeader("name").parse(reader);
        List<String> users=new ArrayList<>();
        List<CSVRecord> list = parser.getRecords();
        for (CSVRecord record : list) {
            System.out.println(record.getRecordNumber()
                    + ":" + new String(record.get("name")));
            if(record.getRecordNumber()==9){
                users.add(record.get("name"));
            }
        }
        users = users.stream().distinct().collect(Collectors.toList());
        parser.close();
        return users.size();
    }
 
    /**
     * 分析Excel csv文件
     */
    public void testParse() throws Exception {
        Reader reader = new FileReader("/Users/weijinhui/Downloads/test.csv");
        CSVParser parser = CSVFormat.EXCEL.parse(reader);
        for (CSVRecord record : parser.getRecords()) {
            System.out.println(record);
        }
        parser.close();
    }
 
    /**
     * 手动定义标题
     */
    public void testParseWithHeader() throws Exception {
        Reader reader = new FileReader("/Users/weijinhui/Downloads/test.csv");
        CSVParser parser = CSVFormat.EXCEL.withHeader("id", "name", "code", "gender").parse(reader);
        for (CSVRecord record : parser.getRecords()) {
            System.out.println(record.get("id") + ","
                    + record.get("name") + ","
                    + record.get("code")
                    + record.get("gender"));
        }
        parser.close();
    }
 
    /**
     * 使用枚举定义头
     */
    enum MyHeaderEnum {
        ID, NAME, CODE, GENDER;
    }
 
    /**
     * 测试使用枚举定义头部
     * @throws Exception
     */
    public void testParseWithEnum() throws Exception {
        Reader reader = new FileReader("/Users/weijinhui/Downloads/test.csv");
        CSVParser parser = CSVFormat.EXCEL.withHeader(MyHeaderEnum.class).parse(reader);
        for (CSVRecord record : parser.getRecords()) {
            System.out.println(record.get(MyHeaderEnum.ID) + ","
                    + record.get(MyHeaderEnum.NAME) + ","
                    + record.get(MyHeaderEnum.CODE) + ","
                    + record.get(MyHeaderEnum.GENDER));
        }
        parser.close();
    }
 
 
    private List<Map<String, String>> recordList = new ArrayList<>();
 
    /**
     * 初始化list
     */
    public void init() {
        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "zhangsan");
            map.put("code", "001");
            recordList.add(map);
        }
    }
 
    /**
     * 测试使用线程生成多个文件，模拟导出
     * @throws InterruptedException
     */
    public void writeMuti() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch doneSignal = new CountDownLatch(2);
 
        executorService.submit(new exprotThread("/Users/weijinhui/Downloads/0.csv", recordList, doneSignal));
        executorService.submit(new exprotThread("/Users/weijinhui/Downloads/1.csv", recordList, doneSignal));
 
        doneSignal.await();
        System.out.println("Finish!!!");
    }
 
    /**
     * 定义导出线程
     */
    class exprotThread implements Runnable {
 
        private String filename;
        private List<Map<String, String>> list;
        private CountDownLatch countDownLatch;
 
        public exprotThread(String filename, List<Map<String, String>> list, CountDownLatch countDownLatch) {
            this.filename = filename;
            this.list = list;
            this.countDownLatch = countDownLatch;
        }
 
        @Override
        public void run() {
            try {
                CSVPrinter printer = new CSVPrinter(new FileWriter(filename), CSVFormat.EXCEL.withHeader("NAME", "CODE"));
                for (Map<String, String> map : list) {
                    printer.printRecord(map.values());
                }
                printer.close();
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 测试写100万数据需要花费多长时间
     */
    /*public void testMillion() throws Exception {
        int times = 10000 * 10;
        Object[] cells = {"满100减15元", "100011", 15};
 
        //  导出为CSV文件
        long t1 = System.currentTimeMillis();
        FileWriter writer = new FileWriter("/Users/weijinhui/Downloads/test1.csv");
        CSVPrinter printer = CSVFormat.EXCEL.print(writer);
        for (int i = 0; i < times; i++) {
            printer.printRecord(cells);
        }
        printer.flush();
        printer.close();
        long t2 = System.currentTimeMillis();
        System.out.println("CSV: " + (t2 - t1));
 
        //  导出为Excel文件
        long t3 = System.currentTimeMillis();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i < times; i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < cells.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(String.valueOf(cells[j]));
            }
        }
        FileOutputStream fos = new FileOutputStream("/Users/weijinhui/Downloads/test2.xlsx");
        workbook.write(fos);
        fos.flush();
        fos.close();
        long t4 = System.currentTimeMillis();
        System.out.println("Excel: " + (t4 - t3));
    }*/
}