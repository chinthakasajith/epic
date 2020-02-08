/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.collection.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance.QueuePersistance;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.CaseTypeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.bean.QueueBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.collection.persistance.LadderPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class QueueManager {

    private QueuePersistance queuePersistance = null;
    private Connection CMSCon;
    private ArrayList<CaseTypeBean> caseBeans;
    private Map<String, String> status;

    public Map<String, String> getAllCasesMap() throws SQLException, Exception {
        Map<String, String> cases = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cases = queuePersistance.getAllCasesMap(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return cases;
    }

    public Map<String, String> getStatus() throws SQLException, Exception {
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            status = queuePersistance.getStatus(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return status;
    }

    public boolean create(QueueBean queue, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;
        boolean successQueue = false;
        boolean successQueueCase = false;

        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            successQueue = queuePersistance.createQueue(queue, CMSCon);
            successQueueCase = queuePersistance.addQueueCases(queue, CMSCon);

            CMSCon.commit();
            success = successQueue && successQueueCase;

        } catch (Exception ex) {
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
        return success;
    }

    public Map<String, String> getAllUserRoles() throws SQLException, Exception {
        Map<String, String> userRoles = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            userRoles = queuePersistance.getAllUserRoles(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return userRoles;
    }

    public ArrayList<CaseTypeBean> getAllCases() throws Exception {
        ArrayList<CaseTypeBean> cases = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cases = queuePersistance.getAllCases(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return cases;
    }

    public ArrayList<QueueBean> getAllQueues() throws Exception {
        ArrayList<QueueBean> queues = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            queues = queuePersistance.getAllQueues(CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return queues;
    }

    public boolean deleteQueue(String code, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean result = false;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            result = queuePersistance.deleteQueue(code, CMSCon);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return result;
    }

    public List<CaseTypeBean> getAssignedCases(String code) throws Exception {
        List<CaseTypeBean> cases = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cases = queuePersistance.getAssignedCases(CMSCon, code);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return cases;
    }

    public List<CaseTypeBean> getNotAssignedCases(String code) throws Exception {
        List<CaseTypeBean> cases = null;
        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cases = queuePersistance.getNotAssignedCaseTypeList(CMSCon, code);
            CMSCon.commit();
        } catch (Exception ex) {
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
        return cases;
    }

    public boolean updateQueue(QueueBean queue, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        boolean success = false;

        try {
            queuePersistance = new QueuePersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            success = queuePersistance.updateQueue(queue, systemAuditBean, CMSCon);

            if (!success) {
                CMSCon.rollback();
            }
            CMSCon.commit();
        } catch (Exception ex) {
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
        return success;
    }
}
