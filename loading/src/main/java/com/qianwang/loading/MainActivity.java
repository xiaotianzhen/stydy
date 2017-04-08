package com.qianwang.loading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private LoadingCustomView mLoadingCustomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingCustomView= (LoadingCustomView) findViewById(R.id.loading);
        new Thread(new Runnable() {
             @Override
             public void run() {

                 try {
                     Thread.sleep(20000);
                     mLoadingCustomView.success();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }
         }).start();


    }
}
