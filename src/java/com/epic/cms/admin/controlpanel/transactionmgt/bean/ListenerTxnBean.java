/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.bean;

/**
 *
 * @author nisansala
 */
public class ListenerTxnBean {
    private String litenerId;
    private String listenerDes;
    private String txnType;
    private String txnDes;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getListenerDes() {
        return listenerDes;
    }

    public void setListenerDes(String listenerDes) {
        this.listenerDes = listenerDes;
    }

    public String getLitenerId() {
        return litenerId;
    }

    public void setLitenerId(String litenerId) {
        this.litenerId = litenerId;
    }

    public String getTxnDes() {
        return txnDes;
    }

    public void setTxnDes(String txnDes) {
        this.txnDes = txnDes;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    
    
    
    
}
