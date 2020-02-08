/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditScoreRecommandBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.CreditScoreRecommendPersistence;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class use to wrote all the manager that relate to  Credit Score Recommend
 * @author ayesh
 */
public class CreditScoreRecommandManager {

    private String errorMsg;
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private CreditScoreRecommendPersistence perObj;
    private static CreditScoreRecommandManager setInstance;

    /**
     * get CreditScoreRecommandManager class instance 
     * @return -CreditScoreRecommandManager
     */
    private CreditScoreRecommandManager() {
    }

    public static synchronized CreditScoreRecommandManager getInstance() {
        if (setInstance == null) {
            setInstance = new CreditScoreRecommandManager();
        }
        return setInstance;
    }

    /**
     * get all available card types
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getAllCardType() throws SQLException, Exception {


        try {
            List<CardTypeMgtBean> bankList = null;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getCardTypeDetails(CMSCon);
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
     * get all available card product
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardProductBean> getAllCardProduct(String cardType) throws SQLException, Exception {


        try {
            List<CardProductBean> bankList = null;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getCardroductDetails(CMSCon, cardType);
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
     * add new credit score recommendation
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int AddNewScoreCardRecommend(SystemAuditBean systemAuditBean, CreditScoreRecommandBean bean) throws SQLException, Exception {


        try {
            int row = -1;
            sysAuditManager = new SystemAuditManager();
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            row = perObj.addNewCrediScoreRecommandation(CMSCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            return row;
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
     * get all recommended score list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreRecommandBean> getAllRecommended() throws SQLException, Exception {


        try {
            List<CreditScoreRecommandBean> bankList = null;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bankList = perObj.getAllRecommended(CMSCon);
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
     * delete recommended credit score
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteRecommendedCreditScore(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            perObj = new CreditScoreRecommendPersistence();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            //----
            rowCount = perObj.deleteRecommendedCreditScore(CMSCon, id);
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
     * get selected data
     * @param ID
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public CreditScoreRecommandBean getSelectedRecommended(String ID) throws SQLException, Exception {


        try {
            CreditScoreRecommandBean bean = null;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.getSelectedRecommended(CMSCon, ID);
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

    /*
     * get current range fore give ID
     */
    public CreditScoreRecommandBean getCurrentRange(String ID) throws SQLException, Exception {


        try {
            CreditScoreRecommandBean bean = null;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.getCurrentrange(CMSCon, ID);
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
     * check given card type  is exist
     * @param cardType-card type code
     * @param cardProduct-card product type code
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean isValidCardType(String cardType, String cardProduct) throws SQLException, Exception {

        try {
            boolean flag = false;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            int noRows = perObj.isCradTypeExist(CMSCon, cardType);

            if (noRows > 0) {
                flag = true;
            } else {
                flag = false;
            }
            CMSCon.commit();
            return flag;


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
     * check given card type and card product exist
     * @param cardType
     * @param cardProduct
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean isValidCardTypeProduct(String cardType, String cardProduct) throws SQLException, Exception {

        try {
            boolean flag = false;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            int noRows = perObj.isCradTypeANDProductExist(CMSCon, cardType, cardProduct);

            if (noRows == 1) {
                flag = true;
            } else {
                flag = false;
            }
            CMSCon.commit();
            return flag;


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
     * check given range is valid for inputs
     * @param cardType -card type code
     * @param cardProduct-card product code
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean isValidRange(String cardType, double lowValue, double highValue) throws SQLException, Exception {

        try {
            boolean flagLow = true, flagHigh = true, flag = true;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);


            List<CreditScoreRecommandBean> rangeList = perObj.getAvailableRanges(CMSCon, cardType);

            //--check low and high value if in previous define range
            for (int i = 0; i < rangeList.size(); i++) {
                CreditScoreRecommandBean rangeBean = rangeList.get(i);
                double low = Double.parseDouble(rangeBean.getLowLimit());
                double high = Double.parseDouble(rangeBean.getHighLimit());

//                if (low <= lowValue && lowValue < high) {
//                    flagLow = false;
//                    if (errorMsg.isEmpty()) {
//                        errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_INVALID;
//                    }
//                }
//                if (low <= highValue && highValue < high) {
//                    flagHigh = false;
//                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_INVALID;
//                }
            }

            if (!flagHigh || !flagLow) {
                flag = false;
            }




            CMSCon.commit();
            return flag;


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

    /*
     * get card type ranges with out give one
     */
    public boolean isCurrentVaildRange(String cardType, String ID, double lowValue, double highValue) throws SQLException, Exception {

        try {
            boolean flagLow = true, flagHigh = true, flag = true;
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);


            List<CreditScoreRecommandBean> rangeList = perObj.getCurrentRanges(CMSCon, ID, cardType);
            CMSCon.commit();
            //--check low and high value if in previous define range
            for (int i = 0; i < rangeList.size(); i++) {
                CreditScoreRecommandBean rangeBean = rangeList.get(i);
                double low = Double.parseDouble(rangeBean.getLowLimit());
                double high = Double.parseDouble(rangeBean.getHighLimit());

//                if (low <= lowValue && lowValue < high) {
//                    flagLow = false;
//                    if (errorMsg.isEmpty()) {
//                        errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_INVALID;
//                    }
//                }
//                if (low <= highValue && highValue < high) {
//                    flagHigh = false;
//                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_INVALID;
//                }
            }

            if (!flagHigh || !flagLow) {
                flag = false;
            }




            return flag;


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
     * update credit score recommended
     * @param systemAuditBean
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateCreditScoreRecommend(SystemAuditBean systemAuditBean, CreditScoreRecommandBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            sysAuditManager = new SystemAuditManager();
            perObj = new CreditScoreRecommendPersistence();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            //--
            rowCount = perObj.updateCreditScoreRecommend(CMSCon, bean);
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

//-------------------------------------- validation proceess ------------------------------
    /**
     * check given inputs are valid to process otherwise return false
     * and set error message
     * @return boolean 
     */
    public boolean isValidToAddProcess(CreditScoreRecommandBean bean) throws SQLException, Exception {
        boolean flag = true;
        errorMsg = "";
        if (bean.getId().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_ID_EMPTY;
            }
        } else {
            try {
                Double.parseDouble(bean.getId());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_ID_NUMERIC;
                }
            }
        }


        if (bean.getCardtype().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CARDTYPE_EMPTY;
            }
        }


        if (bean.getCardproduct().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CARDPRODUCT_EMPTY;
            }
        }


        if (bean.getLowLimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_EMPTY;
            }
        }else if (!UserInputValidator.isNumeric(bean.getLowLimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_NUMERIC;
            }
        }
        else {
            try {
                Double.parseDouble(bean.getLowLimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_NUMERIC;
                }
            }
        }


        if (bean.getHighLimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_EMPTY;
            }
        } else if (!UserInputValidator.isNumeric(bean.getHighLimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_NUMERIC;
            }
        }else {
            try {
                Double.parseDouble(bean.getHighLimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_NUMERIC;
                }
            }
        }


        if (bean.getCreditlimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_EMPTY;
            }
        }else if (!UserInputValidator.isNumeric(bean.getCreditlimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_NUMERIC;
            }
        } else {
            try {
                Double.parseDouble(bean.getCreditlimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_NUMERIC;
                }
            }
        }

//check given card type and product exist------
        if (flag) {
            flag = this.addRangeValidation(bean);
        }
        return flag;
    }

    /*
     * check rage valid for add process 
     */
    private boolean addRangeValidation(CreditScoreRecommandBean bean) throws SQLException, Exception {
        boolean flag = true;

        if (this.isValidCardType(bean.getCardtype(), bean.getCardproduct())) {
            flag = false;
            double setLow = Double.parseDouble(bean.getLowLimit());
            double setHigh = Double.parseDouble(bean.getHighLimit());

            if (setLow >= setHigh) {
                flag = false;
                if (errorMsg.isEmpty()) {
                      errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_RANGE_INVALID;
                }
            } else {

                if (!this.isValidRange(bean.getCardtype(), setLow, setHigh)) {
                    flag = false;

                } else {
                    flag = true;
                }
            }

        } else {
            flag = true;
        }
        return flag;

    }

    /**
     * update process validation
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public boolean isValidToUpdateProcess(CreditScoreRecommandBean bean) throws SQLException, Exception {
        boolean flag = true;
        errorMsg = "";
        if (bean.getId().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_ID_EMPTY;
            }
        } else {
            try {
                Double.parseDouble(bean.getId());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_ID_NUMERIC;
                }
            }
        }


        if (bean.getCardtype().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CARDTYPE_EMPTY;
            }
        }


        if (bean.getCardproduct().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CARDPRODUCT_EMPTY;
            }
        }


        if (bean.getLowLimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_EMPTY;
            }
        } else if (!UserInputValidator.isNumeric(bean.getLowLimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_NUMERIC;
            }
        }else {
            try {
                Double.parseDouble(bean.getLowLimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_LOW_LIMIT_NUMERIC;
                }
            }
        }


        if (bean.getHighLimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_EMPTY;
            }
        } else if (!UserInputValidator.isNumeric(bean.getHighLimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_NUMERIC;
            }
        }else {
            try {
                Double.parseDouble(bean.getHighLimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_HIGH_LIMIT_NUMERIC;
                }
            }
        }


        if (bean.getCreditlimit().isEmpty()) {
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_EMPTY;
            }
        }else if (!UserInputValidator.isNumeric(bean.getCreditlimit()) ){
            if (errorMsg.isEmpty()) {
                flag = false;
                errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_NUMERIC;
            }
        } else {
            try {
                Double.parseDouble(bean.getCreditlimit());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    flag = false;
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_CREDIT_LIMIT_NUMERIC;
                }
            }
        }

//check given card type and product exist------
        if (flag) {
            flag = this.updateRangeValidation(bean);
        }
        return flag;
    }

    /*
     * update process range validation
     */
    private boolean updateRangeValidation(CreditScoreRecommandBean bean) throws SQLException, Exception {
        boolean flag = true;
        double low = Double.parseDouble(bean.getLowLimit());
        double high = Double.parseDouble(bean.getHighLimit());
        CreditScoreRecommandBean currentBean = this.getCurrentRange(bean.getId());

        double currentLow = Double.parseDouble(currentBean.getLowLimit());
        double currentHigh = Double.parseDouble(currentBean.getHighLimit());

        if (!(currentLow < low && low < currentHigh) || !(currentLow < high && high < currentHigh)) {
            //other ranges are ok for low limit
            if (low >= high) {
                flag = false;
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.CREDITSCORE_RECOMMEND_RANGE_INVALID;
                }
            } else {
                if (!this.isCurrentVaildRange(bean.getCardtype(), bean.getId(), low, high)) {
                    flag = false;
                } else {
                    flag = true;
                }
            }
        }

        return flag;

    }

    /**
     * get error messages
     * @return String
     */
    public String getErrorMsg() {
        return errorMsg;
    }
}
