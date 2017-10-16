package com.yinhao.intelligentagriculture.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yinhao.intelligentagriculture.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandControlActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_air_control;
    private RelativeLayout rl_co2_control;
    private RelativeLayout rl_light_control;
    private RelativeLayout rl_soil_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_control);
        initUI();

    }

    private void initUI() {
        rl_air_control = (RelativeLayout) findViewById(R.id.rl_air_control);
        rl_co2_control = (RelativeLayout) findViewById(R.id.rl_co2_control);
        rl_light_control = (RelativeLayout) findViewById(R.id.rl_light_control);
        rl_soil_control = (RelativeLayout) findViewById(R.id.rl_soil_control);

        rl_air_control.setOnClickListener(this);
        rl_co2_control.setOnClickListener(this);
        rl_light_control.setOnClickListener(this);
        rl_soil_control.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_air_control:
                startActivity(new Intent(this, AirSettingActivity.class));
                break;
            case R.id.rl_co2_control:
                startActivity(new Intent(this, CO2SettingActivity.class));
                break;
            case R.id.rl_light_control:
                startActivity(new Intent(this, AirSettingActivity.class));
                break;
            case R.id.rl_soil_control:
                startActivity(new Intent(this, SoilSettingActivity.class));
                break;
        }
    }
}
