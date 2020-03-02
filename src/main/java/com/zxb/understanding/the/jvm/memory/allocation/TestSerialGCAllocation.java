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

    /**
     * 长期存活的对象进入老年代
     * HotSpot虚拟机大多数收集器都采用了分代收集来管理堆内存，内存回收根据对象年龄来区分对象存活在哪个分代中，
     * 虚拟机给每个对象定义来一个对象年龄（Age）计数器，存储在对象头中，对象通常在Eden区诞生，如果经过了一次MinorGC后仍然存活，
     * 并且能够被Survivor容纳的话，该对象会被移动到Survivor空间中，并将其对象年龄设为1岁。对象没熬过一次MinorGC，年龄就增长
     * 一岁，当它的年龄增加到一定程度（默认15），就会被晋升到老年代中。
     * 对象晋升老年代到阈值，可以通过参数：-XX:MaxTenuringThreshold设置
     * <p/>
     * VM Args:
     *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *  -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        // 什么时候进入老年代决定于XX:MaxTenuringThreshold=1
        allocation1 = new byte[_1MB / 4];

        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    /**
     * 动态对象年龄判定：
     *   为了更好地适应不同程序的内存状况，HotSpot虚拟机并不是强制要求对象的年龄必须达到-XX:MaxTenuringThreshold才能晋升老年代，
     *   如果在Survivor空间中相同年龄所有对象大小的总和大于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代，
     *   无须等到阈值要求的年龄。
     * <p/>
     * VM Args:
     *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *  -XX:MaxTenuringThreshold=15 -XX:+PrintTenuringDistribution
     */
    public static void testTenuringThreshold2() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        // allocation1 + allocation2 大于 Survivor空间一半
        // from space 为 0% used
        // 1和2直接进入了老年代，并没有到达15岁的临界年龄，因为这2个对象加起来是512KB，并且是同龄的
        // 满足同年对象达到Survivor空间一半的规则
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[_1MB / 4];

        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];

        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

    /**
     * 空间分配担保：
     *   在发生MinorGC之前，虚拟机必须先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那这一次MinorGC
     *   可用确保是安全的。只要老年代的连续空间大于新生代对象总大小或者历次晋升的平均大小，就会进行MinorGC，否则进行Full GC
     * VM Args:
     *      -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *      -XX:-HandlePromotionFailure  （JDK6才有效）
     */
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];
    }

    public static void main(String[] args) {
        // 对象优先在新生代Eden区分配
//        testAllocation();

        // 大对象直接进入老年代
//        testPretenureSizeThreshold();

        // 长期存活的对象将进入老年代
//        testTenuringThreshold();

        // 动态对象年龄判定
//        testTenuringThreshold2();

        // 空间分配担保
        testHandlePromotion();
    }
}
