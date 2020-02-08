/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.callcenter.card.persistance;

import com.epic.cms.callcenter.card.bean.CardBean;
import com.epic.cms.cpmm.requestconfirmation.bean.RequestConfirmationBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nalin
 */
public class CardActivationPersistance {

    private ResultSet rs;

    public CardBean getCardDetailsToActivation(Connection CMSCon, String cardNumber) throws Exception {
        CardBean cardBean = new CardBean();
        PreparedStatement getCardStat = null;
        try {
            getCardStat = CMSCon.prepareStatement("SELECT "
                    + " C.EXPIERYDATE,C.NAMEONCARD,CC.DESCRIPTION AS CCDESCRIPTION,"
                    + " CT.DESCRIPTION AS CTDESCRIPTION,C.CARDSTATUS,C.CREDITLIMIT"
                    + " FROM CARD C,CARDTYPE CT,CARDCATEGORY CC"
                    + " WHERE CT.CARDTYPECODE=C.CARDTYPE AND CC.CATEGORYCODE=C.CARDCATEGORYCODE AND"
                    + " C.CARDNUMBER=? AND (CARDSTATUS=? OR CARDSTATUS=?)");

            getCardStat.setString(1, cardNumber);
            getCardStat.setString(2, StatusVarList.BLOCK_STATUS);
            getCardStat.setString(3, StatusVarList.INACTIVE_STATUS);

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                cardBean.setCardNumber(cardNumber);
                cardBean.setCardTypeDes(rs.getString("CTDESCRIPTION"));
                cardBean.setCardCatDes(rs.getString("CCDESCRIPTION"));
                cardBean.setExpDate(rs.getString("EXPIERYDATE"));
                cardBean.setNameofCard(rs.getString("NAMEONCARD"));
                cardBean.setCreditLimit(rs.getString("CREDITLIMIT"));
                cardBean.setCardStatus(rs.getString("CARDSTATUS"));
                cardBean.setFlag(true);
            }

        } catch (SQLException ex) {

            throw ex;
        } finally {
            rs.close();
            getCardStat.close();
        }
        return cardBean;
    }

    public RequestConfirmationBean getCardBlockDetails(Connection CMSCon, RequestConfirmationBean cardBean) throws Exception {
        PreparedStatement getCardStat = null;
        try {
            getCardStat = CMSCon.prepareStatement("SELECT C.OLDSTATUS FROM CARDBLOCK C WHERE C.CARDNUMBER=? AND C.STATUS=?");

            getCardStat.setString(1, cardBean.getCardNo());
            getCardStat.setString(2, StatusVarList.ACTIVE_STATUS);

            rs = getCardStat.executeQuery();

            while (rs.next()) {

                cardBean.setCurrentStatus(rs.getString("OLDSTATUS"));

            }

        } catch (SQLException ex) {

            throw ex;
        } finally {
            rs.close();
            getCardStat.close();
        }
        return cardBean;
    }

    public boolean blockCardActivate(Connection CMSCon, RequestConfirmationBean cardBean) throws Exception {
        boolean successUpdate = false;
        PreparedStatement updateStat = null;

        try {

            updateStat = CMSCon.prepareStatement("UPDATE CARD SET CARDSTATUS=?,LASTUPDATEDUSER=?,"
                    + " LASTUPDATEDTIME=SYSDATE,ACTIVATIONDATE=SYSDATE "
                    + " WHERE CARDNUMBER =?");

            updateStat.setString(1, cardBean.getCurrentStatus());
            updateStat.setString(2, cardBean.getLastUpdatedUser());
            updateStat.setString(3, cardBean.getCardNo());

            updateStat.executeUpdate();
            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {

            updateStat.close();
        }

        return successUpdate;

    }

    public boolean cardActivate(Connection CMSCon, RequestConfirmationBean cardBean) throws Exception {
        boolean successUpdate = false;
        PreparedStatement updateStat = null;

        try {

            updateStat = CMSCon.prepareStatement("UPDATE CARD SET CARDSTATUS=?,LASTUPDATEDUSER=?,"
                    + " LASTUPDATEDTIME=SYSDATE, ACTIVATIONDATE=SYSDATE"
                    + " WHERE CARDNUMBER =?");

            updateStat.setString(1, StatusVarList.ACTIVE_STATUS);
            updateStat.setString(2, cardBean.getLastUpdatedUser());
            updateStat.setString(3, cardBean.getCardNo());

            updateStat.executeUpdate();
            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {

            updateStat.close();
        }

        return successUpdate;

    }

    public boolean cardActivateOnline(Connection conOnline, RequestConfirmationBean cardBean) throws Exception {
        boolean successUpdate = false;
        PreparedStatement updateStat = null;

        try {

            updateStat = conOnline.prepareStatement("UPDATE ECMS_ONLINE_CARD"
                    + " SET STATUS=?,LASTUPDATEUSER=?,LASTUPDATETIME=SYSDATE WHERE CARDNUMBER =?");

            updateStat.setInt(1, Integer.parseInt(cardBean.getNewStatusOnline()));
            updateStat.setString(2, cardBean.getLastUpdatedUser());
            updateStat.setString(3, cardBean.getCardNo());

            updateStat.executeUpdate();
            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {

            updateStat.close();
        }

        return successUpdate;

    }

    public boolean requsetApprove(Connection CMSCon, RequestConfirmationBean cardBean, String operation) throws Exception {
        boolean successUpdate = false;
        PreparedStatement ps = null;

        try {

            String updateQuery3 = "UPDATE CARDREQUEST SET STATUS=?,APPROVEDUSER=?,LASTUPDATEDTIME=?,LASTUPDATEDUSER=?,REMARKS=? WHERE CARDNUMBER=? AND REQUESTREASONCODE=? AND STATUS=?";

            ps = CMSCon.prepareStatement(updateQuery3);

            ps.setString(1, StatusVarList.DA_REQUSET_APPROVE);
            ps.setString(2, cardBean.getLastUpdatedUser());
            ps.setTimestamp(3, SystemDateTime.getSystemDataAndTime());
            ps.setString(4, cardBean.getLastUpdatedUser());
            ps.setString(5, cardBean.getRemark());
            
            ps.setString(6, cardBean.getCardNo());
            ps.setString(7, operation);
            ps.setString(8, StatusVarList.DA_REQUSET_INITIATE);

            int result3 = ps.executeUpdate();

            successUpdate = true;

        } catch (SQLException ex) {
            throw ex;
        } finally {

            ps.close();
        }

        return successUpdate;

    }

    public int requsetReject(Connection CMSCon, CardBean cardBean, String operation) throws Exception {
        int result = -1;
        PreparedStatement ps = null;

        try {

            String updateQuery3 = "UPDATE CARDREQUEST SET STATUS=?,APPROVEDUSER=?,LASTUPDATEDTIME=?,LASTUPDATEDUSER=?,REJECTREMARK=? WHERE CARDNUMBER=? AND REQUESTREASONCODE=? AND STATUS=?";

            ps = CMSCon.prepareStatement(updateQuery3);

            ps.setString(1, StatusVarList.DA_REQUSET_REJECT);
            ps.setString(2, cardBean.getLastUpdateduser());
            ps.setTimestamp(3, SystemDateTime.getSystemDataAndTime());
            ps.setString(4, cardBean.getLastUpdateduser());
            ps.setString(5, cardBean.getRemark());
            
            ps.setString(6, cardBean.getCardNumber());
            ps.setString(7, operation);
            ps.setString(8, StatusVarList.DA_REQUSET_INITIATE);

            result = ps.executeUpdate();


        } catch (SQLException ex) {
            throw ex;
        } finally {

            ps.close();
        }

        return result;

    }

    
    public boolean deactivateCardBlockDetails(Connection CMSCon, Connection CMSOnline, RequestConfirmationBean cardBean) throws Exception {
        boolean success = false;
        int row = -1;
        PreparedStatement ps = null;
        try {

//            deleteStat = CMSCon.prepareStatement("DELETE CARDBLOCK WHERE CARDNUMBER=?");
            ps = CMSCon.prepareStatement("UPDATE CARDBLOCK SET STATUS=?, LASTUPDATEDTIME=?, LASTUPDATEDUSER=? WHERE CARDNUMBER=? AND STATUS=? ");

            ps.setString(1, StatusVarList.STATUS_DEACTIVE);
            ps.setTimestamp(2, SystemDateTime.getSystemDataAndTime());
            ps.setString(3, cardBean.getLastUpdatedUser());
            ps.setString(4, cardBean.getCardNo());
            ps.setString(5, StatusVarList.ACTIVE_STATUS);
            row = ps.executeUpdate();

            if (row == 1) {
                row = -1;
                ps = CMSOnline.prepareStatement("UPDATE ECMS_ONLINE_CARD_BLOCK SET STATUS=?, LASTUPDATETIME=?, LASTUPDATEUSER=? WHERE CARDNUMBER=? AND STATUS=?");

                ps.setInt(1, StatusVarList.ONLINE_DEACTIVE_STATUS);
                ps.setTimestamp(2, SystemDateTime.getSystemDataAndTime());
                ps.setString(3, cardBean.getLastUpdatedUser());
                ps.setString(4, cardBean.getCardNo());
                ps.setInt(5, StatusVarList.ONLINE_ACTIVE_STATUS);
                row = ps.executeUpdate();

                if (row == 1) {
                    success = true;
                }
            }

        } catch (SQLException ex) {

            try {
                ex.printStackTrace();
                throw ex;
            } catch (Exception e) {
                ex.printStackTrace();
                throw e;
            }
        } finally {
            ps.close();
        }
        return success;
    }
}
