package com.exmobile.employeefamilyandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.ServerAPI;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.CompanyInfoBean;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.bean.RespUser;
import com.exmobile.employeefamilyandroid.bean.User;
import com.exmobile.employeefamilyandroid.event.Events;
import com.exmobile.employeefamilyandroid.event.RxBus;
import com.exmobile.employeefamilyandroid.presenter.LoginPresenter;
import com.exmobile.employeefamilyandroid.utils.DialogFactory;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import nucleus.factory.RequiresPresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zcc on 2016/5/9 17:57.
 */
@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseHoldBackActivity<LoginPresenter> {

    private static final String CHANGE_THEME = "CHANGE_THEME";
    @BindView(R.id.et_id)
    TextInputLayout mInputId;
    @BindView(R.id.et_username)
    TextInputLayout mInputUserName;
    @BindView(R.id.et_password)
    TextInputLayout mInputPassword;

    @BindView(R.id.iv_login_logo)
    ImageView ivLoginLogo;

    private String id;
    private String username;
    private String password;

    @BindView(R.id.cb_id)
    CheckBox cbId;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;

    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    private Dialog dialog;
    private SharedPreferences preferences, userPreference;

    private String companyNeedIDCode;

    @Override
    protected String onSetTitle() {
        return "登录";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        preferences = SharePreferenceManager.getRememberUser(this);
        userPreference = SharePreferenceManager.getLocalUser(this);
        if (preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFMOBILE, null) != null) {
            mInputUserName.getEditText().setText(preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFMOBILE, null));
        }

        if (preferences.getString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNAME, null) != null) {
            String companyName = preferences.getString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNAME, null);
            tvCompanyName.setText(companyName);
        }


        if (preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null) != null) {
            if (!Utilities.isEmpty(preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null))) {
                cbId.setChecked(true);
            }
            if (preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null).length() > 6) {
                String staffId = preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null).substring(0,
                        preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null).length() - 6);
                staffId = staffId + "******";
                mInputId.getEditText().setText(staffId);
            } else {
                mInputId.getEditText().setText(preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null));
            }

        } else {
            cbId.setChecked(false);
        }

        if (preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, null) != null) {
            if (!Utilities.isEmpty(preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, null))) {
                cbRemember.setChecked(true);
            }
            mInputPassword.getEditText().setText(preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, null));
        } else {
            cbRemember.setChecked(false);
        }

        if (AppManager.LOCAL_LOGINED_USER == null) return;
        String logourl = AppManager.LOCAL_LOGINED_USER.getCompanyInfo().get(0).getLogoName();


        if (!Utilities.isEmpty(logourl)) {
            Picasso.with(this) //
                    .load(logourl) //
                    .placeholder(R.drawable.login_logo) //
//                    .error(R.drawable.no_pic) //
                    .fit() //
                    .tag(this) //
                    .into(ivLoginLogo);
        }
    }


    @OnClick(R.id.btn_submit)
    void submit() {
        // 校验
        id = mInputId.getEditText().getText().toString();
        if (id.contains("******")) {
            if (preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null) != null) {
                id = preferences.getString(SharePreferenceManager.LocalUser.KEY_STAFFID, null);
            }
        }
        username = mInputUserName.getEditText().getText().toString();
        password = mInputPassword.getEditText().getText().toString();
        if (Utilities.isEmpty(id)) {
            mInputId.setError("身份证号不能为空");
            return;
        } else if (Utilities.isEmpty(password)) {
            mInputPassword.setError("密码不能为空");
            return;
        } else if (Utilities.isEmpty(username)) {
            mInputUserName.setError("账号不能为空");
            return;
        }
        //
        dialog = DialogFactory.getFactory().getLoadingDialog(this);
        dialog.show();
