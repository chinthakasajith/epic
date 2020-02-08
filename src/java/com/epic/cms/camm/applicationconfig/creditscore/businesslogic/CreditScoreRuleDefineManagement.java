/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.application.common.persistance.OperatorPersistance;
import com.epic.cms.camm.applicationconfig.creditscore.bean.CreditScoreRuleBean;
import com.epic.cms.camm.applicationconfig.creditscore.persistance.CreditScoreRuleDefinePersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author upul
 */
public class CreditScoreRuleDefineManagement {


 
    
       private OperatorPersistance operatorPersistance;
       private CreditScoreRuleDefinePersistance ruleDefinePersistance;
       private Connection CMSCon;
       private SystemAuditManager sysAuditManager;
       private List<OperatorBean> operatorLst;
       private List<CreditScoreRuleBean> scoreRuleBeans;
       private CreditScoreRuleBean scoreRuleBean;
    
       
       
 
       /**
        * getAllOperatorList
        * @return
        * @throws Exception 
        */
 public List<OperatorBean> getAllOperatorList() throws Exception {
        try {
            operatorPersistance = new OperatorPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            operatorLst = operatorPersistance.getAllOperatorList(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return operatorLst;
    }

    /**
     * getAllCreditScoreRuleDefDetails
     * @return
     * @throws Exception 
     */
    public List<CreditScoreRuleBean> getAllCreditScoreRuleDefDetails() throws Exception {
        try {
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            scoreRuleBeans = ruleDefinePersistance.getAllCreditScoreRuleDefDetails(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return scoreRuleBeans;
    }

      
  
  
  //////////////////////////////////////
  /**
   * 
   * @return
   * @throws Exception 
   */
 
   public CreditScoreRuleBean getCreditScoreRuleDefFromRuleIDDetails(String ruleId) throws Exception {
        try {
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            scoreRuleBean = ruleDefinePersistance.getCreditScoreRuleDefFromRuleIDDetails(CMSCon, ruleId);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return scoreRuleBean;
    }    
    
    
  
  /**
   * getAllDistinctCreditScoreRules
   * @return
   * @throws Exception 
   */
  public List<CreditScoreRuleBean> getAllDistinctActiveCreditScoreRules() throws Exception {
        try {
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            scoreRuleBeans = ruleDefinePersistance.getAllDistinctCreditScoreRules(CMSCon);


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return scoreRuleBeans;
    }

    /**
     * insertNewCreditScoreRule
     * @param bean
     * @param auditBean
     * @return
     * @throws Exception 
     */
    public int insertNewCreditScoreRule(CreditScoreRuleBean bean, SystemAuditBean auditBean) throws Exception {
        int result = -1;
        boolean success = false;
        try {
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);



            //insert audittrace
            result = sysAuditManager.insertAudit(auditBean, CMSCon);

            if (result == 1) {
                //set updated user
                bean.setLastUpdatedUser(auditBean.getUserName());

                result = ruleDefinePersistance.insertNewCreditScoreRule(bean, CMSCon);
            } else {
            }


            CMSCon.commit();

        } catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    }

    /**
     * delete credit score rule by specific id
     * @param ID
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteCreditScoreField(String ID, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int row = -1;

        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sysAuditManager = new SystemAuditManager();
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            row = ruleDefinePersistance.deleteCreditScoreRule(CMSCon, ID);
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
     * @param bean
     * @param auditBean
     * @return
     * @throws Exception 
     */
        public int updateCreditScoreRuleDetail(CreditScoreRuleBean bean,SystemAuditBean auditBean) throws Exception {
         int result = -1;
         boolean success = false;
        try {
            ruleDefinePersistance = new CreditScoreRuleDefinePersistance();
            sysAuditManager=new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
           
            
            
             //insert audittrace
            result= sysAuditManager.insertAudit(auditBean, CMSCon);
            
            if(result==1){
                //set updated user
             bean.setLastUpdatedUser(auditBean.getUserName());
            
             result= ruleDefinePersistance.updateCreditScoreRuleDetail(CMSCon,bean);
            }
            else{
                
            }


            CMSCon.commit();

        } catch (SQLException ex) {
            CMSCon.rollback();
            throw ex;
        }  catch (Exception ex) {
            CMSCon.rollback();
            throw ex;
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return result;
    } 
    
    
    
    
    
    
}
