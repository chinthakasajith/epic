/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.callcenter.customer.bean;

/**
 *
 * @author prageeth_s
 */
public class CustomerContactDetailsChngeHolderBean {
    
     private Boolean isPermanentAddressChanged;     
     private Boolean isResidenceAddressChanged;
     private Boolean isBillingAddressChanged; 
     private Boolean isCompanyAddressChanged; 
     private Boolean isResPhoneNoChanged; 
     private Boolean isMobilePhoneNoChanged; 
     private Boolean isOfficePhoneNoChanged; 
     private Boolean isEstMobContactNoChanged; 
     private Boolean isEstLandContactNoChanged; 
     private Boolean isFaxNoChanged; 

    public Boolean isPermanentAddressChanged() {
        if(this.isPermanentAddressChanged==null){
            return false;
        }
        return isPermanentAddressChanged;
    }

    public void setIsPermanentAddressChanged(Boolean isPermanentAddressChanged) {
        this.isPermanentAddressChanged = isPermanentAddressChanged;
    }

    public Boolean isResidenceAddressChanged() {
        if(this.isResidenceAddressChanged==null){
            return false;
        }
        return isResidenceAddressChanged;
    }

    public void setIsResidenceAddressChanged(Boolean isResidenceAddressChanged) {
        this.isResidenceAddressChanged = isResidenceAddressChanged;
    }

    public Boolean isBillingAddressChanged() {
        if(this.isBillingAddressChanged==null){
            return false;
        }
        return isBillingAddressChanged;
    }

    public void setIsBillingAddressChanged(Boolean isBillingAddressChanged) {
        this.isBillingAddressChanged = isBillingAddressChanged;
    }

    public Boolean isCompanyAddressChanged() {
        if(this.isCompanyAddressChanged==null){
            return false;
        }
        return isCompanyAddressChanged;
    }

    public void setIsCompanyAddressChanged(Boolean isCompanyAddressChanged) {
        this.isCompanyAddressChanged = isCompanyAddressChanged;
    }

    public Boolean isResPhoneNoChanged() {
        if(this.isResPhoneNoChanged==null){
            return false;
        }
        return isResPhoneNoChanged;
    }

    public void setIsResPhoneNoChanged(Boolean isResPhoneNoChanged) {
        this.isResPhoneNoChanged = isResPhoneNoChanged;
    }

    public Boolean isMobilePhoneNoChanged() {
        if(this.isMobilePhoneNoChanged==null){
            return false;
        }
        return isMobilePhoneNoChanged;
    }

    public void setIsMobilePhoneNoChanged(Boolean isMobilePhoneNoChanged) {
        this.isMobilePhoneNoChanged = isMobilePhoneNoChanged;
    }

    public Boolean isOfficePhoneNoChanged() {
        if(this.isOfficePhoneNoChanged==null){
            return false;
        }
        return isOfficePhoneNoChanged;
    }

    public void setIsOfficePhoneNoChanged(Boolean isOfficePhoneNoChanged) {
        this.isOfficePhoneNoChanged = isOfficePhoneNoChanged;
    }

    public Boolean isEstMobContactNoChanged() {
        if(this.isEstMobContactNoChanged==null){
            return false;
        }
        return isEstMobContactNoChanged;
    }

    public void setIsEstMobContactNoChanged(Boolean isEstMobContactNoChanged) {
        this.isEstMobContactNoChanged = isEstMobContactNoChanged;
    }

    public Boolean isEstLandContactNoChanged() {
        if(this.isEstLandContactNoChanged==null){
            return false;
        }
        return isEstLandContactNoChanged;
    }

    public void setIsEstLandContactNoChanged(Boolean isEstLandContactNoChanged) {
        this.isEstLandContactNoChanged = isEstLandContactNoChanged;
    }

    public Boolean isFaxNoChanged() {
        if(this.isFaxNoChanged==null){
            return false;
        }
        return isFaxNoChanged;
    }

    public void setIsFaxNoChanged(Boolean isFaxNoChanged) {
        this.isFaxNoChanged = isFaxNoChanged;
    }
     
     

}
