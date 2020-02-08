/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.bean;

/**
 *
 * @author admin
 */
public class ChannelIncomeBean {
    
    private String channelId;
    private String channelDes;
    private String mti;
    private String processingCode;
    private String transactionTypeCode;
    private String onlineTxnCode;
    private String txnDescription;
    private String Status;
    private int statusToOnline;

    public String getChannelDes() {
        return channelDes;
    }

    public void setChannelDes(String channelDes) {
        this.channelDes = channelDes;
    }

    public String getTxnDescription() {
        return txnDescription;
    }

    public void setTxnDescription(String txnDescription) {
        this.txnDescription = txnDescription;
    }

    public int getStatusToOnline() {
        return statusToOnline;
    }

    public void setStatusToOnline(int statusToOnline) {
        this.statusToOnline = statusToOnline;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getOnlineTxnCode() {
        return onlineTxnCode;
    }

    public void setOnlineTxnCode(String onlineTxnCode) {
        this.onlineTxnCode = onlineTxnCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }
    
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }
    
}
