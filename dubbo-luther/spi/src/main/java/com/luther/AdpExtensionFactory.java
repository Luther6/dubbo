package com.luther;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;

@Adaptive
public class AdpExtensionFactory
        implements ExtensionFactory {
    private final List<AdpExtensionFactory> factories;

    public AdpExtensionFactory() {
        ExtensionLoader<AdpExtensionFactory> loader = ExtensionLoader.getExtensionLoader(AdpExtensionFactory.class);
        ArrayList<AdpExtensionFactory> list = new ArrayList<AdpExtensionFactory>();
        for (Object name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension((String) name));
        }
        this.factories = Collections.unmodifiableList(list);
    }

    public <T> T getExtension(Class<T> type, String name) {
        for (AdpExtensionFactory factory : this.factories) {
            Object extension = factory.getExtension(type, name);
            if (extension == null) continue;
            return (T)extension;
        }
        return null;
    }
}