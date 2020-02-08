/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ApplicationCreditScoreBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfirmation.bean.ApplicationDetailsbean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.SearchAssignAppBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.SupplementaryApplicationBean;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.MessageVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mahesh_m
 */
public class ApplicationConfirmationPersistance {
    
    private ResultSet rs;
    private List<ApplicationDetailsbean> applicationDetails;
    private List<ApplicationDetailsbean> searchList = null;
    private List<ApplicationDetailsbean> finalSearchList = null;
    private List<CardBinBean> cardBinList;
    private List<CustomerTempBean> customerTemplateList;
    private List<AccountTempBean> accounttempList;
    private List<CardTemplateBean> cardTempList;
    private ApplicationDetailsbean applicationDetailsbean = null;
    
    public List<ApplicationDetailsbean> getApplicationDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CA.APPLICATIONID,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,CA.PRIORITYLEVELCODE,CA.REFERANCIALEMPNO,CA.ASSIGNUSER,S.DESCRIPTION FROM CARDAPPLICATION CA,CARDAPPLICATIONSTATUS CAS,STATUS S WHERE CA.APPLICATIONID = CAS.APPLICATIONID AND CAS.APPLICATIONSTATUS = S.STATUSCODE AND CAS.APPLICATIONSTATUS =?");
            
            getAllByCatStat.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            applicationDetails = new ArrayList<ApplicationDetailsbean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                ApplicationDetailsbean bean = new ApplicationDetailsbean();
                
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                bean.setEmployeeNumber(rs.getString("REFERANCIALEMPNO"));
                bean.setAssigneUser(rs.getString("ASSIGNUSER"));
                bean.setApplicationStatus(rs.getString("DESCRIPTION"));
                
                applicationDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return applicationDetails;
    }
    
    public List<CardBinBean> getBinList(Connection con, String productionMode, String cardType, String currency) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT BIN,DESCRIPTION FROM CARDBIN WHERE PRODUCTIONMODE = ? AND CARDDOMAIN = ? AND CARDTYPE = ? AND ONUSSTATUS = ? AND STATUS = ? AND CURRENCYTYPECODE = ?");
            
            getAllByCatStat.setString(1, productionMode);
            getAllByCatStat.setString(2, StatusVarList.CREDIT);
            getAllByCatStat.setString(3, cardType);
            getAllByCatStat.setString(4, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(5, StatusVarList.ACTIVE_STATUS);
            getAllByCatStat.setString(6, currency);
            
            cardBinList = new ArrayList<CardBinBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                CardBinBean bean = new CardBinBean();
                
                bean.setBinNumber(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                
                cardBinList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBinList;
    }
    
    public List<CustomerTempBean> getCustomertemplates(Connection con, String staffStatus, String productType) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            
            String sql = "SELECT DISTINCT CST.TEMPLATECODE , CST.TEMPLATENAME ,CPRD.PRODUCTCODE "
                    + "FROM CUSTOMERTEMPLATE CST "
                    + "INNER JOIN ACCOUNTTEMPLATE ACT ON ACT.CUSTOMERTEMPLATECODE=CST.TEMPLATECODE "
                    + "INNER JOIN CARDTEMPLATE CAT ON CAT.ACCOUNTTEMPLATECODE=ACT.TEMPLATECODE "
                    + "INNER JOIN CARDPRODUCT CPRD ON CPRD.PRODUCTCODE=CAT.PRODUCTCODE "
                    + "WHERE CPRD.PRODUCTCODE=? "
                    + "AND CST.STAFFSTATUS = ? AND CST.STATUS = ?  ";
            
            getAllByCatStat = con.prepareStatement(sql);
            getAllByCatStat.setString(1, productType);
            getAllByCatStat.setString(2, staffStatus);
            getAllByCatStat.setString(3, StatusVarList.ACTIVE_STATUS);
            
            customerTemplateList = new ArrayList<CustomerTempBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                CustomerTempBean bean = new CustomerTempBean();
                
                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));
                
                customerTemplateList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return customerTemplateList;
    }
    
    public List<AccountTempBean> getAccounttemplates(Connection con, String staffStatus, String cardType, String cusTemCode, String currency) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM ACCOUNTTEMPLATE WHERE CARDTYPE = ? AND STAFFSTATUS = ? AND CUSTOMERTEMPLATECODE = ? AND STATUS = ? AND CURRENCYCODE = ?");
            
            getAllByCatStat.setString(1, cardType);
            getAllByCatStat.setString(2, staffStatus);
            getAllByCatStat.setString(3, cusTemCode);
            getAllByCatStat.setString(4, StatusVarList.ACTIVE_STATUS);
            getAllByCatStat.setString(5, currency);
            
            accounttempList = new ArrayList<AccountTempBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                AccountTempBean bean = new AccountTempBean();
                
                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));
                
                accounttempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return accounttempList;
    }
    
    public String getCardType(Connection con, String applicationId, String appType) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String cardtype = null;
        try {
            if (appType != null && appType.equals("S")) {
                getAllByCatStat = con.prepareStatement("SELECT REQUESTCARDTYPE FROM SUPPLEMENTARYAPPLICATION WHERE APPLICATIONID = ? ");
            } else if (appType != null && appType.equals("E")) {
                getAllByCatStat = con.prepareStatement("SELECT REQUESTCARDTYPE FROM ESTABLISHMENTDETAILS WHERE APPLICATIONID = ? ");
            } else {
                getAllByCatStat = con.prepareStatement("SELECT REQUESTCARDTYPE FROM CARDAPPLICATIONPERSONALDETAILS WHERE APPLICATIONID = ? ");
            }
            
            getAllByCatStat.setString(1, applicationId);
            
            accounttempList = new ArrayList<AccountTempBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                cardtype = rs.getString("REQUESTCARDTYPE");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardtype;
    }
    
    public String getStaffStatus(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String staffStatus = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT NVL(STAFFSTATUS,'NO') AS STAFFSTATUS FROM CARDAPPLICATION WHERE APPLICATIONID = ? ");
            
            getAllByCatStat.setString(1, applicationId);
            
            accounttempList = new ArrayList<AccountTempBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                staffStatus = rs.getString("STAFFSTATUS");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return staffStatus;
    }
    
    public List<CardTemplateBean> getCardTemplates(Connection con, String staffStatus, String productCode, String cusTemCode, String accountTempCode, String currency, String cardCategory) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM CARDTEMPLATE WHERE PRODUCTCODE = ? AND STAFFSTATUS = ? AND ACCOUNTTEMPLATECODE = ? AND CURRENCYCODE = ? AND CARDCATEGORY = ? ");
            
            getAllByCatStat.setString(1, productCode);
            getAllByCatStat.setString(2, staffStatus);
            getAllByCatStat.setString(3, accountTempCode);
            getAllByCatStat.setString(4, currency);
            getAllByCatStat.setString(5, cardCategory);
            
            cardTempList = new ArrayList<CardTemplateBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                CardTemplateBean bean = new CardTemplateBean();
                
                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));
                
                cardTempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardTempList;
    }

