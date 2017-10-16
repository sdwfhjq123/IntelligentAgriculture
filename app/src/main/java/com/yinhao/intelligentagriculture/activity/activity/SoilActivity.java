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

public class SoilActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "SoilActivity";

    private boolean changeIV = true;
    private ImageView iv_dakaishuibeng_soil;
    private ImageView iv_dakaiguangzhao_soil;
    private ImageView iv_dakaibaojing_soil;

    private boolean menu;
    private ByteArrayEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil);
        initUI();

    }

    private void initUI() {
        iv_dakaibaojing_soil = (ImageView) findViewById(R.id.iv_dakaibaojing_soil);
        iv_dakaiguangzhao_soil = (ImageView) findViewById(R.id.iv_dakaiguangzhao_soil);
        iv_dakaishuibeng_soil = (ImageView) findViewById(R.id.iv_dakaishuibeng_soil);
        iv_dakaibaojing_soil.setOnClickListener(this);
        iv_dakaiguangzhao_soil.setOnClickListener(this);
        iv_dakaishuibeng_soil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dakaibaojing_soil:
                open("Buzzer");
                if (changeIV) {
                    iv_dakaibaojing_soil.setImageResource(R.mipmap.dakaibaojing2);
                } else {
                    iv_dakaibaojing_soil.setImageResource(R.mipmap.dakaibaojing);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_dakaiguangzhao_soil:
                open("Roadlamp");
                if (changeIV) {
                    iv_dakaiguangzhao_soil.setImageResource(R.mipmap.dakaiguangzhao2);
                } else {
                    iv_dakaiguangzhao_soil.setImageResource(R.mipmap.dakaiguangzhao);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_dakaishuibeng_soil:
                open("WaterPump");
                if (changeIV) {
                    iv_dakaishuibeng_soil.setImageResource(R.mipmap.dakaishui2);
                } else {
                    iv_dakaishuibeng_soil.setImageResource(R.mipmap.dakaishui);
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
