/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.security;

import java.security.MessageDigest;

/**
 *
 * @author janaka_h
 */
public class CMSMd5 {
    
    public static String cmsMd5(String value) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(value.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        return result;
    }
    
}
