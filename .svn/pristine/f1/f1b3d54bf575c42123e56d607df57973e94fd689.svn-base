/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.sysusermgt.persistance;


import com.epic.cms.admin.controlpanel.sysusermgt.bean.PageBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.ApplicationSectionPageBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
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
public class ApplicationSectionPagePersistance {

    private ResultSet rs = null;
    private List<ApplicationSectionPageBean> sectionPageList = null;
    private List<PageBean> pageList = null;

    /**
     * find sectionpage by application code
     * @param con
     * @param applicationCode
     * @return
     * @throws Exception 
     */
    public List<ApplicationSectionPageBean> findSectionPageByApplicationCode(Connection con, String applicationCode) throws Exception {
        PreparedStatement findByAppStatement = null;
        try {

            
            
          
            findByAppStatement = con.prepareStatement("SELECT  ISE.DESCRIPTION AS SECTIONCODE , IP.DESCRIPTION AS PAGECODE, ISP.CREATETIME "
                  + "  FROM SECTIONPAGE ISP,SECTION ISE,PAGE IP,APPLICATIONSECTION AP "
                 + " WHERE  ISP.SECTIONCODE=AP.SECTIONCODE AND  ISP.SECTIONCODE=ISE.SECTIONCODE AND ISP.PAGECODE=IP.PAGECODE AND AP.APPLICATIONCODE=? ");

            ////set parameters
            findByAppStatement.setString(1, applicationCode);
            rs = findByAppStatement.executeQuery();
            sectionPageList = new ArrayList<ApplicationSectionPageBean>();
            while (rs.next()) {
                ApplicationSectionPageBean sectionPage = new ApplicationSectionPageBean();            
                sectionPage.setSectionCode(rs.getString("SECTIONCODE"));
                sectionPage.setPageCode(rs.getString("PAGECODE"));
                sectionPage.setCreatedTime(rs.getDate("CREATETIME"));

                sectionPageList.add(sectionPage);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            findByAppStatement.close();
        }
        return sectionPageList;
    }

    /////////////////////////////////////////////////
    /**
     * insert application section list to database
     * @param con
     * @param sectionPage
     * @return
     * @throws Exception 
     */
    public int insertApplicationSection(Connection con, ApplicationSectionPageBean applicationSection,String sysUser) throws Exception {
        int resultId = -1;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = con.prepareStatement("INSERT INTO APPLICATIONSECTION(APPLICATIONCODE,SECTIONCODE,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES (?,?,?,?) ");
            insertStatement.setString(1, applicationSection.getApplicationCode());
            System.out.println(applicationSection.getApplicationCode()+"*********************************");
            insertStatement.setString(2, applicationSection.getSectionCode());
            System.out.println(applicationSection.getSectionCode()+"*********************************");
            insertStatement.setString(3, sysUser);
            insertStatement.setTimestamp(4, SystemDateTime.getSystemDataAndTime());

            resultId = insertStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {

            insertStatement.close();
        }
        return resultId;
    }

    ////////////////////////////////////////////////////
    /**
     * delete section page
     * @param con
     * @param sectionPage
     * @return
     * @throws Exception 
     */
    public int deleteApplicationSection(Connection con, String  applicationCode,String sectionCode) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStatement = null;
        try {
            //deleteStatement = con.prepareStatement("DELETE APPLICATIONSECTION WHERE APPLICATIONCODE=? ");
            deleteStatement = con.prepareStatement("DELETE APPLICATIONSECTION WHERE APPLICATIONCODE=? AND SECTIONCODE=?" );

            //set parameters for delete
            deleteStatement.setString(1,applicationCode);
            deleteStatement.setString(2,sectionCode);


            resultId = deleteStatement.executeUpdate();

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            deleteStatement.close();
        }
        return resultId;
    }
    
    /////////////////////////////////////////////////////
    
