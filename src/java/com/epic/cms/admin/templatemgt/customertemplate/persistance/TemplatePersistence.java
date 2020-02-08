/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.templatemgt.customertemplate.persistance;

import com.epic.cms.admin.templatemgt.customertemplate.bean.CustomerTempBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateBean;
import com.epic.cms.admin.templatemgt.customertemplate.bean.TemplateCatagoryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janaka_h
 */
public class TemplatePersistence {

    private List<TemplateCatagoryBean> catagoryList ;
    private List<TemplateBean> templateList ;
    private ResultSet rs;
    
    public List<TemplateCatagoryBean> getAllTemplateCatagory(Connection con) throws SQLException {
        
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT TC.TEMPLATECATEGORYCODE,TC.TEMPLATECATEGORYNAME,TC.STATUS FROM TEMPLATECATEGORY TC WHERE TC.STATUS='ACT' ");

          


            catagoryList = new ArrayList<TemplateCatagoryBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                TemplateCatagoryBean bean = new TemplateCatagoryBean();

                bean.setCatagoryCode(rs.getString(1));
                bean.setCatagoryName(rs.getString(2));
                bean.setStatus(rs.getString(3));
                catagoryList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return catagoryList;
    }

    public List<TemplateBean> getAllTemplateLis(Connection con) throws SQLException {
        
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT CT.TEMPLATECODE,CT.TEMPLATENAME,VALIDFROM,VALIDTO,PRODUCTCODE,CURRENCYCODE,TOTALCREDITLIMITCT.STATUS FROM COMMONTEMPLATE CT ");

            


            templateList = new ArrayList<TemplateBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                TemplateBean bean = new TemplateBean();

                bean.setTemplateCatagoryCode(rs.getString(1));
                bean.setTemplateCode(rs.getString(2));
                bean.setDescription(rs.getString(3));
                bean.setStatus(rs.getString(4));
                templateList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return templateList;
    }

    public int insertTemplateRecord(Connection con, CustomerTempBean templateBean) throws Exception, SQLException {
        
        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO COMMONTEMPLATE "
                    + "(TEMPLATECATEGORYCODE,TEMPLATECODE,TEMPLATENAME,STATUS,LASTUPDATEDUSER) "
                   + "VALUES (?,?,?,?,?) ");
           
            insertStat.setString(1, templateBean.getTemplateCode());
            insertStat.setString(2, templateBean.getTemplateName()); 
            insertStat.setString(3, templateBean.getValiedFrom()); 
            insertStat.setString(4, templateBean.getValiedTo()); 
            insertStat.setString(5, templateBean.getProductCode()); 
            insertStat.setString(6, templateBean.getCurrencyCode()); 
            insertStat.setString(7, templateBean.getTotalCreditLimit()); 
            insertStat.setString(8, templateBean.getStatus()); 
            insertStat.setString(9, templateBean.getLastUpdateduser());
 
            

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
             try {                
                    insertStat.close();
               
            } catch (Exception e) {
                throw e;
            }
            
        }

        return resultId;
        
    }

    public int updateTemplateRecord(Connection con, TemplateBean templateBean) throws Exception {
        int resultId;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE COMMONTEMPLATE SET "
                    + "TEMPLATECATEGORYCODE=?,TEMPLATENAME=?,STATUS=?,LASTUPDATEDUSER=? "
                   + "WHERE TEMPLATECODE=? ");
            insertStat.setString(1, templateBean.getTemplateCatagoryCode());
            insertStat.setString(2, templateBean.getDescription()); 
            insertStat.setString(3, templateBean.getStatus()); 
            insertStat.setString(4, templateBean.getLastUpdatedUser());
            insertStat.setString(5, templateBean.getTemplateCode());
 
            

            resultId = insertStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
             try {                
                    insertStat.close();
               
            } catch (Exception e) {
                throw e;
            }
            
        }

        return resultId;
    }

    public int deleteTemplate(Connection con, TemplateBean templateBean) throws Exception {
        int result;
        PreparedStatement deleteStat = null;
        try {
            deleteStat = con.prepareStatement("DELETE FROM COMMONTEMPLATE WHERE TEMPLATECODE=? ");
            deleteStat.setString(1, templateBean.getTemplateCode());
             result= deleteStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            deleteStat.close();
        }

        return result;
    }
    
}
