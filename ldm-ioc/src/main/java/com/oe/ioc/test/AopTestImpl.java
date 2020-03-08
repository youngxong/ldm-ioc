package com.oe.ioc.test;

public class AopTestImpl implements AopTest {

    @Override
    public void test() {
        System.out.println("aop");
    }

    @Override
    public void haha() {
        System.out.println("jajaj");
    }
}
