package com.communicate.module.library.service;

import com.communicate.module.library.annotation.RouterContext;
import com.communicate.module.library.annotation.RouterData;
import com.communicate.module.library.annotation.RouterPath;
import com.communicate.module.library.annotation.RouterQuary;
import com.communicate.module.library.router.LocalRouter;
import com.communicate.module.library.router.RouterRequest;
import com.communicate.module.library.router.RouterRespone;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import rx.Observable;
import rx.functions.Func1;

/**
 * user: zhangjianfeng
 * date: 06/09/2017
 * version: 7.3
 */

public class ServiceMethod<R, T> {

    final String provider;
    final String action;
    private Method mMethod;
    ParameterHandler[] mParameterHandlers;

    public ServiceMethod(Builder builder) {
        provider = builder.provider;
        action = builder.action;
        mMethod = builder.method;
        mParameterHandlers = builder.mParameterHandlers;
    }

    public boolean isObservable() {
        Type returnType = mMethod.getGenericReturnType();
        if (returnType == Observable.class) {
            return true;
        }
        return false;
    }

    public Observable<T> rxAdapt(CommunicateCall communicateCall) throws Exception {
        Type returnType = mMethod.getGenericReturnType();
        if (returnType == void.class || returnType == Void.class) {
            LocalRouter.getInstance().route(communicateCall.request());
            return null;
        } else {
            return LocalRouter.getInstance().rxRoute(communicateCall.request())
                    .flatMap(new Func1<RouterRespone, Observable<T>>() {
                        @Override
                        public Observable<T> call(RouterRespone routerRespone) {
                            if (routerRespone.getResult() instanceof Observable) {
                                return (Observable<T>) routerRespone.getResult();
                            }
                            return (Observable<T>) Observable.just(routerRespone.getResult());
                        }
                    });
        }
    }

    public T adapt(CommunicateCall communicateCall) throws Exception {
        Type returnType = mMethod.getGenericReturnType();
        return (T) LocalRouter.getInstance().route(communicateCall.request()).getResult();

    }

    public RouterRequest toRequest(RouterRequest routerRequest, Object[] args) throws Exception {
        for (int i = 0; i < mParameterHandlers.length; i++) {
            if (mParameterHandlers[i] != null) {
                mParameterHandlers[i].apply(routerRequest, args[i]);
            }
        }
        return routerRequest;
    }

    static final class Builder<T, R> {

        Annotation[][] parameterAnnotationsArray;
        ParameterHandler[] mParameterHandlers;
        Type[] parameterTypes;
        String provider;
        String action;
        Method method;
        Communicate mCommunicate;
        private Annotation[] methodAnnotations;

        public Builder(Communicate communicate, Method method) {
            methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            mCommunicate = communicate;
            this.method = method;
        }


        public ServiceMethod build() {
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            int parameterCount = parameterAnnotationsArray.length;
            mParameterHandlers = new ParameterHandler[parameterCount];
            for (int p = 0; p < parameterCount; p++) {
                Type parameterType = parameterTypes[p];
                Annotation[] parameterAnnotations = parameterAnnotationsArray[p];
                ParameterHandler parameterHandler = parseParameter(parameterType, parameterAnnotations);
                if (parameterHandler != null) {
                    mParameterHandlers[p] = parameterHandler;
                }
            }

            return new ServiceMethod(this);
        }

        private ParameterHandler parseParameter(Type parameterType, Annotation[] parameterAnnotations) {
            if (parameterAnnotations != null && parameterAnnotations.length > 0) {
                if (parameterAnnotations[0] instanceof RouterContext) {
                    RouterContext context = (RouterContext) parameterAnnotations[0];
                    return new ParameterHandler.Context<>(context.value());
                } else if (parameterAnnotations[0] instanceof RouterQuary) {
                    RouterQuary context = (RouterQuary) parameterAnnotations[0];
                    return new ParameterHandler.Quary<>(context.value());
                } else if (parameterAnnotations[0] instanceof RouterData) {
                    RouterData context = (RouterData) parameterAnnotations[0];
                    return new ParameterHandler.Data<>(context.value());
                }
            }
            return null;
        }


        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof RouterPath) {
                parsePath(((RouterPath) annotation).provider());
            }
        }

        private void parsePath(String value) {
            if (value.isEmpty()) {
                return;
            }
            if (value.contains("/")) {
                String[] values = value.split("/");
                this.provider = values[0];
                this.action = values[1];
            }
        }

    }
}
