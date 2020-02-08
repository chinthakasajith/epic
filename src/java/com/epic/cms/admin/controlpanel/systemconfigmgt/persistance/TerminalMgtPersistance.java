package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.TerminalBean;
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
public class TerminalMgtPersistance {

    private ResultSet rs = null;

    public List<TerminalBean> getTerminalDetails(Connection cmsCon) throws SQLException, Exception {
        List<TerminalBean> allTerminal = new ArrayList<TerminalBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT MANUFACTURECODE, DESCRIPTION FROM TERMINALMANUFACTURE";

        try {

            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                TerminalBean bean = new TerminalBean();
                bean.setManufactureCode(rs.getString("MANUFACTURECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                allTerminal.add(bean);
            }
            return allTerminal;

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
    public int insertNewCode(Connection cmsCon, TerminalBean bean) throws SQLException, Exception  {
        int i = -1;
        PreparedStatement ps = null;
        String query;
        
        query = "INSERT INTO TERMINALMANUFACTURE(MANUFACTURECODE, DESCRIPTION, LASTUPDATEDUSER, LASTUPDATEDTIME, CREATEDTIME) VALUES(?,?,?,?,SYSDATE)";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, bean.getManufactureCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getLastUpdatedUser());
            ps.setTimestamp(4, new Timestamp(bean.getLastUpdatedDate().getTime()));
            
            i = ps.executeUpdate();
            
                       
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
        return i;
    }
    
    public TerminalBean viewSelectedData(Connection cmsCon, String id) throws Exception {
        TerminalBean bean = new TerminalBean();
        PreparedStatement ps = null;
        String query;
        
        query = "SELECT MANUFACTURECODE , DESCRIPTION FROM TERMINALMANUFACTURE WHERE MANUFACTURECODE = ?";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bean.setManufactureCode(rs.getString("MANUFACTURECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
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

    public int updateTerminalInfo(Connection cmsCon, TerminalBean bean) throws SQLException, Exception  {
        int rowCount = -1;
        PreparedStatement ps = null;
        String query;
        
        query = "UPDATE TERMINALMANUFACTURE SET DESCRIPTION=? WHERE MANUFACTURECODE=?";
        
        try {
            
            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getManufactureCode());
            
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

    
     public int deleteTerminalInfo(Connection cmsCon, String id) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        
        try {
            String query = "DELETE FROM TERMINALMANUFACTURE WHERE MANUFACTURECODE=?";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            i = ps.executeUpdate();
            
        } catch (Exception ex) {
            throw ex;
        } finally {
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
