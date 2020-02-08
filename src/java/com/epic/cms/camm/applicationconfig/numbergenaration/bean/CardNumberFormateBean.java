/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

/**
 *
 * @author janaka_h
 */
public class CardNumberFormateBean {
    private String formatCode;
    private String description;
    private String cardType;
    private String sequenceNumber;
    private String cardNumberLenght;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public String getCardNumberLenght() {
        return cardNumberLenght;
    }

    public void setCardNumberLenght(String cardNumberLenght) {
        this.cardNumberLenght = cardNumberLenght;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
    
}
