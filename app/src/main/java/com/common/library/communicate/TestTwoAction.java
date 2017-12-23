package com.common.library.communicate;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.communicate.module.annotation.Action;
import com.communicate.module.library.base.action.BaseAction;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Action(privider = "test")
public class TestTwoAction implements BaseAction<String> {
    @Override
    public boolean isAsync( RouterRequest<String> routerRequest) {
        return false;
    }

    @Override
    public RouterRespone invoke(RouterRequest<String> routerRequest) {
        Log.d("TestActivity","test---" );
        RouterRespone result = new RouterRespone.Builder()
                .code(RouterRespone.CODE_SUCCESS)
                .msg("play success")
                .data("sdads")
//                .result(Observable.just(routerTestData))
                .result(new Fragment())
                .build();
        return result;
    }

    @Override
    public String getName() {
        return "test2";
    }
}
