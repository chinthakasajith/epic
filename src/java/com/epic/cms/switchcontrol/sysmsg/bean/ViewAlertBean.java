/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.bean;

/**
 *
 * @author nisansala
 */
public class ViewAlertBean {

    private String txnID;
    private String fromDate;
    private String toDate;
    private String readStatus;
    private String alertInfo;
    private String riskCategory;
    private String riskDes;

    public String getRiskDes() {
        return riskDes;
    }

    public void setRiskDes(String riskDes) {
        this.riskDes = riskDes;
    }
    
    

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }
    
    

    public String getAlertInfo() {
        return alertInfo;
    }

    public void setAlertInfo(String alertInfo) {
        this.alertInfo = alertInfo;
    }

   

    
    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTxnID() {
        return txnID;
    }

    public void setTxnID(String txnID) {
        this.txnID = txnID;
    }
}
