package com.tian.apply.guava;

/**
 * Created by xiaoxuan.jin on 2016/10/14.
 */
public class Foo {

    private Integer period;
    private String name;


    public Foo(Integer period, String name) {
        this.period = period;
        this.name = name;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "period=" + period +
                ", name='" + name + '\'' +
                '}';
    }
}
