package com.oe.ioc.utils;

import java.io.InputStream;

public class ResourcesUtil {

    public static InputStream getResourceAsInputStream(String path){
       return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
}
