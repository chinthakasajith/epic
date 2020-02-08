/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.ExChangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.ExchangeRatePersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *this class use to wrote all the manager method for exchange rate 
 * @author ayesh
 */
public class ExchangeRateManager {

    private static ExchangeRateManager setInstance;
    private Connection CMSCon;
    private ExchangeRatePersistance exchPerObj;
    private SystemAuditManager sysAuditManager;

    private ExchangeRateManager() {
    }

    /**
     * get exchange manager instance
     * @return  ExchangeRateManager
     */
    public static ExchangeRateManager getExchangeRateManager() {

        if (setInstance == null) {
            setInstance = new ExchangeRateManager();
        }
        return setInstance;
    }

    /**
     * get all details about currency 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CurrencyBean> getAllCurrencyDetails() throws SQLException, Exception {
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            List<CurrencyBean> exchangeList = null;
            exchPerObj = new ExchangeRatePersistance();
            exchangeList = exchPerObj.getAllCurrency(CMSCon);
            CMSCon.commit();
            return exchangeList;
        } catch (SQLException ex) {
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
    }

    /**
     * add new exchange rate to database.
     * return 1 if  successfully  add
     * @return int 
     * @throws SQLException
     * @throws Exception 
     */
    public int addExchangeRate(ExChangeRateBean exBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            exchPerObj = new ExchangeRatePersistance();
            sysAuditManager = new SystemAuditManager();

            if (!exBean.isFlag()) {
                row = exchPerObj.addNewExchangeRate(CMSCon, exBean);
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            } else {
                row = exchPerObj.addNewExchangeRate(CMSCon, exBean);
                ExChangeRateBean inExBean = new ExChangeRateBean();
                inExBean.setFromCurrency(exBean.getToCurrency());
                inExBean.setToCurrency(exBean.getFromCurrency());
                inExBean.setRate(String.valueOf(exBean.getInvetRate()));
                inExBean.setLastUpdateUser(exBean.getLastUpdateUser());

                row = row + exchPerObj.addNewExchangeRate(CMSCon, inExBean);
                systemAuditBean.setRemarks(systemAuditBean.getRemarks() + "/Invert");
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            }

            CMSCon.commit();
            return row;
        } catch (SQLException ex) {
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
    }

    /**
     * get all exchange rate details
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<ExChangeRateBean> getAllExchangeRateDetails() throws SQLException, Exception {
        try {
            List<ExChangeRateBean> exchangeList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            exchPerObj = new ExchangeRatePersistance();
            exchangeList = exchPerObj.getAllExchangeRateDetails(CMSCon);
            CMSCon.commit();
            return exchangeList;
        } catch (SQLException ex) {
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
    }

    /**
     * delete exchange rate
     * @param sectionID
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteExchangeRate(ExChangeRateBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            exchPerObj = new ExchangeRatePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            rowCount = exchPerObj.deleteExchangeRate(CMSCon, bean);
            CMSCon.commit();

        } catch (SQLException ex) {
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
        return rowCount;
    }

    /**
     * update exchange rate manager 
     * @param exBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateExchangeRate(ExChangeRateBean exBean, SystemAuditBean systemAuditBean, boolean flag) throws SQLException, Exception {
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            exchPerObj = new ExchangeRatePersistance();
            sysAuditManager = new SystemAuditManager();

            //check invert was there 

            if (!exchPerObj.isRowExist(CMSCon, exBean)) {

                row = exchPerObj.updateExchangeRate(CMSCon, exBean);
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            } else {
                if (flag) {
                    row = exchPerObj.updateExchangeRate(CMSCon, exBean);
                }
                ExChangeRateBean inExBean = new ExChangeRateBean();
                inExBean.setFromCurrency(exBean.getToCurrency());
                inExBean.setToCurrency(exBean.getFromCurrency());
                inExBean.setRate(String.valueOf(exBean.getInvetRate()));
                inExBean.setLastUpdateUser(exBean.getLastUpdateUser());
                inExBean.setLastUpdateDate(exBean.getLastUpdateDate());

                row = row + exchPerObj.updateExchangeRate(CMSCon, inExBean);
                systemAuditBean.setRemarks(systemAuditBean.getRemarks() + "/Invert");
                sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            }

            CMSCon.commit();
            return row;
        } catch (SQLException ex) {
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
    }
}
