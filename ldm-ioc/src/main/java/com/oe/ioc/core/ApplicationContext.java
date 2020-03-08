package com.oe.ioc.core;

import com.oe.ioc.beans.Beanifition;
import com.oe.ioc.event.IdmListener;
import com.oe.ioc.event.LdmEvent;
import com.oe.ioc.utils.ReflectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext extends DefaultBeanFactory {

    Map<Class,IdmListener> listenerMap = new HashMap<>();

    List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    public void loadDefaultListener(){
        //todo
    }

    public void publish(LdmEvent ldmEvent){
        IdmListener idmListener = listenerMap.get(ldmEvent.getClass());
        if(idmListener!=null){
            idmListener.process(ldmEvent);
        }
    }

    public void addListener(IdmListener listener){
        Class genericSuperclass = ReflectUtil.getGenericSuperclass(listener.getClass());
        IdmListener idmListener = listenerMap.get(genericSuperclass);
        if(idmListener==null){
            listenerMap.put(genericSuperclass,listener);
        }
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor){
        beanPostProcessors.add(postProcessor);
    }


}
