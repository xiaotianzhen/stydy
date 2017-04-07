package com.qianwang.slidemenudemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qianwang.slidemenudemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveListFragment extends Fragment {

    private TextView tv_title;

    public LiveListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_list, container, false);
    }

    public static Fragment getInstance(Bundle bundle) {

        LiveListFragment fragment = new LiveListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_titile);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }
}
