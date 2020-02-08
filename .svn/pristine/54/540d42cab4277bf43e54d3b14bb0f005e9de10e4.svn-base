/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
/**
 *
 * @author upul
 */
public class DBConnection {
    
      /**
     * Get a database connection from the registered data source in the 
     * container.
     *
     * @return a database connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            InitialContext context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("EPIC_CMS_JNDI");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
        } catch (SQLException e) {
        }
        return connection;
    }

    public static void dbConnectionClose(Connection con) throws Exception {
        try {
            con.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    
    
    
}
