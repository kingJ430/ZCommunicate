package com.communicate.module.library.router;


import com.communicate.module.library.annotation.RouterContext;
import com.communicate.module.library.base.action.BaseAction;
import com.communicate.module.library.base.action.ErrorAction;
import com.communicate.module.library.base.provider.BaseProvider;
import com.communicate.module.library.service.CommunicateException;
import com.communicate.module.library.utils.CommunicateUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.Observable;

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
        RouterRespone maActionResult;
        Observable<RouterRespone> maActionResultObservable;

        public RouterRespone route( RouterRequest routerRequest) throws Exception {
            rxRoute(routerRequest, RouteResultType.MA_ACTION_RESULT);
            return maActionResult;
        }

        public Observable<RouterRespone> rxRoute( RouterRequest routerRequest) throws Exception {
            RouteResultWrap routeResultWrap = new RouteResultWrap();
            rxRoute(routerRequest,  RouteResultType.OBSERVABLE);
            return routeResultWrap.maActionResultObservable;
        }


        private void rxRoute( RouterRequest routerRequest, RouteResultType type) throws Exception {
            BaseAction targetAction = findRequestAction(routerRequest);
            // Sync result, return the result immediately.
            if (!targetAction.isAsync(routerRequest)) {
                maActionResult = targetAction.invoke(routerRequest);
                if (type == RouteResultType.OBSERVABLE) {
                    if(maActionResult.getCode() == RouterRespone.CODE_SUCCESS) {
                        maActionResultObservable = Observable.just(maActionResult);
                    } else {
                        Observable.error(new CommunicateException(maActionResult.getCode(),maActionResult.getMsg()));
                    }
                    return;
                } else {
                    return;
                }
            }
            // Async result, use the thread pool to execute the task.
            else {
                LocalTask task = new LocalTask(routerRequest, targetAction);
                if (type == RouteResultType.OBSERVABLE) {
                    if(maActionResult.getCode() == RouterRespone.CODE_SUCCESS) {
                        maActionResultObservable = Observable.from(getThreadPool().submit(task));
                    } else {
                        Observable.error(new CommunicateException(maActionResult.getCode(),maActionResult.getMsg()));
                    }
                } else {
                    maActionResult = getThreadPool().submit(task).get();
                }
                return;
            }
        }

        private BaseAction findRequestAction(RouterRequest routerRequest) {
            BaseProvider targetProvider = CommunicateUtil.getProvider(routerRequest.getProvider());
            ErrorAction defaultNotFoundAction = new ErrorAction(false, RouterRespone.CODE_NOT_FOUND, "Not found the action.");
            if (null == targetProvider) {
                return defaultNotFoundAction;
            } else {
                BaseAction targetAction = targetProvider.findAction(routerRequest.getAction());
                if (null == targetAction) {
                    return defaultNotFoundAction;
                } else {
                    return targetAction;
                }
            }
        }

        private class LocalTask implements Callable<RouterRespone> {
            private RouterRequest mRequestData;
            private RouterContext mContext;
            private BaseAction mAction;

            public LocalTask(RouterRequest requestData, BaseAction maAction) {
                this.mRequestData = requestData;
                this.mAction = maAction;
            }

            @Override
            public RouterRespone call() throws Exception {
                RouterRespone result = mAction.invoke(mRequestData);
                return result;
            }
        }


    }

    public RouterRespone route( RouterRequest routerRequest) throws Exception {
        RouteResultWrap routeResultWrap = new RouteResultWrap();
        return routeResultWrap.route(routerRequest);
    }

    public Observable<RouterRespone> rxRoute( RouterRequest routerRequest) throws Exception {
        RouteResultWrap routeResultWrap = new RouteResultWrap();
        return routeResultWrap.rxRoute(routerRequest);
    }

    private enum RouteResultType {
        OBSERVABLE,
        MA_ACTION_RESULT
    }
}
