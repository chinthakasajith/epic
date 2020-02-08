/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.exception;

/**
 *
 * @author ayesh
 */
public class NumberFormatException extends Exception {

    /**
     * Creates a new instance of <code>CMSNumberFormatException</code> without detail message.
     */
    public NumberFormatException() {
    }

    /**
     * Constructs an instance of <code>CMSNumberFormatException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NumberFormatException(String msg) {
        super(msg);
    }
}
