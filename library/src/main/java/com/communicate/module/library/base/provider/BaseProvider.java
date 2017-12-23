package com.communicate.module.library.base.provider;

import com.communicate.module.library.base.action.BaseAction;

import java.util.HashMap;

/**
 * user: zhangjianfeng
 * date: 25/08/2017
 * version: 7.3
 */

public abstract class BaseProvider {

    private HashMap<String,BaseAction> mActions;
    public BaseProvider(){
        mActions = new HashMap<>();
    }
    public void registerAction(String actionName,BaseAction action){
        mActions.put(actionName,action);
    }

    public BaseAction findAction(String actionName){
        return mActions.get(actionName);
    }


    protected abstract String getName();
}
