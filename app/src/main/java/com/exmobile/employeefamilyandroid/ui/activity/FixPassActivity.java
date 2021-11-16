package com.exmobile.employeefamilyandroid.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.presenter.FixPassPresenter;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * 用户中心修改密码界面
 * Created by Administrator on 2016/4/18.
 */
@RequiresPresenter(FixPassPresenter.class)
public class FixPassActivity extends BaseHoldBackActivity<FixPassPresenter> {
    public static final String BUNDLE_KEY_PHONE = "BUNDLE_KEY_PHONE";
    @BindView(R.id.et_old_pass)
    EditText etOldPass;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.et_new_pass_again)
    EditText etNewPassAgain;

    @Override
    protected String onSetTitle() {
        return "修改密码";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_fix_pass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_save)
    void save() {
        String oldPass = etOldPass.getText().toString();
        String pass = etNewPass.getText().toString();
        String passAgain = etNewPassAgain.getText().toString();
        String userPhone = getIntent().getStringExtra(BUNDLE_KEY_PHONE);

        if (Utilities.isEmpty(oldPass)) {
            etOldPass.setError("原密码不能为空");
            return;
        } else if (Utilities.isEmpty(pass)) {
            etNewPass.setError("新密码不能为空");
            return;
        } else if (Utilities.isEmpty(passAgain)) {
            etNewPassAgain.setError("新密码不能为空");
            return;
        } else if (!pass.equals(passAgain)) {
            Toast.makeText(this, "两次密码不一致，请检查~！", Toast.LENGTH_SHORT).show();

        } else {
            getPresenter().updatePass(userPhone, oldPass, pass);
        }
    }

    public void onFixSuccessful(RespMessage result) {
        AppManager.LOCAL_LOGINED_USER.setStaffPassword(etNewPass.getText().toString());
        SharedPreferences.Editor rememEditor = SharePreferenceManager.getRememberUser(this).edit();
        rememEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, etNewPass.getText().toString());
        rememEditor.apply();

        SharedPreferences.Editor firstEditor = SharePreferenceManager.getLocalUser(this).edit();
        firstEditor.putBoolean(SharePreferenceManager.LocalUser.KEY_LOGIN_STATE, true);
        firstEditor.apply();
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_LONG).show();
        UIManager.showMain(this);
        finish();

    }

    public void onFixFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
