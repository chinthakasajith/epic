/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardrequest.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.persistance.BulkCardRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class BulkCardRequestManager {

    private Connection CMSCon;
    private SystemUserBean sysUsrBean = null;
    private SystemAuditManager sysAuditManager = null;
    private HashMap<String, String> cardDomain = null;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;   
    private BulkCardRequestPersistance bulkCdReqPer = null;     
    private List<CurrencyBean> currency = null;
    private List<BulkCardRequestBean> reqList = null;

    public HashMap<String, String> getCardDomains() throws Exception {
        try {

            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardDomain = bulkCdReqPer.getCardDomains(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardDomain;
    }

    public HashMap<String, String> getBranchNames() throws Exception {
        try {

            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bnkBranches = bulkCdReqPer.getBranchNames(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return bnkBranches;
    }
    
    public String getBranchDes(String branchcode) throws Exception {
        
        String branchdes="";
        try {

            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            branchdes = bulkCdReqPer.getBranchDes(CMSCon, branchcode);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return branchdes;
    }

    public SystemUserBean getUserDetails(String username) throws Exception {
        try {

            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysUsrBean = bulkCdReqPer.getUserDetails(CMSCon, username);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return sysUsrBean;
    }

    public HashMap<String, String> getCardProducts(String cdType, String cdDomain) throws Exception {
        try {
            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cdProduct = bulkCdReqPer.getCardProducts(CMSCon, cdType, cdDomain);

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
        return cdProduct;
    }

    public List<CurrencyBean> getCurrency(String cdProduct) throws Exception {
        try {
            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currency = bulkCdReqPer.getCurrency(CMSCon, cdProduct);

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
        return currency;
    }

    public HashMap<String, String> getProductionMode(String currency, String product, String type) throws Exception {
        try {
            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            productModes = bulkCdReqPer.getProductionMode(CMSCon, currency, product, type);

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
        return productModes;
    }

    public List<BulkCardRequestBean> getAllBulkCdReq(SystemUserBean sysBean) throws Exception {
        try {

            bulkCdReqPer = new BulkCardRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            reqList = bulkCdReqPer.getAllBulkCardRequests(CMSCon,sysBean);
            CMSCon.commit();

        } catch (Exception ex) {

            CMSCon.rollback();
            throw ex;

        } finally {

            DBConnection.dbConnectionClose(CMSCon);
        }
        return reqList;
    }

    public Boolean insertNewBulkCdReq(BulkCardRequestBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        String batchId = "";
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            Boolean success2 = false;
            int success1 = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCdReqPer = new BulkCardRequestPersistance();
            success2 = bulkCdReqPer.insertNewBulkCdReq(bean, CMSCon);
            if (success2) {
                batchId = bulkCdReqPer.getBatchId(CMSCon, systemAuditBean.getUserName());
                systemAuditBean.setUniqueId(batchId);
                systemAuditBean.setDescription("Add Bulk Card Request. Batch Id: '" + batchId + "' by: " + systemAuditBean.getUserName());
            }
            sysAuditManager = new SystemAuditManager();
            success1 = sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            if (success2 && success1 == 1) {
                success = true;
            }
            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public boolean deleteBulkCdReq(String questionNo, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCdReqPer = new BulkCardRequestPersistance();
            success = bulkCdReqPer.deleteBulkCdReq(CMSCon, questionNo);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean updateBulkCdReq(BulkCardRequestBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCdReqPer = new BulkCardRequestPersistance();
            success = bulkCdReqPer.updateBulkCdReq(bean, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public Boolean confirmReceived(BulkCardRequestBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCdReqPer = new BulkCardRequestPersistance();
            success = bulkCdReqPer.confirmReceived(bean, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
}
