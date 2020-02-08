/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadDetailsBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.FileDownloadSearchBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chanuka
 */
public class FileDownloadMgtPersistance {

    private List<FileDownloadDetailsBean> commomFileDownloadList = null;

    public List<FileDownloadDetailsBean> getAllFilesToDownload(Connection con, FileDownloadSearchBean searchBean) throws Exception {
        ResultSet rs = null;
        PreparedStatement getAllMasterCard = null;
        String strSqlBasic = null;
        try {




            strSqlBasic = "SELECT DF.FIETYPE,DF.FILENAME,DF.LETTERTYPE,DF.STATUS,DF.GENERATEDUSER,DF.STATEMENTMONTH,"
                    + "DF.STATEMENTYEAR,DF.CARDTYPE,CT.DESCRIPTION AS CARDTYPEDES,DF.CARDPRODUCT,CP.DESCRIPTION AS CARDPRODUCTDES,"
                    + "DF.CREATEDTIME FROM DOWNLOADFILE DF,CARDTYPE CT,CARDPRODUCT CP ";



            String condition = "";


            if (!searchBean.getFileType().contentEquals("") || searchBean.getFileType().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.FIETYPE = '" + searchBean.getFileType() + "'";
            }

            if (!searchBean.getFileName().contentEquals("") || searchBean.getFileName().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.FILENAME LIKE '%" + searchBean.getFileName().toUpperCase() + "%'";
            }


            if (!searchBean.getGenerateUser().contentEquals("") || searchBean.getGenerateUser().length() > 0) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.GENERATEDUSER = '" + searchBean.getGenerateUser() + "'";
            }

            if (!searchBean.getFromDate().contentEquals("") && !searchBean.getToDate().contentEquals("")) {
                if (!condition.equals("")) {
                    condition += " AND ";
                }
                condition += "DF.CREATEDTIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd') AND DF.CREATEDTIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
            } else {

                if (!searchBean.getFromDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "DF.CREATEDTIME >= to_Date('" + this.stringTodate(searchBean.getFromDate()) + "','yy-mm-dd')";
                }
                if (!searchBean.getToDate().contentEquals("")) {
                    if (!condition.equals("")) {
                        condition += " AND ";
                    }
                    condition += "DF.CREATEDTIME <= to_Date('" + this.stringTodate(searchBean.getToDate()) + "','yy-mm-dd')";
                }
            }



            if (!condition.equals("")) {

                strSqlBasic += " WHERE DF.CARDTYPE = CT.CARDTYPECODE AND DF.CARDPRODUCT = CP.PRODUCTCODE AND " + condition + " ORDER BY DF.FILENAME";

            } else {

                strSqlBasic += " WHERE DF.CARDTYPE = CT.CARDTYPECODE AND DF.CARDPRODUCT = CP.PRODUCTCODE ORDER BY DF.FILENAME";
            }



            getAllMasterCard = con.prepareStatement(strSqlBasic);


            rs = getAllMasterCard.executeQuery();
            commomFileDownloadList = new ArrayList<FileDownloadDetailsBean>();

            while (rs.next()) {

                FileDownloadDetailsBean resultBean = new FileDownloadDetailsBean();

                resultBean.setFileType(rs.getString("FIETYPE"));
                resultBean.setFileName(rs.getString("FILENAME"));
                resultBean.setCardType(rs.getString("CARDTYPE"));
                resultBean.setCardTypeDes(rs.getString("CARDTYPEDES"));
                resultBean.setCardProduct(rs.getString("CARDPRODUCT"));
                resultBean.setCardProductDes(rs.getString("CARDPRODUCTDES"));
                resultBean.setGeneratedUser(rs.getString("GENERATEDUSER"));
                resultBean.setLetterType(rs.getString("LETTERTYPE"));
                resultBean.setStatementMonth(rs.getString("STATEMENTMONTH"));
                resultBean.setStatementYear(rs.getString("STATEMENTYEAR"));
                resultBean.setCreateTime(rs.getString("CREATEDTIME"));


                commomFileDownloadList.add(resultBean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            rs.close();
            getAllMasterCard.close();

        }

        return commomFileDownloadList;
    }

    //convert string to Date type
    private String stringTodate(String date) throws ParseException {
        String dateString = date;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date convertedDate = dateFormat.parse(dateString);


        return (dateFormat.format(convertedDate));



    }
}
