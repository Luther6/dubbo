package com.luther.impl;

import com.luther.api.CountryService;

public class CountryServiceImpl implements CountryService {
    @Override
    public String getCountry(String name) {
        System.out.println(name);
        if(name.length()>4){
            System.out.println(12);
            return "America";
        }
        System.out.println(23);
        return "Chinese";
    }
}
