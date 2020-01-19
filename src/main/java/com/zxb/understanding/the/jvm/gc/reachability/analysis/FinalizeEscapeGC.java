package com.zxb.understanding.the.jvm.gc.reachability.analysis;

/**
 * {@link Object#finalize()} 一次对象自我拯救示例
 * 1. 对象可以在被GC时自我拯救
 * 2. 这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 *
 * @author Mr.zxb
 * @date 2020-01-17 14:51
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOCK = null;

    public void isAlive() {
        System.out.println("yes, I am still alive.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize() executed!");
        FinalizeEscapeGC.SAVE_HOCK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOCK = new FinalizeEscapeGC();

        // 对象第一次成功拯救自己
        SAVE_HOCK = null;
        System.gc();
        // 因为 finalize() 方法优先级很低，暂停0.5s
        Thread.sleep(500);
        if (SAVE_HOCK != null) {
            SAVE_HOCK.isAlive();
        } else {
            System.out.println("no, I am dead.");
        }

        // 和上面代码一样，但是这次自救失败
        SAVE_HOCK = null;
        System.gc();
        // 因为 finalize() 方法优先级很低，暂停0.5s
        Thread.sleep(500);
        if (SAVE_HOCK != null) {
            SAVE_HOCK.isAlive();
        } else {
            System.out.println("no, I am dead.");
        }
    }
}
