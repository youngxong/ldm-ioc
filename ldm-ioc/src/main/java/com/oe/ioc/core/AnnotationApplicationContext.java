package com.oe.ioc.core;


import com.oe.ioc.annotation.*;
import com.oe.ioc.beans.Beanifition;
import com.oe.ioc.exception.ExceptionBuilder;
import com.oe.ioc.exception.IocException;
import com.oe.ioc.parsers.BeanParser;
import com.oe.ioc.parsers.ComponentScanParser;
import com.oe.ioc.parsers.ValueParser;
import com.oe.ioc.transaction.TransactionManager;
import com.oe.ioc.utils.ReflectUtil;
import com.oe.ioc.utils.ResourcesUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class AnnotationApplicationContext extends ApplicationContext {


    public AnnotationApplicationContext(Class clazz) {
        Class[] value =null;
        Import annotation = (Import)clazz.getAnnotation(Import.class);
        if(annotation==null||annotation.value()==null||annotation.value().length==0){
            value=new Class[]{clazz};
        }else {
           value = annotation.value();
        }
        loanConfigProperty(value);

        for (int i = 0; i < value.length; i++) {
            Class aClass = value[i];
            loanConfigClass(aClass);
        }
       TransactionManager.getInstance().setDataSource((DataSource) this.getBean("dataSource"));

        startInitContainer();
    }

    private void startInitContainer() {
        Set<Map.Entry<String, Beanifition>> entries = this.beanifitionMap.entrySet();
        for (Map.Entry<String, Beanifition> entry : entries) {
            //初始化所有的bean
            doGetBean(entry.getKey());
        }

    }

    private Object doGetBean(String beanName) {
        Object object=null;
        if(this.singleObjects.get(beanName)!=null){
            return  object=this.singleObjects.get(beanName);
        }
        Object earlyExpose = getEarlyExpose(beanName);
        if(earlyExpose!=null){
            return earlyExpose;
        }
        Beanifition beanifition = this.beanifitionMap.get(beanName);
        object=init(beanifition);
        populate(beanifition,object);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            object = beanPostProcessor.postProcessAfterInitialization(object, beanName);
        }
        this.registerSingleton(beanName,object);
        return object;
    }

    private Object getEarlyExpose(String beanName) {
        return this.earlyObjects.get(beanName);
    }

    private void populate(Beanifition beanifition, Object object) {
        Field[] declaredFields = beanifition.getClazz().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            Value value = declaredField.getAnnotation(Value.class);
            if(value!=null){
                Object properties = this.getProperties(getKey(value.value()));
                try {
                    declaredField.set(object,properties);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    ExceptionBuilder.buildIocException("设置属性异常！");
                }
            }

            Autowired autowired = declaredField.getAnnotation(Autowired.class);
            if(autowired!=null){
                Object o = doGetBean(autowired.name());
                try {
                    declaredField.set(object,o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    ExceptionBuilder.buildIocException("设置属性异常！");
                }
            }

        }


    }

    private Object init(Beanifition beanifition) {
        Object instanceByClass = ReflectUtil.getInstanceByClass(beanifition.getClazz());
        this.earlyObjects.put(beanifition.getId(),instanceByClass);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            instanceByClass=beanPostProcessor.postProcessBeforeInitialization(instanceByClass,beanifition.getId());
        }
        return instanceByClass;
    }


    private void loanConfigProperty(Class[] aClass) {
        Properties all =new Properties();

        for (int i = 0; i < aClass.length; i++) {
            Class aClass1 = aClass[i];
            PropertySource annotation =(PropertySource) aClass1.getAnnotation(PropertySource.class);
            if(annotation!=null){
                String[] paths = annotation.value();
                for (int j = 0; j < paths.length; j++) {
                    String path = paths[j];
                    InputStream resourceAsInputStream = ResourcesUtil.getResourceAsInputStream(path);
                    Properties properties = new Properties();
                    try {
                        properties.load(resourceAsInputStream);
                        all.putAll(properties);
                    } catch (IOException e) {
                        e.printStackTrace();
                        ExceptionBuilder.buildIocException("加载配置异常！");
                    }
                }
            }

        }
        this.initProperties(all);
    }

    private void loanConfigClass(Class clazz) {

        Annotation annotation = clazz.getAnnotation(Configuration.class);
        if(null==annotation){
            throw new IocException("当前类未定义为配置类!");
        }
        Object instanceByClass = ReflectUtil.getInstanceByClass(clazz);
        //1.类上的注解
        ComponentScan componentScan =(ComponentScan) clazz.getAnnotation(ComponentScan.class);
        if(componentScan!=null){
            ComponentScanParser componentScanParser = new ComponentScanParser(this, clazz);
            componentScanParser.parse();
        }

        //2.解析字段
        ValueParser valueParser = new ValueParser(this,clazz, instanceByClass);
        valueParser.parse();


        //3.解析方法
        BeanParser beanParser = new BeanParser(this,clazz, instanceByClass);
        beanParser.parse();


        //把配置类的实例注册进容器
        registerConfig(clazz,instanceByClass);

    }

    private String getKey(String pattern) {
        int start = pattern.indexOf("{");
        int end = pattern.indexOf("}");
        return  pattern.substring(start+1,end);
    }
}
