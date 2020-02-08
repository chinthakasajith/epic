/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.mtmm.terminalmgt.terminaldata.bean;

/**
 *
 * @author mahesh_m
 */
public class MerchantSearchBean {

    private String merchantname;
    private String merchantId;
    private String merchantLocation;
    private String merchantStatus;
    private String merchantStatusDes;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLocation() {
        return merchantLocation;
    }

    public void setMerchantLocation(String merchantLocation) {
        this.merchantLocation = merchantLocation;
    }

    public String getMerchantStatus() {
        return merchantStatus;
    }

    public void setMerchantStatus(String merchantStatus) {
        this.merchantStatus = merchantStatus;
    }

    public String getMerchantStatusDes() {
        return merchantStatusDes;
    }

    public void setMerchantStatusDes(String merchantStatusDes) {
        this.merchantStatusDes = merchantStatusDes;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }
}
