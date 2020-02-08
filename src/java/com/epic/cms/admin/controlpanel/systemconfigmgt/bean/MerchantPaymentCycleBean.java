/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

import java.util.Date;

/**
 *
 * @author admin
 */
public class MerchantPaymentCycleBean {

    private String paymentCycleCode;
    private String paymentOption;
    private String paymentDate;
    private String paymentDescription;
    private String holidayAction;
    private String status;
    private String statusDescripton;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private Date createdTime;
    private String oldValue;

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
    private String newValue;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getHolidayAction() {
        return holidayAction;
    }

    public void setHolidayAction(String holidayAction) {
        this.holidayAction = holidayAction;
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

    public String getPaymentCycleCode() {
        return paymentCycleCode;
    }

    public void setPaymentCycleCode(String paymentCycleCode) {
        this.paymentCycleCode = paymentCycleCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescripton() {
        return statusDescripton;
    }

    public void setStatusDescripton(String statusDescripton) {
        this.statusDescripton = statusDescripton;
    }
}
