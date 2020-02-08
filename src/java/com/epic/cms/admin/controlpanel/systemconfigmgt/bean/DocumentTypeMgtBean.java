/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

/**
 * this bean class use to store all the bean that relate to document type mgt
 * @author ayesh
 */
public class DocumentTypeMgtBean {

    private String docCode;
    private String docName;
    private String verifyCat;
    private String verifyCatDes;
    private String postFix;
    private String status;
    private String lastupdateUser;
    private String lastUpdateDate;
    private String createDate;
    private String cardCategory;
    private Boolean isMandatory;
    private String cardCategoryDes;
    private String isMandatoryDes;

    public String getCardCategoryDes() {
        return cardCategoryDes;
    }

    public void setCardCategoryDes(String cardCategoryDes) {
        this.cardCategoryDes = cardCategoryDes;
    }

    public String getIsMandatoryDes() {
        if(isMandatory!=null && isMandatory){
            isMandatoryDes="YES";
        }else{
            isMandatoryDes="NO";
        }
        return isMandatoryDes;
    }

    public void setIsMandatoryDes(String isMandatoryDes) {
        this.isMandatoryDes = isMandatoryDes;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public void setCardCategory(String cardCategory) {
        this.cardCategory = cardCategory;
    }

    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
       this.isMandatory = isMandatory;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastupdateUser() {
        return lastupdateUser;
    }

    public void setLastupdateUser(String lastupdateUser) {
        this.lastupdateUser = lastupdateUser;
    }

    public String getPostFix() {
        return postFix;
    }

    public void setPostFix(String postFix) {
        this.postFix = postFix;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyCat() {
        return verifyCat;
    }

    public void setVerifyCat(String verifyCat) {
        this.verifyCat = verifyCat;
    }

    public String getVerifyCatDes() {
        return verifyCatDes;
    }

    public void setVerifyCatDes(String verifyCatDes) {
        this.verifyCatDes = verifyCatDes;
    }
    
}
