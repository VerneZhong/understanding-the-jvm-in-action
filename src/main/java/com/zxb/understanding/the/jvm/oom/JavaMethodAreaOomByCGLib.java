package com.zxb.understanding.the.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * {@link OutOfMemoryError} 使用CGLib使方法区内存溢出
 * VM Args: -XX:PermSize=10m -XX:MaxPermSize=10m
 * Jdk8 VM Args: -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * 主流的框架，比如Spring，在对类进行增强时，都会使用到CGLib这类字节码技术，增强到类越多，就需要越大到方法区来保证动态生成到Class可以加载到内存
 * @author Mr.zxb
 * @date 2020-01-16 16:17
 */
public class JavaMethodAreaOomByCGLib {

    static class OomObject {

    }

    public static void main(final String[] args) {

        for (; ; ) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OomObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(objects, args);
                }
            });
            enhancer.create();
        }
    }

}
