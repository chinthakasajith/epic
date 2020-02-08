/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.debitcardtemplate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.cardProductsBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.persistance.DebitCardTemplateMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class DebitCardTemplateMgtManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private DebitCardTemplateMgtPersistance debitCardPersist;
    private List<DebitCardTemplateBean> searchList = null;
    private DebitCardTemplateBean debitCardBean;
    private HashMap<String, String> debitAccountTemplateList = null;
    //modifying
    private HashMap<String, String> feeProfiles = null;
    //private HashMap<String, String> cproducts = null;
    private List<cardProductsBean> cproducts = null;
    private HashMap<String, String> riskProfiles = null;
    private HashMap<String, String> transactionProfiles = null;
    private DebitCardTemplateBean tempBean;

    public List<DebitCardTemplateBean> getAllDebitCardSearchList() throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchList = debitCardPersist.generalDebitCardTemplateSearch(CMSCon);
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
        return searchList;
    }

    public boolean deleteDebitCardTemplate(String templateCode, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = debitCardPersist.deleteDebitCardTemplate(CMSCon, templateCode);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return success;
    }

    public boolean updateDebitCardTemplate(DebitCardTemplateBean debitCardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = debitCardPersist.updateDebitCardTemplate(CMSCon, debitCardBean);
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

    public boolean insertDebitCardTemplate(DebitCardTemplateBean debitBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = debitCardPersist.insertDebitCardTemplate(CMSCon, debitBean);
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

    public HashMap<String, String> getAllDebitAccountTemplateList() throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            debitAccountTemplateList = debitCardPersist.getAllDebitAccountTemplates(CMSCon);
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
        return debitAccountTemplateList;
    }

    public DebitCardTemplateBean getAllDebitDetailsAccountTemplate(DebitCardTemplateBean debitBean) throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            debitCardBean = debitCardPersist.getAllDebitDetailsAccountTemplate(CMSCon, debitBean);
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
        return debitCardBean;
    }

    public HashMap<String, String> getFeeProfiles() throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            feeProfiles = debitCardPersist.getFeeProfiles(CMSCon);
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
        return feeProfiles;
    }

    public List<cardProductsBean> getCardProducts(String cdomain, String ctype) throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cproducts = debitCardPersist.getCardProducts(CMSCon, cdomain, ctype);
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
        return cproducts;
    }

    public DebitCardTemplateBean getDomainAndType(String accTemplte) throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            tempBean = debitCardPersist.getDomainAndType(CMSCon, accTemplte);
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
        return tempBean;
    }

    public HashMap<String, String> getTransactionProfiles() throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            transactionProfiles = debitCardPersist.getTransactionProfiles(CMSCon);
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
        return transactionProfiles;
    }

    public HashMap<String, String> getRiskProfiles() throws Exception {
        try {
            debitCardPersist = new DebitCardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            riskProfiles = debitCardPersist.getCardRiskProfiles(CMSCon);
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
        return riskProfiles;
    }
}
