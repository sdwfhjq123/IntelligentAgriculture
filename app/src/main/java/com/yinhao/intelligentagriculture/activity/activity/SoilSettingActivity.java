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

public class SoilSettingActivity extends Activity {
    private static final String TAG = "SoilSettingActivity";

    private TextView tv_soil_temp;
    private TextView tv_soil_humi;
    private ImageView iv_soil_humi;
    private ImageView iv_soil_temp;
    private EditText et_soil_humi_max;
    private EditText et_soil_humi_min;
    private EditText et_soil_temp_max;
    private EditText et_soil_temp_min;
    private Button btn_soil_setting;
    private ByteArrayEntity byteArrayEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_setting);

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
                    int maxSoilHumidity = jsonObject.getInt("maxSoilHumidity");
                    int minSoilHumidity = jsonObject.getInt("minSoilHumidity");
                    int maxSoilTemperature = jsonObject.getInt("maxSoilTemperature");
                    int minSoilTemperature = jsonObject.getInt("minSoilTemperature");
                    //如果air>=min并且air<=max,normal state
                    HttpValues httpValues = new HttpValues();

                    if (httpValues.getAirTemperature() >= minSoilHumidity && httpValues.getAirTemperature() <= maxSoilHumidity) {
                        iv_soil_humi.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getAirTemperature() == minSoilHumidity || httpValues.getAirTemperature() == maxSoilHumidity) {
                        iv_soil_humi.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getAirTemperature() < minSoilHumidity || httpValues.getAirTemperature() > maxSoilHumidity) {
                        iv_soil_humi.setImageResource(R.mipmap.p3);
                    }

                    if (httpValues.getAirHumidity() >= minSoilTemperature && httpValues.getAirHumidity() <= maxSoilTemperature) {
                        iv_soil_temp.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getAirHumidity() == minSoilTemperature || httpValues.getAirHumidity() == maxSoilTemperature) {
                        iv_soil_temp.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getAirHumidity() < minSoilTemperature || httpValues.getAirHumidity() > maxSoilTemperature) {
                        iv_soil_temp.setImageResource(R.mipmap.p3);
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
            jsonObject.put("maxSoilHumidity", et_soil_humi_max.getText().toString());
            jsonObject.put("minSoilHumidity", et_soil_humi_min.getText().toString());
            jsonObject.put("maxSoilTemperature", et_soil_temp_max.getText().toString());
            jsonObject.put("minSoilTemperature", et_soil_temp_min.getText().toString());
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
        tv_soil_temp.setText(httpValues.getSoilTemperature() + "");
        tv_soil_humi.setText(httpValues.getSoilHumidity() + "");
    }

    private void initUI() {
        tv_soil_temp = (TextView) findViewById(R.id.tv_soil_temp);
        tv_soil_humi = (TextView) findViewById(R.id.tv_soil_humi);
        iv_soil_humi = (ImageView) findViewById(R.id.iv_soil_humi);
        iv_soil_temp = (ImageView) findViewById(R.id.iv_soil_temp);
        et_soil_humi_max = (EditText) findViewById(R.id.et_soil_humi_max);
        et_soil_humi_min = (EditText) findViewById(R.id.et_soil_humi_min);
        et_soil_temp_max = (EditText) findViewById(R.id.et_soil_temp_max);
        et_soil_temp_min = (EditText) findViewById(R.id.et_soil_temp_min);
        btn_soil_setting = (Button) findViewById(R.id.btn_soil_setting);
        btn_soil_setting.setOnClickListener(new View.OnClickListener() {
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
