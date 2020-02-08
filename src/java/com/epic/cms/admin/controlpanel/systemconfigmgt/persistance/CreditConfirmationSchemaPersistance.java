/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CreditConfirmationSchemaBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chinthaka_r
 */
public class CreditConfirmationSchemaPersistance {

    private ResultSet rs;
    private List<CreditConfirmationSchemaBean> schemaDetails;
    private String lastMaxLimit = null;

    public List<CreditConfirmationSchemaBean> getSchemaDetails(Connection con) throws Exception {
        PreparedStatement getAllSchemas = null;
        try {
            getAllSchemas = con.prepareStatement("SELECT SCHEMACODE,DESCRIPTION,MINLIMIT,MAXLIMIT FROM CREDITLIMITSCHEMA");

            schemaDetails = new ArrayList<CreditConfirmationSchemaBean>();

            rs = getAllSchemas.executeQuery();

            while (rs.next()) {

                CreditConfirmationSchemaBean schemaBean = new CreditConfirmationSchemaBean();

                schemaBean.setSchemaCode(rs.getString("SCHEMACODE"));
                schemaBean.setDescription(rs.getString("DESCRIPTION"));
                schemaBean.setMinLimit(rs.getString("MINLIMIT"));
                schemaBean.setMaxLimit(rs.getString("MAXLIMIT"));

                schemaDetails.add(schemaBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllSchemas.close();
        }
        return schemaDetails;
    }

    //get Last Max limit
    public String getLastMaxLimit(Connection con) throws Exception {
        PreparedStatement getLastMaxLimit = null;
        try {
            getLastMaxLimit = con.prepareStatement("SELECT MAX(MAXLIMIT) FROM CREDITLIMITSCHEMA");
            rs = getLastMaxLimit.executeQuery();
            if (rs.next()) {
                lastMaxLimit = rs.getString(1);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getLastMaxLimit.close();
        }
        return lastMaxLimit;
    }

    public boolean insertSchema(Connection con, CreditConfirmationSchemaBean schemaBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CREDITLIMITSCHEMA (SCHEMACODE,DESCRIPTION,MINLIMIT,MAXLIMIT,LASTUPDATEDUSER,CREATEDDATE,LASTUPDATEDDATE) VALUES (?,?,?,?,?,SYSDATE,SYSDATE)");
            insertStat.setString(1, schemaBean.getSchemaCode());
            insertStat.setString(2, schemaBean.getDescription());
            insertStat.setString(3, schemaBean.getMinLimit());
            insertStat.setString(4, schemaBean.getMaxLimit());
            //  insertStat.setString(5, schemaBean.getCreatedDate());
            insertStat.setString(5, schemaBean.getLastUpdatedUser());
            insertStat.executeUpdate();
            success = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean deleteSchema(Connection con, CreditConfirmationSchemaBean bean) throws Exception {
        boolean success = false;
        String lastSchemaCode = null;
        PreparedStatement Stat = null;
        try {
            Stat = con.prepareStatement("SELECT MAX(SCHEMACODE) FROM CREDITLIMITSCHEMA");
            rs = Stat.executeQuery();
            if (rs.next()) {
                lastSchemaCode = rs.getString(1);
            }
        } catch (SQLException ex) {
            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        }
        if (lastSchemaCode.equals(bean.getSchemaCode())) {
            try {
                Stat = con.prepareStatement("DELETE FROM CREDITLIMITSCHEMA WHERE SCHEMACODE=?");
                Stat.setString(1, bean.getSchemaCode());

                Stat.executeUpdate();
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
                rs.close();
                Stat.close();
            }
        }else{
            success=false;
        }
        return success;
    }

}
