package com.common.library.communicate;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.communicate.module.library.router.LocalRouter;
import com.communicate.module.library.router.RouterRequestUtil;
import com.communicate.module.library.router.RouterRespone;
import com.communicate.module.library.utils.CommunicateUtil;
import com.example.test.TestActivity;

import rx.Subscriber;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });

//        try {
//            LocalRouter.getInstance()
//                        .rxRouter(this,
//                                RouterRequestUtil.obtain()
//                                        .provider("main")
//                                        .action("Test"));
////                        .subscribe(new Subscriber<Object>() {
////                        });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
