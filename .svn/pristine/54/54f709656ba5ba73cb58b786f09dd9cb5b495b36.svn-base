/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreBean;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.persistance.CreditScorePersistance;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.logs.LogFileCreator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */
public class CreditScoreManager {

    private Connection CMSCon;
    private CreditScorePersistance creditScorePersis;

    public String getScoreCardCode(String cardType) throws Exception {
        String scoreCardCode;
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            scoreCardCode = creditScorePersis.getScoreCardRuleCode(CMSCon,cardType);

            CMSCon.commit();
            return scoreCardCode;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public String getRequestedCardType(String applicationId,String cardCategoryCode) throws Exception {
        String reqCardType;
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            reqCardType = creditScorePersis.getRequestedCardType(CMSCon,applicationId,cardCategoryCode);

            CMSCon.commit();
            return reqCardType;
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

    public List<String> getCreditScoreRuleList(String scoreCardCode) throws Exception {
        List<String> creditScoreRuleList = new ArrayList<String>();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            creditScoreRuleList = creditScorePersis.getCreditScoreRuleList(CMSCon, scoreCardCode);

            CMSCon.commit();
            return creditScoreRuleList;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public CreditScoreBean getFieldId(CreditScoreBean bean) throws Exception {
        CreditScoreBean crediScoreBean = new CreditScoreBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            crediScoreBean = creditScorePersis.getFieldId(CMSCon, bean);

            CMSCon.commit();
            return crediScoreBean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public CreditScoreBean getFieldNameAndFormName(CreditScoreBean bean) throws Exception {
        CreditScoreBean crediScoreBean = new CreditScoreBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            crediScoreBean = creditScorePersis.getFieldNameAndFormName(CMSCon, bean);

            CMSCon.commit();
            return crediScoreBean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public CreditScoreBean getTableNameAndColmnName(CreditScoreBean bean) throws Exception {
        CreditScoreBean crediScoreBean = new CreditScoreBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            crediScoreBean = creditScorePersis.getTableNameAndColmnName(CMSCon, bean);

            CMSCon.commit();
            return crediScoreBean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public CreditScoreBean getValue(CreditScoreBean bean, String applicationId) throws Exception {
        CreditScoreBean crediScoreBean = new CreditScoreBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            crediScoreBean = creditScorePersis.getValue(CMSCon, bean, applicationId);

            CMSCon.commit();
            return crediScoreBean;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public CreditScoreRuleBean getCreditScoreRuleDetails(String ruleCode) throws Exception {
        CreditScoreRuleBean crediScoreRule = new CreditScoreRuleBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            crediScoreRule = creditScorePersis.getCreditScoreRuleDetails(CMSCon, ruleCode);

            CMSCon.commit();
            return crediScoreRule;
        } catch (SQLException e) {
            LogFileCreator.writeErrorToCreditScoreLog(e);
            try {
                CMSCon.rollback();
                throw e;
            } catch (SQLException ex) {
                LogFileCreator.writeErrorToCreditScoreLog(ex);
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

    public boolean insertToApplicationCreditScore(String applicationId, String ruleCode, String creditScore) throws Exception {
        CreditScoreRuleBean crediScoreRule = new CreditScoreRuleBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            boolean status = creditScorePersis.insertToApplicationCreditScore(CMSCon, applicationId, ruleCode, creditScore);

            CMSCon.commit();
            return status;
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

    public boolean updateApplicationCreditScore(String applicationId, String ruleCode, String creditScore) throws Exception {
        CreditScoreRuleBean crediScoreRule = new CreditScoreRuleBean();
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            boolean status = creditScorePersis.updateApplicationCreditScore(CMSCon, applicationId, ruleCode, creditScore);

            CMSCon.commit();
            return status;
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

    public boolean isRecordExist(String applicationId, String ruleCode) throws Exception {
        try {
            creditScorePersis = new CreditScorePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            boolean status = creditScorePersis.isRecordExist(CMSCon, applicationId, ruleCode);

            CMSCon.commit();
            return status;
        } catch (SQLException e) {
            try {
                CMSCon.rollback();
                return false;
            } catch (SQLException ex) {
                return false;
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
}
