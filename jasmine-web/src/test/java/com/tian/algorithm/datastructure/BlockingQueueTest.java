package com.tian.algorithm.datastructure;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列实现
 * Created by xiaoxuan.jin on 2017/7/25.
 */
public class BlockingQueueTest<E> {

    // 容量
    private final int capacity;

    // 队列count
    private AtomicInteger count = new AtomicInteger();

    // 入队锁
    private ReentrantLock putLock = new ReentrantLock();

    // signal for put
    private Condition notFullCondition = putLock.newCondition();

    // 出队锁
    private ReentrantLock takeLock = new ReentrantLock();

    // signal for take
    private Condition notEmpty = takeLock.newCondition();

    private Entry<E> last = null;

    private Entry<E> head = null;

    private class Entry<E> {
        E ele;
        Entry<E> next;

        Entry(E ele) {
            this.ele = ele;
        }
    }

    // construct method
    public BlockingQueueTest(int capacity) {
        this.capacity = capacity;
        last = head = new Entry<E>(null);
    }


    // if no space throw Exception
    public boolean add() {
        return true;
    }

    // if no space return false
    public boolean offer(E e) {
        AtomicInteger count = this.count;
        if (count.get() == capacity) {
            return false;
        }
        Entry entry = new Entry(e);
        int c = -1;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                c = count.incrementAndGet();
                enqueue(entry);
                if (c + 1 < capacity) {
                    notFullCondition.signal();
                }
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            notEmpty.signal();
        }
        return c >= 0;
    }

    // 入队
    private void enqueue(Entry entry) {
        last = last.next = entry;
    }

    // 出队
    private E dequeue() {
        Entry<E> h = head;
        Entry<E> first = h.next;
        head = first;
        E ele = first.ele;
        first.ele = null;
        return ele;
    }

    // if no space return false
    public boolean offer(E e, long timeout, TimeUnit unit) {
        return true;
    }

    // if no space waiting
    public void put() {

    }

    // if no ele waiting
    public E take() {
        AtomicInteger count = this.count;
        E e;
        int c = -1;
        takeLock.lock();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            e = dequeue();
            c = count.getAndDecrement();
            if (c > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            notFullCondition.signal();
        }
        return e;

    }

    // if no ele return null
    public E poll(long timeout, TimeUnit unit) {
        return null;
    }


    public static void main(String[] args) throws Exception {
        BlockingQueue blockingQueue = new LinkedBlockingQueue(3);
        for (int i = 0; i < 3; i++) {
            blockingQueue.add(i);
        }
        boolean offer = blockingQueue.offer(4);
        System.out.println("offer: " + offer);
    }

}
