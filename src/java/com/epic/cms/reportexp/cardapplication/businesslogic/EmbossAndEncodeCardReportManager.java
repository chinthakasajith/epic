/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.reportexp.cardapplication.bean.EmbossEncodeCardBean;
import com.epic.cms.reportexp.cardapplication.persistance.EmbossAndEncodeCardReportPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class EmbossAndEncodeCardReportManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private EmbossAndEncodeCardReportPersistance eePer = null;
    private HashMap<String, String> cardProductList = null;
    private HashMap<String, String> cardTypeList = null;
    private List<EmbossEncodeCardBean> searchList = null;
    private List<String> userList;
    private HashMap<String, String> cdProduct = null;

    public HashMap<String, String> getCardTypes() throws Exception {
        try {
            eePer = new EmbossAndEncodeCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTypeList = eePer.getCardTypes(CMSCon);
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
        return cardTypeList;
    }

    public HashMap<String, String> getCardProducts() throws Exception {
        try {
            eePer = new EmbossAndEncodeCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = eePer.getCardProducts(CMSCon);
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
        return cardProductList;
    }

    public List<String> getEmbossUser() throws Exception {
        try {
            eePer = new EmbossAndEncodeCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userList = eePer.getEmbossUser(CMSCon);
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

    public List<EmbossEncodeCardBean> searchEmbossEncodeCardReportDetails(EmbossEncodeCardBean inputBean) throws SQLException, Exception {
        try {
            eePer = new EmbossAndEncodeCardReportPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = eePer.searchEmbossEncodeCardReportDetails(CMSCon, inputBean);
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

    public HashMap<String, String> getCardProducts(String cdType, String cdDomain) throws Exception {
        try {
            eePer = new EmbossAndEncodeCardReportPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cdProduct = eePer.getCardProducts(CMSCon, cdType, cdDomain);

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
