/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class CreateCreditScore {

    private CreditScoreManager creditScoremanager = null;
    private CreditScoreBean bean;
    private int creditScore = 0;
    private String applicationId;

    public int getCreditScore(String applicationId ,CardApplicationBean cardApplicationBean) {
        String scoreCardCode = null;
        
        this.applicationId = applicationId;
        creditScore = 0;
        String requestedCardType;

        List<String> creditScoreRuleList = new ArrayList<String>();
        List<CreditScoreBean> creditScoreBeanList = new ArrayList<CreditScoreBean>();
        creditScoremanager = new CreditScoreManager();
        try {
            requestedCardType = creditScoremanager.getRequestedCardType(applicationId ,cardApplicationBean.getCardCategoryCode());
            scoreCardCode = getScoreCardCode(requestedCardType);

            creditScoreRuleList = getCreditScoreRuleList(scoreCardCode);


            //Start for loop
            for (int i = 0; i < creditScoreRuleList.size(); i++) {
                jump:
                {
                    try {
                        setValuesToBean(creditScoreRuleList.get(i), applicationId);  //Set rule code,field ID,field name,form name,data type,table name,colmn name,value of the table

                        CreditScoreRuleBean csRuleBean = new CreditScoreRuleBean();
                        csRuleBean = getCreditScoreRuleDetails(bean.getRuleCode());  //get credit score rule details using rule code

                        this.calculateCreditScore(csRuleBean);                       //get the score according to given rule code

                    } catch (Exception e) {
                        LogFileCreator.writeErrorToCreditScoreLog(e);
                        break jump;
                    }

                    creditScoreBeanList.add(bean);

                }
            }//End for loop

            for (int i = 0; i < creditScoreBeanList.size(); i++) {   //calculate total credit(credit score)
                if (creditScoremanager.isRecordExist(applicationId, creditScoreBeanList.get(i).getRuleCode())) {
                    creditScoremanager.updateApplicationCreditScore(applicationId, creditScoreBeanList.get(i).getRuleCode(), creditScoreBeanList.get(i).getCreditScore());
                } else {
                    creditScoremanager.insertToApplicationCreditScore(applicationId, creditScoreBeanList.get(i).getRuleCode(), creditScoreBeanList.get(i).getCreditScore());
                }

                creditScore = creditScore + Integer.parseInt(creditScoreBeanList.get(i).getCreditScore());

            }

        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
        }
        
        LogFileCreator.writeCreditScoreInfoToLog("generated credit score for application ID : "+applicationId+" --> "+creditScore);
        return creditScore;
    }

    public void setValuesToBean(String ruleCode, String applicationId) throws Exception {
        try {
            bean = new CreditScoreBean();

            bean.setRuleCode(ruleCode);                   //set rule code
            bean = getFieldId(bean);                      //set field ID
            bean = getFieldNameAndFormName(bean);         //set field name and form name and data type
            bean = getTableNameAndColmnName(bean);        //set table name and colmn name
            bean = getValue(bean, applicationId);         //set value


        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }
    }

    public boolean calculateCreditScore(CreditScoreRuleBean csRuleBean) throws Exception {
        boolean status = false;
        try {

            int oparator = Integer.parseInt(csRuleBean.getCondition());

            switch (oparator) {

                case 1:// Operator =
                    if (bean.getDataType().equals("NUMBER")) {
                        if (Integer.parseInt(bean.getValue()) == Integer.parseInt(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("STRING")) {
                        if (bean.getValue().equals(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if (Float.valueOf(bean.getValue().trim()).floatValue() == Float.valueOf(csRuleBean.getValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    }

                    break;

                case 2:// Operator >
                    if (bean.getDataType().equals("NUMBER")) {
                        if (Integer.parseInt(bean.getValue()) > Integer.parseInt(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if (Float.valueOf(bean.getValue().trim()).floatValue() > Float.valueOf(csRuleBean.getValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    }

                    break;

                case 3://Operator <
                    if (bean.getDataType().equals("NUMBER")) {
                        if (Integer.parseInt(bean.getValue()) < Integer.parseInt(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if (Float.valueOf(bean.getValue().trim()).floatValue() < Float.valueOf(csRuleBean.getValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    }
                    break;

                case 4://Operator < or =
                    if (bean.getDataType().equals("NUMBER")) {
                        if (Integer.parseInt(bean.getValue()) <= Integer.parseInt(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if (Float.valueOf(bean.getValue().trim()).floatValue() <= Float.valueOf(csRuleBean.getValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    }
                    break;

                case 5://Operator > or =
                    if (bean.getDataType().equals("NUMBER")) {
                        if (Integer.parseInt(bean.getValue()) >= Integer.parseInt(csRuleBean.getValue())) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if (Float.valueOf(bean.getValue().trim()).floatValue() >= Float.valueOf(csRuleBean.getValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                        } else {
                            bean.setCreditScore("0");
                        }
                    }
                    break;

                case 6:// Operator In Between
                    if (bean.getDataType().equals("NUMBER")) {
                        if ((Integer.parseInt(csRuleBean.getValue())) < Integer.parseInt(bean.getValue()) && (Integer.parseInt(bean.getValue()) < Integer.parseInt(csRuleBean.getMaxValue()))) {

                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    } else if (bean.getDataType().equals("AMOUNT")) {
                        if ((Float.valueOf(csRuleBean.getValue().trim()).floatValue() < Float.valueOf(bean.getValue().trim()).floatValue()) && (Float.valueOf(bean.getValue().trim()).floatValue()) > Float.valueOf(csRuleBean.getMaxValue().trim()).floatValue()) {
                            bean.setCreditScore(csRuleBean.getScore());
                            status = true;
                        } else {
                            bean.setCreditScore("0");
                        }
                    }
                    break;

                case 7:// Operator AND

                    CreditScoreRuleBean csRuleCode1Bean = new CreditScoreRuleBean();
                    CreditScoreRuleBean csRuleCode2Bean = new CreditScoreRuleBean();

                    csRuleCode1Bean = getCreditScoreRuleDetails(csRuleBean.getRuleNoOne());
                    csRuleCode2Bean = getCreditScoreRuleDetails(csRuleBean.getRuleNoTwo());

                    setValuesToBean(csRuleBean.getRuleNoOne(), applicationId);
                    boolean ruleOne = calculateCreditScore(csRuleCode1Bean);

                    setValuesToBean(csRuleBean.getRuleNoTwo(), applicationId);
                    boolean ruleTwo = calculateCreditScore(csRuleCode2Bean);

                    if (ruleOne && ruleTwo) {
                        bean.setCreditScore(csRuleBean.getScore());
                    } else {
                        bean.setCreditScore("0");
                    }

                    break;

                case 8://Operator OR
                    CreditScoreRuleBean csRuleCodeOR1Bean = new CreditScoreRuleBean();
                    CreditScoreRuleBean csRuleCodeOR2Bean = new CreditScoreRuleBean();

                    csRuleCodeOR1Bean = getCreditScoreRuleDetails(csRuleBean.getRuleNoOne());
                    csRuleCodeOR2Bean = getCreditScoreRuleDetails(csRuleBean.getRuleNoTwo());

                    setValuesToBean(csRuleBean.getRuleNoOne(), applicationId);
                    boolean ruleOROne = calculateCreditScore(csRuleCodeOR1Bean);

                    setValuesToBean(csRuleBean.getRuleNoTwo(), applicationId);
                    boolean ruleORTwo = calculateCreditScore(csRuleCodeOR2Bean);

                    if (ruleOROne || ruleORTwo) {
                        bean.setCreditScore(csRuleBean.getScore());
                    } else {
                        bean.setCreditScore("0");
                    }

                    break;

            }
        } catch (NumberFormatException numFormat) {
            LogFileCreator.writeErrorToCreditScoreLog(numFormat);
            throw numFormat;
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }
        return status;
    }

    //get score card code
    public String getScoreCardCode(String cardType) throws Exception {
        String scoreCardCode;

        creditScoremanager = new CreditScoreManager();

        try {
            scoreCardCode = creditScoremanager.getScoreCardCode(cardType);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return scoreCardCode;
    }

    //set credit score rule list
    public List<String> getCreditScoreRuleList(String scoreCardCode) throws Exception {
        List<String> creditScoreRuleList = new ArrayList<String>();
        creditScoremanager = new CreditScoreManager();

        try {
            creditScoreRuleList = creditScoremanager.getCreditScoreRuleList(scoreCardCode);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return creditScoreRuleList;
    }

    //set field id to the bean
    public CreditScoreBean getFieldId(CreditScoreBean bean) throws Exception {
        CreditScoreBean creditScorebean = new CreditScoreBean();
        creditScoremanager = new CreditScoreManager();

        try {
            creditScorebean = creditScoremanager.getFieldId(bean);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return creditScorebean;
    }

    //set field name and form name to the bean
    public CreditScoreBean getFieldNameAndFormName(CreditScoreBean bean) throws Exception {
        CreditScoreBean creditScorebean = new CreditScoreBean();
        creditScoremanager = new CreditScoreManager();

        try {
            creditScorebean = creditScoremanager.getFieldNameAndFormName(bean);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return creditScorebean;
    }

    //set table name and colmn name to the bean
    public CreditScoreBean getTableNameAndColmnName(CreditScoreBean bean) throws Exception {
        CreditScoreBean creditScorebean = new CreditScoreBean();
        creditScoremanager = new CreditScoreManager();

        try {
            creditScorebean = creditScoremanager.getTableNameAndColmnName(bean);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return creditScorebean;
    }

    //set value of the given table and colmn name to the bean
    public CreditScoreBean getValue(CreditScoreBean bean, String applicationId) throws Exception {
        CreditScoreBean creditScorebean = new CreditScoreBean();
        creditScoremanager = new CreditScoreManager();

        try {
            creditScorebean = creditScoremanager.getValue(bean, applicationId);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return creditScorebean;
    }

    //get the credit score rule details
    public CreditScoreRuleBean getCreditScoreRuleDetails(String ruleCode) throws Exception {
        creditScoremanager = new CreditScoreManager();
        String oparator = null;
        CreditScoreRuleBean csRuleBean = new CreditScoreRuleBean();

        try {
            csRuleBean = creditScoremanager.getCreditScoreRuleDetails(ruleCode);
        } catch (Exception e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            throw e;
        }

        return csRuleBean;
    }
}
