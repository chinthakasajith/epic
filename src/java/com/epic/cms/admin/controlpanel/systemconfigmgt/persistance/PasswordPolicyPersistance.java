/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.PasswordPolicyBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * all password policy persistance method wrote here
 * @author ayesh
 */
public class PasswordPolicyPersistance {

    private ResultSet rs = null;

    /**
     * get all details about password policy
     * @param con - java.sql.Connection
     * @return PasswordPolicyBean
     * @throws SQLException
     * @throws Exception 
     */
    public PasswordPolicyBean getAllDetails(Connection con) throws SQLException, Exception {
        PreparedStatement ps = null;
        try {
            PasswordPolicyBean passBean = new PasswordPolicyBean();
            String query = "SELECT PS.PASSWORDPOLICYID,PS.MINIMUMLENGTH,PS.MAXIMUMLENGTH,"
                    + "PS.MINIMUMSPECIALCHARACTERS,PS.MINIMUMUPPERCASECHARACTERS,"
                    + "PS.MINIMUMNUMERICALCHARACTERS,PS.MINIMUMLOWERCASECHARACTERS,"                  
                    + "PS.ALLOWDREPEATCHARACTERS,PS.PASSWORDEXPIRYPERIOD,"
                    + "PS.PASSWORDEXPIRYNOTIFYPERIOD,PS.NOOFHISTORYPASSWORD,"
                    + "PS.IDLEACCOUNTEXPIRYPERIOD,PS.NOOFINVALIDLOGINATTEMPT,"                    
                    + "PS.LASTUPDATEDUSER,PS.LASTUPDATEDTIME,PS.CREATEDTIME,PS.FIRSTPASSWORDEXPIRYPERIOD FROM PASSWORDPOLICY PS";
            
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                passBean.setPolicyID(rs.getInt("PASSWORDPOLICYID"));
                passBean.setMaxLen(rs.getString("MAXIMUMLENGTH"));
                passBean.setMinLen(rs.getString("MINIMUMLENGTH"));
                passBean.setMinSpclCharacterLen(rs.getString("MINIMUMSPECIALCHARACTERS"));
                passBean.setMinUpperCase(rs.getString("MINIMUMUPPERCASECHARACTERS"));
                passBean.setMinNumCharacter(rs.getString("MINIMUMNUMERICALCHARACTERS"));
                passBean.setMinLowerCaseCharacter(rs.getString("MINIMUMLOWERCASECHARACTERS"));
                passBean.setLastUpdateuser(rs.getString("LASTUPDATEDUSER"));
                passBean.setLastUpdateDate(rs.getDate("LASTUPDATEDTIME"));               
                passBean.setAllowedReptCharacters(rs.getString("ALLOWDREPEATCHARACTERS"));
                passBean.setPasswordExpiryPeriod(rs.getString("PASSWORDEXPIRYPERIOD"));
                passBean.setPasswordExpNotifyPeriod(rs.getString("PASSWORDEXPIRYNOTIFYPERIOD"));
                passBean.setNoOfHistoryPasswords(rs.getString("NOOFHISTORYPASSWORD"));
                passBean.setAccountExpiryPeriod(rs.getString("IDLEACCOUNTEXPIRYPERIOD"));
                passBean.setNoOfInvalidLoginAtmps(rs.getString("NOOFINVALIDLOGINATTEMPT")); 
                passBean.setFirstPassExpiryPeriod(rs.getInt("FIRSTPASSWORDEXPIRYPERIOD"));
            }
            return passBean;
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
     * update password policy 
     * @param con
     * @param passBean
     * @return
     * @throws Exception 
     */
    public int updatePasswordPolicy(Connection con, PasswordPolicyBean passBean) throws Exception {
        int resultID = -1;
        PreparedStatement ps = null;
        try {
            String query = "UPDATE PASSWORDPOLICY SET MAXIMUMLENGTH=?,MINIMUMLENGTH=?,"
                    + "MINIMUMSPECIALCHARACTERS=?,MINIMUMUPPERCASECHARACTERS=?,"
                    + "MINIMUMNUMERICALCHARACTERS=?,MINIMUMLOWERCASECHARACTERS=?,"                   
                    + "ALLOWDREPEATCHARACTERS=?,PASSWORDEXPIRYPERIOD=?,"
                    + "PASSWORDEXPIRYNOTIFYPERIOD=?,NOOFHISTORYPASSWORD=?,"
                    + "IDLEACCOUNTEXPIRYPERIOD=?,NOOFINVALIDLOGINATTEMPT=?,"                    
                    + "LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,FIRSTPASSWORDEXPIRYPERIOD=? WHERE PASSWORDPOLICYID=?";

            ps = con.prepareStatement(query);

            ps.setString(1, passBean.getMaxLen());
            ps.setString(2, passBean.getMinLen());
            ps.setString(3, passBean.getMinSpclCharacterLen());
            ps.setString(4, passBean.getMinUpperCase());
            ps.setString(5, passBean.getMinNumCharacter());
            ps.setString(6, passBean.getMinLowerCaseCharacter());
            
            ps.setString(7, passBean.getAllowedReptCharacters());
            ps.setString(8, passBean.getPasswordExpiryPeriod());
            ps.setString(9, passBean.getPasswordExpNotifyPeriod());
            ps.setString(10, passBean.getNoOfHistoryPasswords());
            ps.setString(11, passBean.getAccountExpiryPeriod());
            ps.setString(12, passBean.getNoOfInvalidLoginAtmps());
            
            ps.setString(13, passBean.getLastUpdateuser());
            ps.setTimestamp(14, SystemDateTime.getSystemDataAndTime());
            ps.setInt(15, passBean.getFirstPassExpiryPeriod());
            ps.setInt(16, passBean.getPolicyID());

            resultID = ps.executeUpdate();

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

        return resultID;
    }
}
