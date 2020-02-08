/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.ActivatedCardReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.ActivatedCardReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

/**
 *
 * @author asela
 */
public class ActivatedCardReportManager {

    private ActivatedCardReportPersistance activatedCardReportPersistance;
    private List<ActivatedCardReportBean> activatedCardReportBeanList;
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> branchListMap;
    private List<String> userList;
    private Connection CMSCon;
        private SystemAuditManager sysAuditManager = null;
            private static ActivatedCardReportManager setInstance;

    public synchronized static ActivatedCardReportManager getInstance() {
        if (setInstance == null) {
            setInstance = new ActivatedCardReportManager();
        }
        return setInstance;
    }


    public List<ActivatedCardReportBean> getAllCardProductList() throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            activatedCardReportBeanList = activatedCardReportPersistance.getAllCardProductList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return activatedCardReportBeanList;
    }

    public Map<String, String> getAllPriorityLevelMap() throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelMap = activatedCardReportPersistance.getAllPriorityLevelMap(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return priorityLevelMap;
    }

    public Map<String, String> getAllCardTypesMap() throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypesMap = activatedCardReportPersistance.getAllCardTypesMap(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardTypesMap;
    }

    public Map<String, String> getAllBranchListMap() throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchListMap = activatedCardReportPersistance.getAllBranchListMap(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return branchListMap;
    }

    public List<String> getAllUserList() throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = activatedCardReportPersistance.getUserList(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return userList;
    }
    
    public List<ActivatedCardReportBean> getActivatedCardReportDetails(ActivatedCardReportBean searchBean) throws Exception {
        try {

            activatedCardReportPersistance = new ActivatedCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            activatedCardReportBeanList = activatedCardReportPersistance.getCardActivatedReportDetails(CMSCon, searchBean);
            CMSCon.commit();
            
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return activatedCardReportBeanList;
    }  
  
    public void insertAuditValues(SystemAuditBean systemAuditBean) throws SQLException, Exception{
        try {
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
    }
    
}
