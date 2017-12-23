package com.example.test;

import android.content.Context;

import com.communicate.module.annotation.Action;
import com.communicate.module.library.base.action.BaseAction;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

/**
 * user: zhangjianfeng
 * date: 05/09/2017
 * version: 7.3
 */
@Action(privider = "test1")
public class TestDemoAction implements BaseAction {
    @Override
    public boolean isAsync(RouterRequest routerRequest) {
        return false;
    }

    @Override
    public RouterRespone invoke( RouterRequest routerRequest) {
        return null;
    }

    @Override
    public String getName() {
        return "aa";
    }
}
