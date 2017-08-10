package com.tian.apply.concurrency;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
                    String threadName = Thread.currentThread().getName();
                    System.out.println(threadName + "  entering thread and sleep 5s");
                    Thread.sleep(5000);
                    System.out.println(threadName + " thread is ending...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(subThread, 3, TimeUnit.SECONDS);
        Thread.currentThread().join();
    }

    @Test
    public void testThreadPoolExecutor() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future> futures = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            futures.add(executorService.submit(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000 * 60 * 10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " running....");
                }
            }));
        }
        for (Future future : futures) {
            future.get();
        }
        System.out.println("main exit....");
    }

    /**
     * 测试wait和sleep对锁的释放
     * @throws Exception
     */
    @Test
    public void testWait() throws Exception {
        final Object lockObj = new Object();
        Runnable runnable = new Runnable() {
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " out lock entering outer code");
                synchronized (lockObj) {
                    System.out.println(threadName + " entering sleeping.....");
                    //sleep(3000);
                    waiting(lockObj);
                    System.out.println(threadName + " entering running.....");
                    for (int i = 0; i < 10; i++) {
                        System.out.println(i);
                    }
                    System.out.println(threadName + " entering ending.....");
                }
                System.out.println(threadName + "out lock execute some other code code code code code code code code ");
            }

            private void sleep(int i) {
                try {
                    Thread.sleep(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            private void waiting(Object lockObj) {
                try {
                    lockObj.wait(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread subThreadA = new Thread(runnable);
        Thread subThreadB = new Thread(runnable);
        subThreadA.start();
        subThreadB.start();
        subThreadA.join();
        subThreadB.join();
        System.out.println("main 结束");

    }

    public static void main(String[] args) throws Exception {


    }

}
