package com.exmobile.employeefamilyandroid.ui.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.CultureBanner;
import com.exmobile.mvpbase.ui.fragment.BaseTabNavFragment;
import com.exmobile.mvpbase.utils.UIHelper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 * Created by 张聪聪 on 2016/6/8.
 * Email：2353410167@qq.com
 */
public class CultureBannerFragment extends BaseTabNavFragment {

    private ArrayList<CultureBanner> banners;
    private ImageView imageView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCurrentItem(0);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (banners == null) {

            banners = (ArrayList<CultureBanner>) getArguments().getSerializable(BUNDLE_TYPE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View setupTabItemView(String title) {
        ImageView view = new ImageView(mContext);

//        view.setBackground(getResources().getDrawable(R.drawable.default_banner));
        int m4 = UIHelper.dip2px(mContext, 4);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(m4, m4);
        int m5 = UIHelper.dip2px(mContext, 5);
        layout.setMargins(m5, m5, m5, m5);
        view.setLayoutParams(layout);
        view.setBackgroundResource(R.drawable.selector_dot_nav);
        return view;
    }

    @Override
    public void onSetupTabs() {

        CultureBanner banner;
        titles = new ArrayList<>();
        for (int i = 0; i < banners.size(); i++) {
            banner = banners.get(i);
            if (banners.get(i).getTitle() != null) {
                titles.add(banners.get(i).getTitle());
            }
            if (banner.getFileName() != null) {
                addTab(banner, i);
            }
        }
    }


    public void addTab(CultureBanner banner, int index) {
        String title = banner.getTitle();
        imageView = (ImageView) setupTabItemView(title);
        imageView.setOnClickListener(view -> {
//            UIManager.showDetail(mContext,banner);
        });

        Picasso.with(mContext).load(banner.getFileName()).placeholder(R.drawable.default_banner).centerCrop().fit().into(imageView);
//        addTab(title, new ViewFragment(imageView), index);
        addTab(title, new ViewFragment(imageView));

    }


}

