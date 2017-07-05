package com.tian.apply.concurrency;

import com.google.common.primitives.Ints;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiaoxuan.jin on 2017/6/22.
 */
public class ExecutorTest {

    private AtomicInteger count = new AtomicInteger(0);


    @Test
    public void testExecutor() throws Exception {
        Thread subThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println("thread is running...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        subThread.start();
        Thread thread = Thread.currentThread();
        while (thread.isAlive()) {
            thread.wait();
        }

        System.out.println("main thread exit...");
    }

    public static void main(String[] args) throws Exception {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(4);
        final ReentrantLock reentrantLock = new ReentrantLock();
        Runnable target = new Runnable() {
            @Override
            public void run() {
                try {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("entering " + threadName);
                    reentrantLock.lock();
                    System.out.println(threadName + "获取到分布式锁，开始运行程序");
                    Thread.sleep(5000);
                    System.out.println(threadName + " sub thread running end...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }

            }
        };
        Thread subThread_1 = new Thread(target);

        subThread_1.run();
        Thread subThread_2 = new Thread(target);
        subThread_2.start();
        while(Thread.activeCount() > 1){}

        System.out.println("主程序结束");

        int TERMINATED =  3 << 29;
        System.out.println(TERMINATED);
    }

}
