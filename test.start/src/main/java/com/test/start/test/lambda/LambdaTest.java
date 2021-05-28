package com.test.start.test.lambda;


/**
 * @author CJ
 * @date 2020/6/17
 */
public class LambdaTest {

    public static LambdaProcess calculate(char opr) {
        LambdaProcess result;
        if (opr == '+') {
            // Lambda表达式实现Calculable接口
            result = (a, b) -> {
                return a + b;
            };
        } else {
            // Lambda表达式实现Calculable接口
            result = (a, b) -> {
                return a - b;
            };
        }
        return result;
    }

    public static void main(String[] args) {
        //正常方式
        LambdaProcess lambdaProcess1 = LambdaTest.calculate('-');
        int result1 = lambdaProcess1.process(1, 5);
        System.out.println(result1);

        //简写方式
        LambdaProcess lambdaProcess2 = (int a, int b) -> {
            return a + b;
        };

        int result2 = lambdaProcess2.process(1, 3);

        System.out.println(result2);
    }

}
