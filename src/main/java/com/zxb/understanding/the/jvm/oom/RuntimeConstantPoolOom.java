package com.zxb.understanding.the.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutOfMemoryError} 方法区和运行时常量池溢出
 * VM Args: -XX:PermSize=5m -XX:MaxPermSize=5m
 * JDK1.6 会出现 java.lang.OutOfMemoryError: PermGe space
 * Jdk1.7 会一直运行下去，需要加 -Xmx5m，因为Jdk7以上已经将常量池移至Java堆中
 *
 * @author Mr.zxb
 * @date 2020-01-16 15:49
 */
public class RuntimeConstantPoolOom {

    public static void main(String[] args) {

        // 使用list 保持着常量池引用，避免 Full GC 回收常量池行为
        List<String> list = new ArrayList<>();

        // 10M 的 PermSize 在 Integer 范围内足够产生 OOM了
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
