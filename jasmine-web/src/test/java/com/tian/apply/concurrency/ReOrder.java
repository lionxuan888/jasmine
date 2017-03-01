package com.tian.apply.concurrency;

/**
 * 重排
 * Created by xiaoxuan.jin on 2017/2/7.
 */
public class ReOrder {

    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                System.out.println("one start");
                try {
                    System.out.println("one thread sleep 5 second");
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        //other.start();
        one.join();
        // other.join();
        System.out.println("结果:(" + x + "," + y + ")");
    }

}
