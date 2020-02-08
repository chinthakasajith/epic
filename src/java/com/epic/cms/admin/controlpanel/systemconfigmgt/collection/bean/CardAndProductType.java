/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean;

/**
 *
 * @author ruwan_e
 */
public enum CardAndProductType {

    ALL("ALL");
    
    private String color;

    private CardAndProductType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}