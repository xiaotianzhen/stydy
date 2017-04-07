package com.qianwang.stydy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;

public class MainActivity extends AppCompatActivity {

    private EditText mEtContent;
    private EmotionLayout mElEmotion;
    private Button btn_send;
    private LinearLayout llContent;
    private EmotionKeyboard mEmotionKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEmotionKeyboard();

    }


    private void initView() {

        mEtContent = (EditText) findViewById(R.id.mEtContent);
        mElEmotion = (EmotionLayout) findViewById(R.id.elEmotion);
        btn_send = (Button) findViewById(R.id.btn_send);
        llContent = (LinearLayout) findViewById(R.id.llContent);

        mElEmotion.attachEditText(mEtContent);
        mElEmotion.setEmotionAddVisiable(true);
        mElEmotion.setEmotionSettingVisiable(true);
        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEmotionSettingClick(View view) {
                Toast.makeText(getApplicationContext(), "setting", Toast.LENGTH_SHORT).show();
            }
        });

        mElEmotion.setEmotionSelectedListener(new IEmotionSelectedListener() {
            @Override
            public void onEmojiSelected(String key) {

            }

            @Override
            public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
                String stickerPath = stickerBitmapPath;
                Toast.makeText(getApplicationContext(), stickerPath, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToContent(llContent);
        mEmotionKeyboard.bindToEmotionButton(btn_send);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.setEmotionLayout(mElEmotion);
    }
}
