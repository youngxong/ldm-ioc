package com.oe.ioc.aop;

import com.oe.ioc.beans.Beanifition;
import com.oe.ioc.core.ApplicationContext;
import com.oe.ioc.core.BeanPostProcessor;
import com.oe.ioc.exception.ExceptionBuilder;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 切面增强器
 */
public class AspectEnhancer implements BeanPostProcessor {

    ApplicationContext context;

    AspectBean aspectBean;

    public AspectEnhancer(AspectBean aspectBean,ApplicationContext context) {
        this.aspectBean = aspectBean;
        this.context=context;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> i = bean.getClass().getInterfaces()[0];
        List<String> methods = ifNeedToAop(bean, beanName);
        if (methods.size()==0){
            return  bean;
        }
        return  AopProcessor.proxy(new AopInvocation(methods,bean,i) {
            @Override
            public Object enhance(Invocation invocation) {
                Object o=null;
                try {
                    if(aspectBean.getBofore()!= null){
                        aspectBean.getBofore().invoke(aspectBean.getO(),null);
                    }

                    o=invocation.invoke();

                    if(aspectBean.getAfter()!=null){
                        aspectBean.getAfter().invoke(aspectBean.getO(),null);
                    }
                } catch (Exception e) {
                    ExceptionBuilder.buildIocException("异常!");
                }

                return o;
            }
        });
    }

    /**
     * 笨拙的实现,后续优化吧
     * @param bean
     * @param beanName
     * @return
     */
    private List<String> ifNeedToAop(Object bean, String beanName) {
        List<String> list = new ArrayList<>();
        /**
         * 目前只能切具体某个类的所有方法或者指定方法
         * 指定类的所有方法:com.oe.ioc.xxClass.*
         * 指定类的某些方法:com.oe.ioc.xxClass.test,clean,dosomething
         */
        String pointcut = aspectBean.getPointcut();
        int i = pointcut.lastIndexOf(".");
        String className=pointcut.substring(0,i);
        String methods = pointcut.substring(i + 1, pointcut.length());
        Beanifition beanfition = context.getBeanfition(beanName);
        String beanClassName = beanfition.getClazz().getName();
        if(className.equals(beanClassName)){
            ArrayList<Method>  methodList= new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredMethods()));
            if(methods.equals("*")){
                list.addAll(toStr(methodList));
            }else {
                String[] methodNames = methods.split(",");
                List<String> methodNameList = Arrays.asList(methodNames);
                for (Method method : methodList) {
                    String name = method.getName();
                    int indexOf = name.lastIndexOf(".");
                    String substring = name.substring(indexOf + 1, name.length());
                    if(methodNameList.contains(substring)){
                        list.add(substring);
                    }
                }
            }
        }else {
            return list;
        }
        return list;
    }

    private  List<String> toStr(ArrayList<Method> methodList) {
        List<String> a = new ArrayList<>();
        for (Method method : methodList) {
            String name = method.getName();
            int indexOf = name.lastIndexOf(".");
            String substring = name.substring(indexOf + 1, name.length());
            a.add(substring);
        }
        return a;
    }
}
