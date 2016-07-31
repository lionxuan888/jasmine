package com.tian.jasmine.guava;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.corba.se.impl.activation.NameServiceStartThread;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class FunctionTest {


    @Test
    public void testFunction() throws Exception {
        List<String> ids = Lists.newArrayList("a", "b", "c", "d");
        Function<String, String> upperFunction = new Function<String, String>() {
            public String apply(String input) {
                return input.toUpperCase();
            }
        };
        List<String> transform = Lists.transform(ids, upperFunction);
        System.out.println(transform);

        new Thread(() ->  {
            System.out.println("hah");
        }).start();
        Thread.sleep(1000);
    }
}
