package com.zxb.understanding.the.classloader;

/**
 * 非法向前引用
 *
 * @author Mr.zxb
 * @date 2020-09-08 15:54
 */
public class IllegalForwardReferenceTest {
    static {
        // 赋值可以正常编译通过
        i = 0;
        // 编译器，提示非法向前引用对象
//        System.out.println(i);
    }
    static int i = 1;
}
