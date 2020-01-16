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

        String str2 = new StringBuilder("ja").append("va").toString();
        // intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中存储
        // 至于为什么第二个结果是false，是因为 java字符串已经被放进了常量池，在sun.misc.Version的launcher_name里
        System.out.println("str2.intern() == str2: " + (str2.intern() == str2));

    }
}
