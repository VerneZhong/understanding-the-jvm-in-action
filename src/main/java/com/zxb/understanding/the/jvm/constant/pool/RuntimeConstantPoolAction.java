package com.zxb.understanding.the.jvm.constant.pool;

/**
 * {@link String#intern()} 返回引用测试
 *
 * @author Mr.zxb
 * @date 2020-01-16 16:05
 */
public class RuntimeConstantPoolAction {

    public static void main(String[] args) {

        // 均返回true，jdk1.6会返回false
        String str = new StringBuilder("计算机").append("软件").toString();

        System.out.println("str.intern() == str: " + (str.intern() == str));

        String str2 = new StringBuilder("Ja").append("va").toString();

        System.out.println("str2.intern() == str2: " + (str2.intern() == str2));

    }
}
