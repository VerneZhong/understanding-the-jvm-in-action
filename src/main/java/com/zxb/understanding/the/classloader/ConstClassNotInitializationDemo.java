package com.zxb.understanding.the.classloader;

/**
 * 非主动使用类字段演示
 *
 * @author Mr.zxb
 * @date 2020-09-03 17:49
 */
public class ConstClassNotInitializationDemo {
    public static void main(String[] args) {
        // 使用类的常量，不会触发该类的初始化
        System.out.println(ConstClass.HELLO_WORLD);
    }
}
