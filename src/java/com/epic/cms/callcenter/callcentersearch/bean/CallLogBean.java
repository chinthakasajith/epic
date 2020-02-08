/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.callcentersearch.bean;

/**
 *
 * @author badrika
 */
public class CallLogBean {
    
    private String callLogId;
    private String searchKey;
    private String callerType;    
    private String referType;
    private String referNo;
    private String lastUpdatedUser;
    private String lastUpdatedTime;
    private String createdTime;

    public String getCallLogId() {
        return callLogId;
    }

    public void setCallLogId(String callLogId) {
        this.callLogId = callLogId;
    }

    public String getCallerType() {
        return callerType;
    }

    public void setCallerType(String callerType) {
        this.callerType = callerType;
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

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getReferType() {
        return referType;
    }

    public void setReferType(String referType) {
        this.referType = referType;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }  
    
    
}
