package com.yinhao.intelligentagriculture.activity.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.yinhao.intelligentagriculture.R;
import com.yinhao.intelligentagriculture.activity.utils.GetIpUtil;


public class SplashActivity extends Activity {

    private Button btn_confirm_dialog_ip;
    private View view;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showDialog();
        }
    };
    private EditText et_ip_number_dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(0, 3000);
    }


    private void showDialog() {
        view = View.inflate(this, R.layout.dialog_setting_ip, null);
        scaleAnimation(view);
        btn_confirm_dialog_ip = (Button) view.findViewById(R.id.btn_confirm_dialog_ip);
        et_ip_number_dialog = (EditText) view.findViewById(R.id.et_ip_number_dialog);

        //接受输入的ip地址
        String ip = et_ip_number_dialog.getText().toString();
        GetIpUtil.IP = ip;
        //ip地址的正则表达式
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        btn_confirm_dialog_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


    /**
     * 开启弹出对话框的缩放动画
     *
     * @param view 播放动画的视图
     */
    private void scaleAnimation(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

}
