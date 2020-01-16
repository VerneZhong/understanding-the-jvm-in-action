package com.zxb.understanding.the.jvm.oom;

import com.zxb.understanding.the.jvm.util.UnsafeUtil;
import sun.misc.Unsafe;

/**
 * {@link OutOfMemoryError} 直接内存 OOM
 *
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * -XX:MaxDirectMemorySize 直接内存如果不指定，则与Java堆 Xmx一样的大小
 *
 * 通过 Unsafe操作进行内存分配
 *
 * 本机直接内存的分配虽然不会受到Java 堆大小的限制，但是受到本机总内存大小限制。
 * 直接内存由 -XX:MaxDirectMemorySize 指定，如果不指定，则默认与Java堆最大值（-Xmx指定）一样。
 * NIO程序中，使用ByteBuffer.allocateDirect(capability)分配的是直接内存，可能导致直接内存溢出。
 *
 * @author Mr.zxb
 * @date 2020-01-16 16:59
 */
public class DirectMemoryOom {

    public static final int _10MB = 1024 * 1024 * 10;

    public static void main(String[] args) {

        Unsafe unsafe = UnsafeUtil.getUnsafe();

        while (true) {
            // 分配10M内存
            unsafe.allocateMemory(_10MB);
        }
    }
}
