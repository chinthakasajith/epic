/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.bean;

import java.util.Date;

/**
 *
 * @author badrika
 */
public class BillingStatementProfileBean {
    
    private String profileCode;
    private String description;
    private String gracePeroid;
    private String minimumDueFlatAmount;
    private String minimumDuePercentage;
    private String minimumDueCombination;
    private String statementGenerationStatus;
    private String conditionalBalance;
    private String conditionalCrOrDr;
    private String numberOfActivity;
    
    private String status;
    private String statusDes;    
    private String lastUpdatedUser;
    
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getNumberOfActivity() {
        return numberOfActivity;
    }

    public void setNumberOfActivity(String NumberOfActivity) {
        this.numberOfActivity = NumberOfActivity;
    }

    public String getConditionalBalance() {
        return conditionalBalance;
    }

    public void setConditionalBalance(String conditionalBalance) {
        this.conditionalBalance = conditionalBalance;
    }

    public String getConditionalCrOrDr() {
        return conditionalCrOrDr;
    }

    public void setConditionalCrOrDr(String conditionalCrOrDr) {
        this.conditionalCrOrDr = conditionalCrOrDr;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGracePeroid() {
        return gracePeroid;
    }

    public void setGracePeroid(String gracePeroid) {
        this.gracePeroid = gracePeroid;
    }

    

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getMinimumDueCombination() {
        return minimumDueCombination;
    }

    public void setMinimumDueCombination(String minimumDueCombination) {
        this.minimumDueCombination = minimumDueCombination;
    }

    public String getMinimumDueFlatAmount() {
        return minimumDueFlatAmount;
    }

    public void setMinimumDueFlatAmount(String minimumDueFlatAmount) {
        this.minimumDueFlatAmount = minimumDueFlatAmount;
    }

    public String getMinimumDuePercentage() {
        return minimumDuePercentage;
    }

    public void setMinimumDuePercentage(String minimumDuePercentage) {
        this.minimumDuePercentage = minimumDuePercentage;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getStatementGenerationStatus() {
        return statementGenerationStatus;
    }

    public void setStatementGenerationStatus(String statementGenerationStatus) {
        this.statementGenerationStatus = statementGenerationStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    
    
    
    
}
