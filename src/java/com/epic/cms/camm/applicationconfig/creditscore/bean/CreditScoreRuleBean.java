/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.bean;

import java.util.Date;

/**
 *
 * @author upul
 */
public class CreditScoreRuleBean {
    
    private String ruleCode;
    private String ruleName;
    private String fieldName;
    private String fieldNameDes;
    private String condition;
    private String conditionDes;
    private String value;
    private String maxValue;
    private String ruleNoOne;
    private String ruleNoTwo;
    private String score;
    private String status;
    private String statusDes;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;

    public String getFieldNameDes() {
        return fieldNameDes;
    }

    public void setFieldNameDes(String fieldNameDes) {
        this.fieldNameDes = fieldNameDes;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleNoOne() {
        return ruleNoOne;
    }

    public void setRuleNoOne(String ruleNoOne) {
        this.ruleNoOne = ruleNoOne;
    }

    public String getRuleNoTwo() {
        return ruleNoTwo;
    }

    public void setRuleNoTwo(String ruleNoTwo) {
        this.ruleNoTwo = ruleNoTwo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

 
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getConditionDes() {
        return conditionDes;
    }

    public void setConditionDes(String conditionDes) {
        this.conditionDes = conditionDes;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
    
    
    
    
    
}
