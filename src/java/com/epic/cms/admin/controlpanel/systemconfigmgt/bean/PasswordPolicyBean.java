/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.bean;

import java.util.Date;

/**
 * this bean class use to store all  details about password policy
 * @author ayesh
 */
public class PasswordPolicyBean {

    private int policyID;
    private String minLen;
    private String maxLen;
    private String minSpclCharacterLen;
    private String minUpperCase;
    private String minNumCharacter;
    private String minLowerCaseCharacter;
    private String lastUpdateuser;
    private Date lastUpdateDate;
    
    private String allowedReptCharacters;
    private String passwordExpiryPeriod;
    private String passwordExpNotifyPeriod;
    private String noOfHistoryPasswords;
    private String accountExpiryPeriod;
    private String noOfInvalidLoginAtmps; 
    
    private int firstPassExpiryPeriod;

    public String getAllowedReptCharacters() {
        return allowedReptCharacters;
    }

    public void setAllowedReptCharacters(String allowedReptCharacters) {
        this.allowedReptCharacters = allowedReptCharacters;
    }

    public String getPasswordExpiryPeriod() {
        return passwordExpiryPeriod;
    }

    public void setPasswordExpiryPeriod(String passwordExpiryPeriod) {
        this.passwordExpiryPeriod = passwordExpiryPeriod;
    }

    public String getPasswordExpNotifyPeriod() {
        return passwordExpNotifyPeriod;
    }

    public void setPasswordExpNotifyPeriod(String passwordExpNotifyPeriod) {
        this.passwordExpNotifyPeriod = passwordExpNotifyPeriod;
    }

    public String getNoOfHistoryPasswords() {
        return noOfHistoryPasswords;
    }

    public void setNoOfHistoryPasswords(String noOfHistoryPasswords) {
        this.noOfHistoryPasswords = noOfHistoryPasswords;
    }

    public String getAccountExpiryPeriod() {
        return accountExpiryPeriod;
    }

    public void setAccountExpiryPeriod(String accountExpiryPeriod) {
        this.accountExpiryPeriod = accountExpiryPeriod;
    }

    public String getNoOfInvalidLoginAtmps() {
        return noOfInvalidLoginAtmps;
    }

    public void setNoOfInvalidLoginAtmps(String noOfInvalidLoginAtmps) {
        this.noOfInvalidLoginAtmps = noOfInvalidLoginAtmps;
    }
    /**
     * get last update date
     * @return lastUpdateDate-Date
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * set last update date
     * @param lastUpdateDate 
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * get last update user
     * @return 
     */
    public String getLastUpdateuser() {
        return lastUpdateuser;
    }

    /**
     * set last update user
     * @param lastUpdateuser 
     */
    public void setLastUpdateuser(String lastUpdateuser) {
        this.lastUpdateuser = lastUpdateuser;
    }

    /**
     * get maximum password length 
     * @return 
     */
    public String getMaxLen() {
        return maxLen;
    }

    /**
     * set maximum length for password 
     * @param maxLen 
     */
    public void setMaxLen(String maxLen) {
        this.maxLen = maxLen;
    }

    /**
     * get minimum upper case character size
     * @return 
     */
    public String getMinUpperCase() {
        return minUpperCase;
    }

    /**
     * set minimum upper case latter size
     * @param minUpperCase 
     */
    public void setMinUpperCase(String minUpperCase) {
        this.minUpperCase = minUpperCase;
    }

    /**
     * get minimum password length
     * @return 
     */
    public String getMinLen() {
        return minLen;
    }

    /**
     * set minimum length for password
     * @param minLen 
     */
    public void setMinLen(String minLen) {
        this.minLen = minLen;
    }

    /**
     * get minimum lower case latter 
     * @return 
     */
    public String getMinLowerCaseCharacter() {
        return minLowerCaseCharacter;
    }

    /**
     * set minimum lower case latter 
     * @param minLowerCaseCharacter 
     */
    public void setMinLowerCaseCharacter(String minLowerCaseCharacter) {
        this.minLowerCaseCharacter = minLowerCaseCharacter;
    }

    /**
     * get minimum numeric characters 
     * @return 
     */
    public String getMinNumCharacter() {
        return minNumCharacter;
    }

    /**
     *  set minimum numeric characters 
     * @param minNumCharacter 
     */
    public void setMinNumCharacter(String minNumCharacter) {
        this.minNumCharacter = minNumCharacter;
    }

    /**
     * get minimum especial character for password
     * @return 
     */
    public String getMinSpclCharacterLen() {
        return minSpclCharacterLen;
    }

    /**
     * set minimum especial character for password
     * @param minSpclCharacterLen 
     */
    public void setMinSpclCharacterLen(String minSpclCharacterLen) {
        this.minSpclCharacterLen = minSpclCharacterLen;
    }

    /**
     * get password policy id
     * @return 
     */
    public int getPolicyID() {
        return policyID;
    }

    /**
     * set password policy id
     * @param policyID 
     */
    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public int getFirstPassExpiryPeriod() {
        return firstPassExpiryPeriod;
    }

    public void setFirstPassExpiryPeriod(int firstPassExpiryPeriod) {
        this.firstPassExpiryPeriod = firstPassExpiryPeriod;
    }
    
    
}
