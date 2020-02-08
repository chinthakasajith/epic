/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.sysmsg.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.mtmm.terminalmgt.terminaldata.persistance.TerminalDataCapturePersistance;
import com.epic.cms.switchcontrol.sysmsg.bean.ViewAlertBean;
import com.epic.cms.switchcontrol.sysmsg.persistance.ViewAlertPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class ViewAlertManager {
    
    //initializing variables
    ViewAlertPersistance alertPer;
    private Connection CMSCon;
    private Connection onlineCMSCon;
    private List<ViewAlertBean> searchAlertList;
    HashMap <String,String> riskCategory = null; 
    private SystemAuditManager sysAuditManager;
    ViewAlertBean alerBean;
    
    public HashMap<String, String> getReadStatus() throws Exception {
        HashMap<String,String> readStatus = new HashMap<String, String>();
        try {
            
            alertPer = new ViewAlertPersistance();
            //CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            //CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);
            readStatus= alertPer.getReadStatus(onlineCMSCon);
            //CMSCon.commit();
            onlineCMSCon.commit();
        } catch (Exception ex) {
            try {
                //CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return readStatus;
    }
    
    public List<ViewAlertBean> searchAlert(ViewAlertBean alertBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            alertPer = new ViewAlertPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);

            searchAlertList = alertPer.searchAlertData(onlineCMSCon, alertBean);
            CMSCon.commit();
            onlineCMSCon.commit();
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }


        } catch (Exception ex) {
            CMSCon.rollback();
            onlineCMSCon.rollback();
            throw ex;

        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            
                        }
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

        }
        return searchAlertList;
    }
    
    public int updateReadStatus(String txnID) throws SQLException, Exception {
        //ViewAlertBean oneBean = null;
        int row =0 ;
        try {
            alertPer = new ViewAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            //oneBean = alertPer.viewOneAlert(onlineCMSCon, txnID);
            row = alertPer.updateReadStatus(txnID, onlineCMSCon);
            onlineCMSCon.commit();
            return row;
        } catch (Exception e) {
            try {
                onlineCMSCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public HashMap<String,String> getAllRiskCategories() throws SQLException, Exception {
        //ViewAlertBean oneBean = null;
        int row =0 ;
      riskCategory = new HashMap<String, String>(); 
        try {
            alertPer = new ViewAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);

            //oneBean = alertPer.viewOneAlert(onlineCMSCon, txnID);
            riskCategory = alertPer.getAllRiskCategories(onlineCMSCon);
            onlineCMSCon.commit();
            return riskCategory;
        } catch (Exception e) {
            try {
                onlineCMSCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    
    public int getCountAlert(String condition) throws Exception {
        int count;
        try {
            alertPer = new ViewAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            count = alertPer.getCountAlert(onlineCMSCon, condition);
            onlineCMSCon.commit();
        } catch (Exception ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return count;
    }
    
    public List<ViewAlertBean> getSystemAlertDetails(String condition, int start, int end) throws Exception {
        try {
            alertPer = new ViewAlertPersistance();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            onlineCMSCon.setAutoCommit(false);
            searchAlertList = alertPer.getSystemAlertDetails(onlineCMSCon, condition, start, end);
            onlineCMSCon.commit();
        } catch (Exception ex) {
            try {
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (onlineCMSCon != null) {
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return searchAlertList;
    }
}
