/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.merchant.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.reportexp.merchant.bean.MerchantCustomerReportBean;
import com.epic.cms.reportexp.merchant.persistance.MerchantCustomerDetailsPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author admin
 */
public class MerchantCustomerDetailManager {
    
     private MerchantCustomerDetailsPersistance merchantCusDetailPersistance = null;
     private HashMap<String, String> merchantAreas = null;
     private Connection CMSCon;
     private List<StatusBean> statusList = null;
     private Connection cmsCon;
     private List<MerchantCustomerReportBean> summeryList = null;
     private SystemAuditManager sysAuditManager;
     
     public HashMap<String, String> getAreas() throws Exception{
        try {

            merchantCusDetailPersistance = new MerchantCustomerDetailsPersistance();
            merchantAreas = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            merchantAreas = merchantCusDetailPersistance.getAreas(CMSCon);
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
        return merchantAreas;
    }

    public List<StatusBean> getStatus() throws Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            merchantCusDetailPersistance = new MerchantCustomerDetailsPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = merchantCusDetailPersistance.getStatustList(cmsCon);

            cmsCon.commit();
            return statusList;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<MerchantCustomerReportBean> searchMerchantCusReport(MerchantCustomerReportBean summeryBean) throws Exception {
        try {
            merchantCusDetailPersistance = new MerchantCustomerDetailsPersistance();
            summeryList = new ArrayList<MerchantCustomerReportBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            summeryList = merchantCusDetailPersistance.searchMerchantCusReport(CMSCon, summeryBean);

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
        return summeryList;
    }
    
    public void setAuditTrace(SystemAuditBean systemAuditBean) throws SQLException, Exception {

        try {
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                ex.printStackTrace();
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

    }
   
}
