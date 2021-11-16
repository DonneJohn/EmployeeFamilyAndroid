package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.User;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by：张聪聪 on 2016/5/13 16:05.
 * Email：2353410167@qq.com
 */
public class UserCenterActivity extends BaseHoldBackActivity {

    @BindView(R.id.iv_portrait)
    ImageView mPortrait;

    @BindView(R.id.tv_person_nick_name)
    TextView tvName;

    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_qq)
    TextView tvQQ;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_wetchat)
    TextView tvWetChat;
    @Override
    protected String onSetTitle() {
        return "我的";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_user_center;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        User user = AppManager.LOCAL_LOGINED_USER;
        if (user == null)
            return;
        if (!Utilities.isEmpty(user.getCompanyInfo().get(0).getLogoName())){
            Picasso.with(this).load(user.getCompanyInfo().get(0).getLogoName()).placeholder(R.mipmap.ic_launcher).fit().tag(this).into(mPortrait);
        }

        tvId.setText(user.getStaffID());
        tvName.setText(user.getStaffName());
        tvJob.setText(user.getTitleName());
        tvSex.setText(user.getStaffSex());
        tvBirthday.setText(Utilities.dateFormat2(user.getStafBirthday()));
        tvPhone.setText(user.getStaffPhone());
        tvQQ.setText(user.getStaffQQ());
        tvTel.setText(user.getStaffPhoneExt());
        tvEmail.setText(user.getStaffEmail());
        tvWetChat.setText(user.getStaffWeChatID());
    }
}
