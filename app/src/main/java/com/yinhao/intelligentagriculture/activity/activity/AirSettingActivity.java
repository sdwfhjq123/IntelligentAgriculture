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

public class AirSettingActivity extends Activity {
    private static final String TAG = "AirSettingActivity";

    private GetIpUtil getIpUtil;

    private ImageView iv_air_humi_state;
    private ImageView iv_air_temp_state;
    private TextView tv_air_humi_value;
    private TextView tv_air_temp_value;
    private EditText et_air_humi_max;
    private EditText et_air_humi_min;
    private EditText et_air_temp_max;
    private EditText et_air_temp_min;
    private ByteArrayEntity byteArrayEntity;
    private Button btn_air_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_setting);

        initUI();
        //获取空气温度湿度的值
        getValues();
        //获取空气预警图片的值
        setAlertImage();
    }

    private void setAlertImage() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GetIpUtil.getGetConfig(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.i(TAG, "onFailure: " + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.i(TAG, "onSuccess: " + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int minAirHumidity = jsonObject.getInt("minAirHumidity");
                    int minAirTemperature = jsonObject.getInt("minAirTemperature");
                    int maxAirHumidity = jsonObject.getInt("maxAirHumidity");
                    int maxAirTemperature = jsonObject.getInt("maxAirTemperature");
                    //如果air>=min并且air<=max,normal state
                    HttpValues httpValues = new HttpValues();
                    if (httpValues.getAirTemperature() >= minAirTemperature && httpValues.getAirTemperature() <= maxAirTemperature) {
                        iv_air_temp_state.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getAirTemperature() == minAirTemperature || httpValues.getAirTemperature() == maxAirTemperature) {
                        iv_air_temp_state.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getAirTemperature() < minAirTemperature || httpValues.getAirTemperature() > maxAirTemperature) {
                        iv_air_temp_state.setImageResource(R.mipmap.p3);
                    }
                    if (httpValues.getAirHumidity() >= minAirHumidity && httpValues.getAirHumidity() <= maxAirHumidity) {
                        iv_air_humi_state.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getAirHumidity() == minAirHumidity || httpValues.getAirHumidity() == maxAirHumidity) {
                        iv_air_humi_state.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getAirHumidity() < minAirHumidity || httpValues.getAirHumidity() > maxAirHumidity) {
                        iv_air_humi_state.setImageResource(R.mipmap.p3);
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
            jsonObject.put("minAirHumidity", et_air_humi_min.getText().toString());
            jsonObject.put("minAirTemperature", et_air_temp_min.getText().toString());
            jsonObject.put("maxAirHumidity", et_air_humi_max.getText().toString());
            jsonObject.put("maxAirTemperature", et_air_temp_max.getText().toString());
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
     * 获取空气温度湿度的值
     */
    private void getValues() {
        HttpValues httpValues = new HttpValues();
        tv_air_humi_value.setText(httpValues.getAirHumidity() + "");
        tv_air_temp_value.setText(httpValues.getAirTemperature() + "");
    }

    private void initUI() {
        iv_air_humi_state = (ImageView) findViewById(R.id.iv_air_humi_state);
        iv_air_temp_state = (ImageView) findViewById(R.id.iv_air_temp_state);
        tv_air_humi_value = (TextView) findViewById(R.id.tv_air_humi_value);
        tv_air_temp_value = (TextView) findViewById(R.id.tv_air_temp_value);
        et_air_humi_max = (EditText) findViewById(R.id.et_air_humi_max);
        et_air_humi_min = (EditText) findViewById(R.id.et_air_humi_min);
        et_air_temp_max = (EditText) findViewById(R.id.et_air_temp_max);
        et_air_temp_min = (EditText) findViewById(R.id.et_air_temp_min);
        btn_air_setting = (Button) findViewById(R.id.btn_air_setting);
        //点击button开启传输
        btn_air_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将EditText获取到的值传到服务器
                putScopeValuesInServer();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });
    }
}
