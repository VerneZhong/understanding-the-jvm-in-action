package com.zxb.understanding.the.jvm.gc.epsilon;

import java.util.ArrayList;
import java.util.List;

/**
 * Epsilon Garbage Collector Demo
 *
 * @since JDK 9
 *
 * 不进行垃圾收集的垃圾收集器，执行一会后就oom了，因为Epsilon不会垃圾收集，当Java堆内存消耗殆尽，就关闭jvm
 *
 * VM Args:
 * -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC -Xms10m -Xmx10m
 *
 * @author Mr.zxb
 * @date 2020-02-27 15:29
 */
public class EpsilonGCDemo {

    public static void main(String[] args) {

        List<Garbage> list = new ArrayList<>();

        int count = 1;
        while (true) {
            list.add(new Garbage(count));
            count++;
        }
    }

    static class Garbage {

        private int number;

        public Garbage(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Garbage Collector Running...  number = " + number);
        }
    }
}
