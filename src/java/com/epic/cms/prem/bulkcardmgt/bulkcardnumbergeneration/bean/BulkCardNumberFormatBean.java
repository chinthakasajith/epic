/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.bean;

/**
 *
 * @author nalin
 */
public class BulkCardNumberFormatBean {
    
    private String binNumber;
    private String numberFormatCode;
    private String sequenceNumber;

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getNumberFormatCode() {
        return numberFormatCode;
    }

    public void setNumberFormatCode(String numberFormatCode) {
        this.numberFormatCode = numberFormatCode;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    
}
