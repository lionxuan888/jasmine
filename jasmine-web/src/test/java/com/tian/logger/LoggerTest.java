package com.tian.logger;

import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/8/5.
 */
public class LoggerTest {

    @Test
    public void testLogger() throws Exception {
        Logger logger = Logger.getLogger("test");
        logger.info("hah");


    }
}
