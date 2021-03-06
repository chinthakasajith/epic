/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.backoffice.installementpayment.bean;

import java.util.Date;

/**
 *
 * @author Badrika
 */
public class EasyPaymentConfirmBean {
    
    private Date fromdate;
    private Date todate;
    private String loggeduser;
    private String requestid;
    private String txnid;
    private String cardno;
    private String paymentplanCode;
    private String paymentplan;
    private String plandurarion;
    private String txnamount;
    private String txnamountonline;
    private String installment;
    private String statusCode;
    private String txnstatus;
    private String remark;
    private String requesteduser;
    private String rejectremark;
    private String lastUpdatedUser;
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getPaymentplanCode() {
        return paymentplanCode;
    }

    public void setPaymentplanCode(String paymentplanCode) {
        this.paymentplanCode = paymentplanCode;
    }

    public String getPlandurarion() {
        return plandurarion;
    }

    public void setPlandurarion(String plandurarion) {
        this.plandurarion = plandurarion;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getLoggeduser() {
        return loggeduser;
    }

    public void setLoggeduser(String loggeduser) {
        this.loggeduser = loggeduser;
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

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getPaymentplan() {
        return paymentplan;
    }

    public void setPaymentplan(String paymentplan) {
        this.paymentplan = paymentplan;
    }

    public String getTxnamount() {
        return txnamount;
    }

    public void setTxnamount(String txnamount) {
        this.txnamount = txnamount;
    }

    public String getTxnamountonline() {
        return txnamountonline;
    }

    public void setTxnamountonline(String txnamountonline) {
        this.txnamountonline = txnamountonline;
    }    

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getTxnstatus() {
        return txnstatus;
    }

    public void setTxnstatus(String txnstatus) {
        this.txnstatus = txnstatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRequesteduser() {
        return requesteduser;
    }

    public void setRequesteduser(String requesteduser) {
        this.requesteduser = requesteduser;
    }

    public String getRejectremark() {
        return rejectremark;
    }

    public void setRejectremark(String rejectremark) {
        this.rejectremark = rejectremark;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    
    
    
}
