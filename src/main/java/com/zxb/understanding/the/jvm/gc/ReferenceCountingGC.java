package com.zxb.understanding.the.jvm.gc;

/**
 * 引用计数算法
 *
 * @author Mr.zxb
 * @date 2020-01-16 17:35
 */
public class ReferenceCountingGC {

    private Object instance = null;

    public static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        Runtime.getRuntime().gc();
    }
}
