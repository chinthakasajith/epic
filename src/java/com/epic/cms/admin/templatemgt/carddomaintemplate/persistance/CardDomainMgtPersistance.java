/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.carddomaintemplate.persistance;

import com.epic.cms.admin.templatemgt.carddomaintemplate.bean.CardDomainBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardDomainMgtPersistance {

    private List<CardDomainBean> cardDomainList = null;
    private HashMap<String, String> customerTemplateList = null;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> accountTemplateList = null;    
    
    private HashMap<String, String> riskProfiles = null;

    public List<CardDomainBean> generalCardDomainSearch(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;		// Holds the Sql query
        try {


            strSqlBasic = " SELECT R.CARDDOMAIN,CDM.DESCRIPTION AS CARDOMAINDES,R.TEMPLATECODE,R.TEMPLATENAME ,R.VALIDFROM,"
                    + " R.VALIDTO,R.CURRENCYCODE,R.STATUS,"
                    + "R.CUSTOMERTEMPLATECODE,"
                    + "R.LASTUPDATEDUSER,R.LASTUPDATEDTIME,R.CREATEDTIME,R.CARDTYPE,R.RISKPROFILECODE,RP.DESCRIPTION AS RSK_DES,"
                    + "CT.DESCRIPTION AS CD_TYPE_DES,R.TRANSACTIONFEEPROFILECODE,TP.DESCRIPTION AS TXN_PROF_DES,CTP.TEMPLATENAME AS CUS_TEMP_DES,"
                    + "C.DESCRIPTION AS CURR_DES,S.DESCRIPTION AS STATUS_DES "
                    + " FROM DEBITCARDACCOUNTTEMPLATE R,STATUS S,CARDTYPE CT,RISKPROFILE RP,TRANSACTIONPROFILE TP,CUSTOMERTEMPLATE CTP,CURRENCY C,"
                    + " CARDDOMAIN CDM "
                    + "WHERE R.STATUS = S.STATUSCODE "
                    + "AND R.CUSTOMERTEMPLATECODE = CTP.TEMPLATECODE "
                    + "AND CT.CARDTYPECODE=R.CARDTYPE "
                    + "AND RP.RISKPROFILECODE=R.RISKPROFILECODE "
                    + "AND TP.PROFILECODE = R.TRANSACTIONFEEPROFILECODE "
                    + "AND C.CURRENCYNUMCODE = R.CURRENCYCODE "
                   // + "AND R.INTERESTPROFILECODE = IP.INTERESTPROFILECODE " 
                    + "AND R.CARDDOMAIN = CDM.DOMAINID "
                    + "ORDER BY R.TEMPLATENAME ";

            stmt = con.prepareStatement(strSqlBasic);
            rs = stmt.executeQuery();

            cardDomainList = new ArrayList<CardDomainBean>();

            while (rs.next()) {

                CardDomainBean resultCardDomainBean = new CardDomainBean();

                resultCardDomainBean.setCardDomainCode(rs.getString("CARDDOMAIN"));
                resultCardDomainBean.setCardDomainDes(rs.getString("CARDOMAINDES"));
                resultCardDomainBean.setCurrencyCode(rs.getString("CURRENCYCODE"));
                resultCardDomainBean.setCustemplateCode(rs.getString("CUSTOMERTEMPLATECODE"));
                resultCardDomainBean.setCustemplateDes(rs.getString("CUS_TEMP_DES"));
              //  resultCardDomainBean.setInterestProfileCode(rs.getString("INTERESTPROFILECODE"));
                resultCardDomainBean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                resultCardDomainBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                resultCardDomainBean.setStatus(rs.getString("STATUS"));
                resultCardDomainBean.setTemplateCode(rs.getString("TEMPLATECODE"));
                resultCardDomainBean.setTemplateName(rs.getString("TEMPLATENAME"));
                resultCardDomainBean.setValidFrom(rs.getString("VALIDFROM").substring(0, 10));
                resultCardDomainBean.setValidTo(rs.getString("VALIDTO").substring(0, 10));
                resultCardDomainBean.setCardTypeCode(rs.getString("CARDTYPE"));
                resultCardDomainBean.setRiskProf(rs.getString("RISKPROFILECODE"));
                resultCardDomainBean.setCardTypeDes(rs.getString("CD_TYPE_DES"));
                resultCardDomainBean.setRiskProfDes(rs.getString("RSK_DES"));
                resultCardDomainBean.setTxnProfCode(rs.getString("TRANSACTIONFEEPROFILECODE"));
                resultCardDomainBean.setTxnProfDes(rs.getString("TXN_PROF_DES"));
                resultCardDomainBean.setStatusDes(rs.getString("STATUS_DES"));
                resultCardDomainBean.setCurrencyDes(rs.getString("CURR_DES"));
             //   resultCardDomainBean.setInterestProfDes(rs.getString("INTEREST_PROF_DES"));

                cardDomainList.add(resultCardDomainBean);

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
            }
        }

        return cardDomainList;
    }

    public HashMap<String, String> getAllCustomerTemplates(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.TEMPLATECODE,TC.TEMPLATENAME "
                    + "FROM CUSTOMERTEMPLATE TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE");

            rs = getAllUserRole.executeQuery();
            customerTemplateList = new HashMap<String, String>();

            while (rs.next()) {

                customerTemplateList.put(rs.getString("TEMPLATECODE"), rs.getString("TEMPLATENAME"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return customerTemplateList;
    }

    public HashMap<String, String> getAllAccountTemplates(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.TEMPLATECODE,TC.TEMPLATENAME "
                    + "FROM ACCOUNTTEMPLATE TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE");

            rs = getAllUserRole.executeQuery();
            accountTemplateList = new HashMap<String, String>();

            while (rs.next()) {

                accountTemplateList.put(rs.getString("TEMPLATECODE"), rs.getString("TEMPLATENAME"));


            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return accountTemplateList;
    }

    public HashMap<String, String> getAllCurrencyList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT TC.CURRENCYNUMCODE,TC.DESCRIPTION "
                    + "FROM CURRENCY TC ");

            rs = ps.executeQuery();
            currencyList = new HashMap<String, String>();

            while (rs.next()) {

                currencyList.put(rs.getString("CURRENCYNUMCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return currencyList;
    }

    public HashMap<String, String> getAllProductList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("");

            rs = getAllUserRole.executeQuery();
            productList = new HashMap<String, String>();

            while (rs.next()) {

                productList.put(rs.getString(""), rs.getString(""));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return productList;
    }

    public boolean updateCardDomain(Connection con, CardDomainBean domainBean) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE DEBITCARDACCOUNTTEMPLATE SET TEMPLATECODE=?,TEMPLATENAME=?, "
                    + "VALIDFROM=?,VALIDTO=?,CURRENCYCODE=?,STATUS=?,CUSTOMERTEMPLATECODE=?,LASTUPDATEDUSER=?,CARDTYPE=?,"
                    + "RISKPROFILECODE=?,LASTUPDATEDTIME=?,TRANSACTIONFEEPROFILECODE=?,CARDDOMAIN=? "
                    + "WHERE TEMPLATECODE=? ");

            updateStat.setString(1, domainBean.getTemplateCode());
            updateStat.setString(2, domainBean.getTemplateName());
            updateStat.setTimestamp(3, new Timestamp(stringToDate(domainBean.getValidFrom()).getTime()));
            updateStat.setTimestamp(4, new Timestamp(stringToDate(domainBean.getValidTo()).getTime()));
            updateStat.setString(5, domainBean.getCurrencyCode());
            updateStat.setString(6, domainBean.getStatus());
            updateStat.setString(7, domainBean.getCustemplateCode());
            updateStat.setString(8, domainBean.getLastUpdatedUser());
            updateStat.setString(9, domainBean.getCardTypeCode());
            updateStat.setString(10, domainBean.getRiskProf());
            updateStat.setTimestamp(11, SystemDateTime.getSystemDataAndTime());
            updateStat.setString(12, domainBean.getTxnProfCode());
            updateStat.setString(13, domainBean.getCardDomainCode());
            updateStat.setString(14, domainBean.getTemplateCode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean deleteCardDomain(Connection con, String templateCode) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE DEBITCARDACCOUNTTEMPLATE WHERE TEMPLATECODE=? ");
            deleteStat.setString(1, templateCode);

            deleteStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean insertCardDomain(Connection con, CardDomainBean domainBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO DEBITCARDACCOUNTTEMPLATE(TEMPLATECODE,TEMPLATENAME,VALIDFROM,VALIDTO,CURRENCYCODE,"
                    + "STATUS,CUSTOMERTEMPLATECODE,LASTUPDATEDUSER,CARDTYPE,RISKPROFILECODE,CREATEDTIME,TRANSACTIONFEEPROFILECODE,CARDDOMAIN) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, domainBean.getTemplateCode());
            insertStat.setString(2, domainBean.getTemplateName());
            insertStat.setTimestamp(3, new Timestamp(stringToDate(domainBean.getValidFrom()).getTime()));
            insertStat.setTimestamp(4, new Timestamp(stringToDate(domainBean.getValidTo()).getTime()));
            insertStat.setString(5, domainBean.getCurrencyCode());
            insertStat.setString(6, domainBean.getStatus());
            insertStat.setString(7, domainBean.getCustemplateCode());
            insertStat.setString(8, domainBean.getLastUpdatedUser());
            insertStat.setString(9, domainBean.getCardTypeCode());
            insertStat.setString(10, domainBean.getRiskProf());
            insertStat.setTimestamp(11, SystemDateTime.getSystemDataAndTime());
            insertStat.setString(12, domainBean.getTxnProfCode());
            insertStat.setString(13, domainBean.getCardDomainCode());
            
            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public HashMap<String, String> getAllInterestProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.INTERESTPROFILECODE,TC.DESCRIPTION "
                    + "FROM INTERESTPROFILE TC");

            rs = getAllUserRole.executeQuery();
            interestProfileList = new HashMap<String, String>();

            while (rs.next()) {

                interestProfileList.put(rs.getString("INTERESTPROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return interestProfileList;
    }

    public HashMap<String, String> getAllCardHolderFeeProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.CARDHOLDERFEEPROFILECODE,TC.DESCRIPTION "
                    + "FROM CARDHOLDERFEE TC");

            rs = getAllUserRole.executeQuery();
            cardHolderFeeProfileList = new HashMap<String, String>();

            while (rs.next()) {

                cardHolderFeeProfileList.put(rs.getString("CARDHOLDERFEEPROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return cardHolderFeeProfileList;
    }

    public Date stringToDate(String dt) throws Exception {
        DateFormat formatter;
        Date date = null;
        try {

            formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = (Date) formatter.parse(dt);

        } catch (Exception ex) {
        }
        return date;
    }    
    
    public HashMap<String, String> getCardRiskProfiles(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT RP.RISKPROFILECODE,RP.DESCRIPTION "
                    + "FROM RISKPROFILE RP WHERE RP.PROFILETYPE = 'RPACT'");

            rs = getAllUserRole.executeQuery();
            riskProfiles = new HashMap<String, String>();

            while (rs.next()) {

                riskProfiles.put(rs.getString("RISKPROFILECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return riskProfiles;
    }
    
}
