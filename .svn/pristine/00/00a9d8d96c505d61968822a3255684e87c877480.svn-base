/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.cardlimitincrement.bean.CommonCardParameterBean;
import com.epic.cms.backoffice.cardlimitincrement.bean.TempLimitIncrementBean;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.callcenter.card.persistance.PermLimitIncrementRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author badrika
 */
public class PermLimitIncrementRequestManager {

    private PermLimitIncrementRequestPersistance permIncremantPer;
    private TempLimitIncrementBean tempBean;
    private Connection CMSCon;
    private Connection OnlineCon;
    private CommonCardParameterBean limitBean;
    private SystemAuditManager sysAuditManager;
    private CallCenterMgtManager callCenterManager;

    public TempLimitIncrementBean getCardDetails(String cardNumber) throws Exception {

        try {

            permIncremantPer = new PermLimitIncrementRequestPersistance();
            tempBean = new TempLimitIncrementBean();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            OnlineCon.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            tempBean = permIncremantPer.getCardDetails(CMSCon, cardNumber);
            tempBean = permIncremantPer.getOnlineCardDetails(OnlineCon, tempBean);

            CMSCon.commit();
            OnlineCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }
        return tempBean;
    }

    public CommonCardParameterBean getLimitDetails() throws Exception {

        try {

            permIncremantPer = new PermLimitIncrementRequestPersistance();
            limitBean = new CommonCardParameterBean();
            CMSCon = DBConnection.getConnection();
            //OnlineCon = DBConnectionToOnlineDB.getConnection();

            // OnlineCon.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            limitBean = permIncremantPer.getLimitDetails(CMSCon);


            CMSCon.commit();
            //OnlineCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                // OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                // OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            //DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }
        return limitBean;
    }

    public int insertLimitIncrement(SystemAuditBean systemAuditBean, TempLimitIncrementBean tempBean,CallHistoryBean historyBean) throws Exception {

        int rowCount = -1;
        try {

            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            permIncremantPer = new PermLimitIncrementRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = permIncremantPer.insertLimitIncrement(CMSCon, tempBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            callCenterManager.insertCallHistory(historyBean, CMSCon);

            CMSCon.commit();
            return rowCount;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

    }
}
