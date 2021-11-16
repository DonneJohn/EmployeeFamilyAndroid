package com.exmobile.employeefamilyandroid.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.AppManager;
import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.UIManager;
import com.exmobile.employeefamilyandroid.bean.User;
import com.exmobile.employeefamilyandroid.presenter.MePresenter;
import com.exmobile.employeefamilyandroid.utils.DialogHelp;
import com.exmobile.mvpbase.model.SharePreferenceManager;
import com.exmobile.mvpbase.ui.fragment.BaseFragment;
import com.exmobile.mvpbase.utils.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by abu on 2016/5/9 15:02.
 */

@RequiresPresenter(MePresenter.class)
public class MeFragment extends BaseFragment<MePresenter> {

    @BindView(R.id.iv_portrait)
    ImageView mPortrait;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_title_name)
    TextView tvTitleName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void initView() {

        User user = AppManager.LOCAL_LOGINED_USER;
        if (user != null) {
            if (!Utilities.isEmpty(user.getCompanyInfo().get(0).getLogoName())) {
                Picasso.with(getContext()).load(user.getCompanyInfo().get(0).getLogoName()).placeholder(R.mipmap.ic_launcher).fit().tag(this).into(mPortrait);
            }

            if (user.getStaffMobile() != null) {
                tvMobile.setText(user.getStaffMobile());
            }

            if (user.getStaffName() != null) {
                tvName.setText(user.getStaffName());
            }

            if (user.getTitleName() != null) {
                tvTitleName.setText("( " + user.getTitleName() + " )");
            }
        }


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_me, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
//            UIManager.gotoSet(getContext());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.tv_logout)
    void logout() {

        DialogHelp.getConfirmDialog(getContext(), "您确定退出登录吗", (dialog, which) -> {

//            AppManager.LOCAL_LOGINED_USER = null;
            SharedPreferences.Editor editor = SharePreferenceManager.getLocalUser(getContext()).edit();
            editor.clear().commit();
            UIManager.showLogin(getContext());


            getActivity().finish();
        }, null).show();
    }

    @OnClick(R.id.rl_usercenter)
    void gotoUserCenter() {
        UIManager.gotoUserCenter(getContext());
    }

    @OnClick(R.id.tv_fix_pass)
    void fixPass() {
        UIManager.gotoFixPass(getContext(), AppManager.LOCAL_LOGINED_USER.getStaffPhone());
    }
}
