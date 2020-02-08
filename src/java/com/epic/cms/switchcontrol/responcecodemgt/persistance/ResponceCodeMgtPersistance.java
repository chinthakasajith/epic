/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.switchcontrol.responcecodemgt.persistance;

import com.epic.cms.switchcontrol.responcecodemgt.bean.ResponceCodeBean;
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
public class ResponceCodeMgtPersistance {

    private ResultSet rs;

    public List<ResponceCodeBean> getResponceCodes(Connection conOnline) throws Exception {
        PreparedStatement getAllByCatStat = null;
        List<ResponceCodeBean> responceList = new ArrayList<ResponceCodeBean>();
        try {
            getAllByCatStat = conOnline.prepareStatement("SELECT R.CODE, R.DESCRIPTION FROM ECMS_ONLINE_RESPONSE_CODE R");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                ResponceCodeBean responce = new ResponceCodeBean();


                responce.setResponceCode(rs.getString("CODE"));
                responce.setDescription(rs.getString("DESCRIPTION"));



                responceList.add(responce);

            }

        } catch (SQLException ex) {
            throw ex;

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();

        }
        return responceList;
    }

    public boolean insertResponceCode(Connection conOnline, ResponceCodeBean responce) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = conOnline.prepareStatement("INSERT INTO ECMS_ONLINE_RESPONSE_CODE"
                    + " (CODE,DESCRIPTION)"
                    + " VALUES(?,?) ");



            insertStat.setString(1, responce.getResponceCode());
            insertStat.setString(2, responce.getDescription());


            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateResponceCode(Connection conOnline, ResponceCodeBean responce) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_RESPONSE_CODE"
                    + " SET DESCRIPTION =? "
                    + " WHERE CODE=? ");



            updateStat.setString(1, responce.getDescription());
            updateStat.setString(2, responce.getResponceCode());



            updateStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public boolean deleteResponceCode(Connection conOnline, ResponceCodeBean responce) throws Exception {
        boolean success = false;
        PreparedStatement deleteStat = null;
        try {

            deleteStat = conOnline.prepareStatement("DELETE ECMS_ONLINE_RESPONSE_CODE WHERE CODE=?");

            deleteStat.setString(1, responce.getResponceCode());


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
