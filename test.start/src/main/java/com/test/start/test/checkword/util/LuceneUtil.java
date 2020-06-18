package com.test.start.test.checkword.util;

import com.test.start.test.checkword.analizer.ik.IKAnalyzer4Lucene7;
import com.test.start.test.checkword.constant.LuceneConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全文检索工具类
 */
@Slf4j
@Component
@Getter
@Setter
public class LuceneUtil {

    private static final Logger logger = LoggerFactory.getLogger(LuceneUtil.class);

    private static final Analyzer analyzer=new IKAnalyzer4Lucene7();

    private static LuceneUtil luceneUtil;

    @Value(value = "${luceneService.indexPath}")
    private String lucenePath;

    @PostConstruct
    public void setLuceneUtil(){
        luceneUtil=this;
    }

    public static void main(String[] args) throws Exception {
        Query query = doQuery(LuceneConstant.LUCENE_FIELD_NAME,"加1");
        int count = LuceneUtil.doKeySearch("C:\\lucene\\defaultIndex", query, 10000);
        System.out.println(count);
    }

    public static List<String> processWords(List<String> words){
        //包含字母都转大写处理
        List<String> upperCaseWords=new ArrayList<>();
        for (String word : words) {
            if(CheckUtil.isAlpha(word)){
                upperCaseWords.add(word.toUpperCase());
                upperCaseWords.add(word.toLowerCase());
            }else if(CheckUtil.isAlphaOne(word)){
                String[] split = word.split("");
                for (String s : split) {
                    if (!StringUtils.isEmpty(s) && s != null && s.matches("(?i)[^a-z]*[a-z]+[^a-z]*")) {
                        // 有字母
                        upperCaseWords.add(word.replaceAll(s, s.toUpperCase()));
                    }
                }
            }else{
                upperCaseWords.add(word);
            }
        }
        //添加之后的词语去重
        return upperCaseWords.stream().distinct().collect(Collectors.toList());

    }

    /**
     * 开始更新/写入数据库敏感词索引
     * @param words 数据库中敏感词库
     */
    public static void startWiter(List<String> words) throws Exception {
        witerKeyIndex(luceneUtil.lucenePath,words,analyzer);
    }

