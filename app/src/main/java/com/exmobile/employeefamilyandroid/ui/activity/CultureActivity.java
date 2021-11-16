package com.exmobile.employeefamilyandroid.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.CultureBanner;
import com.exmobile.employeefamilyandroid.presenter.CulturePresenter;
import com.exmobile.employeefamilyandroid.ui.fragment.CultureBannerFragment;
import com.exmobile.employeefamilyandroid.utils.DialogFactory;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.UIHelper;
import com.exmobile.mvpbase.utils.Utilities;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(CulturePresenter.class)
public class CultureActivity extends BaseHoldBackActivity<CulturePresenter> {
    private Dialog dialog;


    @Override
    protected String onSetTitle() {
        return "企业文化";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_culture;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        dialog = DialogFactory.getFactory().getLoadingDialog(this);
        dialog.show();
        getPresenter().getCultureList(AppManager.LOCAL_LOGINED_USER.getFK_Company());
    }

    public void onGetCultureListSuccess(ArrayList<CultureBanner> banners) {
        if (dialog != null) dialog.dismiss();

        Bundle args = new Bundle();
        args.putSerializable(CultureBannerFragment.BUNDLE_TYPE, banners);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, Fragment.instantiate(this, CultureBannerFragment.class.getName(),args))
                .commit();
    }

    public void onGetCultureListError(String message) {
        if (dialog != null) dialog.dismiss();
        if (message != null) {
            Toast.makeText(CultureActivity.this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CultureActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
