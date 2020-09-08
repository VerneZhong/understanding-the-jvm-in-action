package com.zxb.understanding.the.reference;

import java.io.IOException;

/**
 * 强引用示例
 *
 * @author Mr.zxb
 * @date 2020-09-04 10:16
 */
public class StrongReferenceObjectDemo {
    public static void main(String[] args) throws IOException {
        // 创建一个强引用对象实例
        ReferenceObject referenceObject = new ReferenceObject();
        // 触发GC
        System.out.println(referenceObject);
        referenceObject = null;
        System.gc();
        System.out.println(referenceObject);

        System.in.read();
    }
}
