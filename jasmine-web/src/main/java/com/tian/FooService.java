package com.tian;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaoxuan.jin on 2017/7/6.
 */
@Service
public class FooService {

    @Resource
    public TestSpringService testSpringService;

    public String get() {
        System.out.println("FooService get foo");
        return "foo";
    }
}
