/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.callcenter.callcentersearch.bean.CallHistoryBean;
import com.epic.cms.callcenter.callcentersearch.businesslogic.CallCenterMgtManager;
import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.callcenter.card.persistance.CardBlockPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardBlockManager {
    private Connection CMSCon;
    private Connection onlineCMSCon;
    private CardBean card = null;
    private CardBlockPersistance cardBlkPer;
    private SystemAuditManager sysAuditManager;
    private HashMap<String, String> cardStatus = null;    
    private HashMap<String, String> blockReasons = null;
    private CallCenterMgtManager callCenterManager;
    
    public CardBean getCardDetails(String cardNo) throws Exception {
        try {
            cardBlkPer = new CardBlockPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            card = cardBlkPer.getCardDetails(CMSCon,cardNo);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return card;
    }
    
    public HashMap<String, String> getBlockReasons() throws Exception {
        try {
            
            cardBlkPer = new CardBlockPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            blockReasons = cardBlkPer.getBlockReasons(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            
            CMSCon.rollback();
            throw ex;
            
        } finally {
            
            DBConnection.dbConnectionClose(CMSCon);
        }
        return blockReasons;
    }
    
    public HashMap<String, String> getCardStatus() throws Exception {
        try {
            
            cardBlkPer = new CardBlockPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardStatus = cardBlkPer.getCardStatus(CMSCon);
            CMSCon.commit();

        } catch (Exception ex) {
            
            CMSCon.rollback();
            throw ex;
            
        } finally {
            
            DBConnection.dbConnectionClose(CMSCon);
        }
        return cardStatus;
    }
    
    public int blockCard(CardBean card,SystemAuditBean systemAuditBean,CallHistoryBean historyBean) throws Exception {
        int isUpdate;
        try {
            cardBlkPer = new CardBlockPersistance();            
            sysAuditManager = new SystemAuditManager();
            callCenterManager = new CallCenterMgtManager();
            
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);           

            isUpdate = cardBlkPer.blockCard(CMSCon,onlineCMSCon,card);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            callCenterManager.insertCallHistory(historyBean, CMSCon);
            
            CMSCon.commit();
            onlineCMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            onlineCMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);           
            DBConnection.dbConnectionClose(onlineCMSCon);
        }

        return isUpdate;
    }
    
        
    
    
}
