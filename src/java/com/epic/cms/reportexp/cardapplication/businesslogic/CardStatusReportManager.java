/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.CardStatusReportBean;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.persistance.CardStatusReportPersistance;
import com.epic.cms.reportexp.cardapplication.persistance.EmbossAndEncodeCardReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class CardStatusReportManager {
    
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardStatusReportPersistance cdStatusPer = null;  
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> cardTypeList = null;
    private List<CardStatusReportBean> searchList = null;
    private List<String> userList;
    private HashMap<String, String> cdProduct = null;
    
    HashMap<String,String> status ;
        
        public List<String> getUser() throws Exception {
        try {
            cdStatusPer = new CardStatusReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = cdStatusPer.getUser(CMSCon);
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
        return userList;
    }
        
        public HashMap<String,String> getCardStatus() throws Exception {
        try {
            cdStatusPer = new CardStatusReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = cdStatusPer.getCardStatus(CMSCon);
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
        return status;
    }
    
        public List<CardStatusReportBean> searchCardStatusReportDetails(CardStatusReportBean inputBean) throws SQLException, Exception {
        try {
            cdStatusPer = new CardStatusReportPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = cdStatusPer.searchCardStatusReportDetails(CMSCon, inputBean);
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
}
