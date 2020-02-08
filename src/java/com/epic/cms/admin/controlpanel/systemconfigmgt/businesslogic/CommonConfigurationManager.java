/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CommonConfigurationBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CommonConfigurationPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.UserRoleBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemUserRoleManager;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CountryMgtBean;
import com.epic.cms.admin.controlpanel.transactionmgt.bean.CurrencyBean;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CountryMgtManager;
import com.epic.cms.admin.controlpanel.transactionmgt.businesslogic.CurrencyMgtManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruwan_e
 */
public class CommonConfigurationManager {

    private static CommonConfigurationManager commonConfigurationManager;
//    private String errorMsg;
    private Connection CMSCon;
    private CommonConfigurationPersistance perObj;
    private List<CountryMgtBean> countryList;
    private List<BankBean> bankList;
    private List<CurrencyBean> currenyList;
    private List<UserRoleBean> userrolelist;

   
    private SystemAuditManager sysAuditManager;

    private CommonConfigurationManager() {
    }

    public static synchronized CommonConfigurationManager getInstance() {
        if (commonConfigurationManager == null) {
            commonConfigurationManager = new CommonConfigurationManager();
        }
        try {
            commonConfigurationManager.setCountryList(new CountryMgtManager().getAllCountryInfo());
            commonConfigurationManager.setBankList(new BankMgtManager().getBankNames());
            commonConfigurationManager.setCurrenyList(new CurrencyMgtManager().getCurrencyDetails());
            commonConfigurationManager.setUserrolelist(new SystemUserRoleManager().getAllUserRole());
        } catch (SQLException ex) {
            Logger.getLogger(CommonConfigurationManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommonConfigurationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commonConfigurationManager;
    }

    public CommonConfigurationBean getCommonConfiguration() throws SQLException, Exception {
        try {
            perObj = new CommonConfigurationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            CommonConfigurationBean commonConfiguration = perObj.getCommonConfiguration(CMSCon);
            CMSCon.commit();
            return commonConfiguration;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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

    public int updateCommonConfiguration(CommonConfigurationBean commonConfiguration, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int result = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CommonConfigurationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = perObj.updateCommonConfiguration(commonConfiguration, CMSCon);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return result;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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

    public int setCommonConfiguration(CommonConfigurationBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int result = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CommonConfigurationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            result = perObj.setCommonConfiguration(bean, CMSCon);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return result;
        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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

    public boolean isRecordAvailable() throws SQLException, Exception {
        boolean status = false;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CommonConfigurationPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            status = perObj.isRecordAvailable(CMSCon);

        } catch (Exception e) {
            try {
                CMSCon.rollback();
                throw e;
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
        return status;
    }

    public List<BankBean> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankBean> bankList) {
        this.bankList = bankList;
    }

    public List<CountryMgtBean> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<CountryMgtBean> countryList) {
        this.countryList = countryList;
    }

    public List<CurrencyBean> getCurrenyList() {
        return currenyList;
    }

    public void setCurrenyList(List<CurrencyBean> currenyList) {
        this.currenyList = currenyList;
    }
    
    public List<UserRoleBean> getUserrolelist() {
        return userrolelist;
    }

    public void setUserrolelist(List<UserRoleBean> userrolelist) {
        this.userrolelist = userrolelist;
    }
    
}
