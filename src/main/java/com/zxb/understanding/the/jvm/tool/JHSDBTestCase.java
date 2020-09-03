package com.zxb.understanding.the.jvm.tool;

import java.util.concurrent.locks.LockSupport;

/**
 * JHSDB 工具使用示例
 * JVM Args: -Xmx10m -XX:+UseSerialGC -XX:-UseCompressedOops
 * @author Mr.zxb
 * @date 2020-09-03 14:23
 */
public class JHSDBTestCase {

    static class ObjectHolder {}

    static class Test {
        // staticObj 随着Test的类型信息存放在方法区
        static ObjectHolder staticObj = new ObjectHolder();
        // instanceObj 随着Test的对象实例存放在 Java堆
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            // localObj 则是存放在foo() 方法栈帧的局部变量表中
            ObjectHolder localObj = new ObjectHolder();
            LockSupport.park();
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
