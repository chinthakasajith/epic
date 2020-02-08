/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.businesslogic;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.persistance.ServiceCodeMgtPersistance;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.config.DBConnectionToOnlineDB;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ServiceCodeMgtManager {

    private ServiceCodeMgtPersistance servicePersis = null;
    private List<ServiceCodeBean> serviceList = null;
    private Connection CMSCon;
    private Connection CMSOnline;
    private SystemAuditManager sysAuditManager;

    public List<ServiceCodeBean> getServiceCodeDetails() throws Exception {
        try {
            serviceList = new ArrayList<ServiceCodeBean>();
            servicePersis = new ServiceCodeMgtPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            serviceList = servicePersis.getServiceCodeDetails(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return serviceList;
    }

    public boolean serviceCodeInsert(ServiceCodeBean serviceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            servicePersis = new ServiceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            flag = servicePersis.serviceCodeInsert(serviceBean, CMSCon);

            this.setOnlineStatusValue(serviceBean);

            flag = servicePersis.serviceCodeInsertToOnline(serviceBean, CMSOnline);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);

            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return flag;
    }

    public boolean serviceCodeUpdate(ServiceCodeBean serviceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            servicePersis = new ServiceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);


            flag = servicePersis.serviceCodeUpdate(serviceBean, CMSCon);
            this.setOnlineStatusValue(serviceBean);

            flag = servicePersis.serviceCodeUpdateOnine(serviceBean, CMSOnline);

            sysAuditManager.insertAudit(systemAuditBean, CMSCon);


            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return flag;
    }

    public boolean serviceCodeDelete(ServiceCodeBean serviceBean, SystemAuditBean systemAuditBean) throws Exception {
        boolean flag = false;
        try {

            servicePersis = new ServiceCodeMgtPersistance();
            sysAuditManager = new SystemAuditManager();
            CMSCon = DBConnection.getConnection();
            CMSOnline = DBConnectionToOnlineDB.getConnection();

            CMSCon.setAutoCommit(false);
            CMSOnline.setAutoCommit(false);

            flag = servicePersis.serviceCodeDelete(serviceBean, CMSCon);
            flag = servicePersis.serviceCodeDeleteOnline(serviceBean, CMSOnline);
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);


            CMSCon.commit();
            CMSOnline.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                CMSOnline.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
            DBConnectionToOnlineDB.dbConnectionClose(CMSOnline);
        }
        return flag;
    }

    private ServiceCodeBean setOnlineStatusValue(ServiceCodeBean serviceBean) throws Exception {

        try {
            /////internationnal status
            if (serviceBean.getInternationalStatus().equals("YES")) {

                serviceBean.setInternationalStatus("1");

            } else {

                serviceBean.setInternationalStatus("2");
            }
            //////atm status
            if (serviceBean.getAtmStatus().equals("YES")) {

                serviceBean.setAtmStatus("1");

            } else {

                serviceBean.setAtmStatus("2");
            }

            //////////// pin status
            if (serviceBean.getPinStatus().equals("YES")) {

                serviceBean.setPinStatus("1");

            } else {

                serviceBean.setPinStatus("2");
            }
            //////////////// auth status
            if (serviceBean.getAuthStatus().equals("YES")) {

                serviceBean.setAuthStatus("1");

            } else {

                serviceBean.setAuthStatus("2");
            }

            ////////////status
            if (serviceBean.getStatus().equals("ACT")) {

                serviceBean.setStatus("1");

            } else {

                serviceBean.setStatus("2");
            }



        } catch (Exception ex) {
            throw ex;
        }
        return serviceBean;
    }
}
