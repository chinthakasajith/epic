
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.AssignedTasksBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageTaskBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.system.util.variable.StatusVarList;
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
public class PageManagementPersistance {

    private ResultSet rs = null;
    private List<PageTaskBean> pageTaskList = null;

    public List<TaskBean> getTaskDetails(Connection con) throws SQLException, Exception {


        List<TaskBean> taskBeanList = new ArrayList<TaskBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT T.TASKCODE,T.DESCRIPTION,T.SORTKEY,T.STATUS,S.DESCRIPTION,T.LASTUPDATEDUSER,T.LASTUPDATEDTIME,T.CREATETIME FROM TASK T,STATUS S WHERE T.STATUS = S.STATUSCODE ORDER BY T.DESCRIPTION";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TaskBean taskbean = new TaskBean();
                taskbean.setTaskCode(rs.getString(1));
                taskbean.setDescription(rs.getString(2));
                taskbean.setSortKey(rs.getString(3));
                taskbean.setStatus(rs.getString(4));
                taskbean.setStatusDes(rs.getString(5));
                taskbean.setLastUpdatedUser(rs.getString(6));
                taskbean.setLastUpdatedTime(rs.getDate(7));
                taskbean.setCreatedTime(rs.getDate(8));

                taskBeanList.add(taskbean);
            }

            return taskBeanList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    
     public List<TaskBean> getNotAssignedTaskDescription(Connection con,String pageCode) throws SQLException, Exception {


        List<TaskBean> taskBeanList = new ArrayList<TaskBean>();


        PreparedStatement ps = null;
        try {
            String query = "SELECT T.TASKCODE,T.DESCRIPTION,T.SORTKEY,T.STATUS,S.DESCRIPTION,T.LASTUPDATEDUSER,T.LASTUPDATEDTIME,T.CREATETIME "
                    + "FROM TASK T,STATUS S WHERE T.STATUS = S.STATUSCODE "
                    + "AND T.TASKCODE NOT IN (SELECT TASKCODE FROM PAGETASK WHERE PAGECODE = ? )";
            
            ps = con.prepareStatement(query);
            ps.setString(1, pageCode);
            rs = ps.executeQuery();

            while (rs.next()) {
                TaskBean taskbean = new TaskBean();
                taskbean.setTaskCode(rs.getString(1));
                taskbean.setDescription(rs.getString(2));
                taskbean.setSortKey(rs.getString(3));
                taskbean.setStatus(rs.getString(4));
                taskbean.setStatusDes(rs.getString(5));
                taskbean.setLastUpdatedUser(rs.getString(6));
                taskbean.setLastUpdatedTime(rs.getDate(7));
                taskbean.setCreatedTime(rs.getDate(8));

                taskBeanList.add(taskbean);
            }

            return taskBeanList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
    
    
    
    public List<PageBean> getPageDetails(Connection con) throws Exception {
        List<PageBean> pageBeanList = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT P.PAGECODE,P.DESCRIPTION,P.URL,P.ROOT,P.SORTKEY,P.STATUS,"
                    + "S.DESCRIPTION FROM PAGE P,STATUS S WHERE P.STATUS = S.STATUSCODE "
                    + "ORDER BY P.DESCRIPTION");


            pageBeanList = new ArrayList<PageBean>();

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                PageBean page = new PageBean();

                page.setPageCode(rs.getString(1));
                page.setDescription(rs.getString(2));
                page.setUrl(rs.getString(3));
                page.setRoot(rs.getString(4));
                page.setSortKey(Integer.toString(rs.getInt(5)));
                page.setStatusCode(rs.getString(6));
                page.setStatusDes(rs.getString(7));

                pageBeanList.add(page);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return pageBeanList;
    }

    public List<TaskBean> getAssignedList(Connection con, String pageCode) throws Exception {


        List<TaskBean> assignedList = new ArrayList<TaskBean>();
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT TASKCODE FROM PAGETASK WHERE PAGECODE = ? ");


            getAllByCatStat.setString(1, pageCode);
            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                TaskBean assignedtasks = new TaskBean();
                assignedtasks.setTaskCode(rs.getString(1));
                assignedList.add(assignedtasks);
            }
        } catch (SQLException ex) {
     
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return assignedList;
    }

