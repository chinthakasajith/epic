/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.persistance;

import com.epic.cms.switchcontrol.sysmsg.bean.ViewAlertBean;
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
 * @author nisansala
 */
public class ViewAlertPersistance {

    private List<ViewAlertBean> searchAlertList;

    public HashMap<String, String> getReadStatus(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        HashMap<String, String> readStatus = new HashMap<String, String>();
        try {
            String query = "SELECT DISTINCT STATUS FROM ECMS_ONLINE_ALERTS";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            readStatus = new HashMap<String, String>();

            while (rs.next()) {
                if (rs.getString("STATUS").equals("2")) {
                    readStatus.put(rs.getString("STATUS"), "READ");
                } else if (rs.getString("STATUS").equals("1")) {
                    readStatus.put(rs.getString("STATUS"), "UNREAD");
                }
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }

        return readStatus;
    }

    public List<ViewAlertBean> searchAlertData(Connection con, ViewAlertBean alertBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;
        ResultSet rs = null;

        try {
            defaultQuery = "SELECT A.TXNID,A.ALERTINFORMATION,A.STATUS,A.DATETIME,A.RISKCATERGORY,RC.DESCRIPTION "
                    + "FROM ECMS_ONLINE_ALERTS A,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=A.RISKCATERGORY ";

            String condition = "";

            if (!alertBean.getTxnID().contentEquals("") || alertBean.getTxnID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.TXNID = '" + alertBean.getTxnID() + "'";
            }
            if (!alertBean.getReadStatus().contentEquals("") || alertBean.getReadStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.STATUS = '" + alertBean.getReadStatus() + "'";
            }
            if (!alertBean.getRiskCategory().contentEquals("") || alertBean.getRiskCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.RISKCATERGORY = '" + alertBean.getRiskCategory() + "'";
            }
            if (!alertBean.getFromDate().contentEquals("") && !alertBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "A.DATETIME >= to_Date('" + this.stringTodate(alertBean.getFromDate()) + "','yy-mm-dd') AND A.DATETIME <= to_Date('" + this.stringTodate(alertBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!alertBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "A.DATETIME >= to_Date('" + this.stringTodate(alertBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!alertBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "A.DATETIME <= to_Date('" + this.stringTodate(alertBean.getToDate()) + "','yy-mm-dd')";
                }
            }


            if (!condition.equals("")) {
                defaultQuery += " AND " + condition + " ORDER BY A.TXNID";
            } else {
                defaultQuery += " ORDER BY A.TXNID";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchAlertList = new ArrayList<ViewAlertBean>();

            while (rs.next()) {
                ViewAlertBean resultBean = new ViewAlertBean();

                resultBean.setTxnID(rs.getString("TXNID"));
                resultBean.setAlertInfo(rs.getString("ALERTINFORMATION"));
                resultBean.setReadStatus(rs.getString("STATUS"));
                resultBean.setRiskCategory(rs.getString("RISKCATERGORY"));
                resultBean.setRiskDes(rs.getString("DESCRIPTION"));

                searchAlertList.add(resultBean);
            }

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();

            } catch (Exception e) {
            }
        }

        return searchAlertList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }

    public ViewAlertBean viewOneAlert(Connection con, String txnID) throws SQLException, Exception {

        ViewAlertBean viewBean = new ViewAlertBean();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT A.TXNID,A.ALERTINFORMATION,A.STATUS,A.DATETIME,A.RISKCATERGORY "
                    + "FROM ECMS_ONLINE_ALERTS A WHERE  A.TXNID=? ";

            ps = con.prepareStatement(query);
            ps.setString(1, txnID);
            rs = ps.executeQuery();

            while (rs.next()) {
                viewBean.setTxnID(rs.getString("TXNID"));
                viewBean.setAlertInfo(rs.getString("ALERTINFORMATION"));
                viewBean.setReadStatus(rs.getString("STATUS"));
                viewBean.setFromDate(rs.getString("DATETIME"));
                viewBean.setRiskCategory(rs.getString("RISK_CATERGORY"));

            }
            return viewBean;

        } catch (SQLException ex) {
            throw ex;
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

    public int updateReadStatus(String txnID, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE ECMS_ONLINE_ALERTS SET "
                    + "STATUS = ? "
                    + "WHERE TXNID = ? ";


            ps = con.prepareStatement(query);
            ps.setString(1, "2");
            ps.setString(2, txnID);


            row = ps.executeUpdate();

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

        return row;
    }

    public HashMap<String, String> getAllRiskCategories(Connection con) throws SQLException, Exception {

        HashMap<String, String> riskCategory = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            String query = "SELECT RC.CODE,RC.DESCRIPTION FROM ECMS_ONLINE_RISK_CATEGORY RC";

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                riskCategory.put(rs.getString("CODE"), rs.getString("DESCRIPTION"));
            }
            return riskCategory;

        } catch (SQLException ex) {
            throw ex;
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

    public int getCountAlert(Connection con, String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String strSqlBasic = null;
        try {
            
            strSqlBasic = "SELECT COUNT(*) AS TOTAL FROM ECMS_ONLINE_ALERTS A,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=A.RISKCATERGORY AND " + condition;

            System.out.println(strSqlBasic);
            ps = con.prepareStatement(strSqlBasic);
            rs = ps.executeQuery();

            while (rs.next()) {

                count = rs.getInt("TOTAL");

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return count;
    }
    
    public List<ViewAlertBean> getSystemAlertDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = " SELECT * FROM ( "
                    + "SELECT * FROM ( "
                    + "SELECT A.TXNID,A.ALERTINFORMATION,A.STATUS,A.DATETIME,A.RISKCATERGORY,RC.DESCRIPTION, "
                    + "ROWNUM r "
                    + "FROM ECMS_ONLINE_ALERTS A,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=A.RISKCATERGORY AND " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;




            System.out.println("************" + strSqlBasic);
            ps = con.prepareStatement(strSqlBasic);


            rs = ps.executeQuery();
            searchAlertList = new ArrayList<ViewAlertBean>();


            while (rs.next()) {


                ViewAlertBean resultBean = new ViewAlertBean();
                
                resultBean.setTxnID((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setAlertInfo((rs.getString("ALERTINFORMATION") == null) ? "--" :rs.getString("ALERTINFORMATION"));
                resultBean.setReadStatus((rs.getString("STATUS") == null) ? "--" :rs.getString("STATUS"));
                resultBean.setRiskCategory((rs.getString("RISKCATERGORY") == null) ? "--" :rs.getString("RISKCATERGORY"));
                resultBean.setRiskDes((rs.getString("DESCRIPTION") == null) ? "--" :rs.getString("DESCRIPTION"));                

                searchAlertList.add(resultBean);

            }
            

        } catch (Exception ex) {
             throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return searchAlertList;
    }
}
