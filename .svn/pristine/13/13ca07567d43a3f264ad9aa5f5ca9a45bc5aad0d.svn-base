/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.system.util.datetime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author saminda
 */
public class DateTime {


    //initialize, declare variables
    private String sqlstr = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Timestamp systemDateTime = null;
    private String stringDate = null;



    //get system date and time from the database
    public Timestamp getSystemDataAndTime(Connection con) throws Exception {
        try {
            sqlstr = " SELECT SYSDATE " +
                     " FROM DUAL ";

            // create the prepared statement and add the criteria
            ps = con.prepareStatement(sqlstr);

            // process the results
            rs = ps.executeQuery();
            while (rs.next()) {
                stringDate = rs.getString("SYSDATE");
            }

            //then convert it to timestamp
            systemDateTime = Timestamp.valueOf(stringDate);
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return systemDateTime;
    }




}
