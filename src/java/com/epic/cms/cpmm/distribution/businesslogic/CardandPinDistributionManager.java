/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.distribution.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.cpmm.distribution.bean.DistributionBean;
import com.epic.cms.cpmm.distribution.persistance.CardandPinDistributionPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CardandPinDistributionManager {

    private List<DistributionBean> distList = null;
    private CardandPinDistributionPersistance cardPerr = null;
    private SystemAuditManager sysAuditManager = null;
    private Connection CMSCon = null;

    public List<DistributionBean> searchCardDistributionDetails(DistributionBean distBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            distList = new ArrayList<DistributionBean>();
            sysAuditManager = new SystemAuditManager();
            cardPerr = new CardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            distList = cardPerr.searchCardDistributionDetails(distBean, CMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

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
        return distList;
    }

    public List<DistributionBean> searchPinDistributionDetails(DistributionBean distBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            distList = new ArrayList<DistributionBean>();
            sysAuditManager = new SystemAuditManager();
            cardPerr = new CardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            distList = cardPerr.searchPinDistributionDetails(distBean, CMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

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
        return distList;
    }

    public boolean proceedCardDistribution(String[] cardList, String[] appIDList,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            cardPerr = new CardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = cardPerr.proceedCardDistribution(CMSCon, cardList, systemAuditBean.getUserName());
            if (success) {

                cardPerr.updateCardHistory(CMSCon, appIDList, systemAuditBean.getUserName());
            }

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

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
        return success;
    }

    public boolean proceedPinDistribution(String[] pinList, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            sysAuditManager = new SystemAuditManager();
            cardPerr = new CardandPinDistributionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            success = cardPerr.proceedPinDistribution(CMSCon, pinList, systemAuditBean.getUserName());

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

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
        return success;
    }
}
