/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.persistance;

import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
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
public class CustomerTemplatePersistance {

    private ResultSet rs;
    private List<CustomerTempBean> templateList;

    public List<CustomerTempBean> getAllTemplateLis(Connection con) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT CT.TEMPLATECODE,CT.TEMPLATENAME,CT.VALIDFROM,CT.VALIDTO,"
                    + "CT.CURRENCYCODE,CT.TOTALCREDITLIMIT,CT.STATUS,ST.DESCRIPTION AS STATUSDES,CT.STAFFSTATUS,CT.RISKPROFILECODE,"
                    + "C.DESCRIPTION AS CURR_DES,CT.TRNSACTIONPROFILECODE,TP.DESCRIPTION AS TXN_PROF_DES,RP.DESCRIPTION AS RSK_DES  "
                    + "FROM CUSTOMERTEMPLATE CT,STATUS ST,CURRENCY C,TRANSACTIONPROFILE TP,RISKPROFILE RP "
                    + "WHERE  ST.STATUSCODE=CT.STATUS "
                    + "AND C.CURRENCYNUMCODE=CT.CURRENCYCODE "
                    + "AND RP.RISKPROFILECODE = CT.RISKPROFILECODE "
                    + "AND TP.PROFILECODE = CT.TRNSACTIONPROFILECODE");

            templateList = new ArrayList<CustomerTempBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                CustomerTempBean bean = new CustomerTempBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));
                bean.setValiedFrom(rs.getDate("VALIDFROM").toString());
                bean.setValiedTo(rs.getDate("VALIDTO").toString());
                bean.setCurrencyCode(String.valueOf(rs.getInt("CURRENCYCODE")));
                bean.setTotalCreditLimit(rs.getString("TOTALCREDITLIMIT"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STATUSDES"));
                bean.setStaff(rs.getString("STAFFSTATUS"));
                bean.setCusRiskProfile(rs.getString("RISKPROFILECODE"));
                bean.setCurrencyDes(rs.getString("CURR_DES"));
                bean.setTxnProfCode(rs.getString("TRNSACTIONPROFILECODE"));
                bean.setTxnProfDes(rs.getString("TXN_PROF_DES"));
                bean.setCusRiskProfDes(rs.getString("RSK_DES"));

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

    public int insertTemplateRecord(Connection con, CustomerTempBean templateBean) throws Exception, SQLException {

        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CUSTOMERTEMPLATE "
                    + "(TEMPLATECODE,TEMPLATENAME,VALIDFROM,VALIDTO,CURRENCYCODE,TOTALCREDITLIMIT,STATUS,STAFFSTATUS,LASTUPDATEDUSER,RISKPROFILECODE,"
                    + "TRNSACTIONPROFILECODE) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?) ");

            insertStat.setString(1, templateBean.getTemplateCode());
            insertStat.setString(2, templateBean.getTemplateName());
            insertStat.setTimestamp(3, new Timestamp(this.stringTodate(templateBean.getValiedFrom()).getTime()));
            insertStat.setTimestamp(4, new Timestamp(this.stringTodate(templateBean.getValiedTo()).getTime()));
            insertStat.setInt(5, Integer.parseInt(templateBean.getCurrencyCode()));
            insertStat.setDouble(6, Double.parseDouble(templateBean.getTotalCreditLimit()));
            insertStat.setString(7, templateBean.getStatus());
            insertStat.setString(8, templateBean.getStaff());
            insertStat.setString(9, templateBean.getLastUpdateduser());
            insertStat.setString(10, templateBean.getCusRiskProfile());
            insertStat.setString(11, templateBean.getTxnProfCode());

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

    public int updateTemplateRecord(Connection con, CustomerTempBean templateBean) throws Exception {
        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CUSTOMERTEMPLATE SET "
                    + "TEMPLATENAME=?,VALIDFROM=?,VALIDTO=?,CURRENCYCODE=?,TOTALCREDITLIMIT=?,STATUS=?,STAFFSTATUS=?,LASTUPDATEDUSER=?,RISKPROFILECODE=?"
                    + ",TRNSACTIONPROFILECODE=? "
                    + "WHERE TEMPLATECODE=? ");
            insertStat.setString(1, templateBean.getTemplateName());
            insertStat.setTimestamp(2, new Timestamp(this.stringTodate(templateBean.getValiedFrom()).getTime()));
            insertStat.setTimestamp(3, new Timestamp(this.stringTodate(templateBean.getValiedTo()).getTime()));
            insertStat.setString(4, templateBean.getCurrencyCode());
            insertStat.setDouble(5, Double.parseDouble(templateBean.getTotalCreditLimit()));
            insertStat.setString(6, templateBean.getStatus());
            insertStat.setString(7, templateBean.getStaff());
            insertStat.setString(8, templateBean.getLastUpdateduser());
            insertStat.setString(9, templateBean.getCusRiskProfile());
            insertStat.setString(10, templateBean.getTxnProfCode());
            insertStat.setString(11, templateBean.getTemplateCode());

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

    public int deleteTemplate(Connection con, CustomerTempBean templateBean) throws Exception {
        int result;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE FROM CUSTOMERTEMPLATE WHERE TEMPLATECODE=? ");
            deleteStat.setString(1, templateBean.getTemplateCode());
            result = deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }

        return result;
    }

    public List<CurrencyBean> getCurrencyDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<CurrencyBean> currencyDetails = new ArrayList<CurrencyBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT C.CURRENCYNUMCODE,C.DESCRIPTION FROM CURRENCY C");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                CurrencyBean currency = new CurrencyBean();

                currency.setCurrencyCode(rs.getString("CURRENCYNUMCODE"));
                currency.setCurrencyDes(rs.getString("DESCRIPTION"));

                currencyDetails.add(currency);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return currencyDetails;
    }

    public Date stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return convertedDate;



    }

    /**
     * to get all product types
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllProductType(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> productType = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT");
            rs = ps.executeQuery();

            while (rs.next()) {
                productType.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return productType;
    }

    public HashMap<String, String> getAllCustomerRskProf(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> cusRskProf = new HashMap<String, String>();

        try {
            String query = "SELECT RISKPROFILECODE,DESCRIPTION FROM RISKPROFILE WHERE PROFILETYPE='RPCUS'";
            ps = con.prepareStatement(query);

            //ps.setString(1, StatusVarList.CUS_RISK_PROF);
            rs = ps.executeQuery();


            while (rs.next()) {
                cusRskProf.put(rs.getString("RISKPROFILECODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        return cusRskProf;
    }
}
