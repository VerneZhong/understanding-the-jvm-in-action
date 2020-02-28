package com.zxb.understanding.the.jvm.gc.log;

import java.util.ArrayList;
import java.util.List;

/**
 * GC & JVM Log Demo
 * 垃圾收集器日志
 *
 * @since JDK9 之前
 * -XX:+PrintGC -- 查看GC基本信息
 * -XX:+PrintGCDetails -- 查看GC详细信息
 * -XX:+PrintHeapAtGC  -- 查看GC前后的堆、方法区可用容量变化
 * -XX:+PrintGCApplicationConcurrentTime -- 查看GC过程中用户线程并发时间
 * -XX:+PrintGCApplicationStoppedTime  -- 查看GC过程中用户线程停顿时间
 * -XX:+PrintAdaptiveSizePolicy        -- 查看收集器Ergonomics机制（自动设置堆空间各分代区域大小、收集目标等内容，从Parallel收集器开始支持）自动条件的相关信息
 * -XX:+PrintTenuringDistribution      -- 查看熬过收集后剩余对象的分代年龄分布信息
 * <p>
 *
 * JDK9 之后
 * -Xlog:gc:  -- 查看GC基本信息
 * -X-log:gc* -- 查看所有日志，*通配符，显示所有标签下的过程日志
 * -Xlog:gc+heap=debug:  -- 查看GC前后的堆、方法区可用容量变化
 * -Xlog:safepoint:      -- 查看GC过程中用户线程并发时间以及停顿时间
 * -Xlog:gc+ergo*=trace: -- 查看收集器Ergonomics机制（自动设置堆空间各分代区域大小、收集目标等内容，从Parallel收集器开始支持）自动条件的相关信息
 * -Xlog:gc+age=trace:   -- 查看熬过收集后剩余对象的分代年龄分布信息
 *
 * @author Mr.zxb
 * @date 2020-02-28 11:17
 */
public class GCLogTest {

    public static void main(String[] args) {

        List<GCRoots> roots = new ArrayList<>();
        while (true) {
            roots.add(new GCRoots());
        }
    }

    static class GCRoots {

        /**
         * 4M的容量
         */
        public static final byte[] bytes = new byte[1024 * 1024 * 4];

    }
}
