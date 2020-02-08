/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.emailtemplate.persistance;

import com.epic.cms.admin.templatemgt.emailtemplate.bean.EmailBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sajith
 */
public class EmailConfMgtPersistance {

    private ResultSet rs = null;

    /**
     * to take existing email conf list to the user view
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<EmailBean> getAllEmailConfs(Connection cmsCon) throws Exception {

        List<EmailBean> allEmailConfs = new ArrayList<EmailBean>();
        PreparedStatement ps = null;

        try {
            String query = "select e.templatecode,e.description,e.subject,e.body,st.statuscode,e.createddate,e.lastupdateddate,e.lastupdateduser from emailtemplate e,status st WHERE e.status=st.statuscode";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                EmailBean bean=new EmailBean();
                bean.setTemplateCode(rs.getString("templatecode"));
                bean.setDescription(rs.getString("description"));
                bean.setSubject(rs.getString("subject"));
                bean.setBody(rs.getString("body"));
                bean.setStatus(rs.getString("statuscode"));
                bean.setCreatedTime(rs.getTimestamp("createddate"));
                bean.setLastUpdatedTime(rs.getTimestamp("lastupdateddate"));
                bean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                allEmailConfs.add(bean);
                
            }

            return allEmailConfs;

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

    /**
     * to add a new email config to the database
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewEmailConf(Connection cmsCon, EmailBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "insert into emailtemplate (templatecode,description,subject,body,status,createddate,lastupdateddate,lastupdateduser) VALUES (?, ?, ?, ?, ?,SYSDATE, SYSDATE, ?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getTemplateCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getSubject());
            ps.setString(4, bean.getBody());
            ps.setString(5, bean.getStatus());
            ps.setString(6, bean.getLastUpdatedUser());

            i = ps.executeUpdate();

        } catch (SQLException ex) {
             throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    /**
     * to update email config
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateEmailConf(Connection cmsCon, EmailBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE emailtemplate SET DESCRIPTION=?,SUBJECT=?,BODY=?,STATUS=?,LASTUPDATEDDATE=?,LASTUPDATEDUSER=? WHERE TEMPLATECODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getSubject());
            ps.setString(3, bean.getBody());
            ps.setString(4, bean.getStatus());
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            ps.setString(6, bean.getLastUpdatedUser());
            ps.setString(7, bean.getTemplateCode());

            i = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
        return i;
    }

    /**
     * to take the list of status(active and de active)
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<StatusBean> getStatustList(Connection cmsCon) throws Exception {

        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT STATUSCODE, DESCRIPTION FROM STATUS WHERE STATUSCATEGORY = ?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.GENESTATUCAT);
            rs = ps.executeQuery();

            while (rs.next()) {
                StatusBean bean = new StatusBean();

                bean.setStatusCode(rs.getString("STATUSCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                statustList.add(bean);
            }

            return statustList;

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

    /**
     * to delete a email conf from database
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteEmailConf(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM emailtemplate WHERE templatecode=? ";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);

            i = ps.executeUpdate();

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
        return i;
    }

    /**
     * to view selected email conf
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public EmailBean viewSelectedEmailConf(Connection con, String id) throws SQLException, Exception {

        EmailBean bean=new EmailBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT e.templatecode,e.description,e.subject,e.body,st.statuscode,st.description as statusDes,e.createddate,e.lastupdateddate,e.lastupdateduser,e.createddate,e.lastupdateddate,e.lastupdateduser FROM emailtemplate e, STATUS st WHERE e.status=st.statuscode AND e.templatecode = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setTemplateCode(rs.getString("templatecode"));
                bean.setDescription(rs.getString("description"));
                bean.setSubject(rs.getString("subject"));
                bean.setBody(rs.getString("body"));
                bean.setStatus(rs.getString("statuscode"));
                bean.setStatusDes(rs.getString("statusDes"));
                bean.setCreatedTime(rs.getTimestamp("createddate"));
                bean.setLastUpdatedTime(rs.getTimestamp("lastupdateddate"));
                bean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                
            }
            return bean;

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
    
}
