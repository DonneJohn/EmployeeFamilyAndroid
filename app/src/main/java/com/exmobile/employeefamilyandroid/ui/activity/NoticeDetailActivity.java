package com.exmobile.employeefamilyandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmobile.employeefamilyandroid.R;
import com.exmobile.employeefamilyandroid.bean.Notice;
import com.exmobile.mvpbase.ui.activity.BaseHoldBackActivity;
import com.exmobile.mvpbase.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通知详情页面
 * Created by Administrator on 2016/4/11.
 */
public class NoticeDetailActivity extends BaseHoldBackActivity {
    public static final String BUNDLE_KEY_NOTICE_OBJECT = "BUNDLE_KEY_NOTICE_OBJECT";

    @BindView(R.id.tv_notice_detail_title)
    TextView title;

    @BindView(R.id.tv_notice_detail_date)
    TextView date;

    @BindView(R.id.tv_notice_detail_content)
    TextView content;

    @BindView(R.id.iv_item_top)
    ImageView ivItemTop;

    @Override
    protected String onSetTitle() {
        return "公告";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Notice notice = (Notice) getIntent().getSerializableExtra(BUNDLE_KEY_NOTICE_OBJECT);
        if (notice != null) {
            title.setText(notice.getCompanyNotice());
            content.setText(notice.getCompanyNotice());
            date.setText(Utilities.dateFormat2(notice.getCreateTime()));
            if (notice.getNoticeTop().equals("1")) {
                ivItemTop.setVisibility(View.VISIBLE);
            }else if (notice.getNoticeTop().equals("0")){
                ivItemTop.setVisibility(View.INVISIBLE);
            }
        }
    }
}
