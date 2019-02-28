package com.communicate.module.library.router;


import android.os.Parcel;

public class RouterRespone<T> {
    public static final int CODE_SUCCESS = 0x0000;
    public static final int CODE_ERROR = 0x0001;
    public static final int CODE_NOT_FOUND = 0X0002;

    private int code;
    private String msg;
    private T result;

    RouterRespone(){

    }

    private RouterRespone(Builder builder) {
        code = builder.code;
        msg = builder.msg;
        result = (T) builder.result;
    }

    protected RouterRespone(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        result = (T) in.readParcelable(this.getClass().getClassLoader());
    }


    public T getResult() {
        return result;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "MaActionResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", object=" + result +
                '}';
    }



    public static final class Builder<T> {
        private int code;
        private String msg;
        private T result;

        public Builder() {
        }

        public Builder code(int val) {
            code = val;
            return this;
        }

        public Builder msg(String val) {
            msg = val;
            return this;
        }


        public Builder result(T val) {
            result = val;
            return this;
        }

        public RouterRespone build() {
            return new RouterRespone(this);
        }

    }
}
