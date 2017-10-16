package com.yinhao.intelligentagriculture.activity.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.fragment.HelpFragment;
import com.yinhao.intelligentagriculture.activity.fragment.HomeFragment;
import com.yinhao.intelligentagriculture.activity.fragment.SettingFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@ContentView(R.layout.activity_home)
public class HomeActivity extends Activity implements View.OnClickListener {
    //    192.165.57.130本地IP
//    192.165.57.176
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FrameLayout fl_home;
    private RadioButton rb_setting_home;
    private RadioButton rb_help_home;
    private RadioButton rb_home_home;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        x.Ext.init(getApplication());
//        x.view().inject(this);
        initUI();
        initData();
    }


    private void initData() {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_home, new HomeFragment());
        ft.commit();
    }

    private void initUI() {
        rb_home_home = (RadioButton) findViewById(R.id.rb_home_home);
        rb_help_home = (RadioButton) findViewById(R.id.rb_help_home);
        rb_setting_home = (RadioButton) findViewById(R.id.rb_setting_home);
        fl_home = (FrameLayout) findViewById(R.id.fl_home);

        rb_home_home.setOnClickListener(this);
        rb_help_home.setOnClickListener(this);
        rb_setting_home.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ft = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.rb_home_home:// 首页
                ft.replace(R.id.fl_home, new HomeFragment());
                break;
            case R.id.rb_setting_home:// 设置
                ft.replace(R.id.fl_home, new SettingFragment());

                break;
            case R.id.rb_help_home:// 帮助
                ft.replace(R.id.fl_home, new HelpFragment());
                break;

        }
        ft.commit();
    }


}
