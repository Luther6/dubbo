package com.luther.wrapper;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.URL;

public class PersonWrapper implements PersonInterface{
    public PersonInterface personInterface;

    public PersonWrapper(PersonInterface personInterface) {
        this.personInterface = personInterface;
    }

    @Override
    public void getCountry(URL url) {
        System.out.println("before");
        personInterface.getCountry(url);
        System.out.println("after");
    }
}
