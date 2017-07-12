package com.tian.apply.spring;

import com.tian.FooService;
import com.tian.IFooService;
import com.tian.TestSpringService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Proxy;

/**
 * Created by xiaoxuan.jin on 2017/7/5.
 */
public class SpringTest {

    public static void main(String[] args) throws Exception {
        IFooService fooService = new FooService();
        ProxyHandler proxyHandler = new ProxyHandler();
        proxyHandler.setTarget(fooService);
        IFooService fooProxy = (IFooService)Proxy.newProxyInstance(fooService.getClass().getClassLoader(),
                fooService.getClass().getInterfaces(),
                proxyHandler);
        System.out.println(fooProxy.get());
        String[] a = new String[3];
        a.clone();
    }

}
