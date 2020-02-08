/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.emailtemplate.businesslogic;

import com.epic.cms.admin.templatemgt.emailtemplate.bean.EmailBean;
import com.epic.cms.admin.templatemgt.emailtemplate.persistance.EmailConfMgtPersistance;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith
 */
public class EmailConfMgtManager {

    private Connection cmsCon;
    private EmailConfMgtPersistance emailobj;
    private List<StatusBean> statusList = null;
    private EmailBean emailConfBean;

    /**
     * to take email conf list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<EmailBean> getEmailConfs() throws SQLException, Exception {

        try {
            List<EmailBean> emailList = null;
            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            emailList = emailobj.getAllEmailConfs(cmsCon);

            cmsCon.commit();
            return emailList;
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

    /**
     * to take status list(active and deactive)
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = emailobj.getStatustList(cmsCon);

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

    /**
     * to add a new email conf
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewEmailConf( EmailBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = emailobj.addNewEmailConf(cmsCon, bean);
            
            cmsCon.commit();
            return rowCount;
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

    /**
     * to delete a email template
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteEmailTemplate(String id) throws SQLException, Exception {
        int rowCount = -1;
        try {
            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            rowCount = emailobj.deleteEmailConf(cmsCon, id);
            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
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
        return rowCount;
    }

    /**
     * to update email conf details
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateEmailConf(EmailBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = emailobj.updateEmailConf(cmsCon, bean);
            cmsCon.commit();
            
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
        return rowCount;
    }

    /**
     * to view one selected email conf details
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public EmailBean viewSelectedEmailConf(String id) throws SQLException, Exception {

        try {
            emailobj = new EmailConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            emailConfBean = emailobj.viewSelectedEmailConf(cmsCon, id);
            cmsCon.commit();
            return emailConfBean;
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
    
    
}
