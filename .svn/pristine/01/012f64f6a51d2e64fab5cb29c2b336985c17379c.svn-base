/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.ApplicationCreditScoreBean;
import com.epic.cms.system.util.datetime.DateUtil;
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
 * @author admin
 */
public class ApplicationCreditScoreBreakDownPersistance {

    ResultSet rs;
    private HashMap<String, String> csUserList = null;
    private List<ApplicationCreditScoreBean> creditScoreList = null;

    public HashMap<String, String> getCreditScoreUserList(Connection CMSCon) throws Exception {

        PreparedStatement getuser = null;
        try {

            getuser = CMSCon.prepareStatement("SELECT S.USERNAME FROM SYSTEMUSER S WHERE S.STATUS = ? ");

            getuser.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = getuser.executeQuery();

            csUserList = new HashMap<String, String>();

            while (rs.next()) {

                csUserList.put(rs.getString("USERNAME"), rs.getString("USERNAME"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            getuser.close();
        }
        return csUserList;
    }

    public List<ApplicationCreditScoreBean> searchApplicationCreditScoreReport(Connection CMSCon, ApplicationCreditScoreBean creditScoreBean) throws Exception {

        PreparedStatement getcsList = null;
        creditScoreList = new ArrayList<ApplicationCreditScoreBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = " SELECT DISTINCT CA.APPLICATIONID,CS.LASTUPDATEDUSER,CA.CREDITSCORE,"
                    + " CS.LASTUPDATEDTIME "
                    + " FROM APPLICATIONCREDITSCORE CS , CARDAPPLICATION CA "
                    + " WHERE CA.APPLICATIONID = CS.APPLICATIONID";

            String condition = "";

            if (!creditScoreBean.getNic().contentEquals("") || creditScoreBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + creditScoreBean.getNic().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE= 'NIC' ";
            }

            if (!creditScoreBean.getPassport().contentEquals("") || creditScoreBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + creditScoreBean.getPassport().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE ='PAS' ";
            }

//            if (!creditScoreBean.getDrivingLicence().contentEquals("") || creditScoreBean.getDrivingLicence().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + creditScoreBean.getDrivingLicence().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE = 'DRL' ";
//            }

            if (!creditScoreBean.getApplicationID().contentEquals("") || creditScoreBean.getApplicationID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.APPLICATIONID LIKE '%" + creditScoreBean.getApplicationID() + "%'";
            }

            if (!creditScoreBean.getBranch().contentEquals("") || creditScoreBean.getBranch().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.BRANCHCODE = " + creditScoreBean.getBranch() + " ";
            }

            if (!creditScoreBean.getPriorityLevel().contentEquals("") || creditScoreBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.PRIORITYLEVELCODE = " + creditScoreBean.getPriorityLevel() + " ";
            }


            if (!creditScoreBean.getCreditScoreUser().contentEquals("") || creditScoreBean.getCreditScoreUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CARDAPPLIACTIONDOMAIN = '" + creditScoreBean.getCreditScoreUser() + "' ";
            }

            if (!creditScoreBean.getFromDate().contentEquals("") || creditScoreBean.getFromDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CS.LASTUPDATEDTIME >= TO_DATE('" + this.stringTodate(creditScoreBean.getFromDate()) + "','yyyy-MM-dd') ";
            }

            if (!creditScoreBean.getToDate().contentEquals("") || creditScoreBean.getToDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CS.LASTUPDATEDTIME - 1 <=  TO_DATE('" + this.stringTodate(creditScoreBean.getToDate()) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY CA.APPLICATIONID DESC ";
            } else {

                strSqlBasic += " ORDER BY CA.APPLICATIONID DESC ";
            }

            getcsList = CMSCon.prepareStatement(strSqlBasic);
            rs = getcsList.executeQuery();

            while (rs.next()) {

                ApplicationCreditScoreBean bean = new ApplicationCreditScoreBean();

                bean.setApplicationID(rs.getString("APPLICATIONID"));
                bean.setTotalCreditScore(rs.getString("CREDITSCORE"));
                bean.setCreditScoreUser(rs.getString("LASTUPDATEDUSER"));                
                bean.setCreditScoredDate(DateUtil.convertTimestampToStringDate(rs.getTimestamp("LASTUPDATEDTIME")));

                creditScoreList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getcsList.close();

        }
        return creditScoreList;
    }

    public List<ApplicationCreditScoreBean> getApplicationCreditScoreDetails(Connection CMSCon, String applicationId) throws Exception {
        PreparedStatement getcsList = null;
        creditScoreList = new ArrayList<ApplicationCreditScoreBean>();
        try {
            getcsList = CMSCon.prepareStatement("SELECT AC.RULECODE, AC.SCORE,"
                    + " AC.LASTUPDATEDTIME, AC.LASTUPDATEDUSER,CR.RULEDESCRIPTION"
                    + " FROM APPLICATIONCREDITSCORE AC,CREDITSCORERULE CR "
                    + " WHERE AC.RULECODE = CR.RULECODE AND AC.APPLICATIONID =?");

            getcsList.setString(1, applicationId);
            rs = getcsList.executeQuery();

            while (rs.next()) {
                ApplicationCreditScoreBean bean = new ApplicationCreditScoreBean();

                bean.setRuleCode(rs.getString("RULECODE"));
                bean.setRuleDes(rs.getString("RULEDESCRIPTION"));
                bean.setTotalCreditScore(rs.getString("SCORE"));
                bean.setCreditScoredDate(this.convertStringDate(rs.getDate("LASTUPDATEDTIME")));
                bean.setCreditScoreUser(rs.getString("LASTUPDATEDUSER"));
                
                creditScoreList.add(bean);
            }


        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getcsList.close();

        }
        return creditScoreList;
    }

    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }

}
