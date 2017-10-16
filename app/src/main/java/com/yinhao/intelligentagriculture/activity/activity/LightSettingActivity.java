package com.yinhao.intelligentagriculture.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.VO.HttpValues;
import com.yinhao.intelligentagriculture.activity.utils.GetIpUtil;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LightSettingActivity extends Activity {

    private static final String TAG = "LightSettingActivity";

    private GetIpUtil getIpUtil;

    private TextView tv_light_value;
    private ImageView iv_light_state;
    private EditText et_light_max;
    private EditText et_light_min;
    private Button btn_light_setting;

    private ByteArrayEntity byteArrayEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_setting);

        initUI();
        //获取光照强度值
        getValues();
        //改变报警图片的值
        setAlertImage();
    }

    /**
     * 改变报警图片的值
     */
    private void setAlertImage() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getIpUtil.getGetConfig(), new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(TAG, "onFailure: " + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(TAG, "onSuccess: " + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int minLight = jsonObject.getInt("minLight");
                    int maxLight = jsonObject.getInt("maxLight");
                    //如果light>=min并且light<=max,normal state
                    HttpValues httpValues = new HttpValues();
                    if (httpValues.getLight() > minLight && httpValues.getLight() < maxLight) {
                        iv_light_state.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getLight() == minLight || httpValues.getLight() == maxLight) {
                        iv_light_state.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getLight() > maxLight || httpValues.getLight() < minLight) {
                        iv_light_state.setImageResource(R.mipmap.p3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将EditText获取到的值传到服务器
     */
    private void putScopeValuesInServer() {
        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("maxLight", et_light_max.getText().toString());
            jsonObject.put("minLight", et_light_min.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            byteArrayEntity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        client.post(this, GetIpUtil.getSetConfig(), byteArrayEntity, "application/json", new TextHttpResponseHandler() {
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

    /**
     * 获取co2浓度值
     */
    private void getValues() {
        HttpValues httpValues = new HttpValues();
        tv_light_value.setText(httpValues.getLight() + "");
    }

    private void initUI() {
        tv_light_value = (TextView) findViewById(R.id.tv_light_value);
        iv_light_state = (ImageView) findViewById(R.id.iv_light_state);
        et_light_max = (EditText) findViewById(R.id.et_light_max);
        et_light_min = (EditText) findViewById(R.id.et_light_min);
        btn_light_setting = (Button) findViewById(R.id.btn_light_setting);
        btn_light_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定将数据发给服务器
                putScopeValuesInServer();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }

}
