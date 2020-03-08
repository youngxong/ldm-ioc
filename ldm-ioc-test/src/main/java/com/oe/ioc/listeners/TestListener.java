package com.oe.ioc.listeners;

import com.oe.ioc.annotation.LdmComponent;
import com.oe.ioc.event.IdmListener;

@LdmComponent("testListener")
public class TestListener implements IdmListener<TestEvent> {

    public void process(TestEvent testEvent) {
        String aaa=(String)testEvent.getSource();
        System.out.println(aaa);
    }
}
