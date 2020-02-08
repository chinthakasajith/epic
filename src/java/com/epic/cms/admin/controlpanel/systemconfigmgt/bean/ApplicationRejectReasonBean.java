/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 * this bean class use to store all the bean that relate to application rejected
 * reason
 * @author ayesh
 */
public class ApplicationRejectReasonBean {

    private String reasonCode;
    private String description;
    private String lastUpdateUser;
    private String oldValue;
    private String newValue;

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * get reason description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description 
     * @param description - String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get last update user 
     * @return - String
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * set last update user 
     * @param lastUpdateUser - String
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * get reason code
     * @return -String
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * set reason code 
     * @param reasonCode - String 
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }
}
