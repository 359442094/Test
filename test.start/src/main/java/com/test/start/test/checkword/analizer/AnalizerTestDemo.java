//package com.test.start.test.checkword.analizer;
//
//import com.test.start.test.checkword.analizer.ik.IKAnalyzer4Lucene7;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.TokenStream;
////import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//
//import java.io.IOException;
//
//public class AnalizerTestDemo {
//
//    private static void doToken(TokenStream tokenStream) throws IOException {
//        tokenStream.reset();
//        CharTermAttribute cta = tokenStream.getAttribute(CharTermAttribute.class);
//        while(tokenStream.incrementToken()){
//            System.out.print(cta.toString() + "|");
//        }
//        System.out.println();
//        tokenStream.end();
//        tokenStream.close();
//    }
//
//    public static void main(String[] args) {
//        String etext = "Analysis is one of the main causes of slow indexing. Simply put, the more you analyze the slower analyze the indexing (in most cases).";
//        String chineseText = "111 测试敏感词1加V";
//
//        //标准分词器
//        try (Analyzer analyzer = new StandardAnalyzer();){
//            TokenStream tokenStream = analyzer.tokenStream("content",etext);
//            System.out.println("标准分词器，英文分词器效果：");
//            doToken(tokenStream);
//            tokenStream = analyzer.tokenStream("content", chineseText);
//            System.out.println("标准分词器，中文分词效果：");
//            doToken(tokenStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // smart中文分词器
//        /*try (Analyzer smart = new SmartChineseAnalyzer()) {
//            TokenStream tokenStream = smart.tokenStream("content", etext);
//            System.out.println("smart中文分词器，英文分词效果：");
//            doToken(tokenStream);
//            tokenStream = smart.tokenStream("content", chineseText);
//            System.out.println("smart中文分词器，中文分词效果：");
//            doToken(tokenStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//
//        // IKAnalyzer 细粒度切分
//        try (Analyzer analyzer = new IKAnalyzer4Lucene7();){
//            TokenStream tokenStream = analyzer.tokenStream("content",etext);
//            System.out.println("IKAnalyzer中文分词器 细粒度切分，英文分词效果：");
//            doToken(tokenStream);
//            tokenStream = analyzer.tokenStream("content",chineseText);
//            System.out.println("IKAnalyzer中文分词器 细粒度切分，中文分词效果：");
//            doToken(tokenStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //IKAnalyzer 智能切分
//        try(Analyzer analyzer = new IKAnalyzer4Lucene7(true);){
//            TokenStream tokenStream = analyzer.tokenStream("content",etext);
//            System.out.println("IKAnalyzer中文分词器 智能切分，英文分词效果：");
//            doToken(tokenStream);
//            tokenStream = analyzer.tokenStream("content",chineseText);
//            System.out.println("IKAnalyzer中文分词器 智能切分，中文分词效果：");
//            doToken(tokenStream);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//}
