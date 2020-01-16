package com.zxb.understanding.the.jvm.oom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutOfMemoryError} 异常示例，Java Heap 溢出 Java heap space
 * Heap 大小 20m，并不可扩展
 * -XX:+HeapDumpOnOutOfMemoryError 当出现溢出dump出异常快照
 * -XX:HeapDumpPath=/usr/local/base  导出 dump oom 异常信息
 * jmap -dump:file=/Users/zhongxuebin/jvm/dump/HeapOom.bin 47768 导出堆栈信息
 * VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -Xms 堆内存最小值
 * -Xmx 堆内存最大值
 * @author Mr.zxb
 * @date 2020-01-16 09:41
 */
public class HeapOom {

    static class OomObject {

    }

    public static void main(String[] args) throws IOException {

        List<OomObject> list = new ArrayList<>();

        while (true) {
            list.add(new OomObject());
        }
    }
}
