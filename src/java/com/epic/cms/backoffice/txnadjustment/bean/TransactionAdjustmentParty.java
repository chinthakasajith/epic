/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.txnadjustment.bean;

/**
 *
 * @author ruwan_e
 */
public enum TransactionAdjustmentParty {

    CARD("CARD", 1),
    MERCHANT("MERCHANT", 2);
    private String name;
    private int code;

    private TransactionAdjustmentParty(String _name, int _code) {
        name = _name;
        code = _code;
    }

    public int getCode() {
        return code;
    }

    public static TransactionAdjustmentParty get(String name) {
        if (name == "CARD") {
            return CARD;
        } else if (name == "MERCHANT") {
            return MERCHANT;
        }
        return null;
    }
    
    public String getName() {
        return name;
    }
}
