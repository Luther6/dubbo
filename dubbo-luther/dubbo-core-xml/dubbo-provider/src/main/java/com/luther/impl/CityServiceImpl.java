package com.luther.impl;

import com.luther.api.CityService;

public class CityServiceImpl implements CityService {
    @Override
    public String getCity(String name) {
        return "suqian";
    }
}
