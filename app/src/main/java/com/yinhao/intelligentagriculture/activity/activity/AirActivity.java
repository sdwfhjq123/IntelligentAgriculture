package com.yinhao.intelligentagriculture.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.utils.GetIpUtil;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class AirActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AirActivity";
    private boolean menu = false;
    private boolean changeIV = true;

    private ImageView iv_blower_air;
    private ImageView iv_buzzer_air;
    private ByteArrayEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air);
        initUI();

    }


    private void initUI() {
        iv_blower_air = (ImageView) findViewById(R.id.iv_blower_air);
        iv_buzzer_air = (ImageView) findViewById(R.id.iv_buzzer_air);
        iv_blower_air.setOnClickListener(this);
        iv_buzzer_air.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_blower_air:
                open("Blower");
                if (changeIV) {
                    iv_blower_air.setImageResource(R.mipmap.dakaifengshan2);
                } else {
                    iv_blower_air.setImageResource(R.mipmap.dakaifengshan);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_buzzer_air:
                open("Buzzer");
                if (changeIV) {
                    iv_buzzer_air.setImageResource(R.mipmap.dakaibaojing2);
                } else {
                    iv_buzzer_air.setImageResource(R.mipmap.dakaibaojing);
                }
                changeIV = !changeIV;
                break;
        }

    }

    private void open(String value) {
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            if (!menu) {
                jsonObject.put(value, 1);
            } else {
                jsonObject.put(value, 0);
            }
            menu = !menu;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(this, GetIpUtil.getControl(), entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(TAG, "onFailure: " + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(TAG, "onSuccess: " + s);
            }
        });
    }
}
