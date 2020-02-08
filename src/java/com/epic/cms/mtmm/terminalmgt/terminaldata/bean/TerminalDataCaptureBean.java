/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.bean;

import java.util.Date;

/**
 *
 * @author nisansala
 */
public class TerminalDataCaptureBean {
    private String terminalID;
    private String merchantID;
    private String merchantDes;
    private String name;
    private String mcc;
    private String currency;
    private String currencyDes;
    private String transactions;
    private String transactionList[];
    private String transactionDes;
    private String onlineTxnCode;
    private String serialNo;
    private String manufacturer;
    private String model;
    private String installationDate;
    private String activationDate;
    private String allocationStatus;
    private String terminalStatus;
    private String modelDes;
    private String manufactDes;
    private String alloStatus;
    private String terminalStatusDes;
    private String lastUpdateUser;
    private Date lastUpdateDate;
    private Date createDate;
    private String riskProfileCode;

    public String getRiskProfileCode() {
        return riskProfileCode;
    }

    public void setRiskProfileCode(String riskProfileCode) {
        this.riskProfileCode = riskProfileCode;
    }

    public String getOnlineTxnCode() {
        return onlineTxnCode;
    }

    public void setOnlineTxnCode(String onlineTxnCode) {
        this.onlineTxnCode = onlineTxnCode;
    }

    
    public String[] getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(String[] transactionList) {
        this.transactionList = transactionList;
    }

    public String getTransactionDes() {
        return transactionDes;
    }

    public void setTransactionDes(String transactionDes) {
        this.transactionDes = transactionDes;
    }

    
    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }
        
    
    public String getMerchantDes() {
        return merchantDes;
    }

    public void setMerchantDes(String merchantDes) {
        this.merchantDes = merchantDes;
    }
    
    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    

    

       

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    
    

    public String getAlloStatus() {
        return alloStatus;
    }

    public void setAlloStatus(String alloStatus) {
        this.alloStatus = alloStatus;
    }

    public String getManufactDes() {
        return manufactDes;
    }

    public void setManufactDes(String manufactDes) {
        this.manufactDes = manufactDes;
    }

    public String getModelDes() {
        return modelDes;
    }

    public void setModelDes(String modelDes) {
        this.modelDes = modelDes;
    }

    public String getTerminalStatusDes() {
        return terminalStatusDes;
    }

    public void setTerminalStatusDes(String terminalStatusDes) {
        this.terminalStatusDes = terminalStatusDes;
    }

    

    public String getAllocationStatus() {
        return allocationStatus;
    }

    public void setAllocationStatus(String allocationStatus) {
        this.allocationStatus = allocationStatus;
    }

    

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getTerminalStatus() {
        return terminalStatus;
    }

    public void setTerminalStatus(String terminalStatus) {
        this.terminalStatus = terminalStatus;
    }
    
    
    
}
