package com.common.library.communicate;

import android.util.Log;

import com.communicate.module.annotation.Provider;
import com.communicate.module.library.base.provider.BaseProvider;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;
import com.example.test.RouterTestData;


/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Provider(name = "main/test")
public class TestProvider implements BaseProvider<RouterTestData> {

    @Override
    public boolean isAsync( RouterRequest<RouterTestData> routerRequest) {
        return false;
    }

    @Override
    public RouterRespone invoke(RouterRequest<RouterTestData> routerRequest) {
        Log.d("TestActivity","test---main -- id =" +
                routerRequest.getQuaryObject().getId() + " name=  " +
                routerRequest.getQuaryObject().getName());
        RouterTestData routerTestData = new RouterTestData();
        routerTestData.setId("2");
        routerTestData.setName("qq");
        RouterRespone result = new RouterRespone.Builder()
                .code(RouterRespone.CODE_SUCCESS)
                .msg("play success")
                .result(routerTestData)
                .build();
        return result;
    }

    @Override
    public String getName() {
        return null;
    }
}
