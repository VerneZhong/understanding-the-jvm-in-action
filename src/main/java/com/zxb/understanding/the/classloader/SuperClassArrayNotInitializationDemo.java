package com.zxb.understanding.the.classloader;

/**
 * 被动使用类字段演示二：
 *  通过数组定义来引用类，不会触发此类的初始化
 *
 * @author Mr.zxb
 * @date 2020-09-03 17:34
 */
public class SuperClassArrayNotInitializationDemo {
    public static void main(String[] args) {
        SuperClass[] superClasses = new SuperClass[10];
    }
}
