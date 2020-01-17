package com.zxb.understanding.the.jvm.reference;

import java.lang.ref.*;
import java.util.concurrent.FutureTask;

/**
 * {@link java.lang.ref.Reference} 软/弱等引用示例
 * <p>
 * {@link SoftReference} 软引用：描述一些有用但非必须对象，被软引用关联的对象在发生内存溢出异常前，会把这些对象进行回收。
 * {@link WeakReference} 弱引用：被弱引用关联的对象只能生存到下一次垃圾收集发生为止。
 * {@link PhantomReference} 虚引用：又称幽灵引用或幻影引用，它是最弱的一种引用关系，为一个对象设置虚引用关联的唯一目的就是为了能在对象被回收前能收到一个系统通知
 *
 * @author Mr.zxb
 * @date 2020-01-17 14:00
 */
public class SoftReferenceDemo {

    static class SoftReferenceObject {

    }

    public static void main(String[] args) {

        // 软引用实例
        SoftReference<SoftReferenceObject> softReference =
                new SoftReference<>(new SoftReferenceObject());

        // 弱引用实例
        WeakReference<SoftReferenceObject> weakReference =
                new WeakReference<>(new SoftReferenceObject());

        final ReferenceQueue<SoftReferenceObject> referenceQueue = new ReferenceQueue<>();

        // 当执行GC后，会收到一条通知
        FutureTask<SoftReferenceObject> futureTask =
                new FutureTask<>(new Runnable() {
                    @Override
                    public void run() {
                        for (; ; ) {
                            Reference<? extends SoftReferenceObject> poll = referenceQueue.poll();
                            if (poll != null) {
                                System.out.println("虚引用被回收通知：" + poll);
                            }
                        }
                    }
                }, null);
        Thread thread = new Thread(futureTask);
        thread.setDaemon(true);
        thread.start();

        PhantomReference<SoftReferenceObject> phantomReference =
                new PhantomReference<>(new SoftReferenceObject(), referenceQueue);

        System.out.println("GC前软引用对象：" + softReference.get());
        System.out.println("GC前弱引用对象：" + weakReference.get());
        System.out.println("GC前虚引用对象：" + phantomReference.get());
        Runtime.getRuntime().gc();
        System.out.println("GC后软引用对象：" + softReference.get());
        // GC后 弱引用被回收
        System.out.println("GC后弱引用对象：" + weakReference.get());
        System.out.println("GC后虚引用对象：" + phantomReference.get());

    }
}
