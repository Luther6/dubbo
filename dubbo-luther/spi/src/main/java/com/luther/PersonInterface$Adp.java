
package com.luther;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

public class PersonInterface$Adp
        implements PersonInterface {
    public void getCountry(URL uRL) {
        if (uRL == null) {
            throw new IllegalArgumentException("url == null");
        }
        URL uRL2 = uRL;
        //根据URL传递参数来实现依赖注入的效果 --11
        String string = uRL2.getParameter("country", "chinese");
        if (string == null) {
            throw new IllegalStateException(new StringBuffer().
                    append("Failed to get extension (com.luther.api.PersonInterface) name from url (").
                    append(uRL2.toString()).append(") use keys([country])").toString());
        }
        //首先会获取
        PersonInterface personInterface = (PersonInterface) ExtensionLoader.getExtensionLoader(PersonInterface.class).getExtension(string);
        personInterface.getCountry(uRL);
    }
}