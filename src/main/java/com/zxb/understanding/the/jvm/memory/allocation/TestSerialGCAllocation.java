package com.zxb.understanding.the.jvm.memory.allocation;

/**
 * VM参数：-XX:+UseSerialGC
 *
 * @author Mr.zxb
 * @date 2020-03-02 10:23
 */
public class TestSerialGCAllocation {

    public static final int _1MB = 1024 * 1024;

    /**
     * 对象优先在Eden分配，大多是情况下，对象在新生代Eden区中分配，当Eden空间没有足够的空间进行分配时，虚拟机将发起一次MinorGC
     * <p/>
     * VM Args:
     *      -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *      -Xms20M -Xmx20M -Xmn10M这三个参数限制了Java堆大小为20MB，不可扩展，其中10MB分配给新生代，剩下10MB分配给老年代
     */
    public static void testAllocation() {
        // 分配allocation4出现一次MinorGC，回收结果[DefNew: 7992K->417K(9216K), 0.0055072 secs]
        // 总内存占用量没有减少（没有可回收对象）
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        // 出现一次Minor GC，垃圾回收期间发现3个2MB的对象全部无法放入Survivor空间
        // 所以只能通过分配担保机制提前转移到老年代中
        // tenured generation   total 10240K, used 6144K
        // 收集结束后，4MB的allocation4对象顺利分配在Eden空间中
        // 程序执行完结果是Eden占用4MB（allocation4占用），Survivor空闲，老年代被占用6MB
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * 大对象直接进入老年代
     * <p/>
     * 大对象就是指需要大量连续内存空间的Java对象，典型的大对象：很长的字符串或者元素数量很大的数组。
     * 在编程中尽可能避免"短命的大对象"，否则会频繁触发GC，意味着高额的内存开销。
     * HotSpot虚拟机提供 -XX:PretenureSizeThreshold参数，指定大于该设置的值的对象直接在老年代分配，这样就避免在
     * Eden区以及两个Survivor区之间来回复制，产生大量的内存复制操作。
     * 注意：-XX:PretenureSizeThreshold 参数只对 Serial和ParNew两款新生代收集器有效，若要使用该参数进行调优，
     * 可以考虑ParNew+CMS的收集器组合
     * <p/>
     * VM Args:
     *      -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *      -XX:PretenureSizeThreshold=3145728
     *      3MB = 3 * 1024 * 1024 = 3145728
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        // 直接分配在老年代
        // tenured generation   total 10240K, used 4096K
        allocation = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        // 对象优先在新生代Eden区分配
//        testAllocation();

        // 大对象直接进入老年代
        testPretenureSizeThreshold();
    }
}
