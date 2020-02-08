/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.numbergenaration.businesslogic;

import com.epic.cms.admin.templatemgt.accounttemplate.bean.AccountTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.debitcardtemplate.bean.DebitCardTemplateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardAccountCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardApplicationBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardBinBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardMainCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardNumberFormateBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardSuplimentoryCustomerBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTempBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.CardTypeCustomerDetailsBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberFormateFieldTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationDataBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.NumberGenarationSearchBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineAccountTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCardTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.bean.OnlineCustomerTableBean;
import com.epic.cms.camm.applicationconfig.numbergenaration.persistance.NumberGenarationPersistance;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardnumbergeneration.bean.BulkCardNumberFormatBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.logs.LogFileCreator;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class NumberGenarationManager {

    private List<NumberGenarationDataBean> searchList = null;
    private List<CardNumberFormateBean> numberFormateList;
    private List<NumberFormateFieldTableBean> numberFormateFieldList;
    private CardCustomerBean cardCustomerBean = null;
    private List<CardBinBean> binList;
    private Connection CMSCon;
    private Connection OnlineCon;
    private NumberGenarationPersistance persistance;
    private CardTempBean cardTempBean = null;
    private DebitCardTemplateBean debitCardTempBean = null;
    private AccountTempBean accountTempBean = null;
    private DebitCardAccountTemplateBean debitAccountTempBean = null;
    private CustomerTempBean customerTempBean = null;

    public List<NumberGenarationDataBean> numberGenarationSearch(NumberGenarationSearchBean searchBean) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.getNumberGenarationSearch(CMSCon, searchBean);
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

    public List<CardNumberFormateBean> getNumberFormatesByCardType(String cardType) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            numberFormateList = persistance.getNumberFormatesByCardType(CMSCon, cardType);
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

        return numberFormateList;
    }

    public CardNumberFormateBean getNumberFormateDetailsFromCode(String numberFormate) throws Exception {
        CardNumberFormateBean bean = new CardNumberFormateBean();

        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = persistance.getNumberFormateDetailsFromCode(CMSCon, numberFormate);
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

        return bean;
    }

    public List<CardBinBean> getBinNumberByCardType(String cardType) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            binList = persistance.getBinNumberByCardType(CMSCon, cardType);
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

        return binList;
    }

    public List<NumberFormateFieldTableBean> getNumberFormateFieldsFromCode(String numberFormate) throws Exception {

        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            numberFormateFieldList = persistance.getNumberFormateFieldsFromCode(CMSCon, numberFormate);

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

        return numberFormateFieldList;
    }

    public CardApplicationBean getApplicationDetailsFromID(String applicationId) throws Exception {

        CardApplicationBean bean = new CardApplicationBean();
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = persistance.getApplicationDetailsFromID(CMSCon, applicationId);

            CMSCon.commit();
        } catch (SQLException ex) {
            try {
                LogFileCreator.writeErrorToNumberGenLog(ex);
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
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

        return bean;
    }

    public String getMaxCusIDFromCardCustomer() throws Exception {
        String cusId = "";
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cusId = persistance.getMaxCusIDFromCardCustomer(CMSCon);

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

        return cusId;
    }

    public boolean insertIntoCardTables(CardCustomerBean cardCustomerBean, CardTypeCustomerDetailsBean cardTypeCustomerDetailsBean, CardAccountBean cardAccBean,
            CardBean cardBean, OnlineCardTableBean onlineBean, OnlineAccountTableBean onlineAccountBean, OnlineCustomerTableBean onlineCustomerBean, boolean cardCus, String bin, String sequence, String appId, CardAccountCustomerBean cardAccountCustomerBean) throws Exception {
        boolean isAdd = false;
        try {

            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_CATEGORY_MAIN)) {
                int all = 1;

                if (cardCus == true) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }
                    
                    // add customer transaction 
                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }
                    

                    int q = persistance.insertIntoCardMainCustomerTable(CMSCon, cardTypeCustomerDetailsBean.getCardMainCusDetailBean());
                    if (q == 0) {
                        all = 0;
                    }
                }

                int r = persistance.insertIntoCardTable(CMSCon, cardBean);
                if (r == 0) {
                    all = 0;
                }

                int s = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                if (s == 0) {
                    all = 0;
                }
                
                int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                if (t == 0) {
                    all = 0;
                }
                //add card transactions
                int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                
                int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                if (v == 0) {
                    all = 0;
                }
                
                //add account transactions
                int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }
                
                

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }
                
                int w = persistance.updateCardNumberForApp(CMSCon, appId, cardBean);
                if (w == 0)
                {
                    all = 0;
                }
                

                if (cardAccountCustomerBean != null) {
                    boolean isExsit = this.isCACExsit(cardAccountCustomerBean, CMSCon);
                    if (!isExsit) {
                        int ca = persistance.insertIntoCACTable(CMSCon, cardAccountCustomerBean);
                        if (ca == 0) {
                            all = 0;
                        }
                        int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cardAccountCustomerBean);
                        if (cb == 0) {
                            all = 0;
                        }
                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }

            }

            //======================================================================================================================suplimentory
            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_CATEGORY_SUPPLEMENTARY)) {
                int all = 1;
                if (cardCus == true) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }
                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }
                    // add customer transaction
                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }
                    
                }

                int q = persistance.insertIntoCardSupCustomerTable(CMSCon, cardTypeCustomerDetailsBean.getCardSupCusDetailBean());
                if (q == 0) {
                    all = 0;
                }
                /*
                int r = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                if (r == 0) {
                    all = 0;
                }
                */
                int s = persistance.insertIntoCardTable(CMSCon, cardBean);
                if (s == 0) {
                    all = 0;
                }
                int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                if (t == 0) {
                    all = 0;
                }
                //add card transactions
                int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                
                /*
                int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                if (v == 0) {
                    all = 0;
                }
                */
                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }
                
                int w = persistance.updateCardNumberForApp(CMSCon, appId, cardBean);
                if (w == 0)
                {
                    all = 0;
                }

                if (cardAccountCustomerBean != null) {

                    boolean isExsit = this.isCACExsit(cardAccountCustomerBean, CMSCon);
                    if (!isExsit) {
                        int ca = persistance.insertIntoCACTable(CMSCon, cardAccountCustomerBean);
                        if (ca == 0) {
                            all = 0;
                        }
                        int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cardAccountCustomerBean);
                        if (cb == 0) {
                            all = 0;
                        }
                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }
            }
            //======================================================================================================================Establishment
            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_CATEGORY_ESTABLISHMENT)) {
                int all = 1;

                if (cardCus == true) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }
                    
                    // add customer transaction
                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }

                    int q = persistance.insertIntoCardEstCustomerTable(CMSCon, cardTypeCustomerDetailsBean.getCardEstablishmentCustomerDetailsBean());
                    if (q == 0) {
                        all = 0;
                    }
                }
                
                int r = persistance.insertIntoCardTable(CMSCon, cardBean);
                if (r == 0) {
                    all = 0;
                }
                
                int s = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                if (s == 0) {
                    all = 0;
                }

                int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                if (t == 0) {
                    all = 0;
                }
                
                //add card transactions
                int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                
                int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                if (v == 0) {
                    all = 0;
                }
                
                //add account transaction
                int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }
                
                int w = persistance.updateCardNumberForApp(CMSCon, appId, cardBean);
                if (w == 0)
                {
                    all = 0;
                }

                if (cardAccountCustomerBean != null) {
                    boolean isExsit = this.isCACExsit(cardAccountCustomerBean, CMSCon);
                    if (!isExsit) {
                        int ca = persistance.insertIntoCACTable(CMSCon, cardAccountCustomerBean);
                        if (ca == 0) {
                            all = 0;
                        }
                        int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cardAccountCustomerBean);
                        if (cb == 0) {
                            all = 0;
                        }
                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }
            }
            //======================================================================================================================Corporate
            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_CATEGORY_COPORATE)) {
                int all = 1;

                if (cardCus == true) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }
                    
                    // add customer transaction
                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }

                    int q = persistance.insertIntoCardCorporateCustomerTable(CMSCon, cardTypeCustomerDetailsBean.getCardCorporateCusDetailBean());
                    if (q == 0) {
                        all = 0;
                    }
                }

                int r = persistance.insertIntoCardTable(CMSCon, cardBean);
                if (r == 0) {
                    all = 0;
                }
                
                int s = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                if (s == 0) {
                    all = 0;
                }

                int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                if (t == 0) {
                    all = 0;
                }
                
                //add card transactions
                int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                
                
                int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                if (v == 0) {
                    all = 0;
                }
                
                //add account transaction 
                int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }
                
                int w = persistance.updateCardNumberForApp(CMSCon, appId, cardBean);
                if (w == 0)
                {
                    all = 0;
                }

                if (cardAccountCustomerBean != null) {
                    boolean isExsit = this.isCACExsit(cardAccountCustomerBean, CMSCon);
                    if (!isExsit) {
                        int ca = persistance.insertIntoCACTable(CMSCon, cardAccountCustomerBean);
                        if (ca == 0) {
                            all = 0;
                        }
                        int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cardAccountCustomerBean);
                        if (cb == 0) {
                            all = 0;
                        }
                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }

            }
            //======================================================================================================================Card Against FD            
            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_AGAINST_FD_CODE)) {
                int all = 1;

                if (cardCus == true) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }
                    
                    // add customer transaction
                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }

                    int q = persistance.insertIntoCardFDCustomerTable(CMSCon, cardTypeCustomerDetailsBean.getCardFDCustomerDetailsBean());
                    if (q == 0) {
                        all = 0;
                    }
                }

                int r = persistance.insertIntoCardTable(CMSCon, cardBean);
                if (r == 0) {
                    all = 0;
                }
                
                int s = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                if (s == 0) {
                    all = 0;
                }

                int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                if (t == 0) {
                    all = 0;
                }
                
                //add card transactions
                int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                
                int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                if (v == 0) {
                    all = 0;
                }
                
                //add account transactions
                int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }
                
                int w = persistance.updateCardNumberForApp(CMSCon, appId, cardBean);
                if (w == 0)
                {
                    all = 0;
                }

                if (cardAccountCustomerBean != null) {
                    boolean isExsit = this.isCACExsit(cardAccountCustomerBean, CMSCon);
                    if (!isExsit) {
                        int ca = persistance.insertIntoCACTable(CMSCon, cardAccountCustomerBean);
                        if (ca == 0) {
                            all = 0;
                        }
                        int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cardAccountCustomerBean);
                        if (cb == 0) {
                            all = 0;
                        }
                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }

            }
            
            
            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            OnlineCon.commit();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            CMSCon.rollback();
            OnlineCon.rollback();
            isAdd = false;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }

        return isAdd;
    }

    public boolean insertIntoDebitCardTables(CardCustomerBean cardCustomerBean, CardCustomerBean cardCustomerBean2, CardAccountBean cardAccBean, CardAccountBean cardAccBean2, CardAccountBean cardAccBean4, CardAccountBean cardAccLoyalityBean, CardAccountBean cardAccLoyalityBean2,
            CardBean cardBean, CardBean cardBean2, OnlineCardTableBean onlineBean, OnlineCardTableBean onlineBean2, OnlineAccountTableBean onlineAccountBean, OnlineAccountTableBean onlineAccountBean2, OnlineAccountTableBean onlineAccountBean4, OnlineAccountTableBean onlineAccountLoyalityBean,
            OnlineAccountTableBean onlineAccountLoyalityBean2, OnlineCustomerTableBean onlineCustomerBean, OnlineCustomerTableBean onlineCustomerBean2, List<CardAccountCustomerBean> cacCustomer1List, boolean iscardCusExsit, boolean isJointCusExsit, boolean useCustomer1oldcard, boolean useCustomer2oldcard, String bin, String sequence, String appId) throws Exception {
        boolean isAdd = false;
        try {

            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_DEBIT_PERSONAL)) {
                int all = 1;

                if (iscardCusExsit == false) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }

                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }
                }

                if (useCustomer1oldcard == false) {
                    int s = persistance.insertIntoCardTable(CMSCon, cardBean);
                    if (s == 0) {
                        all = 0;
                    }

                    int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                    if (t == 0) {
                        all = 0;
                    }

                    int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }

                }

                if (!this.isAccountExsit(CMSCon, cardAccBean.getAccountNumber())) {
                    int r = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                    if (r == 0) {
                        all = 0;
                    }

                    int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                    if (v == 0) {
                        all = 0;
                    }
                    int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }

                }

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }

                //-----------------------------------------------------------------
                if (cardAccBean2 != null && onlineAccountBean2 != null) {
                    if (!this.isAccountExsit(CMSCon, cardAccBean2.getAccountNumber())) {
                        int a2 = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean2);
                        if (a2 == 0) {
                            all = 0;
                        }

                        int oa2 = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean2);
                        if (oa2 == 0) {
                            all = 0;
                        }

                        int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean2);
                        if (pa == 0) {
                            all = 0;
                        }

                    }
                }

                if (cardAccLoyalityBean != null && onlineAccountLoyalityBean != null) {
                    if (!this.isAccountExsit(CMSCon, cardAccLoyalityBean.getAccountNumber())) {
                        int l = persistance.insertIntoCardAccountTable(CMSCon, cardAccLoyalityBean);
                        if (l == 0) {
                            all = 0;
                        }

                        int ol = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountLoyalityBean);
                        if (ol == 0) {
                            all = 0;
                        }
                    }
                }

                if (cacCustomer1List != null) {

                    for (int cac = 0; cac < cacCustomer1List.size(); cac++) {
                        CardAccountCustomerBean getbean = new CardAccountCustomerBean();
                        boolean isExsit = this.isCACExsit(cacCustomer1List.get(cac), CMSCon);
                        if (!isExsit) {
                            int ca = persistance.insertIntoCACTable(CMSCon, cacCustomer1List.get(cac));
                            int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cacCustomer1List.get(cac));
                            if (ca == 0 || cb == 0) {
                                all = 0;
                            }
                        }

                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }

            }

            if (cardCustomerBean.getCustomerType().equals(StatusVarList.CARD_DEBIT_JOINT)) {
                int all = 1;

                if (iscardCusExsit == false) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean);
                    if (w == 0) {
                        all = 0;
                    }

                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean);
                    if (pp == 0) {
                        all = 0;
                    }
                }
                if (isJointCusExsit == false) {
                    int p = persistance.insertIntoCardCustomerTable(CMSCon, cardCustomerBean2);
                    if (p == 0) {
                        all = 0;
                    }

                    int w = persistance.insertIntoOnlineCustomerTable(OnlineCon, onlineCustomerBean2);
                    if (w == 0) {
                        all = 0;
                    }

                    int pp = persistance.insertIntoCustomerTransactionTables(CMSCon, OnlineCon, cardCustomerBean2);
                    if (pp == 0) {
                        all = 0;
                    }
                }

                if (useCustomer1oldcard == false) {
                    int s = persistance.insertIntoCardTable(CMSCon, cardBean);
                    if (s == 0) {
                        all = 0;
                    }

                    int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean);
                    if (t == 0) {
                        all = 0;
                    }

                    int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean);
                    if (txn == 0) {
                        all = 0;
                    }
                }
                if (useCustomer2oldcard == false) {
                    int s = persistance.insertIntoCardTable(CMSCon, cardBean2);
                    if (s == 0) {
                        all = 0;
                    }

                    int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineBean2);
                    if (t == 0) {
                        all = 0;
                    }

                    int txn = persistance.insertIntoCardTransactionTables(CMSCon, OnlineCon, cardBean2);
                    if (txn == 0) {
                        all = 0;
                    }
                }

                if (!this.isAccountExsit(CMSCon, cardAccBean.getAccountNumber())) {
                    int r = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean);
                    if (r == 0) {
                        all = 0;
                    }

                    int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean);
                    if (v == 0) {
                        all = 0;
                    }

                    int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean);
                    if (pa == 0) {
                        all = 0;
                    }

                }
