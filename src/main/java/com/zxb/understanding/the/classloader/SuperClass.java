package com.zxb.understanding.the.classloader;

/**
 * 被动使用类字段演示一：通过子类引用父类的静态字段，不会导致子类初始化
 * 结论：对于静态字段，只有直接定义这个类才会被初始化，因此通过其子类来引用父类中定义的静态字段，只会触发父类的初始化而不会触发子类的初始化
 * 可通过 -XX:+TraceClassLoading 参数观察到此操作是会导致子类加载的
 * @author Mr.zxb
 * @date 2020-09-03 16:59
 */
public class SuperClass {
    static {
        System.out.println("Super Class Initialization.");
    }

    public static int value = 123;

    static class SubClass extends SuperClass {
        static {
            System.out.println("Sup Class Initialization.");
        }
    }
}
