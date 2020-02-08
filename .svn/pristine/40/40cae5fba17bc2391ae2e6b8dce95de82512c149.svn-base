/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.cms.system.util.datetime;




import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.system.util.variable.MessageVarList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
//import javax.ejb.EJBException;

/**
 *
 * @author saminda
 */
public class SystemDateTime {



    //initialize, declare variables
    static Connection con = null;
    static DateTime dateTime = null;





    //get system date and time
    public static Timestamp getSystemDataAndTime() throws Exception {

        Timestamp systemDateTime = null;

        try {
            //create method for persistance class
            dateTime = new DateTime();

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            systemDateTime = dateTime.getSystemDataAndTime(con);

            con.commit();
        } catch (Exception exception) {
            try {
                con.rollback();
            } catch (SQLException sqx) {
//                throw new EJBException(MessageVarList.ROLLBACK_FAILED +
//                        sqx.getMessage());
            }
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return systemDateTime;
    }






}
