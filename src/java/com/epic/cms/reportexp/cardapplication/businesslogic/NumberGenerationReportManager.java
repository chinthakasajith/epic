/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.NumberGenerationReportBean;
import com.epic.cms.reportexp.cardapplication.persistance.NumberGenerationReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

/**
 *
 * @author asela
 */
public class NumberGenerationReportManager {

    private NumberGenerationReportBean numberGenerationReportBean;
    private List<NumberGenerationReportBean> numbergenerationReportBeanList;
    private NumberGenerationReportPersistance numberGenerationReportPersistance;
    private Map<String, String> cardTypesMap;
    private Map<String, String> priorityLevelMap;
    private Map<String, String> branchListMap;
    private List<String> userList;
    private Connection CMSCon;
    private static NumberGenerationReportManager setInstance;
    private SystemAuditManager sysAuditManager = null;

    public synchronized static NumberGenerationReportManager getInstance() {
        if (setInstance == null) {
            setInstance = new NumberGenerationReportManager();
        }
        return setInstance;
    }

    public List<NumberGenerationReportBean> getAllCardProductList() throws Exception {
        try {

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            numbergenerationReportBeanList = numberGenerationReportPersistance.getAllCardProductList(CMSCon);
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
        return numbergenerationReportBeanList;
    }

    public Map<String, String> getAllPriorityLevelMap() throws Exception {
        try {

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            priorityLevelMap = numberGenerationReportPersistance.getAllPriorityLevelMap(CMSCon);
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

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypesMap = numberGenerationReportPersistance.getAllCardTypesMap(CMSCon);
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

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchListMap = numberGenerationReportPersistance.getAllBranchListMap(CMSCon);
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

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = numberGenerationReportPersistance.getUserList(CMSCon);
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

    public List<NumberGenerationReportBean> getNumberGenerationReportDetails(NumberGenerationReportBean searchBean) throws Exception {
        try {

            numberGenerationReportPersistance = new NumberGenerationReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            numbergenerationReportBeanList = numberGenerationReportPersistance.getNumberGenerationReportDetails(CMSCon, searchBean);
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
        return numbergenerationReportBeanList;
    }

    public void insertAuditValues(SystemAuditBean systemAuditBean) throws SQLException, Exception {
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
