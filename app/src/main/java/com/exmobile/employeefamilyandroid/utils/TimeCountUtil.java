package com.exmobile.employeefamilyandroid.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;


/**
 * Created by zcc on 2015/12/9.
 */
public class TimeCountUtil extends CountDownTimer {
    private Context mActivity;
    private TextView btn;//按钮

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil(Context mActivity, long millisInFuture, long countDownInterval, TextView btn) {
        super(millisInFuture, countDownInterval);
        this.mActivity = mActivity;
        this.btn = btn;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setTextSize(12f);
        btn.setText(millisUntilFinished / 1000 + "秒后重新发送");//设置倒计时时间

        //设置按钮为灰色，这时是不能点击的
        btn.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.selector_shape_button_rectangle_grayblack));
        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
        btn.setText(span);

    }


    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        btn.setText("重新获取验证码");
        btn.setClickable(true);//重新获得点击
        btn.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.selector_shape_button_rectangle_default));//还原背景色

    }


}