//
//                if (!this.isAccountExsit(CMSCon, onlineAccountBean.getAccountNumber())) {
//                    
//                }

                int u = persistance.updateSequenceNumberAndStatus(CMSCon, bin, sequence, StatusVarList.APP_NUMBER_COMPLETE, appId);
                if (u == 0) {
                    all = 0;
                }

                //-----------------------------------------------------------------
                if (cardAccBean2 != null) {
                    if (!this.isAccountExsit(CMSCon, onlineAccountBean2.getAccountNumber())) {
                        int a2 = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean2);
                        if (a2 == 0) {
                            all = 0;
                        }
                        int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean2);
                        if (v == 0) {
                            all = 0;
                        }
                    }

                    int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean2);
                    if (pa == 0) {
                        all = 0;
                    }
                }
                if (cardAccBean4 != null) {
                    if (!this.isAccountExsit(CMSCon, onlineAccountBean4.getAccountNumber())) {
                        int a4 = persistance.insertIntoCardAccountTable(CMSCon, cardAccBean4);
                        if (a4 == 0) {
                            all = 0;
                        }

                        int v = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean4);
                        if (v == 0) {
                            all = 0;
                        }

                        int pa = persistance.insertIntoAccountTransactionTables(CMSCon, OnlineCon, cardAccBean4);
                        if (pa == 0) {
                            all = 0;
                        }

                    }

                }

