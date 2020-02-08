/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationAssignBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.DocumentUploadBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.ApplicationVerificationBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedApplicantDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedEstDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.CorporateVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitCheckBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.PreviousApplicationDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SearchDocumentVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SignatureUploadedBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerificationPointsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import com.epic.cms.camm.applicationproccessing.documentverify.persistance.DocumentVerifyPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class DocumentVerifyManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private DocumentVerifyPersistance documentVerifyPersist;
    private List<ApplicationAssignBean> searchList = null;
    private VerifyApplicantDetailBean verifyCusBean = null;
    private DebitCheckBean debitBean = null;
    private CheckedApplicantDetailsBean bankapplicationVerifyBean = null;
    private List<PreviousApplicationDetailsBean> previousDetailsBean = null;
    private List<VerificationPointsBean> verificationPointsBeanList = null;
    private List<DocumentUploadBean> uploadedDocumentTypeList = null;
    private CheckedApplicantDetailsBean checkedApplicationBean = null;
    private ApplicationAssignPersistance appAssignPersist;
    private SignatureUploadedBean signBean;
    private CheckedEstDetailsBean checkedBean = null;
    private CorporateVerifyBean verifyBean = null;
    private DocumentVerifyPersistance verifyPersis = null;
    private CheckedEstDetailsBean corporateEstVerifyBean = null;
    private ApplicationVerificationBean applicationVerificationBean;

    public List<ApplicationAssignBean> getAllDocumentVerifyList(SearchDocumentVerifyBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
//////            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = documentVerifyPersist.documentVerifySearch(CMSCon, searchBean);
//////            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public List<ApplicationAssignBean> getAllDebitVerifyList(SearchDocumentVerifyBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
//////            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = documentVerifyPersist.getAllDebitVerifyList(CMSCon, searchBean);
//////            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public List<ApplicationAssignBean> getAllBulkVerifyList(SearchDocumentVerifyBean searchBean) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = documentVerifyPersist.getAllBulkVerifyList(CMSCon, searchBean);
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

    public VerifyApplicantDetailBean getAllVerifyCustomerDetails(String applicationId,String applicationTypeCode) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verifyCusBean = documentVerifyPersist.getAllVerifyCustomerDetails(CMSCon, applicationId,applicationTypeCode);
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
        return verifyCusBean;
    }

    public DebitCheckBean getDebitDetailBean(String applicationId) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            debitBean = documentVerifyPersist.getDebitDetailBean(CMSCon, applicationId);
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
        return debitBean;
    }

    public CheckedApplicantDetailsBean getAllPreviousCheckedDetails(String applicationId) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            checkedApplicationBean = documentVerifyPersist.getAllPreviousCheckedDetails(CMSCon, applicationId);
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

    public List<PreviousApplicationDetailsBean> getPreviousCustomerDetails(String identificationNo) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            previousDetailsBean = documentVerifyPersist.getPreviousCustomerDetails(CMSCon, identificationNo);

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


        return previousDetailsBean;
    }

    public List<VerificationPointsBean> getVerificationPoints(String cardCategoryCode ) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verificationPointsBeanList = documentVerifyPersist.getVerificationPoints(CMSCon,cardCategoryCode);

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


        return verificationPointsBeanList;
    }

    public List<DocumentUploadBean> getuploadedDocumentsType(String applicationId) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            uploadedDocumentTypeList = documentVerifyPersist.getuploadedDocumentsType(CMSCon, applicationId);

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


        return uploadedDocumentTypeList;
    }

    public SignatureUploadedBean getuploadedSignatures(String applicationId) throws Exception {
        try {

            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            signBean = documentVerifyPersist.getuploadedSignatures(CMSCon, applicationId);

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


        return signBean;
    }

    public List<ApplicationAssignBean> getCorporateCardVerifyList(SearchDocumentVerifyBean searchBean, SystemAuditBean systemAuditBean) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            //////sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = documentVerifyPersist.corporateCardSearch(CMSCon, searchBean);
            //////sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

    public boolean updateCardApplicationCrib(String applicationId, String cribFileName, String user, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.updateCardApplicationCrib(CMSCon, applicationId, cribFileName, user, new Date());
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

    public boolean insertVerifyDetailsOfApplication(CheckedApplicantDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            documentVerifyPersist = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.insertVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

    public boolean insertDebitVerifyDetails(DebitVerifyBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            documentVerifyPersist = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.insertDebitVerifyDetails(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateDebitCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

    public boolean updateVerifyDetailsOfApplication(CheckedApplicantDetailsBean checkedApplicantBean, ApplicationHistoryBean historyBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            documentVerifyPersist = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.updateVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

    public boolean updateDebitVerifyDetails(DebitVerifyBean checkedApplicantBean, ApplicationHistoryBean historyBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            documentVerifyPersist = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.updateDebitVerifyDetails(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateDebitCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

    public boolean isExistCardApplication(String applicationId) throws Exception {
        boolean flag = false;

        try {

            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.isExistCardApplication(CMSCon, applicationId);

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

    public boolean isRejectedCardApplication(String applicationId) throws Exception {
        boolean flag = false;

        try {

            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.isRejectedCardApplication(CMSCon, applicationId);

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

    public String isCribFileUpload(String applicationId) throws Exception {
        String cribFile = null;

        try {

            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cribFile = documentVerifyPersist.isCribFileUpload(CMSCon, applicationId);

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
        return cribFile;
    }

    public CheckedApplicantDetailsBean getBankApplicationVerifyDetails(String cardType) throws Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankapplicationVerifyBean = documentVerifyPersist.getBankApplicationVerifyDetails(CMSCon, cardType);
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


        return bankapplicationVerifyBean;
    }

    ///////////////////////////////// Corporate Establishment /////////////////////////////////////////
    public CorporateVerifyBean getAllEstablishmentDetails(String corporateAppId) throws Exception {
        try {
            verifyPersis = new DocumentVerifyPersistance();
            verifyBean = new CorporateVerifyBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verifyBean = verifyPersis.getAllEstablishmentDetails(CMSCon, corporateAppId);
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


        return verifyBean;
    }

    public List<VerificationPointsBean> getCorporateVerificationPoints(String cardCategoryCode) throws Exception {
        try {
            verifyPersis = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verificationPointsBeanList = verifyPersis.getCorporateVerificationPoints(CMSCon,cardCategoryCode);

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


        return verificationPointsBeanList;
    }

    public CheckedEstDetailsBean getPreviousEstCheckedDetails(String applicationId) throws Exception {
        try {
            verifyPersis = new DocumentVerifyPersistance();
            checkedBean = new CheckedEstDetailsBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            checkedBean = verifyPersis.getPreviousEstCheckedDetails(CMSCon, applicationId);
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
        return checkedBean;
    }

    public CheckedEstDetailsBean getCorporateBankApplicationVerifyDetails() throws Exception {
        try {
            verifyPersis = new DocumentVerifyPersistance();
            corporateEstVerifyBean = new CheckedEstDetailsBean();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            corporateEstVerifyBean = verifyPersis.getCorporateBankApplicationVerifyDetails(CMSCon);
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
        return corporateEstVerifyBean;
    }
    
    public boolean updateCorporateEstVerifyDetailsOfApplication(CheckedEstDetailsBean checkedApplicantBean, ApplicationHistoryBean historyBean, String applicationStatus, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            verifyPersis = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.updateCorporateEstVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateCorporateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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
    
    public boolean insertCorporateEstVerifyDetailsOfApplication(CheckedEstDetailsBean checkedApplicantBean, String applicationStatus, ApplicationHistoryBean historyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            sysAuditManager = new SystemAuditManager();
            verifyPersis = new DocumentVerifyPersistance();
            appAssignPersist = new ApplicationAssignPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            flag = documentVerifyPersist.insertCorporateEstVerifyDetailsOfApplication(CMSCon, checkedApplicantBean);
            flag = documentVerifyPersist.updateCorporateCardApplicationStatus(CMSCon, checkedApplicantBean, applicationStatus);
            flag = appAssignPersist.insertApplicationHistory(CMSCon, historyBean);
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

    public ApplicationVerificationBean getApplicantVerificationDetails(String applicationId) throws SQLException, Exception {
        try {
            documentVerifyPersist = new DocumentVerifyPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            applicationVerificationBean = documentVerifyPersist.getApplicantVerificationDetails(CMSCon, applicationId);
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
        return applicationVerificationBean;
    }
}
