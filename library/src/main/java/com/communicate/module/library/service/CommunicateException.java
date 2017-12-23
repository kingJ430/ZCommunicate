package com.communicate.module.library.service;

/**
 * user: zhangjianfeng
 * date: 07/09/2017
 * version: 7.3
 */

public class CommunicateException extends Exception {

    int code;
    String message;

    public CommunicateException(int code,String message) {
        super(message);
        this.code = code;
    }
}
