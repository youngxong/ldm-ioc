package com.oe.ioc.beans;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

public class Beanifition {

    private String id;

    private Class clazz;

    private String type;

    private Properties properties;

    private List<FieldProperty> fieldPropertys;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public List<FieldProperty> getFieldPropertys() {
        return fieldPropertys;
    }

    public void addFieldProperty(FieldProperty fieldProperty) {
        this.fieldPropertys .add(fieldProperty);
    }

    @Override
    public String toString() {
        return "Beanifition{" +
                "id='" + id + '\'' +
                ", clazz=" + clazz +
                ", type='" + type + '\'' +
                ", properties=" + properties +
                ", fieldPropertys=" + fieldPropertys +
                '}';
    }
}
