package com.communicate.module.library.service;


import android.util.Log;

import com.communicate.module.library.utils.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public class Communicate {

    private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache = new ConcurrentHashMap<>();

    public <T> T create(final Class<T> service) {
        Utils.validateServiceInterface(service);
        eagerlyValidateMethods(service);
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                new InvocationHandler() {

                    @Override public Object invoke(Object proxy, Method method,  Object[] args)
                            throws Throwable {
                        ServiceMethod<Object, Object> serviceMethod =
                                (ServiceMethod<Object, Object>) loadServiceMethod(method);
                        CommunicateCall<Object> communicateCall = new CommunicateCall<>(serviceMethod, args);
                        boolean isObservable = serviceMethod.isObservable();
                        Log.e("isObservable",isObservable + "");
                        if(isObservable) {
                            return serviceMethod.rxAdapt(communicateCall);
                        } else {
                            return serviceMethod.adapt(communicateCall);
                        }
                    }
                });
    }

    private void eagerlyValidateMethods(Class<?> service) {
        for (Method method : service.getDeclaredMethods()) {
            loadServiceMethod(method);
        }
    }

    ServiceMethod<?, ?> loadServiceMethod(Method method) {
        ServiceMethod<?, ?> result = serviceMethodCache.get(method);
        if (result != null) return result;

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder<>(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }

}
