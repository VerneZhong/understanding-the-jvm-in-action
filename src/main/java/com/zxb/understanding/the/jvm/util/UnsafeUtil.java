package com.zxb.understanding.the.jvm.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * {@link sun.misc.Unsafe} 工具类
 *
 * @author Mr.zxb
 * @date 2020-01-16 16:55
 */
public class UnsafeUtil {

    private static Unsafe unsafe = null;

    /**
     * 通过反射方式获取Unsafe对象
     * @return
     */
    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                unsafe = (Unsafe) theUnsafe.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return unsafe;
    }
}
