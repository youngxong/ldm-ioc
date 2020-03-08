package com.oe.ioc.exception;


import com.oe.ioc.log.BaseLog;

public class ExceptionBuilder extends BaseLog {

    public static void buildIocException(String msg,Exception e){
        logError(msg,e);
        throw new IocException(msg);
    }

    public static void buildIocException(String  msg){
        logError(msg);
        throw new IocException(msg);
    }
}
