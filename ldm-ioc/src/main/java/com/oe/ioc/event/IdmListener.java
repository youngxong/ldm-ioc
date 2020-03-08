package com.oe.ioc.event;

public interface IdmListener<T extends LdmEvent> {

    void process(T t);
}
