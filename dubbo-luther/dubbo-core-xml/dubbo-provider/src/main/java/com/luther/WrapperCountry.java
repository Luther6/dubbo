package com.luther;


import com.luther.api.CountryService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.dubbo.common.bytecode.ClassGenerator;
import org.apache.dubbo.common.bytecode.NoSuchMethodException;
import org.apache.dubbo.common.bytecode.NoSuchPropertyException;
import org.apache.dubbo.common.bytecode.Wrapper;

public class WrapperCountry
        extends Wrapper
        implements ClassGenerator.DC {
    public static String[] pns;
    public static Map pts;
    public static String[] mns;
    public static String[] dmns;
    public static Class[] mts0;

    public Object invokeMethod(Object object, String string, Class[] arrclass, Object[] arrobject) throws InvocationTargetException {
        CountryService countryService;
        try {
            countryService = (CountryService) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        try {
            if ("getCountry".equals(string) && arrclass.length == 1) {
                return countryService.getCountry((String) arrobject[0]);
            }
        } catch (Throwable throwable) {
            throw new InvocationTargetException(throwable);
        }
        throw new NoSuchMethodException(new StringBuffer().append("Not found method \"").append(string).append("\" in class com.luther.api.CountryService."
        ).toString());
    }

    @Override
    public String[] getPropertyNames() {
        return pns;
    }

    public Class getPropertyType(String string) {
        return (Class) pts.get(string);
    }

    @Override
    public Object getPropertyValue(Object object, String string) {
        try {
            CountryService countryService = (CountryService) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or setter method in class com.luther.api.CountryService.").toString());
    }

    @Override
    public void setPropertyValue(Object object, String string, Object object2) {
        try {
            CountryService countryService = (CountryService) object;
        } catch (Throwable throwable) {
            throw new IllegalArgumentException(throwable);
        }
        throw new NoSuchPropertyException(new StringBuffer().append("Not found property \"").append(string).append("\" field or setter method in class com. luther.api.CountryService.").toString());
    }

    @Override
    public String[] getMethodNames() {
        return mns;
    }

    @Override
    public String[] getDeclaredMethodNames() {
        return dmns;
    }

    @Override
    public boolean hasProperty(String string) {
        return pts.containsKey(string);
    }
}