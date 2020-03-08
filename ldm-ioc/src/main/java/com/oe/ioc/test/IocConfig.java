package com.oe.ioc.test;

import com.oe.ioc.annotation.ComponentScan;
import com.oe.ioc.annotation.Configuration;

@Configuration
@ComponentScan(scan = {"com.oe.ioc.test"})
public class IocConfig {

}
