/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardrenew.bean;

/**
 *
 * @author badrika
 */
public class CardSearchBean {
    
    private String cNumber;
    private String cType;
    private String cProduct;
    private String cCategory;
    private String expDate;

    public String getcCategory() {
        return cCategory;
    }

    public void setcCategory(String cCategory) {
        this.cCategory = cCategory;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public String getcProduct() {
        return cProduct;
    }

    public void setcProduct(String cProduct) {
        this.cProduct = cProduct;
    }

    public String getcType() {
        return cType;
    }

    public void setcType(String cType) {
        this.cType = cType;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
    
    
    
}
