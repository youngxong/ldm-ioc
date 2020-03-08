package com.oe.ioc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.oe.ioc.annotation.*;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "application.properties")
@ComponentScan(scan = {"com.oe.ioc.service.Impl.*","com.oe.ioc.aspects.*","com.oe.ioc.listeners.*","com.oe.ioc.dao.impl.*"})
public class ApplicationConfig {

    @Value("#{driverClass}")
    private String driverClass;


    @Value("#{jdbcUrl}")
    private String jdbcUrl;


    @Value("#{username}")
    private String username;


    @Value("#{password}")
    private String password;



    @Bean(id="dataSource")
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;

    }
}