//    public List<ApplicationDetailsbean> getApplicationDetailsForCreditOfficer(Connection con) throws Exception {
//        PreparedStatement getAllByCatStat = null;
//        ResultSet result = null;
//        try {
//            getAllByCatStat = con.prepareStatement("SELECT CA.APPLICATIONID,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,CA.PRIORITYLEVELCODE,CA.REFERANCIALEMPNO,CA.ASSIGNUSER,S.DESCRIPTION FROM CARDAPPLICATION CA,CARDAPPLICATIONSTATUS CAS,STATUS S WHERE CA.APPLICATIONID = CAS.APPLICATIONID AND CAS.APPLICATIONSTATUS = S.STATUSCODE AND CAS.APPLICATIONSTATUS =?");
//
//            getAllByCatStat.setString(1, StatusVarList.APP_SCORE_COMPLATE);
//            applicationDetails = new ArrayList<ApplicationDetailsbean>();
//
//            result = getAllByCatStat.executeQuery();
//
//            while (result.next()) {
//
//
//                ApplicationDetailsbean bean = new ApplicationDetailsbean();
//                String applicationId = result.getString("APPLICATIONID");
//                if (!isLetterAvailable(con,applicationId)) {
//
//                    bean.setApplicationId(result.getString("APPLICATIONID"));
//                    bean.setIdentificationType(result.getString("IDENTIFICATIONTYPE"));
//                    bean.setIdentificationNumber(result.getString("IDENTIFICATIONNO"));
//                    bean.setPriorityLevel(result.getString("PRIORITYLEVELCODE"));
//                    bean.setEmployeeNumber(result.getString("REFERANCIALEMPNO"));
//                    bean.setAssigneUser(result.getString("ASSIGNUSER"));
//                    bean.setApplicationStatus(result.getString("DESCRIPTION"));
//
//                    applicationDetails.add(bean);
//
//                }
//
//
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } finally {
//            result.close();
//            getAllByCatStat.close();
//        }
//        return applicationDetails;
//    }
    public boolean isLetterAvailable(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT(*) AS COUNT FROM CARDAPPLICATIONDOCUMENT WHERE VERIFICATIONCATEGORY = ? AND APPLICATIONID = ?");
            getAllByCatStat.setString(1, StatusVarList.DOC_CAT_RECOMENDATION_LETTER);
            getAllByCatStat.setString(2, applicationId);
            rs = getAllByCatStat.executeQuery();
            
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());
                
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
                throw e;
            }
            
        }
        return true;
    }
    
    public String getCreditlimi(Connection con, int creditScore, String cardType) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String creditLimit = null;
        int higherLimit = 0;
        String creditLimitLast = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT * FROM RECOMMENDSCHEM WHERE CARDTYPE=? ORDER BY SCORELOWERLIMIT ASC");

