package com.qianwang.inputpasswordview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private InputPasswordView mInputPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputPasswordView = (InputPasswordView) this.findViewById(R.id.pwd_view);
        mInputPasswordView.setCallBack(new InputPasswordView.InputCallback() {
            @Override
            public void onInputFinish(String str) {
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
