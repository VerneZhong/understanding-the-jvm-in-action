package com.zxb.understanding.the.classloader;

/**
 * 字段解析
 *
 * @author Mr.zxb
 * @date 2020-09-08 16:12
 */
public class FieldResolutionTest {
    static class DeadLoopClass {
        static {
            // 如果不加上if语句，编译器将提示"Initializer does not complete normally"，并拒绝编译
            if (true) {
                System.out.println(Thread.currentThread() + "init DeadLoopClass");
                while (true) {}
            }
        }
    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread() + "start");
            DeadLoopClass deadLoopClass = new DeadLoopClass();
            System.out.println(Thread.currentThread() + " run over.");
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();
    }
}
