package com.oe.ioc.test;

import com.oe.ioc.config.ApplicationConfig;
import com.oe.ioc.core.AnnotationApplicationContext;
import com.oe.ioc.listeners.TestEvent;
import com.oe.ioc.service.ITestAService;
import com.oe.ioc.service.ITestBService;
import com.oe.ioc.service.ITransferService;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class TestCase {

    /**
     * 测试加载配置的连接池
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        AnnotationApplicationContext annotationApplicationContext = new AnnotationApplicationContext(ApplicationConfig.class);
        DataSource datasource = (DataSource)annotationApplicationContext.getBean("dataSource");
        Connection connection = datasource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){

            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段的值
                Object value = resultSet.getObject(columnName);
                System.out.println(columnName+":"+value);
            }
        }
        connection.close();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 测试ComponentScan注解，进行bean的扫描,并且测试autowired
     */
    @Test
    public void test2(){
        AnnotationApplicationContext context = new AnnotationApplicationContext(ApplicationConfig.class);
        ITestBService testBService = (ITestBService)context.getBean("testBService");
        testBService.testB();
    }


    /**
     * 测试Aop
     */
    @Test
    public void test3(){
        AnnotationApplicationContext context = new AnnotationApplicationContext(ApplicationConfig.class);
        ITestAService testAService = (ITestAService)context.getBean("testAService");
        testAService.testA();
    }


    /**
     * 测试事务
     */
    @Test
    public void test4(){
        AnnotationApplicationContext context = new AnnotationApplicationContext(ApplicationConfig.class);
         ITestBService testBService = (ITestBService)context.getBean("testBService");
         testBService.testB();
    }


    /**
     * 测试事件机制
     */
    @Test
    public void test5(){
        AnnotationApplicationContext context = new AnnotationApplicationContext(ApplicationConfig.class);
        context.publish(new TestEvent("15646516"));
    }


    /**
     * 测试转账,带事务操作
     */
    @Test
    public void test6(){
        /**
         * 张三的账户
         */
        String accountno1="600320005698";
        /**
         * 李四的账户
         */
        String accountno2="632548899551";

        /**
         * 转账金额
         */
        BigDecimal money = new BigDecimal(300);
        AnnotationApplicationContext context = new AnnotationApplicationContext(ApplicationConfig.class);
        ITransferService transferService = (ITransferService)context.getBean("transferService");
        transferService.transfer(accountno1,accountno2,money);
    }


}
