package com.luther;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) throws IOException {
        //获取扩展加载器
        ExtensionLoader<PersonInterface> extensionLoader = ExtensionLoader.getExtensionLoader(PersonInterface.class);
        Map<String,String> map = new HashMap<>();
        map.put("country","america");
        URL url = new URL("", "", 1, map);
        //根据扩展名获取到对应的代理类
        PersonInterface chinese = extensionLoader.getExtension("chinese");
        chinese.getCountry(url);
        System.in.read();
    }
}
