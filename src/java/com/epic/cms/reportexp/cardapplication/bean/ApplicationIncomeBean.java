/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.bean;

/**
 *
 * @author nalin
 */
public class ApplicationIncomeBean {
    
    private String applicationId;
    private String incomeCatogary;
    private String incomeCatogaryCode;
    private String amount;
    private String lastUpdatedUser;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIncomeCatogary() {
        return incomeCatogary;
    }

    public void setIncomeCatogary(String incomeCatogary) {
        this.incomeCatogary = incomeCatogary;
    }

    public String getIncomeCatogaryCode() {
        return incomeCatogaryCode;
    }

    public void setIncomeCatogaryCode(String incomeCatogaryCode) {
        this.incomeCatogaryCode = incomeCatogaryCode;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
    
    
    
}
