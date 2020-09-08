package com.zxb.understanding.the.jvm.stack;

/**
 * JVM对象分配之栈上分配 & TLAB分配
 * VM Args: -Xmx10m -XX:+PrintGCDetails
 * -server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-UseTLAB -XX:+EliminateAllocations -XX:+EliminateLocks
 * @author Mr.zxb
 * @date 2020-09-04 16:58
 */
public class StackCreateObjectDemo {

    static class ObjectHolder {
        private String name;
        private int num;

        public ObjectHolder(String name, int num) {
            this.name = name;
            this.num = num;
        }
    }

    /**
     *
     */
    public static void createByStack() {
        // objectHolder 在方法内部创建，所以不是逃逸对象，所以会在栈上分配对象
        synchronized (ObjectHolder.class) {
            ObjectHolder objectHolder = new ObjectHolder("test", 2);
        }
    }

    private static ObjectHolder objectHolder;

    public static void createByHeap() {
        // objectHolder 是方法外部定义的对象，所以是逃逸对象，就需要在堆内分配对象
        objectHolder = new ObjectHolder("test2", 3);
    }

    /**
     * 同步消除
     * @param str
     */
    public static void appendString(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int count = 100_000_000;
        for (int i = 0; i < count; i++) {
//            createByStack();
//            createByHeap();
            appendString("hello world");
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + " ms");
    }
}
