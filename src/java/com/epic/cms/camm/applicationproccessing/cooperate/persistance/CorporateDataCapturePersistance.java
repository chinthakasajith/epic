/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.cooperate.persistance;

import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstCardManagerDetailsBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.EstablishCardApplicationBean;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class CorporateDataCapturePersistance {
    private ArrayList<EstCardManagerDetailsBean> managerDetails;
    private ResultSet rs;
    
      public EstablishCardApplicationBean getAllDetailsCompany(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query
        EstablishCardApplicationBean resultCompany = null;


        try {

            strSqlBasic = "SELECT cd.* FROM CARDESTAPPLICATIONDETAILS cd WHERE cd.APPLICATIONID=?";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();



            while (rs.next()) {                

                resultCompany = new EstablishCardApplicationBean();

                resultCompany.setAppId(rs.getString("APPLICATIONID"));
                resultCompany.setCompanyName(rs.getString("COMPANYNAME"));
                resultCompany.setAddress1(rs.getString("ADDRESS1"));
                resultCompany.setAddress2(rs.getString("ADDRESS2"));
                resultCompany.setAddress3(rs.getString("ADDRESS3"));
                resultCompany.setArea(rs.getString("AREA"));
                resultCompany.setDistrict(rs.getString("DISTRICT"));
                resultCompany.setProvince(rs.getString("PROVINCE"));
                resultCompany.setOfficePhone1(rs.getString("OFFICEPHONE1"));
                resultCompany.setOfficeFax(rs.getString("OFFICEFAX"));
                resultCompany.setOfficeEmail(rs.getString("OFFICEEMAIL"));
                resultCompany.setContactPerson(rs.getString("CONTACTPERSON"));
                resultCompany.setContactPerPosition(rs.getString("CONTACTPERSONPOSITION"));
                resultCompany.setBrcNumber(rs.getString("BRCNUMBER"));
                resultCompany.setRegisterDate(rs.getString("REGISTEREDDATE"));
                resultCompany.setRegisterPlace(rs.getString("REGISTEREDPLACE"));
                resultCompany.setTypeOfCompany(rs.getString("TYPEOFCOMPANY"));
                resultCompany.setNumberOfEmployees(rs.getString("NUMBEROFEMPLOYEES"));
                resultCompany.setCapitalInvested(rs.getString("CAPITALINVESTED"));
                resultCompany.setAnnualTurnover(rs.getString("ANNUALTURNOVER"));
                resultCompany.setAnnualTurnoverYear(rs.getString("ANNUALTURNOVERYEAR"));
                resultCompany.setAuditName(rs.getString("AUDITORNAME"));
                resultCompany.setAuditAddress1(rs.getString("AUDITORADDRESS1"));
                resultCompany.setAuditAddress2(rs.getString("AUDITORADDRESS2"));
                resultCompany.setAuditAddress3(rs.getString("AUDITORADDRESS3"));
                resultCompany.setAuditArea(rs.getString("AUDITORAREA"));
                resultCompany.setAuditDistrict(rs.getString("AUDITORDISTRICT"));
                resultCompany.setAuditProvince(rs.getString("AUDITORPROVINCE"));
                resultCompany.setAuditOficePhone1(rs.getString("AUDITOROFFICEPHONE1"));
                resultCompany.setAuditOficePhone2(rs.getString("AUDITOROFFICEPHONE2"));
                resultCompany.setAuditContactPerPosition(rs.getString("AUDITORCONTACTPERSONPOSITION"));
                resultCompany.setAuditFax(rs.getString("AUDITORFAX"));
                resultCompany.setAuditEmail(rs.getString("AUDITOROFFICEMAIL"));
                resultCompany.setAuditContactPerson(rs.getString("AUDITORCONTACTPERSON"));
                resultCompany.setCommenceDate(rs.getString("COMENCEMENTDATE"));
                resultCompany.setOfficePhone2(rs.getString("OFFICEPHONE2"));
                resultCompany.setBusinessNature(rs.getString("NATUREOFBUSINESS"));
                

            }

        } catch (SQLException exSql) {
            throw exSql;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }

        return resultCompany;
    }
      
    
        public int insertcomapnyRecord(Connection con, EstablishCardApplicationBean companyBean) throws Exception {
        int resultId;
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date regDate = df.parse(companyBean.getRegisterDate());
        Date comenceDate = df.parse(companyBean.getCommenceDate());

        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDESTAPPLICATIONDETAILS "
                    + "(APPLICATIONID, COMPANYNAME, ADDRESS1, ADDRESS2, ADDRESS3, AREA, DISTRICT, PROVINCE, OFFICEPHONE1, OFFICEFAX, OFFICEEMAIL, CONTACTPERSON, "
                    + "CONTACTPERSONPOSITION, BRCNUMBER, REGISTEREDDATE, REGISTEREDPLACE, TYPEOFCOMPANY, NUMBEROFEMPLOYEES, CAPITALINVESTED, ANNUALTURNOVER, "
                    + "ANNUALTURNOVERYEAR, AUDITORNAME, AUDITORADDRESS1, AUDITORADDRESS2, AUDITORADDRESS3, AUDITORAREA, AUDITORDISTRICT, AUDITORPROVINCE, "
                    + "AUDITOROFFICEPHONE1, AUDITOROFFICEPHONE2, AUDITORCONTACTPERSON, AUDITORCONTACTPERSONPOSITION, AUDITORFAX, AUDITOROFFICEMAIL, "
                    + "OFFICEPHONE2, COMENCEMENTDATE, NATUREOFBUSINESS ) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");



            insertStat.setString(1, companyBean.getAppId());
            insertStat.setString(2, companyBean.getCompanyName());
            insertStat.setString(3, companyBean.getAddress1());
            insertStat.setString(4, companyBean.getAddress2());
            insertStat.setString(5, companyBean.getAddress3());
            insertStat.setString(6, companyBean.getArea());
            insertStat.setString(7, companyBean.getDistrict());
            insertStat.setString(8, companyBean.getProvince());
            insertStat.setString(9, companyBean.getOfficePhone1());
            insertStat.setString(10, companyBean.getOfficeFax());
            insertStat.setString(11, companyBean.getOfficeEmail());
            insertStat.setString(12, companyBean.getContactPerson());
            insertStat.setString(13, companyBean.getContactPerPosition());
            insertStat.setString(14, companyBean.getBrcNumber());
            insertStat.setTimestamp(15, new Timestamp(regDate.getTime()));
            insertStat.setString(16, companyBean.getRegisterPlace());
            if(!companyBean.getTypeOfCompany().equals("EMPOTH")){
                insertStat.setString(17, companyBean.getTypeOfCompany());
            }else{
                insertStat.setString(17, companyBean.getOtherTypeOfCompany());
            }            
            insertStat.setString(18, companyBean.getNumberOfEmployees());
            insertStat.setString(19, companyBean.getCapitalInvested());
            insertStat.setString(20, companyBean.getAnnualTurnover());
            insertStat.setString(21, companyBean.getAnnualTurnoverYear());
            insertStat.setString(22, companyBean.getAuditName());
            insertStat.setString(23, companyBean.getAuditAddress1());
            insertStat.setString(24, companyBean.getAuditAddress2());
            insertStat.setString(25, companyBean.getAuditAddress3());
            insertStat.setString(26, companyBean.getAuditArea());
            insertStat.setString(27, companyBean.getAuditDistrict());
            insertStat.setString(28, companyBean.getAuditProvince());
            insertStat.setString(29, companyBean.getAuditOficePhone1());
            insertStat.setString(30, companyBean.getAuditOficePhone2());
            insertStat.setString(31, companyBean.getAuditContactPerson());
            insertStat.setString(32, companyBean.getAuditContactPerPosition());
            insertStat.setString(33, companyBean.getAuditFax());
            insertStat.setString(34, companyBean.getAuditEmail());
            insertStat.setString(35, companyBean.getOfficePhone2());
            insertStat.setTimestamp(36, new Timestamp(comenceDate.getTime()));
            if(!companyBean.getBusinessNature().equals("OTHER")){
                insertStat.setString(37, companyBean.getBusinessNature());
            }else{
                insertStat.setString(37, companyBean.getOtherBusinessNature());
            }            
           
            
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
      
        public int updateCompanyRecord(Connection con, EstablishCardApplicationBean companyBean) throws Exception {
        int resultId;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date regDate = df.parse(companyBean.getRegisterDate());
        Date comenceDate = df.parse(companyBean.getCommenceDate());
        
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDESTAPPLICATIONDETAILS SET "
                    + "COMPANYNAME=?, ADDRESS1=?, ADDRESS2=?, ADDRESS3=?, AREA=?, DISTRICT=?, PROVINCE=?, OFFICEPHONE1=?, OFFICEFAX=?, OFFICEEMAIL=?, CONTACTPERSON=?, "
                    + "CONTACTPERSONPOSITION=?, BRCNUMBER=?, REGISTEREDDATE=?, REGISTEREDPLACE=?, TYPEOFCOMPANY=?, NUMBEROFEMPLOYEES=?, CAPITALINVESTED=?, ANNUALTURNOVER=?, "
                    + "ANNUALTURNOVERYEAR=?, AUDITORNAME=?, AUDITORADDRESS1=?, AUDITORADDRESS2=?, AUDITORADDRESS3=?, AUDITORAREA=?, AUDITORDISTRICT=?, AUDITORPROVINCE=?, "
                    + "AUDITOROFFICEPHONE1=?, AUDITOROFFICEPHONE2=?, AUDITORCONTACTPERSON=?, AUDITORCONTACTPERSONPOSITION=?, AUDITORFAX=?, AUDITOROFFICEMAIL=?, "
                    + "OFFICEPHONE2=?, COMENCEMENTDATE=?, NATUREOFBUSINESS=? "
                    + "WHERE APPLICATIONID=? ");

            
            updateStat.setString(1, companyBean.getCompanyName());
            updateStat.setString(2, companyBean.getAddress1());
            updateStat.setString(3, companyBean.getAddress2());
            updateStat.setString(4, companyBean.getAddress3());
            updateStat.setString(5, companyBean.getArea());
            updateStat.setString(6, companyBean.getDistrict());
            updateStat.setString(7, companyBean.getProvince());
            updateStat.setString(8, companyBean.getOfficePhone1());
            updateStat.setString(9, companyBean.getOfficeFax());
            updateStat.setString(10, companyBean.getOfficeEmail());
            updateStat.setString(11, companyBean.getContactPerson());
            updateStat.setString(12, companyBean.getContactPerPosition());
            updateStat.setString(13, companyBean.getBrcNumber());
            updateStat.setTimestamp(14, new Timestamp(regDate.getTime()));
            updateStat.setString(15, companyBean.getRegisterPlace());
            if(!companyBean.getTypeOfCompany().equals("EMPOTH")){
                updateStat.setString(16, companyBean.getTypeOfCompany());
            }else{
                updateStat.setString(16, companyBean.getOtherTypeOfCompany());
            } 
            
            updateStat.setString(17, companyBean.getNumberOfEmployees());
            updateStat.setString(18, companyBean.getCapitalInvested());
            updateStat.setString(19, companyBean.getAnnualTurnover());
            updateStat.setString(20, companyBean.getAnnualTurnoverYear());
            updateStat.setString(21, companyBean.getAuditName());
            updateStat.setString(22, companyBean.getAuditAddress1());
            updateStat.setString(23, companyBean.getAuditAddress2());
            updateStat.setString(24, companyBean.getAuditAddress3());
            updateStat.setString(25, companyBean.getAuditArea());
            updateStat.setString(26, companyBean.getAuditDistrict());
            updateStat.setString(27, companyBean.getAuditProvince());
            updateStat.setString(28, companyBean.getAuditOficePhone1());
            updateStat.setString(29, companyBean.getAuditOficePhone2());
            updateStat.setString(30, companyBean.getAuditContactPerson());
            updateStat.setString(31, companyBean.getAuditContactPerPosition());
            updateStat.setString(32, companyBean.getAuditFax());
            updateStat.setString(33, companyBean.getAuditEmail());
            
            updateStat.setString(34, companyBean.getOfficePhone2());
            updateStat.setTimestamp(35, new Timestamp(comenceDate.getTime()));
            if(!companyBean.getBusinessNature().equals("OTHER")){
                updateStat.setString(36, companyBean.getBusinessNature());
            }else{
                updateStat.setString(36, companyBean.getOtherBusinessNature());
            }

            updateStat.setString(37, companyBean.getAppId());

            resultId = updateStat.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                updateStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
        
        public List<EstCardManagerDetailsBean> getManagerDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement ps = null;
        managerDetails = new ArrayList<EstCardManagerDetailsBean>();
        try {
            ps = con.prepareStatement("SELECT TITLE, INITIALS, FIRSTNAME, MIDDLENAME, LASTNAME, YEARSOFSERVICE, OFFICEPHONE, MOBILEPHONE, FAXNUMBER, EMAILADDRESS "
                    + " FROM CARDESTMANAGERSDETAILS "
                    + " WHERE APPLICATIONID = ?");

            ps.setString(1, applicationId);


            rs = ps.executeQuery();

            while (rs.next()) {
                EstCardManagerDetailsBean bean = new EstCardManagerDetailsBean();

                bean.setTitle(rs.getString("TITLE"));
                bean.setInitials(rs.getString("INITIALS"));
                bean.setFname(rs.getString("FIRSTNAME"));
                bean.setMidname(rs.getString("MIDDLENAME"));
                bean.setLname(rs.getString("LASTNAME"));
                bean.setYearsOfService(rs.getString("YEARSOFSERVICE"));
                bean.setOficePhone(rs.getString("OFFICEPHONE"));
                bean.setMobilePhone(rs.getString("MOBILEPHONE"));
                bean.setFax(rs.getString("FAXNUMBER"));
                bean.setEmail(rs.getString("EMAILADDRESS"));

                managerDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return managerDetails;
    }
      
        public int insertManagerDetailsRecords(Connection con, List<EstCardManagerDetailsBean> managerList) throws Exception {
        int resultId = 1;

        PreparedStatement sqlStat = null;
        try {


            for (int i = 0; i < managerList.size(); i++) {

                sqlStat = con.prepareStatement("INSERT INTO CARDESTMANAGERSDETAILS "
                        + "(TITLE, INITIALS, FIRSTNAME, MIDDLENAME, LASTNAME, YEARSOFSERVICE, OFFICEPHONE, MOBILEPHONE, FAXNUMBER, EMAILADDRESS, APPLICATIONID ) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?) ");

                sqlStat.setString(1, managerList.get(i).getTitle());
                sqlStat.setString(2, managerList.get(i).getInitials());
                sqlStat.setString(3, managerList.get(i).getFname());
                sqlStat.setString(4, managerList.get(i).getMidname());
                sqlStat.setString(5, managerList.get(i).getLname());
                sqlStat.setString(6, managerList.get(i).getYearsOfService());
                sqlStat.setString(7, managerList.get(i).getOficePhone());
                sqlStat.setString(8, managerList.get(i).getMobilePhone());
                sqlStat.setString(9, managerList.get(i).getFax());
                sqlStat.setString(10, managerList.get(i).getEmail());
                sqlStat.setString(11, managerList.get(i).getAppId());

                int j = 0;
                j = sqlStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }


            }



        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                sqlStat.close();

            } catch (Exception e) {
                throw e;
            }

        }

        return resultId;
    }
      
        public int updateManagerDetailsRecords(Connection con, List<EstCardManagerDetailsBean> managerList, String appId) throws Exception {
        int resultId = 1;

        PreparedStatement insertStat = null;
        try {

            insertStat = con.prepareStatement("DELETE FROM CARDESTMANAGERSDETAILS WHERE APPLICATIONID=?");
            insertStat.setString(1, appId);
            insertStat.executeUpdate();

            for (int i = 0; i < managerList.size(); i++) {

                insertStat = con.prepareStatement("INSERT INTO CARDESTMANAGERSDETAILS "
                        + "(TITLE, INITIALS, FIRSTNAME, MIDDLENAME, LASTNAME, YEARSOFSERVICE, OFFICEPHONE, MOBILEPHONE, FAXNUMBER, EMAILADDRESS, APPLICATIONID ) "
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?) ");

                insertStat.setString(1, managerList.get(i).getTitle());
                insertStat.setString(2, managerList.get(i).getInitials());
                insertStat.setString(3, managerList.get(i).getFname());
                insertStat.setString(4, managerList.get(i).getMidname());
                insertStat.setString(5, managerList.get(i).getLname());
                insertStat.setString(6, managerList.get(i).getYearsOfService());
                insertStat.setString(7, managerList.get(i).getOficePhone());
                insertStat.setString(8, managerList.get(i).getMobilePhone());
                insertStat.setString(9, managerList.get(i).getFax());
                insertStat.setString(10, managerList.get(i).getEmail());
                insertStat.setString(11, managerList.get(i).getAppId());

                int j = 0;
                j = insertStat.executeUpdate();
                if (j == 0) {
                    resultId = 0;
                    throw new Exception();
                }


            }



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
        
        
        
      
      //
}
