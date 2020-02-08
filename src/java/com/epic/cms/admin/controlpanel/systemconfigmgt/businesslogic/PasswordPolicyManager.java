/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.PasswordPolicyBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.PasswordPolicyPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * this class use for wrote all the manager methods for password policy process
 * @author ayesh
 */
public class PasswordPolicyManager {

    private static PasswordPolicyManager setInstance;
    private Connection CMSCon;
    private PasswordPolicyPersistance passwdPolicyPerObj = null;
    private SystemAuditManager sysAuditManager;

    private PasswordPolicyManager() {
    }

    /**
     * get PasswordPolicyManager instance
     * @return 
     */
    public static synchronized PasswordPolicyManager getPasswordPolicyManager() {
        if (setInstance == null) {
            setInstance = new PasswordPolicyManager();
        }
        return setInstance;
    }

    /**
     * get all details about password policy 
     * @return  PasswordPolicyBean
     */
    public PasswordPolicyBean getPasswordPolicyDetails() throws SQLException, Exception {
        try {
            PasswordPolicyBean passBean = null;
            passwdPolicyPerObj = new PasswordPolicyPersistance();
           
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
          
            passBean = passwdPolicyPerObj.getAllDetails(CMSCon);
            CMSCon.commit();
            return passBean;
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
     *  update password policy
     * @param passBean - password policy info bean
     * @return row - updated row count.if it equal to 1 successfully update
     * @throws SQLException
     * @throws Exception 
     */
    public int updatePasswordPolicy(PasswordPolicyBean passBean, SystemAuditBean systemAuditBean) throws SQLException, Exception {
        try {
            int row = -1;
            passwdPolicyPerObj = new PasswordPolicyPersistance();
            sysAuditManager = new SystemAuditManager();

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            row = passwdPolicyPerObj.updatePasswordPolicy(CMSCon, passBean);
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
}
