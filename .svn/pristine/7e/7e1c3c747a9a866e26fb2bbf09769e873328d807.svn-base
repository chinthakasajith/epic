/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CardScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.ScoreCardAssignRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.persistance.CardScorePersistance;
import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.exception.ValidateException;
import com.epic.cms.system.util.validate.UserInputValidator;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * this class use for wrote all the manager that relate to score card manager
 * @author ayesh
 */
public class CardScoreManager {

    private static CardScoreManager setInstance;
    private SystemAuditManager sysAuditManager;
    private CardScorePersistance perObj;
    private Connection CMSCon;
    private String errorMsg = "";

    private CardScoreManager() {
    }

    /**
     * get CardScoreManager Instance
     * @return CardScoreManager
     */
    public static synchronized CardScoreManager getScoreCardInstance() {
        if (setInstance == null) {
            setInstance = new CardScoreManager();
        }
        return setInstance;
    }

    /**
     * get all available credit score rules,return CreditScoreRuleBean bean with rule code\
     * and description.
     * @return List-CreditScoreRuleBean
     * @throws SQLException
     * @throws Exception 
     */
    public List<CreditScoreRuleBean> getAllCreditScoreRules() throws SQLException, Exception {
        try {
            List<CreditScoreRuleBean> ruleList = null;
            perObj = new CardScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            ruleList = perObj.getAllCreditScoreRule(CMSCon);

            CMSCon.commit();
            return ruleList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public List<CardTypeMgtBean> getCardTypeList() throws SQLException, Exception {
        try {
            List<CardTypeMgtBean> cardTypeList = null;
            perObj = new CardScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            cardTypeList = perObj.getCardTypeList(CMSCon);

            CMSCon.commit();
            return cardTypeList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * get already assign rule set
     * @return List-ScoreCardAssignRuleBean
     * @throws SQLException
     * @throws Exception 
     */
    public List<ScoreCardAssignRuleBean> getAssignRule(String code) throws SQLException, Exception {
        try {
            List<ScoreCardAssignRuleBean> ruleList = null;
            perObj = new CardScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            ruleList = perObj.getAssignedRuleList(CMSCon, code);

            CMSCon.commit();
            return ruleList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * get all details about score card
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<CardScoreBean> getAllScoreCardDetails() throws SQLException, Exception {
        try {
            List<CardScoreBean> allList = null;
            perObj = new CardScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            allList = perObj.getAllScoreCardDetails(CMSCon);

            CMSCon.commit();
            return allList;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * insert new score card 
     * @param bean
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int insertNewScoreCard(CardScoreBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {

        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CardScorePersistance();

            row = perObj.addNewScoreCard(CMSCon, bean);
            for (int i = 0; i < bean.getRules().length; i++) {
                row = row + perObj.addRules(CMSCon, bean.getScoreCardCode(), bean.getRules()[i]);

            }

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();


            return row;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

    }

    public boolean deleteCardScore(CardScoreBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean delRule = false;
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CardScorePersistance();

            delRule = perObj.deleteCardScoreRules(CMSCon, bean);

            if (delRule) {
                success = perObj.deleteCardScore(CMSCon, bean);
            }

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();


            return success;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

    }

    /**
     * update card score 
     * @param bean - details bean
     * @param systemAuditBean - audit trance bean
     * @return number of rows updated
     * @throws SQLException
     * @throws Exception 
     */
    public int updateCardScore(CardScoreBean bean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        List<ScoreCardAssignRuleBean> backUpBean = null;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            perObj = new CardScorePersistance();

            backUpBean = perObj.getAssignedRuleList(CMSCon, bean.getScoreCardCode());
            perObj.deleteRulesUpdate(CMSCon, bean.getScoreCardCode());


            try {

                row = perObj.updateCardScore(CMSCon, bean);
                for (int i = 0; i < bean.getRules().length; i++) {
                    row = row + perObj.addRules(CMSCon, bean.getScoreCardCode(), bean.getRules()[i]);
                }

            } catch (Exception e) {
                for (int i = 0; i < backUpBean.size(); i++) {
                    ScoreCardAssignRuleBean backUp = backUpBean.get(i);
                    perObj.addRules(CMSCon, backUp.getScoreCardCode(), backUp.getRuleCode());

                }
            }
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return row;

        } catch (SQLException e) {
            try {
                for (int i = 0; i < backUpBean.size(); i++) {
                    ScoreCardAssignRuleBean backUp = backUpBean.get(i);
                    perObj.addRules(CMSCon, backUp.getScoreCardCode(), backUp.getRuleCode());

                }
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }

    }

    /**
     * validate input that relate to  score card process
     * @return return true inputs validate and return false not valid input
     */
    public boolean isInputValidate(CardScoreBean bean) throws ValidateException, Exception {
        boolean flag = true;
        errorMsg = "";
        if (bean.getScoreCardCode().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_CODE_EMPTY;
                throw new ValidateException();
            }
        }
        if (!UserInputValidator.isAlphaNumeric(bean.getScoreCardCode())) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORE_CARD_CODE_INVALID;
                throw new ValidateException();
            }
        }

        if (bean.getScoreCardName().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_NAME_EMPTY;
                throw new ValidateException();
            }
        }

        if (!UserInputValidator.isDescription(bean.getScoreCardName())) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORE_CARD_NAME_INVALID;
                throw new ValidateException();
            }
        }


        if (bean.getProduct().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_PRODUCT_EMPTY;
                throw new ValidateException();
            }
        }


        if (bean.getRules() == null) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_RULE_NULL;
                throw new ValidateException();
            }
        }


        if (bean.getMinScoreCard().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_MINSCORE_EMPTY;
                throw new ValidateException();
            }
        } else {
            try {
                Double.parseDouble(bean.getMinScoreCard());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.SCORECRAD_MINSCORE_INVALID;
                    throw new ValidateException();
                }
            }

        }

        if (bean.getMaxScoreCard().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_MAXSCORE_EMPTY;
                throw new ValidateException();
            }
        } else {
            try {
                Double.parseDouble(bean.getMaxScoreCard());
            } catch (Exception e) {
                if (errorMsg.isEmpty()) {
                    errorMsg = MessageVarList.SCORECRAD_MAXSCORE_INVALID;
                    throw new ValidateException();
                }
            }

        }

        if (bean.getStatus().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_SATUS_EMPTY;
                throw new ValidateException();
            }
        }


        if (Integer.parseInt(bean.getMaxScoreCard()) < Integer.parseInt(
                bean.getMinScoreCard())) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.SCORECRAD_SCORE_RANGE_INVALID;
                throw new ValidateException();
            }
        }

        if (bean.getCardType().isEmpty()) {
            if (errorMsg.isEmpty()) {
                errorMsg = MessageVarList.CARDTYPE_SATUS_EMPTY;
                throw new ValidateException();
            }
        }


        return flag;
    }

    /**
     * get error massage in validate
     * @return  String
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * set error massage in validate
     * @param errorMsg 
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public CardApplicationBean getCardApplicationBean(String applicationId) throws  Exception{
        
        CardApplicationBean bean = new CardApplicationBean();
        
                try {
            perObj = new CardScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            bean = perObj.getCardApplicationBean(CMSCon,applicationId);

            CMSCon.commit();
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                throw ex;
            }
        } finally {
            try {
                if (CMSCon != null) {
                    CMSCon.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        
        return bean;
    }
}
