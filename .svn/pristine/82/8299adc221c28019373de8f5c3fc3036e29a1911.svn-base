/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.persistance;

import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessCategoryFlowBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessFlowBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EodProcessCategoryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class ProcessCategoryFlowPersistance {

    private EodProcessCategoryBean categoryBean;
    private List<EodProcessCategoryBean> categoryList;
    private List<EODProcessCategoryFlowBean> allFlowList;
    private List<EODProcessMgtBean> prosessList;
    private EODProcessMgtBean prosessBean;
    private List<EODProcessFlowBean> allproFlowList;

    public int getCategoryCount(Connection con) throws Exception {
        int count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = con.prepareStatement("SELECT COUNT (PROCESSCATEGORYID) FROM EODPROCESSCATEGORY");

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("COUNT(PROCESSCATEGORYID)");
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return count;

    }

    public List<EodProcessCategoryBean> getAllCategoryList(Connection con) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        categoryList = new ArrayList<EodProcessCategoryBean>();

        try {

            ps = con.prepareStatement("SELECT PROCESSCATEGORYID,DESCRIPTION FROM EODPROCESSCATEGORY "
                    + "where PROCESSCATEGORYID not in(select PROCESSCATEGORYID from EODPROCESSCATEGORYFLOW) and PROCESSCATEGORYID != '1'");

            rs = ps.executeQuery();

            while (rs.next()) {
                categoryBean = new EodProcessCategoryBean();

                categoryBean.setCategoryCode(rs.getString("PROCESSCATEGORYID"));
                categoryBean.setDescription(rs.getString("DESCRIPTION"));

                categoryList.add(categoryBean);

            }

        } catch (SQLException ex) {
            throw ex;
        }
        return categoryList;

    }

    public List<EodProcessCategoryBean> getSelectedCategoryList(Connection con, String step) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        categoryList = new ArrayList<EodProcessCategoryBean>();

        try {

            ps = con.prepareStatement("SELECT PCF.PROCESSCATEGORYID, PC.DESCRIPTION FROM EODPROCESSCATEGORYFLOW PCF, EODPROCESSCATEGORY PC "
                    + "where PCF.PROCESSCATEGORYID = PC.PROCESSCATEGORYID and PCF.STEPID = ? ");

            ps.setString(1, step);

            rs = ps.executeQuery();

            while (rs.next()) {
                categoryBean = new EodProcessCategoryBean();

                categoryBean.setCategoryCode(rs.getString("PROCESSCATEGORYID"));
                categoryBean.setDescription(rs.getString("DESCRIPTION"));

                categoryList.add(categoryBean);

            }

        } catch (SQLException ex) {
            throw ex;
        }
        return categoryList;

    }

    //getCategoryListforProcessFlow
    public List<EodProcessCategoryBean> getCategoryListforProcessFlow(Connection con) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        categoryList = new ArrayList<EodProcessCategoryBean>();

        try {

            ps = con.prepareStatement("SELECT PROCESSCATEGORYID,DESCRIPTION FROM EODPROCESSCATEGORY "
                    + "where PROCESSCATEGORYID in(select PROCESSCATEGORYID from EODPROCESSCATEGORYFLOW)");

            rs = ps.executeQuery();

            while (rs.next()) {
                categoryBean = new EodProcessCategoryBean();

                categoryBean.setCategoryCode(rs.getString("PROCESSCATEGORYID"));
                categoryBean.setDescription(rs.getString("DESCRIPTION"));

                categoryList.add(categoryBean);

            }

        } catch (SQLException ex) {
            throw ex;
        }
        return categoryList;

    }

    public Boolean deleteCategories(Connection con, String step) throws Exception {
        Boolean status = false;
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("DELETE FROM EODPROCESSCATEGORYFLOW WHERE STEPID = ?");
            ps.setString(1, step);
            ps.executeUpdate();

            status = true;

        } catch (Exception ex) {
            status = false;
            throw ex;
        } finally {
            ps.close();
        }
        return status;
    }

    public int addcategories(Connection cmsCon, String stepId, String categoryId) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO EODPROCESSCATEGORYFLOW (STEPID,PROCESSCATEGORYID) VALUES(?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, stepId);
            ps.setString(2, categoryId);

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

    public List<EODProcessCategoryFlowBean> getAllCategoryFlows(Connection cmsCon) throws Exception {

        allFlowList = new ArrayList<EODProcessCategoryFlowBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT PCF.STEPID, PCF.PROCESSCATEGORYID, PC.DESCRIPTION "
                    + "FROM EODPROCESSCATEGORYFLOW PCF, EODPROCESSCATEGORY PC WHERE PCF.PROCESSCATEGORYID=PC.PROCESSCATEGORYID ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                EODProcessCategoryFlowBean bean = new EODProcessCategoryFlowBean();
                bean.setStepId(rs.getString("STEPID"));
                bean.setPocessCategoryId(rs.getString("PROCESSCATEGORYID"));
                bean.setPocessCategoryDes(rs.getString("DESCRIPTION"));

                allFlowList.add(bean);
            }

            return allFlowList;

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

    //getProcessByCategory
    public List<EODProcessMgtBean> getProcessByCategory(Connection con, String category) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        prosessList = new ArrayList<EODProcessMgtBean>();

        try {

            ps = con.prepareStatement("SELECT PROCESSID, DESCRIPTION FROM EODPROCESS "
                    + "WHERE PROCESSCATEGORYID = ? AND PROCESSID not in(select PROCESSID from EODPROCESSFLOW) and PROCESSID != '1' and PROCESSID != '2' ");

            ps.setString(1, category);

            rs = ps.executeQuery();

            while (rs.next()) {
                prosessBean = new EODProcessMgtBean();

                prosessBean.setProcessId(rs.getString("PROCESSID"));
                prosessBean.setDescription(rs.getString("DESCRIPTION"));

                prosessList.add(prosessBean);

            }

        } catch (SQLException ex) {
            throw ex;
        }
        return prosessList;

    }

    public List<EODProcessMgtBean> getSelectedProcessList(Connection con, String category, String step) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        prosessList = new ArrayList<EODProcessMgtBean>();

        try {

            ps = con.prepareStatement("SELECT PF.PROCESSID, P.DESCRIPTION FROM EODPROCESSFLOW PF, EODPROCESS P "
                    + "WHERE PF.PROCESSID = P.PROCESSID AND PF.PROCESSCATEGORYID = ? AND PF.STEPID = ?");

            ps.setString(1, category);
            ps.setString(2, step);

            rs = ps.executeQuery();

            while (rs.next()) {
                prosessBean = new EODProcessMgtBean();

                prosessBean.setProcessId(rs.getString("PROCESSID"));
                prosessBean.setDescription(rs.getString("DESCRIPTION"));

                prosessList.add(prosessBean);

            }

        } catch (SQLException ex) {
            throw ex;
        }
        return prosessList;

    }

    public int getprocessCount(Connection con, String Category) throws Exception {
        int count = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = con.prepareStatement("SELECT COUNT (PROCESSID) FROM EODPROCESS WHERE PROCESSCATEGORYID = ? ");

            ps.setString(1, Category);

            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt("COUNT(PROCESSID)");
            }

        } catch (SQLException ex) {
            throw ex;
        }
        return count;

    }

    public Boolean deleteProcesses(Connection con, String cat, String step) throws Exception {
        Boolean status = false;
        PreparedStatement ps = null;

        try {

            ps = con.prepareStatement("DELETE FROM EODPROCESSFLOW WHERE PROCESSCATEGORYID = ? and STEPID = ?");

            ps.setString(1, cat);
            ps.setString(2, step);
            ps.executeUpdate();

            status = true;

        } catch (Exception ex) {
            status = false;
            throw ex;
        } finally {
            ps.close();
        }
        return status;
    }

    public int addProcesses(Connection cmsCon, String categoryId, String stepId, String processId) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO EODPROCESSFLOW (PROCESSCATEGORYID,STEPID,PROCESSID) VALUES(?,?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, categoryId);
            ps.setString(2, stepId);
            ps.setString(3, processId);

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

    public List<EODProcessFlowBean> getAllProcessFlows(Connection cmsCon) throws Exception {

        allproFlowList = new ArrayList<EODProcessFlowBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT PF.PROCESSCATEGORYID, PC.DESCRIPTION as catdes, PF.STEPID, PF.PROCESSID, P.DESCRIPTION as prodes "
                    + "from EODPROCESSFLOW PF, EODPROCESSCATEGORY PC, EODPROCESS P "
                    + "where PF.PROCESSCATEGORYID = PC.PROCESSCATEGORYID and PF.PROCESSID = P.PROCESSID ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                EODProcessFlowBean bean = new EODProcessFlowBean();

                bean.setPocessCategoryId(rs.getString("PROCESSCATEGORYID"));
                bean.setPocessCategoryDes(rs.getString("catdes"));
                bean.setStepId(rs.getString("STEPID"));
                bean.setPocessId(rs.getString("PROCESSID"));
                bean.setPocessDes(rs.getString("prodes"));

                allproFlowList.add(bean);
            }

            return allproFlowList;

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
