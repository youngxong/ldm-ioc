package com.oe.ioc.parsers;

import com.oe.ioc.annotation.Value;
import com.oe.ioc.core.AnnotationApplicationContext;
import com.oe.ioc.exception.ExceptionBuilder;

import java.lang.reflect.Field;

public class ValueParser {

    AnnotationApplicationContext context;

    Class configBeanClass;

    Object configBean;

    public ValueParser(AnnotationApplicationContext context,Class configBeanClass ,Object configBean) {
        this.context = context;
        this.configBeanClass = configBeanClass;
        this.configBean = configBean;
    }

    public  void parse(){
        Field[] declaredFields = configBeanClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            Value value = declaredField.getAnnotation(Value.class);
            if(value!=null){
                String pattern = value.value();
                String key = getKey(pattern);
                Object properties = context.getProperties(key);
                declaredField.setAccessible(true);
                try {
                    declaredField.set(configBean,properties);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    ExceptionBuilder.buildIocException("设置值异常");
                }
            }

        }
    }

        private String getKey(String pattern) {
            int start = pattern.indexOf("{");
            int end = pattern.indexOf("}");
            return  pattern.substring(start+1,end);
        }
}
