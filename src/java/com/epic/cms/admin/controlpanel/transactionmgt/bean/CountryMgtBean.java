/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.bean;

import java.util.Date;

/**
 *this class use fro store all bean information that relate to country management 
 * @author ayesh
 */
public class CountryMgtBean {

    private String countryCode;
    private String alphaFirst;
    private String alphaSecond;
    private String description;
    private String frdVal;
    private String region;
    private String lastUpdateUser;
    private Date lastUpdateDate;
    private Date createDate;

    /**
     * get first alpha code to county
     * @return String
     */
    public String getAlphaFirst() {
        return alphaFirst;
    }

    /**
     * set first alpha code to county
     * @param alphaFirst  String
     */
    public void setAlphaFirst(String alphaFirst) {
        this.alphaFirst = alphaFirst;
    }

    /**
     * get second alpha code
     * @return String
     */
    public String getAlphaSecond() {
        return alphaSecond;
    }

    /**
     * set alpha second code for country
     * @param alphaSecond String
     */
    public void setAlphaSecond(String alphaSecond) {
        this.alphaSecond = alphaSecond;
    }

    /**
     * get country code 
     * @return String
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * set country code 
     * @param countryCode  String
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * get description
     * @return  String
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get Fraud value
     * @return int
     */
    public String getFrdVal() {
        return frdVal;
    }

    /**
     * set Fraud value
     * @param frdVal int
     */
    public void setFrdVal(String frdVal) {
        this.frdVal = frdVal;
    }

    /**
     * get region
     * @return String
     */
    public String getRegion() {
        return region;
    }

    /**
     * set region
     * @param region String
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * get last update user
     * @return String
     */
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    /**
     * set last update user
     * @param lastUpdateUser String
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    
}
