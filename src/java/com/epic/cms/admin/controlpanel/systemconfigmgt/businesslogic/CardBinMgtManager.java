/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardDomainsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ProductionModeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CardBinMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class CardBinMgtManager {

    private Connection conToOnline;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardBinMgtPersistance cardBinMgtPersistance = null;
    private List<CardBinBean> cardBinBeanLst = null;
    private List<ProductionModeBean> productionModeList = null;
    private List<CardDomainsBean> cardDomainsList=null;
    private HashMap<String, String> currencyMap;
    
    public List<CardBinBean> getAllCardBinDetailsList() throws Exception {
        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinBeanLst = cardBinMgtPersistance.getAllCardBinDetailsList(CMSCon);
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
        return cardBinBeanLst;
    }
    // to take production mode list -------

    public List<ProductionModeBean> getProductionModeList() throws Exception {
        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            productionModeList = cardBinMgtPersistance.getProductionModeList(CMSCon);
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
        return productionModeList;
    }
    
    //getCardDomainsList
    public List<CardDomainsBean> getCardDomainsList() throws Exception {
        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardDomainsList = cardBinMgtPersistance.getCardDomainsList(CMSCon);
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
        return cardDomainsList;
    }
    // ----------

    public boolean insertCardBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardBinMgtPersistance.insertCardBin(CMSCon, cardBinBean);
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

    public boolean updateCardBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardBinMgtPersistance.updateCardBin(CMSCon, cardBinBean);
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

     public boolean deleteCardBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardBinMgtPersistance.deleteCardBin(CMSCon, cardBinBean);
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
    
    public String getProductCode(String binNum) throws Exception {

        String proCode="";

        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();            
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            proCode = cardBinMgtPersistance.getProductCode(CMSCon, binNum);            
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
        return proCode;
    }

    public boolean insertBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            flag = cardBinMgtPersistance.insertBin(CMSCon,conToOnline,cardBinBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            conToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (conToOnline != null) {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            }
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
    
    public List<CardBinBean> getAllBinDetailsList() throws Exception {
        try {
            cardBinMgtPersistance = new CardBinMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            cardBinBeanLst = cardBinMgtPersistance.getAllBinDetailsList(CMSCon,conToOnline);
            CMSCon.commit();
            conToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (conToOnline != null) {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            }
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardBinBeanLst;
    }
    
    public boolean deleteBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            flag = cardBinMgtPersistance.deleteBin(CMSCon,conToOnline,cardBinBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            conToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (conToOnline != null) {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            }
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
    
    public boolean updateBin(CardBinBean cardBinBean, SystemAuditBean systemAuditBean) throws Exception {

        boolean flag = false;

        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            flag = cardBinMgtPersistance.updateBin(CMSCon,conToOnline,cardBinBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            conToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                conToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (conToOnline != null) {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
            }
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
    
    public HashMap<String, String> getCurrency() throws Exception{
        try {

            cardBinMgtPersistance = new CardBinMgtPersistance();
            currencyMap = new HashMap<String, String>();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currencyMap = cardBinMgtPersistance.getCurrency(CMSCon);
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
        return currencyMap;
    }
}
