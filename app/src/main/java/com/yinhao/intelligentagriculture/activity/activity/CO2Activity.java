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

public class CO2Activity extends Activity implements View.OnClickListener {

    private static final String TAG = "CO2Activity";

    private ImageView iv_dakaifengshan_co2;
    private ImageView iv_dakaibaojing_co2;
    private ImageView iv_dakaishuibeng_co2;
    private ImageView iv_dakaiguangzhao_co2;
    private ByteArrayEntity entity;
    private boolean menu = false;
    private boolean changeIV = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取字符串
        setContentView(R.layout.activity_co2);

        initUI();
    }

    private void initUI() {
        iv_dakaifengshan_co2 = (ImageView) findViewById(R.id.iv_dakaifengshan_co2);
        iv_dakaibaojing_co2 = (ImageView) findViewById(R.id.iv_dakaibaojing_co2);
        iv_dakaiguangzhao_co2 = (ImageView) findViewById(R.id.iv_dakaiguangzhao_co2);
        iv_dakaishuibeng_co2 = (ImageView) findViewById(R.id.iv_dakaishuibeng_co2);
        iv_dakaibaojing_co2.setOnClickListener(this);
        iv_dakaifengshan_co2.setOnClickListener(this);
        iv_dakaiguangzhao_co2.setOnClickListener(this);
        iv_dakaishuibeng_co2.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dakaifengshan_co2:
                open("Blower");
                if (changeIV) {
                    iv_dakaifengshan_co2.setImageResource(R.mipmap.dakaifengshan2);
                } else {
                    iv_dakaifengshan_co2.setImageResource(R.mipmap.dakaifengshan);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_dakaibaojing_co2:
                open("Buzzer");
                if (changeIV) {
                    iv_dakaibaojing_co2.setImageResource(R.mipmap.dakaibaojing2);
                } else {
                    iv_dakaibaojing_co2.setImageResource(R.mipmap.dakaibaojing);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_dakaiguangzhao_co2:
                open("Roadlamp");
                if (changeIV) {
                    iv_dakaiguangzhao_co2.setImageResource(R.mipmap.dakaiguangzhao2);
                } else {
                    iv_dakaiguangzhao_co2.setImageResource(R.mipmap.dakaiguangzhao);
                }
                changeIV = !changeIV;
                break;
            case R.id.iv_dakaishuibeng_co2:
                open("WaterPump");
                if (changeIV) {
                    iv_dakaishuibeng_co2.setImageResource(R.mipmap.dakaishui2);
                } else {
                    iv_dakaishuibeng_co2.setImageResource(R.mipmap.dakaishui);
                }
                changeIV = !changeIV;
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
