package com.tian.algorithm.datastructure;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by xiaoxuan.jin on 2017/2/7.
 */
public class HashMapTest {

    @Test
    public void testHashMap() throws Exception {


    }

    static void aa() {
        System.out.println("aa");
    }

    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < 17; i++) {
            hashMap.put(i, "v" + i);
        }
        System.out.println("done");
    }
}
