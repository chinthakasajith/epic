/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.camm.applicationconfirmation.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardBinBean;
import com.epic.cms.admin.templatemgt.cardtemplate.bean.CardTemplateBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitAppValuesBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitApplicationDetailBean;
import com.epic.cms.camm.applicationconfirmation.bean.DebitCardAccountTemplateBean;
import com.epic.cms.camm.applicationconfirmation.bean.RecommendSchemBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author badrika
 */
public class DebitApplicationConfirmationPersistance {

    private List<RecommendSchemBean> cardProductList;
    private ResultSet rs;
    private List<DebitCardAccountTemplateBean> accountTempList;
    private List<CardBinBean> cardBinList;
    private List<CardTemplateBean> cardTempList;
    private List<DebitApplicationDetailBean> personalDetailList;
    private DebitApplicationDetailBean bean;

    public List<RecommendSchemBean> getCardProducts(Connection con, String cardType) throws Exception {
        PreparedStatement ps = null;

        cardProductList = new ArrayList<RecommendSchemBean>();
        try {
            ps = con.prepareStatement("SELECT PRODUCTCODE, DESCRIPTION FROM CARDPRODUCT WHERE CARDTYPE = ? AND CARDDOMAIN = ? ");
            ps.setString(1, cardType);
            ps.setString(2, StatusVarList.DEBIT);

            rs = ps.executeQuery();

            while (rs.next()) {

                RecommendSchemBean bean = new RecommendSchemBean();

                bean.setCardProductCode(rs.getString("PRODUCTCODE"));
                bean.setCardproductDescription(rs.getString("DESCRIPTION"));

                cardProductList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardProductList;
    }

    //getDebitCardApplicationDetails
    public DebitApplicationDetailBean getDebitCardApplicationDetails(Connection con, String applicationId) throws Exception {
        PreparedStatement ps = null;

        bean = new DebitApplicationDetailBean();
        try {
            ps = con.prepareStatement("SELECT TITLE, FIRSTNAME, LASTNAME, DATEOFBIRTH, NAMEONCARD, REQUESTEDCARDTYPE, CARDPRODUCT, ADDRESS1, ADDRESS2, ADDRESS3, "
                    + "AREA, HOMETELEPHONENO, OFFICEPHONENO, MOBILENO, a.DESCRIPTION as areaDes, ct.DESCRIPTION as ctdes, cp.DESCRIPTION as cpdes, "
                    + "LOYALTYREQUIRED "
                    + "FROM DEBITCARDAPPLICATIONDETAILS, AREA a, CARDTYPE ct, CARDPRODUCT cp "
                    + "WHERE a.AREACODE=AREA and REQUESTEDCARDTYPE=ct.CARDTYPECODE and CARDPRODUCT=cp.PRODUCTCODE and APPLICATIONID=? ");

            ps.setString(1, applicationId);

            rs = ps.executeQuery();

            while (rs.next()) {

                //DebitApplicationDetailBean bean = new DebitApplicationDetailBean();

                bean.setTitle(rs.getString("TITLE"));
                bean.setFirstName(rs.getString("FIRSTNAME"));
                bean.setLastName(rs.getString("LASTNAME"));
                bean.setDateOfBirth(rs.getString("DATEOFBIRTH"));
                bean.setNameOnCard(rs.getString("NAMEONCARD"));
                bean.setReqCardType(rs.getString("REQUESTEDCARDTYPE"));
                bean.setReqCardTypeDes(rs.getString("ctdes"));
                bean.setCardProductDes(rs.getString("cpdes"));
                bean.setAddress1(rs.getString("ADDRESS1"));
                bean.setAddress2(rs.getString("ADDRESS2"));
                bean.setAddress3(rs.getString("ADDRESS3"));
                bean.setArea(rs.getString("AREA"));
                bean.setAreaDes(rs.getString("areaDes"));
                bean.setHomeTele(rs.getString("HOMETELEPHONENO"));
                bean.setOfficeTele(rs.getString("OFFICEPHONENO"));
                bean.setMobileTele(rs.getString("MOBILENO"));
                bean.setLoyaltyReq(rs.getString("LOYALTYREQUIRED"));



                // personalDetailList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return bean;
    }

    public List<DebitCardAccountTemplateBean> getAccountTemplates(Connection con, String cardType) throws Exception {
        PreparedStatement ps = null;

        accountTempList = new ArrayList<DebitCardAccountTemplateBean>();
        try {
            ps = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM DEBITCARDACCOUNTTEMPLATE WHERE CARDTYPE=? ");
            ps.setString(1, cardType);

            rs = ps.executeQuery();

            while (rs.next()) {

                DebitCardAccountTemplateBean bean = new DebitCardAccountTemplateBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));

                accountTempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return accountTempList;
    }

    public List<CardBinBean> getDebitBinList(Connection con, String productionMode, String cardType) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT BIN,DESCRIPTION FROM CARDBIN WHERE PRODUCTIONMODE = ? AND CARDTYPE = ? AND CARDDOMAIN = ?  AND ONUSSTATUS = ? AND STATUS = ?");

            ps.setString(1, productionMode);
            ps.setString(2, cardType);
            ps.setString(3, StatusVarList.DEBIT);
            ps.setString(4, StatusVarList.YESSTATUS);
            ps.setString(5, StatusVarList.ACTIVE_STATUS);

            cardBinList = new ArrayList<CardBinBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                CardBinBean bean = new CardBinBean();

                bean.setBinNumber(rs.getString("BIN"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                cardBinList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardBinList;
    }

    public List<CardTemplateBean> getDebitCardTemplates(Connection con, String productCode, String accountTempCode) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT TEMPLATECODE,TEMPLATENAME FROM DEBITCARDTEMPLATE WHERE PRODUCTCODE = ?  AND DEBITACCOUNTTEMPLATECODE = ? AND STATUS = ? ");

            ps.setString(1, productCode);
            ps.setString(2, accountTempCode);
            ps.setString(3, StatusVarList.ACTIVE_STATUS);

            cardTempList = new ArrayList<CardTemplateBean>();

            rs = ps.executeQuery();

            while (rs.next()) {

                CardTemplateBean bean = new CardTemplateBean();

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateName(rs.getString("TEMPLATENAME"));

                cardTempList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardTempList;
    }

    public String getCusTemplate(Connection con, String accTemplateCode) throws SQLException {
        String custempltid = "";
        PreparedStatement ps = null;
        //  ResultSet rs = null;
        try {
            ps = con.prepareStatement("select CUSTOMERTEMPLATECODE from DEBITCARDACCOUNTTEMPLATE where TEMPLATECODE = ? ");

            ps.setString(1, accTemplateCode);

            rs = ps.executeQuery();

            while (rs.next()) {
                custempltid = rs.getString("CUSTOMERTEMPLATECODE");
            }

        } catch (SQLException ex) {
            throw ex;
        } finally {
            ps.close();
            rs.close();
        }
        return custempltid;


    }

    public boolean updateCardApplication(Connection con, String applicationId, String cardProduct, String productionMode, String cardKey, String binProfile, String acctempCode, String cardtempCode, String loyalty) throws Exception {
        PreparedStatement ps = null;
         boolean result = false;
        String cusTempltId = this.getCusTemplate(con, acctempCode);

        try {
            ps = con.prepareStatement("UPDATE CARDAPPLICATION SET CONFIRMEDCARDPRODUCT = ?,PRODUCTIONMODE = ?,BINPROFILE = ?,ACCTEMPLATECODE = ?,"
                    + "CARDTEMPLATECODE = ?,LOYALTYREQUIRED = ?,CUSTEMPLATECODE = ?,CARDKEYID = ? WHERE APPLICATIONID = ?");

            ps.setString(1, cardProduct);
            ps.setString(2, productionMode);
            ps.setString(3, binProfile);
            ps.setString(4, acctempCode);
            ps.setString(5, cardtempCode);
            ps.setString(6, loyalty);
            ps.setString(7, cusTempltId);
            ps.setString(8, cardKey);
            ps.setString(9, applicationId);


            ps.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            ps.close();
        }
        return result;
    }

    public boolean UpdateCardApplicationStatus(Connection con, String applicationId, String status) throws Exception {
        PreparedStatement uptByCatStat = null;
        boolean result = false;
        try {
            uptByCatStat = con.prepareStatement("UPDATE CARDAPPLICATIONSTATUS SET APPLICATIONSTATUS=? WHERE APPLICATIONID = ?");

            uptByCatStat.setString(1, status);
            uptByCatStat.setString(2, applicationId);


            rs = uptByCatStat.executeQuery();

            result = true;
        } catch (SQLException ex) {
            result = false;
            throw ex;
        } finally {
            rs.close();
            uptByCatStat.close();
        }
        return result;
    }

    public Boolean updateRejectReasonAndRemark(Connection con, String rejectReason, String remark, String applicationId) throws Exception {
        PreparedStatement getAllByCatStat = null;
        boolean status = false;
        try {
            getAllByCatStat = con.prepareStatement("UPDATE CARDAPPLICATION SET REJECTREASONCODE = ?, REJECTREMARKS = ? WHERE APPLICATIONID = ? ");

            getAllByCatStat.setString(1, rejectReason);
            getAllByCatStat.setString(2, remark);
            getAllByCatStat.setString(3, applicationId);

            rs = getAllByCatStat.executeQuery();
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            getAllByCatStat.close();
        }
        return status;
    }

    public List<DebitAppValuesBean> changeBinProfile(Connection cmsCon, String productCode) throws Exception {
        List<DebitAppValuesBean> changeBinProfileList = new ArrayList<DebitAppValuesBean>();
        PreparedStatement ps = null;
        String query;

       // query = "SELECT CB.BIN, CB.DESCRIPTION FROM CARDBIN CB";
        
        StringBuffer buff = new StringBuffer();
        buff.append("SELECT DISTINCT CB.BIN,CB.DESCRIPTION FROM CARDPRODUCTBIN CPB, CARDBIN CB WHERE CPB.BINCODE = CB.BIN AND CPB.PRODUCTCODE = ? ");
        buff.append(" AND CB.CARDDOMAIN = ? AND CB.ONUSSTATUS = ? AND CB.STATUS = ? ");
        
        try {

            ps = cmsCon.prepareStatement(buff.toString());

            ps.setString(1, productCode);
            ps.setString(2, StatusVarList.DEBIT);
            ps.setString(3, StatusVarList.YESSTATUS);
            ps.setString(4, StatusVarList.ACTIVE_STATUS);            
            
            rs = ps.executeQuery();

            while (rs.next()) {
                DebitAppValuesBean bean = new DebitAppValuesBean();
                bean.setCardBin(rs.getString("BIN"));
                bean.setCardBinDescription(rs.getString("DESCRIPTION"));

                changeBinProfileList.add(bean);
            }
            return changeBinProfileList;

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
    }

    public List<CardBinBean> getCardKeyList(Connection con, String cardProductCode,String binProfileCode) throws Exception {
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
        List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
        buff.append("SELECT CARDKEYID FROM CARDPRODUCTBIN WHERE PRODUCTCODE = ? AND BINCODE = ?");
       
        try {
            ps = con.prepareStatement(buff.toString());
            ps.setString(1, cardProductCode);
            ps.setString(2, binProfileCode);
            cardBinList = new ArrayList<CardBinBean>();
            rs = ps.executeQuery();
            while (rs.next()) {
                CardBinBean bean = new CardBinBean();
                bean.setCardKey(rs.getString("CARDKEYID"));
               
                cardKeyList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardKeyList;
    }
   
 public List<CardBinBean> getProductionModeList(Connection con, String CardKeyId) throws Exception {
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
       
        List<CardBinBean> productionModeList = new ArrayList<CardBinBean>();
        buff.append("SELECT CPM.CODE , CPM.DESCRIPTION");
        buff.append(" FROM ECMS_ONLINE_CARDKEYS CK , ECMS_ONLINE_CARDKEYPROFILE CKP, ECMS_ONLINE_CARDPRODUCTIONMODE CPM ");
        buff.append(" WHERE CK.CARDKEYID = ? AND CK.CARDKEYPROFILEID = CKP.ID ");
        buff.append(" AND CKP.PRODUCTIONMODE = CPM.CODE");       
       
        try {
            ps = con.prepareStatement(buff.toString());
            ps.setString(1, CardKeyId);
            cardBinList = new ArrayList<CardBinBean>();
            rs = ps.executeQuery();
            while (rs.next()) {
                CardBinBean bean = new CardBinBean();
                bean.setProductionMode(rs.getString("CODE"));
                bean.setProductionModeDes(rs.getString("DESCRIPTION"));
               
                productionModeList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return productionModeList;
    }    

 public List<CardBinBean> getCardKeyListByProductionMode(Connection con, String productionMode) throws Exception {
        PreparedStatement ps = null;
        StringBuffer buff = new StringBuffer();
       
        List<CardBinBean> cardKeyList = new ArrayList<CardBinBean>();
        buff.append("SELECT CK.CARDKEYID, CK.DESCRIPTION");
        buff.append(" FROM  ECMS_ONLINE_CARDKEYS CK, ECMS_ONLINE_CARDPRODUCTIONMODE CPM , ECMS_ONLINE_CARDKEYPROFILE CP ");
        buff.append(" WHERE CPM.CODE = CP.PRODUCTIONMODE AND CP.ID = CK.CARDKEYPROFILEID  AND CPM.CODE = ? ");      
       
        try {
            ps = con.prepareStatement(buff.toString());
            ps.setString(1, productionMode);
            cardBinList = new ArrayList<CardBinBean>();
            rs = ps.executeQuery();
            while (rs.next()) {
                CardBinBean bean = new CardBinBean();
                bean.setCardKey(rs.getString("CARDKEYID"));
                bean.setCardKeyDes(rs.getString("DESCRIPTION"));
               
                cardKeyList.add(bean);
            }
        } catch (SQLException ex) {
            throw ex;
        } finally {
            rs.close();
            ps.close();
        }
        return cardKeyList;
    } 
 
}
