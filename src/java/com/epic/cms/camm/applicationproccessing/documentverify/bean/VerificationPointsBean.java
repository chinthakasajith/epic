/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.bean;

/**
 *
 * @author chanuka
 */
public class VerificationPointsBean {

    private String fieldName;
    private String description;
    private String documentType;
    private String documentExist;
    private String status;
    private String fileName;
    private String verificationCategory;
    private String cardCategoryCode;
    private Boolean isMandatory;
    private String isStaff;



    public Boolean getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(Boolean isMandatory) {
       this.isMandatory = isMandatory;
    }

    public String getCardCategoryCode() {
        return cardCategoryCode;
    }

    public void setCardCategoryCode(String cardCategoryCode) {
        this.cardCategoryCode = cardCategoryCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getVerificationCategory() {
        return verificationCategory;
    }

    public void setVerificationCategory(String verificationCategory) {
        this.verificationCategory = verificationCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentExist() {
        return documentExist;
    }

    public void setDocumentExist(String documentExist) {
        this.documentExist = documentExist;
    }
    
    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }
}
