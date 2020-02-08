/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.carddomaintemplate.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.carddomaintemplate.bean.CardDomainBean;
import com.epic.cms.admin.templatemgt.carddomaintemplate.persistance.CardDomainMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardDomainMgtManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardDomainMgtPersistance domainpersist;
    private List<CardDomainBean> searchList = null;
    private HashMap<String, String> customerTemplateList = null;
    private HashMap<String, String> currencyList = null;
    private HashMap<String, String> productList = null;
    private HashMap<String, String> accountTemplateList = null;
    private HashMap<String, String> interestProfileList = null;
    private HashMap<String, String> cardHolderFeeProfileList = null;
    
    private HashMap<String, String> riskProfiles = null;
    

    public List<CardDomainBean> getAllCardDomainSearchList() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchList = domainpersist.generalCardDomainSearch(CMSCon);
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

    public HashMap<String, String> getAllCustomerTemplateList() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            customerTemplateList = domainpersist.getAllCustomerTemplates(CMSCon);
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
        return customerTemplateList;
    }

    public HashMap<String, String> getAllAccountTemplateList() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTemplateList = domainpersist.getAllAccountTemplates(CMSCon);
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
        return accountTemplateList;
    }

    public HashMap<String, String> getAllCurrencyList() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyList = domainpersist.getAllCurrencyList(CMSCon);
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
        return currencyList;
    }

    public HashMap<String, String> getAllProductList() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            productList = domainpersist.getAllProductList(CMSCon);
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
        return productList;
    }

    public boolean updateCardDomain(CardDomainBean domainBean ,SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            domainpersist = new CardDomainMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = domainpersist.updateCardDomain(CMSCon, domainBean);
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

    public boolean deleteCardDomain(String templateCode, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            domainpersist = new CardDomainMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = domainpersist.deleteCardDomain(CMSCon, templateCode);
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

    public boolean insertCardDomain(CardDomainBean domainBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            domainpersist = new CardDomainMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = domainpersist.insertCardDomain(CMSCon, domainBean);
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

    public HashMap<String, String> getAllInterestProfiles() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            interestProfileList = domainpersist.getAllInterestProfiles(CMSCon);
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
        return interestProfileList;
    }

    public HashMap<String, String> getAllCardHolderFeeProfiles() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardHolderFeeProfileList = domainpersist.getAllCardHolderFeeProfiles(CMSCon);
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
        return cardHolderFeeProfileList;
    }
    
    public HashMap<String, String> getRiskProfiles() throws Exception {
        try {
            domainpersist = new CardDomainMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            riskProfiles = domainpersist.getCardRiskProfiles(CMSCon);
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
