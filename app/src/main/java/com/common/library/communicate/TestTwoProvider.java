package com.common.library.communicate;

import com.communicate.module.annotation.Provider;
import com.communicate.module.library.base.provider.BaseProvider;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */
@Provider(name = "test")
public class TestTwoProvider extends BaseProvider {
    @Override
    protected String getName() {
        return null;
    }
}
