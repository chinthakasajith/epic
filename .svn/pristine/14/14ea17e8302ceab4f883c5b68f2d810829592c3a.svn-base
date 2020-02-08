package com.epic.cms.backoffice.eodlettergeneration.persistance;

import com.epic.cms.backoffice.eodlettergeneration.bean.CardBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFormatBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class GenerateLetterPersistance {

    private ResultSet rs = null;

    public CardBean getAddress1(Connection cmsCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        CardBean bean = null;

        query = "SELECT ADDRESS1 FROM CARD";

        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean = new CardBean();
                bean.setAddress1(rs.getString("ADDRESS1"));
            }
            return bean;

        } catch (SQLDataException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
    }
    
    public LetterTemplateBean getTemplateFormat(Connection cmsCon, String processCategoryCode) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        LetterTemplateBean bean = null;

        query = "SELECT LT.TEMPLATECODE,LT.TEMPLATEFORMAT,LT.BODY, LT.TITLE,LT.PROCESSCODE "
                + " FROM LETTERTEMPLATE LT"
                + " WHERE LT.PROCESSCODE=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, processCategoryCode);

            rs = ps.executeQuery();
            bean = new LetterTemplateBean();

            while (rs.next()) {

                bean.setTemplateCode(rs.getString("TEMPLATECODE"));
                bean.setTemplateFormat(rs.getString("TEMPLATEFORMAT"));
                bean.setProcessCode(rs.getString("PROCESSCODE"));
                bean.setBody(rs.getString("BODY"));
                bean.setTitle(rs.getString("TITLE"));
            }
            return bean;

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

    public String getDynamicValue(Connection cmsCon, String tableName, String fieldName, String cardNo) throws SQLException, Exception {

        PreparedStatement ps = null;
        String query;
        String dynamicValue = null;

        query = "SELECT " + fieldName + " AS DYNAMICVALUE FROM " + tableName + " WHERE CARDNUMBER=" + cardNo;

        try {

            ps = cmsCon.prepareStatement(query);



            rs = ps.executeQuery();

            while (rs.next()) {

                dynamicValue = rs.getString("DYNAMICVALUE");
            }

            return dynamicValue;

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

    public List<String> getTableValues(Connection cmsCon, String fieldName) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        List<String> dynamicValues = null;
        query = "SELECT TABLEFIELD, TABLENAME FROM LETTERFIELDDETAILS WHERE FIELDNAME=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, fieldName);

            rs = ps.executeQuery();
            dynamicValues = new ArrayList<String>();

            while (rs.next()) {
                dynamicValues.add(rs.getString("TABLENAME"));
                dynamicValues.add(rs.getString("TABLEFIELD"));
            }

            return dynamicValues;

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

    public LetterFormatBean getLetterFormat(Connection cmsCon, String formatCode) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        LetterFormatBean letterFormatBean = null;
        query = "SELECT DESCRIPTION, ALIGNMENT, FONTTYPE, FONTSTYLE,FONTSIZE FROM LETTERFORMATS WHERE FORMATCODE=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, formatCode);

            rs = ps.executeQuery();

            while (rs.next()) {

                letterFormatBean = new LetterFormatBean();

                letterFormatBean.setDescription(rs.getString("DESCRIPTION"));
                letterFormatBean.setAllignment(rs.getString("ALIGNMENT"));
                letterFormatBean.setFontType(rs.getString("FONTTYPE"));
                letterFormatBean.setFontStyle(rs.getString("FONTSTYLE"));
                letterFormatBean.setSize(rs.getString("FONTSIZE"));


            }
            return letterFormatBean;

        } catch (SQLException sqlEx) {
            throw sqlEx;
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

    public int updateTable(Connection cmsCon, String cardNumber) throws SQLException, Exception {
        int rowCount = -1;
        PreparedStatement ps = null;
        String query;

        query = "UPDATE CARDRENEW SET LETTERGENERATIONSTATUS='YES' WHERE CARDNUMBER=?";

        try {

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, cardNumber);

            rowCount = ps.executeUpdate();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }

    public int updateAllLetterGeneratedCards(Connection cmsCon, String cardNumbers) throws SQLException, Exception {
        int rowCount = -1;
        PreparedStatement ps = null;
        String query;

        query = "UPDATE CARDRENEW SET LETTERGENERATIONSTATUS='YES' WHERE CARDNUMBER IN (" + cardNumbers +  ")";

        try {

            ps = cmsCon.prepareStatement(query);
//            ps.setString(1, cardNumbers);

            rowCount = ps.executeUpdate();

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return rowCount;
    }
}