        ////////////////////////////////////////////////////
    /**
     * findExistanceOfApplicationSection
     * @param con
     * @param applicationCode
     * @param sectionCode
     * @return
     * @throws Exception 
     */
    public boolean findExistanceOfUserApplicationSection(Connection con, String  applicationCode,String sectionCode) throws Exception {
        boolean isExist=false;
        PreparedStatement findStatement = null;
        try {
           
            findStatement = con.prepareStatement("SELECT A.APPLICATIONCODE,A.SECTIONCODE FROM USERAPPLICATIONSECTION A WHERE A.APPLICATIONCODE=? AND A.SECTIONCODE=? " );

            //set parameters for select
            findStatement.setString(1,applicationCode);
            findStatement.setString(2,sectionCode);


            rs = findStatement.executeQuery();
             while (rs.next()) {
                 isExist=true;
                 
             }
             
             return isExist;

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            findStatement.close();
        }
       
    }
    
    
    
    ////////////////////////////////////////////////////////
   
    
    
    
    
        
        ////////////////////////////////////////////////////
    /**
     * findExistanceOfApplicationSection
     * @param con
     * @param applicationCode
     * @param sectionCode
     * @return
     * @throws Exception 
     */
    public boolean findExistanceOfApplicationSection(Connection con, String  applicationCode,String sectionCode) throws Exception {
        boolean isExist=false;
        PreparedStatement findStatement = null;
        try {
           
            findStatement = con.prepareStatement("SELECT A.APPLICATIONCODE,A.SECTIONCODE FROM APPLICATIONSECTION A WHERE A.APPLICATIONCODE=? AND A.SECTIONCODE=? " );

            //set parameters for select
            findStatement.setString(1,applicationCode);
            findStatement.setString(2,sectionCode);


            rs = findStatement.executeQuery();
             while (rs.next()) {
                 isExist=true;
                 
             }
             
             return isExist;

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            findStatement.close();
        }
       
    }
    
    
    
    ////////////////////////////////////////////////////////
    
    
    
          ////////////////////////////////////////////////////
    /**
     * findExistanceOfSectionPage
     * @param con
     * @param sectionCode
     * @param pageCode
     * @return
     * @throws Exception 
     */
    public boolean findExistanceOfUserSectionPage(Connection con, String  sectionCode,String pageCode) throws Exception {
        boolean isExist=false;
        PreparedStatement findStatement = null;
        try {
           
            findStatement = con.prepareStatement("SELECT A.SECTIONCODE,A.PAGECODE FROM USERSECTIONPAGE A WHERE A.SECTIONCODE=? AND A.PAGECODE=? " );

            //set parameters for select
            findStatement.setString(1,sectionCode);
            findStatement.setString(2,pageCode);


            rs = findStatement.executeQuery();
             while (rs.next()) {
                 isExist=true;
                 
             }
             
             return isExist;

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            findStatement.close();
        }
       
    }
    
    
    
    ////////////////////////////////////////////////////////
    
      ////////////////////////////////////////////////////
    /**
     * findExistanceOfSectionPage
     * @param con
     * @param sectionCode
     * @param pageCode
     * @return
     * @throws Exception 
     */
    public boolean findExistanceOfSectionPage(Connection con, String  sectionCode,String pageCode) throws Exception {
        boolean isExist=false;
        PreparedStatement findStatement = null;
        try {
           
            findStatement = con.prepareStatement("SELECT A.SECTIONCODE,A.PAGECODE FROM SECTIONPAGE A WHERE A.SECTIONCODE=? AND A.PAGECODE=? " );

            //set parameters for select
            findStatement.setString(1,sectionCode);
            findStatement.setString(2,pageCode);


            rs = findStatement.executeQuery();
             while (rs.next()) {
                 isExist=true;
                 
             }
             
             return isExist;

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            findStatement.close();
        }
       
    }
    
    
    
