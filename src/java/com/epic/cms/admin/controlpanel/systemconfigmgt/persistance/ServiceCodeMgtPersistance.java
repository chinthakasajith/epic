/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.ServiceCodeBean;
import com.epic.cms.system.util.session.SessionVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ServiceCodeMgtPersistance {

    private ResultSet rs;
    private SessionVarList sessionVarlist;

    public List<ServiceCodeBean> getServiceCodeDetails(Connection con) throws Exception {
        PreparedStatement getAllServiceCode = null;
        List<ServiceCodeBean> serviceList = new ArrayList<ServiceCodeBean>();
        try {
            getAllServiceCode = con.prepareStatement("SELECT SC.SERVICECODE,SC.DESCRIPTION,SC.INTERNATIONALSTATUS,"
                    + " SC.ATMSTATUS,SC.PINSTATUS,SC.AUTHSTATUS,SC.STATUS,S.DESCRIPTION AS SDESCRIPTION,"
                    + " SC.LASTUPDATEDUSER,SC.LASTUPDATEDTIME,SC.CREATEDTIME"
                    + " FROM SERVICECODE SC,STATUS S"
                    + " WHERE S.STATUSCODE=SC.STATUS");

            rs = getAllServiceCode.executeQuery();

            while (rs.next()) {

                ServiceCodeBean service = new ServiceCodeBean();

                service.setServiceCode(rs.getString("SERVICECODE"));
                service.setDescription(rs.getString("DESCRIPTION"));
                service.setInternationalStatus(rs.getString("INTERNATIONALSTATUS"));
                service.setAtmStatus(rs.getString("ATMSTATUS"));
                service.setPinStatus(rs.getString("PINSTATUS"));
                service.setAuthStatus(rs.getString("AUTHSTATUS"));
                service.setStatus(rs.getString("STATUS"));
                service.setStatusDes(rs.getString("SDESCRIPTION"));
                service.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                service.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                service.setCreateTime(rs.getDate("CREATEDTIME"));




                serviceList.add(service);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllServiceCode.close();

        }
        return serviceList;
    }

    public boolean serviceCodeInsert(ServiceCodeBean serviceBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO SERVICECODE"
                    + " (SERVICECODE,DESCRIPTION,INTERNATIONALSTATUS,ATMSTATUS,PINSTATUS,AUTHSTATUS,STATUS,"
                    + " LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE) ");



            insertStat.setString(1, serviceBean.getServiceCode());
            insertStat.setString(2, serviceBean.getDescription());
            insertStat.setString(3, serviceBean.getInternationalStatus());
            insertStat.setString(4, serviceBean.getAtmStatus());
            insertStat.setString(5, serviceBean.getPinStatus());
            insertStat.setString(6, serviceBean.getAuthStatus());
            insertStat.setString(7, serviceBean.getStatus());
            insertStat.setString(8, serviceBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean serviceCodeInsertToOnline(ServiceCodeBean serviceBean, Connection conOnline) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_SERVICE_CODE"
                    + " (CODE,DESCRIPTION,INTERNATIONAL,ATM,PIN,AUTHERIZATION,STATUS)"
                    + " VALUES(?,?,?,?,?,?,?) ");



            insertStat.setString(1, serviceBean.getServiceCode());
            insertStat.setString(2, serviceBean.getDescription());
            insertStat.setInt(3, Integer.parseInt(serviceBean.getInternationalStatus()));
            insertStat.setInt(4, Integer.parseInt(serviceBean.getAtmStatus()));
            insertStat.setInt(5, Integer.parseInt(serviceBean.getPinStatus()));
            insertStat.setInt(6, Integer.parseInt(serviceBean.getAuthStatus()));
            insertStat.setInt(7, Integer.parseInt(serviceBean.getStatus()));

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean serviceCodeUpdate(ServiceCodeBean serviceBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE SERVICECODE SET "
                    + " DESCRIPTION=?,INTERNATIONALSTATUS=?,ATMSTATUS=?,PINSTATUS=?,AUTHSTATUS=?,STATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE SERVICECODE=? ");

            updateStat.setString(1, serviceBean.getDescription());
            updateStat.setString(2, serviceBean.getInternationalStatus());
            updateStat.setString(3, serviceBean.getAtmStatus());
            updateStat.setString(4, serviceBean.getPinStatus());
            updateStat.setString(5, serviceBean.getAuthStatus());
            updateStat.setString(6, serviceBean.getStatus());
            updateStat.setString(7, serviceBean.getLastUpdatedUser());

            updateStat.setString(8, serviceBean.getServiceCode());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean serviceCodeUpdateOnine(ServiceCodeBean serviceBean, Connection conOnine) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnine.prepareStatement("UPDATE ECMS_ONLINE_SERVICE_CODE SET "
                    + " DESCRIPTION=?,INTERNATIONAL=?,ATM=?,PIN=?,AUTHERIZATION=?,STATUS=?"
                    + " WHERE CODE=? ");

            updateStat.setString(1, serviceBean.getDescription());
            updateStat.setInt(2, Integer.parseInt(serviceBean.getInternationalStatus()));
            updateStat.setInt(3, Integer.parseInt(serviceBean.getAtmStatus()));
            updateStat.setInt(4, Integer.parseInt(serviceBean.getPinStatus()));
            updateStat.setInt(5, Integer.parseInt(serviceBean.getAuthStatus()));
            updateStat.setInt(6, Integer.parseInt(serviceBean.getStatus()));

            updateStat.setString(7, serviceBean.getServiceCode());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean serviceCodeDelete(ServiceCodeBean serviceBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE SERVICECODE WHERE SERVICECODE=?");

            deleteStat.setString(1, serviceBean.getServiceCode());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }

    public boolean serviceCodeDeleteOnline(ServiceCodeBean serviceBean, Connection conOnline) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = conOnline.prepareStatement("DELETE ECMS_ONLINE_SERVICE_CODE WHERE CODE=?");

            deleteStat.setString(1, serviceBean.getServiceCode());


            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            deleteStat.close();
        }
        return success;
    }
}
