package com.epic.cms.reportexp.cardapplication.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardProductBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemUserBean;
import com.epic.cms.reportexp.cardapplication.bean.ApplicationPinGenAndMailBean;
import com.epic.cms.reportexp.cardapplication.bean.CardprdctBean;
import com.epic.cms.system.util.variable.DataTypeVarList;
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
 * @author jeevan
 */
public class ApplicationPinGenAndMailPersistance {

    ResultSet rs;
    HashMap<String, String> cardDomain = null;
    HashMap<String, String> bnkBranches = null;
    HashMap<String, String> cdProduct = null;
    private HashMap<String, String> productModes = null;
    private HashMap<String, String> cardDomainList = null;
    private HashMap<String, String> appStatusList = null;
    private HashMap<String, String> cardTypeList = null;
    private HashMap<String, String> cardPrdctList = null;
    private HashMap<String, String> cardTypePinMethod = null;
    private HashMap<String, String> cardTypeProduct = null;
    private HashMap<String, String> pinGenTime = null;
    private HashMap<String, String> bnkCardNo = null;
    private HashMap<String, String> bnkUserName = null;
    private HashMap<String, String> fromDate = null; //
    private HashMap<String, String> toDate = null; //
    SystemUserBean sysUsrBean = null;
    private HashMap<String, String> priorityLevelList = null;
    private ApplicationPinGenAndMailBean resultBean = null;

