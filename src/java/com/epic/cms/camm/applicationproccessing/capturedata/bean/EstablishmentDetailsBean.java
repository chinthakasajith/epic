/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.camm.applicationproccessing.capturedata.bean;

/**
 *
 * @author prageeth_s
 */
public class EstablishmentDetailsBean {
    
    private String companyName;
    private String businessRegNumber;   
    private String applicationId;
    private String identificationType;
    private String identificationNumber;
    private String contactPersonFullName;
    private String contactPersLandTelNumber;
    private String contactPersMobileNumber;
    
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String postalcode;
    private String email;
    private String faxNo;
    private String natureOfTheBusiness;
    
    private String annualTurnover;    
    private String shareCapital; 
    private String netProfit;
    
    
    
    private String useCardOnline;
    private String lastUpdateUser;
    
    
    private String cardType;
    
    private String cardProduct;
    private String cardCurrency;
    private String creditLimit;
    private String netAssets;
    
    private String cardTypeDes;
    private String cardProductDes;
    private String currencyDes;
    private String remark;
    
    //track wheter add or edit
    private String mode; 

    public String getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(String netAssets) {
        this.netAssets = netAssets;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardProduct() {
        return cardProduct;
    }

    public void setCardProduct(String cardProduct) {
        this.cardProduct = cardProduct;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }
    public String getContactPersonFullName() {
        return contactPersonFullName;
    }

    public void setContactPersonFullName(String contactPersonFullName) {
        this.contactPersonFullName = contactPersonFullName;
    }
    
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        if(businessRegNumber!=null&& !businessRegNumber.trim().isEmpty()){
           identificationNumber=businessRegNumber;
        }
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }
    
    public String getContactPersLandTelNumber() {
        return contactPersLandTelNumber;
    }

    public void setContactPersLandTelNumber(String contactPersLandTelNumber) {
        this.contactPersLandTelNumber = contactPersLandTelNumber;
    }

    public String getContactPersMobileNumber() {
        return contactPersMobileNumber;
    }

    public void setContactPersMobileNumber(String contactPersLandMobileNumber) {
        this.contactPersMobileNumber = contactPersLandMobileNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getNatureOfTheBusiness() {
        return natureOfTheBusiness;
    }

    public void setNatureOfTheBusiness(String natureOfTheBusiness) {
        this.natureOfTheBusiness = natureOfTheBusiness;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getShareCapital() {
        return shareCapital;
    }

    public void setShareCapital(String shareCapital) {
        this.shareCapital = shareCapital;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getUseCardOnline() {
        return useCardOnline;
    }

    public void setUseCardOnline(String useCardOnline) {
        this.useCardOnline = useCardOnline;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessRegNumber() {
        return businessRegNumber;
    }

    public void setBusinessRegNumber(String businessRegNumber) {
        this.businessRegNumber = businessRegNumber;
    }

    public String getCardTypeDes() {
        return cardTypeDes;
    }

    public void setCardTypeDes(String cardTypeDes) {
        this.cardTypeDes = cardTypeDes;
    }

    public String getCardProductDes() {
        return cardProductDes;
    }

    public void setCardProductDes(String cardProductDes) {
        this.cardProductDes = cardProductDes;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    
    
    
}
