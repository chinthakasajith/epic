/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CalculateCreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.PrioritylevelBean;
import com.epic.cms.system.util.variable.DataTypeVarList;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ayesh
 */
public class CalculateCreditScorePersistence {

    private ResultSet rs = null;

    /**
     * get all priority level details from database
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PrioritylevelBean> getpriorityLevel(Connection con) throws SQLException, Exception {
        List<PrioritylevelBean> ruleList = new ArrayList<PrioritylevelBean>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT PL.PRIORITYLEVELCODE,PL.DESCRIPTION FROM PRIORITYLEVEL PL";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                PrioritylevelBean bean = new PrioritylevelBean();
                bean.setPrioCode(rs.getString("PRIORITYLEVELCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                ruleList.add(bean);
            }
            return ruleList;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * search application list according to user input
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CalculateCreditScoreBean> getSeachAppList(Connection con, CalculateCreditScoreBean bean) throws SQLException, Exception {
        List<CalculateCreditScoreBean> ruleList = new ArrayList<CalculateCreditScoreBean>();
        PreparedStatement ps = null;
        try {
            String where = "";
            String query = "SELECT DISTINCT CA.APPLICATIONID,ad.DOCUMENTNAME,CA.PRIORITYLEVELCODE,CA.IDENTIFICATIONTYPE,"
                    + "CA.IDENTIFICATIONNO, CA.REFERANCIALEMPNO,CA.ASSIGNUSER "
                    + "FROM CARDAPPLICATIONSTATUS CS,CARDAPPLICATION CA,APPLICATIONDOCUMENT ad";


            if (!bean.getApplicationID().isEmpty()) {
                if (!where.isEmpty()) {
                    where += " AND ";
                }
                where += "  CA.APPLICATIONID = '" + bean.getApplicationID() + "' ";
            }
            if (!bean.getPrioCode().isEmpty()) {
                if (!where.isEmpty()) {
                    where += " AND ";
                }
                where += "  CA.PRIORITYLEVELCODE = '" + bean.getPrioCode() + "' ";
            }
            if (!bean.getAssignUser().isEmpty()) {
                if (!where.isEmpty()) {
                    where += " AND ";
                }
                where += "  CA.ASSIGNUSER = '" + bean.getAssignUser() + "' ";
            }

            if (!bean.getFromDate().contentEquals("") && !bean.getToDate().contentEquals("")) {
                if (!where.equals("")) {
                    where += " AND ";
                }
                where += "CA.CREATETIME >= to_Date('" + this.stringTodate(bean.getFromDate()) + "','yy-mm-dd') AND CA.CREATETIME <= to_Date('" + this.stringTodate(bean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!bean.getFromDate().contentEquals("")) {
                    if (!where.equals("")) {
                        where += " AND ";
                    }
                    where += "CA.CREATETIME >= to_Date('" + this.stringTodate(bean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!bean.getToDate().contentEquals("")) {
                    if (!where.equals("")) {
                        where += " AND ";
                    }
                    where += "CA.CREATETIME <= to_Date('" + this.stringTodate(bean.getToDate()) + "','yy-mm-dd')";
                }
            }
            if (where.isEmpty()) {
                query = query + "  WHERE " + "  CA.APPLICATIONID=CS.APPLICATIONID AND CA.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND CA.CARDCATEGORY=ad.CARDCATEGORYCODE AND CS.APPLICATIONSTATUS='VCOM' AND CA.CARDAPPLIACTIONDOMAIN = ?";
            }
            if (!where.isEmpty()) {
                query = query + "  WHERE " + where + " AND CA.APPLICATIONID=CS.APPLICATIONID AND CA.IDENTIFICATIONTYPE=ad.DOCUMENTTYPECODE AND CA.CARDCATEGORY=ad.CARDCATEGORYCODE AND CS.APPLICATIONSTATUS='VCOM' AND CA.CARDAPPLIACTIONDOMAIN = ?";
            }

            System.out.println(query);
            

            ps = con.prepareStatement(query);
            ps.setString(1, DataTypeVarList.CARD_DOMAIN_CREDIT);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                CalculateCreditScoreBean calBean = new CalculateCreditScoreBean();
                calBean.setApplicationID(rs.getString("APPLICATIONID"));
                calBean.setIdNo(rs.getString("IDENTIFICATIONNO"));
                calBean.setIdType(rs.getString("IDENTIFICATIONTYPE"));
                calBean.setAssignUser(rs.getString("ASSIGNUSER"));
                calBean.setEmployeeNo(rs.getString("REFERANCIALEMPNO"));
                calBean.setIdentityTypeName(rs.getString("DOCUMENTNAME"));
                ruleList.add(calBean);
            }
            return ruleList;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }

    /**
     * get all users from database
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<String> getSystemUsers(Connection con) throws SQLException, Exception {
        List<String> userList = new ArrayList<String>();
        PreparedStatement ps = null;
        try {

            String query = "SELECT SU.USERNAME FROM SYSTEMUSER SU";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                userList.add(rs.getString("USERNAME"));
            }
            return userList;
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    
    /**
     * update credit value in card application table
     * @param con
     * @param appID
     * @param value
     * @return
     * @throws Exception 
     */
    public int updateCreditValue(Connection con, String appID, int value) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE CARDAPPLICATION SET CREDITSCORE=?"
                    + "WHERE APPLICATIONID=?";

            ps = con.prepareStatement(query);
            ps.setInt(1, value);
            ps.setString(2, appID);


            i = ps.executeUpdate();



        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }
    
    /**
     * update status of card application status table
     * @param con
     * @param appID
     * @return
     * @throws Exception 
     */
    public int updateApplicaionStatus(Connection con, String appID) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=?"
                    + "WHERE APPLICATIONID=?";

            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.APP_SCORE_COMPLATE);
            ps.setString(2, appID);


            i = ps.executeUpdate();



        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }
}
