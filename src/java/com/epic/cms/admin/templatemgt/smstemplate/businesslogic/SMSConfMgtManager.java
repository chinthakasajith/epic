/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.smstemplate.businesslogic;

import com.epic.cms.admin.templatemgt.smstemplate.bean.SMSBean;
import com.epic.cms.admin.templatemgt.smstemplate.persistance.SMSConfMgtPersistance;
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
public class SMSConfMgtManager {

    private Connection cmsCon;
    private SMSConfMgtPersistance smsobj;
    private List<StatusBean> statusList = null;
    private SMSBean smsConfBean;

    /**
     * to take sms conf list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<SMSBean> getSMSConfs() throws SQLException, Exception {

        try {
            List<SMSBean> smsList = null;
            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            smsList = smsobj.getAllSMSTemplates(cmsCon);

            cmsCon.commit();
            return smsList;
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

            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = smsobj.getStatustList(cmsCon);

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
     * to add a new sms conf
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewSMSConf( SMSBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = smsobj.addNewSMSConf(cmsCon, bean);
            
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
     * to delete a smstemplate
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteSMSTemplate(String id) throws SQLException, Exception {
        int rowCount = -1;
        try {
            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            rowCount = smsobj.deleteSMSConf(cmsCon, id);
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
     * to update sms conf details
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateSMSTempConf(SMSBean bean) throws SQLException, Exception {

        int rowCount = 0;
        try {
            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = smsobj.updateSMSlConf(cmsCon, bean);
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
     * to view one selected sms conf details
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public SMSBean viewSelectedSMSTempConf(String id) throws SQLException, Exception {

        try {
            smsobj = new SMSConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            smsConfBean = smsobj.viewSelectedSMSlConf(cmsCon, id);
            cmsCon.commit();
            return smsConfBean;
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
