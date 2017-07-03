package com.tian.apply.concurrency;

import com.google.common.primitives.Ints;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xiaoxuan.jin on 2017/6/22.
 */
public class ExecutorTest {

    private AtomicInteger count = new AtomicInteger(0);


    @Test
    public void testExecutor() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            Future<String> submit = executorService.submit(new Callable<String>() {
                public String call() throws Exception {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "running...");
                    return null;
                }
            });
        }
        executorService.shutdown();
        while(Thread.activeCount() > 1) {
        }
        System.out.println("main thread exit...");
    }

    public static void main(String[] args) throws Exception{
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(4);
        for (int i = 0; i < 10; i++) {
            String element = "test-" + i;
            blockingQueue.put(element);
            System.out.println("成功放入队列 + " + element);
        }
    }

}
