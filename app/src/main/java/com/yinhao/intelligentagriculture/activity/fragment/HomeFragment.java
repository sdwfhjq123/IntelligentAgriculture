package com.yinhao.intelligentagriculture.activity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.VO.HttpScopeValues;
import com.yinhao.intelligentagriculture.activity.VO.HttpValues;
import com.yinhao.intelligentagriculture.activity.activity.AirActivity;
import com.yinhao.intelligentagriculture.activity.activity.CO2Activity;
import com.yinhao.intelligentagriculture.activity.activity.LightActivity;
import com.yinhao.intelligentagriculture.activity.activity.SoilActivity;
import com.yinhao.intelligentagriculture.activity.utils.GetIpUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yinhao on 2017/3/16.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView tv_co2_value_fragment;
    private TextView tv_light_value_fragment;
    private TextView tv_soiltemperature_value_fragment;
    private TextView tv_soilhumidity_value_fragment;
    private TextView tv_airtemperature_value_fragment;
    private TextView tv_airhumidity_value_fragment;
    private ImageView iv_air_state_msg_item;
    private ImageView iv_co2_state_msg_item;
    private ImageView iv_guangzhao_state_msg_item;
    private ImageView iv_turang_state_msg_item;
    /**
     * 空气湿度
     */
    private int airHumidity;
    /**
     * 空气温度
     */
    private int airTemperature;
    /**
     * 土壤温度
     */
    private int soilTemperature;
    /**
     * 土壤湿度
     */
    private int soilHumidity;
    /**
     * co2浓度
     */
    private int co2;
    /**
     * 光照强度
     */
    private int light;
    private int maxCo2;
    private int minCo2;
    private int maxLight;
    private int minLight;
    private int maxSoilHumidity;
    private int minSoilHumidity;
    private int maxSoilTemperature;
    private int minSoilTemperature;
    private int controlAuto;
    private int maxAirTemperature;
    private int minAirTemperature;
    private int maxAirHumidity;
    private int minAirHumidity;
    private TextView tv_co2_set_min_fragment;
    private TextView tv_co2_set_max_fragment;
    private TextView tv_light_set_min_fragment;
    private TextView tv_light_set_max_fragment;
    private TextView tv_soil_temp_min_fragment;
    private TextView tv_soil_humi_min_fragment;
    private TextView tv_soil_temp_max_fragment;
    private TextView tv_soil_humi_max_fragment;
    private TextView tv_air_temp_min_fragment;
    private TextView tv_air_temp_max_fragment;
    private TextView tv_air_humi_min_frgment;
    private TextView tv_air_humi_max_fragment;

    //
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, null);

        tv_co2_value_fragment = (TextView) v.findViewById(R.id.tv_co2_value_fragment);
        tv_light_value_fragment = (TextView) v.findViewById(R.id.tv_light_value_fragment);
        tv_soiltemperature_value_fragment = (TextView) v.findViewById(R.id.tv_soiltemperature_value_fragment);
        tv_soilhumidity_value_fragment = (TextView) v.findViewById(R.id.tv_soilhumidity_value_fragment);
        tv_airtemperature_value_fragment = (TextView) v.findViewById(R.id.tv_airtemperature_value_fragment);
        tv_airhumidity_value_fragment = (TextView) v.findViewById(R.id.tv_airhumidity_value_fragment);

        ImageView iv_right_co2_fragment = (ImageView) v.findViewById(R.id.iv_right_co2_fragment);
        ImageView iv_right_air_fragment = (ImageView) v.findViewById(R.id.iv_right_air_fragment);
        ImageView iv_right_guangzhao_frgment = (ImageView) v.findViewById(R.id.iv_right_guangzhao_frgment);
        ImageView iv_right_turang_fragment = (ImageView) v.findViewById(R.id.iv_right_turang_fragment);

        iv_air_state_msg_item = (ImageView) v.findViewById(R.id.iv_air_state_msg_item);
        iv_co2_state_msg_item = (ImageView) v.findViewById(R.id.iv_co2_state_msg_item);
        iv_guangzhao_state_msg_item = (ImageView) v.findViewById(R.id.iv_guangzhao_state_msg_item);
        iv_turang_state_msg_item = (ImageView) v.findViewById(R.id.iv_turang_state_msg_item);

        tv_co2_set_min_fragment = (TextView) v.findViewById(R.id.tv_co2_set_min_fragment);
        tv_co2_set_max_fragment = (TextView) v.findViewById(R.id.tv_co2_set_max_fragment);
        tv_light_set_min_fragment = (TextView) v.findViewById(R.id.tv_light_set_min_fragment);
        tv_light_set_max_fragment = (TextView) v.findViewById(R.id.tv_light_set_max_fragment);
        tv_soil_temp_min_fragment = (TextView) v.findViewById(R.id.tv_soil_temp_min_fragment);
        tv_soil_temp_max_fragment = (TextView) v.findViewById(R.id.tv_soil_temp_max_fragment);
        tv_soil_humi_min_fragment = (TextView) v.findViewById(R.id.tv_soil_humi_min_fragment);
        tv_soil_humi_max_fragment = (TextView) v.findViewById(R.id.tv_soil_humi_max_fragment);

        tv_air_temp_min_fragment = (TextView) v.findViewById(R.id.tv_air_temp_min_fragment);
        tv_air_temp_max_fragment = (TextView) v.findViewById(R.id.tv_air_temp_max_fragment);
        tv_air_humi_min_frgment = (TextView) v.findViewById(R.id.tv_air_humi_min_frgment);
        tv_air_humi_max_fragment = (TextView) v.findViewById(R.id.tv_air_humi_max_fragment);

        iv_right_air_fragment.setOnClickListener(this);
        iv_right_co2_fragment.setOnClickListener(this);
        iv_right_guangzhao_frgment.setOnClickListener(this);
        iv_right_turang_fragment.setOnClickListener(this);

        //轮播图控件绑定
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        ll_point_container = (LinearLayout) v.findViewById(R.id.ll_point_container);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//		viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象

        initData();
        //循环将imageview放入ArrayList中
        initAdapter();
        //获取Http字符串
        getJson();
        //根据最大值最小值设置预警图片
        getScopeValue();

        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
        }.start();

        return v;
    }

    private void initData() {
        // 初始化要显示的数据

        // 图片资源id数组
        imageResIds = new int[]{R.mipmap.bana1, R.mipmap.bana2, R.mipmap.bana3,};

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(getActivity());
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(10, 10);
            if (i != 0)
                layoutParams.leftMargin = 10;

            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void getScopeValue() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GetIpUtil.getGetConfig(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    maxCo2 = jsonObject.getInt("maxCo2");
                    minCo2 = jsonObject.getInt("minCo2");
                    maxLight = jsonObject.getInt("maxLight");
                    minLight = jsonObject.getInt("minLight");
                    maxSoilHumidity = jsonObject.getInt("maxSoilHumidity");
                    minSoilHumidity = jsonObject.getInt("minSoilHumidity");
                    maxSoilTemperature = jsonObject.getInt("maxSoilTemperature");
                    minSoilTemperature = jsonObject.getInt("minSoilTemperature");
                    controlAuto = jsonObject.getInt("controlAuto");
                    maxAirTemperature = jsonObject.getInt("maxAirTemperature");
                    minAirTemperature = jsonObject.getInt("minAirTemperature");
                    maxAirHumidity = jsonObject.getInt("maxAirHumidity");
                    minAirHumidity = jsonObject.getInt("minAirHumidity");
                    HttpScopeValues httpScopeValues = new HttpScopeValues(maxCo2, minCo2, maxLight, minLight, maxSoilHumidity, minSoilHumidity
                            , maxSoilTemperature, minSoilTemperature, controlAuto, maxAirTemperature, minAirTemperature, maxAirHumidity, minAirHumidity);
                    //给条目设置范围值
                    setItemScopeValues(httpScopeValues);
                    //根据大小值改变预警图片
                    setScopeValues(httpScopeValues);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 给条目设置范围值
     */
    private void setItemScopeValues(HttpScopeValues httpScopeValues) {

        tv_co2_set_min_fragment.setText(httpScopeValues.getMinCo2() + "");
        tv_co2_set_max_fragment.setText(httpScopeValues.getMaxCo2() + "");
        tv_light_set_min_fragment.setText(httpScopeValues.getMinLight() + "");
        tv_light_set_max_fragment.setText(httpScopeValues.getMaxLight() + "");

        tv_soil_temp_min_fragment.setText(httpScopeValues.getMinSoilTemperature() + "");
        tv_soil_humi_min_fragment.setText(httpScopeValues.getMinSoilHumidity() + "");
        tv_soil_temp_max_fragment.setText(httpScopeValues.getMaxSoilTemperature() + "");
        tv_soil_humi_max_fragment.setText(httpScopeValues.getMaxSoilHumidity() + "");

        tv_air_temp_min_fragment.setText(httpScopeValues.getMinAirTemperature() + "");
        tv_air_temp_max_fragment.setText(httpScopeValues.getMaxAirTemperature() + "");
        tv_air_humi_min_frgment.setText(httpScopeValues.getMinAirHumidity() + "");
        tv_air_humi_max_fragment.setText(httpScopeValues.getMaxAirHumidity() + "");

    }

    private void setScopeValues(HttpScopeValues httpScopeValues) {
        //当获取到的co2浓度大于maxCo2或小于minCo2时，更换预警图片,相同时预警，超过时危险，正常即正常
        if (co2 > minCo2 && co2 < maxCo2) {
            iv_co2_state_msg_item.setImageResource(R.mipmap.p1);
        } else if (co2 == minCo2 || co2 == maxCo2) {
            iv_co2_state_msg_item.setImageResource(R.mipmap.p2);
        } else if (co2 < minCo2 || co2 > maxCo2) {
            iv_co2_state_msg_item.setImageResource(R.mipmap.p3);
        }

        //当获取到的光照强度大于maxLight或小于minLight时，更换预警图片
        if (light > minLight && light < maxLight) {
            iv_guangzhao_state_msg_item.setImageResource(R.mipmap.p1);
        } else if (light == minLight || light == maxLight) {
            iv_guangzhao_state_msg_item.setImageResource(R.mipmap.p2);
        } else if (light < minLight || light > minLight) {
            iv_guangzhao_state_msg_item.setImageResource(R.mipmap.p3);
        }

        //当获取到土壤湿度大于max或小于xin时没更换预警图片
        if (soilHumidity > minSoilHumidity && soilHumidity < maxSoilHumidity
                && soilTemperature > minSoilTemperature && soilTemperature < maxSoilTemperature) {
            iv_turang_state_msg_item.setImageResource(R.mipmap.p1);
        } else if (soilHumidity == minSoilHumidity && soilTemperature == minSoilTemperature
                || soilHumidity == maxSoilHumidity && soilTemperature == maxSoilTemperature) {
            iv_turang_state_msg_item.setImageResource(R.mipmap.p2);
        } else if (soilHumidity < minSoilHumidity || soilHumidity > maxSoilHumidity ||
                soilTemperature < minSoilTemperature || soilTemperature > maxSoilTemperature) {
            iv_turang_state_msg_item.setImageResource(R.mipmap.p3);
        }

        //当获取到的空气湿度....不想写了
        //当获取到土壤湿度大于max或小于xin时没更换预警图片
        if (airHumidity > minAirHumidity && airHumidity < maxAirHumidity
                && airTemperature > minAirTemperature && airTemperature < maxAirTemperature) {
            iv_air_state_msg_item.setImageResource(R.mipmap.p1);
        } else if (airHumidity == minAirHumidity && airTemperature == minAirTemperature
                || airHumidity == maxAirHumidity && airTemperature == maxAirTemperature) {
            iv_air_state_msg_item.setImageResource(R.mipmap.p2);
        } else if (airHumidity < minAirHumidity || airHumidity > maxAirHumidity ||
                airTemperature < minAirTemperature || airTemperature > maxAirTemperature) {
            iv_air_state_msg_item.setImageResource(R.mipmap.p3);
        }
    }


    /**
     * 设置属性的value
     *
     * @param httpValues
     */
    private void setValues(HttpValues httpValues) {
        tv_co2_value_fragment.setText(httpValues.getCo2() + "");
        tv_light_value_fragment.setText(httpValues.getLight() + "");
        tv_soiltemperature_value_fragment.setText(httpValues.getSoilTemperature() + "");
        tv_soilhumidity_value_fragment.setText(httpValues.getSoilHumidity() + "");
        tv_airtemperature_value_fragment.setText(httpValues.getAirTemperature() + "");
        tv_airhumidity_value_fragment.setText(httpValues.getAirHumidity() + "");
    }


    private void getJson() {

        AsyncHttpClient client = new AsyncHttpClient();
//        "http://172.20.10.7:8890/type/jason/action/getSensor"
        client.get(GetIpUtil.getGetSensor(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    airHumidity = jsonObject.getInt("airHumidity");
                    airTemperature = jsonObject.getInt("airTemperature");
                    soilTemperature = jsonObject.getInt("soilTemperature");
                    soilHumidity = jsonObject.getInt("soilHumidity");
                    co2 = jsonObject.getInt("co2");
                    light = jsonObject.getInt("light");

                    HttpValues httpValues = new HttpValues(airHumidity, airTemperature, soilTemperature, soilHumidity, co2, light);
                    setValues(httpValues);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    /**
     * 绑定两种适配器
     */

    private void initAdapter() {

        ll_point_container.getChildAt(0).setEnabled(true);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right_co2_fragment:
                Intent intent = new Intent();
                intent.setClass(getActivity(), CO2Activity.class);
                startActivity(intent);
                break;
            case R.id.iv_right_air_fragment:
                startActivity(new Intent(getActivity(), AirActivity.class));
                break;
            case R.id.iv_right_guangzhao_frgment:
                startActivity(new Intent(getActivity(), LightActivity.class));
                break;
            case R.id.iv_right_turang_fragment:
                startActivity(new Intent(getActivity(), SoilActivity.class));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        int newPosition = position % imageViewList.size();

//		for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//			View childAt = ll_point_container.getChildAt(position);
//			childAt.setEnabled(position == i);
//		}
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4
//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            container.removeView((View) object);
        }

    }
}

