/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.callcenter.card.persistance.CardChangeRequestPersistance;
import com.epic.cms.callcenter.card.persistance.CardRequestPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author nisansala
 */
public class CardChangeRequestManager {
    
    private Connection CMSCon;
    private Connection onlineCMSCon;
    private CardBean card = null;
    
    private SystemAuditManager sysAuditManager;
    //private CardRequestPersistance cdReqPer;
    private HashMap<String, String> cardProduct;
    private CardChangeRequestPersistance cdchngePer;
    private HashMap<String, String> cardCategory = null;    
    private HashMap<String, String> blockReasons = null;
    
    public CardBean getCardDetails(String cardNo) throws Exception {
        try {
            cdchngePer = new CardChangeRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            card = cdchngePer.getCardDetails(CMSCon,cardNo);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return card;
    }
    
    public int changeCard(String reasonCode, CardBean card,String logID,SystemAuditBean systemAuditBean) throws Exception {
        int row;
        try {
            cdchngePer = new CardChangeRequestPersistance();
            sysAuditManager = new SystemAuditManager();
            
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            
            row = cdchngePer.changeCard(CMSCon,reasonCode,card,logID);
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
        return row;
    }
    
    public HashMap<String, String> loadCardProduct(String cardType) throws Exception {
        try {
            cdchngePer = new CardChangeRequestPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProduct = cdchngePer.loadCardProduct(CMSCon, cardType);

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
        return cardProduct;
    }
    
    
}