    /**
     * 开始对比索引中的敏感词
     * @param word 用户输入的文字
     * @return true:未含有敏感词 false:含有敏感词
     * @throws ParseException
     */
    public static boolean startSearch(String word){
        if(!StringUtils.isEmpty(word)){
            //不可以匹配到中文
            //Query query = doQuery(LuceneConstant.LUCENE_FIELD_NAME+":"+word, analyzer);
            //可以匹配到中文
            Query query = doQuery(LuceneConstant.LUCENE_FIELD_NAME,word);
            int count = LuceneUtil.doKeySearch(luceneUtil.lucenePath, query, 10000);
            if(count>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 写入敏感词内容信息
     * */
    public static void witerKeyIndex(String witerPath,List<String> words,Analyzer analyzer) throws Exception {
        List<Document> documents = getFundDocument(words);
        witerIndex(witerPath,analyzer,documents,true);
    }

    /**
     * 写入敏感词索引
     * */
    public static void witerIndex(String witerPath,Analyzer analyzer,List<Document> documents,Boolean emptyFlag) throws IOException {
        // 创建IndexWriter
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        // 指定索引库的地址
        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(witerPath));
        IndexWriter writer = new IndexWriter(directory, cfg);
        //为true清除之前的索引
        if(emptyFlag){
            log.info("清空上次遗留的索引");
            writer.deleteAll();
        }
        // 通过IndexWriter对象将Document写入到索引库中
        for (Document document : documents) {
            writer.addDocument(document);
        }
        writer.commit();
        // 关闭writer
        writer.close();
        logger.info("更新敏感词索引成功");
    }

    /**
     * 敏感分词域Document
     * */
    public static List<Document> getFundDocument(List<String> words){
        List<Document> documents = new ArrayList<>();
        //创建索引
        for (String word:words) {
            Document document = new Document();
            Field key=new StringField(LuceneConstant.LUCENE_FIELD_NAME,word,Field.Store.YES);
            document.add(key);
            documents.add(document);
        }
        return documents;
    }

    /**
     * 设置权重、模糊查询、不可以中文检索
     */
    public static Query doQuery(String query, Analyzer analyzer) throws ParseException {
        //设置权重
        Map<String, Float> boosts = new HashMap<String, Float>();
        boosts.put("key",4.0f);              //关键词
        //使用QueryParser搜索时，需要指定分词器，搜索时的分词器要和索引时的分词器一致
        //指定搜索的域的名称
        //MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(Version.LUCENE_CURRENT, new String[]{"name", "code"}, analyzer, boosts);
        QueryParser queryParser = new MultiFieldQueryParser(new String[]{"key"}, analyzer, boosts);
        //返回
        return queryParser.parse(query);
    }

    /**
     * 中文检索
     * @param fieldName
     * @param query
     * @return
     */
    public static Query doQuery(String fieldName, String query){
        return new TermQuery(new Term(fieldName, query));
    }

    /**
     * 搜索基金索引中的内容
     */
    public static List<String> doFundSearch(String readPath, Query query,int pageIndex, int pageSize) {
        List<String> words = new ArrayList<>();
        try {
            // 1、创建Directory
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(readPath));
            // 指定索引库的地址
            IndexReader reader = DirectoryReader.open(directory);
            // 创建IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);
            // 通过searcher来搜索索引库
            // 第二个参数：指定需要显示的顶部记录的N条
            TopDocs topDocs = searcher.search(query, 1000);
            // 根据查询条件匹配出的记录
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            // 根据查询条件匹配出的记录总数
            int count =(int)topDocs.totalHits;
            logger.debug("匹配出的记录总数:" + count);
            int pages= count%pageSize==0?count/pageSize:count/pageSize+1;
            logger.debug("匹配出的总页数:" + pages);
            //分页获取索引内容
            words = doPageFundIndexContent(searcher,scoreDocs,pageIndex,pageSize);
            //关闭资源
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    /**
     * 分页获取敏感词索引内容
     * */
    public static List<String> doPageFundIndexContent(IndexSearcher searcher,ScoreDoc[] scoreDocs,int pageIndex,int pageSize) throws IOException {
        List<String> words=new ArrayList<>();
        logger.debug("匹配出的基金信息");
        //查询起始记录位置
        int begin = pageSize * (pageIndex - 1) ;

        //查询终止记录位置
        int end = Math.min(begin + pageSize, scoreDocs.length);

        //进行分页查询
        for(int i=begin;i<end;i++) {
            // 获取文档的ID
            int docID = scoreDocs[i].doc;
            // 通过ID获取文档
            Document doc = searcher.doc(docID);
            logger.debug("敏感词:" + doc.get("key"));
            words.add(doc.get("key"));
        }

        return words;
    }


    /**
     * 搜索敏感词索引中的内容
     */
    public static int doKeySearch(String readPath, Query query,int resultNumber) {
        int count=0;
        //List<String> keys=new ArrayList<>();
        try {
            // 1、创建Directory
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(readPath));
            // 指定索引库的地址
            IndexReader reader = DirectoryReader.open(directory);
            // 创建IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);
            // 通过searcher来搜索索引库
            // 第二个参数：指定需要显示的顶部记录的N条
            TopDocs topDocs = searcher.search(query, resultNumber);

            // 根据查询条件匹配出的记录总数
            count = (int) topDocs.totalHits;
            /*
            // 根据查询条件匹配出的记录
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //获取索引内容
            keys = doKeyIndexContent(searcher, scoreDocs);
            */

            // 关闭资源
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 关键词索引内容
     * */
    public static List<String> doKeyIndexContent(IndexSearcher searcher,ScoreDoc[] scoreDocs) throws IOException {
        List<String> keys=new ArrayList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取文档的ID
            int docId = scoreDoc.doc;
            // 通过ID获取文档
            Document doc = searcher.doc(docId);
            keys.add(doc.get(LuceneConstant.LUCENE_FIELD_NAME));
        }
        return keys;

    }

    /**
     * 去除List中重复的关键词
     * */
    public static List<String> removeKeyDuplicate(List<String> keys) {
        HashSet h = new HashSet(keys);
        keys.clear();
        keys.addAll(h);
        return keys;
    }

}
