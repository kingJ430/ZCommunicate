package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.communicate.module.library.service.Communicate;

import org.reactivestreams.Subscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        try {
            RouterTestData routerTestData = new RouterTestData();
            routerTestData.setId("1");
            routerTestData.setName("ddddasda");
//            new Communicate().create(TestService.class)
//                    .getData(this,routerTestData)
//                    .subscribe(new Observer<RouterTestData>() {
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.d(TAG, "error -----" + e.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(RouterTestData s) {
//                            Log.d(TAG, " -----" + s.getId() + " -- " + s.getName());
//                            Toast.makeText(TestActivity.this," -----" + s.getId() + " -- " + s.getName(),Toast.LENGTH_SHORT).show();
//                        }
//                    });

        new Communicate().create(TestService.class)
                .getData(this,routerTestData)
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error -----" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object s) {
                        Log.d(TAG, " -----" );
                        Toast.makeText(TestActivity.this," -----" ,Toast.LENGTH_SHORT).show();
                    }
                });

            Fragment fragment = new Communicate().create(TestService.class).testData();
            Log.d(TAG, fragment.toString());

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
