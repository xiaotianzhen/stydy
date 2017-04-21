package com.qianwang.slidemenudemo.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.qianwang.slidemenudemo.R;
import com.qianwang.slidemenudemo.adapter.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveMainFragment extends Fragment {

    private ImageView im_search;
    private ImageView im_message;
    private PagerSlidingTabStrip mTabStrip;
    private ViewPager viewpager;
    private ArrayList<String> mTitle;
    private ArrayList<Fragment> mFragments;
    private ViewPagerAdapter mViewPagerAdapter;

    public LiveMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        im_search = (ImageView) view.findViewById(R.id.im_search);
        im_message = (ImageView) view.findViewById(R.id.im_message);
        mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.circle_index_indicator);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);

        initViewpage();
        mTabStrip.setViewPager(viewpager);
        mTabStrip.setTextColor(Color.WHITE);
        mTabStrip.setIndicatorColor(Color.WHITE);
        mTabStrip.setDividerColor(Color.GRAY);
        mTabStrip.setTextSize(getResources().getDimensionPixelSize(R.dimen.abc_text_size_menu_material));
        mTabStrip.setUnderlineHeight(1);
    }



    private void initViewpage() {
        mFragments = new ArrayList<Fragment>();
        mTitle = new ArrayList<String>();

        mTitle.add("最新");
        mTitle.add("最热");
        mTitle.add("达力");
        mTitle.add("活力");
        mTitle.add("英雄联盟");
        mTitle.add("王者荣耀");

        //fragment 常用的两种适配器，pagerAdapter,fragmentAdapter
        mViewPagerAdapter=new ViewPagerAdapter(getFragmentManager(),mTitle);
        viewpager.setAdapter(mViewPagerAdapter);
        viewpager.setCurrentItem(0);

    }
}
