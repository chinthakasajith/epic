/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.keymailer.businesslogic;

import com.epic.cms.switchcontrol.keymailer.bean.KeyBean;
import com.epic.cms.switchcontrol.keymailer.bean.TerminalKeyMailerBean;
import com.epic.cms.switchcontrol.keymailer.persistance.TerminalKeyMailerPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nalin
 */
public class TerminalKeyMailerManager {

    private TerminalKeyMailerPersistance keymailerPerr = null;
    private List<TerminalKeyMailerBean> searchList = null;
    private TerminalKeyMailerBean keyMailerBean = null;
    private KeyBean keyBean = null;
    private Connection CMSCon;
    private Connection CMSConOnline;

    public List<TerminalKeyMailerBean> searchTerminal(TerminalKeyMailerBean keyMailerBean) throws Exception, Exception {
        try {

            keymailerPerr = new TerminalKeyMailerPersistance();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            searchList = keymailerPerr.searchTerminal(CMSCon, keyMailerBean);
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

    public TerminalKeyMailerBean verifyTerminalData(String terminalID) throws Exception {

        try {

            keyMailerBean = new TerminalKeyMailerBean();
            keymailerPerr = new TerminalKeyMailerPersistance();
            CMSCon = DBConnection.getConnection();

            CMSCon.setAutoCommit(false);

            keyMailerBean = keymailerPerr.verifyTerminalData(CMSCon, terminalID);

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
        return keyMailerBean;
    }

    public KeyBean getTMKKey(String terminalID) throws Exception {

        try {

            keyBean = new KeyBean();
            keymailerPerr = new TerminalKeyMailerPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);

            keyBean = keymailerPerr.getTMKKey(CMSConOnline, terminalID);

            CMSConOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSConOnline.rollback();
            throw ex;

        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return keyBean;
    }

    public boolean updateTMKKey(KeyBean keyBean) throws Exception {

        boolean isSuccess = false;

        try {
            keymailerPerr = new TerminalKeyMailerPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);

            isSuccess = keymailerPerr.updateTMKKey(CMSConOnline, keyBean);

            CMSConOnline.commit();
        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            CMSConOnline.rollback();
            throw ex;

        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return isSuccess;
    }
    
     public boolean updateTPKKey(KeyBean keyBean) throws Exception {

        boolean isSuccess = false;

        try {
            keymailerPerr = new TerminalKeyMailerPersistance();
            CMSConOnline = DBConnectionToOnlineDB.getConnection();

            CMSConOnline.setAutoCommit(false);

            isSuccess = keymailerPerr.updateTPKKey(CMSConOnline, keyBean);

            CMSConOnline.commit();
        } catch (SQLException ex) {
            try {
                CMSConOnline.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }

        } catch (Exception ex) {
            CMSConOnline.rollback();
            throw ex;

        } finally {
            if (CMSConOnline != null) {
                try {
                    CMSConOnline.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return isSuccess;
    }
}
