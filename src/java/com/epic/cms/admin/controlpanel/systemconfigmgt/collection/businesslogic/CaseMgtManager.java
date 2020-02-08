/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance.CaseMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author asitha_l
 */
public class CaseMgtManager {

    private Connection cmsCon;
    private CaseMgtPersistance caseMgtPersistance;
    private SystemAuditManager sysAuditManager;

    public Map<String, String> getStatus() throws Exception {
        Map<String, String> statusMap;
        try {

            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusMap = caseMgtPersistance.getStatus(cmsCon);

            cmsCon.commit();
            return statusMap;
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

    public Map<String, String> getCurrencyTypes() throws Exception {
        Map<String, String> currencyMap;
        try {

            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            currencyMap = caseMgtPersistance.getCurrencyTypes(cmsCon);

            cmsCon.commit();
            return currencyMap;
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

    public Map<String, String> getCardStatus() throws Exception {
        Map<String, String> cardStatusMap;
        try {

            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            cardStatusMap = caseMgtPersistance.getCardStatus(cmsCon);

            cmsCon.commit();
            return cardStatusMap;
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

    public boolean insertCaseType(CaseTypeBean caseTypeBean,SystemAuditBean systemAuditBean) throws Exception {
        boolean success;
        try {
            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            success = caseMgtPersistance.insertCaseType(cmsCon, caseTypeBean);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            return success;
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

    public List<CaseTypeBean> getAllCases() throws Exception {
        List<CaseTypeBean> caseList = new ArrayList<CaseTypeBean>();
        try {
            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            caseList = caseMgtPersistance.getAllCases(cmsCon);
            cmsCon.commit();
            return caseList;
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

    public boolean deleteCase(String caseTypeCode,SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            success = caseMgtPersistance.deleteCase(cmsCon, caseTypeCode);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            return success;
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

    public boolean updateCaseType(CaseTypeBean caseTypeBean,SystemAuditBean systemAuditBean) throws Exception {
        boolean success;
        try {
            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            success = caseMgtPersistance.updateCaseType(cmsCon, caseTypeBean);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            cmsCon.commit();
            return success;
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

    public CaseTypeBean getCaseById(String caseTypeCode) throws Exception {
        CaseTypeBean caseBean = new CaseTypeBean();
        try {
            caseMgtPersistance = new CaseMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            caseBean = caseMgtPersistance.getCaseById(cmsCon, caseTypeCode);
            cmsCon.commit();
            return caseBean;
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
}
