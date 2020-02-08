/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CardTypeMgtPersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardCategoryBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class use to wrote all the manager method that relate to card type management 
 * @author ayesh
 */
public class CardTypeMgtManager {

    private static CardTypeMgtManager setInstace;
    private String errorMsg;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CardTypeMgtPersistence perObj;
    private List<CardCategoryBean> cardCategoryLst;

    private CardTypeMgtManager() {
    }

    /**
     * get CardTypeMgtManager instance
     * @return CardTypeMgtManager
     */
    public static synchronized CardTypeMgtManager getInctance() {
        if (setInstace == null) {
            setInstace = new CardTypeMgtManager();
        }
        return setInstace;
    }

    /**
     * add new card type manager
     * @param systemAuditBean
     * @param bean- CardTypeMgtBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewCardType(SystemAuditBean systemAuditBean, CardTypeMgtBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            rowCount = perObj.addNewCardType(CMSCon, bean);
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
     * get all card type detail list managerss
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getAllCardType() throws SQLException, Exception {


        try {
            List<CardTypeMgtBean> bankList = null;
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getAllCardType(CMSCon);
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
     * delete selected card type manager
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCardtype(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new CardTypeMgtPersistence();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            //----
            rowCount = perObj.deleteCardtype(CMSCon, id);
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
     * get selected card type manager
     * @param id-String
     * @return -CardTypeMgtBean
     * @throws SQLException
     * @throws Exception 
     */
    public CardTypeMgtBean getSelectedCradType(String id) throws SQLException, Exception {


        try {
            CardTypeMgtBean bean = null;
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.getSelectedCradtype(CMSCon, id);
            CMSCon.commit();
            return bean;
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
     * update card type mgt manager
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateCardType(SystemAuditBean systemAuditBean, CardTypeMgtBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateCardType(CMSCon, bean);
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

    //------------------------------------
    /**
     * check given bean data valid for next process-validate user given input
     * @param bean - CardTypeMgtBean
     * @return true-if valid,otherwise return false
     */
    public boolean inValidToProcess(CardTypeMgtBean bean)throws Exception {
        boolean flag = true;
        errorMsg = "";
        if (bean.getCardTypeCode().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARD_TYPE_MGT_CODE_EMPTY;
            }
        }
        
        if (!UserInputValidator.isAlphaNumeric(bean.getCardTypeCode())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARD_TYPE_MGT_CODE_INVALID;
            }
        }
        
        
        if (bean.getDescription().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARD_TYPE_MGT_DESCRIPTION_EMPTY;
            }
        }
        
        
        
        if (!UserInputValidator.isDescription(bean.getDescription())) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARD_TYPE_MGT_DESCRIPTION_INVALID;
            }
        }
        
        
        
        
        if (bean.getStatus().isEmpty()) {
            flag = false;
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARD_TYPE_MGT_STATUS_EMPTY;
            }
        }


        return flag;

    }

    /**
     * get error message that relate to validate process
     * @return String
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    public List<CardCategoryBean> getAllCardCategory() throws Exception {
       
        try {
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardCategoryLst = perObj.getAllCardCategory(CMSCon);
            CMSCon.commit();
            return cardCategoryLst;
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
    
    public List<CardCategoryBean> getCreditCardCategory() throws Exception {
       
        try {
            perObj = new CardTypeMgtPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardCategoryLst = perObj.getCreditCardCategory(CMSCon);
            CMSCon.commit();
            return cardCategoryLst;
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
}