    public HashMap<String, String> getBranchNames(Connection con) throws SQLException {
        PreparedStatement ps = null;
        bnkBranches = new HashMap<String, String>();

        try {
            ps = con.prepareStatement("SELECT BRANCHCODE,DESCRPTION FROM BANKBRANCH WHERE BANKCODE = (SELECT BANKCODE FROM COMMONPARAMETER)");
            rs = ps.executeQuery();

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

    public HashMap<String, String> getPriorityLevels(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRoles = null;

        try {
            getAllUserRoles = con.prepareStatement("SELECT TC.PRIORITYLEVELCODE,TC.DESCRIPTION "
                    + "FROM PRIORITYLEVEL TC");
            rs = getAllUserRoles.executeQuery();
            priorityLevelList = new HashMap<String, String>();

            while (rs.next()) {
                priorityLevelList.put(rs.getString("PRIORITYLEVELCODE"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRoles.close();
        }
        return priorityLevelList;
    }

    public HashMap<String, String> getCardDomains(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRole = null;

        try {
            getAllUserRole = con.prepareStatement("SELECT TC.DOMAINID,TC.DESCRIPTION FROM CARDDOMAIN TC");
            rs = getAllUserRole.executeQuery();

            cardDomainList = new HashMap<String, String>();
            while (rs.next()) {
                cardDomainList.put(rs.getString("DOMAINID"), rs.getString("DESCRIPTION"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRole.close();
        }
        return cardDomainList;
    }

    public HashMap<String, String> getCardType(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUaerRolle = null;

        try {
            getAllUaerRolle = con.prepareStatement("SELECT CARDTYPECODE,DESCRIPTION FROM CARDTYPE");
            rs = getAllUaerRolle.executeQuery();

            cardTypeList = new HashMap<String, String>();
            while (rs.next()) {
                cardTypeList.put(rs.getString("CARDTYPECODE"), rs.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUaerRolle.close();
        }
        return cardTypeList;
    }

    public List<CardprdctBean> getCardProductByType(Connection con, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUaerRolle = null;
        List<CardprdctBean> cardPrdctList = null;
        try {
            getAllUaerRolle = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE CARDTYPE = ?");

            getAllUaerRolle.setString(1, cardType);

            rs = getAllUaerRolle.executeQuery();

            cardPrdctList = new ArrayList<CardprdctBean>();
            while (rs.next()) {
                CardprdctBean bean = new CardprdctBean();

                bean.setProductCode(rs.getString("PRODUCTCODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardPrdctList.add(bean);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUaerRolle.close();
        }
        return cardPrdctList;
    }

    public HashMap<String, String> getCardPinMethod(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRolle = null;

        try {
            getAllUserRolle = con.prepareStatement("SELECT C.PINMETHODID, C.DESCRIPTION FROM CARDPINMETHOD C");
            rs = getAllUserRolle.executeQuery();

            cardTypePinMethod = new HashMap<String, String>();
            while (rs.next()) {
                cardTypePinMethod.put(rs.getString("PINMETHODID"), rs.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRolle.close();
        }
        return cardTypePinMethod;
    }

    public HashMap<String, String> getCardProduct(Connection con, String cardType) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRolle = null;

        try {
            getAllUserRolle = con.prepareStatement("SELECT C.PRODUCTCODE, C.DESCRIPTION FROM CARDPRODUCT C WHERE C.CARDTYPE = ?");

            getAllUserRolle.setString(1, cardType);
            rs = getAllUserRolle.executeQuery();

            cardTypeProduct = new HashMap<String, String>();
            while (rs.next()) {
                cardTypeProduct.put(rs.getString("PRODUCTCODE"), rs.getString("DESCRIPTION"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRolle.close();
        }
        return cardTypeProduct;
    }

    public HashMap<String, String> getPinGenTime(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRolle = null;

        try {
            getAllUserRolle = con.prepareStatement("SELECT C.CREATEDTIME, C.LASTUPDATEDTIME FROM CARDAPPLICATIONHISTORY C");
            rs = getAllUserRolle.executeQuery();

            pinGenTime = new HashMap<String, String>();
            while (rs.next()) {
                pinGenTime.put(rs.getString("LASTUPDATEDTIME"), rs.getString("CREATEDTIME"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRolle.close();
        }
        return pinGenTime;
    }

    public HashMap<String, String> getCardNo(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRolle = null;

        try {
            getAllUserRolle = con.prepareStatement("SELECT C.CARDTYPE, C.CARDNUMBER FROM CARD C");
            rs = getAllUserRolle.executeQuery();

            bnkCardNo = new HashMap<String, String>();
            while (rs.next()) {
                bnkCardNo.put(rs.getString("CARDTYPE"), rs.getString("CARDNUMBER"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRolle.close();
        }
        return bnkCardNo;
    }

    public HashMap<String, String> getUser(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllUserRolle = null;

        try {
            getAllUserRolle = con.prepareStatement("");
            rs = getAllUserRolle.executeQuery();

            bnkUserName = new HashMap<String, String>();
            while (rs.next()) {
                bnkUserName.put(rs.getString(""), rs.getString(""));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllUserRolle.close();
        }
        return bnkUserName;
    }
//     public HashMap<String, String>getFromDate(Connection con) throws Exception { ///*****
//        ResultSet rs = null;
//        PreparedStatement getAllUserRolle = null;
//        
//        try {
//            getAllUserRolle = con.prepareStatement("");
//            rs = getAllUserRolle.executeQuery();
//            
//            fromDate = new HashMap<String, String>();
//            while (rs.next()) {
//                fromDate.put(rs.getString(""), rs.getString(""));
//            }
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            getAllUserRolle.close();
//        }
//        return fromDate;
//    }

//     public HashMap<String, String>getToDate(Connection con) throws Exception { ///*****
//        ResultSet rs = null;
//        PreparedStatement getAllUserRolle = null;
//        
//        try {
//            getAllUserRolle = con.prepareStatement("");
//            rs = getAllUserRolle.executeQuery();
//            
//            toDate = new HashMap<String, String>();
//            while (rs.next()) {
//                toDate.put(rs.getString(""), rs.getString(""));
//            }
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            getAllUserRolle.close();
//        }
//        return toDate;
//    }
    public List<ApplicationPinGenAndMailBean> getPinGenAndMailDetails(Connection con, ApplicationPinGenAndMailBean inputBean) throws Exception {
        List<ApplicationPinGenAndMailBean> searchList = new ArrayList<ApplicationPinGenAndMailBean>();
        PreparedStatement ps = null;

        try {
            String defaultQuery = "SELECT DISTINCT C.CARDNUMBER,CPM.PINMETHODID,C.CARDTYPE,C.CARDPRODUCT,CT.DESCRIPTION AS TYPEDES,CPD.DESCRIPTION AS CPDESCRIPTION ,CAH.LASTUPDATEDUSER, CAH.CREATEDTIME,"
                    + "CA.BRANCHCODE,BB.DESCRPTION AS BRANCHDES,CAH.LASTUPDATEDUSER,PL.DESCRIPTION AS PLDES,"
                    + "CD.DESCRIPTION AS CDDES,CPM.DESCRIPTION AS PINMETHODDES,CA.PRIORITYLEVELCODE,C.CARDDOMAIN FROM"
                    + " CARD C,CARDTYPE CT,CARDPRODUCT CPD,CARDDOMAIN CD,CARDPINMETHOD CPM,"
                    + "CARDAPPLICATIONHISTORY CAH,CARDAPPLICATIONPERSONALDETAILS CP,CARDAPPLICATION CA,BANKBRANCH BB,PRIORITYLEVEL PL WHERE C.CARDTYPE = CT.CARDTYPECODE "
                    + "AND C.CARDPRODUCT = CPD.PRODUCTCODE AND C.CARDDOMAIN = CD.DOMAINID "
                    + "AND C.PINMETHOD = CPM.PINMETHODID AND C.CARDNUMBER = CAH.APPLICATIONID "
                    + "AND C.APPLICATIONID = CP.APPLICATIONID AND C.APPLICATIONID = CA.APPLICATIONID AND C.PRIORITYLEVEL = PL.PRIORITYLEVELCODE AND CA.BRANCHCODE = BB.BRANCHCODE AND (CAH.APPLICATIONLEVEL = 'PGEN' OR CAH.APPLICATIONLEVEL = 'PMAL') ";

            String condition = "";

            if (inputBean.getNic() != null && (!inputBean.getNic().contentEquals("") || inputBean.getNic().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getNic() + "%' ";
                condition += "AND CA.IDENTIFICATIONTYPE = 'NIC'";
            }
            if (inputBean.getPassport() != null && (!inputBean.getPassport().contentEquals("") || inputBean.getPassport().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getPassport() + "%' ";
                condition += "AND CA.IDENTIFICATIONTYPE = 'PAS'";
//            }
//            if ( inputBean.getLicence() !=null && (!inputBean.getLicence().contentEquals("") || inputBean.getLicence().length() > 0)) {
//                if (!condition.equals("")) {
//                    condition += " AND ";
//                }
//                condition += "CA.IDENTIFICATIONNO LIKE '%" + inputBean.getLicence() + "%' ";
//                condition += "AND CA.IDENTIFICATIONTYPE = 'PAS'";

            }
            if (inputBean.getCardNo() != null && (!inputBean.getCardNo().contentEquals("") || inputBean.getCardNo().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDNUMBER LIKE '%" + inputBean.getCardNo() + "%' ";
            }
            if (inputBean.getBranchCode() != null && (!inputBean.getBranchCode().contentEquals("") || inputBean.getBranchCode().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.BRANCHCODE = '" + inputBean.getBranchCode() + "'";
            }

            if (inputBean.getPriorityLevelCode() != null && (!inputBean.getPriorityLevelCode().contentEquals("") || inputBean.getPriorityLevelCode().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.PRIORITYLEVELCODE = '" + inputBean.getPriorityLevelCode() + "'";
            }
            if (inputBean.getDomainCode() != null && (!inputBean.getDomainCode().contentEquals("") || inputBean.getDomainCode().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.CARDAPPLIACTIONDOMAIN = '" + inputBean.getDomainCode().toUpperCase() + "'";
            }
            if (inputBean.getCardType() != null && (!inputBean.getCardType().contentEquals("") || inputBean.getCardType().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDTYPE = '" + inputBean.getCardType().toUpperCase() + "'";
            }
            if (inputBean.getCardProduct() != null && (!inputBean.getCardProduct().contentEquals("") || inputBean.getCardProduct().length() > 0 || inputBean.getCardProduct() == null)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.CARDPRODUCT = '" + inputBean.getCardProduct().toUpperCase() + "'";
            }
            if (inputBean.getPinMethod() != null && (!inputBean.getPinMethod().contentEquals("") || inputBean.getPinMethod().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "C.PINMETHOD = '" + inputBean.getPinMethod().toUpperCase() + "'";
            }
            if (inputBean.getUser() != null && (!inputBean.getUser().contentEquals("") || inputBean.getUser().length() > 0)) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAH.LASTUPDATEDUSER='" + inputBean.getUser() + "'";
            }
            if (inputBean.getFromDate() != null && inputBean.getToDate() != null && !inputBean.getFromDate().contentEquals("") && !inputBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CAH.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate().toString()) + "','yy-mm-dd') AND CAH.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate().toString()) + "','yy-mm-dd')";
            } else {

                if (inputBean.getFromDate() != null && !inputBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CAH.CREATEDTIME >= to_Date('" + this.stringTodate(inputBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (inputBean.getToDate() != null && !inputBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CAH.CREATEDTIME <= to_Date('" + this.stringTodate(inputBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                defaultQuery += "  AND " + condition;
            } else {
            }

            System.out.println("******************** " + defaultQuery);
            ps = con.prepareStatement(defaultQuery);
            rs = ps.executeQuery();

            while (rs.next()) {
                resultBean = new ApplicationPinGenAndMailBean();

                resultBean.setBranchCode(rs.getString("BRANCHCODE"));
                resultBean.setBranchName(rs.getString("BRANCHDES"));
                resultBean.setPriorityLevelCode(rs.getString("PRIORITYLEVELCODE"));
                resultBean.setPriorityLevelDes(rs.getString("PLDES"));
                resultBean.setDomainCode(rs.getString("CARDDOMAIN"));
                resultBean.setDomainDes(rs.getString("CDDES"));
                resultBean.setCardNo(rs.getString("CARDNUMBER"));
                resultBean.setPinMethod(rs.getString("PINMETHODDES"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("TYPEDES"));
                resultBean.setCardProductDes(rs.getString("CPDESCRIPTION"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setUser(rs.getString("LASTUPDATEDUSER"));
                resultBean.setPinGeneratedDateTime(rs.getString("CREATEDTIME"));
                resultBean.setFromDate(rs.getString("CREATEDTIME"));
                resultBean.setToDate(rs.getString("CREATEDTIME"));

                searchList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();

        }

        return searchList;
    }

    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));
    }

    public List<CardProductBean> getCardProductList(Connection con) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllByCatStat = null;
        CardProductBean cProduct = null;
        List<CardProductBean> cpList = new ArrayList<CardProductBean>();
        try {
            getAllByCatStat = con.prepareStatement("SELECT PRODUCTCODE,DESCRIPTION FROM CARDPRODUCT WHERE (CARDDOMAIN = ? OR CARDDOMAIN = ? OR CARDDOMAIN = ? OR CARDDOMAIN = ? OR CARDDOMAIN = ?)");

            getAllByCatStat.setString(1, DataTypeVarList.CARD_DOMAIN_DEBIT);
            getAllByCatStat.setString(2, DataTypeVarList.CARD_DOMAIN_GIFT);
            getAllByCatStat.setString(3, DataTypeVarList.CARD_DOMAIN_LOYALTY);
            getAllByCatStat.setString(4, DataTypeVarList.CARD_DOMAIN_PREPAID);
            getAllByCatStat.setString(5, DataTypeVarList.CARD_DOMAIN_CREDIT);

            rs = getAllByCatStat.executeQuery();

            while (rs.next()) {

                cProduct = new CardProductBean();

                cProduct.setProductCode(rs.getString("PRODUCTCODE"));
                cProduct.setDescription(rs.getString("DESCRIPTION"));

                cpList.add(cProduct);
            }
        } catch (SQLException ex) {
            throw ex;

        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return cpList;
    }

}