//            getAllByCatStat.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            applicationDetails = new ArrayList<ApplicationDetailsbean>();
            
            getAllByCatStat.setString(1, cardType);
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                higherLimit = rs.getInt("SCOREHIGHLIMIT");
                creditLimitLast = rs.getString("CREDITLIMIT");
                
                if (rs.getInt("SCORELOWERLIMIT") <= creditScore && creditScore < rs.getInt("SCOREHIGHLIMIT")) {
                    creditLimit = rs.getString("CREDITLIMIT");
                    break;
                }
                
            }
            if (creditLimit == null) {
                creditLimit = "0";
            }
            if (higherLimit < creditScore) {
                creditLimit = creditLimitLast;
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return creditLimit;
    }
    
    public List<RecommendSchemBean> getCardProducts(Connection con, String cardType, String creditLimit) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<RecommendSchemBean> cardProducts = new ArrayList<RecommendSchemBean>();
        try {
            //getAllByCatStat = con.prepareStatement("SELECT RC.ID,RC.SCORELOWERLIMIT,RC.SCOREHIGHLIMIT,RC.CARDTYPE,RC.CREDITLIMIT,RC.LASTUPDATEDUSER,RC.LASTUPDATEDTIME,RC.CREATETIME,CP.DESCRIPTION,RC.CARDPRODUCT FROM RECOMMENDSCHEM RC,CARDPRODUCT CP WHERE RC.CARDPRODUCT = CP.PRODUCTCODE AND RC.CARDTYPE=? AND to_number(RC.CREDITLIMIT) <= ? AND CP.CARDDOMAIN = ? ORDER BY SCORELOWERLIMIT ASC");
            getAllByCatStat = con.prepareStatement("SELECT RC.ID,RC.SCORELOWERLIMIT,RC.SCOREHIGHLIMIT,RC.CARDTYPE,RC.CREDITLIMIT,RC.LASTUPDATEDUSER,RC.LASTUPDATEDTIME,RC.CREATETIME,CP.DESCRIPTION,RC.CARDPRODUCT FROM RECOMMENDSCHEM RC,CARDPRODUCT CP WHERE RC.CARDPRODUCT = CP.PRODUCTCODE AND RC.CARDTYPE=? AND CP.CARDDOMAIN = ? ORDER BY SCORELOWERLIMIT ASC");

//            getAllByCatStat.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            applicationDetails = new ArrayList<ApplicationDetailsbean>();
            
            getAllByCatStat.setString(1, cardType);
            //getAllByCatStat.setString(2, creditLimit);
            getAllByCatStat.setString(2, DataTypeVarList.CARD_DOMAIN_CREDIT);
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                RecommendSchemBean bean = new RecommendSchemBean();
                bean.setCardProductCode(rs.getString("CARDPRODUCT"));
                bean.setCardproductDescription(rs.getString("DESCRIPTION"));
                cardProducts.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardProducts;
    }

    //get card product list for requested card type by chinthaka
    public List<CardProductBean> getCardProductsToRequestedCardType(Connection con, String cardType) throws Exception {
        PreparedStatement getCardProducts = null;
        List<CardProductBean> cardProductList = new ArrayList<CardProductBean>();
        try {
            getCardProducts = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE CARDTYPE=? AND CARDDOMAIN=?");
            getCardProducts.setString(1, cardType);
            getCardProducts.setString(2, StatusVarList.CREDIT);
            rs = getCardProducts.executeQuery();
            while (rs.next()) {
                CardProductBean cpbean = new CardProductBean();
                cpbean.setProductCode(rs.getString("PRODUCTCODE"));
                cpbean.setDescription(rs.getString("DESCRIPTION"));
                cardProductList.add(cpbean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getCardProducts.close();
        }
        return cardProductList;
    }
    
    public int getUpperLimit(Connection con, int index) throws Exception {
        PreparedStatement getAllByCatStat = null;
        int upperIndex = 0;
        try {
            getAllByCatStat = con.prepareStatement("SELECT SCOREHIGHLIMIT FROM RECOMMENDSCHEM WHERE ID = ?");
            getAllByCatStat.setInt(1, index);
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                upperIndex = rs.getInt("SCOREHIGHLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return upperIndex;
    }
    
    public RecommendSchemBean getDetails(Connection con, int index) throws Exception {
        PreparedStatement getAllByCatStat = null;
        RecommendSchemBean bean = new RecommendSchemBean();
        try {
            getAllByCatStat = con.prepareStatement("SELECT ID,SCORELOWERLIMIT,SCOREHIGHLIMIT,CARDTYPE,CREDITLIMIT FROM RECOMMENDSCHEM WHERE ID = ?");
            getAllByCatStat.setInt(1, index);
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                bean.setId(rs.getString("ID"));
                bean.setLowerlevel(rs.getString("SCORELOWERLIMIT"));
                bean.setUpperLevel(rs.getString("SCOREHIGHLIMIT"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setCreditLimit(rs.getString("CREDITLIMIT"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }
    
    public String getCreditLimit(Connection con, int index) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String creditLimit = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CREDITLIMIT FROM RECOMMENDSCHEM WHERE ID = ?");
            getAllByCatStat.setInt(1, index);
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                creditLimit = rs.getString("CREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return creditLimit;
    }
    
    public Boolean updateRejectReasonAndRemark(Connection con, String rejectReason, String remark, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE CARDAPPLICATION SET REJECTREASONCODE = ?,REJECTREMARKS = ? WHERE APPLICATIONID = ? ");
            
            getAllByCatStat.setString(1, rejectReason);
            getAllByCatStat.setString(2, remark);
            getAllByCatStat.setString(3, applicationId);
            
            rs = getAllByCatStat.executeQuery();
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }
    
    public Boolean updateCardApplicationDocument(Connection con, String applicationId, String sysUser) throws Exception {
        PreparedStatement getAllByCatStat = null;
        Boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONDOCUMENT (APPLICATIONID,VERIFICATIONCATEGORY,DOCUMENTTYPE,FILENAME,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) VALUES(?,?,?,?,?,SYSDATE,SYSDATE)");
            
            getAllByCatStat.setString(1, applicationId);
            getAllByCatStat.setString(2, StatusVarList.DOC_CAT_RECOMENDATION_LETTER);
            getAllByCatStat.setString(3, StatusVarList.DOC_CAT_RECOMENDATION_LETTER_DOCUMENTTYPE);
            getAllByCatStat.setString(4, "Recommendation Letter.pdf");
            getAllByCatStat.setString(5, sysUser);
            rs = getAllByCatStat.executeQuery();
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    //insert credit officer recommended credit limit and card product to cardapplication table
    public Boolean updateCardApplication(Connection con, String appId, CardApplicationBean bean) throws Exception {
        PreparedStatement updateStmt = null;
        Boolean status = false;
        try {
            updateStmt = con.prepareStatement("UPDATE CARDAPPLICATION SET CROFFICERRECCRLIMIT=? , CROFFICERRECPRODTYPE =? WHERE APPLICATIONID= ?");
            updateStmt.setString(1, bean.getcOfficerRecCrditLimt());
            updateStmt.setString(2, bean.getcOfficerRecCardProduct());
            updateStmt.setString(3, appId);
            rs = updateStmt.executeQuery();
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            updateStmt.close();
        }
        return status;
    }
    
    public List<ApplicationCreditScoreBean> getBreakDownValues(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ApplicationCreditScoreBean> breakDownList = new ArrayList<ApplicationCreditScoreBean>();
        
        try {
            getAllByCatStat = con.prepareStatement("SELECT CS.APPLICATIONID,CSR.RULEDESCRIPTION,CS.SCORE FROM APPLICATIONCREDITSCORE CS,CREDITSCORERULE CSR WHERE CS.RULECODE = CSR.RULECODE AND CS.APPLICATIONID = ?");
            
            getAllByCatStat.setString(1, applicationId);
            
            rs = getAllByCatStat.executeQuery();
            while (rs.next()) {
                ApplicationCreditScoreBean bean = new ApplicationCreditScoreBean();
                bean.setApplicationId(applicationId);
                bean.setRuleCode(rs.getString("RULEDESCRIPTION"));
                bean.setScore(rs.getString("SCORE"));
                
                breakDownList.add(bean);
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return breakDownList;
    }
//load confirmation applications

    public List<ApplicationDetailsbean> confirmationApplicationsSearch(Connection con, SearchAssignAppBean searchBean) throws Exception {
        
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            
            strSqlBasic = "SELECT DISTINCT a.APPLICATIONID,ad.DOCUMENTNAME,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO, "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.CARDCATEGORY,cc.DESCRIPTION AS CATEGORYDESCRIPTION,CROFFICERRECCRLIMIT "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,CARDAPPLICATIONSTATUS cas,CARDCATEGORY cc,APPLICATIONDOCUMENT ad";
            
            String condition = "";
            
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }
            
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }
            
            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            } else {
                
                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }
            
            if (!condition.equals("")) {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND " + condition + " AND cas.APPLICATIONSTATUS = ?  AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND cas.APPLICATIONSTATUS = ? AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            }
            
            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

//            stmt.setString(1, StatusVarList.CARD_CATEGORY_MAIN);
            stmt.setString(1, StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE);
//            stmt.setString(2, StatusVarList.APP_SUPP_VERIFY_COMPELTE);
//            stmt.setString(3, StatusVarList.APP_FIXED_VERIFY_COMPELTE);
//            stmt.setString(4, StatusVarList.APP_CORPORATE_VERIFY_COMPELTE);
            stmt.setString(2, DataTypeVarList.CARD_DOMAIN_CREDIT);
            
            rs = stmt.executeQuery();
            
            searchList = new ArrayList<ApplicationDetailsbean>();
            finalSearchList = new ArrayList<ApplicationDetailsbean>();
            
            while (rs.next()) {
                
                ApplicationDetailsbean bean = new ApplicationDetailsbean();
                
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setPriorityLevel(rs.getString("PRIORITYNAME"));
                bean.setApplicationStatus(rs.getString("DESCRIPTION"));
                bean.setApplicationCategory(rs.getString("CARDCATEGORY"));
                bean.setIdentityTypeName(rs.getString("DOCUMENTNAME"));
                bean.setCreditOfficerRecCreditLimit(rs.getString("CROFFICERRECCRLIMIT"));
                bean.setApplicationCategoryDes(rs.getString("CATEGORYDESCRIPTION"));
                
                searchList.add(bean);
                
            }
            //filter applications from credit application confirmation schemas
            stmt = con.prepareStatement("SELECT MINLIMIT,MAXLIMIT FROM CREDITLIMITSCHEMA WHERE SCHEMACODE IN (SELECT SCHEMACODE FROM CREDITLIMTSCHEMAUSERROLE WHERE USERROLECODE= ?) ORDER BY MINLIMIT ASC");
            stmt.setString(1, searchBean.getCurrentUserRole());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Double MINLIMIT = Double.parseDouble(rs.getString("MINLIMIT"));
                Double MAXLIMIT = Double.parseDouble(rs.getString("MAXLIMIT"));
                
                for (int i = 0; i < searchList.size(); i++) {
                    if (Double.parseDouble(searchList.get(i).getCreditOfficerRecCreditLimit()) > MINLIMIT && Double.parseDouble(searchList.get(i).getCreditOfficerRecCreditLimit()) <= MAXLIMIT) {
                        finalSearchList.add(searchList.get(i));
                        //searchList.remove(i);
                    }
                }
                
            }
        } catch (SQLException sqlex) {
            
            throw sqlex;
        } catch (Exception ex) {
            
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();
                
            } catch (Exception e) {
                throw e;
            }
        }
        System.out.println(finalSearchList.size());
        
        return finalSearchList;
    }

    //load creadit officer review applications
    public List<ApplicationDetailsbean> generalSearch(Connection con, SearchAssignAppBean searchBean) throws Exception {
        
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            
            strSqlBasic = "SELECT DISTINCT a.APPLICATIONID,ad.DOCUMENTNAME,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO, "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.CARDCATEGORY,cc.DESCRIPTION AS CATEGORYDESCRIPTION "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,CARDAPPLICATIONSTATUS cas,CARDCATEGORY cc,APPLICATIONDOCUMENT ad";
            
            String condition = "";
            
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }
            
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }
            
            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            } else {
                
                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }
            
            if (!condition.equals("")) {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND " + condition + " AND cas.APPLICATIONSTATUS = (CASE WHEN a.CARDCATEGORY = 'M' OR a.CARDCATEGORY ='E' THEN ? WHEN a.CARDCATEGORY = 'S' THEN ? WHEN a.CARDCATEGORY = 'F' THEN ? WHEN a.CARDCATEGORY = 'C' THEN ? END)  AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND cas.APPLICATIONSTATUS = (CASE WHEN a.CARDCATEGORY = 'M' OR a.CARDCATEGORY ='E' THEN ? WHEN a.CARDCATEGORY = 'S' THEN ? WHEN a.CARDCATEGORY = 'F' THEN ?  WHEN a.CARDCATEGORY = 'C' THEN ? END) AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            }
            
            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

//            stmt.setString(1, StatusVarList.CARD_CATEGORY_MAIN);
            stmt.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            stmt.setString(2, StatusVarList.APP_SUPP_VERIFY_COMPELTE);
            stmt.setString(3, StatusVarList.APP_FIXED_VERIFY_COMPELTE);
            stmt.setString(4, StatusVarList.APP_CORPORATE_VERIFY_COMPELTE);
            stmt.setString(5, DataTypeVarList.CARD_DOMAIN_CREDIT);
            
            rs = stmt.executeQuery();
            
            searchList = new ArrayList<ApplicationDetailsbean>();
            
            while (rs.next()) {
                
                ApplicationDetailsbean bean = new ApplicationDetailsbean();
                
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setPriorityLevel(rs.getString("PRIORITYNAME"));
                bean.setApplicationStatus(rs.getString("DESCRIPTION"));
                bean.setApplicationCategory(rs.getString("CARDCATEGORY"));
                bean.setIdentityTypeName(rs.getString("DOCUMENTNAME"));
                bean.setApplicationCategoryDes(rs.getString("CATEGORYDESCRIPTION"));
                
                searchList.add(bean);
                
            }
            
        } catch (SQLException sqlex) {
            
            throw sqlex;
        } catch (Exception ex) {
            
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();
                
            } catch (Exception e) {
                throw e;
            }
        }
        
        return searchList;
    }

    //debit card application search for confirmation
    public List<ApplicationDetailsbean> debitCardGeneralSearch(Connection con, SearchAssignAppBean searchBean) throws Exception {
        
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            
            strSqlBasic = "SELECT a.APPLICATIONID,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS , st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.CARDCATEGORY,cc.DESCRIPTION AS CATEGORYDESCRIPTION "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,CARDAPPLICATIONSTATUS cas,CARDCATEGORY cc";
            
            String condition = "";
            
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }
            
            if (!searchBean.getIdNumber().contentEquals("") || searchBean.getIdNumber().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.IDENTIFICATIONNO = '" + searchBean.getIdNumber() + "'";
            }
            
            if (!searchBean.getApplicationType().contentEquals("") || searchBean.getApplicationType().length() > 0) {//check again
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getApplicationType() + "'";
            }
            
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }
            
            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND to_Date(a.CREATETIME,'YYYY-MON-DD') < to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')+1";
            }
            
            if (!searchBean.getFromDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME > to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')+1";
            }
            if (!searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "to_Date(a.CREATETIME,'YYYY-MON-DD') <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }
            
            if (!condition.equals("")) {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND " + condition + " AND cas.APPLICATIONSTATUS = ? AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND cas.APPLICATIONSTATUS = ? AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            }
            
            stmt = con.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);

