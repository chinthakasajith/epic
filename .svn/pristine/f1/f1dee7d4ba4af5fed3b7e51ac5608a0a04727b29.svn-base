/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.application.common.persistance;

import com.epic.cms.application.common.bean.OperatorBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author upul
 */
public class OperatorPersistance {
   
    
    
    
    private ResultSet rs;
    private List<OperatorBean> operatorLst;
    
    public List<OperatorBean> getAllOperatorList(Connection con) throws Exception {
        PreparedStatement getAllOperatorListStat = null;
        try {
            getAllOperatorListStat = con.prepareStatement("SELECT OP.OPERATORCODE,OP.DESCRIPTION,OP.STATUS,OP.SORTKEY FROM OPERATOR OP WHERE OP.STATUS=? ORDER BY SORTKEY");

            getAllOperatorListStat.setString(1, StatusVarList.ACTIVE_STATUS);


            operatorLst = new ArrayList<OperatorBean>();

            rs = getAllOperatorListStat.executeQuery();

            while (rs.next()) {

                OperatorBean operatorBean = new OperatorBean();

                operatorBean.setOperatorCode(rs.getString("OPERATORCODE"));
                operatorBean.setDescription(rs.getString("DESCRIPTION"));
                operatorBean.setStatus(rs.getString("STATUS"));
              
                operatorLst.add(operatorBean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllOperatorListStat.close();
        }
        return operatorLst;
    }
    
    
    
    
}
