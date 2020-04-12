package com.luther;

import com.luther.api.CountryService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ApplicationConsumer {
    public static void main(String[] args) throws Throwable {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        //获取该类型的bean 在这里为ReferenceBean为FactoryBean在获取到该bean的时候回调用getObject()方法
        CountryService countryServiceImpl = (CountryService) applicationContext.getBean(CountryService.class);

        /**
         *  当前为代理对象{@link com.luther.Proxy0}
         */
        String country = countryServiceImpl.getCountry("luther");
        System.out.println(country);
        System.in.read();

    }
}
