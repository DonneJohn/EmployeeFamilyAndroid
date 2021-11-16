package com.exmobile.employeefamilyandroid;


import com.exmobile.employeefamilyandroid.bean.RespCultureBanner;
import com.exmobile.employeefamilyandroid.bean.RespMessage;
import com.exmobile.employeefamilyandroid.bean.RespNotice;
import com.exmobile.employeefamilyandroid.bean.RespSalaryDetail;
import com.exmobile.employeefamilyandroid.bean.RespSalaryItem;
import com.exmobile.employeefamilyandroid.bean.RespSalaryMonth;
import com.exmobile.employeefamilyandroid.bean.RespSalaryYear;
import com.exmobile.employeefamilyandroid.bean.RespUser;
import com.exmobile.employeefamilyandroid.bean.RespCulture;
import com.exmobile.employeefamilyandroid.bean.RespRule;
import com.exmobile.employeefamilyandroid.bean.RespVersion;
import com.exmobile.employeefamilyandroid.bean.RespVote;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by：张聪聪 on 2016/4/22 10:35.
 * Email：2353410167@qq.com
 */
public class ServerAPI {

    public static HealthAPI healthAPI;
    public static String baseUrl = "http://115.29.233.245:8015/API/";

    public static HealthAPI getEmployeeAPI(String url) {
        if (healthAPI == null) {
            OkHttpClient httpClient = new OkHttpClient();
            healthAPI = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .client(httpClient)
                    .build()
                    .create(HealthAPI.class);
        }

        return healthAPI;
    }

    public interface HealthAPI {

        @POST("Login.ashx")
        Observable<RespUser> loginFirst(@Query("ID") String id,
                                   @Query("Phone") String phone,
                                   @Query("Password") String password);

        @POST("Login.ashx")
        Call<ResponseBody> login(@Query("ID") String id,
                                 @Query("Phone") String phone,
                                 @Query("Password") String password);


        /**
         * 企业文化列表
         *
         * @param company 公司ID
         */
        @POST("GetCultureList.ashx")
        Observable<RespCultureBanner> getCultureList(@Query("Company") String company);

        /**
         * 规章制度列表
         *
         * @param pageNum 页码
         * @param company 公司ID
         */
        @POST("GetRuleList.ashx")
        Observable<RespRule> getRuleList(@Query("PageNum") int pageNum,
                                         @Query("Company") String company);

        /**
         * 调查报告列表
         *
         * @param pageNum   页码
         * @param userRowID 用户RowID
         */
        @POST("GetVoteList.ashx")
        Observable<RespVote> getVoteList(@Query("PageNum") int pageNum,
                                         @Query("UserRowID") String userRowID,
                                         @Query("FK_Department") String fkDepartment);

        /**
         * @param rowID
         * @return
         */
        @POST("GetUserInfo.ashx")
        Observable<RespUser> getUserInfo(@Query("RowID") String rowID);


        @POST("GetNoticeList.ashx")
        Observable<RespNotice> getNoticeList(@Query("PageNum") int pageNum,
                                             @Query("FK_Department") String fkDepartment,
                                             @Query("Keyword") String Keyword);

        /**
         * @param userRowID 用户RowID
         * @param data      格式：
         *                  [{\"VoteRowID\":\"f7ba1f03-ce8c-45fa-a94e-771b9816127d\",\"Answer\":\"A\"}]   VoteRowID是投票的ID，Answer是选中的答案
         * @return
         */
        @POST("SubmitVoteResult.ashx")
        Observable<RespMessage> submitVoteResult(@Query("UserRowID") String userRowID,
                                                 @Query("Data") String data);

        @POST("UpdatePassword.ashx")
        Observable<RespMessage> updatePassword(@Query("Phone") String phone,
                                               @Query("OldPassword") String oldPassword,
                                               @Query("Password") String password);

        @POST("GetMessageCode.ashx")
        Observable<RespMessage> getMessageCode(@Query("Mobile") String mobile);

        @POST("ConfirmMessageCode.ashx")
        Observable<RespMessage> confirmMessageCode(@Query("RowID") String rowID);


        @POST("GetMonthList.ashx")
        Observable<RespSalaryMonth> getMonthList(@Query("Company") String company);

        @POST("GetSalaryItemList.ashx")
        Observable<RespSalaryItem> getSalaryItemList(@Query("Company") String company,
                                                     @Query("Year") String year,
                                                     @Query("Month") String month,
                                                     @Query("StaffID") String staffID);

        @POST("GetYearList.ashx")
        Observable<RespSalaryYear> getYearList(@Query("Company") String company);

        @POST("GetSalaryDetail.ashx")
        Observable<RespSalaryDetail> getSalaryDetail(@Query("_strcompanyRowID") String fK_Company,
                                                     @Query("_straccessRowID") String accessRowID,
                                                     @Query("_strstaffRowID") String rowID);

        @POST("GetVersion.ashx")
        Observable<RespVersion> getVersion();
    }
}
