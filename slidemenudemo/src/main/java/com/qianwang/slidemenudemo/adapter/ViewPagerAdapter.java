package com.qianwang.slidemenudemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.qianwang.slidemenudemo.fragment.DaliFragment;
import com.qianwang.slidemenudemo.fragment.NewFragment;

import java.util.ArrayList;

/**
 * Created by sky on 2017/3/30.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mList = new ArrayList<String>();


    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.mList = list;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case  0:

               return new NewFragment();

            case 1:

                return new DaliFragment();

            case 2:
                return new DaliFragment();
            case 3:
                return new DaliFragment();
            case 4:
                return new DaliFragment();
            case 5:
                return new DaliFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {


        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position);
    }
}
