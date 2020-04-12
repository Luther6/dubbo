package com.luther.impl;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.URL;

public class America implements PersonInterface {

    @Override
    public void getCountry(URL url) {
        System.out.println("Hi");
    }
}
