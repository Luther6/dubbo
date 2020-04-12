package com.luther;

import com.luther.api.CountryService;

public class MockCountryServiceImpl implements CountryService{


    @Override
    public String getCountry(String name) throws Throwable {
        return "mock";
    }
}
