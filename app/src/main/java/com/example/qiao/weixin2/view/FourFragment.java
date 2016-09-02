package com.example.qiao.weixin2.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qiao.weixin2.R;

/**
 * Created by 一 on 2016/7/17.
 */
public class FourFragment extends Fragment {
    private ImageView im;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewroot=inflater.inflate(R.layout.fragment_four,container,false);

        return viewroot;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
