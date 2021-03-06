/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.templatemgt.lettertemplate.businesslogic;

import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterFieldBean;
import com.epic.cms.templatemgt.lettertemplate.persistance.LetterConfMgtPersistance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sajith_g
 */
public class LetterConfMgtManager {
    private Connection cmsCon;
    private LetterConfMgtPersistance letterobj;
    private List<StatusBean> statusList = null;
    private LetterBean letterConfBean;
    private List<LetterFieldBean> letterFieldList=null;

    /**
     * to take letter conf list
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<LetterBean> getLetterConfs() throws SQLException, Exception {

        try {
            List<LetterBean> letterList = null;
            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            letterList = letterobj.getAllLetterConfs(cmsCon);

            cmsCon.commit();
            return letterList;
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

    /**
     * to take status list(active and deactive)
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public List<StatusBean> getStatusList() throws SQLException, Exception {
        statusList = new ArrayList<StatusBean>();
        try {

            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            statusList = letterobj.getStatustList(cmsCon);

            cmsCon.commit();
            return statusList;
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

    /**
     * to add a new letter conf
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int addNewLetterConf(LetterBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = letterobj.addNewLetterConf(cmsCon, bean);
            
            cmsCon.commit();
            return rowCount;
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

    /**
     * to delete a letter template
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteLetterTemplate(String id) throws SQLException, Exception {
        int rowCount = -1;
        try {
            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            rowCount = letterobj.deleteLetterConf(cmsCon, id);
            cmsCon.commit();

        } catch (SQLException ex) {
            try {
                cmsCon.rollback();
                throw ex;
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
        return rowCount;
    }

    /**
     * to update letter conf details
     * @param bean
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int UpdateLetterConf(LetterBean bean) throws SQLException, Exception {

        int rowCount = -1;
        try {
            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            //--
            rowCount = letterobj.updateLetterConf(cmsCon, bean);
            cmsCon.commit();
            
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
        return rowCount;
    }

    /**
     * to view one selected letter conf details
     * @param id
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public LetterBean viewSelectedLetterConf(String id) throws SQLException, Exception {

        try {
            letterobj = new LetterConfMgtPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            letterConfBean = letterobj.viewSelectedLetterConf(cmsCon, id);
            cmsCon.commit();
            return letterConfBean;
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
    
    public List<LetterFieldBean> getLetterFieldDetailsList() throws Exception {
        try {
            letterobj = new LetterConfMgtPersistance();
             cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            letterFieldList = letterobj.getLetterFieldDetails(cmsCon);

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
        return letterFieldList;
    }
}
