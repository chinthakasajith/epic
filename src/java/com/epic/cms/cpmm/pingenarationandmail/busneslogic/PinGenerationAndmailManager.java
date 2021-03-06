/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.cpmm.pingenarationandmail.busneslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingCardDetailsBean;
import com.epic.cms.cpmm.cardembossing.bean.CardEmbossingSearchBean;
import com.epic.cms.cpmm.pingenarationandmail.been.DomainBean;
import com.epic.cms.cpmm.pingenarationandmail.been.SearchBulkPingenBean;
import com.epic.cms.cpmm.pingenarationandmail.persistance.PinGenerationAndMailPersistance;
import com.epic.cms.prem.bulkcardmgt.bulkcardrequest.bean.BulkCardRequestBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class PinGenerationAndmailManager {

    private List<CardEmbossingCardDetailsBean> cardEmbossingVISAList = null;
    private PinGenerationAndMailPersistance pingenarationPer = null;
    private List<CardEmbossingCardDetailsBean> pinMailList = null;
    private List<SearchBulkPingenBean> bulkSearchList;
    private Connection CMSCon;
    private Connection CMSConToOnline;
    private SystemAuditManager sysAuditManager = null;

    public List<CardEmbossingCardDetailsBean> getAllPinGenerationList(CardEmbossingSearchBean searchBean, String domain) throws Exception {
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardEmbossingVISAList = pingenarationPer.getAllPinGenerationList(CMSCon, searchBean, domain);
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
        return cardEmbossingVISAList;
    }

    public List<SearchBulkPingenBean> getBulkSearchList(SearchBulkPingenBean searchBean) throws Exception {
        bulkSearchList = new ArrayList<SearchBulkPingenBean>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkSearchList = pingenarationPer.getBulkSearchList(CMSCon, searchBean);
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
        return bulkSearchList;
    }

    public List<SearchBulkPingenBean> getBulkPinMailSearchList(SearchBulkPingenBean searchBean) throws Exception {
        bulkSearchList = new ArrayList<SearchBulkPingenBean>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkSearchList = pingenarationPer.getBulkPinMailSearchList(CMSCon, searchBean);
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
        return bulkSearchList;
    }

    public List<CardEmbossingCardDetailsBean> getAllPinMailList(CardEmbossingSearchBean searchBean, String domain) throws Exception {
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            pinMailList = pingenarationPer.getAllPinMailList(CMSCon, searchBean, domain);
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
        return pinMailList;
    }

    public String getTPKFromOnlineKey() throws Exception {

        String TPK = null;
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSConToOnline.setAutoCommit(false);
            TPK = pingenarationPer.getTPKFromOnlineKey(CMSConToOnline);
            CMSConToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConToOnline != null) {
                try {
                    CMSConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return TPK;
    }

    public CardEmbossingCardDetailsBean getCardTableDetails(String cardNo) throws Exception {

        CardEmbossingCardDetailsBean cardbean = new CardEmbossingCardDetailsBean();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardbean = pingenarationPer.getCardTableDetails(CMSCon, cardNo);
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
        return cardbean;
    }

    public List<CardProductBean> getBulkCardProductList() throws Exception {

        List<CardProductBean> bulkProductList = new ArrayList<CardProductBean>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkProductList = pingenarationPer.getBulkCardProductList(CMSCon);
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
        return bulkProductList;
    }

    public List<String> getBulkCardList(String batchId) throws Exception {

        List<String> bulkCardList = new ArrayList<String>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCardList = pingenarationPer.getBulkCardList(CMSCon, batchId);
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
        return bulkCardList;
    }

    public List<String> getBulkPinMailCardList(String batchId) throws Exception {

        List<String> bulkCardList = new ArrayList<String>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkCardList = pingenarationPer.getBulkPinMailCardList(CMSCon, batchId);
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
        return bulkCardList;
    }

    public BulkCardRequestBean getBulkCardReqDetails(String batchId) throws Exception {

        BulkCardRequestBean bulkbean = new BulkCardRequestBean();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkbean = pingenarationPer.getBulkCardReqDetails(CMSCon, batchId);
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
        return bulkbean;
    }

    public List<DomainBean> getBulkCardDomains() throws Exception {

        List<DomainBean> bulkDomainList = new ArrayList<DomainBean>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            bulkDomainList = pingenarationPer.getBulkCardDomains(CMSCon);
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
        return bulkDomainList;
    }

    public Boolean isCardAvailable(String cardBNumber) throws Exception {
        Boolean status = false;
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSConToOnline.setAutoCommit(false);
            status = pingenarationPer.isCardnumberAvailable(CMSConToOnline, cardBNumber);
            CMSConToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConToOnline != null) {
                try {
                    CMSConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public Boolean updateOnlineCard(String eppkPin, String pvv, String offset, String pinMethod, String cvv, String cardNo) throws Exception {
        Boolean status = false;
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSConToOnline.setAutoCommit(false);
            status = pingenarationPer.updateOnlineCard(CMSConToOnline, eppkPin, pvv, offset, pinMethod, cvv, cardNo);
            CMSConToOnline.commit();
        } catch (Exception ex) {
            try {
                CMSConToOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSConToOnline != null) {
                try {
                    CMSConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public Boolean updateCardPinmailStatus(String cardNo, ApplicationHistoryBean historybean, SystemAuditBean systemAuditBean) throws Exception {
        Boolean status = false;
        try {
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();
            pingenarationPer = new PinGenerationAndMailPersistance();

            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            history.insertApplicationHistory(CMSCon, historybean);
            status = pingenarationPer.updateOnlineCard(CMSCon, cardNo);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
        } catch (Exception ex) {
            LogFileCreator.writePinMailerErrorToLog(ex);
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                LogFileCreator.writePinMailerErrorToLog(ex);
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
        return status;
    }

    public Boolean updateCardTable(String eppkPin, String pvv, String cvv, String cvv2, String icvv, String offset, String pvki, String cardNo, ApplicationHistoryBean historybean, String pinMethod, SystemAuditBean systemAuditBean) throws Exception {
        Boolean status = false;
        try {
            //Enhancement ,Do not store PIN_BLOCK, CVV,CVV2
            //eppkPin=null;
            //cvv=null;
            //cvv2=null;
            pingenarationPer = new PinGenerationAndMailPersistance();
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();

            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            CMSConToOnline = DBConnectionToOnlineDB.getConnection();
            CMSConToOnline.setAutoCommit(false);

            history.insertApplicationHistory(CMSCon, historybean);

            pingenarationPer.updateOnlineCard(CMSConToOnline, eppkPin, pvv, offset, pinMethod, cvv, cardNo);
            pingenarationPer.updateCardTable(CMSCon, eppkPin, pvv, cvv, cvv2, icvv, offset, pvki, cardNo,pinMethod);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSConToOnline.commit();
            CMSCon.commit();

            status = true;

        } catch (Exception ex) {
            try {
                CMSConToOnline.rollback();
                CMSCon.rollback();
                ex.printStackTrace();
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

            if (CMSConToOnline != null) {
                try {
                    CMSConToOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public void updateBulkReqqestTable(String status, String batchId) throws Exception {
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            pingenarationPer.updateBulkReqqestTable(CMSCon, status, batchId);

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
    }
    
        public void updateBulkReqqestTableForPinMail(String status, String batchId) throws Exception {
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            pingenarationPer.updateBulkReqqestTableForPinMail(CMSCon, status, batchId);

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
    }
        
    public HashMap<String,String> getCardCategoryList() throws Exception {

        HashMap<String,String> map = new HashMap<String,String>();
        try {
            pingenarationPer = new PinGenerationAndMailPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            map = pingenarationPer.getCardCategoryList(CMSCon);
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
        return map;
    }
    
}
