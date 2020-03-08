package com.oe.ioc.parsers;

import com.oe.ioc.annotation.Bean;
import com.oe.ioc.core.AnnotationApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanParser {


    AnnotationApplicationContext context;

    Object configBean;

    Class configBeanClass;

    public BeanParser(AnnotationApplicationContext context,Class configBeanClass ,Object configBean) {
        this.context = context;
        this.configBean = configBean;
        this.configBeanClass=configBeanClass;
    }

    public  void parse(){
        Method[] declaredMethods = configBeanClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method method = declaredMethods[i];
            Class<?> beanClass = method.getReturnType();
            Bean annotation = method.getAnnotation(Bean.class);
            if(annotation!=null){
                try {
                    context.registerSingleton(annotation.id(),method.invoke(configBean,null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