//                if (cardAccLoyalityBean != null) {
//                    int l = persistance.insertIntoCardAccountTable(CMSCon, cardAccLoyalityBean);
//                    if (l == 0) {
//                        all = 0;
//                    }
//                }
                if (onlineAccountBean2 != null) {
                    if (!this.isAccountExsit(CMSCon, onlineAccountBean2.getAccountNumber())) {
                        int oa2 = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean2);
                        if (oa2 == 0) {
                            all = 0;
                        }
                    }
                }
                if (onlineAccountBean4 != null) {
                    if (!this.isAccountExsit(CMSCon, onlineAccountBean4.getAccountNumber())) {
                        int oa4 = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountBean4);
                        if (oa4 == 0) {
                            all = 0;
                        }
                    }
                }

//                if (onlineAccountLoyalityBean != null) {
//                    int ol = persistance.insertIntoOnlineAccountTable(OnlineCon, onlineAccountLoyalityBean);
//                    if (ol == 0) {
//                        all = 0;
//                    }
//                }
                if (cacCustomer1List != null) {

                    for (int cac = 0; cac < cacCustomer1List.size(); cac++) {
//                        CardAccountCustomerBean getbean = new CardAccountCustomerBean();
                        boolean isExsit = this.isCACExsit(cacCustomer1List.get(cac), CMSCon);
                        if (!isExsit) {
                            int ca = persistance.insertIntoCACTable(CMSCon, cacCustomer1List.get(cac));
                            int cb = persistance.insertIntoOnlineCACTable(OnlineCon, cacCustomer1List.get(cac));
                            if (ca == 0) {
                                all = 0;
                            }
                            if (cb == 0) {
                                all = 0;
                            }
                        }

                    }
                }

                if (all == 1) {
                    isAdd = true;
                } else {
                    throw new Exception();
                }

            }

            //sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            OnlineCon.commit();

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            CMSCon.rollback();
            OnlineCon.rollback();
            isAdd = false;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }

        return isAdd;
    }

    public boolean insertIntoReplaceDebitCardTables(CardBean replacecardBean, OnlineCardTableBean onlineReplaceCard) throws Exception {
        boolean isAdd = false;
        int all = 1;
        try {

            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            int s = persistance.insertIntoCardTable(CMSCon, replacecardBean);
            if (s == 0) {
                all = 0;
            }

            int t = persistance.insertIntoOnlineCardTable(OnlineCon, onlineReplaceCard);
            if (t == 0) {
                all = 0;
            }

            if (all == 1) {
                isAdd = true;
            } else {
                throw new Exception();
            }

        } catch (Exception ex) {
            LogFileCreator.writeErrorToNumberGenLog(ex);
            CMSCon.rollback();
            OnlineCon.rollback();
            isAdd = false;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(OnlineCon);
        }

        return isAdd;

    }

    public CardCustomerBean getCustomerCardDetailsById(String primaryId) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardCustomerBean = persistance.getCustomerCardDetailsById(CMSCon, primaryId);
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

        return cardCustomerBean;
    }

    public String getMaxAccnoFromCardAccount() throws Exception {
        String accno = "";
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accno = persistance.getMaxAccnoFromCardAccount(CMSCon);

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

        return accno;
    }

    public String getPrimaryAccountNumberFromCardAccont(String customerId) throws Exception {
        String accno = "";
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accno = persistance.getPrimaryAccountNumberFromCardAccont(CMSCon, customerId);

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

        return accno;
    }

    public CustomerTempBean getCustomerTemplateDetails(String customerTemCode) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            customerTempBean = persistance.getCustomerTemplateDetails(CMSCon, customerTemCode);

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
        return customerTempBean;

    }

    public AccountTempBean getAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTempBean = persistance.getAccountTemplateDetails(CMSCon, accountTemCode);

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

    public DebitCardAccountTemplateBean getDebitAccountTemplateDetails(String accountTemCode) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            debitAccountTempBean = persistance.geDebitAccountTemplateDetails(CMSCon, accountTemCode);

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
        return debitAccountTempBean;

    }

    public CardTempBean getCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTempBean = persistance.getCardTemplateDetails(CMSCon, cardTemCode);

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

    public DebitCardTemplateBean getDebitCardTemplateDetails(String cardTemCode) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            debitCardTempBean = persistance.getDebitCardTemplateDetails(CMSCon, cardTemCode);

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
        return debitCardTempBean;

    }

    public String getNumberFormateBinNumber(String binNumber) throws Exception {
        String value = "";
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            value = persistance.getNumberFormateBinNumber(CMSCon, binNumber);

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

        return value;
    }

    public String isReplaceAppication(String applicationId) throws Exception {
        String replaceCard = "";
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            replaceCard = persistance.isReplaceAppication(CMSCon, applicationId);

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
        return replaceCard;
    }

    public CardBean getBackendCard(String replaceCard) throws Exception {
        CardBean replaceBean = new CardBean();
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            replaceBean = persistance.getBackendCard(CMSCon, replaceCard);

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
        return replaceBean;
    }

    public OnlineCardTableBean getOnlineCard(String replaceCard) throws Exception {
        OnlineCardTableBean replaceBean = new OnlineCardTableBean();
        try {
            persistance = new NumberGenarationPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            replaceBean = persistance.getOnlineCard(OnlineCon, replaceCard);

            OnlineCon.commit();
        } catch (SQLException ex) {
            try {
                OnlineCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            OnlineCon.rollback();
            throw ex;

        } finally {
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return replaceBean;
    }

    public List<NumberGenarationDataBean> debitNumberGenarationSearch(NumberGenarationSearchBean searchBean) throws Exception {
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = persistance.getDebitNumberGenarationSearch(CMSCon, searchBean);
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

    private boolean isCACExsit(CardAccountCustomerBean bean, Connection con) throws Exception {
        boolean isExist = false;
        try {

            persistance = new NumberGenarationPersistance();

            isExist = persistance.isCACExsit(con, bean);

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

        }

        return isExist;
    }

    private boolean isAccountExsit(Connection con, String accountNo) throws Exception {
        boolean isExist = false;
        try {

            persistance = new NumberGenarationPersistance();

            isExist = persistance.isAccountExsit(CMSCon, accountNo);

        } catch (Exception ex) {
            throw ex;

        }

        return isExist;
    }

    public List<CardAccountCustomerBean> getCustomerDebitCardLst(String customerId) throws Exception {
        List<CardAccountCustomerBean> list = new ArrayList<CardAccountCustomerBean>();
        try {

            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            list = persistance.getCustomerDebitCardLst(CMSCon, customerId);
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

        return list;
    }

    public boolean isCardExist(String cno) throws Exception {
        boolean isCArdExist = false;
        try {
            persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            isCArdExist = persistance.isCardExist(CMSCon, cno);
        } catch (SQLException ex) {
            try {
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
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

        return isCArdExist;
    }

    public CardBean getEstablishmentCardBeanByBusinssRegNo(String businessRegNumber) throws Exception{
        CardBean bean = new CardBean();
        
        try {
             persistance = new NumberGenarationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bean = persistance.getEstablishmentCardBeanByBusinssRegNo(CMSCon, businessRegNumber);


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
        
        return bean;
    }
}