//            stmt.setString(1, StatusVarList.CARD_CATEGORY_MAIN);
            stmt.setString(1, StatusVarList.APP_VERIFY_COMPELTE);
            stmt.setString(2, DataTypeVarList.CARD_DOMAIN_DEBIT);
            
            rs = stmt.executeQuery();
            
            searchList = new ArrayList<ApplicationDetailsbean>();
            
            while (rs.next()) {
                
                ApplicationDetailsbean bean = new ApplicationDetailsbean();
                
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setPriorityLevel(rs.getString("PRIORITYNAME"));
                bean.setApplicationStatus(rs.getString("DESCRIPTION"));
                bean.setApplicationCategory(rs.getString("CARDCATEGORY"));
                bean.setApplicationCategoryDes(rs.getString("CATEGORYDESCRIPTION"));//CATEGORYDESCRIPTION

                searchList.add(bean);
                
            }
            
        } catch (SQLException sqlex) {
            
            throw sqlex;
        } catch (Exception ex) {
            
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();
                
            } catch (Exception e) {
                throw e;
            }
        }
        
        return searchList;
    }
    
    private String stringTodate(String date) throws ParseException {
        String dateString = date;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        
        Date convertedDate = dateFormat.parse(dateString);
        
        return (dateFormat.format(convertedDate));
        
    }
    
    public String getApplicationCategory(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String cardCategory = null;
        List<ApplicationCreditScoreBean> breakDownList = new ArrayList<ApplicationCreditScoreBean>();
        
        try {
            getAllByCatStat = con.prepareStatement("SELECT CARDCATEGORY FROM CARDAPPLICATION WHERE APPLICATIONID = ?");
            
            getAllByCatStat.setString(1, applicationId);
            
            rs = getAllByCatStat.executeQuery();
            while (rs.next()) {
                cardCategory = rs.getString("CARDCATEGORY");
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardCategory;
    }
    
    public String getLetterDirectory(Connection con, String osType) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String letterDirectory = null;
        
        try {
            getAllByCatStat = con.prepareStatement("SELECT LETTERS FROM COMMONFILEPATH WHERE OSTYPE = ?");
            
            getAllByCatStat.setString(1, osType);
            
            rs = getAllByCatStat.executeQuery();
            while (rs.next()) {
                letterDirectory = rs.getString("LETTERS");
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return letterDirectory;
    }
    
    public String getSignatureDirectory(Connection con, String osType) throws Exception {
        PreparedStatement getAllByCatStat = null;
        String signatureDirectory = null;
        
        try {
            getAllByCatStat = con.prepareStatement("SELECT SCANNEDDOCUMENT FROM COMMONFILEPATH WHERE OSTYPE = ?");
            
            getAllByCatStat.setString(1, osType);
            
            rs = getAllByCatStat.executeQuery();
            while (rs.next()) {
                signatureDirectory = rs.getString("SCANNEDDOCUMENT");
            }
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return signatureDirectory;
    }
    
    public List<CardBinBean> getBinList(Connection con, String cardProductCode) throws Exception {
        PreparedStatement getAllByCatStat = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT DISTINCT CB.BIN,CB.DESCRIPTION FROM CARDPRODUCTBIN CPB, CARDBIN CB WHERE CPB.BINCODE = CB.BIN AND CPB.PRODUCTCODE = ? ");
        buff.append(" AND CB.CARDDOMAIN = ? AND CB.ONUSSTATUS = ? AND CB.STATUS = ? ");
        try {
            getAllByCatStat = con.prepareStatement(buff.toString());
            
            getAllByCatStat.setString(1, cardProductCode);
            getAllByCatStat.setString(2, StatusVarList.CREDIT);
            getAllByCatStat.setString(3, StatusVarList.YESSTATUS);
            getAllByCatStat.setString(4, StatusVarList.ACTIVE_STATUS);
            
            cardBinList = new ArrayList<CardBinBean>();
            
            rs = getAllByCatStat.executeQuery();
            
            while (rs.next()) {
                
                CardBinBean bean = new CardBinBean();
                
                bean.setBinNumber(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                
                cardBinList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBinList;
    }
    
    public List<CardBinBean> getCardKeyList(Connection con, String cardProductCode, String binProfileCode) throws Exception {
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
        buff.append("SELECT CARDKEYID FROM CARDPRODUCTBIN WHERE PRODUCTCODE = ? AND BINCODE = ?");
        
        try {
            ps = con.prepareStatement(buff.toString());
            
            ps.setString(1, cardProductCode);
            ps.setString(2, binProfileCode);
            
            cardBinList = new ArrayList<CardBinBean>();
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                CardBinBean bean = new CardBinBean();
                
                bean.setCardKey(rs.getString("CARDKEYID"));
                
                cardKeyList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardKeyList;
    }
    
    public List<CardBinBean> getProductionModeList(Connection con, String CardKeyId) throws Exception {
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        
        List<CardBinBean> productionModeList = new ArrayList<CardBinBean>();
        buff.append("SELECT CPM.CODE , CPM.DESCRIPTION");
        buff.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CKP, ECMS_ONLINE_CARDPRODUCTIONMODE CPM ");
        buff.append(" WHERE CK.CARDKEYID = ? AND CK.CARDKEYPROFILEID = CKP.ID ");
        buff.append(" AND CKP.PRODUCTIONMODE = CPM.CODE");
        
        try {
            ps = con.prepareStatement(buff.toString());
            
            ps.setString(1, CardKeyId);
            
            cardBinList = new ArrayList<CardBinBean>();
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                
                CardBinBean bean = new CardBinBean();
                
                bean.setProductionMode(rs.getString("CODE"));
                bean.setProductionModeDes(rs.getString("DESCRIPTION"));
                
                productionModeList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return productionModeList;
    }
    
    public Double getEstablishmentUsedCreditLimit(Connection CMSCon, String businessRegNo) throws Exception {
        Double establishmentUsedCreditLimit = null;
        
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT NVL (SUM(CREDITLIMIT),0) AS USEDCREDITLIMIT ");
        buff.append("FROM CARD C ");
        buff.append("WHERE C.APPLICATIONID IN (SELECT  CA.APPLICATIONID FROM CARDAPPLICATION CA  ,CARDAPPLICATIONPERSONALDETAILS CAP ");
        buff.append(" WHERE CA.APPLICATIONID=CAP.APPLICATIONID ");
        buff.append(" AND CAP.BUSINESSREGNUMBER =?) ");
        
        try {
            ps = CMSCon.prepareStatement(buff.toString());
            ps.setString(1, businessRegNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                establishmentUsedCreditLimit = rs.getDouble("USEDCREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return establishmentUsedCreditLimit;
    }
    
    public Double getCorporateConformedTotalCredit(Connection CMSCon, String businessRegNo) throws Exception {
        Double corporateConformedTotalCredit = null;
        
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT NVL (SUM(CA.CONFIRMEDCREDITLIMIT),0) AS CONFIRMEDCREDITLIMIT ");
        buff.append("FROM CARDAPPLICATION CA  ");
        buff.append(",CARDAPPLICATIONPERSONALDETAILS CAP ");
        buff.append(",CARDAPPLICATIONSTATUS ST ");
        buff.append("WHERE CA.APPLICATIONID=CAP.APPLICATIONID ");
        buff.append("AND CA.APPLICATIONID=ST.APPLICATIONID  ");
        buff.append("AND ST.APPLICATIONSTATUS=? ");
        buff.append("AND CAP.BUSINESSREGNUMBER =? ");
        try {
            ps = CMSCon.prepareStatement(buff.toString());
            ps.setString(1, StatusVarList.APP_APPROVE_COMPLETE);
            ps.setString(2, businessRegNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                corporateConformedTotalCredit = rs.getDouble("CONFIRMEDCREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return corporateConformedTotalCredit;
    }
    
    public Double getSupUsedCreditLimitOfMainCard(Connection CMSCon, String mainCardNo) throws Exception {
        Double usedCreditLimitOfMainCard = null;
        
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT NVL (SUM(CREDITLIMIT),0) AS USEDCREDITLIMIT ");
        buff.append("FROM CARD C ");
        buff.append("WHERE C.MAINCARDNO =? AND C.CARDNUMBER!=?");
        
        try {
            ps = CMSCon.prepareStatement(buff.toString());
            ps.setString(1, mainCardNo);
            ps.setString(2, mainCardNo); //Exclude main credit limit 
            rs = ps.executeQuery();
            while (rs.next()) {
                usedCreditLimitOfMainCard = rs.getDouble("USEDCREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return usedCreditLimitOfMainCard;
    }

    //get sum of confirmed credit limit of suplimentory applications related to the one primary card from cardapplication table
    public Double getSumOfUsedCreditLimitsOfPrimaryCard(Connection con, String primaryAppId, String supAppId) throws Exception {
        Double sumOfUsedCreditLimitOfMaincard = null;
        PreparedStatement ps = null;
        String sql = "SELECT NVL(SUM(CONFIRMEDCREDITLIMIT),0) AS SUMOFUSEDCREDITLIMITS FROM CARDAPPLICATION WHERE APPLICATIONID IN (SELECT APPLICATIONID FROM SUPPLEMENTARYAPPLICATION WHERE PRIMARYAPPLICATIONID=? AND APPLICATIONID!=?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, primaryAppId);
            ps.setString(2, supAppId);
            rs = ps.executeQuery();
            while (rs.next()) {                
                sumOfUsedCreditLimitOfMaincard=rs.getDouble("SUMOFUSEDCREDITLIMITS");
            }
        } catch (SQLException ex) {
            throw ex;
        }finally {
            rs.close();
            ps.close();
        }
        return sumOfUsedCreditLimitOfMaincard;
    }
    //get credit limit of primary card from cardapplication 
    public Double getCreditLimitOfprimaryCard(Connection con,String primAppId) throws Exception{
        Double primCardCreditlimit=0.0;
        PreparedStatement ps=null;
        String sql="SELECT CONFIRMEDCREDITLIMIT FROM CARDAPPLICATION WHERE APPLICATIONID=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setString(1, primAppId);
            rs=ps.executeQuery();
            while (rs.next()) {                
                primCardCreditlimit=rs.getDouble("CONFIRMEDCREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        }finally{
            rs.close();
            ps.close();
        }
        return primCardCreditlimit;
    }
    
    public Double getSupplymentoryConformedTotalCredit(Connection CMSCon, String mainApplicationId) throws Exception {
        Double supConformedTotalCredit = null;
        
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT NVL (SUM(CA.CONFIRMEDCREDITLIMIT),0) AS CONFIRMEDCREDITLIMIT ");
        buff.append("FROM CARDAPPLICATION CA  ");
        buff.append(",SUPPLEMENTARYAPPLICATION SAP ");
        buff.append(",CARDAPPLICATIONSTATUS ST ");
        buff.append("WHERE CA.APPLICATIONID=SAP.APPLICATIONID ");
        buff.append("AND CA.APPLICATIONID=ST.APPLICATIONID  ");
        buff.append("AND ST.APPLICATIONSTATUS=? ");
        buff.append("AND SAP.PRIMARYAPPLICATIONID =? ");
        try {
            ps = CMSCon.prepareStatement(buff.toString());
            ps.setString(1, StatusVarList.APP_APPROVE_COMPLETE);
            ps.setString(2, mainApplicationId);
            rs = ps.executeQuery();
            while (rs.next()) {
                supConformedTotalCredit = rs.getDouble("CONFIRMEDCREDITLIMIT");
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return supConformedTotalCredit;
    }
    
    public boolean UpdateCardApplicationStatus(Connection con, String applicationId, String status) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=? WHERE APPLICATIONID = ?");
            
            uptByCatStat.setString(1, status);
            uptByCatStat.setString(2, applicationId);
            
            rs = uptByCatStat.executeQuery();
            
            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }
    
    public CardAccountBean getCardAccountByCardNumber(Connection CMSCon, String mainCardNumber) throws Exception {
        
        CardAccountBean cardAccountBean = new CardAccountBean();
        
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT NVL(C.CREDITLIMIT,0) AS CREDITLIMIT ");
        buff.append("FROM CARDACCOUNT C ");
        buff.append("INNER JOIN CARDACCOUNTCUSTOMER CUS  ON CUS.ACCOUNTNO=C.ACCOUNTNO ");
        buff.append("AND CUS.CARDNUMBER =? ");
        
        try {
            ps = CMSCon.prepareStatement(buff.toString());
            ps.setString(1, mainCardNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                cardAccountBean.setCreditLimit(rs.getString("CREDITLIMIT"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return cardAccountBean;
        
    }
    
    public List<ApplicationDetailsbean> getReviewAndConfirmSearchList(Connection CMSCon, SearchAssignAppBean searchBean) throws Exception {
        
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query

        try {
            
            strSqlBasic = "SELECT DISTINCT a.APPLICATIONID,ad.DOCUMENTNAME,a.IDENTIFICATIONTYPE,a.IDENTIFICATIONNO,a.PRIORITYLEVELCODE , pl.DESCRIPTION AS PRIORITYNAME, a.REFERANCIALEMPNO, "
                    + "a.ASSIGNUSER, a.ASSIGNSTATUS ,cas.APPLICATIONSTATUS, st.DESCRIPTION ,a.LASTUPDATEDUSER,a.LASTUPDATEDTIME,a.CREATETIME,a.CARDCATEGORY,cc.DESCRIPTION AS CATEGORYDESCRIPTION,CROFFICERRECCRLIMIT "
                    + "FROM CARDAPPLICATION a ,STATUS st,PRIORITYLEVEL pl ,CARDAPPLICATIONSTATUS cas,CARDCATEGORY cc,APPLICATIONDOCUMENT ad";
            
            String condition = "";
            
            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
            }
            
            if (!searchBean.getPriorityLevel().contentEquals("") || searchBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.PRIORITYLEVELCODE = '" + searchBean.getPriorityLevel() + "'";
            }
            
            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            }
            if (!searchBean.getCardCategory().contentEquals("") || searchBean.getCardCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "a.CARDCATEGORY = '" + searchBean.getCardCategory() + "'";
            } else {
                
                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "a.CREATETIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }
            
            if (!condition.equals("")) {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND " + condition + " AND  (cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? ) AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            } else {
                strSqlBasic += " WHERE cas.APPLICATIONSTATUS = st.STATUSCODE AND a.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND a.CARDCATEGORY=ad.CARDCATEGORYCODE AND a.PRIORITYLEVELCODE = pl.PRIORITYLEVELCODE AND a.APPLICATIONID = cas.APPLICATIONID AND cc.CATEGORYCODE = a.CARDCATEGORY AND (cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? OR cas.APPLICATIONSTATUS = ? ) AND a.CARDAPPLIACTIONDOMAIN =? ORDER BY a.APPLICATIONID";
            }
            
            stmt = CMSCon.prepareStatement(strSqlBasic);
            System.out.println(strSqlBasic);
            stmt.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            stmt.setString(2, StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE);
            stmt.setString(3, StatusVarList.APP_SUPP_VERIFY_COMPELTE);
            stmt.setString(4, StatusVarList.APP_FIXED_VERIFY_COMPELTE);
            stmt.setString(5, StatusVarList.APP_CORPORATE_VERIFY_COMPELTE);
            stmt.setString(6, DataTypeVarList.CARD_DOMAIN_CREDIT);
            
            rs = stmt.executeQuery();
            
            searchList = new ArrayList<ApplicationDetailsbean>();
            finalSearchList = new ArrayList<ApplicationDetailsbean>();
            
            while (rs.next()) {
                
                ApplicationDetailsbean bean = new ApplicationDetailsbean();
                
                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                bean.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setPriorityLevel(rs.getString("PRIORITYNAME"));
                bean.setApplicationStatus(rs.getString("DESCRIPTION"));
                bean.setApplicationCategory(rs.getString("CARDCATEGORY"));
                bean.setIdentityTypeName(rs.getString("DOCUMENTNAME"));
                bean.setCreditOfficerRecCreditLimit(rs.getString("CROFFICERRECCRLIMIT"));
                bean.setApplicationStatusCode(rs.getString("APPLICATIONSTATUS"));
                bean.setApplicationCategoryDes(rs.getString("CATEGORYDESCRIPTION"));
                
                searchList.add(bean);
                
            }
            //filter applications from credit application confirmation schemas
            stmt = CMSCon.prepareStatement("SELECT MINLIMIT,MAXLIMIT FROM CREDITLIMITSCHEMA WHERE SCHEMACODE IN (SELECT SCHEMACODE FROM CREDITLIMTSCHEMAUSERROLE WHERE USERROLECODE= ?) ORDER BY MINLIMIT ASC");
            stmt.setString(1, searchBean.getCurrentUserRole());
            rs = stmt.executeQuery();
            //define a map to store credit limit confirmation schemas
            Map<Integer, List<Double>> schemaMap = new HashMap<Integer, List<Double>>();
            int count = 0;
            while (rs.next()) {
                List<Double> list = new ArrayList<Double>(2);
                
                list.add(0, Double.parseDouble(rs.getString("MINLIMIT")));
                list.add(1, Double.parseDouble(rs.getString("MAXLIMIT")));
                
                schemaMap.put(count, list);
                count++;
            }
            Iterator itr = searchList.iterator();
            while (itr.hasNext()) {
                applicationDetailsbean = (ApplicationDetailsbean) itr.next();
                if (applicationDetailsbean.getApplicationStatusCode() != null && applicationDetailsbean.getApplicationStatusCode().equals(StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE)) {
                    for (Integer kMap : schemaMap.keySet()) {
                        List<Double> schema = schemaMap.get(kMap);
                        schema.size();
                        if (Double.parseDouble(applicationDetailsbean.getCreditOfficerRecCreditLimit()) > schema.get(0) && Double.parseDouble(applicationDetailsbean.getCreditOfficerRecCreditLimit()) <= schema.get(1)) {
                            finalSearchList.add(applicationDetailsbean);
                        }
                    }
                } else {
                    finalSearchList.add(applicationDetailsbean);
                }
            }

//            while (rs.next()) {                
//                Double MINLIMIT=Double.parseDouble(rs.getString("MINLIMIT"));
//                Double MAXLIMIT=Double.parseDouble(rs.getString("MAXLIMIT"));
//                
//                for(int i=0;i<searchList.size();i++){
//                    if(searchList.get(i).getApplicationStatusCode() !=null && searchList.get(i).getApplicationStatusCode().equals(StatusVarList.APP_CREDIT_OFFICER_REVIEW_COMPLETE)){
//                        if(searchList.get(i).getApplicationId().equals("010000000224")){
//                            int a=5;
//                        }
//                        
//                        if(Double.parseDouble(searchList.get(i).getCreditOfficerRecCreditLimit())>MINLIMIT  &&  Double.parseDouble(searchList.get(i).getCreditOfficerRecCreditLimit())<=MAXLIMIT){
//                            finalSearchList.add(searchList.get(i));
//                            searchList.remove(i);
//                        }                    
//                    }else{
//                         finalSearchList.add(searchList.get(i));
//                         searchList.remove(i);
//                    }
//                }
//                
//            }
        } catch (SQLException sqlex) {
            
            throw sqlex;
        } catch (Exception ex) {
            
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();
                
            } catch (Exception e) {
                throw e;
            }
        }
        // System.out.println(finalSearchList.size());

        return finalSearchList;
    }
    
    public CommonCardParameterBean getSupplementaryCreditLimitConfigs(Connection CMSCon) throws Exception {
        
        CommonCardParameterBean commonCardParam = new CommonCardParameterBean();
        
        PreparedStatement ps = null;
        String sqlStmt = "SELECT SUPPLEMENTARYFIXED, SUPPLEMENTARYPERCENTAGE FROM COMMONCARDPARAMETER";
        
        try {
            ps = CMSCon.prepareStatement(sqlStmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                commonCardParam.setSupplementaryCreditLimitFixed(rs.getDouble("SUPPLEMENTARYFIXED"));
                commonCardParam.setSupplementaryCreditLimitPercentage(rs.getDouble("SUPPLEMENTARYPERCENTAGE"));
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        
        return commonCardParam;
    }
    //get supplementary credit limit params from SUPPLEMENTARYAPPLICATION table
    public SupplementaryApplicationBean getSupplementaryCreditLimitParam(Connection CMSCon,String appId) throws Exception{
        SupplementaryApplicationBean supAppCreditLimitParam=new SupplementaryApplicationBean();
        PreparedStatement ps=null;
        String sql="SELECT REQUESTCREDITLIMIT,PERCENTAGECREDITLIMIT FROM SUPPLEMENTARYAPPLICATION WHERE APPLICATIONID= ?";
        try {
            ps=CMSCon.prepareStatement(sql);
            ps.setString(1, appId);
            rs=ps.executeQuery();
            while (rs.next()) {                
                supAppCreditLimitParam.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                supAppCreditLimitParam.setPercentageValue(rs.getString("PERCENTAGECREDITLIMIT"));
            }
        } catch (SQLException e) {
            throw e;
        }finally{
            rs.close();
            ps.close();
        }
        return supAppCreditLimitParam;
    }
    
}
