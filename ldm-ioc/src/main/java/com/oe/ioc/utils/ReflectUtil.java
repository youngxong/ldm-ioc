package com.oe.ioc.utils;


import com.oe.ioc.exception.ExceptionBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtil {

    public static Object getInstanceByClassName(String className){
        Class clazz=null;
        try {
            clazz =Class.forName(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            ExceptionBuilder.buildIocException("类找不到",e);
        } catch (IllegalAccessException e) {
            ExceptionBuilder.buildIocException("反射获取实例异常",e);
        } catch (InstantiationException e) {
            ExceptionBuilder.buildIocException("反射获取实例异常",e);
        }
        return null;
    }

    public static Object getInstanceByClass(Class clazz){
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            ExceptionBuilder.buildIocException("反射获取实例异常",e);
        } catch (InstantiationException e) {
            ExceptionBuilder.buildIocException("反射获取实例异常",e);
        }
        return null;
    }


    public static Class classForName(String className){
        Class clazz=null;
        try {
            return  Class.forName(className);
        } catch (ClassNotFoundException e) {
            ExceptionBuilder.buildIocException("类找不到",e);
        }
        return null;
    }

    public static Class getGenericSuperclass(Class child){
        Class clazz=null;
        try {
            Type[] genericInterfaces = child.getGenericInterfaces();
            for (Type type : genericInterfaces) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] types = parameterizedType.getActualTypeArguments();
                clazz = (Class) types[0];
            }

        return clazz;
        } catch (Exception e) {
            ExceptionBuilder.buildIocException("类找不到",e);
        }
        return null;
    }
}
