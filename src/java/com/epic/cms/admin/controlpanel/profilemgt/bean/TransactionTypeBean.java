/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.profilemgt.bean;

import java.sql.Date;

/**
 *
 * @author mahesh_m
 */
public class TransactionTypeBean {
    private String transactionCode;
    private String description;
    private String profileCategoryCode;
    private String financialStatus;
    private String status;
    private String lastUpdatedUser;
    private Date lastUpdatedDate;
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getProfileCategoryCode() {
        return profileCategoryCode;
    }

    public void setProfileCategoryCode(String profileCategoryCode) {
        this.profileCategoryCode = profileCategoryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }
}
