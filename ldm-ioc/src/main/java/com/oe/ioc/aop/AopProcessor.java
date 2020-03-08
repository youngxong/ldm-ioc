package com.oe.ioc.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class AopProcessor {

   public static List<Method> matchMethods(Class clazz){
      return null;
   };

   public static Object proxy(AopInvocation aopInvocation){
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      return Proxy.newProxyInstance(classLoader,new Class[]{aopInvocation.getInterfaceClass()},aopInvocation.getH());
   }



}
