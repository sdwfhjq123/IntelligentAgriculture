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

public class CO2SettingActivity extends Activity {
    private static final String TAG = "CO2SettingActivity";

    private TextView tv_co2_value;
    private ImageView iv_co2_state;
    private EditText et_co2_max;
    private EditText et_co2_min;
    private Button btn_co2_setting;

    private ByteArrayEntity byteArrayEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co2_setting);
        initUI();

        //获取co2浓度值
        getValues();
        //改变报警图片的值
        setAlertImage();

    }

    /**
     * 将EditText获取到的值传到服务器
     */
    private void putScopeValuesInServer() {

        AsyncHttpClient client = new AsyncHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("maxCo2", et_co2_max.getText().toString());
            jsonObject.put("minCo2", et_co2_min.getText().toString());
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
     * 改变报警图片的值
     */
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
                    int minCo2 = jsonObject.getInt("minCo2");
                    int maxCo2 = jsonObject.getInt("maxCo2");
                    //如果co2>=min并且co2<=max,normal state
                    HttpValues httpValues = new HttpValues();
                    if (httpValues.getCo2() > minCo2 && httpValues.getCo2() < maxCo2) {
                        iv_co2_state.setImageResource(R.mipmap.p1);
                    } else if (httpValues.getCo2() == minCo2 || httpValues.getCo2() == maxCo2) {
                        iv_co2_state.setImageResource(R.mipmap.p2);
                    } else if (httpValues.getCo2() > maxCo2 || httpValues.getCo2() < minCo2) {
                        iv_co2_state.setImageResource(R.mipmap.p3);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取co2浓度值
     */
    private void getValues() {
        HttpValues httpValues = new HttpValues();
        tv_co2_value.setText(httpValues.getCo2() + "");
    }

    private void initUI() {
        tv_co2_value = (TextView) findViewById(R.id.tv_co2_value);
        iv_co2_state = (ImageView) findViewById(R.id.iv_co2_state);
        et_co2_max = (EditText) findViewById(R.id.et_co2_max);
        et_co2_min = (EditText) findViewById(R.id.et_co2_min);
        btn_co2_setting = (Button) findViewById(R.id.btn_co2_setting);
        btn_co2_setting.setOnClickListener(new View.OnClickListener() {
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
