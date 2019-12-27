package com.tian.apply.logger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2016/8/5.
 */
public class LoggerTest {

    @Test
    public void testLogger() throws Exception {
        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        logger.info("哈哈");
    }

    public static void main(String[] args) {

        Integer aa = 128;
        int bbb = 128;

        System.out.println(aa == bbb);
    }

}
