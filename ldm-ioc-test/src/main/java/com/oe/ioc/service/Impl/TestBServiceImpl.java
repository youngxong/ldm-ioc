package com.oe.ioc.service.Impl;

import com.oe.ioc.annotation.Autowired;
import com.oe.ioc.annotation.LdmComponent;
import com.oe.ioc.annotation.Transactional;
import com.oe.ioc.service.ITestAService;
import com.oe.ioc.service.ITestBService;

import java.math.BigDecimal;

@LdmComponent("testBService")
public class TestBServiceImpl implements ITestBService {

    @Autowired(name="testAService")
    ITestAService testAService;

    @Transactional
    public void testB() {
        System.out.println("testBService");
        testAService.testA();
    }
}
