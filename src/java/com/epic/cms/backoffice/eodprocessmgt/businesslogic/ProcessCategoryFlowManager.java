/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodprocessmgt.businesslogic;

import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessCategoryFlowBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessFlowBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EODProcessMgtBean;
import com.epic.cms.backoffice.eodprocessmgt.bean.EodProcessCategoryBean;
import com.epic.cms.backoffice.eodprocessmgt.persistance.ProcessCategoryFlowPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author badrika
 */
public class ProcessCategoryFlowManager {

    private ProcessCategoryFlowPersistance perObj;
    private Connection cmsCon;
    private List<EodProcessCategoryBean> categoryList;
    private List<EODProcessCategoryFlowBean> allFlowList;
    private List<EODProcessMgtBean> processList;
    private List<EODProcessFlowBean> allProFlowList;

    public int getCategoryCount() throws Exception {
        int count = 0;
        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            count = perObj.getCategoryCount(cmsCon);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return count;
    }

    public List<EodProcessCategoryBean> getAllCategoryList() throws Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            categoryList = perObj.getAllCategoryList(cmsCon);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return categoryList;
    }

    public List<EodProcessCategoryBean> getSelectedCategoryList(String step) throws Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            categoryList = perObj.getSelectedCategoryList(cmsCon, step);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return categoryList;
    }

    //getCategoryListforProcessFlow
    public List<EodProcessCategoryBean> getCategoryListforProcessFlow() throws Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            categoryList = perObj.getCategoryListforProcessFlow(cmsCon);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return categoryList;
    }

    public boolean deleteCategories(String step) throws SQLException, Exception {

        boolean success = false;
        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            success = perObj.deleteCategories(cmsCon, step);
            cmsCon.commit();

            return success;
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

    public int addcategoryFlow(String stepId, String[] assigned) throws SQLException, Exception {

        int rowCount = -1;
        boolean delsucces = false;
        int addsuccess = -1;

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            categoryList = perObj.getSelectedCategoryList(cmsCon, stepId);

            if (categoryList != null) {
                delsucces = perObj.deleteCategories(cmsCon, stepId);
            } else {
                delsucces = true;
            }

            if (assigned != null) {
                for (int i = 0; i < assigned.length; i++) {
                    rowCount = perObj.addcategories(cmsCon, stepId, assigned[i]);
                }
            } else {
                rowCount = 1;
            }

            if (rowCount > 0 && delsucces) {
                addsuccess = 1;
                cmsCon.commit();
            } else {
                cmsCon.rollback();
            }

            return addsuccess;

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

    public List<EODProcessCategoryFlowBean> getAllCategoryFlows() throws SQLException, Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            allFlowList = perObj.getAllCategoryFlows(cmsCon);

            cmsCon.commit();
            return allFlowList;
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

    public List<EODProcessMgtBean> getProcessByCategory(String category) throws Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            processList = perObj.getProcessByCategory(cmsCon, category);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return processList;
    }

    //getSelectedProcessList
    public List<EODProcessMgtBean> getSelectedProcessList(String category, String step) throws Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            processList = perObj.getSelectedProcessList(cmsCon, category, step);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return processList;
    }

    public int getprocessCount(String category) throws Exception {
        int count = 0;
        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            count = perObj.getprocessCount(cmsCon, category);

            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                cmsCon.rollback();
                throw e;
            }
        } finally {
            DBConnection.dbConnectionClose(cmsCon);
        }
        return count;
    }

    public boolean deleteProcesses(String cat, String step) throws SQLException, Exception {

        boolean success = false;
        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            success = perObj.deleteProcesses(cmsCon, cat, step);
            cmsCon.commit();

            return success;
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

    public int addProcesseFlow(String cat, String stepId, String[] assigned) throws SQLException, Exception {

        int rowCount = -1;
        boolean delsucces = false;
        int addsuccess = -1;

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            processList = perObj.getSelectedProcessList(cmsCon, cat, stepId);

            if (processList != null) {

                delsucces = perObj.deleteProcesses(cmsCon, cat, stepId);
            } else {
                delsucces = true;
            }

            if (assigned != null) {
                for (int i = 0; i < assigned.length; i++) {
                    rowCount = perObj.addProcesses(cmsCon, cat, stepId, assigned[i]);
                }
            } else {
                rowCount = 1;
            }
            if (rowCount > 0 && delsucces) {
                addsuccess = 1;
                cmsCon.commit();
            } else {
                cmsCon.rollback();
            }
            
            return addsuccess;

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

    public List<EODProcessFlowBean> getAllProcessFlows() throws SQLException, Exception {

        try {

            perObj = new ProcessCategoryFlowPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            allProFlowList = perObj.getAllProcessFlows(cmsCon);

            cmsCon.commit();
            return allProFlowList;
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
