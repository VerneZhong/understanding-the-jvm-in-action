package com.zxb.understanding.the.classloader;

/**
 * 非主动使用类字段演示：
 *  子类调用父类的静态字段，并不会触发子类的初始化
 *
 * @author Mr.zxb
 * @date 2020-09-03 17:34
 */
public class SuperClassNotInitialization {
    public static void main(String[] args) {
        System.out.println(SuperClass.SubClass.value);
    }
}
