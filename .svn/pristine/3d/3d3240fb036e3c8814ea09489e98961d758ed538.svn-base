/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.callhistory.persistance;

import com.epic.cms.reportexp.callhistory.bean.CallHistoryDetailsBean;
import com.epic.cms.reportexp.callhistory.bean.CallHistorySearchBean;
import com.epic.cms.reportexp.callhistory.bean.CallHistoryViewBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CallHistoryMgtPersistance {
    
    private List<CallHistoryDetailsBean> callHistoryBeanList = null;
    private List<CallHistoryViewBean> callHistoryViewBeanList = null;
    
    public List<CallHistoryDetailsBean> getAllCallHistoryDetails(Connection con, CallHistorySearchBean searchBean) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        
        try {
            
            
            strSqlBasic = " SELECT cl.callertype, cl.calllogid , cl.createdtime,"
                    + "cl.lastupdatedtime , cl.lastupdateduser ,cl.referenceno,cl.referencetype "
                    + " FROM CALLLOG cl";
            
            
            
            String condition = "";
            
            
            if (!searchBean.getCallType().contentEquals("") || searchBean.getCallType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " cl.CALLERTYPE = '" + searchBean.getCallType() + "'";
            }
            
//            if (!searchBean.getCardNumber().contentEquals("") || searchBean.getCardNumber().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.CARDNO = '" + searchBean.getCardNumber() + "'";
//            }
            
            
//            if (!searchBean.getApplicationId().contentEquals("") || searchBean.getApplicationId().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.APPLICATIONID = '" + searchBean.getApplicationId() + "'";
//            }
//            if (!searchBean.getmId().contentEquals("") || searchBean.getmId().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.MID = '" + searchBean.getmId() + "'";
//            }
            if (!searchBean.getReferenceNo().contentEquals("") || searchBean.getReferenceNo().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " cl.REFERENCENO = '" + searchBean.getReferenceNo() + "'";
            }
//            if (!searchBean.gettId().contentEquals("") || searchBean.gettId().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.TID = '" + searchBean.gettId() + "'";
//            }
            
//            if (!searchBean.getCustomerId().contentEquals("") || searchBean.getCustomerId().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.CUSTOMERID = '" + searchBean.getCustomerId() + "'";
//            }
            
//            if (!searchBean.getAccountNumber().contentEquals("") || searchBean.getAccountNumber().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " cl.ACCOUNTNO = '" + searchBean.getAccountNumber() + "'";
//            }
            
            
            
            if (!condition.equals("")) {
                strSqlBasic += " WHERE " + condition + " ORDER BY cl.calllogid";
            } else {
                strSqlBasic += " ORDER BY cl.calllogid";
            }
            
            
            
            getAllMasterCard = con.prepareStatement(strSqlBasic);
            
            
            rs = getAllMasterCard.executeQuery();
            callHistoryBeanList = new ArrayList<CallHistoryDetailsBean>();
            
            while (rs.next()) {
                
                CallHistoryDetailsBean resultBean = new CallHistoryDetailsBean();
                
                resultBean.setCallLogId(rs.getString("calllogid"));
                resultBean.setCallType(rs.getString("callertype"));
              //  resultBean.setCardNumber(rs.getString("cardno"));
              //  resultBean.setApplicationId(rs.getString("applicationid"));
              //  resultBean.setmId(rs.getString("mid"));
                resultBean.setReferenceNo(rs.getString("referenceno"));
              //  resultBean.settId(rs.getString("tid"));
              //  resultBean.setCustomerId(rs.getString("customerid"));
              //  resultBean.setAccountNumber(rs.getString("accountno"));
                
                callHistoryBeanList.add(resultBean);
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();
            
        }
        
        return callHistoryBeanList;
    }
    
    public List<CallHistoryViewBean> getAllCallHistoryViewDetails(Connection con, String callLogId) throws Exception {
        
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        
        try {
            
            
            strSqlBasic = "select ch.callhistoryid,ch.calllogid,ch.createddate,ch.lastupdatedtime,ch.lastupdateduser,ch.newvalue,"
                    + "ch.oldvalue,ch.operation,ch.remarks,ch.CARDNO,ch.ACCOUNTNO,ch.CUSTOMERID,ch.APPLICATIONID, from CALLHISTORY ch where ch.calllogid=?";
            
            
       
            
            getAllMasterCard = con.prepareStatement(strSqlBasic);
            getAllMasterCard.setString(1, callLogId);
            
            rs = getAllMasterCard.executeQuery();
            
            callHistoryViewBeanList = new ArrayList<CallHistoryViewBean>();
            
            while (rs.next()) {
                
                CallHistoryViewBean resultBean = new CallHistoryViewBean();
                
                resultBean.setCallLogId(rs.getString("calllogid"));
                resultBean.setCallHistoryId(rs.getString("callhistoryid"));
                resultBean.setNewValue(rs.getString("newvalue"));
                resultBean.setOldValue(rs.getString("oldvalue"));
                resultBean.setOperation(rs.getString("operation"));
                resultBean.setRemarks(rs.getString("remarks"));
                resultBean.setLastUpdatedTime(rs.getString("lastupdatedtime"));
                resultBean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                
                callHistoryViewBeanList.add(resultBean);
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();
            
        }
        
        return callHistoryViewBeanList;
    }
}
