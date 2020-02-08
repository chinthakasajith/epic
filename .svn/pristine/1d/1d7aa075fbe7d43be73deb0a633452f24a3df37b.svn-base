package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalModelBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class TerminalModelMgtPersistance {

    ResultSet rs = null;

    public List<TerminalModelBean> getTerminalModel(Connection cmsCon) throws SQLException, Exception {
        List<TerminalModelBean> terminalModelList = new ArrayList<TerminalModelBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT TM.MODLECODE, TM.DESCRIPTION,TMA.DESCRIPTION AS TERMANUDESC, "
                + "ST.DESCRIPTION AS STDESCRIPTION FROM TERMINALMODLE TM, TERMINALMANUFACTURE TMA,"
                + " STATUS ST WHERE TM.MANUFACTURECODE=TMA.MANUFACTURECODE AND TM.STATUS=ST.STATUSCODE";
        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalModelBean bean = new TerminalModelBean();

                bean.setModelCode(rs.getString("MODLECODE"));
                bean.setDescription(rs.getString("TERMANUDESC"));
                bean.setStatus(rs.getString("STDESCRIPTION"));
                bean.setModelDesc(rs.getString("DESCRIPTION"));

                terminalModelList.add(bean);
            }
            return terminalModelList;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }


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

    public List<TerminalModelBean> getManufacture(Connection cmsCon) throws Exception {
        List<TerminalModelBean> terManufacList = new ArrayList<TerminalModelBean>();
        PreparedStatement ps = null;
        String query;
        
//        query = "SELECT TMA.MANUFACTURECODE, TMA.DESCRIPTION FROM TERMINALMANUFACTURE TMA, TERMINALMODLE TMO";
        query = "SELECT MANUFACTURECODE, DESCRIPTION FROM TERMINALMANUFACTURE";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
               TerminalModelBean bean = new TerminalModelBean();
               bean.setManufactureCode(rs.getString("MANUFACTURECODE"));
               bean.setDescription(rs.getString("DESCRIPTION"));
               
               terManufacList.add(bean);
            }
            return terManufacList;
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public int insertNewCode(Connection cmsCon, TerminalModelBean bean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        String query;
        
        query = "INSERT INTO TERMINALMODLE(MODLECODE, DESCRIPTION, STATUS, MANUFACTURECODE, LASTUPDATEDUSER,"
                + " LASTUPDATEDTIME, CREATEDTIME) VALUES(?, ?, ?, ?, ?, ?, SYSDATE)";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, bean.getModelCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getStatus());
            ps.setString(4, bean.getManufactureCode());
            ps.setString(5, bean.getLastUpdatedUser());
            ps.setTimestamp(6, new Timestamp(bean.getLastUpdatedTime().getTime()));
            
            i = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
        }catch (Exception ex) {
            throw ex;
        }  finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return i;
    }

   
    public TerminalModelBean viewSelectedData(Connection cmsCon, String id) throws Exception {
        TerminalModelBean bean = new TerminalModelBean();
        PreparedStatement ps = null;
        String query;
        
//        query = "SELECT TM.MODLECODE, TM.DESCRIPTION,TMA.DESCRIPTION AS TERMANUDESC,TM.MANUFACTURECODE, "
//                + "ST.DESCRIPTION AS STDESCRIPTION FROM TERMINALMODLE TM, TERMINALMANUFACTURE TMA,"
//                + " STATUS ST WHERE TM.STATUS=ST.STATUSCODE AND TM.MODLECODE=? ";
        
        query = "SELECT TM.MODLECODE, TM.DESCRIPTION,TMA.DESCRIPTION AS TERMANUDESC,TM.MANUFACTURECODE, "
                + "ST.DESCRIPTION AS STDESCRIPTION FROM TERMINALMODLE TM, TERMINALMANUFACTURE TMA,"
                + " STATUS ST WHERE TM.STATUS=ST.STATUSCODE AND TM.MANUFACTURECODE=TMA.MANUFACTURECODE AND TM.MODLECODE=? ";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bean.setModelCode(rs.getString("MODLECODE"));
                bean.setDescription(rs.getString("TERMANUDESC"));
                bean.setStatus(rs.getString("STDESCRIPTION"));
                bean.setModelDesc(rs.getString("DESCRIPTION"));
                bean.setManufactureCode(rs.getString("MANUFACTURECODE"));
            }
            return bean;
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }

   
      public int updateTerminalModelInfo(Connection cmsCon, TerminalModelBean bean) throws SQLException, Exception  {
        int rowCount = -1;
        PreparedStatement ps = null;
        String query;
        
        query = "UPDATE TERMINALMODLE SET DESCRIPTION=?, STATUS=?, MANUFACTURECODE=?, LASTUPDATEDTIME=SYSDATE, CREATEDTIME=SYSDATE WHERE MODLECODE=?";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getStatus());
            ps.setString(3, bean.getManufactureCode());
            ps.setString(4, bean.getModelCode());
            
            rowCount = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }

    
    public int deleteModelInfo(Connection cmsCon, String id) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        
        try {
            String query = "DELETE FROM TERMINALMODLE WHERE MODLECODE=?";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            i = ps.executeUpdate();
            
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        return i;
    }

    
}
