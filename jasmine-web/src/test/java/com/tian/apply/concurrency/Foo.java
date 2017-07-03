package com.tian.apply.concurrency;

/**
 * Created by xiaoxuan.jin on 2017/6/27.
 */
public class Foo {

    static {
        System.out.println("foo static init...");
    }

    public Foo() {
        System.out.println("foo init...");
    }
    public Foo(String sss) {
        System.out.println("foo sss init...");
    }

}
