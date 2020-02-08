/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.bean;

/**
 *
 * @author janaka_h
 */
public class NumberFormateFieldTableBean {

    private String formatCode;
    private String fieldTypeCode;
    private int fromDigit;
    private int toDigit;

    public String getFieldTypeCode() {
        return fieldTypeCode;
    }

    public void setFieldTypeCode(String fieldTypeCode) {
        this.fieldTypeCode = fieldTypeCode;
    }

    public String getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(String formatCode) {
        this.formatCode = formatCode;
    }

    public int getFromDigit() {
        return fromDigit;
    }

    public void setFromDigit(int fromDigit) {
        this.fromDigit = fromDigit;
    }

    public int getToDigit() {
        return toDigit;
    }

    public void setToDigit(int toDigit) {
        this.toDigit = toDigit;
    }

    
    
    
}
