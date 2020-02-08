/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.callhistory.businesslogic;

import com.epic.cms.reportexp.callhistory.bean.CallHistoryDetailsBean;
import com.epic.cms.reportexp.callhistory.bean.CallHistorySearchBean;
import com.epic.cms.reportexp.callhistory.bean.CallHistoryViewBean;
import com.epic.cms.reportexp.callhistory.persistance.CallHistoryMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CallHistoryMgtManager {

    private Connection CMSCon;
    private CallHistoryMgtPersistance callHistoryMgtPersistance = null;
    private List<CallHistoryDetailsBean> callHistoryBeanList = null;
    private List<CallHistoryViewBean> callHistoryViewBeanList = null;

    public List<CallHistoryDetailsBean> getAllCallHistoryDetails(CallHistorySearchBean searchBean) throws Exception {

        try {
            callHistoryMgtPersistance = new CallHistoryMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            callHistoryBeanList = callHistoryMgtPersistance.getAllCallHistoryDetails(CMSCon, searchBean);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return callHistoryBeanList;
    }

    public List<CallHistoryViewBean> getAllCallHistoryViewDetails(String callLogId) throws Exception {

        try {
            callHistoryMgtPersistance = new CallHistoryMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            callHistoryViewBeanList = callHistoryMgtPersistance.getAllCallHistoryViewDetails(CMSCon, callLogId);
            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return callHistoryViewBeanList;
    }
}
