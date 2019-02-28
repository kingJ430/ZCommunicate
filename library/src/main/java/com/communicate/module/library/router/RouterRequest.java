package com.communicate.module.library.router;

import android.content.Context;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public class RouterRequest<T> {

    static AtomicInteger sIndex = new AtomicInteger(0);

    public AtomicBoolean isIdle = new AtomicBoolean(true);

    String provider;
    Context context;
    HashMap<String, String> data;
    T requestObject;

    public RouterRequest() {
        this.provider = "";
        context = null;
        this.data = new HashMap<>();
    }


    public RouterRequest provider(String provider) {
        this.provider = provider;
        return this;
    }



    public RouterRequest context(Context context) {
        this.context = context;
        return this;
    }

    public RouterRequest reqeustObject(T t) {
        this.requestObject = t;
        return this;
    }

    public RouterRequest data(String key, String data) {
        this.data.put(key, data);
        return this;
    }

    public String getProvider() {
        return provider;
    }


    public Context getContext() {
        return context;
    }


    public String getQuaryValue(String key) {
        return data.get(key);
    }


    public T getQuaryObject() {
        return requestObject;
    }

}