    public TaskBean getTaskDescriptionByCode(Connection con, String taskCode) throws Exception {
//        String description = null;
        TaskBean bean = null;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT DESCRIPTION FROM TASK WHERE TASKCODE = ? ");

            getAllByCatStat.setString(1, taskCode);


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                bean = new TaskBean();
                bean.setTaskCode(taskCode);
                bean.setDescription( rs.getString("DESCRIPTION"));
            }
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return bean;
    }

    public boolean insertPage(Connection con, PageBean page) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO PAGE(PAGECODE,DESCRIPTION,URL,SORTKEY,STATUS,LASTUPDATEDUSER) VALUES(?,?,?,?,?,?) ");
            insertStat.setString(1, page.getPageCode());
            insertStat.setString(2, page.getDescription());
            insertStat.setString(3, page.getUrl());
            insertStat.setInt(4, Integer.parseInt(page.getSortKey()));
            insertStat.setString(5, page.getStatusCode());
            insertStat.setString(6, page.getLastUpdateduser());
//            insertStat.setTimestamp(6, new Timestamp(task.getCreatedTime().getTime()));

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean insertToPagetask(Connection con, String pageCode, String taskCode) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO PAGETASK(PAGECODE,TASKCODE) VALUES(?,?) ");
            insertStat.setString(1, pageCode);
            insertStat.setString(2, taskCode);

            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }
    
    
     public boolean insertToPagetaskAllData(Connection con,PageTaskBean pageTask) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        
        
        try {
            insertStat = con.prepareStatement("INSERT INTO PAGETASK(PAGECODE,TASKCODE,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME) VALUES(?,?,?,?,?) ");
            insertStat.setString(1, pageTask.getPageCode());
            insertStat.setString(2, pageTask.getTaskCode());
            insertStat.setString(3, pageTask.getLastUpdatedUser());
            insertStat.setDate(4, pageTask.getLastUpdatedTime());
            insertStat.setDate(5, pageTask.getCreatedTime());
            

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean isRecordAvailable(Connection con, String pageCode, String taskCode) throws Exception {
        boolean success = false;
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT COUNT(*) AS COUNT FROM  PAGETASK WHERE PAGECODE =? AND TASKCODE = ?");
            getAllByCatStat.setString(1, pageCode);
            getAllByCatStat.setString(2, taskCode);

            rs = getAllByCatStat.executeQuery();
            if (rs.next()) {
                int count = Integer.parseInt(rs.getString("COUNT").trim());

                if (count > 0) {
                    success = true;
                } else {
                    success = false;
                }
            } else {
                success = false;
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            getAllByCatStat.close();
        }
        return success;
    }

    /////upul////
    /**
     * getAllPagesBySection
     * @param con
     * @param sectionCode
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PageBean> getAllPagesBySection(Connection con, String sectionCode) throws SQLException, Exception {


        List<PageBean> sectionPageList = new ArrayList<PageBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT SP.SECTIONCODE,SP.PAGECODE,PA.DESCRIPTION FROM SECTIONPAGE SP,PAGE PA  WHERE SP.PAGECODE=PA.PAGECODE AND SP.SECTIONCODE=? ";
            ps = con.prepareStatement(query);
            ps.setString(1, sectionCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                PageBean bean = new PageBean();
                bean.setPageCode(rs.getString("PAGECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));


                sectionPageList.add(bean);
            }

            return sectionPageList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /////upul////
    /**
     * getAllRemainingPages
     * @param con
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<PageBean> getAllRemainingPages(Connection con) throws SQLException, Exception {


        List<PageBean> allRemainingPageList = new ArrayList<PageBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT PG.PAGECODE,PG.DESCRIPTION  FROM PAGE PG   WHERE PG.STATUS=? AND PG.PAGECODE NOT IN (SELECT PAGECODE FROM SECTIONPAGE )";
            ps = con.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);

            rs = ps.executeQuery();

            while (rs.next()) {
                PageBean bean = new PageBean();
                bean.setPageCode(rs.getString("PAGECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));


                allRemainingPageList.add(bean);
            }

            return allRemainingPageList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public boolean updatePage(Connection con, PageBean page) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE PAGE SET DESCRIPTION=?,URL=?,SORTKEY=?,STATUS=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE WHERE PAGECODE=? ");

            updateStat.setString(1, page.getDescription());
            updateStat.setString(2, page.getUrl());
            updateStat.setInt(3, Integer.parseInt(page.getSortKey()));
            updateStat.setString(4, page.getStatusCode());
            updateStat.setString(5, page.getLastUpdateduser());
//            updateStat.setTimestamp(4, new Timestamp(.getLastUpdatedTime().getTime()));
            updateStat.setString(6, page.getPageCode());

            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

//        
    public Boolean deletePageTasks(Connection con, String pageCode) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {
          
            
            updateStat = con.prepareStatement("DELETE FROM PAGETASK WHERE PAGECODE = ?");

            updateStat.setString(1, pageCode);

            updateStat.executeUpdate();

            status = true;
        } catch (Exception ex) {
            status = false;
            throw ex;       
        } finally {
            updateStat.close();
        }
        return status;
    }
    
    
            
    public Boolean deletePageTasksByTaskCode(Connection con,String taskCode, String pageCode) throws Exception {
        Boolean status = false;
        PreparedStatement updateStat = null;
        try {
            System.out.println("+++++++ "+taskCode);
            
            updateStat = con.prepareStatement("DELETE FROM PAGETASK WHERE PAGECODE = ? AND TASKCODE=?");

            updateStat.setString(1, pageCode);
            updateStat.setString(2, taskCode);

            updateStat.executeUpdate();

            status = true;
        } catch (SQLException ex) {
            status = false;
            throw ex;       
        } finally {
            updateStat.close();
        }
        return status;
    }

    public boolean deletePage(Connection con, PageBean page) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE PAGE WHERE PAGECODE=? ");
            deleteStat.setString(1, page.getPageCode());

            deleteStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }
        return success;
    }
    
    
     public List<PageTaskBean> getPageTaskDetailsByPageCodes(Connection con,String pageCode) throws SQLException, Exception {


        List<PageTaskBean> pageTaskList = new ArrayList<PageTaskBean>();

        PreparedStatement ps = null;
        try {
            String query = "SELECT PT.PAGECODE,PT.TASKCODE,PT.LASTUPDATEDUSER,PT.LASTUPDATEDTIME,PT.CREATETIME FROM PAGETASK PT WHERE PT.PAGECODE=?";
            ps = con.prepareStatement(query);
            ps.setString(1, pageCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                PageTaskBean bean = new PageTaskBean();
               
                bean.setPageCode(rs.getString("PAGECODE"));
                bean.setTaskCode(rs.getString("TASKCODE"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getDate("CREATETIME"));

                pageTaskList.add(bean);
            }

            return pageTaskList;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    
     
        public boolean findExistanceOfTask(Connection con, String  pageCode,String taskCode) throws Exception {
        boolean isExist=false;
        PreparedStatement findStatement = null;
        try {
           
            findStatement = con.prepareStatement("SELECT * FROM PAGETASK PT WHERE PT.PAGECODE=? AND PT.TASKCODE=? " );

            //set parameters for select
            findStatement.setString(1,pageCode);
            findStatement.setString(2,taskCode);


            rs = findStatement.executeQuery();
             while (rs.next()) {
                 isExist=true;
                 
             }
             
             return isExist;

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            findStatement.close();
        }
       
    }
        
        
    
}
