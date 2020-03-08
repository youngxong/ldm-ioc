package com.oe.ioc.parsers;

import com.oe.ioc.annotation.*;
import com.oe.ioc.aop.AspectBean;
import com.oe.ioc.aop.AspectEnhancer;
import com.oe.ioc.beans.Beanifition;
import com.oe.ioc.core.AnnotationApplicationContext;
import com.oe.ioc.event.IdmListener;
import com.oe.ioc.transaction.TransactionEnhancer;
import com.oe.ioc.utils.ReflectUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ComponentParser {

    private Class clazz;

    private AnnotationApplicationContext context;

    public ComponentParser(Class clazz, AnnotationApplicationContext context) {
        this.clazz = clazz;
        this.context = context;
    }

    public void parse(){
        LdmComponent ldmComponent = (LdmComponent)clazz.getAnnotation(LdmComponent.class);
        if(ldmComponent!=null){
            if(IdmListener.class.isAssignableFrom(clazz)){
                context.addListener((IdmListener) ReflectUtil.getInstanceByClass(clazz));
            }else {
                Beanifition beanifition = new Beanifition();
                beanifition.setId(ldmComponent.value());
                beanifition.setClazz(clazz);
                context.registerBeanfition(beanifition);
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<String> met= new ArrayList<>();
                for (Method declaredMethod : declaredMethods) {
                    Transactional transactional = declaredMethod.getAnnotation(Transactional.class);
                    if(transactional!=null){
                        String name = declaredMethod.getName();
                        int indexOf = name.lastIndexOf(".");
                        String substring = name.substring(indexOf + 1, name.length());
                        met.add(substring);
                    }
                }
                if(met.size()>0){
                    context.addBeanPostProcessor(new TransactionEnhancer(met));
                }
            }
        }
        Aspect aspect = (Aspect)clazz.getAnnotation(Aspect.class);
        if(aspect!=null){
            Method before=null;
            Method after=null;
            String pointcut=null;
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                Method declaredMethod = declaredMethods[i];
                Before boforeyy = declaredMethod.getAnnotation(Before.class);
                if(boforeyy!=null){
                    before=declaredMethod;
                    continue;
                }
                After afteryy = declaredMethod.getAnnotation(After.class);
                if(afteryy!=null){
                    after=declaredMethod;
                    continue;
                }
                PointCut pointCutyy = declaredMethod.getAnnotation(PointCut.class);
                if(pointCutyy!=null){
                    pointcut=pointCutyy.rule();
                }
            }
            AspectBean aspectBean = new AspectBean(ReflectUtil.getInstanceByClass(clazz), pointcut, before, after);
            context.addBeanPostProcessor(new AspectEnhancer(aspectBean,context));
        }
    }
}
