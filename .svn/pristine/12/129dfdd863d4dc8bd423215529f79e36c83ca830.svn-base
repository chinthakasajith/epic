/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedSupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyAccountDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.camm.applicationproccessing.documentverify.persistance.SupplementaryVerifyPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class SupplementaryVerifyManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private SupplementaryVerifyPersistance supplementaryPersistance;
    private SupplementaryDetailsBean supplementaryBean = null;
    private VerifyApplicantDetailBean mainBean = null;
    private DebitVerifyBean debitVerifyBean = null;
    private List<VerifyAccountDetailsBean> accoutBeanLst = null;
    private CheckedSupplementaryDetailsBean checkedApplicationBean = null;

    public SupplementaryDetailsBean getAllSupplementaryDetails(String applicationId) throws Exception {
        try {
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            supplementaryBean = supplementaryPersistance.getAllSupplementaryDetails(CMSCon, applicationId);
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
        return supplementaryBean;
    }

    public VerifyApplicantDetailBean getAllMainDetails(String applicationId) throws Exception {
        try {
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            mainBean = supplementaryPersistance.getAllMainDetails(CMSCon, applicationId);
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
        return mainBean;
    }

    public List<VerifyAccountDetailsBean> getAllAccountDetails(String applicationId) throws Exception {
        try {
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            accoutBeanLst = supplementaryPersistance.getAllAccountDetails(CMSCon, applicationId);
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
        return accoutBeanLst;
    }

    public DebitVerifyBean getAllDebitVerifyDetails(String applicationId) throws Exception {
        try {
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            debitVerifyBean = supplementaryPersistance.getAllDebitVerifyDetails(CMSCon, applicationId);
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
        return debitVerifyBean;
    }

    public boolean insertVerifyDetailsOfApplication(CheckedSupplementaryDetailsBean checkedApplicantBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = supplementaryPersistance.insertVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = supplementaryPersistance.updateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }

    public boolean updateVerifyDetailsOfApplication(CheckedSupplementaryDetailsBean checkedApplicantBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = supplementaryPersistance.updateVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = supplementaryPersistance.updateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return flag;
    }

    public CheckedSupplementaryDetailsBean getAllPreviousCheckedDetails(String applicationId) throws Exception {
        try {
            supplementaryPersistance = new SupplementaryVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            checkedApplicationBean = supplementaryPersistance.getAllPreviousCheckedDetails(CMSCon, applicationId);
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
        return checkedApplicationBean;
    }
}
