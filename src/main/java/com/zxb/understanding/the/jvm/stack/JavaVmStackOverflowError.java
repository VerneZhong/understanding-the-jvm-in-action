package com.zxb.understanding.the.jvm.stack;

/**
 * {@link StackOverflowError} 虚拟机栈和本地方法栈溢出示例
 * VM Args: -Xss128k
 * -Xss 减少栈内存的容量
 * 单线程下，无论是由于栈帧太大还是虚拟机栈容量太小，当内存无法分配时，虚拟机抛出的都是 StackOverflowError 异常
 *
 * @author Mr.zxb
 * @date 2020-01-16 14:57
 */
public class JavaVmStackOverflowError {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {

        JavaVmStackOverflowError overflowError = new JavaVmStackOverflowError();

        try {
            overflowError.stackLeak();
        } catch (Throwable e) {
            System.out.printf("stack length = %s\n", overflowError.stackLength);
            throw e;
        }
    }
}
