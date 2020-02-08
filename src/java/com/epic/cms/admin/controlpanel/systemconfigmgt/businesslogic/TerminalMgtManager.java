package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.TerminalMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class TerminalMgtManager {
    private Connection cmsCon;
    private TerminalMgtPersistance terPersistance;
    private TerminalBean terminalBean;
    private List<StatusBean> statusList = null;
    private SystemAuditManager sysAuditManager;
    
 public List<TerminalBean> getTerminalDetails() throws SQLException, Exception {
     try {
         
         List<TerminalBean> terminalList = null;
         terPersistance = new TerminalMgtPersistance();
         cmsCon = DBConnection.getConnection();
         cmsCon.setAutoCommit(false);
         
         terminalList = terPersistance.getTerminalDetails(cmsCon);
         cmsCon.commit();
         
          return terminalList;  
     } catch (SQLException ex) {
         try {
             cmsCon.rollback();
             throw ex;
         } catch (SQLException e) {
             throw e;
         }
     } finally {
         if (cmsCon != null) {
             try {
                 cmsCon.close();
             } catch (SQLException e) {
                 throw e;
             }
         }
     }
 }
 
 public int insertNewCode(SystemAuditBean systemAuditBean, TerminalBean bean) throws SQLException, Exception {
     int rowCount = -1;
     
     try {
         
         sysAuditManager = new SystemAuditManager();
         terPersistance = new TerminalMgtPersistance();
         cmsCon = DBConnection.getConnection();
         cmsCon.setAutoCommit(false);
         
         rowCount = terPersistance.insertNewCode(cmsCon, bean);
         sysAuditManager.insertAudit(systemAuditBean, cmsCon);
         
         cmsCon.commit();
         
         return rowCount;
         
     }catch (SQLException ex) {
         try {
             cmsCon.rollback();
             throw ex;
         } catch (SQLException e) {
             throw e;
         }
     } finally {
         if (cmsCon != null) {
             try {
                 cmsCon.close();
             } catch (SQLException e) {
                 throw e;
             }
         }
     }
 }
 
 public TerminalBean viewSelectedData(String id) throws Exception {
     try {
         terPersistance = new TerminalMgtPersistance();
         cmsCon = DBConnection.getConnection();
         cmsCon.setAutoCommit(false);
         
         terminalBean = terPersistance.viewSelectedData(cmsCon, id);
         cmsCon.commit();
         
         return terminalBean;
         
     } catch (SQLException ex) {
         try {
             cmsCon.rollback();
             throw ex;
         } catch (SQLException ex2) {
             throw ex2;
         }
     } finally {
         if (cmsCon != null) {
             try {
                 cmsCon.close();
             } catch (SQLException e) {
                 throw e;
             }
         }
     }
 }
//
//    public int updateTerminalInfo(SystemAuditBean systemAuditBean, TerminalBean bean) throws Exception {
//        
//    }
 
    public int updateTerminalInfo(SystemAuditBean systemAuditBean, TerminalBean bean) throws Exception {
        int rowCount = -1;
       
        try {
            
            sysAuditManager = new SystemAuditManager();
            terPersistance = new TerminalMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            rowCount = terPersistance.updateTerminalInfo(cmsCon, bean);
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            
            cmsCon.commit();
            return rowCount;
            
            
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlex2) {
                    throw sqlex2;
                }
            }
        }
    }

   
      public int deleteTerminalInfo(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            
            terPersistance = new TerminalMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = terPersistance.deleteTerminalInfo(cmsCon, id);
            
            cmsCon.commit();
            
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }
}
