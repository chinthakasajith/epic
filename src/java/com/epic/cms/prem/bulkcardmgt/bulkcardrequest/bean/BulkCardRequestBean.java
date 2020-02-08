/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean;

/**
 *
 * @author nisansala
 */
public class BulkCardRequestBean {

    private String batchID;//batch Id
    private String cdDomain; //Card Domain
    private String cdDomainDes; //Card Domain Description
    private String cdType;// Card Type
    private String cdTypeDes; //Card Type Description
    private String cdProduct;// Card Product
    private String cdProductDes; //Card Product Description
    private String branchCode; //Branch Code
    private String branchName;// Branch Name
    private String priorityLvl; //Priority Level
    private String priorityLvlDes; //Priority Level Description
    private String productMode; //Production Mode
    private String productModeDes; //Production Mode Description
    private String reqNumOfCds; //Requested Number of Cards 
    private String apprvNumOfCds; //Approved Number of Cards
    private String reqUser; // Requested User
    private String apprvUser;//Approved User
    private String reqBranch; //Requested Users Branch Code
    private String reqBranchDes;//Requested Users Branch Name
    private String apprvBranch; //Approved Users Branch Code
    private String apprvBranchDes; //Approved Users Branch Name
    private String status; //
    private String statusDes; //Status Description
    private String currency;
    private String currencyDes; //Currecncy Description
    private String cdBin; // Card Bin
    private String cdBinDes; // Card Bin Description
    private String cdProductionMode;
    private String templateCode;
    private String templateDes;
    private String remark;
    private String fromDate;
    private String toDate;
    private String creditLimit;
    private String receivedUser;
    private String lastUpdatedUser; //Last updated User
    private String lastUpdatedTime;// Last UPdated Time
    private String ceatedTime; //Created Time

    public String getTemplateDes() {
        return templateDes;
    }

    public void setTemplateDes(String templateDes) {
        this.templateDes = templateDes;
    }

    public String getReceivedUser() {
        return receivedUser;
    }

    public void setReceivedUser(String receivedUser) {
        this.receivedUser = receivedUser;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getCdBin() {
        return cdBin;
    }

    public void setCdBin(String cdBin) {
        this.cdBin = cdBin;
    }

    public String getCdBinDes() {
        return cdBinDes;
    }

    public void setCdBinDes(String cdBinDes) {
        this.cdBinDes = cdBinDes;
    }

    public String getCdProductionMode() {
        return cdProductionMode;
    }

    public void setCdProductionMode(String cdProductionMode) {
        this.cdProductionMode = cdProductionMode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getApprvBranchDes() {
        return apprvBranchDes;
    }

    public void setApprvBranchDes(String apprvBranchDes) {
        this.apprvBranchDes = apprvBranchDes;
    }

    public String getReqBranchDes() {
        return reqBranchDes;
    }

    public void setReqBranchDes(String reqBranchDes) {
        this.reqBranchDes = reqBranchDes;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getApprvBranch() {
        return apprvBranch;
    }

    public void setApprvBranch(String apprvBranch) {
        this.apprvBranch = apprvBranch;
    }

    public String getApprvNumOfCds() {
        return apprvNumOfCds;
    }

    public void setApprvNumOfCds(String apprvNumOfCds) {
        this.apprvNumOfCds = apprvNumOfCds;
    }

    public String getApprvUser() {
        return apprvUser;
    }

    public void setApprvUser(String apprvUser) {
        this.apprvUser = apprvUser;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCdDomain() {
        return cdDomain;
    }

    public void setCdDomain(String cdDomain) {
        this.cdDomain = cdDomain;
    }

    public String getCdDomainDes() {
        return cdDomainDes;
    }

    public void setCdDomainDes(String cdDomainDes) {
        this.cdDomainDes = cdDomainDes;
    }

    public String getCdProduct() {
        return cdProduct;
    }

    public void setCdProduct(String cdProduct) {
        this.cdProduct = cdProduct;
    }

    public String getCdProductDes() {
        return cdProductDes;
    }

    public void setCdProductDes(String cdProductDes) {
        this.cdProductDes = cdProductDes;
    }

    public String getCdType() {
        return cdType;
    }

    public void setCdType(String cdType) {
        this.cdType = cdType;
    }

    public String getCdTypeDes() {
        return cdTypeDes;
    }

    public void setCdTypeDes(String cdTypeDes) {
        this.cdTypeDes = cdTypeDes;
    }

    public String getCeatedTime() {
        return ceatedTime;
    }

    public void setCeatedTime(String ceatedTime) {
        this.ceatedTime = ceatedTime;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public String getPriorityLvl() {
        return priorityLvl;
    }

    public void setPriorityLvl(String priorityLvl) {
        this.priorityLvl = priorityLvl;
    }

    public String getPriorityLvlDes() {
        return priorityLvlDes;
    }

    public void setPriorityLvlDes(String priorityLvlDes) {
        this.priorityLvlDes = priorityLvlDes;
    }

    public String getProductMode() {
        return productMode;
    }

    public void setProductMode(String productMode) {
        this.productMode = productMode;
    }

    public String getProductModeDes() {
        return productModeDes;
    }

    public void setProductModeDes(String productModeDes) {
        this.productModeDes = productModeDes;
    }

    public String getReqBranch() {
        return reqBranch;
    }

    public void setReqBranch(String reqBranch) {
        this.reqBranch = reqBranch;
    }

    public String getReqNumOfCds() {
        return reqNumOfCds;
    }

    public void setReqNumOfCds(String reqNumOfCds) {
        this.reqNumOfCds = reqNumOfCds;
    }

    public String getReqUser() {
        return reqUser;
    }

    public void setReqUser(String reqUser) {
        this.reqUser = reqUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
}
