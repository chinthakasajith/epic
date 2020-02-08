package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalModelBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.TerminalMgtPersistance;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.TerminalModelMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jeevan
 */
public class TerminalModelMgtManager {
    private Connection cmsCon;
    private TerminalModelMgtPersistance terPersistance;
    private TerminalModelBean terminalBean;
    private List<StatusBean> statusList = null;
    private SystemAuditManager sysAuditManager;
    
    public List<TerminalModelBean> getTerminalModel() throws Exception {
        try {
            
            List<TerminalModelBean> terModelList = null;
            terPersistance = new TerminalModelMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            terModelList = terPersistance.getTerminalModel(cmsCon);
            cmsCon.commit();
            
            return terModelList;
            
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

   
    
     public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            terPersistance = new TerminalModelMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = terPersistance.getStatustList(cmsCon);

            cmsCon.commit();
            return statusList;
        } catch (Exception e) {
            try {
                cmsCon.rollback();
                throw e;
            } catch (SQLException sqx) {
                throw sqx;
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

    public List<TerminalModelBean> getManufacture() throws Exception {
         try {
            
            List<TerminalModelBean> terModelList = null;
            terPersistance = new TerminalModelMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            terModelList = terPersistance.getManufacture(cmsCon);
            cmsCon.commit();
            
            return terModelList;
            
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

   
   public int inserModelData(SystemAuditBean systemAuditBean, TerminalModelBean bean) throws SQLException, Exception {
     int rowCount = -1;
     
     try {
         
         sysAuditManager = new SystemAuditManager();
         terPersistance = new TerminalModelMgtPersistance();
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
     } catch (Exception ex) {
         try {
             cmsCon.rollback();
             throw ex;
         } catch (SQLException e) {
             throw e;
         }
     }finally {
         if (cmsCon != null) {
             try {
                 cmsCon.close();
             } catch (SQLException e) {
                 throw e;
             }
         }
     }
 }

    public TerminalModelBean viewSelectedData(String id) throws Exception {
     try {
         terPersistance = new TerminalModelMgtPersistance();
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

   
   public int updateTerminalModelInfo(SystemAuditBean systemAuditBean, TerminalModelBean bean) throws Exception {
        int rowCount = -1;
       
        try {
            
            sysAuditManager = new SystemAuditManager();
            terPersistance = new TerminalModelMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            rowCount = terPersistance.updateTerminalModelInfo(cmsCon, bean);
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


     public int deleteModelInfo(String id, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        int rowCount = -1;
        try {
            
            sysAuditManager = new SystemAuditManager();
            terPersistance = new TerminalModelMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            
            sysAuditManager.insertAudit(systemAuditBean, cmsCon);
            rowCount = terPersistance.deleteModelInfo(cmsCon, id);
            
            cmsCon.commit();
            
        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (SQLException sqlex) {
                throw sqlex;
            }
        }finally {
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
