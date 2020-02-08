/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequestconfirm.persistance.BulkCardConfirmPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class BulkCardConfirmManager {

    private BulkCardConfirmPersistance bulkConfirmPer;
    private Connection CMSCon;
    private List<BulkCardRequestBean> searchList;
    private List<CardBinBean> binlist;
    private List<CardTemplateBean> cardTempList;
    private SystemAuditManager sysAuditManager;

    public List<BulkCardRequestBean> searchBulkCard(BulkCardRequestBean searchBean) throws Exception {
        try {
            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = bulkConfirmPer.searchBulkCard(CMSCon, searchBean);
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

    public List<CardBinBean> getBinList(String productionModeCode, String cardtype, String cardDomain, String currency,String product) throws Exception {
        try {
            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            binlist = bulkConfirmPer.getBinList(CMSCon, productionModeCode, cardtype, cardDomain, currency,product);


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
        return binlist;
    }

    public List<CardTemplateBean> getTempltList(String cdtype, String cddomain, String cdproduct, String currency) throws Exception {
        try {
            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTempList = bulkConfirmPer.getTempltList(CMSCon, cdtype, cddomain, cdproduct, currency);


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
        return cardTempList;
    }

    public String getUserBranch(String username) throws Exception {
        String branch = "";
        try {

            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            branch = bulkConfirmPer.getUserBranch(CMSCon, username);

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
        return branch;
    }

    public boolean updateBulkCard(BulkCardRequestBean bean, SystemAuditBean sysben) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = bulkConfirmPer.updateBulkCard(CMSCon, bean);
            sysAuditManager.insertAudit(sysben, CMSCon);
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
        return status;
    }

    public boolean rejectBulkCard(BulkCardRequestBean bean, SystemAuditBean sysben) throws Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            bulkConfirmPer = new BulkCardConfirmPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = bulkConfirmPer.rejectBulkCard(CMSCon, bean);
            sysAuditManager.insertAudit(sysben, CMSCon);
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
        return status;
    }
}
