package com.exmobile.employeefamilyandroid.bean;

import com.exmobile.mvpbase.domain.Entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class Rule extends Entity{
    private String RowID;
    private String CompanyRule;
    private String CompanyRuleTitle;
    private String RuleShortText;

    public String getRowID() {
        return RowID;
    }

    public void setRowID(String rowID) {
        RowID = rowID;
    }

    public String getCompanyRule() {
        return CompanyRule;
    }

    public void setCompanyRule(String companyRule) {
        CompanyRule = companyRule;
    }

    public String getCompanyRuleTitle() {
        return CompanyRuleTitle;
    }

    public void setCompanyRuleTitle(String companyRuleTitle) {
        CompanyRuleTitle = companyRuleTitle;
    }

    public String getRuleShortText() {
        return RuleShortText;
    }

    public void setRuleShortText(String ruleShortText) {
        RuleShortText = ruleShortText;
    }

    public Rule() {

    }

    public Rule(String rowID, String companyRule, String companyRuleTitle, String ruleShortText) {

        RowID = rowID;
        CompanyRule = companyRule;
        CompanyRuleTitle = companyRuleTitle;
        RuleShortText = ruleShortText;
    }
}
