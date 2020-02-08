/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.security;

import java.security.SecureRandom;

/**
 *
 * @author sajith_g
 */
public class PasswordGenerator {

    static final String ALPHANUMERICVALUES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom secureRandom = new SecureRandom();

    /**
    * Returns randamly generated string
    * <p>
    * used to append user defined parameters to email message body 
    *
    * @param  passwordLength   length you want generate password
    * @return randamly generated string
    * 
    */
    public String getRandomPassword(int passwordLength) {
        StringBuilder stringBuilder = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            stringBuilder.append(ALPHANUMERICVALUES.charAt(secureRandom.nextInt(ALPHANUMERICVALUES.length())));
        }
        return stringBuilder.toString();
    }  

}
