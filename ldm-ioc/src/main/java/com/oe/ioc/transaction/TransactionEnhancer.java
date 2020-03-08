package com.oe.ioc.transaction;

import com.oe.ioc.aop.AopInvocation;
import com.oe.ioc.aop.AopProcessor;
import com.oe.ioc.aop.Invocation;
import com.oe.ioc.core.BeanPostProcessor;
import com.oe.ioc.exception.ExceptionBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * 事务增强器
 */
public class TransactionEnhancer implements BeanPostProcessor {

    List<String> methods;

    public TransactionEnhancer(List<String> methods) {
        this.methods = methods;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Class<?> i = bean.getClass().getInterfaces()[0];
        if (methods.size()==0){
            return  bean;
        }
        return  AopProcessor.proxy(new AopInvocation(methods,bean,i) {
            @Override
            public Object enhance(Invocation invocation) {
                Transaction transaction = null;
                Object o=null;
                try {
                    transaction = TransactionManager.getInstance().getTransaction();
                    if(transaction==null){
                        transaction= TransactionManager.getInstance().start();
                    }
                    o=invocation.invoke();
                    transaction.commit();
                }catch (Exception e){
                    try {
                        transaction.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    ExceptionBuilder.buildIocException("执行异常:",e);
                }finally {
                    try {
                        transaction.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return o;
            }
        });
    }
}
