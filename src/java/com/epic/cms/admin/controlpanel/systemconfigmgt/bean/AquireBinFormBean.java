/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 *
 * @author badrika
 */
public class AquireBinFormBean {

    private String channelName;
    private String channelID;
    private String cardType;
    private String cardTypeDes;
    private String entryMode;
    private String entryModeDes;
    private String status;
    private String statusDes;
    private String statusCategoryCode;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeDes() {
        return cardTypeDes;
    }

    public void setCardTypeDes(String cardTypeDes) {
        this.cardTypeDes = cardTypeDes;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(String entryMode) {
        this.entryMode = entryMode;
    }

    public String getEntryModeDes() {
        return entryModeDes;
    }

    public void setEntryModeDes(String entryModeDes) {
        this.entryModeDes = entryModeDes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCategoryCode() {
        return statusCategoryCode;
    }

    public void setStatusCategoryCode(String statusCategoryCode) {
        this.statusCategoryCode = statusCategoryCode;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
}
