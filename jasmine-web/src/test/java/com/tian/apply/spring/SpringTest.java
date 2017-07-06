package com.tian.apply.spring;

import com.tian.FooService;
import com.tian.TestSpringService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xiaoxuan.jin on 2017/7/5.
 */
public class SpringTest {

    public static void main(String[] args) throws Exception {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        TestSpringService testSpringService = beanFactory.getBean(TestSpringService.class);
        FooService fooService = beanFactory.getBean(FooService.class);
        FooService fooServiceFromTest = testSpringService.fooService;
        System.out.println("判断是否相等：" + (fooServiceFromTest == fooService));


    }

}
