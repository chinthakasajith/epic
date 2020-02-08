/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

/**
 *
 * @author janaka_h
 */
public class CardTempBean {
    
    private String templateCode;
    private String currencyNumCode;
    private String currencyCharCode;
    private String productCode;
    private String riskProfileCode;
    private String expiryPeriod;
    private String cashAdvancedRate;
    private String serviceCode;
    private String reissueTresholPeriod;
    private String accountTempCode;
    private String txnProfCode;

    public String getTxnProfCode() {
        return txnProfCode;
    }

    public void setTxnProfCode(String txnProfCode) {
        this.txnProfCode = txnProfCode;
    }

    public String getAccountTempCode() {
        return accountTempCode;
    }

    public void setAccountTempCode(String accountTempCode) {
        this.accountTempCode = accountTempCode;
    }
    
    public String getCashAdvancedRate() {
        return cashAdvancedRate;
    }

    public void setCashAdvancedRate(String cashAdvancedRate) {
        this.cashAdvancedRate = cashAdvancedRate;
    }

    public String getCurrencyCharCode() {
        return currencyCharCode;
    }

    public void setCurrencyCharCode(String currencyCharCode) {
        this.currencyCharCode = currencyCharCode;
    }

    public String getCurrencyNumCode() {
        return currencyNumCode;
    }

    public void setCurrencyNumCode(String currencyNumCode) {
        this.currencyNumCode = currencyNumCode;
    }

    public String getExpiryPeriod() {
        return expiryPeriod;
    }

    public void setExpiryPeriod(String expiryPeriod) {
        this.expiryPeriod = expiryPeriod;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getRiskProfileCode() {
        return riskProfileCode;
    }

    public void setRiskProfileCode(String riskProfileCode) {
        this.riskProfileCode = riskProfileCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getReissueTresholPeriod() {
        return reissueTresholPeriod;
    }

    public void setReissueTresholPeriod(String reissueTresholPeriod) {
        this.reissueTresholPeriod = reissueTresholPeriod;
    }
    
    
    
}
