package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineBaseDataBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCountryCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCurrencyCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.BankMgtPersistance;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.OnlineBasePersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class OnlineBaseManager {

    private Connection backendConn;
    private Connection cmsCon;
    private Connection con;
    private Connection con2;
    private OnlineBasePersistence perObj;
//    private OnLineCurrencyCodeBean currencyCodeBean;
    private SystemAuditManager sysAuditManager;

    public List<OnLineCurrencyCodeBean> getCurrenceDesc() throws SQLException, Exception {
        try {
            List<OnLineCurrencyCodeBean> currencyCodeBean = null;
            perObj = new OnlineBasePersistence();
            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);

            currencyCodeBean = perObj.getCurrenceDesc(cmsCon);
            cmsCon.commit();

            return currencyCodeBean;
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public List<OnLineCountryCodeBean> getBaseCountry() throws SQLException, Exception {
        try {
            List<OnLineCountryCodeBean> onlineBeanList = null;
            perObj = new OnlineBasePersistence();
            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);

            onlineBeanList = perObj.getBaseCountry(cmsCon);
            cmsCon.commit();

            return onlineBeanList;
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public OnLineBaseDataBean getOnLineBaseData() throws SQLException, Exception {
        try {
            OnLineBaseDataBean onLineBaseDataBeans = null;
            perObj = new OnlineBasePersistence();
            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);
            backendConn = DBConnection.getConnection();
            backendConn.setAutoCommit(false);
            onLineBaseDataBeans = perObj.getOnLineBaseData(cmsCon,backendConn);
            cmsCon.commit();

            return onLineBaseDataBeans;
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                backendConn.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
            if (backendConn != null) {
                try {
                    backendConn.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public boolean saveConfigData(SystemAuditBean systemAuditBean, OnLineBaseDataBean bean) throws SQLException, Exception {
//    public boolean saveConfigData(OnLineBaseDataBean bean) throws SQLException, Exception {
        boolean status = false;
        try {
            OnLineBaseDataBean onLineBaseDataBean = null;
            sysAuditManager = new SystemAuditManager();
            perObj = new OnlineBasePersistence();

            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);
            
            con2 = DBConnection.getConnection();
            con2.setAutoCommit(false);

            status = perObj.saveConfigData(cmsCon,con2, bean);
            sysAuditManager.insertAudit(systemAuditBean, con2);
            
            cmsCon.commit();
            con2.commit();
            
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                con2.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();                
                } catch (Exception e) {
                    throw e;
                }
            }
            if (con2 != null) {
                try {                  
                    con2.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public boolean updateConfigData(SystemAuditBean systemAuditBean, OnLineBaseDataBean bean) throws SQLException, Exception {
        boolean status = false;
        try {
            OnLineBaseDataBean onLineBaseDataBean = null;
            sysAuditManager = new SystemAuditManager();
            perObj = new OnlineBasePersistence();

            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            status = perObj.updateConfigData(cmsCon,con, bean);
            sysAuditManager.insertAudit(systemAuditBean, con);

            cmsCon.commit();
            con.commit();
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                con.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                    con.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return status;
    }

    public boolean isRecordAvailable() throws SQLException, Exception {
        boolean status = false;
        try {
            perObj = new OnlineBasePersistence();
            cmsCon = DBConnectionToOnlineDB.getConnection();
            cmsCon.setAutoCommit(false);

            status = perObj.isRecordAvailable(cmsCon);
            cmsCon.commit();


        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return status;
    }
}
