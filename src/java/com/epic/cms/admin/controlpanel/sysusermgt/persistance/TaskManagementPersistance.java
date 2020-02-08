/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahesh_m
 */

public class TaskManagementPersistance {
    private ResultSet rs;
    private List<TaskBean> taskDetails;
    
    public List<TaskBean> gettaskDetails(Connection con) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT T.TASKCODE,T.DESCRIPTION,T.SORTKEY,T.STATUS,S.DESCRIPTION FROM TASK T,STATUS S WHERE T.STATUS = S.STATUSCODE ");

            
            taskDetails = new ArrayList<TaskBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                TaskBean task = new TaskBean();

                task.setTaskCode(rs.getString(1));
                task.setDescription(rs.getString(2));
                task.setSortKey(Integer.toString(rs.getInt(3)));
                task.setStatus(rs.getString(4));
                task.setStatusDes(rs.getString(5));
                
                
                taskDetails.add(task);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return taskDetails;
    }
    
     public boolean insertTask(Connection con, TaskBean task) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO TASK(TASKCODE,DESCRIPTION,SORTKEY,STATUS,LASTUPDATEDUSER,LASTUPDATEDTIME) VALUES(?,?,?,?,?,SYSDATE) ");
            insertStat.setString(1, task.getTaskCode());
            insertStat.setString(2, task.getDescription());
            insertStat.setInt(3, Integer.parseInt(task.getSortKey()));
            insertStat.setString(4, task.getStatus());
            insertStat.setString(5, task.getLastUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

     
     public boolean updateTask(Connection con, TaskBean task) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE TASK T SET T.DESCRIPTION=?,T.SORTKEY=?,T.STATUS=?,T.LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE T.TASKCODE=? ");

            updateStat.setString(1, task.getDescription());
            updateStat.setInt(2, Integer.parseInt(task.getSortKey()));
            updateStat.setString(3, task.getStatus());
            updateStat.setString(4, task.getLastUpdatedUser());
            updateStat.setString(5, task.getTaskCode());

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }
     
     public boolean deleteTask(Connection con, TaskBean task) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE TASK WHERE TASKCODE=? ");
            deleteStat.setString(1, task.getTaskCode());

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
