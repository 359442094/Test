package com.test.start.test.checkword.constant;

/**
 * 全文检索文件路径
 */
public class LuceneConstant {

    /**
     * 关键词索引保存/读取路径
     * */
    //public static final String LUCENE_DEFAULT_PATH="C:\\lucene\\defaultIndex";
    /**
     * 关键词索引字段名称
     * */
    public static final String LUCENE_FIELD_NAME="key";
    /**
     * 自定义分词字典的项目路径
     * */
    public static final String LUCENE_FILE_LOAD_PATH="src/main/resources/ikAnalyzer/load.dic";

    public static final String LUCENE_FILE_LOAD_FILE_PATH="src/main/resources/ikAnalyzer";

    public static final String LUCENE_FILE_LOAD_CLASS_PATH="/ikAnalyzer/load.dic";


}
