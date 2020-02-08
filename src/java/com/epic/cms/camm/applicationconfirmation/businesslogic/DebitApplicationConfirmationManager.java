/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitAppValuesBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitApplicationDetailBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.camm.applicationconfirmation.persistance.DebitApplicationConfirmationPersistance;
import com.epic.cms.camm.applicationproccessing.assigndata.bean.ApplicationHistoryBean;
import com.epic.cms.camm.applicationproccessing.assigndata.persistance.ApplicationAssignPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class DebitApplicationConfirmationManager {

    private List<RecommendSchemBean> cardProductList;
    private DebitApplicationConfirmationPersistance perObj;
    private Connection CMSCon;
    private Connection cmsConOnline;
    private List<DebitCardAccountTemplateBean> accountTempList;
    private List<CardBinBean> cardBinList;
    private List<CardBinBean> cardKeyList;
    
    private List<CardTemplateBean> cardTempList;
    private SystemAuditManager sysAuditManager;
    private List<DebitApplicationDetailBean> personalDetailList;
    private DebitApplicationDetailBean personalDetailBean;
    private List<DebitAppValuesBean> binList;

    private List<DebitAppValuesBean> changeBinValuesList;
    
    public List<RecommendSchemBean> getCardProducts(String cardType) throws Exception {

        cardProductList = new ArrayList<RecommendSchemBean>();
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProductList = perObj.getCardProducts(CMSCon, cardType);


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
        return cardProductList;
    }

    //getDebitCardApplicationDetails
    public DebitApplicationDetailBean getDebitCardApplicationDetails(String applicationId) throws Exception {

        personalDetailList = new ArrayList<DebitApplicationDetailBean>();
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            personalDetailBean = perObj.getDebitCardApplicationDetails(CMSCon, applicationId);


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
        return personalDetailBean;
    }

    public List<DebitCardAccountTemplateBean> getAccountTemplates(String cardType) throws Exception {

        accountTempList = new ArrayList<DebitCardAccountTemplateBean>();
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            accountTempList = perObj.getAccountTemplates(CMSCon, cardType);


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
        return accountTempList;
    }

    public List<CardBinBean> getDebitBinList(String productionModeCode, String cardType) throws Exception {
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinList = perObj.getDebitBinList(CMSCon, productionModeCode, cardType);


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
        return cardBinList;
    }

    public List<CardTemplateBean> getDebitCardTemplates(String productCode, String accountTempId) throws Exception {
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardTempList = perObj.getDebitCardTemplates(CMSCon, productCode, accountTempId);


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
        return cardTempList;
    }

    public boolean updateCardApplication(String applicationId, String cardProduct, String productionMode,String cardKey, String binProfile, String acctempCode, String cardtempCode, SystemAuditBean systemAuditBean, String appStatus, ApplicationHistoryBean historyBean,String loyalty) throws Exception {
        boolean status = false;
        boolean status2 = false;
        boolean success = false;

        try {
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();

            sysAuditManager = new SystemAuditManager();
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = perObj.updateCardApplication(CMSCon, applicationId, cardProduct, productionMode,cardKey, binProfile, acctempCode, cardtempCode,loyalty);
            status2 = perObj.UpdateCardApplicationStatus(CMSCon, applicationId, appStatus);
            history.insertApplicationHistory(CMSCon, historyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (status && status2) {
                success = true;
                CMSCon.commit();
            } else {
                CMSCon.rollback();
            }



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
        return success;
    }

    public Boolean insertRejectReasonAndRemarks(String rejectReason, String remark, String applicationId, String appStatus, SystemAuditBean systemAuditBean, ApplicationHistoryBean historybean) throws Exception {
        
        boolean status = false;
        boolean status2 = false;
        boolean success = false;

        try {
            ApplicationAssignPersistance history = new ApplicationAssignPersistance();

            sysAuditManager = new SystemAuditManager();
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = perObj.updateRejectReasonAndRemark(CMSCon, rejectReason, remark, applicationId);
            status2 = perObj.UpdateCardApplicationStatus(CMSCon, applicationId, appStatus);
            history.insertApplicationHistory(CMSCon, historybean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (status && status2) {
                success = true;
                CMSCon.commit();
            } else {
                CMSCon.rollback();
            }


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
        return success;
    }

    
    public List<DebitAppValuesBean> changeBinProfile(String productCode) throws SQLException, Exception {

        changeBinValuesList = new ArrayList<DebitAppValuesBean>();
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            changeBinValuesList = perObj.changeBinProfile(CMSCon, productCode);


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
        return changeBinValuesList;
    }
    
    public List<CardBinBean> getCardKeyList(String cardProfileCode,String binProfileCode) throws Exception {
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardBinList = perObj.getCardKeyList(CMSCon, cardProfileCode, binProfileCode);


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
        return cardBinList;
    }  
    
   public List<CardBinBean> getProductionModeList(String CardKeyId) throws Exception {
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);
            cardBinList = perObj.getProductionModeList(cmsConOnline, CardKeyId);


            cmsConOnline.commit();
        } catch (SQLException ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                cmsConOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsConOnline);
        }
        return cardBinList;
    }   
  
   public List<CardBinBean> getCardKeyListByProductionMode(String productionMode) throws Exception {
        try {
            perObj = new DebitApplicationConfirmationPersistance();
            cmsConOnline = DBConnectionToOnlineDB.getConnection();
            cmsConOnline.setAutoCommit(false);
            cardKeyList = perObj.getCardKeyListByProductionMode(cmsConOnline, productionMode);


            cmsConOnline.commit();
        } catch (SQLException ex) {
            try {
                cmsConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                cmsConOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsConOnline);
        }
        return cardKeyList;
    }
   
}
