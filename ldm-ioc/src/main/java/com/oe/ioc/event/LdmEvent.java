package com.oe.ioc.event;

public abstract class LdmEvent {

    Object source;

    public LdmEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}
