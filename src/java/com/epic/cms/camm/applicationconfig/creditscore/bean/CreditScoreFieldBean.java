/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.bean;

import com.epic.cms.system.util.exception.ValidateException;
import java.util.Date;

/**
 *this bean class use to store and validate all field that relate to credit score
 * field process
 * @author ayesh
 */
public class CreditScoreFieldBean {

    private String fieldCode;
    private String fieldDes;
    private String formName;
    private String fieldName;
    private String status;
    private Date lastUpDateDate;
    private Date createDate;
    private String lastUpdateUser;
    private boolean isValid;
    private String error = "";
    private String dataType;

    private String statusDes;
    /**
     * get create date
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * set create date
     * @param createDate 
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * get error that relate to validation 
     * @return error-String
     */
    public String getError() {
        return error;
    }

    /**
     * set error that relate to validation
     * @param error 
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * get field code
     * @return fieldCode-String            
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * set field code with it's validation 
     * @param fieldCode - String
     */
    public void setFieldCode(String fieldCode) throws ValidateException {

        this.fieldCode = fieldCode;
    }

    /**
     * get field description
     * @return fieldDes-String
     */
    public String getFieldDes() {
        return fieldDes;
    }

    /**
     * set field description with validation
     * @param fieldDes - String
     */
    public void setFieldDes(String fieldDes) throws ValidateException {

        this.fieldDes = fieldDes;
    }

    /**
     * get field name
     * @return fieldName - String
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * set field name with it's validation
     * @param fieldName 
     */
    public void setFieldName(String fieldName) throws ValidateException {

        this.fieldName = fieldName;
    }

    /**
     * get form name
     * @return formName-String
     */
    public String getFormName() {
        return formName;
    }

    /**
     * set form name with it's validation
     * @param formName -String
     */
    public void setFormName(String formName) throws ValidateException {

        this.formName = formName;
    }

    /**
     * check validation ture to process 
     * @return isValid - boolean
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * set validation result
     * @param isValid 
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * get last update date
     * @return String
     */
    public Date getLastUpDateDate() {
        return lastUpDateDate;
    }

    /**
     * set last update date
     * @param lastUpDateDate 
     */
    public void setLastUpDateDate(Date lastUpDateDate) {
        this.lastUpDateDate = lastUpDateDate;
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
     * @param lastUpdateUser 
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    /**
     * get status
     * @return  String
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status with it's validation
     * @param status 
     */
    public void setStatus(String status) throws ValidateException {

        this.status = status;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) throws ValidateException {

        this.dataType = dataType;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
    
    
}
