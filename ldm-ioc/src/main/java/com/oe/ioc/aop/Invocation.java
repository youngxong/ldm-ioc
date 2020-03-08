package com.oe.ioc.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Invocation {

    Method method;
    Object[] args;
    Object  obj;

    public Invocation(Method method,Object[] args,Object obj) {
        this.method = method;
        this.args = args;
        this.obj = obj;
    }

    public  Object invoke() throws Exception{
        return method.invoke(obj,args);

    }
}
