/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.bean;

import java.util.Date;

/**
 *
 * @author ruwan_e
 */
public class TransactionAdjustment {

    private long id;
    //Card or Merchant
    private TransactionAdjustmentParty adjustmentParty;
    //Card Number or Merchant ID
    private String uniqueId;
    //Expiery Date or Terminal ID
    private String verificationValue;
    private String amount;
    private String formatedamount;
    private Double amountD;
    private String currencyCode;
    private String crOrdr;
    private String traceNo;
    private String status;
    private String requestedUser;
    private String approvedUser;
    private String updatedUser;
    private String remarks;
    private String adjustmentType;
    private String transactionType;
    
    private String currencyDes;
    private Double exponent;
    private String statusDes;
    private String adjustmentTypeDes;
    
    private String loggeduser;
    private Date fromdate;
    private Date todate;    
    

    public String getLoggeduser() {
        return loggeduser;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public void setLoggeduser(String loggeduser) {
        this.loggeduser = loggeduser;
    }
    
    public String getAdjustmentTypeDes() {
        return adjustmentTypeDes;
    }

    public void setAdjustmentTypeDes(String adjustmentTypeDes) {
        this.adjustmentTypeDes = adjustmentTypeDes;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    public Double getExponent() {
        return exponent;
    }

    public void setExponent(Double exponent) {
        this.exponent = exponent;
    } 

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    } 

    public TransactionAdjustmentParty getAdjustmentParty() {
        return adjustmentParty;
    }

    public void setAdjustmentParty(TransactionAdjustmentParty adjustmentType) {
        this.adjustmentParty = adjustmentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFormatedamount() {
        return formatedamount;
    }

    public void setFormatedamount(String formatedamount) {
        this.formatedamount = formatedamount;
    }
    
    public Double getAmountD() {
        return amountD;
    }

    public void setAmountD(Double amountD) {
        this.amountD = amountD;
    }

    public String getApprovedUser() {
        return approvedUser;
    }

    public void setApprovedUser(String approvedUser) {
        this.approvedUser = approvedUser;
    }

    public String getCrOrdr() {
        return crOrdr;
    }

    public void setCrOrdr(String crOrdr) {
        this.crOrdr = crOrdr;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(String requestedUser) {
        this.requestedUser = requestedUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getVerificationValue() {
        return verificationValue;
    }

    public void setVerificationValue(String verificationValue) {
        this.verificationValue = verificationValue;
    }
    
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
