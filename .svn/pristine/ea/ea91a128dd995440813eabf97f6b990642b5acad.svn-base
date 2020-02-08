package com.epic.cms.admin.controlpanel.systemconfigmgt.persistance;

import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.BankBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineBaseDataBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCountryCodeBean;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.OnLineCurrencyCodeBean;
import com.epic.cms.application.common.bean.StatusBean;
import com.epic.cms.system.util.variable.StatusVarList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class OnlineBasePersistence {

    ResultSet rs = null;

    public List<OnLineCurrencyCodeBean> getCurrenceDesc(Connection cmsCon) throws Exception {
        ArrayList<OnLineCurrencyCodeBean> allCurrency = new ArrayList<OnLineCurrencyCodeBean>();
        PreparedStatement ps = null;

        try {

//            String query = "SELECT C.NOCODE, C.DESCRIPTION FROM ECMS_ONLINE_CURRENCY_CODE C";
            String query = "SELECT C.NOCODE, C.DESCRIPTION FROM ECMS_ONLINE_CURRENCY_CODE C ORDER BY C.DESCRIPTION ASC";
            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                OnLineCurrencyCodeBean bean = new OnLineCurrencyCodeBean();
                bean.setCurrencyNCode(rs.getString("NOCODE"));
                bean.setCurrenceDesc(rs.getString("DESCRIPTION"));

                allCurrency.add(bean);
            }
            return allCurrency;


        } catch (SQLException sqx) {
            throw sqx;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public List<OnLineCountryCodeBean> getBaseCountry(Connection cmsCon) throws Exception {
        List<OnLineCountryCodeBean> allCountry = new ArrayList<OnLineCountryCodeBean>();
        PreparedStatement ps = null;

        try {

//            String query = "SELECT C.CODE, C.DESCRIPTION FROM ECMS_ONLINE_COUNTRY_CODE C";
            String query = "SELECT C.CODE, C.DESCRIPTION FROM ECMS_ONLINE_COUNTRY_CODE C ORDER BY C.DESCRIPTION ASC";
            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                OnLineCountryCodeBean bean = new OnLineCountryCodeBean();
                bean.setCountryCode(rs.getString("CODE"));
                bean.setCountryCodeDesc(rs.getString("DESCRIPTION"));

                allCountry.add(bean);
            }
            return allCountry;

        } catch (SQLException sqx) {
            throw sqx;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public OnLineBaseDataBean getOnLineBaseData(Connection cmsCon,Connection backendConn) throws Exception {
//        List<OnLineBaseDataBean> baseDataBeans = new ArrayList<OnLineBaseDataBean>();
        PreparedStatement ps = null;
        ResultSet backend_rs = null;
        PreparedStatement backend_ps = null;

        try {
            OnLineBaseDataBean baseDataBeans = new OnLineBaseDataBean();
            String query = "SELECT D.COUNTRYCODE, D.CURRENCYCODE  FROM ECMS_ONLINE_BASE_DATA D";
            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                
                baseDataBeans.setCountryCode(rs.getString("COUNTRYCODE"));
                baseDataBeans.setCurrencyCode(rs.getString("CURRENCYCODE"));

            }
            
            String backend_query = "SELECT TERMINALID, MERCHANTID  FROM COMMONPARAMETER";
            backend_ps = backendConn.prepareStatement(backend_query);
            
            backend_rs = backend_ps.executeQuery();
            
            while (backend_rs.next()) {
                
                baseDataBeans.setTerminalId(backend_rs.getString("TERMINALID"));
                baseDataBeans.setMerchantId(backend_rs.getString("MERCHANTID"));

            }
            
            return baseDataBeans;

        } catch (SQLException sqx) {
            throw sqx;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
            if (backend_rs != null) {
                try {
                    backend_rs.close();
                    backend_ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

//    public int addNewBank(Connection cmsCon, BankBean bean) throws Exception {
//        int i = -1;
//        PreparedStatement ps = null;
//        try {
//
//            String query = "INSERT INTO BANK (BANKCODE,BANKNAME,STATUS,CREATEDDATE,LASTUPDATEDUSER,LASTUPDATEDDATE) VALUES(?,?,?,SYSDATE,?,?)";
//
//            ps = cmsCon.prepareStatement(query);
//            ps.setString(1, bean.getBankCode());
//            ps.setString(2, bean.getBankName());
//            ps.setString(3, bean.getStatusDes());
//            ps.setString(4, bean.getLastUpdatedUser());
//            ps.setTimestamp(5, new Timestamp(bean.getLastUpdatedDate().getTime()));
//
//            i = ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            throw ex;
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (Exception e) {
//                throw e;
//            }
//        }
//        return i;
//    }
    public boolean  saveConfigData(Connection cmsCon,Connection backendConn, OnLineBaseDataBean bean) throws Exception {
       boolean status = false;
        PreparedStatement ps = null;
        PreparedStatement ps_backend = null;
        String query,query_backend = null;
        try {
            query = "INSERT INTO ECMS_ONLINE_BASE_DATA(COUNTRYCODE,CURRENCYCODE) VALUES(?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getCountryCode());
            ps.setString(2, bean.getCurrencyCode());

            ps.executeUpdate();
            
            query_backend = "INSERT INTO COMMONPARAMETER(TERMINALID,MERCHANTID) VALUES(?,?)";

            ps_backend = backendConn.prepareStatement(query_backend);
            ps_backend.setString(1, bean.getTerminalId());
            ps_backend.setString(2, bean.getMerchantId());

            ps_backend.executeUpdate();
            
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps_backend != null) {
                    ps_backend.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        return status;
    }        
    
    
       public boolean  updateConfigData(Connection cmsCon,Connection backendConn, OnLineBaseDataBean bean) throws Exception {
       boolean status = false;
        PreparedStatement ps = null;
        PreparedStatement ps_backend = null;
        String query,query_backend = null;
        try {
            query = "UPDATE ECMS_ONLINE_BASE_DATA SET COUNTRYCODE =?,CURRENCYCODE =?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getCountryCode());
            ps.setString(2, bean.getCurrencyCode());

            ps.executeUpdate();
            
            query_backend = "UPDATE COMMONPARAMETER SET TERMINALID =?,MERCHANTID =?";

            ps_backend = backendConn.prepareStatement(query_backend);
            ps_backend.setString(1, bean.getTerminalId());
            ps_backend.setString(2, bean.getMerchantId());

            ps_backend.executeUpdate();
            
            status = true;
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps_backend != null) {
                    ps_backend.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        return status;
    }

    public boolean isRecordAvailable(Connection cmsCon) throws Exception {
//        List<OnLineBaseDataBean> baseDataBeans = new ArrayList<OnLineBaseDataBean>();
        PreparedStatement ps = null;    
        OnLineBaseDataBean baseDataBeans = null;    
        boolean status = false;
        int x = 0;
        try {         

            String query = "SELECT COUNT(*) AS COUNT FROM ECMS_ONLINE_BASE_DATA";
            ps = cmsCon.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {          
                x = rs.getInt("COUNT");
            }   

            if (x <= 0) {        
                status = false;
            } else {     
                status = true;
            }    
          
        } catch (SQLException sqx) { 
            throw sqx;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        return status;
    }
}
