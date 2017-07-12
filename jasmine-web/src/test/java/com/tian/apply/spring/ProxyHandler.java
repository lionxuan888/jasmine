package com.tian.apply.spring;

import com.tian.IFooService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理学习
 * <p>
 * Created by xiaoxuan.jin on 2017/7/12.
 */
public class ProxyHandler implements InvocationHandler {


    private IFooService target;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("哈哈");
        return method.invoke(target, args);
    }

    public void setTarget(IFooService target) {
        this.target = target;
    }
}
