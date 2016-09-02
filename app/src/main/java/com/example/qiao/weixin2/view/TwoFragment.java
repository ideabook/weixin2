package com.example.qiao.weixin2.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.qiao.weixin2.R;
import com.example.qiao.weixin2.wave.WaveHelper;
import com.example.qiao.weixin2.wave.WaveView;

/**
 * Created by 一 on 2016/7/17.
 */
public class TwoFragment extends Fragment {



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View viewroot = inflater.inflate(R.layout.fragment_two, container, false);
//        waveView = (WaveView) viewroot.findViewById(R.id.wave);
//        waveView.setBorder(mBorderWidth, mBorderColor);

        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

}
