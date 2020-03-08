package com.oe.ioc.core;

import com.oe.ioc.annotation.Value;
import com.oe.ioc.beans.Beanifition;
import com.oe.ioc.exception.IocException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultBeanFactory implements BeanFactory{

    Map<String,Object> singleObjects=new HashMap<String, Object>();

    Map<String,Object> earlyObjects=new HashMap<String, Object>();

    Map<String,Beanifition> beanifitionMap = new HashMap<>();

    Map<Class,Object> configMap = new HashMap<>();

    Properties globalProperties;


    public Object getBean(String name) {
        return singleObjects.get(name);
    }

    public Object getBean(Class c) {
        return null;
    }

    @Override
    public void close() {
        singleObjects=null;
        beanifitionMap=null;
    }

    public void   initProperties(Properties properties){
        this.globalProperties=properties;
    }


    public  void registerBeanfition(Beanifition beanifition){
        if(beanifitionMap.get(beanifition.getId())!=null){
            throw  new IocException("id confict!");
        }
        beanifitionMap.put(beanifition.getId(),beanifition);
    }

    public  Beanifition getBeanfition(String id){
       return beanifitionMap.get(id);
    }

    public  void registerConfig(Class clazz,Object o){
       configMap.put(clazz,o);
    }


    public  void registerSingleton(String id,Object o){
        singleObjects.put(id,o);
    }

    public  Object getProperties(String key){
        return globalProperties.get(key);
    }

}
