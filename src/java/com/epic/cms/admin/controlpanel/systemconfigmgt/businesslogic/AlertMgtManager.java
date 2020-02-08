/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.AlertBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.AlertMgtPersistance;
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
public class AlertMgtManager {

    private Connection cmsCon;
    private AlertMgtPersistance altobj;
    private List<StatusBean> statusList = null;
    private AlertBean alertBean;

    /**
     * to take alert list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<AlertBean> getAlerts() throws SQLException, Exception {

        try {
            List<AlertBean> alertList = null;
            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            alertList = altobj.getAlerts(cmsCon);

            cmsCon.commit();
            return alertList;
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

            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = altobj.getStatustList(cmsCon);

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
     * to add a new alert
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewAlert( AlertBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = altobj.addNewAlert(cmsCon, bean);


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
     * to delete a alert
     * @param id
     * @param systemAuditBean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteAlert(String id) throws SQLException, Exception {
        int rowCount = -1;
        try {
            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            rowCount = altobj.deleteAlert(cmsCon, id);
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
     * to update alert details
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateAlert(AlertBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = altobj.updateAlert(cmsCon, bean);
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
     * to view one selected alert details
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public AlertBean viewSelectedAlert(String id) throws SQLException, Exception {

        try {
            altobj = new AlertMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            alertBean = altobj.viewSelectedAlertDetails(cmsCon, id);
            cmsCon.commit();
            return alertBean;
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
