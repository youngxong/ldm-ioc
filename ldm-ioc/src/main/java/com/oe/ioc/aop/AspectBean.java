package com.oe.ioc.aop;

import java.lang.reflect.Method;

public class AspectBean {

    private Object o;

    private String pointcut;

    private Method bofore;

    private Method after;


    public AspectBean(Object o,String pointcut, Method bofore, Method after) {
        this.o=o;
        this.pointcut = pointcut;
        this.bofore = bofore;
        this.after = after;
    }


    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public Method getBofore() {
        return bofore;
    }

    public void setBofore(Method bofore) {
        this.bofore = bofore;
    }

    public Method getAfter() {
        return after;
    }

    public void setAfter(Method after) {
        this.after = after;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}
