/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.bean;

import java.sql.Date;


/**
 *
 * @author mahesh_m
 */
public class PostalCodeBean {
    private String countryCode;
    private String city;
    private String postalCode;
    private String lastUpdateduser;
    private Date lastUpdatedTime;
    private Date createdTime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdateduser() {
        return lastUpdateduser;
    }

    public void setLastUpdateduser(String lastUpdateduser) {
        this.lastUpdateduser = lastUpdateduser;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
}
