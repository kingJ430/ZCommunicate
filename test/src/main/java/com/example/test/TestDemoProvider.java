package com.example.test;

import com.communicate.module.annotation.Provider;
import com.communicate.module.library.base.provider.BaseProvider;

/**
 * user: zhangjianfeng
 * date: 05/09/2017
 * version: 7.3
 */
@Provider(name = "test1")
public class TestDemoProvider extends BaseProvider{
    @Override
    protected String getName() {
        return null;
    }
}
