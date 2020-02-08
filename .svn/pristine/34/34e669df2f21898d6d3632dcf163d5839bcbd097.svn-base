/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.securityquesmgt.businesslogic;

import com.epic.cms.admin.controlpanel.securityquesmgt.bean.SecurityQuestionBean;
import com.epic.cms.admin.controlpanel.securityquesmgt.persistance.SecurityQuestionPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nisansala
 */
public class SecurityQuestionManager {

    //initializing variables
    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private SecurityQuestionPersistance secQuesPer;

    public List<SecurityQuestionBean> getAllSecurityQuestions() throws SQLException, Exception {

        try {
            List<SecurityQuestionBean> questionList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();

            //assign the merchant details to the erchant bean instance
            questionList = secQuesPer.getAllSecurityQuestion(CMSCon);

            CMSCon.commit();
            return questionList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }
    
    public List<SecurityQuestionBean> getAllACQSecurityQuestions() throws SQLException, Exception {

        try {
            List<SecurityQuestionBean> questionList = null;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();

            //assign the merchant details to the erchant bean instance
            questionList = secQuesPer.getAllACQSecurityQuestions(CMSCon);

            CMSCon.commit();
            return questionList;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public SecurityQuestionBean viewOneSecurityQuestion(String id) throws SQLException, Exception {
        SecurityQuestionBean oneBean = null;
        try {
            secQuesPer = new SecurityQuestionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            oneBean = secQuesPer.viewOneSecurityQuestion(CMSCon, id);
            CMSCon.commit();
            return oneBean;
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
    
    //to Acquire security quiz
    public SecurityQuestionBean viewOneAcquireSecurityQuestion(String id) throws SQLException, Exception {
        SecurityQuestionBean oneBean = null;
        try {
            secQuesPer = new SecurityQuestionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            oneBean = secQuesPer.viewOneAcquireSecurityQuestion(CMSCon, id);
            CMSCon.commit();
            return oneBean;
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

    //getQuestionNumber
    public int getQuestionNumber() throws SQLException, Exception {
        int num = -1;
        try {
            secQuesPer = new SecurityQuestionPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            num = secQuesPer.getQuestionNumber(CMSCon);
            CMSCon.commit();
            return num;
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

    public boolean deleteSecurityQuestion(String questionNo, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();
            secQuesPer.deleteSecurityQuestion(CMSCon, questionNo);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            //'row' indicates the success of deletion
            success = true;
        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public Boolean insertNewQuestion(SecurityQuestionBean question, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {

            //if the insert become success row will return 1
            Boolean success = false;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();
            success = secQuesPer.insertNewQuestion(question, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();

            return success;

        } catch (SQLException ex) {

            //throws an exception when rollback fails 
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                //throws an exception if some error occurs when closing the connection
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public boolean updateSecurityQuestion(SecurityQuestionBean question, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        try {
            int row = -1;
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();

            row = secQuesPer.updateSecurityQuestion(question, CMSCon);
            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            success = true;

            //row will indicate the success of updation 

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return success;
    }

    public HashMap<String, String> tablesToCardCategory(String cardCategory) {
        HashMap<String, String> tables;
        secQuesPer = new SecurityQuestionPersistance();
        tables = secQuesPer.tablesToCardCategory(cardCategory);
        return tables;
    }
    
    //to acquire seq quiz
     public HashMap<String, String> tablesToAcquireQuestions(String qtype) {
        HashMap<String, String> tables;
        secQuesPer = new SecurityQuestionPersistance();
        tables = secQuesPer.tablesToAcquireQuestions(qtype);
        return tables;
    }

    public HashMap<String, String> getColumnNames(String tableName) throws SQLException, Exception {
        HashMap<String, String> columnNames;

        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();

            //assign the merchant details to the erchant bean instance

            columnNames = secQuesPer.columnNames(CMSCon, tableName);
            CMSCon.commit();
            return columnNames;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public List<String> getColumnName(String tableName) throws SQLException, Exception {
        List<String> columnNames;

        try {

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            secQuesPer = new SecurityQuestionPersistance();

            //assign the merchant details to the erchant bean instance

            columnNames = secQuesPer.columnName(CMSCon, tableName);
            CMSCon.commit();
            return columnNames;

        } catch (SQLException ex) {
            //throws an exception if the rollback fails
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            //throws an exception if some error occurs when closing the connection
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
