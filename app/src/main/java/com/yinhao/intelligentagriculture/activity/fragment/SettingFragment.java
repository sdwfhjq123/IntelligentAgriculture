package com.yinhao.intelligentagriculture.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.activity.HandControlActivity;

/**
 * Created by yinhao on 2017/3/16.
 */


public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        RelativeLayout rl__autocontrol = (RelativeLayout) view.findViewById(R.id.rl__autocontrol);
        rl__autocontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HandControlActivity.class));
            }
        });
        return view;
    }


}