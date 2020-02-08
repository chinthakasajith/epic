/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.chanelconfig.bean;

/**
 *
 * @author nalin
 */
public class ChannelTypeBean {
    
    private String status;
    private String description;
    private String code;
    
    private String txnType;
    private String txnTypeDes;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnTypeDes() {
        return txnTypeDes;
    }

    public void setTxnTypeDes(String txnTypeDes) {
        this.txnTypeDes = txnTypeDes;
    }
    
    
}
