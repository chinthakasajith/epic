package com.epic.cms.backoffice.eodlettergeneration.persistance;

import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterGenerationBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class SearchLetterGenerationPersistance {

    ResultSet rs = null;
    private List<CardRenewalBean> searchList = null;
    private List<LetterDetailsBean> searchedletterList = null;

    public List<LetterGenerationBean> getProcessCategory(Connection cmsCon) throws SQLException, Exception {
        List<LetterGenerationBean> procassList = new ArrayList<LetterGenerationBean>();
        PreparedStatement ps = null;
        String query;

        query = "SELECT PROCESSID, DESCRIPTION FROM EODPROCESS WHERE LETTERGENARATIONSTATUS='YES' ORDER BY DESCRIPTION";

        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                LetterGenerationBean bean = new LetterGenerationBean();
                bean.setProcessId(rs.getString("PROCESSID"));
                bean.setProcessDescription(rs.getString("DESCRIPTION"));

                procassList.add(bean);
            }
            return procassList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    //get all letter templates

    public List<LetterTemplateBean> getAllLetterTemplates(Connection con) throws Exception {

        List<LetterTemplateBean> tmpList = new ArrayList<LetterTemplateBean>();
        PreparedStatement ps = null;
        String query = "SELECT TEMPLATECODE,DESCRIPTION FROM LETTERTEMPLATE WHERE STATUS='ACT' ORDER BY DESCRIPTION";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                LetterTemplateBean bean = new LetterTemplateBean();
                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));

                tmpList.add(bean);
            }
            return tmpList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    public List<CardRenewalBean> searchLetterGeneration(Connection cmsCon, CardRenewalBean searchBean) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query = null;

        searchList = new ArrayList<CardRenewalBean>();
        try {

            query = "SELECT CA.CARDNUMBER, CA.EXPIRYDATE, ST1.DESCRIPTION, CA.RENEWALCONFIRMATIONDATE, ST2.DESCRIPTION AS PINGENERATIONSTATUS, CA.LASTUPDATEDTIME ,CA.CREATEDDATE, CA.LETTERGENERATIONSTATUS "
                    + " FROM CARDRENEW CA, STATUS ST1, STATUS ST2"
                    + " WHERE CA.STATUS=ST1.STATUSCODE"
                    + " AND LETTERGENERATIONSTATUS = 'NO' "
                    + " AND ST2.STATUSCODE=CA.PINGENERATIONSTATUS ";

            String condition = "";

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "CA.CREATEDDATE >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd') AND CA.CREATEDDATE >= to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {
                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CA.CREATEDDATE >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "CA.CREATEDDATE <= to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }

            if (!condition.equals("")) {
                query += "AND " + condition;
            }

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                CardRenewalBean bean = new CardRenewalBean();

                bean.setCardNumber(rs.getString("CARDNUMBER"));
                bean.setExpiryDate(rs.getString("EXPIRYDATE"));
                bean.setStatus(rs.getString("DESCRIPTION"));
                bean.setRenewalConfirmationDate(rs.getDate("RENEWALCONFIRMATIONDATE"));
                bean.setPinGenerationStatus(rs.getString("PINGENERATIONSTATUS"));

                bean.setFromDate(rs.getString("LASTUPDATEDTIME"));
                bean.setToDate(rs.getString("LASTUPDATEDTIME"));

                bean.setLetterGenerationStatus(rs.getString("LETTERGENERATIONSTATUS"));
                searchList.add(bean);

            }
            return searchList;

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    //search letters to generate from letterdetails table

    public List<LetterDetailsBean> searchLettersToGenerate(Connection cmsCon, LetterDetailsBean searchBean) throws Exception {
        PreparedStatement ps = null;
        String query = null;
        searchedletterList = new ArrayList<LetterDetailsBean>();

        try {
            query = "SELECT LD.IDTYPE,LD.ID,LD.TEMPLATECODE,LD.REFNUMBER,LD.STATUS,LD.LASTUPDATEDDATE,LT.DESCRIPTION AS TMPDES,ST.DESCRIPTION AS STDES "
                    + "FROM LETTERDETAILS LD "
                    + "JOIN LETTERTEMPLATE LT ON LD.TEMPLATECODE=LT.TEMPLATECODE "
                    + "JOIN STATUS ST ON LD.STATUS=ST.STATUSCODE "
                    + "WHERE LD.STATUS='NO' ";
            
            String condition = "";
            
            if(!searchBean.getTmpCode().equals("")){
                if(!condition.equals("")){
                    condition+=" AND ";
                }
                condition+="LD.TEMPLATECODE='"+ searchBean.getTmpCode() +"'";
            }
            if(!searchBean.getFromDate().equals("") && !searchBean.getToDate().equals("")){
                if(!condition.equals("")){
                    condition+=" AND ";
                }
                condition+="LD.LASTUPDATEDDATE >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd') AND LD.LASTUPDATEDDATE <= to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')";
            }else{
                if(!searchBean.getFromDate().equals("")){
                    if(!condition.equals("")){
                        condition+=" AND ";
                    }
                    condition+="LD.LASTUPDATEDDATE >= to_Date('" + this.stringToDate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if(!searchBean.getToDate().equals("")){
                    if(!condition.equals("")){
                        condition+=" AND ";
                    }
                    condition+="LD.LASTUPDATEDDATE <= to_Date('" + this.stringToDate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }
            if(!condition.equals("")){
                query+="AND "+condition;
            }
            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {                
                LetterDetailsBean bean=new LetterDetailsBean();
                bean.setId(rs.getString("ID"));
                bean.setIdType(rs.getString("IDTYPE"));
                bean.setRefNum(rs.getString("REFNUMBER"));
                bean.setTmpCode(rs.getString("TEMPLATECODE"));
                bean.setTmpDes(rs.getString("TMPDES"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setStatusDes(rs.getString("STDES"));
                
                searchedletterList.add(bean);
            }
            return searchedletterList;
            
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }

    }

    private String stringToDate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date convertedDate = dateFormat.parse(dateString);

        return (dateFormat.format(convertedDate));

    }
}
