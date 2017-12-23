package com.example.test;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.communicate.module.library.annotation.*;

import rx.Observable;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public interface TestService {

    @RouterPath(provider = "main/Test")
    Observable<RouterTestData> getData(@RouterContext("context") Context context, @RouterData("data") RouterTestData routerData);

    @RouterPath(provider = "test/test2")
    Fragment testData();
}
