/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.generalledgermgtmgt.persistance;

import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerAccTypeBean;
import com.epic.cms.backoffice.generalledgermgtmgt.bean.GeneralLedgerMgtBean;
import com.epic.cms.system.util.variable.StatusVarList;
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
public class GeneralLedgerMgtPersistance {

    private ResultSet rs;

    public List<StatusBean> getStatustList(Connection cmsCon) throws Exception {

        List<StatusBean> statustList = new ArrayList<StatusBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

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
//chinthaka

    public List<GeneralLedgerAccTypeBean> getAccTypeList(Connection cmsCon) throws Exception {

        List<GeneralLedgerAccTypeBean> accTypeList = new ArrayList<GeneralLedgerAccTypeBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT GLACCOUNTTYPE,DESCRIPTION FROM GLACCOUNTTYPE WHERE STATUS= ?";
            
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, StatusVarList.ACTIVE_STATUS);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                GeneralLedgerAccTypeBean bean = new GeneralLedgerAccTypeBean();
                bean.setGlaccType(rs.getString("GLACCOUNTTYPE"));
                bean.setGlaccDes(rs.getString("DESCRIPTION"));

                accTypeList.add(bean);
            }
            return accTypeList;

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
    //end chinthaka

    public List<GeneralLedgerMgtBean> getAllGlAccounts(Connection cmsCon) throws Exception {

        List<GeneralLedgerMgtBean> allAccont = new ArrayList<GeneralLedgerMgtBean>();
        PreparedStatement ps = null;

        try {
            String query = "SELECT gl.GLACCOUNTCODE, gl.DESCRIPTION as des, gl.STATUS,gl.GLACCOUNTTYPE, ST.DESCRIPTION as stdes,gt.DESCRIPTION AS GTDES FROM GLACCOUNT gl, STATUS ST,GLACCOUNTTYPE gt WHERE gl.STATUS=ST.STATUSCODE AND gl.GLACCOUNTTYPE=gt.GLACCOUNTTYPE ";

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                GeneralLedgerMgtBean bean = new GeneralLedgerMgtBean();
                bean.setGlAccNo(rs.getString("GLACCOUNTCODE"));
                bean.setDescription(rs.getString("des"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("stdes"));
                bean.setGlAccType(rs.getString("GLACCOUNTTYPE"));
                bean.setGlAccTypeDes(rs.getString("GTDES"));

                allAccont.add(bean);
            }

            return allAccont;

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

    public int addNewglAccont(Connection cmsCon, GeneralLedgerMgtBean bean) throws Exception, SQLException {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "INSERT INTO GLACCOUNT (GLACCOUNTCODE,DESCRIPTION,STATUS,LASTUPDATEDUSER,CREATEDTIME,LASTUPDATETIME,GLACCOUNTTYPE) VALUES(?,?,?,?,SYSDATE,SYSDATE,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getGlAccNo());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getLastUpdatedUser());
            ps.setString(5, bean.getGlAccType());

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

    public GeneralLedgerMgtBean viewSelectedAccont(Connection con, String id) throws SQLException, Exception {

        GeneralLedgerMgtBean bean = new GeneralLedgerMgtBean();

        PreparedStatement ps = null;
        try {
            String query = "SELECT GL.GLACCOUNTCODE,GL.DESCRIPTION AS DES,GL.STATUS,GL.GLACCOUNTTYPE,ST.DESCRIPTION AS STDES,GL.LASTUPDATEDUSER,GL.LASTUPDATETIME,GT.DESCRIPTION AS GTDES FROM GLACCOUNT GL,STATUS ST,GLACCOUNTTYPE GT WHERE GL.STATUS =ST.STATUSCODE AND GL.GLACCOUNTTYPE=GT.GLACCOUNTTYPE AND GL.GLACCOUNTCODE= ?";

            ps = con.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setGlAccNo(rs.getString("GLACCOUNTCODE"));
                bean.setDescription(rs.getString("des"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setGlAccType(rs.getString("GLACCOUNTTYPE"));
                bean.setStatusDes(rs.getString("stdes"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLastUpdatedTime(rs.getTimestamp("LASTUPDATETIME").toString());
                bean.setGlAccTypeDes(rs.getString("GTDES"));
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

    public int updateglAccont(Connection cmsCon, GeneralLedgerMgtBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {

            String query = "UPDATE GLACCOUNT SET DESCRIPTION=?,STATUS=?,LASTUPDATEDUSER=?,GLACCOUNTTYPE=?,LASTUPDATETIME=SYSDATE WHERE GLACCOUNTCODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatus());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setString(4, bean.getGlAccType());
            ps.setString(5, bean.getGlAccNo());

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

    public int deleteglAcconut(Connection cmsCon, String id) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM GLACCOUNT WHERE GLACCOUNTCODE=? ";

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

}
