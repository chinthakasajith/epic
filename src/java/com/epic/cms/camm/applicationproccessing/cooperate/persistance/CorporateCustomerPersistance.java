package com.epic.cms.camm.applicationproccessing.cooperate.persistance;

import com.epic.cms.camm.applicationproccessing.capturedata.bean.CardApplicationStatusBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.AreaBeanCoCustomer;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBankBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.CustomerCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocTypeBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.DocumentUploadCorporateBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.IdCoCustomerBean;
import com.epic.cms.camm.applicationproccessing.cooperate.bean.VerificationCategoryCorporateBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class CorporateCustomerPersistance {

    private List<AreaBeanCoCustomer> areaLst = null;
    private HashMap<String, String> identificationList = null;
    private List<String> nationalityList = null;
    private List<CustomerCorporateBean> cardBankDetails = null;
    private List<VerificationCategoryCorporateBean> verificationCatCoList = null;
    private List<DocTypeBean> documentTypeLst = null;
    List<DocumentUploadCorporateBean> cardDocumentDetails = null;
    private CommonFilePathBean commonFilePathBean = null;
    private ResultSet rs;

    public List<AreaBeanCoCustomer> getAllAreaList(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        areaLst = new ArrayList<AreaBeanCoCustomer>();
        try {
            stmt = con.prepareStatement("SELECT AREACODE,DESCRIPTION,DISTRICTCODE FROM AREA ORDER BY DESCRIPTION ASC ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                AreaBeanCoCustomer bean = new AreaBeanCoCustomer();

                bean.setAreaCode(rs.getString("AREACODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setDistricCode(rs.getString("DISTRICTCODE"));

                areaLst.add(bean);
            }


        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return areaLst;
    }

    public HashMap<String, String> getAllIdentificationType(Connection con) throws Exception {

        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;
        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOCUMENTTYPECODE,TC.DOCUMENTNAME "
                    + " FROM APPLICATIONDOCUMENT TC, STATUS ST WHERE TC.STATUS = ST.STATUSCODE AND TC.VERIFICATIONCATEGORY= 'ID'");

            rs = getAllUserRole.executeQuery();
            identificationList = new HashMap<String, String>();

            while (rs.next()) {

                identificationList.put(rs.getString("DOCUMENTTYPECODE"), rs.getString("DOCUMENTNAME"));

            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    getAllUserRole.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return identificationList;
    }

    public List<String> getAllNationality(Connection con) throws Exception {
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        nationalityList = new ArrayList<String>();
        try {
            stmt = con.prepareStatement("SELECT COUNTRYNUMCODE,COUNTRYALPHA2CODE,COUNTRYALPHA3CODE,NATIONALITY FROM COUNTRY WHERE NATIONALITY IS NOT NULL");
            rs = stmt.executeQuery();

            while (rs.next()) {

                nationalityList.add(rs.getString("NATIONALITY"));

            }


        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return nationalityList;
    }

    public IdCoCustomerBean getIdentifyDetails(Connection CMSCon, String applicationId) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        IdCoCustomerBean bean = new IdCoCustomerBean();


        query = "SELECT CA.APPLICATIONID, AD.DOCUMENTTYPECODE, AD.DOCUMENTNAME, CA.IDENTIFICATIONNO"
                + " FROM CARDAPPLICATION CA, APPLICATIONDOCUMENT AD"
                + " WHERE CA.IDENTIFICATIONTYPE=AD.DOCUMENTTYPECODE"
                + " AND CA.CARDCATEGORY=AD.CARDCATEGORYCODE"
                + " AND APPLICATIONID=?";

        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, applicationId);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setIdNumber(rs.getString("IDENTIFICATIONNO"));
                bean.setIdType(rs.getString("DOCUMENTNAME"));
                bean.setIdCode(rs.getString("DOCUMENTTYPECODE"));
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return bean;
    }

//    public CustomerCorporateBean getAllDetailsCustomer(Connection CMSCon, String applicationId) {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        String query;
//        
//        query = "";
//        
//    }
    public int insertPersonalRecord(Connection CMSCon, CustomerCorporateBean personalBean) throws SQLException, Exception {
        int resultId;
        PreparedStatement ps = null;
        String query;

        query = "INSERT INTO CARDCOOPRATEAPPLICATIONDETAILS(APPLICATIONID, TITLE, FIRSTNAME, LASTNAME, MIDDLENAME, NAMEONCARD,"
                + " DATEOFBIRTH, GENDER, NATIONALITY, NIC, ADDRESS1, ADDRESS2, ADDRESS3, CITY, HOMETELEPHONENO, ACADEMICQUALIFICATION,"
                + " RELIGION, BLOODGROUP, MOTHERSMAIDENNAME, ANNUALSALARY,"
                + " FIXEDALLOWANCES, JOBTITLE, EMPDURATION)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            ps = CMSCon.prepareStatement(query);///

            ps.setString(1, personalBean.getApplicationId());
            ps.setString(2, personalBean.getTitle());
            ps.setString(3, personalBean.getFirstName());
            ps.setString(4, personalBean.getLastName());
            ps.setString(5, personalBean.getMiddleName());
            ps.setString(6, personalBean.getNameoncard());
            ps.setString(7, personalBean.getBirthday());
            ps.setString(8, personalBean.getGender());
            ps.setString(9, personalBean.getNationality());
            ps.setString(10, personalBean.getNic());
            ps.setString(11, personalBean.getAddress1());
            ps.setString(12, personalBean.getAddress2());
            ps.setString(13, personalBean.getAddress3());
            ps.setString(14, personalBean.getCity());
            ps.setString(15, personalBean.getTelphone());
            ps.setString(16, personalBean.getAcedemicQualifications());
            ps.setString(17, personalBean.getReligion());
            ps.setString(18, personalBean.getBloodGroup());
            ps.setString(19, personalBean.getMothersMadden());
            ps.setString(20, personalBean.getAnnualSalary());
            ps.setString(21, personalBean.getFixedallowance());
            ps.setString(22, personalBean.getJobtitle());
            ps.setString(23, personalBean.getEmpDuration());


            resultId = ps.executeUpdate();

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        return resultId;
    }

    public int insertPersonalBankRecord(Connection CMSCon, CustomerCorporateBean personalBean) throws SQLException, Exception {
        int resultId;
        PreparedStatement ps = null;
        String query;

        query = "INSERT INTO CARDAPPLICATIONBANKDETAILS(APPLICATIONID, BANKCODE, BRANCHCODE, ACCOUNTTYPE, ACCOUNTNO, ACCOUNTSINCEYEARS, ACCOUNTSINCEMONTHS)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {

            ps = CMSCon.prepareStatement(query);

            ps.setString(1, personalBean.getApplicationId());
            ps.setString(2, personalBean.getBankCode());
            ps.setString(3, personalBean.getBranchCode());
            ps.setString(4, personalBean.getAccountTpye());
            ps.setString(5, personalBean.getAccountNo());
            ps.setString(6, personalBean.getAccountSinceYears());
            ps.setString(7, personalBean.getAccountSinceMonths());

            resultId = ps.executeUpdate();


        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        return resultId;

    }

    public boolean updateCoCustomerCardApplicationStatus(String applicationId, String value, Connection con) throws Exception {

        PreparedStatement stmt = null;		// Holds the statement to be executed
        try {

            stmt = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS =?  WHERE APPLICATIONID= " + applicationId);
            stmt.setString(1, value);
            stmt.executeUpdate();

        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                stmt.close();

            } catch (Exception e) {
            }
        }
        return true;
    }

    public List<CustomerCorporateBean> getBankDetails(Connection CMSCon) throws Exception {
        PreparedStatement getAllByCatStat = null;
        cardBankDetails = new ArrayList<CustomerCorporateBean>();

        try {
            getAllByCatStat = CMSCon.prepareStatement("SELECT BANKCODE,BANKNAME FROM BANK ORDER BY BANKNAME ASC");

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                CustomerCorporateBean bean = new CustomerCorporateBean();

                bean.setBankCode(rs.getString("BANKCODE"));
                bean.setBankName(rs.getString("BANKNAME"));
//                bean.setAccountSince("Year: " + rs.getString("ACCOUNTSINCEYEARS") + " Months: " + rs.getString("ACCOUNTSINCEMONTHS"));

                cardBankDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardBankDetails;
    }

    /*
    public List<CardBankDetailsBean> getBankDetails(Connection con, String applicationId) throws Exception {
    PreparedStatement getAllByCatStat = null;
    cardBankDetails = new ArrayList<CardBankDetailsBean>();
    try {
    getAllByCatStat = con.prepareStatement("SELECT B.BANKNAME,BB.DESCRPTION,ACCOUNTTYPE,ACCOUNTNO,ACCOUNTSINCEYEARS,ACCOUNTSINCEMONTHS FROM CARDAPPLICATIONBANKDETAILS CA,BANK B,BANKBRANCH BB WHERE CA.BANKCODE=B.BANKCODE AND CA.BRANCHCODE=BB.BRANCHCODE AND B.BANKCODE=BB.BANKCODE AND APPLICATIONID = ?");
    
    getAllByCatStat.setString(1, applicationId);
    
    
    rs = getAllByCatStat.executeQuery();
    
    while (rs.next()) {
    CardBankDetailsBean bean = new CardBankDetailsBean();
    
    bean.setBankName(rs.getString("BANKNAME"));
    bean.setBankNameDes(rs.getString("BANKNAME"));
    bean.setBranchName(rs.getString("DESCRPTION"));
    bean.setAccountType(rs.getString("ACCOUNTTYPE"));
    bean.setAccountNumber(rs.getString("ACCOUNTNO"));
    bean.setAccountSince("Year: " + rs.getString("ACCOUNTSINCEYEARS") + " Months: " + rs.getString("ACCOUNTSINCEMONTHS"));
    
    cardBankDetails.add(bean);
    }
    } catch (SQLException ex) {
    throw ex;
    } finally {
    rs.close();
    getAllByCatStat.close();
    }
    return cardBankDetails;
    }
    
     */
    public List<VerificationCategoryCorporateBean> getVerificationCatCoList(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        verificationCatCoList = new ArrayList<VerificationCategoryCorporateBean>();

        query = "SELECT VERIFICATIONCATEGORYCODE,DESCRIPTON FROM VERIFICATIONCATEGORY";

        try {

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                VerificationCategoryCorporateBean bean = new VerificationCategoryCorporateBean();
                bean.setCategoryCode(rs.getString("VERIFICATIONCATEGORYCODE"));
                bean.setCategoryName(rs.getString("DESCRIPTON"));

                verificationCatCoList.add(bean);

            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                throw e;
            }
        }
        return verificationCatCoList;
    }

    public List<DocTypeBean> getDocTypeList(Connection CMSCon, String docType) throws IOException, Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query;
        documentTypeLst = new ArrayList<DocTypeBean>();

        query = "SELECT DOCUMENTTYPECODE,DOCUMENTNAME FROM APPLICATIONDOCUMENT WHERE VERIFICATIONCATEGORY=?";

        try {

            ps = CMSCon.prepareStatement(query);
            ps.setString(1, docType);
            rs = ps.executeQuery();

            while (rs.next()) {
                DocTypeBean bean = new DocTypeBean();
                bean.setTypeCode(rs.getString("DOCUMENTTYPECODE"));
                bean.setDescription(rs.getString("DOCUMENTNAME"));

                documentTypeLst.add(bean);
            }

        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
                ps.close();
            } catch (SQLException e) {
                throw e;
            }
        }

        return documentTypeLst;
    }

    public List<DocumentUploadCorporateBean> getDocumentDetails(Connection CMSCon, String applicationId) throws SQLException {
        PreparedStatement getAllByCatStat = null;
        cardDocumentDetails = new ArrayList<DocumentUploadCorporateBean>();
        try {
            getAllByCatStat = CMSCon.prepareStatement("SELECT D.APPLICATIONID,D.VERIFICATIONCATEGORY,D.DOCUMENTTYPE,D.FILENAME FROM CARDAPPLICATIONDOCUMENT D WHERE D.APPLICATIONID = ?");

            getAllByCatStat.setString(1, applicationId);


            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {
                DocumentUploadCorporateBean bean = new DocumentUploadCorporateBean();

                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setVerificationCategory(rs.getString("VERIFICATIONCATEGORY"));
                bean.setDocumentType(rs.getString("DOCUMENTTYPE"));
                bean.setFileName(rs.getString("FILENAME"));
                cardDocumentDetails.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cardDocumentDetails;
    }

    public CommonFilePathBean getFilePaths(Connection CMSCon, String osType) throws SQLException, Exception {

        PreparedStatement updateStat = null;
        ResultSet rs = null;
        try {
            updateStat = CMSCon.prepareStatement("SELECT CF.SCANNEDAPPLICATION,CF.SCANNEDDOCUMENT,CF.EMBOSSFILE,"
                    + "CF.EODFILE,CF.STATEMENTFILE,CF.EMVFILE,CF.ENCODEFILE,CF.MODFILE,CF.LETTERS"
                    + " FROM COMMONFILEPATH CF WHERE CF.OSTYPE=?");

            updateStat.setString(1, osType);

            rs = updateStat.executeQuery();

            while (rs.next()) {

                commonFilePathBean = new CommonFilePathBean();

                commonFilePathBean.setScanApplication(rs.getString("SCANNEDAPPLICATION"));
                commonFilePathBean.setScandocument(rs.getString("SCANNEDDOCUMENT"));
                commonFilePathBean.setEmbossFile(rs.getString("EMBOSSFILE"));
                commonFilePathBean.setEodFile(rs.getString("EODFILE"));
                commonFilePathBean.setStatement(rs.getString("STATEMENTFILE"));
                commonFilePathBean.setEmvFile(rs.getString("EMVFILE"));
                commonFilePathBean.setEncodefile(rs.getString("ENCODEFILE"));
                commonFilePathBean.setModFile(rs.getString("MODFILE"));
                commonFilePathBean.setLetters(rs.getString("LETTERS"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            updateStat.close();
        }
        return commonFilePathBean;
    }

    public CardApplicationStatusBean getAllApplicationStatus(String applicationId, Connection con) throws Exception {
        CardApplicationStatusBean bean = new CardApplicationStatusBean();
        PreparedStatement stmt = null;		// Holds the statement to be executed
        ResultSet rs = null;// Holds the Sql query
        try {
            stmt = con.prepareStatement("SELECT APPLICATIONID,TAB01STATUS,TAB02STATUS,TAB03STATUS,TAB04STATUS,TAB05STATUS,TAB06STATUS,APPLICATIONSTATUS FROM CARDAPPLICATIONSTATUS WHERE APPLICATIONID =?  ");
            stmt.setString(1, applicationId);
            rs = stmt.executeQuery();

            while (rs.next()) {

                bean.setApplicationId(rs.getString("APPLICATIONID"));
                bean.setTableOne(rs.getString("TAB01STATUS"));
                bean.setTableTwo(rs.getString("TAB02STATUS"));
                bean.setTableThree(rs.getString("TAB03STATUS"));
                bean.setTableFore(rs.getString("TAB04STATUS"));
                bean.setTableFive(rs.getString("TAB05STATUS"));
                bean.setTableSix(rs.getString("TAB06STATUS"));
                bean.setApplicationStatus(rs.getString("APPLICATIONSTATUS"));


            }


        } catch (SQLException sqlex) {

            throw sqlex;
        } catch (Exception ex) {

            throw ex;
        } finally {
            try {
                rs.close();
                stmt.close();

            } catch (Exception e) {
            }
        }
        return bean;
    }
}
