/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfig.creditscore.persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *this persistence class use to wrote all the persistence that relate to credit score
 * @author ayesh
 */
public class CreditScorePersistence {

    private ResultSet rs = null;

    /**
     * get all priority level details
     * @param con
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAllPriorityLevels(Connection con) throws Exception {

        PreparedStatement ps = null;
        HashMap<String, String> priorityLevelList = null;
        try {
            ps = con.prepareStatement("SELECT TC.PRIORITYLEVELCODE,TC.DESCRIPTION "
                    + "FROM PRIORITYLEVEL TC");

            rs = ps.executeQuery();
            priorityLevelList = new HashMap<String, String>();

            while (rs.next()) {
                priorityLevelList.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (SQLException e) {
            throw e;
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

        return priorityLevelList;
    }
}
