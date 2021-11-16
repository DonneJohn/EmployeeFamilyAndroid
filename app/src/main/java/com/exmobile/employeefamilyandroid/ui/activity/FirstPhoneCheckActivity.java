package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.presenter.FindPassPresenter;
import com.exmobile.employeefamilyandroid.utils.TimeCountUtil;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by 张聪聪 on 2016/5/24.
 * Email：2353410167@qq.com
 */
@RequiresPresenter(FindPassPresenter.class)
public class FirstPhoneCheckActivity extends BaseHoldBackActivity<FindPassPresenter> {

    public static final String BUNDLE_KEY_PHONE = "BUNDLE_KEY_PHONE";
    @BindView(R.id.et_userphone)
    TextInputLayout mInputUserPhone;
    @BindView(R.id.et_message_code)
    TextInputLayout mInputMessageCode;

    @BindView(R.id.tv_get_verify_code)
    TextView tvgetVerifyCode;
    private String verifyCodeNet, userPhone;

    @Override
    protected String onSetTitle() {
        return "验证手机号";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_first_phone_check;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        String userPhone = getIntent().getStringExtra(BUNDLE_KEY_PHONE);
        if (userPhone != null) {
            mInputUserPhone.getEditText().setText(userPhone);
        }

    }

    @OnClick(R.id.tv_get_verify_code)
    void getCode() {
        userPhone = mInputUserPhone.getEditText().getText().toString();

        if (Utilities.isEmpty(userPhone)) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Utilities.isPhone(userPhone)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        } else {
            TimeCountUtil timeCountUtil = new TimeCountUtil(FirstPhoneCheckActivity.this, 60000, 1000, tvgetVerifyCode);
            timeCountUtil.start();
            getPresenter().getVerifyCode(userPhone);
        }
    }

    public void onGetMessageFailed(String messageRemind) {
        Toast.makeText(this, messageRemind, Toast.LENGTH_SHORT).show();

    }

    public void onGetMessageSuccessful(RespMessage respMessage) {
        verifyCodeNet = respMessage.getResult();
//        Toast.makeText(this, respMessage.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_next)
    void next() {
        String verifyCode = mInputMessageCode.getEditText().getText().toString();

        if (Utilities.isEmpty(verifyCode)) {
            Toast.makeText(this, "验证码为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (verifyCode.equals(verifyCodeNet)) {

            getPresenter().confirmMessageConde(AppManager.LOCAL_LOGINED_USER.getRowID());

        } else {
            Toast.makeText(this, "验证码错误，请检查", Toast.LENGTH_SHORT).show();
        }
    }

    public void onConFirmMessageSuccessful(RespMessage data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
        UIManager.gotoFixPass(this,userPhone);
        finish();
    }

    public void onConFirmMessageFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
