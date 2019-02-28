package com.communicate.module.library.service;


import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRequestUtil;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public class CommunicateCall<T> {

    private final ServiceMethod<T, ?> serviceMethod;
    private final
    Object[] args;

    CommunicateCall(ServiceMethod<T, ?> serviceMethod,  Object[] args) {
        this.serviceMethod = serviceMethod;
        this.args = args;
    }

    RouterRequest request() throws Exception {
        RouterRequest routerRequest = RouterRequestUtil.obtain()
                .provider(serviceMethod.provider);
        return serviceMethod.toRequest(routerRequest,args);
    }
}
