package com.test.start.test.checkword.analizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;

import java.io.IOException;

public class MyWhitespaceAnalyzer extends Analyzer {
    /**
     * 继承Analyzer创建组件的方法
     * @param fieldName
     * @return
     */
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        //创建Tokenizer
        Tokenizer source = new MyWhitespaceTokenizer();
        //创建TokenStream 分项过滤器
        TokenStream filter = new MyLowerCaseTokenFilter(source);
        //多个过滤器
        //TokenStream filter2 = new MyLowerCaseTokenFilter(filter);
        return new TokenStreamComponents(source,filter);
    }

    static class MyWhitespaceTokenizer extends Tokenizer{

        //需要记录的 属性 词
        MyCharAttribute charAttr = this.addAttribute(MyCharAttribute.class);

        char[] buffer = new char[255];
        int lenth = 0;
        int c;

        @Override
        public boolean incrementToken() throws IOException {
            //清除所有的词元属性
            clearAttributes();
            lenth = 0;

            while (true){
                //读取字符
                c = this.input.read();

                //判断是否读取到了元素 -1 字符读取完毕
                if (c == -1){
                    if (lenth > 0){
                        //复制到charAttr
                        this.charAttr.setChars(buffer,lenth);
                        return true;
                    }else {
                        return false;
                    }
                }

                //判断是否为空白字符
                if(Character.isWhitespace(c)){
                    if (lenth > 0){
                        this.charAttr.setChars(buffer,lenth);
                        return true;
                    }
                }

                buffer[lenth++] = (char) c;
            }
        }
    }

    public  static class MyLowerCaseTokenFilter extends TokenFilter{

        public MyLowerCaseTokenFilter(TokenStream input){
            super(input);
        }

        MyCharAttribute charAttr = this.addAttribute(MyCharAttribute.class);

        public boolean incrementToken() throws IOException {
            //调用 TokenStream（输入的）increamentToken方法
            boolean res = this.input.incrementToken();
            if (res){
                char[] chars = charAttr.getChars();
                int length = charAttr.getLength();
                if (length > 0){
                    for (int i = 0; i < length; i++){
                        //字符转换为小写
                        chars[i] = Character.toLowerCase(chars[i]);
                    }
                }
            }
            return res;
        }
    }

    public static interface MyCharAttribute extends Attribute{

        void setChars(char[] buffer, int length);

        char[] getChars();

        int getLength();

        String getString();
    }

    public static class MyCharAttributeImpl extends AttributeImpl implements MyCharAttribute{

        private char[] charTerm = new char[255];
        private int length = 0;

        public void setChars(char[] buffer, int length) {
            this.length = length;
            if (length > 0){
                //复制数组
                System.arraycopy(buffer,0,this.charTerm,0,length);
            }
        }

        public char[] getChars() {
            return this.charTerm;
        }

        public int getLength() {
            return this.length;
        }

        public String getString() {
            if (this.length > 0){
                return new String(this.charTerm,0,length);
            }
            return null;
        }

        public void clear() {
            this.length = 0;
        }

        public void reflectWith(AttributeReflector reflector) {

        }

        public void copyTo(AttributeImpl target) {

        }
    }

    public static void main(String[] args) {

        String text = "An AttributeSource contains a list of different AttributeImpls, and methods to add and get them. ";

        try (Analyzer analyzer = new MyWhitespaceAnalyzer();
        TokenStream tokenStream = analyzer.tokenStream("aa",text);){

            MyCharAttribute charAttribute = tokenStream.getAttribute(MyCharAttribute.class);
            //重置该分词器，使得此TokenStrean可以重新开始返回各个分词。重置该分词器，使得此TokenStrean可以重新开始返回各个分词。
            tokenStream.reset();
            while (tokenStream.incrementToken()){
                System.out.print(charAttribute.getString() + "|");
            }
            tokenStream.end();
            System.out.println();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


