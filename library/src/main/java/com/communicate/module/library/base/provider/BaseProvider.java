package com.communicate.module.library.base.provider;

import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;


/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */

public interface BaseProvider<T> {

    boolean isAsync( RouterRequest<T> routerRequest);

    RouterRespone invoke(RouterRequest<T> routerRequest);

    String getName();
}
