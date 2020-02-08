/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 *
 * @author ruwan_e
 */
public class CommonConfigurationBean {

    private String bank;
    private String baseCurrency;
    private String country;
    private double accumulationPointValue;
    private double redemptionPointValue;
    private int batchTimeout;
    private double maxPerPaymentAmount;
    private double maxPerBatchAmount;
    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public double getAccumulationPointValue() {
        return accumulationPointValue;
    }

    public void setAccumulationPointValue(double accumulationPointValue) {
        this.accumulationPointValue = accumulationPointValue;
    }

    public double getMaxPerBatchAmount() {
        return maxPerBatchAmount;
    }

    public void setMaxPerBatchAmount(double maxPerBatchAmount) {
        this.maxPerBatchAmount = maxPerBatchAmount;
    }

    public double getMaxPerPaymentAmount() {
        return maxPerPaymentAmount;
    }

    public void setMaxPerPaymentAmount(double maxPerPaymentAmount) {
        this.maxPerPaymentAmount = maxPerPaymentAmount;
    }

    public double getRedemptionPointValue() {
        return redemptionPointValue;
    }

    public void setRedemptionPointValue(double redemptionPointValue) {
        this.redemptionPointValue = redemptionPointValue;
    }

    public int getBatchTimeout() {
        return batchTimeout;
    }

    public void setBatchTimeout(int batchTimeout) {
        this.batchTimeout = batchTimeout;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String currency) {
        this.baseCurrency = currency;
    }

}
