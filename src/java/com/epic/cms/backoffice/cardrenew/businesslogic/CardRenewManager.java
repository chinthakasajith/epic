/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.cardrenew.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.cardrenew.bean.CardRenewBean;
import com.epic.cms.backoffice.cardrenew.bean.CardSearchBean;
import com.epic.cms.backoffice.cardrenew.persistance.CardRenewPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CardRenewManager {

    private CardRenewPersistance perObj;
    private Connection cmsCon;
    private Connection onlineCon;
    private List<CardRenewBean> cardList;
    private SystemAuditManager sysAuditManager;

    public List<CardRenewBean> searchCard(CardSearchBean searchBean) throws SQLException, Exception {

        try {
            perObj = new CardRenewPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            cardList = perObj.searchCard(cmsCon, searchBean);
            cmsCon.commit();
            return cardList;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public boolean proceedCardRenew(String card, SystemAuditBean systemAuditBean) throws Exception, SQLException {
        boolean success1 = false;
        boolean success2 = false;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CardRenewPersistance();

            cmsCon = DBConnection.getConnection();
            onlineCon = DBConnectionToOnlineDB.getConnection();

            cmsCon.setAutoCommit(false);
            onlineCon.setAutoCommit(false);

            success1 = perObj.updateCardTable(cmsCon, onlineCon, card);
            success2 = perObj.updateCardRenewTable(cmsCon, card);

            sysAuditManager.insertAudit(systemAuditBean, cmsCon);

            cmsCon.commit();
            onlineCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                onlineCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                onlineCon.rollback();
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                cmsCon.close();
            }
            if (onlineCon != null) {
                onlineCon.close();
            }
        }
        return (success1 & success2);
    }
}
