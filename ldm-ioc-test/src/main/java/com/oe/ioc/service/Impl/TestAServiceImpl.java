package com.oe.ioc.service.Impl;

import com.oe.ioc.annotation.Autowired;
import com.oe.ioc.annotation.LdmComponent;
import com.oe.ioc.annotation.Transactional;
import com.oe.ioc.service.ITestAService;
import com.oe.ioc.service.ITestBService;

@LdmComponent("testAService")
public class TestAServiceImpl implements ITestAService {

    @Autowired(name="testBService")
    ITestBService testBService;

    @Transactional
    public void testA() {
        System.out.println("testA");
    }
}