    ////////////////////////////////////////////////////////
    /**
     * insertSectionPage
     * @param con
     * @param applicationSection
     * @return
     * @throws Exception 
     */
     public int insertSectionPage(Connection con, ApplicationSectionPageBean applicationSection,String sysUser) throws Exception {
        int resultId = -1;
        PreparedStatement insertStatement = null;
        try {
            insertStatement = con.prepareStatement("INSERT INTO SECTIONPAGE(SECTIONCODE,PAGECODE,LASTUPDATEDUSER,CREATETIME) "
                    + "VALUES (?,?,?,?) ");
            insertStatement.setString(1, applicationSection.getSectionCode());
            System.out.println(applicationSection.getSectionCode()+"*********************************");
            insertStatement.setString(2, applicationSection.getPageCode());
            System.out.println(applicationSection.getPageCode()+"*********************************");
            insertStatement.setString(3, sysUser);
            insertStatement.setTimestamp(4, SystemDateTime.getSystemDataAndTime());

            resultId = insertStatement.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } finally {

            insertStatement.close();
        }
        return resultId;
    }


    ///////////////////////////////////////////////////////
     
     
     
    ///////////////////////////////////////////////////////
    /**
      * deleteSectionPage
      * @param con
      * @param sectionCode
      * @param pageCode
      * @return
      * @throws Exception 
      */
    public int deleteSectionPage(Connection con, String  sectionCode,String pageCode) throws Exception {
        int resultId = -1;
        PreparedStatement deleteStatement = null;
        try {
            //deleteStatement = con.prepareStatement("DELETE APPLICATIONSECTION WHERE APPLICATIONCODE=? ");
            deleteStatement = con.prepareStatement("DELETE SECTIONPAGE WHERE SECTIONCODE=? AND PAGECODE=?" );

            //set parameters for delete
            deleteStatement.setString(1,sectionCode);
            deleteStatement.setString(2,pageCode);


            resultId = deleteStatement.executeUpdate();

        } catch (SQLException ex) {
           
            throw ex;
        } finally {

            deleteStatement.close();
        }
        return resultId;
    }
    
    /////////////////////////////////////////////////////
     
    /**
     * find page by section and application code
     * @param con
     * @param applicationCode
     * @param sectionCode
     * @return
     * @throws Exception 
     */
    public List<PageBean> findPageBySectionAndApplicationNotAssign(Connection con, String applicationCode, String sectionCode) throws Exception {
        PreparedStatement findpageStatement = null;
        try {

            findpageStatement = con.prepareStatement("SELECT IP.PAGECODE,IP.DESCRIPTION "
                    + "FROM PAGE IP "
                    + "WHERE IP.PAGECODE NOT IN (SELECT ISP.PAGECODE FROM SECTIONPAGE ISP "
                    + "WHERE  ISP.USERROLECODE=?  ) AND IP.STATUSCODE=? ");



            findpageStatement.setString(1, applicationCode);
            findpageStatement.setString(2, StatusVarList.ACTIVE_STATUS);

            rs = findpageStatement.executeQuery();
            pageList = new ArrayList<PageBean>();
            while (rs.next()) {
                PageBean page = new PageBean();
                page.setPageCode(rs.getString("PAGECODE"));
                page.setDescription(rs.getString("DESCRIPTION"));
                pageList.add(page);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findpageStatement.close();
        }

        return pageList;
    }

    ////////////////////////////////////////
    /**
     * findPageBySectionAndApplicationCode
     * @param con
     * @param applicationCode
     * @param sectionCode
     * @return
     * @throws Exception 
     */
    public List<PageBean> findPageBySectionAndApplicationCode(Connection con, String applicationCode, String sectionCode) throws Exception {

        PreparedStatement findpageStatement = null;
        try {

            findpageStatement = con.prepareStatement("SELECT IP.PAGECODE,IP.DESCRIPTION "
                    + "FROM SECTIONPAGE ISP, PAGE IP "
                    + "WHERE ISP.USERROLECODE=? AND ISP.SECTIONCODE=? AND IP.PAGECODE=ISP.PAGECODE ");
            findpageStatement.setString(1, applicationCode);
            findpageStatement.setString(2, sectionCode);

            rs = findpageStatement.executeQuery();
            pageList = new ArrayList<PageBean>();
            while (rs.next()) {
                PageBean page = new PageBean();
                page.setPageCode(rs.getString("PAGECODE"));
                page.setDescription(rs.getString("DESCRIPTION"));
                pageList.add(page);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            findpageStatement.close();
        }

        return pageList;
    }
}
