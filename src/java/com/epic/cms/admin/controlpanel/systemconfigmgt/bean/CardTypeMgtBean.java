/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 *
 * @author ayesh
 */
public class CardTypeMgtBean {

    private String cardTypeCode;
    private String description;
    private String status;
    private String lastUpdateUser;
    private String statusDes;

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    /**
     *get card type code
     * @return String
     */
    public String getCardTypeCode() {
        return cardTypeCode;
    }

    /**
     * set card type 
     * @param cardTypeCode-String 
     */
    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    /**
     * get description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * @param description -String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * get status
     * @return -String 
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status 
     * @param status -String
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
