/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.persistance;

import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.reportexp.merchant.bean.MerchantCustomerReportBean;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author admin
 */
public class MerchantCustomerDetailsPersistance {
    
    ResultSet rs;
    private HashMap<String, String> merchantAreas = null;
    private List<MerchantCustomerReportBean> summeryList = null;
    
    public HashMap<String, String> getAreas(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT AREACODE,DESCRIPTION FROM AREA");
            rs = ps.executeQuery();
            merchantAreas = new HashMap<String, String>();

            while (rs.next()) {
                merchantAreas.put(rs.getString("AREACODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }
        merchantAreas=sortMerchantAreas(merchantAreas);
        return merchantAreas;
    }
    
    private static HashMap<String, String> sortMerchantAreas(HashMap<String, String> input) {
        Map<String, String> tempMap = new HashMap<String, String>();
        for (String wsState : input.keySet()){
            tempMap.put(wsState,input.get(wsState));
        }
        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        List<String> mapValues = new ArrayList<String>(tempMap.values());
        HashMap<String, String> sortedMap = new LinkedHashMap<String, String>();
        TreeSet<String> sortedSet = new TreeSet<String>(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        for (int i=0; i<size; i++){
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), 
                          (String)sortedArray[i]);
        }
        return sortedMap;
    }

    public List<StatusBean> getStatustList(Connection cmsCon) throws SQLException, Exception {
        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

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

    public List<MerchantCustomerReportBean> searchMerchantCusReport(Connection CMSCon, MerchantCustomerReportBean summeryBean)throws Exception {
        PreparedStatement getSummeryList = null;
        summeryList = new ArrayList<MerchantCustomerReportBean>();
        String strSqlBasic = null;
        
        try {
            strSqlBasic = "SELECT DISTINCT MC.MERCHANTCUSTOMERNO,MC.MERCHANTNAME,AREA.DESCRIPTION AS CITY,"
                    + " MC.COMMISSIONPROFILE,MC.FEEPROFILE,MC.RISKPROFILE,MC.PAYMENTMODE,"
                    + " MC.STATEMENTCYCLE,MC.ACTIVATIONDATE,MC.ACCOUNTNUMBER ,ST.DESCRIPTION AS STATUSDESC,"
                    + " CURRENCY.DESCRIPTION AS CURRENCY, BANK.BANKNAME"
                    + " FROM MERCHANTCUSTOMER MC,CURRENCY,BANK,AREA,STATUS ST,MERCHANTLOCATION,TERMINAL"
                    + " WHERE MC.CITY = AREA.AREACODE AND"
                    + " MC.STATUS = ST.STATUSCODE AND"
                    + " MC.CURRENCYCODE = CURRENCY.CURRENCYNUMCODE AND"
                    + " MC.MERCHANTCUSTOMERNO = MERCHANTLOCATION.MERCHANTCUSTOMERNO AND"
                    + " MERCHANTLOCATION.MERCHANTID = TERMINAL.MERCHANTID AND"
                    + " MC.BANKCODE =BANK.BANKCODE";  
            
            String condition = "";       
           
            String merchantCusID=summeryBean.getMerchantCusID();
            String merchantCusName=summeryBean.getMerchantCusName();
            String merchantID=summeryBean.getMerchantID();
            String terminalID=summeryBean.getTerminalID();
            String merchantCusArea=summeryBean.getMerchantCusArea();
            String merchantCusStatus=summeryBean.getMerchantCusStatus();
            String fromDate=summeryBean.getFromDate();
            String toDate=summeryBean.getToDate();
            
            if (!merchantCusID.contentEquals("") || merchantCusID.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MC.MERCHANTCUSTOMERNO) LIKE '%" + merchantCusID.toUpperCase() + "%'";
            }
            if (!merchantCusName.contentEquals("") || merchantCusName.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MC.MERCHANTNAME) LIKE '%" + merchantCusName.toUpperCase() + "%'";
            }
            if (!merchantID.contentEquals("") || merchantID.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(MERCHANTLOCATION.MERCHANTID) LIKE '%" + merchantID.toUpperCase() + "%'";
            }
            if (!terminalID.contentEquals("") || terminalID.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(TERMINAL.MERCHANTID) LIKE '%" + terminalID.toUpperCase() + "%'";
            }
            if (!merchantCusArea.contentEquals("") || merchantCusArea.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MC.CITY = " + merchantCusArea + " ";
            }
            if (!merchantCusStatus.contentEquals("") || merchantCusStatus.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MC.STATUS = '" + merchantCusStatus + "' ";
            }
            if (!fromDate.contentEquals("") || fromDate.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MC.ACTIVATIONDATE >= TO_DATE('" + this.stringTodate(fromDate) + "','yyyy-MM-dd') ";
            }

            if (!toDate.contentEquals("") || toDate.length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " MC.ACTIVATIONDATE - 1 <=  TO_DATE('" + this.stringTodate(toDate) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY MC.MERCHANTCUSTOMERNO DESC ";
            } else {

                strSqlBasic += " ORDER BY MC.MERCHANTCUSTOMERNO DESC ";
            }

            getSummeryList = CMSCon.prepareStatement(strSqlBasic);
            rs = getSummeryList.executeQuery();

            while (rs.next()) {

                MerchantCustomerReportBean bean = new MerchantCustomerReportBean();

                bean.setMerchantCusID(rs.getString("MERCHANTCUSTOMERNO"));
                bean.setMerchantCusName(rs.getString("MERCHANTNAME"));
                bean.setMerchantCusArea(rs.getString("CITY"));
                bean.setCommissionProfile(rs.getString("COMMISSIONPROFILE"));
                bean.setFeeProfile(rs.getString("FEEPROFILE"));
                bean.setRiskProfile(rs.getString("RISKPROFILE"));
                bean.setPaymentMode(rs.getString("PAYMENTMODE"));
                bean.setStatementCycle(rs.getString("STATEMENTCYCLE"));
                bean.setActivationDate(this.convertStringDate(rs.getDate("ACTIVATIONDATE")));
                bean.setAccNumber(rs.getString("ACCOUNTNUMBER"));
                bean.setMerchantCusStatus(rs.getString("STATUSDESC"));
                bean.setCurrency(rs.getString("CURRENCY"));
                bean.setBankName(rs.getString("BANKNAME"));
                
                summeryList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getSummeryList.close();

        }
        return summeryList;
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
