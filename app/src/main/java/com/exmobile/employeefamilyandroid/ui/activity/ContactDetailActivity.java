package com.exmobile.employeefamilyandroid.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.utils.DialogFactory;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcc on 2016/4/8.
 */
public class ContactDetailActivity extends BaseHoldBackActivity {
    private String contactUrl = "http://115.29.233.245:8015/Appweb/AddressList.aspx?Company=";

    @BindView(R.id.layout_web_view)
    WebView mWebView;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initView();
        onLoading();
        initData();
    }

    @Override
    protected String onSetTitle() {
        return "通讯录";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_contact_detail;
    }

    private void initData() {


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                onLoadFinished();
            }

        });


        if (AppManager.LOCAL_LOGINED_USER.getFK_Company() != null) {

            mWebView.loadUrl(contactUrl + AppManager.LOCAL_LOGINED_USER.getFK_Company());
        }


    }

    private void initView() {

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
    }

    /**
     * 当加载中, 显示dialog
     */
    public void onLoading() {
        dialog = DialogFactory.getFactory().getLoadingDialog(this);
        dialog.show();
    }

    public void onLoadFinished() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
