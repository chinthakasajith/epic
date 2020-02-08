/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.persistance;

import com.epic.cms.backoffice.eodprocessmgt.bean.EodProcessCategoryBean;
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
public class EodProcessCategoryPersistance {

    ResultSet rs = null;
    private List<EodProcessCategoryBean> categoryList = null;

    public List<EodProcessCategoryBean> getProcessCategoryDetails(Connection CMSCon) throws Exception {
        PreparedStatement getAllCategory = null;
        categoryList = new ArrayList<EodProcessCategoryBean>();

        try {
            getAllCategory = CMSCon.prepareStatement("SELECT P.PROCESSCATEGORYID,P.DESCRIPTION,"
                    + " P.DEPENDANCYSTATUS,P.STATUS,S.DESCRIPTION AS SDESCRIPTION,"
                    + " P.LASTUPDATEDUSER,P.LASTUPDATEDTIME,P.CREATEDTIME"
                    + " FROM EODPROCESSCATEGORY P,STATUS S"
                    + " WHERE S.STATUSCODE=P.STATUS");

            rs = getAllCategory.executeQuery();

            while (rs.next()) {

                EodProcessCategoryBean bean = new EodProcessCategoryBean();

                bean.setCategoryCode(Integer.toString(rs.getInt("PROCESSCATEGORYID")));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setDependancyStatus(Integer.toString(rs.getInt("DEPENDANCYSTATUS")));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("SDESCRIPTION"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getDate("LASTUPDATEDTIME"));
                bean.setCreatedTime(rs.getDate("CREATEDTIME"));

                categoryList.add(bean);

            }
        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllCategory.close();

        }

        return categoryList;
    }

    public String getCategoryId(String user, Connection CMSCon) throws Exception {
        PreparedStatement getCategoryId = null;
        String categoryId = null;

        try {

            getCategoryId = CMSCon.prepareStatement("SELECT P.PROCESSCATEGORYID "
                    + " FROM EODPROCESSCATEGORY P WHERE P.LASTUPDATEDUSER= ? AND "
                    + " P.PROCESSCATEGORYID=(SELECT MAX(PROCESSCATEGORYID) FROM EODPROCESSCATEGORY )");

            getCategoryId.setString(1, user);
            rs = getCategoryId.executeQuery();

            while (rs.next()) {

                categoryId = Integer.toString(rs.getInt("PROCESSCATEGORYID"));

            }


        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getCategoryId.close();

        }

        return categoryId;
    }

    public boolean eodProcessCategoryInsert(EodProcessCategoryBean categoryBean, Connection CMSCon) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = CMSCon.prepareStatement("INSERT INTO EODPROCESSCATEGORY"
                    + " (DESCRIPTION,DEPENDANCYSTATUS,STATUS,"
                    + " LASTUPDATEDUSER,LASTUPDATEDTIME,CREATEDTIME)"
                    + " VALUES(?,?,?,?,SYSDATE,SYSDATE) ");


            insertStat.setString(1, categoryBean.getDescription());
            insertStat.setInt(2, Integer.parseInt(categoryBean.getDependancyStatus()));
            insertStat.setString(3, categoryBean.getStatus());
            insertStat.setString(4, categoryBean.getLastUpdatedUser());

            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean eodProcessCategoryUpdate(EodProcessCategoryBean categoryBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE EODPROCESSCATEGORY SET "
                    + " DESCRIPTION=?,DEPENDANCYSTATUS=?,STATUS=?,"
                    + " LASTUPDATEDUSER=?,LASTUPDATEDTIME=SYSDATE"
                    + " WHERE PROCESSCATEGORYID=? ");

            updateStat.setString(1, categoryBean.getDescription());
            updateStat.setInt(2, Integer.parseInt(categoryBean.getDependancyStatus()));
            updateStat.setString(3, categoryBean.getStatus());
            updateStat.setString(4, categoryBean.getLastUpdatedUser());

            updateStat.setInt(5, Integer.parseInt(categoryBean.getCategoryCode()));

            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean eodProcessCategoryDelete(EodProcessCategoryBean categoryBean, Connection con) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = con.prepareStatement("DELETE EODPROCESSCATEGORY WHERE PROCESSCATEGORYID=?");

            deleteStat.setInt(1, Integer.parseInt(categoryBean.getCategoryCode()));


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
