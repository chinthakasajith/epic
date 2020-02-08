/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.persistance;

import com.epic.cms.switchcontrol.sysmsg.bean.ViewFailAlertBean;
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
 * @author nisansala
 */
public class ViewFailAlertPersistance {

    private List<ViewFailAlertBean> searchAlertList;

    public List<ViewFailAlertBean> searchAlertData(Connection con, ViewFailAlertBean errorBean) throws SQLException, Exception {
        //hold the statement to be executed
        PreparedStatement ps = null;
        String defaultQuery = null;
        ResultSet rs = null;

        try {
            defaultQuery = "SELECT F.TXNID,F.ERRORINFORMATION,F.STATUS,F.DATETIME,F.RISKCATERGORY,RC.DESCRIPTION "
                    + "FROM ECMS_ONLINE_FAILS F,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=F.RISKCATERGORY ";

            String condition = "";

            if (!errorBean.getTxnID().contentEquals("") || errorBean.getTxnID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "F.TXNID = '" + errorBean.getTxnID() + "'";
            }
            if (!errorBean.getReadStatus().contentEquals("") || errorBean.getReadStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "F.STATUS = '" + errorBean.getReadStatus() + "'";
            }
            if (!errorBean.getRiskCategory().contentEquals("") || errorBean.getRiskCategory().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "F.RISKCATERGORY = '" + errorBean.getRiskCategory() + "'";
            }
            if (!errorBean.getFromDate().contentEquals("") && !errorBean.getFromDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "F.DATETIME >= to_Date('" + this.stringTodate(errorBean.getFromDate()) + "','yy-mm-dd') AND F.DATETIME <= to_Date('" + this.stringTodate(errorBean.getFromDate()) + "','yy-mm-dd')";
            } else {

                if (!errorBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "F.DATETIME >= to_Date('" + this.stringTodate(errorBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!errorBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "F.DATETIME <= to_Date('" + this.stringTodate(errorBean.getToDate()) + "','yy-mm-dd')";
                }
            }


            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition + " ORDER BY F.TXNID";
            } else {
                defaultQuery += " ORDER BY F.TXNID";
            }

            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            searchAlertList = new ArrayList<ViewFailAlertBean>();

            while (rs.next()) {
                ViewFailAlertBean resultBean = new ViewFailAlertBean();

                resultBean.setTxnID(rs.getString("TXNID"));
                resultBean.setErrorInfo(rs.getString("ERRORINFORMATION"));
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

    public int updateReadStatus(String txnID, Connection con) throws SQLException, Exception {
        int row = -1;
        PreparedStatement ps = null;

        try {
            String query = "UPDATE ECMS_ONLINE_FAILS SET "
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

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }

    public int getCountTxn(Connection con, String condition) throws Exception {

        int count = 0;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String strSqlBasic = null;
        try {


            strSqlBasic = "SELECT COUNT(*) AS TOTAL FROM ECMS_ONLINE_FAILS F,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=F.RISKCATERGORY AND " + condition;
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

    public List<ViewFailAlertBean> getTxnAlertDetails(Connection con, String condition, int start, int end) throws Exception {

        ResultSet rs = null;
        PreparedStatement ps = null;
        String strSqlBasic = null;

        try {


            strSqlBasic = " SELECT * FROM ( SELECT * FROM ( SELECT F.TXNID,F.ERRORINFORMATION,F.STATUS,F.DATETIME,F.RISKCATERGORY,RC.DESCRIPTION, "
                    + "ROWNUM r "
                    + "FROM ECMS_ONLINE_FAILS F,ECMS_ONLINE_RISK_CATEGORY RC WHERE RC.CODE=F.RISKCATERGORY AND " + condition + "  )"
                    + " WHERE r <=  " + end + "  ) WHERE r > " + start;




            System.out.println("************" + strSqlBasic);
            ps = con.prepareStatement(strSqlBasic);


            rs = ps.executeQuery();
            searchAlertList = new ArrayList<ViewFailAlertBean>();


            while (rs.next()) {


                ViewFailAlertBean resultBean = new ViewFailAlertBean();

                resultBean.setTxnID((rs.getString("TXNID") == null) ? "--" : rs.getString("TXNID"));
                resultBean.setErrorInfo((rs.getString("ERRORINFORMATION") == null) ? "--" : rs.getString("ERRORINFORMATION"));
                resultBean.setReadStatus((rs.getString("STATUS") == null) ? "--" : rs.getString("STATUS"));
                resultBean.setRiskCategory((rs.getString("RISKCATERGORY") == null) ? "--" : rs.getString("RISKCATERGORY"));
                resultBean.setRiskDes((rs.getString("DESCRIPTION") == null) ? "--" : rs.getString("DESCRIPTION"));

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
