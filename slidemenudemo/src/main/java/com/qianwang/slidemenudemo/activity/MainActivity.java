package com.qianwang.slidemenudemo.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.qianwang.slidemenudemo.R;
import com.qianwang.slidemenudemo.fragment.LiveMainFragment;
import com.qianwang.slidemenudemo.fragment.LivePlayerFragment;
import com.qianwang.slidemenudemo.fragment.MineFragment;


public class MainActivity extends BaseActivity {


    private Class mFragment[] = {LiveMainFragment.class, LivePlayerFragment.class, MineFragment.class};
    private String mTextViewArray[] = {"live", "publish", "my"};
    private int mImageViewArray[] = {R.drawable.tab_live_selector, R.drawable.tab_publish_selector, R.drawable.tab_my_selector};
    private FragmentTabHost mTabHost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();

    }

    protected void initView() {
        //查找 FragmentTabHost 控件，并为其添加 FragmentManager 管理器
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.contentPanel);
    }

    protected void initData() {
        //初始化 TabSpec，添加其相应数据
        int fragmentCount = mFragment.length;

        for (int i=0;i<fragmentCount;i++){
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextViewArray[i]).setIndicator(getTabItemView(i));
            Log.i("520it", "" + "**************************"+mTextViewArray[i]);
            mTabHost.addTab(tabSpec,mFragment[i],null);
            mTabHost.getTabWidget().setDividerDrawable(null);
        }
    }

    private View getTabItemView(int index) {
        //该布局只有一张图片，很简单，不贴代码
        View v = LayoutInflater.from(this).inflate(R.layout.tab_image,null);
        ImageView icon = (ImageView) v.findViewById(R.id.tab_img);
        icon.setImageResource(mImageViewArray[index]);
        return v;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


}
