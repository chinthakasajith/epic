/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyExchangeRateBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.CurrencyExchangeRatePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class CurrencyExchangeRateManager {

    //declaring variables
    private Connection CMSCon;
    private Connection onlineCMSCon;
    private SystemAuditManager sysAuditManager;
    private CurrencyExchangeRatePersistance currXchngRatePer;

    /**
     * to retrieve all currency details
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CurrencyBean> getAllCurrencyDetails() throws SQLException, Exception {
        try {
            List<CurrencyBean> currencyList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currXchngRatePer = new CurrencyExchangeRatePersistance();

            currencyList = currXchngRatePer.getAllCurrencyDetails(CMSCon);

            CMSCon.commit();
            return currencyList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
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
     * to retrieve currency details which the currency exchange rates are not set yet
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CurrencyBean> getUnusedCurrencyDetails() throws SQLException, Exception {
        try {
            List<CurrencyBean> currencyList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currXchngRatePer = new CurrencyExchangeRatePersistance();

            currencyList = currXchngRatePer.getUnusedCurrencyDetails(CMSCon);

            CMSCon.commit();
            return currencyList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
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
     * to retrieve all the currency exchange rates
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CurrencyExchangeRateBean> getAllCurrencyExchangeRates() throws SQLException, Exception {
        try {
            List<CurrencyExchangeRateBean> exchangeRateList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            currXchngRatePer = new CurrencyExchangeRatePersistance();

            //assign the merchant details to the erchant bean instance
            exchangeRateList = currXchngRatePer.getAllCurrencyExchangeRates(CMSCon);

            CMSCon.commit();
            return exchangeRateList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
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
     * to insert a new currency exchange rate
     * @param exchangeBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Boolean insertNewCurrencyExchangeRate(CurrencyExchangeRateBean exchangeBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {


            Boolean success1 = false;
            Boolean success2 = false;

            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();
            currXchngRatePer = new CurrencyExchangeRatePersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);

            success1 = currXchngRatePer.insertNewCurrencyExchangeRate(exchangeBean, CMSCon);
            success2 = currXchngRatePer.insertNewCurrencyExchangeRateOnline(exchangeBean, onlineCMSCon);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            onlineCMSCon.commit();


            return (success1 & success2);

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (onlineCMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * to delete a currency exchange rate
     * @param currencyCode
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean deleteCurrencyExchangeRate(String currencyCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);

            sysAuditManager = new SystemAuditManager();
            currXchngRatePer = new CurrencyExchangeRatePersistance();

            int row = currXchngRatePer.deleteCurrencyExchangeRate(CMSCon, onlineCMSCon, currencyCode);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            onlineCMSCon.commit();

            //'row' indicates the success of deletion
            if (row == 1) {
                success = true;
            }
        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (onlineCMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    /**
     * to update an existing currency exchange rate
     * @param exchange
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean updateCurrencyExchangeRate(CurrencyExchangeRateBean exchange, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            onlineCMSCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            onlineCMSCon.setAutoCommit(false);

            sysAuditManager = new SystemAuditManager();
            currXchngRatePer = new CurrencyExchangeRatePersistance();

            row = currXchngRatePer.updateCurrencyExchangeRate(exchange, CMSCon, onlineCMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            onlineCMSCon.commit();
            if (row == 1) {
                success = true;
            }

            //row will indicate the success of updation 

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                onlineCMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }

            if (onlineCMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    onlineCMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }
}
