package com.oe.ioc.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class AopInvocation {


    List<String> list = new ArrayList<>();

    InvocationHandler h;

    Class interfaceClass;

    Object object;


    public AopInvocation(List<String> list,Object object, Class interfaceClass) {
        this.list = list;
        this.object=object;
        this.interfaceClass=interfaceClass;
        this.h=new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                int last = method.getName().lastIndexOf(".");
                String substring = method.getName().substring(last + 1, method.getName().length());
                if(list.contains(substring)){
                    return enhance(new Invocation(method,args,object));
                }
                return method.invoke(object,args);
            }
        };
        }

    public abstract Object enhance(Invocation invocation);


    public InvocationHandler getH() {
        return h;
    }

    public void setH(InvocationHandler h) {
        this.h = h;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
