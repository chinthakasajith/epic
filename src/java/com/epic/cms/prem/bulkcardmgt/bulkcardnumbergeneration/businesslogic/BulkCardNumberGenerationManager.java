/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.persistance.NumberGenarationPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.bean.BulkCardNumberFormatBean;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.persistance.BulkCardNumberGenerationPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class BulkCardNumberGenerationManager {
    
    private Connection CMSCon;
    private Connection OnlineCon;
    private SystemAuditManager sysAuditManager;
    private List<BulkCardRequestBean> cardList = null;
    private BulkCardRequestBean numberGenBean = null;
    private BulkCardNumberGenerationPersistance numberGenper = null;
    private NumberGenarationPersistance persistance = null;
    private BulkCardNumberFormatBean numFormatBean = null;
    private CardTempBean cardTempBean = null;
    private AccountTempBean accountTempBean = null;
    private List<BulkCardRequestBean> batchList = null;
    private List<String> cardKeyList = null;
    
    public List<BulkCardRequestBean> searchBulkCard(BulkCardRequestBean numberGenBean) throws Exception {
        try {
            cardList = new ArrayList<BulkCardRequestBean>();
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            cardList = numberGenper.searchBulkCard(CMSCon, numberGenBean);
            
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
        
        return cardList;
    }
    
    public BulkCardRequestBean getBulkCardBatchDetails(String batchID) throws Exception {
        
        try {
            numberGenBean = new BulkCardRequestBean();
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            numberGenBean = numberGenper.getBulkCardBatchDetails(CMSCon, batchID);
            
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
        return numberGenBean;
    }
    
    public BulkCardNumberFormatBean getNumberFormateBinNumber(String binNumber) throws Exception {
        
        try {
            numFormatBean = new BulkCardNumberFormatBean();
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            numFormatBean = numberGenper.getNumberFormateBinNumber(CMSCon, binNumber);
            
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
        
        
        return numFormatBean;
    }
    
    public boolean insertIntoCardTables(BulkCardRequestBean numGenBean, CardBean cardBean, OnlineCardTableBean onlineBean,
            String bin, String sequence) throws Exception {
        boolean isAdd = false;
        try {
            
            
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);
            
            int all = 1;

//            int r = numberGenper.insertIntoCardAccountTable(CMSCon, cardAccBean);
//            if (r == 0) {
//                all = 0;
//            }

            int s = numberGenper.insertIntoCardTable(CMSCon, cardBean);
            if (s == 0) {
                all = 0;
            }
            
            int t = numberGenper.insertIntoOnlineCardTable(OnlineCon, onlineBean);
            if (t == 0) {
                all = 0;
            }

//            int v = numberGenper.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
//            if (v == 0) {
//                all = 0;
//            }

            int u = numberGenper.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE);
            System.out.println("u ="+u);
            if (u == 0) {
                all = 0;
            }
            
            int v = numberGenper.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
            if (v == 0) {
                all = 0;
            }
            
            if (all == 1) {
                isAdd = true;
            } else {
                throw new Exception();
            }

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            
            CMSCon.commit();
            OnlineCon.commit();
            
        } catch (Exception ex) {
            CMSCon.rollback();
            OnlineCon.rollback();
            isAdd = false;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }
        return isAdd;
    }
    
    public boolean setNumberGenerationStatus(String batchID, String status, String user) throws Exception {
        boolean isSet = false;
        try {
            
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            
            CMSCon.setAutoCommit(false);
            
            isSet = numberGenper.setNumberGenerationStatus(CMSCon, batchID, status, user);
            
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
        
        return isSet;
        
    }
    
    public AccountTempBean getAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTempBean = numberGenper.getAccountTemplateDetails(CMSCon, accountTemCode);
            
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
        return accountTempBean;
        
    }
    
    public CardTempBean getCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTempBean = numberGenper.getCardTemplateDetails(CMSCon, cardTemCode);
            
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
        return cardTempBean;
        
    }
    
    public BulkCardRequestBean getBulkCardBatch(String batchID) throws Exception {
        try {
            numberGenBean = new BulkCardRequestBean();
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            numberGenBean = numberGenper.getBulkCardBatch(CMSCon, batchID);
            
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
        
        return numberGenBean;
    }
    
    public String getOnlineCardDomain(String cardDomin) throws Exception {
        String cardDominId = null;
        try {
            
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            cardDominId = numberGenper.getOnlineCardDomain(CMSCon, cardDomin);
            
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
        
        return cardDominId;
    }
    
    public String getCardKey(String bin, String product,String productionMode) throws Exception {
        String cardKey = null;
        try {
            
            numberGenper = new BulkCardNumberGenerationPersistance();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            
            cardKeyList = new ArrayList<String>();
            
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);
            
            cardKeyList = numberGenper.getCardKeyList(CMSCon, bin, product);
            
            cardKey = numberGenper.getCardKey(OnlineCon, cardKeyList, productionMode);
            
            
            CMSCon.commit();
            OnlineCon.commit();
            
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                OnlineCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }
        
        return cardKey;
    }
}
