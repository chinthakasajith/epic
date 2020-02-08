/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBranchBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.BankBranchMgtPersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class use to wrote all the manager class that relate to bankBranchMgt
 * @author ayesh
 */
public class BankBranchMgtManager {

    private static BankBranchMgtManager setInstance;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private BankBranchMgtPersistence perObj;
    private String errorMsg;

    private BankBranchMgtManager() {
    }

    /**
     * get BankBranchMgtManager instance
     * @return BankBranchMgtManager
     */
    public static synchronized BankBranchMgtManager getInstance() {

        if (setInstance == null) {
            setInstance = new BankBranchMgtManager();
        }
        return setInstance;
    }

    /**
     * get all bank details manager,its include bank code and bank name
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BankBranchBean> getBankNames() throws SQLException, Exception {

        try {
            List<BankBranchBean> bankList = null;
            perObj = new BankBranchMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getBankNames(CMSCon);

            CMSCon.commit();
            return bankList;
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

    /**
     * add new branch Manager 
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewBranchManager(SystemAuditBean systemAuditBean, BankBranchBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BankBranchMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.addNewBranch(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            return rowCount;
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

    /**
     * update branch details
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateBranchManager(SystemAuditBean systemAuditBean, BankBranchBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new BankBranchMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateBranch(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            return rowCount;
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

    /**
     * get all branches 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<BankBranchBean> getAllBranch() throws SQLException, Exception {


        try {
            List<BankBranchBean> bankList = null;
            perObj = new BankBranchMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getAllBranch(CMSCon);
            CMSCon.commit();
            return bankList;
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

    /**
     * get selected branch details
     * @return BankBranchBean-details store bean
     * @throws SQLException
     * @throws Exception 
     */
    public BankBranchBean getSelectedBranch(String id, String bankCode) throws SQLException, Exception {


        try {
            BankBranchBean bankBranchBean = null;
            perObj = new BankBranchMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankBranchBean = perObj.getSelectedBranch(CMSCon, id, bankCode);
            CMSCon.commit();
            return bankBranchBean;
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

    /**
     * delete branch
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteBranchBank(String id, String bank,SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new BankBranchMgtPersistence();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            //----
            rowCount = perObj.deleteBranchRate(CMSCon, id,bank);
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

    //--------------------------------------------
    /**
     * check given input valid to process
     * @param bean
     * @return true-if ok otherwise-false
     */
    public boolean isValidToProcess(BankBranchBean bean) {
        boolean flag = true;
        errorMsg = "";
        if (bean.getBranchName().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_BRANCH_EMPTY;
            }
        }

        if (!UserInputValidator.isCorrectString(bean.getBranchName())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_BRANCH_INVALID;
            }
        }






        if (bean.getDescription().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BRANCH_DESCRIPTION_EMPTY;
            }
        }


        if (!UserInputValidator.isCorrectString(bean.getDescription())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BRANCH_DESCRIPTION_INVALID;
            }
        }


        if (bean.getBankCode().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_EMPTY;
            }
        }


        if (!UserInputValidator.isCorrectString(bean.getBankCode())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_INVALID;
            }
        }



        if (bean.getAddress1().isEmpty() && bean.getAddress2().isEmpty() && bean.getAddress3().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.ADDRESS_EMPTY;
            }
        }

        if (!UserInputValidator.isCorrectAddress(bean.getAddress1()) && !UserInputValidator.isCorrectAddress(bean.getAddress2()) && !UserInputValidator.isCorrectAddress(bean.getAddress3())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_ADDR_INVALID;
            }
        }

        if (bean.getContactPer().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CONTACT_PERSOAN_EMPTY;
            }
        }

        if (!UserInputValidator.isCorrectString(bean.getContactPer())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.BANK_CON_PER_INVALID;
            }
        }

        if (bean.getContactNo().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CONTACT_NUMBER_EMPTY;
            }
        } else if (bean.getContactNo().length()<8 || bean.getContactNo().length()>20) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CONTACT_NUMBER_INVALID;
            }
        }

        try {
            Long.parseLong(bean.getContactNo());
        } catch (Exception e) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CONTACT_NUMBER_INVALID;
            }
        }
        
        if (bean.getDisplayDigit().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DIS_DIGIT_EMPTY;
            }
        } else if (bean.getDisplayDigit().length()>4) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DIS_DIGIT_INVALID;
            }
        }

        try {
            Long.parseLong(bean.getDisplayDigit());
        } catch (Exception e) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DIS_DIGIT_INVALID;
            }
        }


        return flag;
    }

    /*
     * get error message that relate to validation process
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
