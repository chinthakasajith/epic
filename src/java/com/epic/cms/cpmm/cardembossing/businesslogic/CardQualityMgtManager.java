/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.cardembossing.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.cpmm.cardembossing.bean.CardQualityMgtBean;
import com.epic.cms.cpmm.cardembossing.persistance.CardQualityMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author nalin
 */
public class CardQualityMgtManager {

    private CardQualityMgtPersistance qualityPerr = null;
    private CardQualityMgtBean cardBean = null;
    private Connection CMSCon = null;
    private SystemAuditManager sysAuditManager;

    public CardQualityMgtBean getCardDetails(String cardNumber, SystemAuditBean systemAuditBean) throws Exception {
        try {
            cardBean = new CardQualityMgtBean();
            qualityPerr = new CardQualityMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardBean = qualityPerr.getCardDetails(CMSCon, cardNumber);
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
        return cardBean;
    }
    
       public CardQualityMgtBean getBulkCardDetails(String cardNumber) throws Exception {
        try {
            cardBean = new CardQualityMgtBean();
            qualityPerr = new CardQualityMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardBean = qualityPerr.getBulkCardDetails(CMSCon, cardNumber);

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
        return cardBean;
    }

    public boolean failCardQuality(CardQualityMgtBean cardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean qualityFail = false;
        boolean successInsert = false;
        try {
            
            qualityPerr = new CardQualityMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);


            qualityFail = qualityPerr.failCardQuality(CMSCon, cardBean);

            if (qualityFail) {
                successInsert = qualityPerr.insertQualityFailDetail(CMSCon, cardBean);
            }
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();

        } catch (Exception ex) {
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
        return successInsert;
    }
}
