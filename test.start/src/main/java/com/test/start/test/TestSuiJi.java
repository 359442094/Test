package com.test.start.test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CJ
 * @date 2020/5/7
 */
public class TestSuiJi {

    /**
     * 打乱选项、试题顺序
     * @param questions 试题列表
     * @return
     */
    public static List<Integer> getShuffleQuestions(List<Question> questions){
        List<Integer> items = questions.stream().map(questionItem -> questionItem.getQuestionId()).collect(Collectors.toList());
        //打乱顺序
        Collections.shuffle(items);
        return items;
    }

    /**
     * 打乱选项、试题顺序
     * @param questionItems 试题选项列表
     * @return
     */
    public static List<Integer> getShuffleItems(List<QuestionItem> questionItems){
        List<Integer> items = questionItems.stream().map(questionItem -> questionItem.getItemId()).collect(Collectors.toList());
        //打乱顺序
        Collections.shuffle(items);
        return items;
    }

    /**
     * 初始化数字序号
     * 生成1-size的数字序号
     * @param size
     * @return
     */
    public static List<Integer> getItemIndexs(int size){
        return new ArrayList<Integer>(){
            {
                for (int i=1;i<=size;i++){
                    add(i);
                }
            }
        };
    }

    /**
     * 初始化试题内容
     * 生成选项的数字、字母序号
     * @param questions
     * @return
     */
    public static List<Question> processQuestion(List<Question> questions){
        List<String> letters = LetterUtil.letterItems(true);
        List<Integer> itemIndexs = getItemIndexs(questions.size());
        for (int i = 0; i < questions.size(); i++) {
            questions.get(i).setQuestionIndex(String.valueOf(itemIndexs.get(i)));
            questions.get(i).setQuestionLetter(letters.get(i));
        }
        return questions;
    }

    /**
     * 初始化选项内容
     * 生成选项的数字、字母序号
     * @param questionItems
     * @return
     */
    public static List<QuestionItem> processItem(List<QuestionItem> questionItems){
        List<String> letters = LetterUtil.letterItems(true);
        List<Integer> itemIndexs = getItemIndexs(questionItems.size());
        for (int i = 0; i < questionItems.size(); i++) {
            questionItems.get(i).setItemIndex(String.valueOf(itemIndexs.get(i)));
            questionItems.get(i).setItemLetter(letters.get(i));
        }
        return questionItems;
    }

    /**
     * 试题正常顺序
     * @param questions
     * @return
     */
    public static List<Question> getQuestionList(List<Question> questions){
        //初始化选项内容(重新生成选项的数字、字母序号)
        return processQuestion(questions);
    }

    /**
     * 选项正常顺序
     * @param questionItems
     * @return
     */
    public static List<QuestionItem> getItemList(List<QuestionItem> questionItems){
        //初始化选项内容(重新生成选项的数字、字母序号)
        return processItem(questionItems);
    }

    /**
     * 试题乱序
     * @param questions
     * @return
     */
    public static List<Question> getQuestionShuffleList(List<Question> questions){
        Map<String, Question> maps=new HashMap<>();
        for (Question question : questions) {
            maps.put(String.valueOf(question.getQuestionId()), question);
        }
        //开始乱序
        List<Integer> questionIds = getShuffleQuestions(questions);
        List<Question> shuffleQuestions =new ArrayList<>();
        for (Integer questionId : questionIds) {
            Question question = maps.get(String.valueOf(questionId));
            shuffleQuestions.add(question);
        }
        return processQuestion(shuffleQuestions);
    }

    /**
     * 选项乱序
     * @param questionItems
     * @return
     */
    public static List<QuestionItem> getItemShuffleList(List<QuestionItem> questionItems){
        Map<String, QuestionItem> maps=new HashMap<>();
        for (QuestionItem questionItem : questionItems) {
            maps.put(String.valueOf(questionItem.getItemId()), questionItem);
        }
        //开始乱序
        List<Integer> itemIds = getShuffleItems(questionItems);
        List<QuestionItem> shuffleQuestionItems =new ArrayList<>();
        for (Integer itemId : itemIds) {
            QuestionItem questionItem = maps.get(String.valueOf(itemId));
            shuffleQuestionItems.add(questionItem);
        }
        return processItem(shuffleQuestionItems);
    }

    public static void main(String[] args) {
        //思路步骤:
        //1.数据库分页查询试题列表(包含选项)、默认返回前1-5条数据
        //2.根据配置项判断是否开始打乱试题、选项顺序
        //3.第一次返回前端显示1-5条数据(打乱|不打乱的顺序)
        //4.前端传入第二次查询的分页参数
        //重复2步骤...
        //...

        List<Question> questions =new ArrayList<Question>(){
            {
                //模拟试题1生成的选项内容
                List<QuestionItem> questionItems =new ArrayList<QuestionItem>(){
                    {
                        add(new QuestionItem(1,"questionId1","内容1",false));
                        add(new QuestionItem(2,"questionId1","内容2",false));
                        add(new QuestionItem(3,"questionId1","内容3",true));
                        add(new QuestionItem(4,"questionId1","内容4",false));
                    }
                };
                add(new Question(1,"1","test1", questionItems));
                add(new Question(2,"2","test2", questionItems));
                add(new Question(3,"3","test3", questionItems));
            }
        };

        /*System.out.println("*********试题正常顺序*********");
        List<Question> questionList = getQuestionList(questions);
        //正常顺序
        questionList.forEach(System.out::println);*/

        /*System.out.println("*********试题打乱顺序*********");*/
        questions = getQuestionShuffleList(questions);

        for (Question suiJiQuestion : questions) {
            /*System.out.println("*********选项正常顺序*********");
            List<QuestionItem> itemList = getItemList(suiJiQuestion.getQuestionItems());
            suiJiQuestion.setQuestionItems(itemList);
            //正常顺序
            list.forEach(System.out::println);*/
            /*System.out.println("*********选项打乱顺序*********");*/
            List<QuestionItem> itemShuffleList = getItemShuffleList(suiJiQuestion.getQuestionItems());
            suiJiQuestion.setQuestionItems(itemShuffleList);
        }

        //乱序
        questions.forEach(System.out::println);

    }
}
