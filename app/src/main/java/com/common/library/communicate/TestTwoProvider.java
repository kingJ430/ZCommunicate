package com.common.library.communicate;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.communicate.module.annotation.Provider;
import com.communicate.module.library.base.provider.BaseProvider;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Provider(name = "test/test2")
public class TestTwoProvider implements BaseProvider<String> {

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
//                .result(Observable.just(routerTestData))
                .result(new Fragment())
                .build();
        return result;
    }

    @Override
    public String getName() {
        return null;
    }
}
