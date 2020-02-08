/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

import com.epic.cms.camm.applicationproccessing.capturedata.bean.EstablishmentDetailsBean;

/**
 *
 * @author prageeth_s
 */
public class CardEstablishmentCustomerDetailsBean {
    
    private String customerId;
    private String creditScore;
    private String creditLimit; 
    private EstablishmentDetailsBean establishmentDetailsBean;
    private String lastUpdateUser;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }
    public EstablishmentDetailsBean getEstablishmentDetailsBean() {
        return establishmentDetailsBean;
    }
    
    public  CardEstablishmentCustomerDetailsBean(EstablishmentDetailsBean establishmentDetailsBean) {
        this.establishmentDetailsBean=establishmentDetailsBean;
    }

    public void setEstablishmentDetailsBean(EstablishmentDetailsBean establishmentDetailsBean) {
        this.establishmentDetailsBean = establishmentDetailsBean;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    
    
}
