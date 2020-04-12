package com.luther;


import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ApplicationProvider {
    public static void main(String[] args) throws IOException {
//        DubboBootstrap.getInstance().exportAsync();
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/dubbo-provider.xml");
        applicationContext.start();
        System.in.read();
    }
}
