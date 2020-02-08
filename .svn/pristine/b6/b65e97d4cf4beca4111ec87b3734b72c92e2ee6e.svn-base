package com.epic.cms.camm.applicationproccessing.cooperate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocTypeBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocumentUploadCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.IdCoCustomerBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.VerificationCategoryCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.persistance.CorporateCustomerPersistance;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class CorporateCustomerManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private List<String> nationalityList = null;
    private List<AreaBeanCoCustomer> areaList = null;
    private CorporateCustomerPersistance persistance;
    private CardApplicationStatusBean appStatusBean = null;
    private List<DocTypeBean> documentTypeLst = null;
    private CustomerCorporateBean customerPersonalBean = null;
    private List<CustomerCorporateBean> bankDetailsList;
    private List<VerificationCategoryCorporateBean> verificationCatCoList = null;
    private HashMap<String, String> identificationList = null;
    private List<DocumentUploadCorporateBean> documentDetailsList;
    private CommonFilePathBean commonFilePathBean = null;

    public List<AreaBeanCoCustomer> getAllArealist() throws Exception {
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            areaList = persistance.getAllAreaList(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

        return areaList;
    }

    public HashMap<String, String> getAllIdentificationType() throws Exception {

        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            identificationList = persistance.getAllIdentificationType(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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
        return identificationList;
    }

    public List<String> getAllNationality() throws Exception {
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            nationalityList = persistance.getAllNationality(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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

        return nationalityList;
    }

    public IdCoCustomerBean getIdentifyDetails(String applicationId) throws SQLException, Exception {
        IdCoCustomerBean idCustBean = new IdCoCustomerBean();

        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            idCustBean = persistance.getIdentifyDetails(CMSCon, applicationId);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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
        return idCustBean;
    }

    public int insertPersonalData(CustomerCorporateBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            //CustomerCorporateBean bean = this.getAllDetailsCustomer(personalBean.getApplicationId());

            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();

            isAdd = persistance.insertPersonalRecord(CMSCon, personalBean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    private CustomerCorporateBean getAllDetailsCustomer(String applicationId) throws SQLException, Exception {
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            //customerPersonalBean = persistance.getAllDetailsCustomer(CMSCon, applicationId);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception ex2) {
                throw ex2;
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


        return customerPersonalBean;
    }

    public int insertPersonalBankData(CustomerCorporateBean personalBean, SystemAuditBean systemAuditBean) throws Exception {
        int isAdd;
        try {
            //CustomerCorporateBean bean = this.getAllDetailsCustomer(personalBean.getApplicationId());

            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager = new SystemAuditManager();

            isAdd = persistance.insertPersonalBankRecord(CMSCon, personalBean);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (isAdd == 1) {
                persistance.updateCoCustomerCardApplicationStatus(personalBean.getApplicationId(), StatusVarList.APP_FILLING_COMPLETE, CMSCon);
            }

            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return isAdd;
    }

    /*
    public List<CardBankDetailsBean> getCardBankDetailsDetails(String applicationId) throws Exception {
    bankDetailsList = new ArrayList<CardBankDetailsBean>();
    try {
    checkingPersis = new AppilcationCheckingPersistance();
    CMSCon = DBConnection.getConnection();
    CMSCon.setAutoCommit(false);
    bankDetailsList = checkingPersis.getBankDetails(CMSCon, applicationId);
    
    
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
    return bankDetailsList;
    }
     */
    public List<CustomerCorporateBean> getCardBankDetailsDetails() throws Exception {
        bankDetailsList = new ArrayList<CustomerCorporateBean>();
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bankDetailsList = persistance.getBankDetails(CMSCon);


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
        return bankDetailsList;
    }

    public List<VerificationCategoryCorporateBean> getVerificationCatCoList() throws Exception {
        try {

            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            verificationCatCoList = persistance.getVerificationCatCoList(CMSCon);
            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                try {
                    CMSCon.rollback();
                    throw ex;
                } catch (SQLException e) {
                    throw e;
                }
            } catch (Exception e) {
                try {
                    CMSCon.rollback();
                    throw ex;
                } catch (Exception e1) {
                    throw e1;
                }
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return verificationCatCoList;
    }

    public List<DocTypeBean> changeDocTypeList(String docTypeList) throws SQLException, Exception {
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            documentTypeLst = persistance.getDocTypeList(CMSCon, docTypeList);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                try {
                    CMSCon.rollback();
                    throw ex;
                } catch (SQLException e) {
                    throw e;
                }
            } catch (Exception e) {
                try {
                    CMSCon.rollback();
                    throw ex;
                } catch (Exception e1) {
                    throw e1;
                }
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }

        return documentTypeLst;
    }

    public List<DocumentUploadCorporateBean> getAllDocumentDetails(String applicationId) throws Exception {
        documentDetailsList = new ArrayList<DocumentUploadCorporateBean>();
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            documentDetailsList = persistance.getDocumentDetails(CMSCon, applicationId);


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
        return documentDetailsList;
    }

    public CommonFilePathBean getFilePaths(String osType) throws Exception {

        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            commonFilePathBean = persistance.getFilePaths(CMSCon, osType);
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
        return commonFilePathBean;
    }

    public CardApplicationStatusBean getAllApplicationStatus(String appliactionId) throws Exception {
        try {
            persistance = new CorporateCustomerPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            appStatusBean = persistance.getAllApplicationStatus(appliactionId, CMSCon);

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

        return appStatusBean;
    }
}
