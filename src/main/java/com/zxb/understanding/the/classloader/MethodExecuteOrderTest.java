package com.zxb.understanding.the.classloader;

/**
 * 方法执行顺序
 *
 * @author Mr.zxb
 * @date 2020-09-08 16:04
 */
public class MethodExecuteOrderTest {
    static class Parent {
        public static int A = 1;
        static {
            A = 2;
        }
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }
}
