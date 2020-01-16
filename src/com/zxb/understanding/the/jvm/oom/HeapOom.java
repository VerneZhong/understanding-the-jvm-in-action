package com.zxb.understanding.the.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link OutOfMemoryError} 异常示例
 * VM Args: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author Mr.zxb
 * @date 2020-01-16 09:41
 */
public class HeapOom {

    static class OomObject {

    }

    public static void main(String[] args) {

        List<OomObject> list = new ArrayList<>();

        while (true) {
            list.add(new OomObject());
        }
    }
}
