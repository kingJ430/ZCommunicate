package com.communicate.module.library.router;


import android.util.Log;

import com.communicate.module.library.annotation.RouterContext;
import com.communicate.module.library.base.provider.ErrorProvider;
import com.communicate.module.library.base.provider.BaseProvider;
import com.communicate.module.library.service.CommunicateException;
import com.communicate.module.library.utils.CommunicateUtil;

import org.reactivestreams.Subscriber;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class LocalRouter {
    private static final String TAG = "LocalRouter";
    private static LocalRouter sInstance = null;
    private static ExecutorService threadPool = null;

    private LocalRouter() {
    }

    public static synchronized LocalRouter getInstance() {
        if (sInstance == null) {
            sInstance = new LocalRouter();
        }
        return sInstance;
    }

    private static ExecutorService getThreadPool() {
        if (null == threadPool) {
            synchronized (LocalRouter.class) {
                threadPool = Executors.newCachedThreadPool();
            }
        }
        return threadPool;
    }


    private class RouteResultWrap {

        public RouterRespone route(RouterRequest routerRequest) throws Exception {
            return route(routerRequest, RouteResultType.MA_ACTION_RESULT);
        }

        public Observable<RouterRespone> rxRoute(RouterRequest routerRequest) throws Exception {
            return rxRoute(routerRequest, RouteResultType.OBSERVABLE);
        }


        private Observable<RouterRespone> rxRoute(final RouterRequest routerRequest, RouteResultType type) throws Exception {
            final BaseProvider targetProvider = findRequestProvider(routerRequest);
            // Sync result, return the result immediately.
            Observable<RouterRespone> mResultObservable = Observable.create(new ObservableOnSubscribe<RouterRespone>() {
                @Override
                public void subscribe(ObservableEmitter<RouterRespone> e) throws Exception {
                    RouterRespone respone = targetProvider.invoke(routerRequest);
                    if (respone.getCode() == RouterRespone.CODE_SUCCESS) {
                        e.onNext(respone);
                    } else {
                        e.onError(new CommunicateException(respone.getCode(), respone.getMsg()));
                    }
                }
            });
            if (!targetProvider.isAsync(routerRequest)) {
                return mResultObservable;
            }
            // Async result, use the thread pool to execute the task.
            else {
                return mResultObservable.subscribeOn(Schedulers.io());


            }
        }

        private RouterRespone route(final RouterRequest routerRequest, RouteResultType type) throws Exception {
            final BaseProvider targetProvider = findRequestProvider(routerRequest);
            // Sync result, return the result immediately.

            if (!targetProvider.isAsync(routerRequest)) {
                RouterRespone respone = targetProvider.invoke(routerRequest);
                Log.e("RouterRespone",targetProvider + " " + respone);
                return respone;
            }
            // Async result, use the thread pool to execute the task.
            else {
                LocalTask task = new LocalTask(routerRequest, targetProvider);
                return getThreadPool().submit(task).get();

            }
        }

        private BaseProvider findRequestProvider(RouterRequest routerRequest) {
            Log.e("xxx", routerRequest.getProvider());
            BaseProvider targetProvider = CommunicateUtil.getProvider(routerRequest.getProvider());
            ErrorProvider defaultNotFoundAction = new ErrorProvider(false, RouterRespone.CODE_NOT_FOUND, "Not found the action.");
            if (null == targetProvider) {
                return defaultNotFoundAction;
            } else {
                return targetProvider;
            }
        }

        private class LocalTask implements Callable<RouterRespone> {
            private RouterRequest mRequestData;
            private RouterContext mContext;
            private BaseProvider mProvider;

            public LocalTask(RouterRequest requestData, BaseProvider provider) {
                this.mRequestData = requestData;
                this.mProvider = provider;
            }

            @Override
            public RouterRespone call() throws Exception {
                RouterRespone result = mProvider.invoke(mRequestData);
                return result;
            }
        }


    }

    public RouterRespone route(RouterRequest routerRequest) throws Exception {
        RouteResultWrap routeResultWrap = new RouteResultWrap();
        return routeResultWrap.route(routerRequest);
    }

    public Observable<RouterRespone> rxRoute(RouterRequest routerRequest) throws Exception {
        RouteResultWrap routeResultWrap = new RouteResultWrap();
        return routeResultWrap.rxRoute(routerRequest);
    }

    private enum RouteResultType {
        OBSERVABLE,
        MA_ACTION_RESULT
    }
}
