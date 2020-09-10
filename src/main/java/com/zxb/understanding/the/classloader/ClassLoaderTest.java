package com.zxb.understanding.the.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 不同的类加载器对instanceof关键字运算的结果的影响 <p>
 * 类加载器与 {@code instanceof} 关键字演示
 *
 * @author Mr.zxb
 * @date 2020-09-08 16:46
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    if (inputStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        try {
            Object obj = myLoader.loadClass("com.zxb.understanding.the.classloader.ClassLoaderTest").newInstance();

            System.out.println(obj.getClass());
            // 预期是false
            System.out.println(obj instanceof com.zxb.understanding.the.classloader.ClassLoaderTest);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
