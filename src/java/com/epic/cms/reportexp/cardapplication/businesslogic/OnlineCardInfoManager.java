/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.businesslogic;

import com.epic.cms.reportexp.cardapplication.bean.OnlineCardBean;
import com.epic.cms.reportexp.cardapplication.persistance.OnlineCardInfoPersistance;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author badrika
 */
public class OnlineCardInfoManager {
    private OnlineCardBean cardBean=null;
    private OnlineCardInfoPersistance perObj;
    private Connection OnlineCon;
    
     public OnlineCardBean getOnlineCard(String cardNum) throws Exception {
        try {

            cardBean= new OnlineCardBean();
            
            perObj = new OnlineCardInfoPersistance();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            OnlineCon.setAutoCommit(false);
            cardBean=perObj.getOnlineCard(OnlineCon, cardNum);
            OnlineCon.commit();
            
        } catch (Exception ex) {
            try {
                OnlineCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            OnlineCon.close();
        }
        return cardBean;
    }
    
}
