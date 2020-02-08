/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.transactionmgt.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.persistance.CountryMgtPersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class use to wrote all the manager class that relate to country management
 * @author ayesh
 */
public class CountryMgtManager {

    private static CountryMgtManager setInstance;
    private Connection CMSCon, OnlineCon;
    private SystemAuditManager sysAuditManager;
    private CountryMgtPersistance countryPer;
    private String errorMsg;

    /**
     * get country manager instance 
     * @return CountryMgtManager
     */
    public synchronized static CountryMgtManager getCountryManager() {
        if (setInstance == null) {
            setInstance = new CountryMgtManager();
        }
        return setInstance;
    }

    /**
     * insert country management manager
     * @param countryMgtBean
     * @param systemAuditBean
     * @return
     * @throws SQLException 
     */
    public boolean insertNewCountry(CountryMgtBean countryMgtBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            boolean success = false;
            int row1 = -1;
            int row2 = -1;
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);

            countryPer = new CountryMgtPersistance();
            row1 = countryPer.insertNewCountryPer(countryMgtBean, CMSCon);
            row2 = countryPer.insertNewCountryPerToOnline(countryMgtBean, OnlineCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);


            if (row1 == 1 && row2 == 1) {
                success = true;
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }

            return success;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
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
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    /**
     * get all information about country
     * @return List-CountryMgtBean
     */
    public List<CountryMgtBean> getAllCountryInfo() throws SQLException, Exception {
        try {
            List<CountryMgtBean> countryList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            countryPer = new CountryMgtPersistance();
            countryList = countryPer.getAllExchangeRateDetails(CMSCon);

            CMSCon.commit();
            return countryList;

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
     * update country information 
     * @param countryMgtBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean updateCountry(CountryMgtBean countryMgtBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        //boolean success = false;
        try {
            boolean success = false;
            int row1 = -1;
            int row2 = -1;
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);
            countryPer = new CountryMgtPersistance();
            //
            row1 = countryPer.updateCountryPer(countryMgtBean, CMSCon);
            row2 = countryPer.updateCountryPerToOnline(countryMgtBean, OnlineCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);


            if (row1 == 1 && row2 == 1) {
                success = true;
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }


            return success;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
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
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

    }

    /**
     * delete country manager
     * @param countryMgtBean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean deleteCountry(int countryCode, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            boolean success = false;
            int row1 = -1;
            int row2 = -1;
            CMSCon = DBConnection.getConnection();
            OnlineCon = DBConnectionToOnlineDB.getConnection();
            CMSCon.setAutoCommit(false);
            OnlineCon.setAutoCommit(false);
            countryPer = new CountryMgtPersistance();
            row1 = countryPer.deleteCountryPer(CMSCon, countryCode);
            row2 = countryPer.deleteCountryPerFromOnline(OnlineCon, countryCode);

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            if (row1 == 1 && row2 == 1) {
                success = true;
                CMSCon.commit();
                OnlineCon.commit();
            } else {
                CMSCon.rollback();
                OnlineCon.rollback();
            }

            return success;
        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                OnlineCon.rollback();
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
            if (OnlineCon != null) {
                try {
                    OnlineCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public boolean isValidInput(CountryMgtBean countryBean) throws ValidateException, Exception {

        boolean flag = true;

        errorMsg = "";

        if (countryBean.getCountryCode().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_COUNTRY_CODE_NULL;
            }

            throw new ValidateException();
        } else if (countryBean.getCountryCode().length() != 3) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRY_CODE_IN_3;
            }
            throw new ValidateException();
        } else {
            try {
                Integer.parseInt(countryBean.getCountryCode());
            } catch (Exception e) {

                flag = false;
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.COUNTRYMGT_COUNTRY_CODE;
                }

                throw new ValidateException();
            }

        }





        if (countryBean.getAlphaFirst().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_ALPHAFIRST_NULL;
//                    request.setAttribute("operationtype", "view");
            }
            throw new ValidateException();
        }


        if (!UserInputValidator.isAlpha(countryBean.getAlphaFirst())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_ALPHAFIRST_INVALID;
            }

            throw new ValidateException();
        }



        if (countryBean.getAlphaSecond().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_ALPHASECOND_NULL;
            }
            throw new ValidateException();
        }


        if (!UserInputValidator.isAlpha(countryBean.getAlphaSecond())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_ALPHASECOND_INVALID;
            }

            throw new ValidateException();
        }

        if (countryBean.getDescription().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_DESCRIPTION_NULL;
            }
            throw new ValidateException();
        }

        if (!UserInputValidator.isDescription(countryBean.getDescription())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_DESCRIPTION_INVALID;
            }

            throw new ValidateException();
        }


        if (countryBean.getFrdVal().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_FRAUD_VALUE_NULL;
            }
            throw new ValidateException();
        } else {
            try {

                Integer.parseInt(countryBean.getFrdVal());
            } catch (Exception e) {
                flag = false;
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.COUNTRYMGT_FRAUD_VALUE;
                }
                throw new ValidateException();
            }
        }






        if (countryBean.getRegion().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_REGION_NULL;
            }
            throw new ValidateException();
        }


        if (!UserInputValidator.isCorrectString(countryBean.getRegion())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.COUNTRYMGT_REGION_INVALID;
            }

            throw new ValidateException();
        }


        return flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
