package com.zxb.understanding.the.reference;

/**
 * 引用对象
 *
 * @author Mr.zxb
 * @date 2020-09-04 10:15
 */
public class ReferenceObject {
    @Override
    protected void finalize() throws Throwable {
        // gc回收时会调用
        System.out.println("finalize");
    }
}
