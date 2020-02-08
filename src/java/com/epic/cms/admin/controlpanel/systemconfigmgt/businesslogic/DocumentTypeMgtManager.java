/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.DocumentTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.LetterTemplateBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.VerifyCategoryBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.DocumentTypeMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * this manager class use to wrote all the manager that relate to document type management
 * @author ayesh
 */
public class DocumentTypeMgtManager {

    private static DocumentTypeMgtManager instance;
    private String errorMsg;
    private SystemAuditManager sysAuditManager;
    private DocumentTypeMgtPersistance perObj;
    private Connection CMSCon;

    private DocumentTypeMgtManager() {
    }

    /**
     * get DocumentTypeMgtManager instance 
     * @return instance-DocumentTypeMgtManager
     */
    public static DocumentTypeMgtManager getInstance() {

        if (instance == null) {
            instance = new DocumentTypeMgtManager();
        }
        return instance;
    }

    /**
     * insert new document type manager
     * @param bean
     * @param systemAuditBean
     * @return int - number of rows that insert
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewDocumentType(DocumentTypeMgtBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            row = perObj.insertNewDocumentType(bean, CMSCon);

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
     * get all details about document types
     * @return List-DocumentTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public List<DocumentTypeMgtBean> getAllCretidScoreFieldDetails() throws SQLException, Exception {
        try {
            List<DocumentTypeMgtBean> beanList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            beanList = perObj.getAllDocumentTypeDetails(CMSCon);

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
     * get all details about document types
     * @return List-DocumentTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getLetterFiledFormats() throws SQLException, Exception {
        try {
            HashMap<String, String> formatList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            formatList = perObj.getLetterFiledFormats(CMSCon);

            CMSCon.commit();
            return formatList;

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
     * get all details about document types
     * @return List-DocumentTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public HashMap<String, String> getLetterProcesses() throws SQLException, Exception {
        try {
            HashMap<String, String> processList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            processList = perObj.getLetterProcesses(CMSCon);

            CMSCon.commit();
            return processList;

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
     * get all details about document types
     * @return List-DocumentTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public List<LetterTemplateBean> getAllLetterTemplateDetailsList() throws SQLException, Exception {
        try {
            List<LetterTemplateBean> templateList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            templateList = perObj.getAllLetterTemplateDetailsList(CMSCon);

            CMSCon.commit();
            return templateList;

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
     * get all details about document types
     * @return List-DocumentTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public HashSet<String> getLetterFieldsDetail() throws SQLException, Exception {
        try {
            HashSet<String> fieldsList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            fieldsList = perObj.getLetterFieldsDetail(CMSCon);

            CMSCon.commit();
            return fieldsList;

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
     * get all  available verification category 
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<VerifyCategoryBean> getVerifyCatList() throws SQLException, Exception {
        try {
            List<VerifyCategoryBean> beanList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            beanList = perObj.getVerificationCatgory(CMSCon);

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
     * get selected document code details
     * @param docCode-selected document type code
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public DocumentTypeMgtBean getSelectedDocumentCodeDetails(String docCode) throws SQLException, Exception {
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            DocumentTypeMgtBean bean = perObj.getSelectedDocumentType(CMSCon, docCode);

            CMSCon.commit();
            return bean;
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
     *check give document type code already exist in database
     * @param docCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean isAlreadyExist(String docCode) throws SQLException, Exception {
        try {
            boolean flag = true;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            perObj = new DocumentTypeMgtPersistance();
            flag = perObj.isAlreadyExist(CMSCon, docCode);

            CMSCon.commit();
            return flag;
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
     * delete selected document type
     * @param ID-Document code which want to delete
     * @param systemAuditBean
     * @return int-number of rows that delete
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteDocumentType(String ID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int row = -1;

        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new DocumentTypeMgtPersistance();
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
     * to add a new bank
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewLetterFormat(SystemAuditBean systemAuditBean, LetterTemplateBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new DocumentTypeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.addNewLetterFormat(CMSCon, bean);
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
     * to add a new bank
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateLetterTemplates(SystemAuditBean systemAuditBean, LetterTemplateBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new DocumentTypeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.updateLetterTemplates(CMSCon, bean);
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
     * to delete a bank
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteLetterTemplate(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new DocumentTypeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            rowCount = perObj.deleteLetterTemplate(CMSCon, id);
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
     * update document type
     * @param bean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateDocumentType(DocumentTypeMgtBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int row = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new DocumentTypeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            row = perObj.updateDocumentType(CMSCon, bean);
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
     * validate user input
     * @param bean-DocumentTypeMgtBean
     * @return true-if all inputs are valid,false-if all input not valid
     */
    public boolean isValidate(DocumentTypeMgtBean bean) throws ValidateException {
        boolean flag = true;
        errorMsg = "";
        if (bean.getDocCode().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_CODE_EMPTY);
            }
            throw new ValidateException();
        }

        if (!UserInputValidator.isCorrectString(bean.getDocCode())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DOCUMENT_TYPE_CODE_INVALID;
            }

            throw new ValidateException();
        }


        if (bean.getDocName().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_NAME_EMPTY);
            }
            throw new ValidateException();
        }

        if (!UserInputValidator.isCorrectString(bean.getDocName())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DOCUMENT_TYPE_NAME_INVALID;
            }

            throw new ValidateException();
        }


        if (bean.getVerifyCat().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_VERIFI_CATAGORY_EMPTY);
            }
            throw new ValidateException();
        }
        if (bean.getPostFix().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_POSTFIX_EMPTY);
            }
            throw new ValidateException();
        }

        if (!UserInputValidator.isCorrectString(bean.getPostFix())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.DOCUMENT_TYPE_POSTFIX_INVALID;
            }

            throw new ValidateException();
        }




        if (bean.getStatus().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_STATUS_EMPTY);
            }
            throw new ValidateException();
        }
        
        if (bean.getCardCategory()== null || bean.getCardCategory().equals("")) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_CARD_CATEGORY_EMPTY);
            }
            throw new ValidateException();
        }
        
        if (bean.getIsMandatory()==null) {
            flag = false;
            if (errorMsg.isEmpty()) {
                setErrorMsg(MessageVarList.DOCUMENT_TYPE_MANDATORY_EMPTY);
            }
            throw new ValidateException();
        }
                
                
        return flag;
    }

    /**
     * get error message that relate to validate 
     * @return  String
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * set error message that relate to validate
     * @param errorMsg -String
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<CardCategoryBean> getCardCategoryList() throws Exception {
        try {
            CardTypeMgtManager cardTypeManager = CardTypeMgtManager.getInctance();
            List<CardCategoryBean> cardCategoryLst = cardTypeManager.getAllCardCategory();
            return cardCategoryLst;

        } catch (Exception e) {
            throw e;
        }

    }
}
