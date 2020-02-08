/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.camm.applicationconfig.creditscore.persistance.CreditScoreFieldPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreFieldBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *this class use to wrote all the manager class
 * @author ayesh
 */
public class CreditScoreFieldManager {

    private static CreditScoreFieldManager setIntance;
    private SystemAuditManager sysAuditManager;
    private CreditScoreFieldPersistance perObj;
    private Connection CMSCon;
    private String errorMsg;

    /**
     * get credit score field id define manager instance
     * @return setIntance - CreditScoreFieldManager
     */
    public static CreditScoreFieldManager getCreditScoreFieldInstance() {

        if (setIntance == null) {
            setIntance = new CreditScoreFieldManager();
        }
        return setIntance;
    }

    /**
     * get all credit score form details
     * @return List <String> 
     */
    public List<String> getCreditScoreFormNameList() throws SQLException, Exception {
        try {
            List<String> formList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();

            formList = perObj.getCreditScoreFormName(CMSCon);

            CMSCon.commit();
            return formList;
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
     * get field id according to form name
     * @param  formName - String
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<String> getCreditScoreFieldID(String formName) throws SQLException, Exception {
        try {
            List<String> fieldList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();

            fieldList = perObj.getCreditScoreFieldName(CMSCon, formName);

            CMSCon.commit();
            return fieldList;
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
     * add new field to credit score manager
     * @param bean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewCrediScoreField(CreditScoreFieldBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();

            row = perObj.insertNewCreditScoreField(bean, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
     * get all details about credit score fields 
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreFieldBean> getAllCretidScoreFieldDetails() throws SQLException, Exception {
        try {
            List<CreditScoreFieldBean> beanList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();
            beanList = perObj.getAllCreditScoreFieldDetails(CMSCon);


            CMSCon.commit();
            return beanList;
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

    /////////////this is from upul////////////////////////////////
    /**
     * 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreFieldBean> getAllActiveCreditScoreFieldName() throws SQLException, Exception {
        try {
            List<CreditScoreFieldBean> beanList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();
            beanList = perObj.getAllActiveCreditScoreFieldName(CMSCon);


            CMSCon.commit();
            return beanList;
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
     * 
     * @param formName
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public String getCreditScoreFieldDataType(String fieldId) throws SQLException, Exception {
        String dataType = null;
        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();

            dataType = perObj.getCreditScoreFieldDataType(CMSCon, fieldId);

            CMSCon.commit();
            return dataType;
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

    /////////////this is from upul////////////////////////////////
    /**
     * delete credit score field
     * @param ID
     * @return int
     */
    public int deleteCreditScoreField(String ID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int row = -1;

        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CreditScoreFieldPersistance();
            sysAuditManager = new SystemAuditManager();

            row = perObj.deleteCreditScoreField(CMSCon, ID);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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

        return row;
    }

    /**
     * update credit score field manager  
     * @param bean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateCreditScoreField(CreditScoreFieldBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int row = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CreditScoreFieldPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
//-------
            row = perObj.updateCreditScoreField(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
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
        return row;
    }

    /**
     * 
     * @param creditBean
     * @return
     * @throws ValidateException 
     */
    public boolean isValidInput(CreditScoreFieldBean creditBean) throws Exception {
        try {
            boolean flag = true;
            errorMsg = "";




            if (creditBean.getFieldCode().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_FIELDCODE_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            } else {
                if (!UserInputValidator.isCorrectString(creditBean.getFieldCode())) {
                    if (errorMsg.isEmpty()) {
                        errorMsg = MessageVarList.CREDITSCORE_FIELD_FIELDCODE_INVALID;
                        flag = false;
//                    throw new ValidateException();

                    }
                }
            }


            if (creditBean.getFieldDes().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_FIELDDES_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            } else {

                if (!UserInputValidator.isCorrectString(creditBean.getFieldDes())) {
                    if (errorMsg.isEmpty()) {
                        errorMsg = MessageVarList.CREDITSCORE_FIELD_FIELDDES_INVALID;
                    }
                    flag = false;
//                throw new ValidateException();
                }
            }

             if (creditBean.getFormName().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_FORMNAME_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            }

            if (creditBean.getFieldName().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_FIELDNAME_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            }


           


            if (creditBean.getStatus().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_STATUS_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            }

            if (creditBean.getDataType().isEmpty()) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_FIELD_DATATYPE_EMPTY;
                }
                flag = false;
//            throw new ValidateException();
            }

            return flag;
            
        } catch (Exception e) {

            throw e;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
