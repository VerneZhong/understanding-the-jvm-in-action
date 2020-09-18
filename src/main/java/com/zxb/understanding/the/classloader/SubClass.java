package com.zxb.understanding.the.classloader;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2020-09-18 09:57
 */
public class SubClass extends SuperClass {
    static {
        System.out.println("Sup Class Initialization.");
    }
}
