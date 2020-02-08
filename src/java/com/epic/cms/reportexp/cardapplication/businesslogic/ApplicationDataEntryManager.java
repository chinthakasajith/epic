/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDataEntryBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationSummaryBean;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationDataEntryPersistance;
import com.epic.cms.reportexp.cardapplication.persistance.ApplicationSummaryPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ApplicationDataEntryManager {

    private Connection CMSCon;
    private SystemUserBean sysUsrBean = null;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> cardDomain = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private ApplicationSummaryPersistance appSumPer = null;
    private List<CurrencyBean> currency = null;
    private List<BulkCardRequestBean> reqList = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> priorityLevelList = null;
    private List<ApplicationDataEntryBean> searchList = null;
    private ApplicationDataEntryPersistance dataEntryPer;
    List<String> userList;

    public List<String> getDataCapturingUser() throws Exception {
        try {
            dataEntryPer = new ApplicationDataEntryPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = dataEntryPer.getDataCapturingUser(CMSCon);
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
    
    public List<ApplicationDataEntryBean> getApplicationDataEntry(ApplicationDataEntryBean inputBean) throws SQLException, Exception {
        try {
            dataEntryPer = new ApplicationDataEntryPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = dataEntryPer.getApplicationDataEntry(CMSCon, inputBean);
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
