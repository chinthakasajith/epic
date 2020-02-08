/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.accounttemplate.persistance;

import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class AccountTemplatePersistance {

    private List<AccountTempBean> templateList = null;
    private ResultSet rs;

    public List<AccountTempBean> getAllTemplateLis(Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT AT.TEMPLATECODE,AT.TEMPLATENAME,AT.VALIDFROM,AT.VALIDTO,AT.PRODUCTCODE,AT.CURRENCYCODE,AT.TOTALCREDITLIMIT,"
                    + "AT.STATUS,AT.CUSTOMERTEMPLATECODE,AT.INTERESTPROFILECODE,AT.BILLINGCYCLECODE,AT.BILLINGSTATEMENTPROFILECODE,AT.RISKPROFILECODE,"
                    + "S.DESCRIPTION AS STATUS_DES,AT.CARDTYPE,AT.STAFFSTATUS,CT.TEMPLATENAME AS CUS_TEMP_DES,C.DESCRIPTION AS CURR_DES,"
                    + "CY.DESCRIPTION AS CARDTYPEDES,AT.TRNSACTIONPROFILECODE,TP.DESCRIPTION AS TXN_PROF_DES,B.DESCRIPTION AS BILL_DES,"
                    + "RF.DESCRIPTION AS ACC_RSK_DES,BSP.DESCRIPTION AS BILL_STATMNT_DES,IP.DESCRIPTION AS INTEREST_PROF_DES,AT.LASTUPDATEDUSER AS USERR "
                    + "FROM ACCOUNTTEMPLATE AT,STATUS S,CUSTOMERTEMPLATE CT,CURRENCY C,CARDTYPE CY,TRANSACTIONPROFILE TP,BILLINGCYCLE B,"
                    + "RISKPROFILE RF,BILLINGSTATEMENTPROFILE BSP,INTERESTPROFILE IP "
                    + "WHERE S.STATUSCODE=AT.STATUS "
                    + "AND AT.CARDTYPE = CY.CARDTYPECODE "
                    + "AND CT.TEMPLATECODE=AT.CUSTOMERTEMPLATECODE "
                    + "AND C.CURRENCYNUMCODE=AT.CURRENCYCODE "
                    + "AND TP.PROFILECODE = AT.TRNSACTIONPROFILECODE "
                    + "AND AT.BILLINGCYCLECODE = B.BILLINGCYCLECODE "
                    + "AND AT.RISKPROFILECODE = RF.RISKPROFILECODE "
                    + "AND AT.BILLINGSTATEMENTPROFILECODE = BSP.PROFILECODE "
                    + "AND AT.INTERESTPROFILECODE = IP.INTERESTPROFILECODE ");

            templateList = new ArrayList<AccountTempBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                AccountTempBean bean = new AccountTempBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));
                bean.setValiedFrom(rs.getDate("VALIDFROM").toString());
                bean.setValiedTo(rs.getDate("VALIDTO").toString());
                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setCurrencyCode(String.valueOf(rs.getInt("CURRENCYCODE")));
                bean.setTotalCreditLimit(rs.getString("TOTALCREDITLIMIT"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setCustomerTemplateCode(rs.getString("CUSTOMERTEMPLATECODE"));
                bean.setInterestprofileCode(rs.getString("INTERESTPROFILECODE"));
                bean.setBillCycle(rs.getString("BILLINGCYCLECODE"));
                bean.setStatementProf(rs.getString("BILLINGSTATEMENTPROFILECODE"));
                bean.setAccRskProf(rs.getString("RISKPROFILECODE"));
                bean.setStatusDes(rs.getString("STATUS_DES"));
                bean.setCardType(rs.getString("CARDTYPE"));
                bean.setStaff(rs.getString("STAFFSTATUS"));
                bean.setCustomerTemplateDes(rs.getString("CUS_TEMP_DES"));
                bean.setCurrencyDes(rs.getString("CURR_DES"));
                bean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));
                bean.setTxnProfDes(rs.getString("TXN_PROF_DES"));
                bean.setBillCycleDes(rs.getString("BILL_DES"));
                bean.setAccRskProfDes(rs.getString("ACC_RSK_DES"));
                bean.setStatementProfDes(rs.getString("BILL_STATMNT_DES"));
                bean.setInterestprofileDes(rs.getString("INTEREST_PROF_DES"));
                bean.setLastUpdateduser(rs.getString("USERR"));


                templateList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return templateList;
    }

    public List<CustomerTempBean> getCustomerTemplates(Connection con, String staff) throws SQLException {
        PreparedStatement ps = null;
        List<CustomerTempBean> cusTempList = new ArrayList<CustomerTempBean>();
        try {
            ps = con.prepareStatement("SELECT CT.TEMPLATECODE,CT.TEMPLATENAME FROM CUSTOMERTEMPLATE CT WHERE CT.STAFFSTATUS = ? AND CT.STATUS=? ");
            ps.setString(1, staff);
            ps.setString(2, StatusVarList.ACTIVE_STATUS);

            rs = ps.executeQuery();

            while (rs.next()) {

                CustomerTempBean bean = new CustomerTempBean();

                bean.setTemplateCode(rs.getString(1));
                bean.setTemplateName(rs.getString(2));


                cusTempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cusTempList;

    }

    public int insertTemplateRecord(Connection con, AccountTempBean templateBean) throws Exception {

        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO ACCOUNTTEMPLATE "
                    + "(TEMPLATECODE,TEMPLATENAME,VALIDFROM,VALIDTO,TOTALCREDITLIMIT,BILLINGCYCLECODE,CARDTYPE,CURRENCYCODE,CUSTOMERTEMPLATECODE,"
                    + "STATUS,INTERESTPROFILECODE,RISKPROFILECODE,BILLINGSTATEMENTPROFILECODE,LASTUPDATEDUSER,STAFFSTATUS,TRNSACTIONPROFILECODE) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, templateBean.getTemplateCode());
            insertStat.setString(2, templateBean.getTemplateName());
            insertStat.setTimestamp(3, new Timestamp(this.stringTodate(templateBean.getValiedFrom()).getTime()));
            insertStat.setTimestamp(4, new Timestamp(this.stringTodate(templateBean.getValiedTo()).getTime()));
            insertStat.setDouble(5, Double.parseDouble(templateBean.getTotalCreditLimit()));
            insertStat.setString(6, templateBean.getBillCycle());
            insertStat.setString(7, templateBean.getCardType());
            insertStat.setInt(8, Integer.parseInt(templateBean.getCurrencyCode()));
            insertStat.setString(9, templateBean.getCustomerTemplateCode());
            insertStat.setString(10, templateBean.getStatus());
            insertStat.setString(11, templateBean.getInterestprofileCode());
            insertStat.setString(12, templateBean.getAccRskProf());
            insertStat.setString(13, templateBean.getStatementProf());
            insertStat.setString(14, templateBean.getLastUpdateduser());
            insertStat.setString(15, templateBean.getStaff());
            insertStat.setString(16, templateBean.getTxnProfCode());



            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;

    }

    public int updateTemplateRecord(Connection con, AccountTempBean templateBean) throws Exception {
        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE ACCOUNTTEMPLATE SET "
                    + "TEMPLATENAME=?,VALIDFROM=?,VALIDTO=?,TOTALCREDITLIMIT=?,BILLINGCYCLECODE=?,CARDTYPE=?,CURRENCYCODE=?,"
                    + "CUSTOMERTEMPLATECODE=?,STATUS=?,INTERESTPROFILECODE=?,RISKPROFILECODE=?,BILLINGSTATEMENTPROFILECODE=?,"
                    + "LASTUPDATEDUSER=?,STAFFSTATUS=?,TRNSACTIONPROFILECODE=? "
                    + "WHERE TEMPLATECODE=? ");
            insertStat.setString(1, templateBean.getTemplateName());
            insertStat.setTimestamp(2, new Timestamp(this.stringTodate(templateBean.getValiedFrom()).getTime()));
            insertStat.setTimestamp(3, new Timestamp(this.stringTodate(templateBean.getValiedTo()).getTime()));
            insertStat.setString(4, templateBean.getTotalCreditLimit());
            insertStat.setString(5, templateBean.getBillCycle());
            insertStat.setString(6, templateBean.getCardType());
            insertStat.setString(7, templateBean.getCurrencyCode());
            insertStat.setString(8, templateBean.getCustomerTemplateCode());
            insertStat.setString(9, templateBean.getStatus());
            insertStat.setString(10, templateBean.getInterestprofileCode());
            insertStat.setString(11, templateBean.getAccRskProf());
            insertStat.setString(12, templateBean.getStatementProf());
            insertStat.setString(13, templateBean.getLastUpdateduser());
            insertStat.setString(14, templateBean.getStaff());
            insertStat.setString(15, templateBean.getTxnProfCode());
            insertStat.setString(16, templateBean.getTemplateCode());



            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                insertStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return convertedDate;



    }

    public int deleteTemplate(Connection con, AccountTempBean templateBean) throws Exception {
        int result;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE FROM ACCOUNTTEMPLATE WHERE TEMPLATECODE=? ");
            deleteStat.setString(1, templateBean.getTemplateCode());
            result = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }

        return result;
    }

    public HashMap<String, String> getAllAccountRskProf(Connection con, String cusTemp) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> accountRskProf = new HashMap<String, String>();

        try {

            String query = "SELECT RISKPROFILECODE,ACCOUNTPROFILECODE,DESCRIPTION FROM RISKPROFILE WHERE CUSTOMERPROFILECODE = (SELECT RISKPROFILECODE FROM CUSTOMERTEMPLATE WHERE TEMPLATECODE = ?)";
            ps = con.prepareStatement(query);

            ps.setString(1, cusTemp);
            rs = ps.executeQuery();



            while (rs.next()) {
                accountRskProf.put(rs.getString("RISKPROFILECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return accountRskProf;
    }

    public HashMap<String, String> getAllInterestProf(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> interestProf = new HashMap<String, String>();

        try {
            String query = "SELECT INTERESTPROFILECODE,DESCRIPTION FROM INTERESTPROFILE WHERE STATUS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                interestProf.put(rs.getString("INTERESTPROFILECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return interestProf;
    }

    public HashMap<String, String> getAllBillingCycle(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> billingCycle = new HashMap<String, String>();

        try {
            String query = "SELECT BILLINGCYCLECODE,DESCRIPTION FROM BILLINGCYCLE WHERE STATUS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                billingCycle.put(rs.getString("BILLINGCYCLECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return billingCycle;
    }

    public HashMap<String, String> getAllBillStatementProf(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> billStatementProf = new HashMap<String, String>();

        try {
            String query = "SELECT PROFILECODE,DESCRIPTION FROM BILLINGSTATEMENTPROFILE WHERE STATUS = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                billStatementProf.put(rs.getString("PROFILECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return billStatementProf;
    }

    public HashMap<String, String> getAllCardType(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> cardType = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT CARDTYPECODE,DESCRIPTION FROM CARDTYPE WHERE STATUS = ?");
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();

            while (rs.next()) {
                cardType.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return cardType;
    }
}
