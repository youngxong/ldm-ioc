package com.oe.ioc.aspects;

import com.oe.ioc.annotation.After;
import com.oe.ioc.annotation.Aspect;
import com.oe.ioc.annotation.Before;
import com.oe.ioc.annotation.PointCut;

@Aspect
public class MyAspect {

    /**
     * 目前AOP的规则只支持切面到某个类的所有方法或指定方法
     * 例如
     * 1.TestAServiceImpl的所有方法:com.oe.ioc.service.Impl.TestAServiceImpl.*
     * 2.TestAServiceImpl的指定方法:com.oe.ioc.service.Impl.TestAServiceImpl.testA
     * 3.其他规则不支持
     */
    @PointCut(rule = "com.oe.ioc.service.Impl.TestAServiceImpl.*")
    public void pointcut(){

    }

    @Before
    public void before(){
        System.out.println("在执行之前干点事情");
    }

    @After
    public void after(){
        System.out.println("在执行之后干点事情");
    }
}
