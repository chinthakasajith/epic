/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerEmploymentBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationCustomerPersonalBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationDetailsBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationHistoryBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationIncomeBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nalin
 */
public class ApplicationDetailsPersistance {

    ResultSet rs;
    private HashMap<String, String> bnkBranches = null;
    private HashMap<String, String> priorityLevelList = null;
    private HashMap<String, String> applicationStatusList = null;
    private HashMap<String, String> domainList = null;
    private List<ApplicationDetailsBean> summeryList = null;
    private List<ApplicationHistoryBean> historyList = null;
    private ApplicationDetailsBean applicationBean = null;
    private ApplicationCustomerPersonalBean personalBean = null;
    private ApplicationCustomerEmploymentBean employeeBean = null;
    private List<ApplicationIncomeBean> incomeDetailList = null;

    public HashMap<String, String> getBranchNames(Connection con) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION "
                    + "FROM BANKBRANCH "
                    + "WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)");
            rs = ps.executeQuery();
            bnkBranches = new HashMap<String, String>();

            while (rs.next()) {
                bnkBranches.put(rs.getString("BRANCHCODE"), rs.getString("DESCRPTION"));
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {

            rs.close();
            ps.close();
        }

        return bnkBranches;
    }

    public HashMap<String, String> getAllPriorityLevels(Connection con) throws Exception {

        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.PRIORITYLEVELCODE,TC.DESCRIPTION "
                    + "FROM PRIORITYLEVEL TC");

            rs = getAllUserRole.executeQuery();
            priorityLevelList = new HashMap<String, String>();

            while (rs.next()) {

                priorityLevelList.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();

        }

        return priorityLevelList;
    }

    public HashMap<String, String> getStatusList(Connection con, String category) throws Exception {

        PreparedStatement getAllStatus = null;
        try {
            getAllStatus = con.prepareStatement("SELECT S.STATUSCODE,S.DESCRIPTION "
                    + "FROM STATUS S WHERE S.STATUSCATEGORY = ?");

            getAllStatus.setString(1, category);
            rs = getAllStatus.executeQuery();
            applicationStatusList = new HashMap<String, String>();

            while (rs.next()) {

                applicationStatusList.put(rs.getString("STATUSCODE"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllStatus.close();

        }

        return applicationStatusList;
    }

    public HashMap<String, String> getAllDomainList(Connection con) throws Exception {

        PreparedStatement getAllDomain = null;
        try {
            getAllDomain = con.prepareStatement("SELECT D.DOMAINID,D.DESCRIPTION "
                    + "FROM CARDDOMAIN D WHERE D.PREPERSOSTATUS = ?");

            getAllDomain.setString(1, StatusVarList.YESSTATUS);
            rs = getAllDomain.executeQuery();
            domainList = new HashMap<String, String>();

            while (rs.next()) {

                domainList.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllDomain.close();

        }

        return domainList;
    }

    public List<ApplicationDetailsBean> searchApplicationSummeryReport(Connection CMSCon, ApplicationDetailsBean summeryBean) throws Exception {

        PreparedStatement getSummeryList = null;
        summeryList = new ArrayList<ApplicationDetailsBean>();
        String strSqlBasic = null;
        try {

            strSqlBasic = "SELECT CA.APPLICATIONID,CA.IDENTIFICATIONTYPE,CA.IDENTIFICATIONNO,"
                    + " CA.PRIORITYLEVELCODE,CA.REFERANCIALEMPNO,CA.BRANCHCODE,CA.ASSIGNUSER,"
                    + " CA.ASSIGNSTATUS,CA.CREATETIME,CA.CARDAPPLIACTIONDOMAIN ,AD.DOCUMENTNAME,"
                    + " PL.DESCRIPTION AS PLDESCRIPTION, CS.APPLICATIONSTATUS,"
                    + " ST.DESCRIPTION AS STDESCRIPTION,CD.DESCRIPTION AS CDDESCRIPTION,"
                    + " BB.DESCRPTION AS BBDESCRPTION"
                    + " FROM CARDAPPLICATION CA,APPLICATIONDOCUMENT AD,BANKBRANCH BB,"
                    + " PRIORITYLEVEL PL,CARDAPPLICATIONSTATUS CS,CARDDOMAIN CD,STATUS ST "
                    + " WHERE CA.IDENTIFICATIONTYPE = AD.DOCUMENTTYPECODE AND"
                    + " CA.CARDCATEGORY=AD.CARDCATEGORYCODE AND"
                    + " CA.PRIORITYLEVELCODE =PL.PRIORITYLEVELCODE AND"
                    + " CA.APPLICATIONID=CS.APPLICATIONID AND"
                    + " CA.CARDAPPLIACTIONDOMAIN = CD.DOMAINID AND"
                    + " CS.APPLICATIONSTATUS = ST.STATUSCODE AND"
                    + " CA.BRANCHCODE = BB.BRANCHCODE AND "
                    + " BB.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) ";

            String condition = "";

            if (!summeryBean.getNic().contentEquals("") || summeryBean.getNic().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + summeryBean.getNic().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE= 'NIC' ";
            }

            if (!summeryBean.getPassport().contentEquals("") || summeryBean.getPassport().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + summeryBean.getPassport().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE ='PAS' ";
            }

//            if (!summeryBean.getDrivingLicence().contentEquals("") || summeryBean.getDrivingLicence().length() > 0) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += " UPPER(CA.IDENTIFICATIONNO) LIKE '%" + summeryBean.getDrivingLicence().toUpperCase() + "%' AND CA.IDENTIFICATIONTYPE = 'DRL' ";
//            }

            if (!summeryBean.getApplicationID().contentEquals("") || summeryBean.getApplicationID().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.APPLICATIONID LIKE '%" + summeryBean.getApplicationID() + "%'";
            }

            if (!summeryBean.getBranch().contentEquals("") || summeryBean.getBranch().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.BRANCHCODE = " + summeryBean.getBranch() + " ";
            }

            if (!summeryBean.getPriorityLevel().contentEquals("") || summeryBean.getPriorityLevel().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.PRIORITYLEVELCODE = " + summeryBean.getPriorityLevel() + " ";
            }

            if (!summeryBean.getApplicationStatus().contentEquals("") || summeryBean.getApplicationStatus().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CS.APPLICATIONSTATUS = '" + summeryBean.getApplicationStatus() + "' ";
            }

            if (!summeryBean.getDomain().contentEquals("") || summeryBean.getDomain().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CARDAPPLIACTIONDOMAIN = '" + summeryBean.getDomain() + "' ";
            }

            if (!summeryBean.getFromDate().contentEquals("") || summeryBean.getFromDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CREATETIME >= TO_DATE('" + this.stringTodate(summeryBean.getFromDate()) + "','yyyy-MM-dd') ";
            }

            if (!summeryBean.getToDate().contentEquals("") || summeryBean.getToDate().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += " CA.CREATETIME - 1 <=  TO_DATE('" + this.stringTodate(summeryBean.getToDate()) + "','yyyy-MM-dd') ";
            }

            if (!condition.equals("")) {
                strSqlBasic += " AND " + condition + " ORDER BY CA.APPLICATIONID DESC ";
            } else {

                strSqlBasic += " ORDER BY CA.APPLICATIONID DESC ";
            }

            getSummeryList = CMSCon.prepareStatement(strSqlBasic);
            rs = getSummeryList.executeQuery();

            while (rs.next()) {

                ApplicationDetailsBean bean = new ApplicationDetailsBean();

                bean.setApplicationID(rs.getString("APPLICATIONID"));
                bean.setIdentificationNo(rs.getString("IDENTIFICATIONNO"));
                bean.setIdentificationTypeDes(rs.getString("DOCUMENTNAME"));
                bean.setPriorityLevelDes(rs.getString("PLDESCRIPTION"));
                bean.setRefEmpNo(rs.getString("REFERANCIALEMPNO"));
                bean.setAssignUser(rs.getString("ASSIGNUSER"));
                bean.setApplicationDate(this.convertStringDate(rs.getDate("CREATETIME")));
                bean.setDomainDes(rs.getString("CDDESCRIPTION"));
                bean.setBranchDes(rs.getString("BBDESCRPTION"));
                bean.setApplicationStatusDes(rs.getString("STDESCRIPTION"));

                summeryList.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getSummeryList.close();

        }
        return summeryList;
    }

    public List<ApplicationHistoryBean> getApplicationHistory(Connection CMSCon, String applicationID) throws Exception {
        PreparedStatement getHistoryList = null;
        try {
            getHistoryList = CMSCon.prepareStatement("SELECT CH.APPLICATIONLEVEL,CH.STATUS,CH.REMARKS,"
                    + " CH.LASTUPDATEDUSER,CH.LASTUPDATEDTIME,AL.DESCRIPTION AS ALDESCRIPTION,"
                    + " ST.DESCRIPTION AS STDESCRIPTION"
                    + " FROM CARDAPPLICATIONHISTORY CH,APPLICATIONLEVEL AL,STATUS ST"
                    + " WHERE CH.APPLICATIONLEVEL=AL.LEVELID AND CH.STATUS= ST.STATUSCODE AND APPLICATIONID = ?"
                    + " ORDER BY CH.LASTUPDATEDTIME DESC ");

            getHistoryList.setString(1, applicationID);
            rs = getHistoryList.executeQuery();
            historyList = new ArrayList<ApplicationHistoryBean>();

            while (rs.next()) {

                ApplicationHistoryBean bean = new ApplicationHistoryBean();

                bean.setApplicationLevelDes(rs.getString("ALDESCRIPTION"));
                bean.setApplicationStatusDes(rs.getString("STDESCRIPTION"));
                bean.setRemarks(rs.getString("REMARKS"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                bean.setLsatUpdaetdTime(this.convertStringDate(rs.getDate("LASTUPDATEDTIME")));

                historyList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getHistoryList.close();

        }
        return historyList;
    }

    public ApplicationDetailsBean getBranchDescription(Connection CMSCon, ApplicationDetailsBean detailsBean) throws Exception {
        PreparedStatement getBranchDes = null;
        try {
            getBranchDes = CMSCon.prepareStatement("SELECT B.DESCRIPTION "
                    + " FROM BANKBRANCH B "
                    + " WHERE B.BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER) "
                    + " AND BRANCHCODE = ?");

            getBranchDes.setString(1, detailsBean.getBranch());
            rs = getBranchDes.executeQuery();

            while (rs.next()) {

                detailsBean.setBranchDes(rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getBranchDes.close();

        }

        return detailsBean;
    }

    public ApplicationDetailsBean getPriorityLevelDescription(Connection CMSCon, ApplicationDetailsBean detailsBean) throws Exception {
        PreparedStatement getPLDes = null;
        try {
            getPLDes = CMSCon.prepareStatement("SELECT P.DESCRIPTION "
                    + " FROM PRIORITYLEVEL P "
                    + " WHERE P.PRIORITYLEVELCODE = ?");

            getPLDes.setString(1, detailsBean.getPriorityLevel());
            rs = getPLDes.executeQuery();

            while (rs.next()) {

                detailsBean.setPriorityLevelDes(rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getPLDes.close();
        }
        return detailsBean;
    }

    public ApplicationDetailsBean getStatusDescription(Connection CMSCon, ApplicationDetailsBean detailsBean) throws Exception {
        PreparedStatement getStatusDes = null;
        try {
            getStatusDes = CMSCon.prepareStatement("SELECT S.DESCRIPTION "
                    + " FROM STATUS S "
                    + " WHERE S.STATUSCODE = ?");

            getStatusDes.setString(1, detailsBean.getApplicationStatus());
            rs = getStatusDes.executeQuery();

            while (rs.next()) {

                detailsBean.setApplicationStatusDes(rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getStatusDes.close();
        }
        return detailsBean;
    }

    public ApplicationDetailsBean getDomainDescription(Connection CMSCon, ApplicationDetailsBean detailsBean) throws Exception {
        PreparedStatement getDomainDes = null;
        try {
            getDomainDes = CMSCon.prepareStatement("SELECT D.DESCRIPTION "
                    + " FROM CARDDOMAIN D "
                    + " WHERE D.DOMAINID = ?");

            getDomainDes.setString(1, detailsBean.getDomain());
            rs = getDomainDes.executeQuery();

            while (rs.next()) {

                detailsBean.setDomainDes(rs.getString("DESCRIPTION"));

            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getDomainDes.close();
        }
        return detailsBean;
    }

    //////////////////////////////Application Details Catching Area///////////////////////////////////////////////////////  
    public ApplicationDetailsBean getApplicationCardDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT APPLICATIONID,IDENTIFICATIONTYPE,IDENTIFICATIONNO,PRIORITYLEVELCODE,"
                    + " REFERANCIALEMPNO,BRANCHCODE,ASSIGNUSER,ASSIGNSTATUS,LASTUPDATEDUSER,LASTUPDATEDTIME,CREATETIME,CREDITSCORE,"
                    + " STAFFSTATUS,CARDCATEGORY,cc.DESCRIPTION as ccdes "
                    + " FROM CARDAPPLICATION, CARDCATEGORY cc WHERE cc.CATEGORYCODE=CARDCATEGORY and APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                applicationBean = new ApplicationDetailsBean();

                applicationBean.setApplicationID(rs.getString("APPLICATIONID"));
                applicationBean.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                applicationBean.setIdentificationNo(rs.getString("IDENTIFICATIONNO"));
                applicationBean.setPriorityLevel(rs.getString("PRIORITYLEVELCODE"));
                applicationBean.setRefEmpNo(rs.getString("REFERANCIALEMPNO"));
                applicationBean.setBranch(rs.getString("BRANCHCODE"));
                applicationBean.setAssignUser(rs.getString("ASSIGNUSER"));
                applicationBean.setAssignStatus(rs.getString("ASSIGNSTATUS"));
                applicationBean.setLastUpdatedUser(rs.getString("LASTUPDATEDUSER"));
                applicationBean.setCreditScore(rs.getString("CREDITSCORE"));
                applicationBean.setStaffStatus(rs.getString("STAFFSTATUS"));
                applicationBean.setAppTypeDes(rs.getString("ccdes"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return applicationBean;
    }

    public ApplicationCustomerPersonalBean getPersonalDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        try {
            getAllByCatStat = con.prepareStatement("SELECT CPD.RELEMAIL,CPD.APPLICATIONID,CPD.TITLE,CPD.INITIALS,CPD.FIRSTNAME,"
                    + "CPD.LASTNAME,CPD.MIDDLENAME,CPD.NAMEINFULL,CPD.DATEOFBIRTH,CPD.PLACEOFBIRTH,CPD.GENDER,CPD.MARITALSTATUS,CPD.NATIONALITY,"
                    + "CPD.NIC,CPD.PASSPORTNUMBER,CPD.PASSPORTEXPIREDATE,CPD.ADDRESS1,CPD.ADDRESS2,CPD.ADDRESS3,A.DESCRIPTION AS CITY,CPD.POSTALCODE,"
                    + "CPD.HOMETELEPHONENO,CPD.MOBILENO,CPD.SMSALERTMOBILENO,CPD.EMERGENCYCONTACTNO,CPD.DURATIONATTHEABOVEADDRESS,"
                    + "CPD.EMAIL,CPD.RECIDENCETYPE,CPD.MONTHLYRENTAL,CPD.MORGAGERENTAL,CPD.ACADEMICQUALIFICATION,"
                    + "CPD.PROFESSIONALQUALIFICATION,CPD.VEHICALTYPE,CPD.VEHICALNO,CPD.OWNERSHIP,CPD.RELIGION,CPD.BLOODGROUP,"
                    + "CPD.MOTHERSMAIDENNAME,CPD.NOOFDEPENDANCE,CPD.USECARDONLINE,CPD.LASTUPDATEDUSER,CPD.LASTUPDATEDTIME,"
                    + "CPD.CREATETIME,CPD.NAMEONCARD,CPD.OFFICEPHONENO,CPD.RELATIVENAME,CPD.RELATIONSHIP,CPD.RELADDRESS1,"
                    + "CPD.RELADDRESS2,CPD.RELADDRESS3,CPD.RELCITY,CPD.RELRESIDANCEPHONE,CPD.RELMOBILENO,CPD.RELEMPLOYER,"
                    + "CPD.RELOFFICEPHONE,CPD.SPOUSENAME,CPD.SPOUSEDATEOFBIRTH,CPD.SPOUSENIC,CPD.SPOUSEPASSPORTNO,"
                    + "CPD.SPOUSENAMEOFEMPLOYEE,CPD.SPOUSECOMPANYADDRESS,CPD.SPOUSEDESIGNATION,CPD.SPOUSEPHONE,"
                    + "CPD.SPOUSEEMAIL,CPD.SPOUSEMONTHLYINCOME,CPD.REQUESTCARDTYPE,CPD.REQUESTCARDPRODUCT,"
                    + "CP.DESCRIPTION AS PRODUCTDES,CPD.REQUESTCREDITLIMIT,CPD.CURRENCYTYPE,C.DESCRIPTION AS CURRENCYDES"
                    + " FROM CARDAPPLICATIONPERSONALDETAILS CPD,AREA A,CURRENCY C,CARDPRODUCT CP "
                    + "WHERE CPD.CITY = A.AREACODE AND CPD.CURRENCYTYPE = C.CURRENCYNUMCODE AND CPD.REQUESTCARDPRODUCT = CP.PRODUCTCODE "
                    + "AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();
            personalBean = new ApplicationCustomerPersonalBean();
            while (rs.next()) {

                personalBean.setFullName(rs.getString("NAMEINFULL"));
                personalBean.setRelEmail(rs.getString("RELEMAIL"));
                personalBean.setApplicationId(rs.getString("APPLICATIONID"));
                personalBean.setTitle(rs.getString("TITLE"));
                personalBean.setInitials(rs.getString("INITIALS"));
                personalBean.setFirstName(rs.getString("FIRSTNAME"));
                personalBean.setLastName(rs.getString("LASTNAME"));
                personalBean.setMiddleName(rs.getString("MIDDLENAME"));
                personalBean.setBirthday(rs.getString("DATEOFBIRTH"));
                personalBean.setPlaceOfbirth(rs.getString("PLACEOFBIRTH"));
                personalBean.setGender(rs.getString("GENDER"));
                personalBean.setMaritalStatus(rs.getString("MARITALSTATUS"));
                personalBean.setNationality(rs.getString("NATIONALITY"));
                personalBean.setNic(rs.getString("NIC"));
                personalBean.setPassportNumber(rs.getString("PASSPORTNUMBER"));
                personalBean.setPassportExpdate(rs.getString("PASSPORTEXPIREDATE"));
                personalBean.setAddress1(rs.getString("ADDRESS1"));
                personalBean.setAddress2(rs.getString("ADDRESS2"));
                personalBean.setAddress3(rs.getString("ADDRESS3"));
                personalBean.setCity(rs.getString("CITY"));
                personalBean.setPostalcode(rs.getString("POSTALCODE"));
                personalBean.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                personalBean.setMobileNumber(rs.getString("MOBILENO"));
                personalBean.setSmsalertMobileNumber(rs.getString("SMSALERTMOBILENO"));
                personalBean.setEmergencyContactNumber(rs.getString("EMERGENCYCONTACTNO"));
                personalBean.setDurationofTheAddress(rs.getString("DURATIONATTHEABOVEADDRESS"));
                personalBean.setEmail(rs.getString("EMAIL"));
                personalBean.setResidenceType(rs.getString("RECIDENCETYPE"));
                personalBean.setMonthlyRental(rs.getString("MONTHLYRENTAL"));
                personalBean.setMorgageRental(rs.getString("MORGAGERENTAL"));
                personalBean.setAcedemicQualifications(rs.getString("ACADEMICQUALIFICATION"));
                personalBean.setProfessionalQualifications(rs.getString("PROFESSIONALQUALIFICATION"));
                personalBean.setVehicalType(rs.getString("VEHICALTYPE"));
                personalBean.setVehicalNo(rs.getString("VEHICALNO"));
                personalBean.setOwnership(rs.getString("OWNERSHIP"));
                personalBean.setReligion(rs.getString("RELIGION"));
                personalBean.setBloodgroup(rs.getString("BLOODGROUP"));
                personalBean.setMothersMaidenName(rs.getString("MOTHERSMAIDENNAME"));
                personalBean.setNumberOfDependance(rs.getString("NOOFDEPENDANCE"));
                personalBean.setUseCardOnline(rs.getString("USECARDONLINE"));
                personalBean.setLastUpdateUser(rs.getString("LASTUPDATEDUSER"));
                personalBean.setNameOncard(rs.getString("NAMEONCARD"));
                personalBean.setOfficeTelNumber(rs.getString("OFFICEPHONENO"));
                personalBean.setRelName(rs.getString("RELATIVENAME"));
                personalBean.setRelationship(rs.getString("RELATIONSHIP"));
                personalBean.setRelAddress1(rs.getString("RELADDRESS1"));
                personalBean.setRelAddress2(rs.getString("RELADDRESS2"));
                personalBean.setRelAddress3(rs.getString("RELADDRESS3"));
                personalBean.setRelCity(rs.getString("RELCITY"));
                personalBean.setRelResidencePhone(rs.getString("RELRESIDANCEPHONE"));
                personalBean.setRelMobile(rs.getString("RELMOBILENO"));
                personalBean.setRelCompany(rs.getString("RELEMPLOYER"));
                personalBean.setRelOfficeNumber(rs.getString("RELOFFICEPHONE"));
                personalBean.setSpouseName(rs.getString("SPOUSENAME"));
                personalBean.setSpouseDateofBirth(rs.getString("SPOUSEDATEOFBIRTH"));
                personalBean.setSpouseNic(rs.getString("SPOUSENIC"));
                personalBean.setSpousePassportNumber(rs.getString("SPOUSEPASSPORTNO"));
                personalBean.setSpouseNameofEmployee(rs.getString("SPOUSENAMEOFEMPLOYEE"));
                personalBean.setSpousecompanyAddress(rs.getString("SPOUSECOMPANYADDRESS"));
                personalBean.setSpouseDesignation(rs.getString("SPOUSEDESIGNATION"));
                personalBean.setSpousePhone(rs.getString("SPOUSEPHONE"));
                personalBean.setSpouseMail(rs.getString("SPOUSEEMAIL"));
                personalBean.setSpouseMonthlyIncome(rs.getString("SPOUSEMONTHLYINCOME"));
                personalBean.setCardType(rs.getString("REQUESTCARDTYPE"));
                personalBean.setCardProduct(rs.getString("REQUESTCARDPRODUCT"));
                personalBean.setCardProductDes(rs.getString("PRODUCTDES"));
                personalBean.setCreditLimit(rs.getString("REQUESTCREDITLIMIT"));
                personalBean.setCardCurrency(rs.getString("CURRENCYTYPE"));
                personalBean.setCurrencyDes(rs.getString("CURRENCYDES"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return personalBean;
    }

    public ApplicationCustomerEmploymentBean getApplicationEmployementDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;

        try {
            getAllByCatStat = con.prepareStatement("SELECT APPLICATIONID,EMPLOYMENTSTATUS,OFFICETELEPHONE,O.DESCRIPTION AS OCCUPATION,"
                    + "DESIGNATION,EN.DESCRIPTION AS NATUREOFBUSINESS,NOOFEMPLOYEES,COMPANYNAME,ANNUALTURNOVER,NETPROFIT,"
                    + "LASTUPDATEDTIME,CREATEDDATE,LASTUPDATEDUSER,EMPLOYMENTTYPE,OFFICEADDRESS1,OFFICEADDRESS2,"
                    + "OFFICEADDRESS3,OTHEROCCUPATION,SELFEMPLOYEECOMPANYNAME,SELFEMPLOYEENOOFEMPLOYEES,OTHEREMPLOYMENTTYPE,OTHERNATUREOFBUSINESS,SERVICEMONTH,SERVICEYEARS,STAFFSTATUS FROM CARDAPPLICATIONEMPDETAILS CE,OCCUPATIONTYPE O,EMPLOYMENTNATURE EN WHERE CE.OCCUPATION = O.OCCUPATIONTYPECODE AND CE.NATUREOFBUSINESS = EN.BUSINESSNATURECODE AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                employeeBean = new ApplicationCustomerEmploymentBean();

                employeeBean.setApplicationId(rs.getString("APPLICATIONID"));
                employeeBean.setEmploymentStatus(rs.getString("EMPLOYMENTSTATUS"));
                employeeBean.setOfficePhone(rs.getString("OFFICETELEPHONE"));
                employeeBean.setOccupation(rs.getString("OCCUPATION"));
                employeeBean.setDesignation(rs.getString("DESIGNATION"));
                employeeBean.setBusinessNature(rs.getString("NATUREOFBUSINESS"));
                employeeBean.setNumberOfEmployees(rs.getString("NOOFEMPLOYEES"));
                employeeBean.setCompanyName(rs.getString("COMPANYNAME"));
                employeeBean.setAnnualTurnOver(rs.getString("ANNUALTURNOVER"));
                employeeBean.setNetProfit(rs.getString("NETPROFIT"));
                employeeBean.setEmploymentType(rs.getString("EMPLOYMENTTYPE"));
                employeeBean.setAdress1(rs.getString("OFFICEADDRESS1"));
                employeeBean.setAdress2(rs.getString("OFFICEADDRESS2"));
                employeeBean.setAdress3(rs.getString("OFFICEADDRESS3"));
                employeeBean.setOtherOccupation(rs.getString("OTHEROCCUPATION"));
                employeeBean.setSelfEmpCompanyname(rs.getString("SELFEMPLOYEECOMPANYNAME"));
                employeeBean.setSelfEmpNoOfEmployee(rs.getString("SELFEMPLOYEENOOFEMPLOYEES"));
                employeeBean.setOtherEmploymentType(rs.getString("OTHEREMPLOYMENTTYPE"));
                employeeBean.setOtherBusinessNature(rs.getString("OTHERNATUREOFBUSINESS"));
                employeeBean.setBusnessFrom(rs.getString("SERVICEMONTH"));
                employeeBean.setServiceMonth(rs.getString("SERVICEMONTH"));
                employeeBean.setServiceYear(rs.getString("SERVICEYEARS"));
                employeeBean.setStaffStatus(rs.getString("STAFFSTATUS"));

            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return employeeBean;
    }

    public List<ApplicationIncomeBean> getIncomeDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        incomeDetailList = new ArrayList<ApplicationIncomeBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT IC.DESCRIPTION,CI.INCOMECATEGORYCODE,CI.AMOUNT FROM CARDAPPLICATIONINCOME CI,INCOMECATEGORY IC WHERE CI.INCOMECATEGORYCODE=IC.INCOMECATEGORYCODE AND APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                ApplicationIncomeBean bean = new ApplicationIncomeBean();

                bean.setIncomeCatogary(rs.getString("DESCRIPTION"));
                bean.setIncomeCatogaryCode(rs.getString("INCOMECATEGORYCODE"));
                bean.setAmount(rs.getString("AMOUNT"));

                incomeDetailList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return incomeDetailList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    private String convertStringDate(java.sql.Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return (dateFormat.format(date));
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }
}
