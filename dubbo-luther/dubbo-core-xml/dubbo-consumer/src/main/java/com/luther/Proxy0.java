package com.luther;

/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  com.alibaba.dubbo.rpc.service.EchoService
 *  com.luther.api.CountryService
 *  org.apache.dubbo.common.bytecode.ClassGenerator
 *  org.apache.dubbo.common.bytecode.ClassGenerator$DC
 *  org.apache.dubbo.rpc.service.Destroyable
 */

import com.alibaba.dubbo.rpc.service.EchoService;
import com.luther.api.CountryService;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.apache.dubbo.common.bytecode.ClassGenerator;
import org.apache.dubbo.rpc.service.Destroyable;

public class Proxy0
        implements ClassGenerator.DC,
        CountryService,
        Destroyable,
        EchoService {
    public static Method[] methods;
    private InvocationHandler handler;

    public Object $echo(Object object) throws Throwable {
        Object[] arrobject = new Object[]{object};
        Object object2 = this.handler.invoke(this, methods[0], arrobject);
        return object2;
    }

    public String getCountry(String string) throws Throwable {
        Object[] arrobject = new Object[]{string};
        //这里将会调用invoke是我们之前生成InvokerInvocationHandler
        Object object = this.handler.invoke(this, methods[1], arrobject);
        return (String)object;
    }

    public void $destroy() throws Throwable {
        Object[] arrobject = new Object[]{};
        Object object = this.handler.invoke(this, methods[2], arrobject);
    }

    public Proxy0() {
    }

    public Proxy0(InvocationHandler invocationHandler) {
        this.handler = invocationHandler;
    }
}