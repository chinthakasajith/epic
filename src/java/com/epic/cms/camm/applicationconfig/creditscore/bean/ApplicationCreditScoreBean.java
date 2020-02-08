/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.bean;

/**
 *
 * @author mahesh_m
 */
public class ApplicationCreditScoreBean {
    private String applicationId;
    private String ruleCode;
    private String score;
    private String lastUpdateduser;
    private String lastUpdatedTime;
    private String createdTime;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdateduser() {
        return lastUpdateduser;
    }

    public void setLastUpdateduser(String lastUpdateduser) {
        this.lastUpdateduser = lastUpdateduser;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
