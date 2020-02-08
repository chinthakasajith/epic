/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.recovery.callcenter.persistance;

import com.epic.cms.recovery.callcenter.bean.CollectionBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class CollectionAssignmentPersistance {

    private ResultSet rs;
    private HashMap<String, String> queueList = null;
    private HashMap<String, String> userList = null;
    private List<CollectionBean> collectionList = null;

    public HashMap<String, String> getQueueList(Connection CMSCon) throws Exception {
        PreparedStatement stmt = null;
        queueList = new HashMap<String, String>();
        try {

            stmt = CMSCon.prepareStatement("SELECT QUEUECODE,DESCRIPTION "
                    + " FROM COLLECTORQUEUE WHERE STATUS = ?");

            stmt.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = stmt.executeQuery();

            while (rs.next()) {

                queueList.put(rs.getString("QUEUECODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return queueList;
    }

    public List<CollectionBean> getSelectedCollectionList(Connection CMSCon, String queueId) throws Exception {
        PreparedStatement stmt = null;
        collectionList = new ArrayList<CollectionBean>();
        try {

            stmt = CMSCon.prepareStatement("SELECT C.COLLECTIONID,C.ACCOUNTNO,CC.DESCRIPTION AS CCDESCRIPTION,"
                    + " C.CARDNUMBER,S.DESCRIPTION SDESCRIPTION ,C.INDATE"
                    + " FROM COLLECTION C,STATUS S,COLLECTORCASE CC"
                    + " WHERE ASSIGNSTATUS = ? AND C.STATUS= S.STATUSCODE AND C.CASEID =CC.CASETYPECODE"
                    + " AND CASEID IN (SELECT CASEID FROM COLLECTORQUEUECASE WHERE QUEUEID = ?)");

            stmt.setString(1, StatusVarList.INITIAL_STATUS);
            stmt.setString(2, queueId);
            rs = stmt.executeQuery();

            while (rs.next()) {

                CollectionBean bean = new CollectionBean();

                bean.setCollectionId(rs.getString("COLLECTIONID"));
                bean.setAccountNo(rs.getString("ACCOUNTNO"));
                bean.setCaseId(rs.getString("CCDESCRIPTION"));
                bean.setCardNo(rs.getString("CARDNUMBER"));
                bean.setStatus(rs.getString("SDESCRIPTION"));
                bean.setInDate(rs.getDate("INDATE"));

                collectionList.add(bean);

            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return collectionList;
    }

    public HashMap<String, String> getAssignUserList(Connection CMSCon, String queueId) throws Exception {
        PreparedStatement stmt = null;
        userList = new HashMap<String, String>();
        try {
            stmt = CMSCon.prepareStatement("SELECT USERNAME FROM SYSTEMUSER "
                    + " WHERE USERROLECODE = (SELECT ASSIGNUSERROLE FROM COLLECTORQUEUE WHERE QUEUECODE = ?)");

            stmt.setString(1, queueId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                userList.put(rs.getString("USERNAME"), rs.getString("USERNAME"));
            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return userList;
    }

    public boolean assignCollection(Connection CMSCon, String[] assignList, String assignUser) throws Exception {
        PreparedStatement stmt = null;
        boolean success = false;
        try {
            stmt = CMSCon.prepareStatement("UPDATE COLLECTION SET ASSIGNEDUSER = ?,ASSIGNSTATUS = ? "
                    + " WHERE COLLECTIONID=?");

            for (int i = 0; i < assignList.length; i++) {

                stmt.setString(1, assignUser);
                stmt.setString(2, StatusVarList.COMPLETE_STATUS);
                stmt.setString(3, assignList[i]);

                stmt.executeUpdate();
                success = true;
            }

        } catch (SQLException sqlex) {
            throw sqlex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
                throw e;
            }
        }
        return success;
    }
}
