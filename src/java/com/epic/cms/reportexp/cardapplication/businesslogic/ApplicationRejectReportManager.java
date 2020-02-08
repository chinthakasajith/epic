/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationconfirmation.bean.RejectReasonBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationRejectReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationRejectReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  
 * @author nisansala
 */
public class ApplicationRejectReportManager {
    
    private Connection CMSCon;
    private SystemUserBean sysUsrBean = null;
    private SystemAuditManager sysAuditManager = null;     
    private ApplicationRejectReportPersistance appRejPer = null;  
    private List<ApplicationRejectReportBean> searchList = null;
    private List<RejectReasonBean> rejectReasons;
    
        public List<ApplicationRejectReportBean> getApplicationRejectDetails(ApplicationRejectReportBean inputBean) throws SQLException, Exception {
        try {
            appRejPer = new ApplicationRejectReportPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = appRejPer.getApplicationRejectDetails(CMSCon, inputBean);
            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return searchList;
    }
        
        public List<RejectReasonBean> getRejectReasons() throws Exception {
        rejectReasons = new ArrayList<RejectReasonBean>();
        try {
            appRejPer = new ApplicationRejectReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            rejectReasons = appRejPer.getRejectReasonList(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return rejectReasons;
    }

    
}
