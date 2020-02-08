/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.cardkeys.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeyProfilebean;
import com.epic.cms.switchcontrol.cardkeys.bean.CardKeybean;
import com.epic.cms.switchcontrol.cardkeys.persistance.CardKeyPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asela
 */
public class CardKeyManager {

    private Connection CMSConOnline;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardKeybean keybean;
    private List<CardKeybean> keybeanList;
    private List<CardKeyProfilebean> profileKeybeanList;
    private CardKeyPersistance cardKeyPersistance;

    public List<CardKeybean> getKeyBeanList(int cardKeyId) throws Exception {
        try {
            keybeanList = new ArrayList<CardKeybean>();
            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            keybeanList = cardKeyPersistance.getKeyBeanList(CMSConOnline, cardKeyId);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
        }
        return keybeanList;
    }

    public List<CardKeybean> getAllKeyBeanList() throws Exception {
        try {
            keybeanList = new ArrayList<CardKeybean>();
            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            keybeanList = cardKeyPersistance.getAllKeyBeanList(CMSConOnline);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try {
                DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            } catch (Exception e) {
                throw e;
            }
        }
        return keybeanList;
    }

    public List<CardKeyProfilebean> getProfileKeyBeanList(int cardKeyId) throws Exception {
        try {
            keybeanList = new ArrayList<CardKeybean>();
            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            profileKeybeanList = cardKeyPersistance.getProfileKeyBeanList(CMSConOnline, cardKeyId);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try {
                DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            } catch (Exception e) {
                throw e;
            }
        }
        return profileKeybeanList;
    }

    public List<CardKeyProfilebean> getAllProfileKeyBeanList() throws Exception {
        try {
            keybeanList = new ArrayList<CardKeybean>();
            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSConOnline.setAutoCommit(false);
            profileKeybeanList = cardKeyPersistance.getAllProfileKeyBeanList(CMSConOnline);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try {
                DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            } catch (Exception e) {
                throw e;
            }
        }
        return profileKeybeanList;
    }

    public boolean updatePVKAKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updatePVKAKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updatePVKBKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updatePVKBKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updatePVKKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updatePVKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK1AKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK1AKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK1BKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK1BKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK1KeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK1KeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK2AKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK2AKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK2BKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK2BKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCVK2KeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCVK2KeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateICVKAKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateICVKAKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateICVKBKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateICVKBKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateICVKKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateICVKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCSCKAKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCSCKAKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCSCKBKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCSCKBKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCSCKKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCSCKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateIMKACKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateIMKACKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateZCMKKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateZCMKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateIMKSMIKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateIMKSMIKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateIMKSMCKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateIMKSMCKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updatePPKKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updatePPKKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean insertCardKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.insertCardKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public boolean updateCardKeyDetails(CardKeybean keyBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean success = false;
        try {

            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            success = cardKeyPersistance.updateCardKeyDetails(CMSConOnline, keyBean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            }
        } finally {
            try{
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
            }catch(Exception e){
                throw e;
            }
        }
        return success;
    }

    public int deleteCardKeyDetails(String keyId, SystemAuditBean systemAuditBean) throws Exception {
        int num = -1;
        try {
            cardKeyPersistance = new CardKeyPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();
            CMSCon = DBConnection.getConnection();
            sysAuditManager = new SystemAuditManager();
            CMSConOnline.setAutoCommit(false);
            CMSCon.setAutoCommit(false);

            num = cardKeyPersistance.deleteCardKeyDetails(CMSConOnline, keyId);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSConOnline.commit();
            return num;
        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSConOnline.rollback();
                throw e;
            }

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSConOnline.rollback();
                throw e;
            } finally {
                try {
                    DBConnectionToOnlineDB.dbConnectionClose(CMSConOnline);
                } catch (Exception e) {
                    throw e;
                }
            }


        }
    }
}
