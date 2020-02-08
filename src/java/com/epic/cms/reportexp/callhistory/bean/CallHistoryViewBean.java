/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.callhistory.bean;

/**
 *
 * @author chanuka
 */
public class CallHistoryViewBean {

    private String callHistoryId = null;
    private String callLogId = null;
    private String operation = null;
    private String oldValue = null;
    private String newValue = null;
    private String remarks = null;
    private String lastUpdatedUser = null;
    private String lastUpdatedTime = null;
    
    private String cardNo = null;
    private String accountNo = null;
    private String customerId = null;
    private String applicationId = null;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    

    public String getCallHistoryId() {
        return callHistoryId;
    }

    public void setCallHistoryId(String callHistoryId) {
        this.callHistoryId = callHistoryId;
    }

    public String getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(String callLogId) {
        this.callLogId = callLogId;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
}
