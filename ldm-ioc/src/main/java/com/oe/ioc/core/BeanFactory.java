package com.oe.ioc.core;

public interface BeanFactory {

    Object getBean(String name);

    Object getBean(Class c);

    void close();
}
