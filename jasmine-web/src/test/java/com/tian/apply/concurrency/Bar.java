package com.tian.apply.concurrency;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xiaoxuan.jin on 2017/6/27.
 */
public class Bar {

    static String  base = "string";

    public static void main(String[] args) {
        for (int i=0;i< Integer.MAX_VALUE;i++){
            String str = base + base;
            base = str.intern();
        }
        System.out.println("done");
    }
}
