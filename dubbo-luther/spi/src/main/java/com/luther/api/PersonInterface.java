package com.luther.api;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("chinese")//可以设置默认的扩展名
public interface PersonInterface {

    @Adaptive(value = "country")
    void getCountry(URL url);
}
