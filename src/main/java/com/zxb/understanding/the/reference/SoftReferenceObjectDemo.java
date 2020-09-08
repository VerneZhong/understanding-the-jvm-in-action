package com.zxb.understanding.the.reference;

import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * 软引用示例；适合做缓存
 * 设置最大的堆内存为： -Xmx20m -XX:+PrintGCDetails
 * @author Mr.zxb
 * @date 2020-09-04 10:16
 */
public class SoftReferenceObjectDemo {
    public static void main(String[] args) throws IOException {
        // 分配一个10m的byte数组的软引用对象
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 10]);

        // 触发 GC
        System.gc();

        // 获取引用对象
        System.out.println(softReference.get());

        // 分配一个10m的byte数组强引用对象，此时，堆内存不够分配该对象，会收集软引用对象，若收集之后还是不够分配，就会触发OOM
        byte[] bytes = new byte[1024 * 1024 * 10];

        // 再次获取软引用对象
        System.out.println(softReference.get());
    }
}
