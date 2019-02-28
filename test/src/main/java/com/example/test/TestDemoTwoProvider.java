package com.example.test;

import com.communicate.module.annotation.Provider;
import com.communicate.module.library.base.provider.BaseProvider;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

/**
 * user: zhangjianfeng
 * date: 05/09/2017
 * version: 7.3
 */
@Provider(name = "test/test3")
public class TestDemoTwoProvider implements BaseProvider {

    @Override
    public boolean isAsync(RouterRequest routerRequest) {
        return false;
    }

    @Override
    public RouterRespone invoke(RouterRequest routerRequest) {
        return null;
    }

    @Override
    public String getName() {
        return "test/test2";
    }
}
