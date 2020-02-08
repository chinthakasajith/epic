/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.bean;

//import java.util.Date;

import java.sql.Date;

/**
 *
 * @author mahesh_m
 */
public class InterestProfileTransactionBean {
    private String interestProfileCode;
    private String transactionCode;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private Date createdTime;

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

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getInterestProfileCode() {
        return interestProfileCode;
    }

    public void setInterestProfileCode(String interestProfileCode) {
        this.interestProfileCode = interestProfileCode;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
