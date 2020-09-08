package com.zxb.understanding.the.reference;

import sun.misc.Cleaner;

import java.nio.ByteBuffer;

/**
 * 直接内存里使用里虚引用，{@link Cleaner}
 *
 * @author Mr.zxb
 * @date 2020-09-04 10:47
 */
public class NIODirectBufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
    }
}
