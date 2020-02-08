/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CalculateCreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.PrioritylevelBean;
import com.epic.cms.camm.applicationconfig.creditscore.persistance.CalculateCreditScorePersistence;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ayesh
 */
public class CalculateCreditScoreManager {

    private static CalculateCreditScoreManager setInstance;
    private SystemAuditManager sysAuditManager;
    private CalculateCreditScorePersistence perObj;
    private Connection CMSCon;

    /**
     * get calculate credit score manager instance
     * @return 
     */
    public static synchronized CalculateCreditScoreManager getInstance() {
        if (setInstance == null) {
            setInstance = new CalculateCreditScoreManager();
        }
        return setInstance;
    }

    /**
     * get all priority level and it code
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PrioritylevelBean> getPriorityLevel() throws SQLException, Exception {
        try {
            List<PrioritylevelBean> ruleList = null;
            perObj = new CalculateCreditScorePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            ruleList = perObj.getpriorityLevel(CMSCon);

            CMSCon.commit();
            return ruleList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * get search application list from database
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CalculateCreditScoreBean> getSearchList(CalculateCreditScoreBean bean) throws SQLException, Exception {
        try {
            List<CalculateCreditScoreBean> ruleList = null;
            perObj = new CalculateCreditScorePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            ruleList = perObj.getSeachAppList(CMSCon, bean);
            //

            CMSCon.commit();
            return ruleList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * update calculated credit score
     * @param appID
     * @param scoreVal
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateCreditScoreValue(String appID, int scoreVal,ApplicationHistoryBean historyBean) throws SQLException, Exception {
        try {
            int row = 0;

            perObj = new CalculateCreditScorePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();
            
            int creditRow = perObj.updateCreditValue(CMSCon, appID, scoreVal);
            int statusRow = perObj.updateApplicaionStatus(CMSCon, appID);
            
            history.insertApplicationHistory(CMSCon, historyBean);

            row = creditRow + statusRow;
            CMSCon.commit();
            return row;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * get all available system users 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<String> getSystemUsers() throws SQLException, Exception {
        try {
            List<String> ruleList = null;
            perObj = new CalculateCreditScorePersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            ruleList = perObj.getSystemUsers(CMSCon);

            CMSCon.commit();
            return ruleList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
