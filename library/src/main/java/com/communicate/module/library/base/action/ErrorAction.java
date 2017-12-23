package com.communicate.module.library.base.action;

import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

public class ErrorAction implements BaseAction {

    private static final String DEFAULT_MESSAGE = "Something was really wrong. Ha ha!";
    private int mCode;
    private String mMessage;
    private boolean mAsync;
    public ErrorAction() {
        mCode = RouterRespone.CODE_ERROR;
        mMessage = DEFAULT_MESSAGE;
        mAsync = false;
    }

    public ErrorAction(boolean isAsync, int code, String message) {
        this.mCode = code;
        this.mMessage = message;
        this.mAsync = isAsync;
    }

    @Override
    public boolean isAsync( RouterRequest requestData) {
        return mAsync;
    }

    @Override
    public RouterRespone invoke(RouterRequest requestData) {
        RouterRespone result = new RouterRespone.Builder()
                .code(mCode)
                .msg(mMessage)
                .data(null)
                .result(null)
                .build();
        return result;
    }

    @Override
    public String getName() {
        return "error";
    }

}
