package com.exmobile.employeefamilyandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.exmobile.employeefamilyandroid.bean.SalaryItem;
import com.exmobile.employeefamilyandroid.bean.Vote;
import com.exmobile.employeefamilyandroid.bean.Notice;
import com.exmobile.employeefamilyandroid.ui.activity.ChangeThemeActivity;
import com.exmobile.employeefamilyandroid.ui.activity.ContactDetailActivity;
import com.exmobile.employeefamilyandroid.ui.activity.CultureActivity;
import com.exmobile.employeefamilyandroid.ui.activity.DetailActivity;
import com.exmobile.employeefamilyandroid.ui.activity.FirstPhoneCheckActivity;
import com.exmobile.employeefamilyandroid.ui.activity.FixPassActivity;
import com.exmobile.employeefamilyandroid.ui.activity.LoginActivity;
import com.exmobile.employeefamilyandroid.ui.activity.MainActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryHistoryListActivity;
import com.exmobile.employeefamilyandroid.ui.activity.NoticeDetailActivity;
import com.exmobile.employeefamilyandroid.ui.activity.RuleActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryItemActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SalaryItemDetailActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SearchNoticeActivity;
import com.exmobile.employeefamilyandroid.ui.activity.SettingActivity;
import com.exmobile.employeefamilyandroid.ui.activity.UserCenterActivity;
import com.exmobile.employeefamilyandroid.ui.activity.VoteActivity;
import com.exmobile.employeefamilyandroid.ui.activity.VoteDetailActivity;
import com.exmobile.employeefamilyandroid.ui.activity.YearListActivity;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UIManager {
    /**
     * 进入企业文化界面
     */
    public static void gotoCulture(Context context) {
        Intent intent = new Intent(context, CultureActivity.class);
        context.startActivity(intent);
    }

    /**
     * 进入规章制度界面
     */
    public static void gotoRule(Context context) {
        Intent intent = new Intent(context, RuleActivity.class);
        context.startActivity(intent);
    }

    /**
     * 进入在线调查界面
     */
    public static void gotoVote(Context context) {
        Intent intent = new Intent(context, VoteActivity.class);
        context.startActivity(intent);
    }

    public static void jumptoLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    /**
     * 加载URL界面
     *
     * @param url 需要调转的URL
     */
    public static void gotoDetail(Context context, String url, String title) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DetailActivity.KEY_URL, url);
        intent.putExtra(DetailActivity.KEY_TITLE, title);
        context.startActivity(intent);
    }

    /**
     * 进入调查报告界面
     */
    public static void gotoVoteDetail(Context context, Vote vote) {
        Intent intent = new Intent(context, VoteDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VoteDetailActivity.KEY_VOTE, vote);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void gotoSearchNotice(Context context, String keyword) {
        Intent intent = new Intent(context, SearchNoticeActivity.class);
        intent.putExtra(SearchNoticeActivity.BUNDLE_KEYWORD, keyword);
        context.startActivity(intent);
    }

    public static void showNoticeDetail(Context context, Notice item) {
        Intent intent = new Intent(context, NoticeDetailActivity.class);
        intent.putExtra(NoticeDetailActivity.BUNDLE_KEY_NOTICE_OBJECT, item);
        context.startActivity(intent);
    }

    public static void gotoSet(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void gotoChangeTheme(Context context) {
        Intent intent = new Intent(context, ChangeThemeActivity.class);
        context.startActivity(intent);
    }

    public static void showLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void gotoUserCenter(Context context) {
        Intent intent = new Intent(context, UserCenterActivity.class);
        context.startActivity(intent);
    }

    public static void gotoFixPass(Context context,String userPhone) {
        Intent intent = new Intent(context, FixPassActivity.class);
        intent.putExtra(FixPassActivity.BUNDLE_KEY_PHONE,userPhone);
        context.startActivity(intent);
    }

    public static void showMain(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void gotoFirstPhoneCheck(Context context,String userPhone) {
        Intent intent = new Intent(context, FirstPhoneCheckActivity.class);
        intent.putExtra(FirstPhoneCheckActivity.BUNDLE_KEY_PHONE,userPhone);
        context.startActivity(intent);
    }

    public static void gotoSalaryItemList(Context context, String year, String month) {
        Intent intent = new Intent(context, SalaryItemActivity.class);
        intent.putExtra(SalaryItemActivity.BUNDLE_KEY_YEAR, year);
        intent.putExtra(SalaryItemActivity.BUNDLE_KEY_MONTH, month);
        context.startActivity(intent);
    }

    public static void gotoSalaryHistoryList(Context context) {
        Intent intent = new Intent(context, SalaryHistoryListActivity.class);
        context.startActivity(intent);
    }

    public static void showSalaryItemDetail(Context context, SalaryItem item) {
        Intent intent = new Intent(context, SalaryItemDetailActivity.class);
        intent.putExtra(SalaryItemDetailActivity.BUNDLE_KEY_SALARY_ITEM, item);
        context.startActivity(intent);
    }

    public static void gotoGetYearList(Context context) {
        Intent intent = new Intent(context, YearListActivity.class);
        context.startActivity(intent);

    }

    public static void gotoContact(Context context) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        context.startActivity(intent);
    }
}
