/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.servlet;

import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.businesslogic.ApplicationConfirmationManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class SystemRecomendedCredit {
    ApplicationConfirmationManager confirmationmanager = new ApplicationConfirmationManager();
    private int lowerLimit = 0;
    private int higherLimit = 0;
    private String creditLimit = null;
    private String systemRecomendedCard = null;
    private String cardType = null;
    private String id = null;
    private RecommendSchemBean result = null;
    
    public int getSystemRecomendedCreditLimit(int creditScore,String cardType) throws Exception{
       int numberOfRecords = 0;
        try {
           
            numberOfRecords = confirmationmanager.getNumberOfRecords(creditScore,cardType);
         
//            for (int i = 0; i < numberOfRecords; i++) {
//                RecommendSchemBean bean = new RecommendSchemBean();
//                bean = confirmationmanager.getDetails(i+1);
//                lowerLimit = Integer.parseInt(bean.getLowerlevel());
//                higherLimit = Integer.parseInt(bean.getUpperLevel()); 
//
//                if (lowerLimit <= creditScore && creditScore <= higherLimit) {
//                    creditLimit = bean.getCreditLimit();
//                    cardType = bean.getCardType();
//                     id = bean.getId();
//                    break;
//                }
//            }
//            if (higherLimit < creditScore) {
//                RecommendSchemBean bean = new RecommendSchemBean();
//                bean = confirmationmanager.getDetails(numberOfRecords);
//                creditLimit = bean.getCreditLimit();
//                cardType = bean.getCardType();
//                id = bean.getId();
//            }
//           
//            result = new RecommendSchemBean();
//            result.setCreditLimit(creditLimit);
//            result.setCardType(cardType);
//            result.setId(id);
            
        } catch (Exception e) {
            throw e;
        }
        
        return numberOfRecords;
    }

    public List<RecommendSchemBean> getRecommendedCardTypes(String cardType,String creditLimit) throws Exception {
        
//        String cardArray[] = null;
        List<RecommendSchemBean> cardTypes = new ArrayList<RecommendSchemBean>();
        try {
            cardTypes = confirmationmanager.getCardProducts(cardType,creditLimit);
        } catch (Exception e) {
            
        }
        return cardTypes;
        }
}
        

