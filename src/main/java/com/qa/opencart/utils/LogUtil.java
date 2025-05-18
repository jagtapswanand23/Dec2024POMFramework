package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
    private static final Logger log = LogManager.getLogger(LogUtil.class);

    public static void info(String messg){
        log.info(messg);
    }
    public static void wanr(String messg){
        log.warn(messg);
    }
    public static void error(String messg){
        log.error(messg);
    }
    public static void fatal(String messg){
        log.fatal(messg);
    }
}
