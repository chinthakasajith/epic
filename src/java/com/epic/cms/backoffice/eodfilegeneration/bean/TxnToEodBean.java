/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodfilegeneration.bean;

import java.sql.Timestamp;

/**
 *
 * @author chanuka
 */
public class TxnToEodBean {
    
    private String txnId = null;
    private String channelType = null;
    private String mti = null;
    private String responseMti = null;
    private String processingCode = null;
    private String offMti = null;
    private String offResponseMti = null;
    private String offProcessingCode = null;
    private String nii = null;
    private String txnTypeCode = null;
    private String bin = null;
    private String requestFrom = null;
    private String listenerType = null;
    private String onOffStatus = null;
    private String serviceCode = null;
    private String cardNo = null;
    private String expiryDate = null;
    private String txnCurrency = null;
    private String billAmount = null;
    private String settlementCurrency = null;
    private String settlementAmount = null;
    private String settlementTxnCount = null;
    private Timestamp settlementDate = null;
    private String batchNo = null;
    private String tId = null;
    private String mId = null;
    private String mcc = null;
    private String countryCode = null;
    private String traceNo = null;
    private String invoiceNo = null;
    private String rrn = null;
    private String authCode = null;
    private String responseCode = null;
    private String status = null;
    private String postConditionCode = null;
    private String postEntryMode = null;
    private String aiic = null;
    private String fiic = null;
    private String caic = null;
    private String tag5f2a = null;
    private String tag9a = null;
    private String tag9c = null;
    private String tag9f34 = null;
    private String tag9f02 = null;
    private String tag9f03 = null;
    private String tag9f1A = null;
    private String tag9f1E = null;
    private String tag9f27 = null;
    private String tag9f33 = null;
    private String tag9f35 = null;
    private String tag9f41 = null;
    private String txnTime = null;
    private String txnDate = null;
    private String localTime = null;
    private String localDate = null;

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    
    public String getCaic() {
        return caic;
    }

    public void setCaic(String caic) {
        this.caic = caic;
    }

    
    public String getAiic() {
        return aiic;
    }

    public void setAiic(String aiic) {
        this.aiic = aiic;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFiic() {
        return fiic;
    }

    public void setFiic(String fiic) {
        this.fiic = fiic;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getListenerType() {
        return listenerType;
    }

    public void setListenerType(String listenerType) {
        this.listenerType = listenerType;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getNii() {
        return nii;
    }

    public void setNii(String nii) {
        this.nii = nii;
    }

    public String getOffMti() {
        return offMti;
    }

    public void setOffMti(String offMti) {
        this.offMti = offMti;
    }

    public String getOffProcessingCode() {
        return offProcessingCode;
    }

    public void setOffProcessingCode(String offProcessingCode) {
        this.offProcessingCode = offProcessingCode;
    }

    public String getOffResponseMti() {
        return offResponseMti;
    }

    public void setOffResponseMti(String offResponseMti) {
        this.offResponseMti = offResponseMti;
    }

    public String getOnOffStatus() {
        return onOffStatus;
    }

    public void setOnOffStatus(String onOffStatus) {
        this.onOffStatus = onOffStatus;
    }

    public String getPostConditionCode() {
        return postConditionCode;
    }

    public void setPostConditionCode(String postConditionCode) {
        this.postConditionCode = postConditionCode;
    }

    public String getPostEntryMode() {
        return postEntryMode;
    }

    public void setPostEntryMode(String postEntryMode) {
        this.postEntryMode = postEntryMode;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMti() {
        return responseMti;
    }

    public void setResponseMti(String responseMti) {
        this.responseMti = responseMti;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }

    public Timestamp getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Timestamp settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSettlementTxnCount() {
        return settlementTxnCount;
    }

    public void setSettlementTxnCount(String settlementTxnCount) {
        this.settlementTxnCount = settlementTxnCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getTag5f2a() {
        return tag5f2a;
    }

    public void setTag5f2a(String tag5f2a) {
        this.tag5f2a = tag5f2a;
    }

    public String getTag9a() {
        return tag9a;
    }

    public void setTag9a(String tag9a) {
        this.tag9a = tag9a;
    }

    public String getTag9c() {
        return tag9c;
    }

    public void setTag9c(String tag9c) {
        this.tag9c = tag9c;
    }

    public String getTag9f02() {
        return tag9f02;
    }

    public void setTag9f02(String tag9f02) {
        this.tag9f02 = tag9f02;
    }

    public String getTag9f03() {
        return tag9f03;
    }

    public void setTag9f03(String tag9f03) {
        this.tag9f03 = tag9f03;
    }

    public String getTag9f1A() {
        return tag9f1A;
    }

    public void setTag9f1A(String tag9f1A) {
        this.tag9f1A = tag9f1A;
    }

    public String getTag9f1E() {
        return tag9f1E;
    }

    public void setTag9f1E(String tag9f1E) {
        this.tag9f1E = tag9f1E;
    }

    public String getTag9f27() {
        return tag9f27;
    }

    public void setTag9f27(String tag9f27) {
        this.tag9f27 = tag9f27;
    }

    public String getTag9f33() {
        return tag9f33;
    }

    public void setTag9f33(String tag9f33) {
        this.tag9f33 = tag9f33;
    }

    public String getTag9f34() {
        return tag9f34;
    }

    public void setTag9f34(String tag9f34) {
        this.tag9f34 = tag9f34;
    }

    public String getTag9f35() {
        return tag9f35;
    }

    public void setTag9f35(String tag9f35) {
        this.tag9f35 = tag9f35;
    }

    public String getTag9f41() {
        return tag9f41;
    }

    public void setTag9f41(String tag9f41) {
        this.tag9f41 = tag9f41;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getTxnCurrency() {
        return txnCurrency;
    }

    public void setTxnCurrency(String txnCurrency) {
        this.txnCurrency = txnCurrency;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnTypeCode() {
        return txnTypeCode;
    }

    public void setTxnTypeCode(String txnTypeCode) {
        this.txnTypeCode = txnTypeCode;
    }
    
    
    
    
    
}
