/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationproccessing.documentverify.persistance;

import com.epic.cms.camm.applicationproccessing.documentverify.bean.CheckedSupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.DebitVerifyBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.SupplementaryDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyAccountDetailsBean;
import com.epic.cms.camm.applicationproccessing.documentverify.bean.VerifyApplicantDetailBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class SupplementaryVerifyPersistance {

    private SupplementaryDetailsBean supplementaryBean = null;
    private VerifyApplicantDetailBean mainBean = null;
    private List<VerifyAccountDetailsBean> accoutBeanLst = null;
    private DebitVerifyBean debitVerifyBean = null;
    private CheckedSupplementaryDetailsBean checkedApplicationBean = null;

    public SupplementaryDetailsBean getAllSupplementaryDetails(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query


        try {

            strSqlBasic = "SELECT cd.APPLICATIONID,cd.FIRSTNAME,cd.LASTNAME,cd.MIDDLENAME ,cd.DATEOFBIRTH ,cd.ADDRESS1 ,"
                    + "cd.HOMETELEPHONE ,cd.MOBILE ,ca.IDENTIFICATIONTYPE ,ca.IDENTIFICATIONNO ,cd.RELATIONSHIP "
                    + "FROM SUPPLEMENTARYAPPLICATION cd, CARDAPPLICATION ca WHERE cd.APPLICATIONID=? AND cd.APPLICATIONID = ca.APPLICATIONID";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            supplementaryBean = new SupplementaryDetailsBean();

            while (rs.next()) {


                SupplementaryDetailsBean resultCustomer = new SupplementaryDetailsBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                resultCustomer.setLastName(rs.getString("LASTNAME"));
                resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                resultCustomer.setFullName(rs.getString("FIRSTNAME").concat(" ").concat(rs.getString("MIDDLENAME")).concat(" ").concat(rs.getString("LASTNAME")));
                resultCustomer.setBirthday(rs.getString("DATEOFBIRTH"));
                resultCustomer.setApplicantAddress(rs.getString("ADDRESS1"));
                resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONE"));
                resultCustomer.setMobileNumber(rs.getString("MOBILE"));
                resultCustomer.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                resultCustomer.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                resultCustomer.setRelationship(rs.getString("RELATIONSHIP"));



                supplementaryBean = resultCustomer;

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

        return supplementaryBean;
    }

    public VerifyApplicantDetailBean getAllMainDetails(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query


        try {

            strSqlBasic = " SELECT cd.APPLICATIONID,cd.NAMEINFULL ,"
                    + "cd.DATEOFBIRTH ,cd.MARITALSTATUS ,cd.ADDRESS1 ,cd.HOMETELEPHONENO ,"
                    + "cd.MOBILENO ,ce.OFFICETELEPHONE,ce.DESIGNATION ,ce.COMPANYNAME,"
                    + "ce.SERVICEYEARS ,ce.OFFICEADDRESS1 ,ca.IDENTIFICATIONTYPE ,ca.IDENTIFICATIONNO ,"
                    + "cd.RELATIVENAME,cd.RELATIONSHIP,cd.RELADDRESS1 ,cd.RELRESIDANCEPHONE ,cd.RELMOBILENO"
                    + " FROM CARDAPPLICATIONPERSONALDETAILS cd,CARDAPPLICATIONEMPDETAILS ce ,CARDAPPLICATION ca ,"
                    + "SUPPLEMENTARYAPPLICATION sp WHERE sp.APPLICATIONID =? AND sp.primaryid = ca.identificationno"
                    + " AND cd.APPLICATIONID = ce.APPLICATIONID AND cd.APPLICATIONID     = ca.APPLICATIONID";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            mainBean = new VerifyApplicantDetailBean();

            while (rs.next()) {


                VerifyApplicantDetailBean resultCustomer = new VerifyApplicantDetailBean();

                resultCustomer.setApplicationId(rs.getString("APPLICATIONID"));
                //resultCustomer.setFirstName(rs.getString("FIRSTNAME"));
                //resultCustomer.setLastName(rs.getString("LASTNAME"));
                //resultCustomer.setMiddleName(rs.getString("MIDDLENAME"));
                resultCustomer.setFullName(rs.getString("NAMEINFULL"));
                resultCustomer.setBirthday(rs.getString("DATEOFBIRTH"));
                resultCustomer.setMaritalStatus(rs.getString("MARITALSTATUS"));
                resultCustomer.setApplicantAddress(rs.getString("ADDRESS1"));
                resultCustomer.setHomeTelNumber(rs.getString("HOMETELEPHONENO"));
                resultCustomer.setMobileNumber(rs.getString("MOBILENO"));
                resultCustomer.setOfficeTelNumber(rs.getString("OFFICETELEPHONE"));
                resultCustomer.setCompany(rs.getString("COMPANYNAME"));
                resultCustomer.setDesignation(rs.getString("DESIGNATION"));
                resultCustomer.setServiceLength(rs.getString("SERVICEYEARS"));
                resultCustomer.setIdentificationType(rs.getString("IDENTIFICATIONTYPE"));
                resultCustomer.setIdentificationNumber(rs.getString("IDENTIFICATIONNO"));
                resultCustomer.setRefereeFullName(rs.getString("RELATIVENAME"));
                resultCustomer.setRelationship(rs.getString("RELATIONSHIP"));
                resultCustomer.setRefereeAddress(rs.getString("RELADDRESS1"));
                resultCustomer.setRefereeHomeTel(rs.getString("RELRESIDANCEPHONE"));
                resultCustomer.setRefereeMobile(rs.getString("RELMOBILENO"));



                mainBean = resultCustomer;

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

        return mainBean;
    }

    public List<VerifyAccountDetailsBean> getAllAccountDetails(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query


        try {

            strSqlBasic = " SELECT dc.APPLICATIONID,dc.ACCOUNTNO1,dc.ACCOUNTNO2,dc.ACCOUNTNO3,dc.ACCOUNTNO4,dc.ACCTYPE1,dc.ACCTYPE2,"
                    + "dc.ACCTYPE3,dc.ACCTYPE4,dc.ACCCURRENCY1,dc.ACCCURRENCY2,dc.ACCCURRENCY3,dc.ACCCURRENCY4,cr1.DESCRIPTION as acccurrencydes1,cr2.DESCRIPTION as acccurrencydes2,cr3.DESCRIPTION as acccurrencydes3,cr4.DESCRIPTION as acccurrencydes4,at1.DESCRIPTION as acctypedes1,at2.DESCRIPTION as acctypedes2,at3.DESCRIPTION as acctypedes3,at4.DESCRIPTION as acctypedes4"
                    + " FROM DEBITCARDAPPLICATIONDETAILS dc,CARDAPPLICATION ca,ACCOUNTTYPE at1,ACCOUNTTYPE at2,ACCOUNTTYPE at3,ACCOUNTTYPE at4,CURRENCY cr1,CURRENCY cr2,CURRENCY cr3,CURRENCY cr4 WHERE ca.APPLICATIONID =? AND ca.APPLICATIONID = dc.APPLICATIONID"
                    + " AND at1.ACCOUNTTYPECODE=dc.ACCTYPE1 AND at2.ACCOUNTTYPECODE(+)=dc.ACCTYPE2 AND at3.ACCOUNTTYPECODE(+)=dc.ACCTYPE3 AND at4.ACCOUNTTYPECODE(+)=dc.ACCTYPE4"
                      + " AND cr1.CURRENCYNUMCODE=dc.ACCCURRENCY1 AND cr2.CURRENCYNUMCODE(+)=dc.ACCCURRENCY2 AND cr3.CURRENCYNUMCODE(+)=dc.ACCCURRENCY3 AND cr4.CURRENCYNUMCODE(+)=dc.ACCCURRENCY4";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            accoutBeanLst = new ArrayList<VerifyAccountDetailsBean>();

            while (rs.next()) {


                if (rs.getString("ACCOUNTNO1") != null) {

                    VerifyAccountDetailsBean resultCustomer = new VerifyAccountDetailsBean();

                    resultCustomer.setAccountNumber(rs.getString("ACCOUNTNO1"));
                    resultCustomer.setAccountType(rs.getString("acctypedes1"));
                    resultCustomer.setAccountCurrency(rs.getString("acccurrencydes1"));
                    resultCustomer.setAccountStatus("Yes");

                    accoutBeanLst.add(resultCustomer);
                }
                if (rs.getString("ACCOUNTNO2") != null) {

                    VerifyAccountDetailsBean resultCustomer = new VerifyAccountDetailsBean();

                    resultCustomer.setAccountNumber(rs.getString("ACCOUNTNO2"));
                    resultCustomer.setAccountType(rs.getString("acctypedes2"));
                    resultCustomer.setAccountCurrency(rs.getString("acccurrencydes2"));
                    resultCustomer.setAccountStatus("No");

                    accoutBeanLst.add(resultCustomer);
                }
                if (rs.getString("ACCOUNTNO3") != null) {

                    VerifyAccountDetailsBean resultCustomer = new VerifyAccountDetailsBean();

                    resultCustomer.setAccountNumber(rs.getString("ACCOUNTNO3"));
                    resultCustomer.setAccountType(rs.getString("acctypedes3"));
                    resultCustomer.setAccountCurrency(rs.getString("acccurrencydes3"));
                    resultCustomer.setAccountStatus("No");

                    accoutBeanLst.add(resultCustomer);
                }
                if (rs.getString("ACCOUNTNO4") != null) {

                    VerifyAccountDetailsBean resultCustomer = new VerifyAccountDetailsBean();

                    resultCustomer.setAccountNumber(rs.getString("ACCOUNTNO4"));
                    resultCustomer.setAccountType(rs.getString("acctypedes4"));
                    resultCustomer.setAccountCurrency(rs.getString("acccurrencydes4"));
                    resultCustomer.setAccountStatus("No");

                    accoutBeanLst.add(resultCustomer);
                }

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

        return accoutBeanLst;
    }

    public DebitVerifyBean getAllDebitVerifyDetails(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query


        try {

            strSqlBasic = " SELECT dc.DEBITACCOUNTNO,dc.DEBITCARDHOLDERID,dc.DEBITCARDHOLDERNAME,dc.DEBITDOCID,dc.DEBITJOINTNIC,"
                    + "dc.DEBITJOINTACCOUNTNO,dc.DEBITJOINTCARDHOLDERID,dc.DEBITJOINTCARDHOLDERNAME"
                    + " FROM CARDAPPLICATIONVERIFY dc WHERE dc.APPLICATIONID=?";



            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            debitVerifyBean = new DebitVerifyBean();

            while (rs.next()) {



                DebitVerifyBean resultCustomer = new DebitVerifyBean();

                resultCustomer.setDocId(rs.getString("DEBITDOCID"));
                resultCustomer.setAccontNo(rs.getString("DEBITACCOUNTNO"));
                resultCustomer.setId(rs.getString("DEBITCARDHOLDERID"));
                resultCustomer.setName(rs.getString("DEBITCARDHOLDERNAME"));
                resultCustomer.setsNic(rs.getString("DEBITJOINTNIC"));
                resultCustomer.setsId(rs.getString("DEBITJOINTCARDHOLDERID"));
                resultCustomer.setsAccNo(rs.getString("DEBITJOINTACCOUNTNO"));
                resultCustomer.setsName(rs.getString("DEBITJOINTCARDHOLDERNAME"));

                debitVerifyBean = resultCustomer;



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

        return debitVerifyBean;
    }

    public boolean insertVerifyDetailsOfApplication(Connection con, CheckedSupplementaryDetailsBean checkedApplicantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("INSERT INTO CARDAPPLICATIONVERIFY(APPLICATIONID,MAINAPPLICANT,SUPPLIMENTARYAPPLICANT,"
                    + "DOCMARRIAGECERTIFICATE,DOCBIRTHCERTIFICATE,LASTUPDATEDUSER,LASTUPDATEDTIME,DOCIDENTIFICATION) VALUES(?,?,?,?,?,?,?,?) ");


            insertStat.setString(1, checkedApplicantBean.getApplicationId());
            insertStat.setString(2, checkedApplicantBean.getMainVerification());
            insertStat.setString(3, checkedApplicantBean.getSupplentaryVerification());
            insertStat.setString(4, checkedApplicantBean.getDocMarriageCertificate());
            insertStat.setString(5, checkedApplicantBean.getDocBirthCertificate());
            insertStat.setString(6, checkedApplicantBean.getLastUpdatedUser());
            insertStat.setTimestamp(7, new Timestamp(checkedApplicantBean.getLastUpdatedTime().getTime()));
            insertStat.setString(8, checkedApplicantBean.getDocIdentification());


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateVerifyDetailsOfApplication(Connection con, CheckedSupplementaryDetailsBean checkedApplicantBean) throws Exception {
        boolean success = false;
        PreparedStatement insertStat = null;
        try {
            insertStat = con.prepareStatement("UPDATE CARDAPPLICATIONVERIFY SET MAINAPPLICANT=?,SUPPLIMENTARYAPPLICANT=?,DOCMARRIAGECERTIFICATE=?,"
                    + "DOCBIRTHCERTIFICATE=?,LASTUPDATEDUSER=?,LASTUPDATEDTIME=?,DOCIDENTIFICATION=? WHERE APPLICATIONID =?");



            insertStat.setString(1, checkedApplicantBean.getMainVerification());
            insertStat.setString(2, checkedApplicantBean.getSupplentaryVerification());
            insertStat.setString(3, checkedApplicantBean.getDocMarriageCertificate());
            insertStat.setString(4, checkedApplicantBean.getDocBirthCertificate());
            insertStat.setString(5, checkedApplicantBean.getLastUpdatedUser());
            insertStat.setTimestamp(6, new Timestamp(checkedApplicantBean.getLastUpdatedTime().getTime()));
            insertStat.setString(7, checkedApplicantBean.getDocIdentification());
            insertStat.setString(8, checkedApplicantBean.getApplicationId());


            insertStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertStat.close();
        }
        return success;
    }

    public boolean updateCardApplicationStatus(Connection con, CheckedSupplementaryDetailsBean checkedApplicantBean, String applicationStatus) throws Exception {
        boolean success = false;
        PreparedStatement updateStat = null;
        try {
            updateStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=?"
                    + " WHERE APPLICATIONID=? ");

            updateStat.setString(1, applicationStatus);
            updateStat.setString(2, checkedApplicantBean.getApplicationId());


            updateStat.executeUpdate();
            success = true;

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return success;
    }

    public CheckedSupplementaryDetailsBean getAllPreviousCheckedDetails(Connection con, String applicationId) throws Exception {


        PreparedStatement stmt = null;		// Holds the statement to be executed
        String strSqlBasic = null;
        ResultSet rs = null;// Holds the Sql query


        try {

            strSqlBasic = "SELECT APPLICATIONID,MAINAPPLICANT,SUPPLIMENTARYAPPLICANT,DOCMARRIAGECERTIFICATE,DOCBIRTHCERTIFICATE,DOCIDENTIFICATION "
                    + "FROM CARDAPPLICATIONVERIFY WHERE APPLICATIONID=? ";

            stmt = con.prepareStatement(strSqlBasic);
            stmt.setString(1, applicationId);

            rs = stmt.executeQuery();

            checkedApplicationBean = new CheckedSupplementaryDetailsBean();

            while (rs.next()) {


                CheckedSupplementaryDetailsBean resultChecked = new CheckedSupplementaryDetailsBean();


                resultChecked.setApplicationId(rs.getString("APPLICATIONID"));
                resultChecked.setMainVerification(rs.getString("MAINAPPLICANT"));
                resultChecked.setSupplentaryVerification(rs.getString("SUPPLIMENTARYAPPLICANT"));
                resultChecked.setDocMarriageCertificate(rs.getString("DOCMARRIAGECERTIFICATE"));
                resultChecked.setDocBirthCertificate(rs.getString("DOCBIRTHCERTIFICATE"));
                resultChecked.setDocIdentification(rs.getString("DOCIDENTIFICATION"));

                checkedApplicationBean = resultChecked;

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

        return checkedApplicationBean;
    }
}
