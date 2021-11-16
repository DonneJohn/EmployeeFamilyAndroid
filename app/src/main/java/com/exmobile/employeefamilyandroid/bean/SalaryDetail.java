package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

/**
 * Created by 张聪聪 on 2016/5/26.
 * Email：2353410167@qq.com
 */
public class SalaryDetail extends Entity {

    private String ItemName;
    private String ItemValue;

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemValue() {
        return ItemValue;
    }

    public void setItemValue(String itemValue) {
        ItemValue = itemValue;
    }

    public SalaryDetail() {

    }

    public SalaryDetail(String itemName, String itemValue) {

        ItemName = itemName;
        ItemValue = itemValue;
    }
}
