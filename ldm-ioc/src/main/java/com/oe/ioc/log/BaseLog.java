package com.oe.ioc.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseLog {

    public static final Logger logger = LoggerFactory.getLogger(BaseLog.class);

    public static void logInfo(String pattern,Object...msg){
        logger.info(pattern,msg);
    }

    public static void logError(String pattern,Object o,Exception e){
        logger.error(pattern,o,e);
    }

    public static void logError(String pattern,Object o){
        logger.error(pattern,o);
    }

    public static void logError(String msg,Exception e){
        logger.error(msg,e);
    }

    public static void logError(String msg){
        logger.error(msg);
    }

}
