package com.tian;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaoxuan.jin on 2017/7/6.
 */
@Service
public class TestSpringService {

    @Resource
    public FooService fooService;

    public String getAll() {
        System.out.println("TestSpringService get All ....");
        return "hello world";
    }


}
