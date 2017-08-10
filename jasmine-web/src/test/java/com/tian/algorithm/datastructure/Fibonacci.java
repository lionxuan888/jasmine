package com.tian.algorithm.datastructure;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xiaoxuan.jin on 2017/7/25.
 */
public class Fibonacci {

    private static Map<Integer, Integer> map = new HashMap<Integer,Integer>();

    static {
        map.put(0, 0);
        map.put(1, 1);
    }
    // fibonacci
    // hash algorithm
    // fibonacci
    public static int fib(int n){
        if (map.get(n) != null) {
            return map.get(n);
        }
        int i = fib(n - 1) + fib(n - 2);
        map.put(n, i);
        return i;
    }


    /**
     * 打印结果
     */
    public static void printResult(double i, double result) {

    }


    public static void main(String[] args) {
        int fib = fib(100);
        System.out.println(map.values());

        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        File ff = new File("ff");
        System.out.println(ff.getName());
    }

}
