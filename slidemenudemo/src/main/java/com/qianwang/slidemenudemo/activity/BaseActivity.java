package com.qianwang.slidemenudemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.qianwang.slidemenudemo.R;

public abstract class BaseActivity extends AppCompatActivity {

    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        View view = getLayoutInflater().inflate(getLayoutId(),ll_content,false);
        ll_content.addView(view);
    }

    public abstract int getLayoutId();


}
