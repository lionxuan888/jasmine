package com.tian.apply.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaoxuan.jin on 2017/7/5.
 */
public class SpringTest {

    public static void main(String[] args) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        Date parse = simpleDateFormat.parse("2017-13-30");
        System.out.println(parse);
        char a = 33;
        System.out.println(a);
    }

}
