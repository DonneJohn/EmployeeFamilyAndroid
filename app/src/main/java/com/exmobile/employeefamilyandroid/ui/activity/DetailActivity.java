package com.exmobile.employeefamilyandroid.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.utils.DialogFactory;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseHoldBackActivity {
    public static final String KEY_URL = "url";
    public static final String KEY_TITLE = "title";

    @BindView(R.id.layout_web_view)
    WebView mWebView;

    private ProgressDialog dialog;

    @Override
    protected String onSetTitle() {
        return getIntent().getExtras().getString(KEY_TITLE);
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        onLoading();
        initData();
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

        mWebView.loadUrl(getIntent().getExtras().getString(KEY_URL));
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
