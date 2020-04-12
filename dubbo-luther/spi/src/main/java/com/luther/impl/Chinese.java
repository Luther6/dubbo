package com.luther.impl;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.Extension;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.DisableInject;

@Extension("chinese")
public class Chinese implements PersonInterface {

    private PersonInterface personInterface;

    //注入点
    //@DisableInject  :表明该方法不会被自动注入调用
    public void setPersonInterface(PersonInterface personInterface) {
        this.personInterface = personInterface;
    }

    @Override
    @Adaptive //@DisableInject
    public void getCountry(URL url) {
        System.out.println("你好");
        personInterface.getCountry(url);

    }
}
