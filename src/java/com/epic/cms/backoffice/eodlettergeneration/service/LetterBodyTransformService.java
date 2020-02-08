/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodlettergeneration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author chinthaka_r
 */
public class LetterBodyTransformService {
    
    public String transformLetterBodyTokenToParamer(String letterBody,String delimiter, Map<String,String> parameters) {
        
        String[] tokenizedMessageBody = letterBody.split(delimiter);
        
        //check if a parameter exists in the first value of the tikenized array
        boolean isFirstStringParameterd=false;
        
        List parameterKeys = new ArrayList(parameters.keySet());
        
        if (tokenizedMessageBody[0].equals(parameterKeys.get(0))) {
            isFirstStringParameterd=true;
        }
        
        if (letterBody != null) {
            //get the value between the delimitted values
            for (int x = 0; x < tokenizedMessageBody.length; x++) {              
                if (!parameters.isEmpty() && !isFirstStringParameterd && x%2 ==1 && parameterKeys.contains(tokenizedMessageBody[x])) {
                    tokenizedMessageBody[x]=parameters.get(tokenizedMessageBody[x]);
                }
                if (!parameters.isEmpty() && isFirstStringParameterd && x%2 ==1 && parameterKeys.contains(tokenizedMessageBody[x])) {
                    tokenizedMessageBody[x]=parameters.get(tokenizedMessageBody[x]);
                }
            }
        }

        return StringUtils.join(tokenizedMessageBody);
    }
    
}
