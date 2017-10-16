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

public class LightActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LightActivity";
    private ImageView iv_dakaibaojing_light;
    private ImageView iv_dakaiguangzhao_light;
    private boolean menu = false;
    private boolean changeIV = true;
    private ByteArrayEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        initUI();
    }

    private void initUI() {
        iv_dakaiguangzhao_light = (ImageView) findViewById(R.id.iv_dakaiguangzhao_light);
        iv_dakaibaojing_light = (ImageView) findViewById(R.id.iv_dakaibaojing_light);

        iv_dakaibaojing_light.setOnClickListener(this);
        iv_dakaiguangzhao_light.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dakaibaojing_light:
                open("Buzzer");
                if (changeIV) {
                    iv_dakaibaojing_light.setImageResource(R.mipmap.dakaibaojing2);
                } else {
                    iv_dakaibaojing_light.setImageResource(R.mipmap.dakaibaojing);
                }
                break;
            case R.id.iv_dakaiguangzhao_light:
                open("Roadlamp");
                if (changeIV) {
                    iv_dakaiguangzhao_light.setImageResource(R.mipmap.dakaiguangzhao2);
                } else {
                    iv_dakaiguangzhao_light.setImageResource(R.mipmap.dakaiguangzhao);
                }
                break;
        }
    }

    private void open(final String value) {

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
