package com.common.library.communicate;

import android.util.Log;

import com.communicate.module.annotation.Action;
import com.communicate.module.library.base.action.BaseAction;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;
import com.example.test.RouterTestData;

import rx.Observable;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Action(privider = "main")
public class TestAction implements BaseAction<RouterTestData> {
    @Override
    public boolean isAsync( RouterRequest<RouterTestData> routerRequest) {
        return false;
    }

    @Override
    public RouterRespone invoke(RouterRequest<RouterTestData> routerRequest) {
        Log.d("TestActivity","test---main -- id =" +
                routerRequest.getRequestObject().getId() + " name=  " +
                        routerRequest.getRequestObject().getName());
        RouterTestData routerTestData = new RouterTestData();
        routerTestData.setId("2");
        routerTestData.setName("qq");
        RouterRespone result = new RouterRespone.Builder()
                .code(RouterRespone.CODE_SUCCESS)
                .msg("play success")
                .data("sdads")
                .result(Observable.just(routerTestData))
//                .result(routerTestData)
                .build();
        return result;
    }

    @Override
    public String getName() {
        return "Test";
    }
}