//        getPresenter().login(id, username, password);
        Call<ResponseBody> call = ServerAPI.getEmployeeAPI(ServerAPI.baseUrl).login(id, username, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                String body = null;
                try {
                    body = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                try {
                    RespUser respUser = gson.fromJson(body, RespUser.class);
                    if (respUser.getCode() == 1) {
                        onLoadSuccessful(respUser.getResult().get(0));
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    RespMessage respMessage = gson.fromJson(body, RespMessage.class);
                    if (respMessage.getCode() == 0) {
                        onLoadError(respMessage);
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    public void onLoadSuccessful(User user) {
        if (dialog != null) dialog.dismiss();

        SharedPreferences.Editor editor = userPreference.edit();
        editor.putString(SharePreferenceManager.LocalUser.KEY_ROWID, user.getRowID());

        SharedPreferences.Editor rememberEditor = preferences.edit();

        if (cbId.isChecked()) {
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFID, user.getStaffID());
        } else {
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFID, "");
        }

        if (cbRemember.isChecked()) {
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFMOBILE, mInputUserName.getEditText().getText().toString());
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, mInputPassword.getEditText().getText().toString());


            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFNAME, user.getStaffName());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFSEX, user.getStaffSex());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_COMPANY, user.getFK_Company());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_DEPARTMENT, user.getFK_Department());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_ROLE, user.getFK_Role());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FK_TITLE, user.getFK_Title());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFICON, user.getStaffIcon());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFEMAIL, user.getStaffEmail());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPHONE, user.getStaffPhone());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPHONEEXT, user.getStaffPhoneExt());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFWECHATID, user.getStaffWeChatID());
            editor.putString(SharePreferenceManager.LocalUser.KEY_CURRENTIP, user.getCurrentIP());
            editor.putString(SharePreferenceManager.LocalUser.KEY_FIRSTTIMELOGIN, user.getFirstTimeLogin());
            editor.putString(SharePreferenceManager.LocalUser.KEY_API_IP, user.getAPI_IP());
            editor.putString(SharePreferenceManager.LocalUser.KEY_TITLENAME, user.getTitleName());


            editor.putLong(SharePreferenceManager.LocalUser.KEY_LAST_LOGIN_TIME, new Date().getTime());
            editor.putBoolean(SharePreferenceManager.LocalUser.KEY_LOGIN_STATE, true);

            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFFQQ, user.getStaffQQ());
            editor.putString(SharePreferenceManager.LocalUser.KEY_STAFBIRTHDAY, user.getStafBirthday());

            editor.putString(SharePreferenceManager.LocalUser.KEY_PASSWORDMOFIED, user.getPasswordMofied());

            CompanyInfoBean companyInfoBean = user.getCompanyInfo().get(0);
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_ROWID, companyInfoBean.getRowID());
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_LOGONAME, companyInfoBean.getLogoName());

            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNAME, companyInfoBean.getCompanyName());

            editor.putInt(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYCOLOR, companyInfoBean.getCompanyColor());
            editor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNEEDIDCODE, companyInfoBean.getCompanyNeedIDCode());

        } else {
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFMOBILE, "");
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_STAFFPASSWORD, "");
            rememberEditor.putString(SharePreferenceManager.LocalUser.KEY_COMPANY_COMPANYNAME, "");
        }
        editor.apply();
        rememberEditor.apply();


        HashSet<String> tags = new HashSet<>();
        tags.add(Utilities.transTag(user.getFK_Company()));
        tags.add(Utilities.transTag(user.getFK_Department()));
        tags.add(user.getStaffPhone());
        JPushInterface.setAliasAndTags(this, user.getStaffMobile(), tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("JPush", i + "--------------------------");
                Log.i("JPush", s + "--------------------------");
            }
        });

        AppManager.LOCAL_LOGINED_USER = user;

        //设置主题

        int themeId = user.getCompanyInfo().get(0).getCompanyColor();
        SharedPreferences preferences = SharePreferenceManager.getApplicationSetting(this);

        SharedPreferences.Editor themeEditor = preferences.edit();
        switch (themeId) {

            case 1:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.DEFAULT.getKey());
                break;
            case 2:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.BLUEGREEN.getKey());
                break;
            case 3:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.GREEN.getKey());
                break;
            case 4:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.BLUE.getKey());
                break;

            case 5:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.YELLOW.getKey());
                break;

            case 6:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.GRAYBLACK.getKey());
                break;

            case 7:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.PURPLE.getKey());
                break;
            case 8:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.RED.getKey());
                break;
            default:
                themeEditor.putInt(SharePreferenceManager.ApplicationSetting.KEY_THEME, SharePreferenceManager.ApplicationSetting.ApplicationTheme.DEFAULT.getKey());
        }

        themeEditor.apply();


        //通知用户登陆
        if (user.getFirstTimeLogin().equals("1")) {
            SharedPreferences.Editor firstEditor = SharePreferenceManager.getLocalUser(this).edit();
            firstEditor.putBoolean(SharePreferenceManager.LocalUser.KEY_LOGIN_STATE, false);
            firstEditor.apply();

            companyNeedIDCode = user.getCompanyInfo().get(0).getCompanyNeedIDCode();
            getPresenter().confirmMessageConde(user.getRowID());
//            UIManager.gotoFirstPhoneCheck(this, username);
//            if (user.getCompanyInfo().get(0).getCompanyNeedIDCode().equals("0")) {
//                UIManager.gotoFixPass(this, username);
//
//            } else if (user.getCompanyInfo().get(0).getCompanyNeedIDCode().equals("1")) {
//                UIManager.gotoFirstPhoneCheck(this, username);
//            }
        } else if (user.getFirstTimeLogin().equals("0")) {
//            RxBus.getInstance().send(Events.EventEnum.DELIVER_LOGIN, null);
//            UIManager.showMain(this);

            if (user.getPasswordMofied().equals("0")) {
                UIManager.gotoFixPass(this, username);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(CHANGE_THEME, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }


        }
//        finish();

    }

    public void onLoadFailed(String message) {
        if (dialog != null) dialog.dismiss();
        if (message == null) {

            return;
        } else {

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            mInputUserName.setError(message);
        }

    }


    public void onLoadError(RespMessage data) {
        if (dialog != null) dialog.dismiss();
        if (data != null) {
            Toast.makeText(this, data.getResult(), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_forget_pass)
    void forgetPass() {
        Toast.makeText(LoginActivity.this, "请联系人力资源部重置密码", Toast.LENGTH_SHORT).show();
    }


    public void onConFirmMessageSuccessful(RespMessage data) {

        if (data.getCode() == 1) {
            if (companyNeedIDCode.equals("1")) {
                UIManager.gotoFirstPhoneCheck(this, username);
            } else {
                UIManager.gotoFixPass(this, username);
            }
        }

        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
//        UIManager.gotoFixPass(this,username);
//        finish();
    }

    public void onConFirmMessageFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
