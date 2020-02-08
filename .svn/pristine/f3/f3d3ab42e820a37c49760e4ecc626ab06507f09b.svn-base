/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.templatemgt.lettertemplate.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.TaskBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterFieldBean;
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
 * @author sajith_g
 */
public class LetterConfMgtPersistance {
    private ResultSet rs = null;

    /**
     * to take existing letter conf list to the user view
     * @param cmsCon
     * @return
     * @throws Exception 
     */
    public List<LetterBean> getAllLetterConfs(Connection cmsCon) throws Exception {

        List<LetterBean> allLetterConfs = new ArrayList<LetterBean>();
        PreparedStatement ps = null;

        try {
            String query = "select e.templatecode,e.description,e.title,e.body,st.statuscode,e.createddate,e.lastupdatedtime,e.lastupdateduser from lettertemplate e,status st WHERE e.status=st.statuscode";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {

                LetterBean bean=new LetterBean();
                bean.setTemplateCode(rs.getString("templatecode"));
                bean.setDescription(rs.getString("description"));
                bean.setTitle(rs.getString("title"));
                bean.setBody(rs.getString("body"));
                bean.setStatus(rs.getString("statuscode"));
                bean.setCreatedTime(rs.getTimestamp("createddate"));
                bean.setLastUpdatedTime(rs.getTimestamp("lastupdatedtime"));
                bean.setLastUpdatedUser(rs.getString("lastupdateduser"));
                allLetterConfs.add(bean);
                
            }

            return allLetterConfs;

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
     * to add a new letter config to the database
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int addNewLetterConf(Connection cmsCon, LetterBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "insert into lettertemplate (templatecode,description,title,body,status,createddate,lastupdatedtime,lastupdateduser) VALUES (?, ?, ?, ?, ?,SYSDATE, SYSDATE, ?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getTemplateCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getTitle());
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
     * to update letter config
     * @param cmsCon
     * @param bean
     * @return
     * @throws Exception 
     */
    public int updateLetterConf(Connection cmsCon, LetterBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE lettertemplate SET DESCRIPTION=?,TITLE=?,BODY=?,STATUS=?,LASTUPDATEDTIME=?,LASTUPDATEDUSER=? WHERE TEMPLATECODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getTitle());
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
     * to delete a letter conf from database
     * @param cmsCon
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteLetterConf(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM lettertemplate WHERE templatecode=? ";

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
     * to view selected letter conf
     * @param con
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public LetterBean viewSelectedLetterConf(Connection con, String id) throws SQLException, Exception {

        LetterBean bean=new LetterBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT e.templatecode,e.description,e.title,e.body,st.statuscode,st.description as statusDes,e.createddate,e.lastupdatedtime,e.lastupdateduser,e.createddate FROM lettertemplate e, STATUS st WHERE e.status=st.statuscode AND e.templatecode = ? ";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setTemplateCode(rs.getString("templatecode"));
                bean.setDescription(rs.getString("description"));
                bean.setSubject(rs.getString("title"));
                bean.setBody(rs.getString("body"));
                bean.setStatus(rs.getString("statuscode"));
                bean.setStatusDes(rs.getString("statusDes"));
                bean.setCreatedTime(rs.getTimestamp("createddate"));
                bean.setLastUpdatedTime(rs.getTimestamp("lastupdatedtime"));
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
    
    public List<LetterFieldBean> getLetterFieldDetails(Connection con) throws SQLException, Exception {


        List<LetterFieldBean> letterFieldBeanList = new ArrayList<LetterFieldBean>();


        PreparedStatement ps = null;
        try {
            String query = "select lfd.fieldname,lfd.fixedvalue,lfd.tablefield,lfd.tablename from letterfielddetails lfd";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                LetterFieldBean letterFieldBean = new LetterFieldBean();
                letterFieldBean.setFieldName(rs.getString(1));
                letterFieldBean.setFixedValue(rs.getString(2));
                letterFieldBean.setTableField(rs.getString(3));
                letterFieldBean.setTableName(rs.getString(4));

                letterFieldBeanList.add(letterFieldBean);
            }

            return letterFieldBeanList;
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
