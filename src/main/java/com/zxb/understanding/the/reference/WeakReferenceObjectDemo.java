package com.zxb.understanding.the.reference;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 弱引用示例
 * @author Mr.zxb
 * @date 2020-09-04 10:16
 */
public class WeakReferenceObjectDemo {
    public static void main(String[] args) throws IOException {
        // 分配一个10m的byte数组的弱引用对象
        WeakReference<byte[]> softReference = new WeakReference<>(new byte[1024 * 1024 * 10]);
        // 获取引用对象
        System.out.println(softReference.get());
        // 触发 GC
        System.gc();
        // 再次获取软引用对象
        System.out.println(softReference.get());
    }
}
