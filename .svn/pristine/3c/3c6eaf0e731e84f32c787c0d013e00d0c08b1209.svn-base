/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.persistance;

import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.AreaBean;
import com.epic.cms.mtmm.merchantmgt.merchantcustomer.bean.MerchantPaymentModeBean;
import com.epic.cms.reportexp.merchant.bean.MerchantLocationReportBean;
import com.epic.cms.reportexp.merchant.bean.MerchantLocationSearchBean;
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
import java.util.List;

/**
 *
 * @author ruwan_e
 */
public class MerchantLocationReportPersistance {

    ResultSet rs;
    private List<MerchantPaymentModeBean> paymentModeList = null;
    private List<StatusBean> statusList = null;
    private List<AreaBean> areaList = null;
    private MerchantLocationSearchBean searchBean = null;

    public List<StatusBean> getStatusList(Connection con) throws Exception {

        PreparedStatement getAllStatus = null;
        try {
            getAllStatus = con.prepareStatement("SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?");
            getAllStatus.setString(1, StatusVarList.GENESTATUCAT);
            rs = getAllStatus.executeQuery();
            statusList = new ArrayList<StatusBean>();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statusList.add(bean);
            }

            return statusList;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllStatus.close();

        }

    }

    public List<MerchantPaymentModeBean> getPaymentModeList(Connection con) throws Exception {

        PreparedStatement getAllPaymentModes = null;
        try {
            getAllPaymentModes = con.prepareStatement("SELECT P.PAYMENTMODECODE,P.DESCRIPTION "
                    + "FROM PAYMENTMODE P");

            rs = getAllPaymentModes.executeQuery();
            paymentModeList = new ArrayList<MerchantPaymentModeBean>();

            while (rs.next()) {
                MerchantPaymentModeBean paymentMode = new MerchantPaymentModeBean();
                paymentMode.setPaymentMode(rs.getString("PAYMENTMODECODE"));
                paymentMode.setDescription(rs.getString("DESCRIPTION"));
                paymentModeList.add(paymentMode);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllPaymentModes.close();

        }
        return paymentModeList;
    }

    public List<AreaBean> getAreaList(Connection con) throws Exception {

        PreparedStatement getAllAreas = null;
        try {
            getAllAreas = con.prepareStatement("SELECT A.AREACODE,A.DESCRIPTION "
                    + "FROM AREA A");

            rs = getAllAreas.executeQuery();
            areaList = new ArrayList<AreaBean>();

            while (rs.next()) {
                AreaBean areaBean = new AreaBean();
                areaBean.setAreaCode(rs.getString("AREACODE"));
                areaBean.setDescription(rs.getString("DESCRIPTION"));
                areaList.add(areaBean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllAreas.close();

        }

        return areaList;
    }

    public MerchantLocationReportBean getMerchantLocationById(Connection con, String id) throws SQLException {
        PreparedStatement getMerchantLocationById = null;
        try {
            getMerchantLocationById = con.prepareStatement("SELECT * "
                    + "FROM MERCHANTLOCATION "
                    + "WHERE MERCHANTID = " + id);

            rs = getMerchantLocationById.executeQuery();
            searchBean = new MerchantLocationSearchBean();

            MerchantLocationReportBean bean = new MerchantLocationReportBean();
            
            while (rs.next()) {        

                bean.setMerchantId(rs.getString("MERCHANTID"));
                bean.setMerchantCustomerNo(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setContactPersonTitle(rs.getString("CONTACTPERSONTITLE"));
                bean.setContactPersonFirstName(rs.getString("CONTACTPERSONFIRSTNAME"));
                bean.setContactPersonMiddleName(rs.getString("CONTACTPERSONMIDDLENAME"));
                bean.setContactPersonLastName(rs.getString("CONTACTPERSONLASTNAME"));
                bean.setContactPersonPosistion(rs.getString("CONTACTPERSONPOSITON"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setCity(rs.getString("CITY"));
                bean.setPostalCode(rs.getString("POSTALCODE"));
                bean.setCountry(rs.getString("COUNTRY"));
                bean.setTelephoneNo(rs.getString("TELEPHONE"));
                bean.setFaxNo(rs.getString("FAX"));
                bean.setEmail(rs.getString("EMAIL"));
                bean.setActivationDate(rs.getDate("ACTIVATIONDATE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRiskProfile(rs.getString("RISKPROFILE"));
                bean.setFeeProfile(rs.getString("FEEPROFILE"));
                bean.setCommissionProfile(rs.getString("COMMITIONPROFILE"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getString("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getString("CREATEDTIME"));
                bean.setMerchantAccountNo(rs.getString("MERCHANTACCOUNTNO"));
                bean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                bean.setPaymentCycle(rs.getString("PAYMENTCYCLE"));
                bean.setPaymentMode(rs.getString("PAYMENTMODE"));
                bean.setStatementMaintenanceStatus(rs.getString("STATEMENTMAINTEINANCESTATUS"));
                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBranchName(rs.getString("BRANCHNAME"));
                bean.setAccountNumber(rs.getString("ACCOUNTNUMBER"));
                bean.setAccountType(rs.getString("ACCOUNTTYPE"));
                bean.setAccountName(rs.getString("ACCOUNTNAME"));
                bean.setCurrencyCode(rs.getString("CURRENCYCODE"));
            }
            
            return bean;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getMerchantLocationById.close();

        }
    }

    public List<MerchantLocationSearchBean> searchMerchantLocationDetails(Connection CMSCon, MerchantLocationSearchBean searchBean) throws ParseException, SQLException {
        PreparedStatement getSummeryList = null;
        List<MerchantLocationSearchBean> summaryList = new ArrayList<MerchantLocationSearchBean>();
        String strSqlBasic = null;

        try {

            strSqlBasic = "SELECT ML.MERCHANTID,MC.MERCHANTNAME,T.TERMINALID,"
                    + " STATUS.DESCRIPTION AS STATUSDESC,"
                    + " PM.DESCRIPTION AS PMDESCRIPTION,"
                    + " AREA.DESCRIPTION AS CITY,"
                    + " ML.ACTIVATIONDATE"
                    + " FROM MERCHANTLOCATION ML, MERCHANTCUSTOMER MC, TERMINAL T,"
                    + " STATUS, PAYMENTMODE PM, AREA"
                    + " WHERE ML.MERCHANTCUSTOMERNO = MC.MERCHANTCUSTOMERNO AND"
                    + " ML.MERCHANTID =T.MERCHANTID AND"
                    + " ML.STATUS=STATUS.STATUSCODE AND"
                    + " ML.PAYMENTMODE = PM.PAYMENTMODECODE AND"
                    + " ML.CITY = AREA.AREACODE";

            String condition = "";

            if (!searchBean.getMerchantId().contentEquals("") || searchBean.getMerchantId().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(ML.MERCHANTID) LIKE '%" + searchBean.getMerchantId().toUpperCase() + "%'";
            }

            if (!searchBean.getMerchantName().contentEquals("") || searchBean.getMerchantName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MC.MERCHANTNAME) LIKE '%" + searchBean.getMerchantName().toUpperCase() + "%'";
            }

            if (!searchBean.getTerminalID().contentEquals("") || searchBean.getTerminalID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(T.TERMINALID) LIKE '%" + searchBean.getTerminalID().toUpperCase() + "%'";
            }

            if (!searchBean.getStatus().contentEquals("") || searchBean.getStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " STATUS.STATUSCODE = '" + searchBean.getStatus() + "' ";
            }

            if (!searchBean.getPaymentMode().contentEquals("") || searchBean.getPaymentMode().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " PM.PAYMENTMODECODE = '" + searchBean.getPaymentMode() + "' ";
            }



            if (!searchBean.getArea().contentEquals("") || searchBean.getArea().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " AREA.AREACODE = '" + searchBean.getArea() + "' ";
            }

            if (!searchBean.getActivationDateFrom().contentEquals("") || searchBean.getActivationDateFrom().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ML.ACTIVATIONDATE >= TO_DATE('" + this.stringTodate(searchBean.getActivationDateFrom()) + "','yyyy-MM-dd') ";
            }

            if (!searchBean.getActivationDateTo().contentEquals("") || searchBean.getActivationDateTo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " ML.ACTIVATIONDATE - 1 <=  TO_DATE('" + this.stringTodate(searchBean.getActivationDateTo()) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY ML.MERCHANTID DESC ";
            } else {

                strSqlBasic += " ORDER BY ML.MERCHANTID DESC ";
            }

            getSummeryList = CMSCon.prepareStatement(strSqlBasic);
            rs = getSummeryList.executeQuery();

            while (rs.next()) {


                MerchantLocationSearchBean bean = new MerchantLocationSearchBean();

                bean.setMerchantId(rs.getString("MERCHANTID"));
                bean.setTerminalID(rs.getString("TERMINALID"));
                bean.setPaymentMode(rs.getString("PMDESCRIPTION"));
                bean.setStatus(rs.getString("STATUSDESC"));
                bean.setMerchantName(rs.getString("MERCHANTNAME"));
                bean.setArea(rs.getString("CITY"));
                bean.setActivationDate(rs.getString("ACTIVATIONDATE"));

                summaryList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getSummeryList.close();

        }
        return summaryList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
}
