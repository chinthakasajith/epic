/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.email.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sajith_g
 */
public class EmailBodyTransformService {

    /**
    * Returns paramets added message body string 
    * <p>
    * used to append user defined parameters to email message body 
    *
    * @param  emailBody  use email template body string
    * @param  delimiter use to split email body for tokenization
    * @param  parameters hashmap <parameterName,parameterValues>
    * @return parameters appended email body string
    * 
    */
    public String transformEmailBodyTokenToParamer(String emailBody,String delimiter, Map<String,String> parameters) {
        
        String[] tokenizedMessageBody = emailBody.split(delimiter);
        
        //check if a parameter exists in the first value of the tikenized array
        boolean isFirstStringParameterd=false;
        
        List parameterKeys = new ArrayList(parameters.keySet());
        
        if (tokenizedMessageBody[0].equals(parameterKeys.get(0))) {
            isFirstStringParameterd=true;
        }
        
        if (emailBody != null) {
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
