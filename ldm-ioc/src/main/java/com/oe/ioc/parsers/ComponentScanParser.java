package com.oe.ioc.parsers;

import com.oe.ioc.annotation.Aspect;
import com.oe.ioc.annotation.ComponentScan;
import com.oe.ioc.core.AnnotationApplicationContext;
import com.oe.ioc.utils.ClassLoadUtil;
import com.oe.ioc.utils.ReflectUtil;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.util.List;

public class ComponentScanParser {

    private Class configClass;

    private AnnotationApplicationContext context;

    public ComponentScanParser(AnnotationApplicationContext context,Class configClass) {
        this.context = context;
        this.configClass=configClass;
    }

    public void parse(){
        ComponentScan annotation = (ComponentScan)configClass.getAnnotation(ComponentScan.class);
        String[] scan = annotation.scan();
        for (int i = 0; i < scan.length; i++) {
            String path = scan[i];
            List<String> classStr = ClassLoadUtil.loadClassByPackage(path);
            registerBeanifition(classStr);
        }
    }

    private void registerBeanifition(List<String> classStr) {
        for (String clazzName : classStr) {
            Class clazz = ReflectUtil.classForName(clazzName);
            ComponentParser componentParser = new ComponentParser(clazz, context);
            componentParser.parse();
        }
    }


}
