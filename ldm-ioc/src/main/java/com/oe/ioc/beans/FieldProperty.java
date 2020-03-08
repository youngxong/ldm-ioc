package com.oe.ioc.beans;

import java.lang.reflect.Field;

public class FieldProperty {

    private Field field;

    private String value;

    public FieldProperty(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
