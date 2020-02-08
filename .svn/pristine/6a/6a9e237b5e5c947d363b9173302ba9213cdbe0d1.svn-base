/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;

import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionPageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SectionBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author upul
 */
public class ApplicationSectionPersistance {
    
    private ResultSet rs = null;
    private List<ApplicationSectionPageBean> applicationSectionList = null;
    private List<SectionBean> sectionList = null;
            
            
  /**
     * insertApplicationSection
     * @param con
     * @param applicationSection
     * @return
     * @throws Exception 
     */
     public int insertApplicationSection(Connection con, ApplicationSectionPageBean applicationSection) throws Exception {
        int resultId = -1;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = con.prepareStatement("INSERT INTO APPLICATIONSECTION(APPLICATIONCODE,SECTIONCODE,LASTUPDATEDUSER,LASTUPDATEDTIME) "
                    + "VALUES (?,?,?,?) ");
            insertStatement.setString(1, applicationSection.getApplicationCode());
            insertStatement.setString(2, applicationSection.getSectionCode());
            insertStatement.setString(3, applicationSection.getLastUpdatedUser());
            insertStatement.setTimestamp(4, SystemDateTime.getSystemDataAndTime());

            resultId = insertStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {

            insertStatement.close();
        }
        return resultId;
    }
  
     
     /**
      * deleteApplicationSection
      * @param con
      * @param sectionPage
      * @return
      * @throws Exception 
      */
      public int deleteApplicationSection(Connection con, ApplicationSectionPageBean sectionPage) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = con.prepareStatement(" DELETE APPLICATIONSECTION WHERE APPLICATIONCODE=? AND SECTIONCODE=?");

            //set parameters for delete
            deleteStatement.setString(1, sectionPage.getApplicationCode());
            deleteStatement.setString(2, sectionPage.getSectionCode());
            

            resultId = deleteStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {

            deleteStatement.close();
        }
        return resultId;
    }
    
    
    

}
