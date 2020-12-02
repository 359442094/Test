//package com.test.common.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.test.common.analizer.ik.IKAnalyzer4Lucene7;
//import com.test.common.constant.LuceneConstant;
//import com.test.model.domain.MtcSalesOrder;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.StringField;
//import org.apache.lucene.index.*;
//import org.apache.lucene.queryparser.classic.ParseException;
//import org.apache.lucene.search.*;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.nio.file.FileSystems;
//import java.util.*;
//
///**
// * 全文检索工具类
// */
//@Slf4j
//@Component
//@Getter
//@Setter
//public class LuceneUtil {
//
//    private static final Logger logger = LoggerFactory.getLogger(LuceneUtil.class);
//
//    private static final Analyzer analyzer=new IKAnalyzer4Lucene7();
//
//    private static LuceneUtil luceneUtil;
//
//    //@Value(value = "${luceneService.indexPath}")
//    private static String lucenePath="C:\\lucene\\defaultIndex";
//
//    @PostConstruct
//    public void setLuceneUtil(){
//        luceneUtil=this;
//    }
//
//    public static void main(String[] args) throws Exception {
//        List<Map<String,String>> list =new ArrayList<Map<String,String>>();
//        Map<String, String> map = new HashMap<>();
//        map.put(LuceneConstant.ORDER_USER_NAME,"cj1");
//        map.put(LuceneConstant.ORDER_MOBILE,"16621242385");
//        list.add(map);
//        Query query = doQueryLike(list);
//        //int count = LuceneUtil.doKeySearch(lucenePath, query, 10000);
//        List<Map<String, String>> resultMaps = LuceneUtil.doPageSearch(lucenePath, query, 1, 1);
//        System.out.println(resultMaps);
//    }
//
//    /**
//     * 开始更新/写入数据库敏感词索引
//     * @param orders 数据库中敏感词库
//     */
//    public static void startWiter(List<MtcSalesOrder> orders) throws Exception {
//        witerKeyIndex(luceneUtil.lucenePath,orders,analyzer);
//    }
//
//    /**
//     * 写入索引内容信息
//     * */
//    public static void witerKeyIndex(String witerPath,List<MtcSalesOrder> orders,Analyzer analyzer) throws Exception {
//        List<Document> documents = getIndexDocument(orders);
//        witerIndex(witerPath,analyzer,documents,true);
//    }
//
//    /**
//     * 写入索引
//     * */
//    public static void witerIndex(String witerPath,Analyzer analyzer,List<Document> documents,Boolean emptyFlag) throws IOException {
//        // 创建IndexWriter
//        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
//        // 指定索引库的地址
//        Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(witerPath));
//        IndexWriter writer = new IndexWriter(directory, cfg);
//        //为true清除之前的索引
//        if(emptyFlag){
//            log.info("清空上次遗留的索引");
//            writer.deleteAll();
//        }
//        // 通过IndexWriter对象将Document写入到索引库中
//        for (Document document : documents) {
//            writer.addDocument(document);
//        }
//        writer.commit();
//        // 关闭writer
//        writer.close();
//        logger.info("更新敏感词索引成功");
//    }
//
//    /**
//     * 索引分词域
//     * */
//    public static List<Document> getIndexDocument(List<MtcSalesOrder> orders){
//        List<Document> documents = new ArrayList<>();
//        //创建索引
//        for (MtcSalesOrder order : orders) {
//            Document document = new Document();
//
//            //联系人
//            Field orderUserName=new StringField(LuceneConstant.ORDER_USER_NAME,order.getContract(),Field.Store.YES);
//            //手机号码
//            Field ordermobile=new StringField(LuceneConstant.ORDER_MOBILE,order.getMobile(),Field.Store.YES);
//            //收货地址
//            Field orderaddress=new StringField(LuceneConstant.ORDER_ADDRESS,order.getRegionAddress(),Field.Store.YES);
//            //当前订单信息
//            Field orderValue = new StringField(LuceneConstant.ORDER_VALUE, JSONObject.toJSONString(order),Field.Store.YES);
//
//            document.add(orderUserName);
//            document.add(ordermobile);
//            document.add(orderaddress);
//            document.add(orderValue);
//
//            documents.add(document);
//        }
//        return documents;
//    }
//
//    /**
//     * 设置权重、模糊查询单个作用域中内容
//     * @param fieldName 作用域
//     * @param query 匹配的内容
//     * @return
//     * @throws ParseException
//     */
//    public static Query doQueryLike(String fieldName,String query) throws ParseException {
//        //设置权重
//        return new WildcardQuery(new Term(fieldName, "*" + query + "*"));
//    }
//
//
//    /**
//     * 设置权重、模糊查询多个作用域中内容
//     * @param queryList
//     * @return
//     * @throws ParseException
//     */
//    public static Query doQueryLike(List<Map<String,String>> queryList) throws ParseException {
//        //设置权重
//        BooleanQuery.Builder builder = new BooleanQuery.Builder();
//        for (Map<String,String> map : queryList) {
//            Set<Map.Entry<String, String>> entries = map.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                builder.add(new BooleanClause(new WildcardQuery(new Term(entry.getKey(), "*" + entry.getValue() + "*")), BooleanClause.Occur.MUST));
//            }
//        }
//        return builder.build();
//    }
//
//    /**
//     * 精确匹配
//     * @param fieldName 作用域
//     * @param query 匹配的内容
//     * @return
//     */
//    public static Query doQuery(String fieldName, String query){
//        return new TermQuery(new Term(fieldName, query));
//    }
//
//    /**
//     * 搜索基金索引中的内容
//     */
//    public static List<Map<String, String>> doPageSearch(String readPath, Query query,int pageIndex, int pageSize) {
//        List<Map<String, String>> maps = new ArrayList<>();
//        try {
//            // 1、创建Directory
//            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(readPath));
//            // 指定索引库的地址
//            IndexReader reader = DirectoryReader.open(directory);
//            // 创建IndexSearcher
//            IndexSearcher searcher = new IndexSearcher(reader);
//            // 通过searcher来搜索索引库
//            // 第二个参数：指定需要显示的顶部记录的N条
//            TopDocs topDocs = searcher.search(query, 1000000000);
//            // 根据查询条件匹配出的记录
//            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//            // 根据查询条件匹配出的记录总数
//            int count =(int)topDocs.totalHits;
//            logger.debug("匹配出的记录总数:" + count);
//            int pages= count%pageSize==0?count/pageSize:count/pageSize+1;
//            logger.debug("匹配出的总页数:" + pages);
//            //分页获取索引内容
//            maps = doPageFundIndexContent(searcher, scoreDocs, pageIndex, pageSize);
//            //关闭资源
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return maps;
//    }
//
//    /**
//     * 分页获取敏感词索引内容
//     * */
//    public static List<Map<String,String>> doPageFundIndexContent(IndexSearcher searcher,ScoreDoc[] scoreDocs,int pageIndex,int pageSize) throws IOException {
//
//        List<Map<String,String>> results=new ArrayList<>();
//        //查询起始记录位置
//        int begin = pageSize * (pageIndex - 1) ;
//        System.out.println("begin:"+begin);
//        //查询终止记录位置
//        int end = Math.min(begin + pageSize, scoreDocs.length);
//        System.out.println("end:"+end);
//        //进行分页查询
//        for(int i=begin;i<end;i++) {
//            // 获取文档的ID
//            int docID = scoreDocs[i].doc;
//            // 通过ID获取文档
//            Document doc = searcher.doc(docID);
//
//            Map<String,String> map=new HashMap<>();
//            map.put(LuceneConstant.ORDER_MOBILE,doc.get(LuceneConstant.ORDER_MOBILE));
//            map.put(LuceneConstant.ORDER_USER_NAME,doc.get(LuceneConstant.ORDER_USER_NAME));
//            map.put(LuceneConstant.ORDER_ADDRESS,doc.get(LuceneConstant.ORDER_ADDRESS));
//            map.put(LuceneConstant.ORDER_VALUE,doc.get(LuceneConstant.ORDER_VALUE));
//
//            results.add(map);
//        }
//
//        return results;
//    }
//
//
//    /**
//     * 搜索敏感词索引中的内容
//     */
//    public static int doKeySearch(String readPath, Query query,int resultNumber) {
//        int count=0;
//        try {
//            // 1、创建Directory
//            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(readPath));
//            // 指定索引库的地址
//            IndexReader reader = DirectoryReader.open(directory);
//            // 创建IndexSearcher
//            IndexSearcher searcher = new IndexSearcher(reader);
//            // 通过searcher来搜索索引库
//            // 第二个参数：指定需要显示的顶部记录的N条
//            TopDocs topDocs = searcher.search(query, resultNumber);
//
//            // 根据查询条件匹配出的记录总数
//            count = (int) topDocs.totalHits;
//
//            // 根据查询条件匹配出的记录
//            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//
//            //获取索引内容
//            //keys = doKeyIndexContent(searcher, scoreDocs);
//
//            // 关闭资源
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    /**
//     * 关键词索引内容
//     * */
//    public static List<String> doKeyIndexContent(IndexSearcher searcher,ScoreDoc[] scoreDocs) throws IOException {
//        List<String> keys=new ArrayList<>();
//        for (ScoreDoc scoreDoc : scoreDocs) {
//            // 获取文档的ID
//            int docId = scoreDoc.doc;
//            // 通过ID获取文档
//            Document doc = searcher.doc(docId);
//            keys.add(doc.get("key"));
//        }
//        return removeKeyDuplicate(keys);
//
//    }
//
//    /**
//     * 去除List中重复的关键词
//     * */
//    public static List<String> removeKeyDuplicate(List<String> keys) {
//        HashSet h = new HashSet(keys);
//        keys.clear();
//        keys.addAll(h);
//        return keys;
//    }
//
//    /**
//     * 开始对比索引中的敏感词
//     * @param word 用户输入的文字
//     * @return true:未含有敏感词 false:含有敏感词
//     * @throws ParseException
//     */
//    public static boolean startSearch(String word){
//        if(!StringUtils.isEmpty(word)){
//            //不可以匹配到中文
//            //Query query = doQuery(LuceneConstant.LUCENE_FIELD_NAME+":"+word, analyzer);
//            //可以匹配到中文
//            Query query = doQuery("name",word);
//            int count = LuceneUtil.doKeySearch(luceneUtil.lucenePath, query, 10000);
//            if(count>0){
//                return false;
//            }
//        }
//        return true;
//    }
//
//}
