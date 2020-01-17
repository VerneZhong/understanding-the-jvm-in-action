package com.zxb.understanding.the.jvm.gc;

/**
 * 垃圾收集器 {@link Runtime#gc()} --引用计数算法模拟
 * VM Args: -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * 主流虚拟机并未使用该算法
 * @author Mr.zxb
 * @date 2020-01-16 17:35
 */
public class ReferenceCountingGC {

    private Object instance = null;

    public static final int _1MB = 1024 * 1024;

    /**
     * 占用内存，以便在GC日志中看清楚是否有回收过
     */
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

    public static void main(String[] args) {
        testGC();
    }
}
