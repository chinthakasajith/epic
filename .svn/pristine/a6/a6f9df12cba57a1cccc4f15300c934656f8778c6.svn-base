/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.serverdetails.businesslogic;

import com.epic.cms.switchcontrol.serverdetails.bean.OnlineVersionInfoBean;
import com.epic.cms.switchcontrol.serverdetails.persistance.ServerDetailsPersistance;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mahesh_m
 */
public class ServerDetailsManager {
    private Connection conToOnline;
    private ServerDetailsPersistance serverDetailsPer;
    
      public OnlineVersionInfoBean getServerInfo() throws Exception {
        OnlineVersionInfoBean serverVersionInfo = new OnlineVersionInfoBean();
        try {
            serverDetailsPer = new ServerDetailsPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            serverVersionInfo = serverDetailsPer.getServerInfo(conToOnline);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return serverVersionInfo;
    }
      
   public String getTransactionCount() throws Exception {
        String transactionCount = null;
        try {
            serverDetailsPer = new ServerDetailsPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            transactionCount = serverDetailsPer.getTransactionCount(conToOnline);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return transactionCount;
    }  
   
      public String getFailCount() throws Exception {
        String failCount = null;
        try {
            serverDetailsPer = new ServerDetailsPersistance();
            conToOnline = DBConnectionToOnlineDB.getConnection();
            conToOnline.setAutoCommit(false);
            failCount = serverDetailsPer.getFailCount(conToOnline);


            conToOnline.commit();

        } catch (SQLException ex) {
            try {
                conToOnline.rollback();
                throw ex;
            } catch (Exception e) {
                conToOnline.rollback();
                throw ex;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(conToOnline);
        }
        return failCount;
    } 
}
