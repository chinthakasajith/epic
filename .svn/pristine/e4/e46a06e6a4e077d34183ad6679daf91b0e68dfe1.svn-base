/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.cardtemplate.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.admin.templatemgt.cardtemplate.persistance.CardTemplateMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardTemplateMgtManager {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardTemplateMgtPersistance cardPersist;
    private List<CardTemplateBean> searchList = null;
    private CardTemplateBean cardTemplateBean = null;
    private List<ServiceCodeBean> serviceList = null;
    private List<CardProductBean> cardProductBeanLst = null;
    private List<CurrencyBean> currencyList = null;

    public List<CardTemplateBean> getAllCardDomainSearchList() throws Exception {
        try {
            cardPersist = new CardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            searchList = cardPersist.generalCardTemplateSearch(CMSCon);
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

    public List<ServiceCodeBean> getActiveServiceCode() throws Exception {
        try {
            serviceList = new ArrayList<ServiceCodeBean>();
            cardPersist = new CardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            serviceList = cardPersist.getActiveServiceCode(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return serviceList;
    }

    public boolean deleteCardTemplate(String templateCode, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {
            cardPersist = new CardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = cardPersist.deleteCardTemplate(CMSCon, templateCode);
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

    public boolean insertCardTemplate(CardTemplateBean cardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            cardPersist = new CardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardPersist.insertCardTemplate(CMSCon, cardBean);
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

    public boolean updateCardDomain(CardTemplateBean cardBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;

        try {

            cardPersist = new CardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardPersist.updateCardTemplate(CMSCon, cardBean);
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

    public CardTemplateBean getAllDetailsAccountTemplate(CardTemplateBean cardBean) throws Exception {
        try {
            cardPersist = new CardTemplateMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTemplateBean = cardPersist.getAllDetailsAccountTemplate(CMSCon, cardBean);
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
        return cardTemplateBean;
    }

    public List<CardProductBean> getCardProductList(String cardType) throws Exception {
        try {
            cardPersist = new CardTemplateMgtPersistance();
            cardProductBeanLst = new ArrayList<CardProductBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardProductBeanLst = cardPersist.getCardProductList(CMSCon, cardType);

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
        return cardProductBeanLst;
    }

    public List<CardProductBean> getUpdateCardProductList(String productCode) throws Exception {
        try {
            cardPersist = new CardTemplateMgtPersistance();
            cardProductBeanLst = new ArrayList<CardProductBean>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardProductBeanLst = cardPersist.getUpdateCardProductList(CMSCon, productCode);

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
        return cardProductBeanLst;
    }

    public HashMap<String,String> getCurrencyList(String productCode) throws Exception {
            HashMap<String,String> currencyList = new HashMap<String,String>();
        try {
            cardPersist = new CardTemplateMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            currencyList = cardPersist.getCurrencyList(CMSCon,productCode);

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
}
