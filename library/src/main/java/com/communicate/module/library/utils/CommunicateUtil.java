package com.communicate.module.library.utils;

import com.communicate.module.library.base.provider.BaseProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * user: zhangjianfeng
 * date: 05/09/2017
 * version: 7.3
 */

public class CommunicateUtil {

    private static Map<String ,BaseProvider> providerMaps = new HashMap<>();
    public static String[] moduleName = null;

    public synchronized static void setup(String[] bundlesName) {
        moduleName = bundlesName;
        initProvider();
    }

    public static void map(String key,BaseProvider baseProvider) {
        providerMaps.put(key,baseProvider);
    }

    private static void initProvider() {
        if (moduleName == null) {
            return;
        }
        for (String bundleName : moduleName) {
            try {
                Class<?> c1 = Class.forName("com.provider.PrividerMap" + bundleName);
                c1.getMethod("init").invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Map<String, BaseProvider> getProviderMaps() {
        return providerMaps;
    }

    public static BaseProvider getProvider(String provider) {
        return providerMaps.get(provider);
    }
}
