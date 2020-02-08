/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.admin.camm.numbergeneration.businesslogic;

import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberFormatFieldBean;
import com.epic.cms.admin.camm.numbergeneration.bean.NumberGenerationFormatBean;
import com.epic.cms.admin.camm.numbergeneration.persistance.NumberGeneFormatPersistance;
import com.epic.cms.admin.controlpanel.systemconfigmgt.bean.CardTypeMgtBean;
import com.epic.cms.admin.controlpanel.sysusermgt.bean.SystemAuditBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author upul
 */
public class NumberGeneFormatBusinessLogic {

    private Connection CMSCon;
    private SystemAuditManager sysAuditManager;
    private NumberGeneFormatPersistance formatPersistance;
    private HashMap<String, String> UsedBinList = null;
    private HashMap<String, String> AssignBinList = null;
    private HashMap<String, String> NotAssignBinList = null;
    private HashMap<String, String> cardProcuctBinList = null;
    private HashMap<String, String> preAssignBinList = null;

    /**
     * getNumberFormatFieldDetails from persistance
     * @return
     * @throws Exception 
     */
    public List<NumberFormatFieldBean> getNumberFormatFieldDetails() throws Exception {

        List<NumberFormatFieldBean> formatFieldBeans = new ArrayList<NumberFormatFieldBean>();
        try {


            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //set get data values to list
            formatFieldBeans = formatPersistance.getNumberFormatFields(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return formatFieldBeans;
    }

    /**
     * getNumberFormatFieldDetails
     * @return
     * @throws Exception 
     */
    public List<NumberFormatFieldBean> getNumberFormatCodeDetails() throws Exception {

        List<NumberFormatFieldBean> formatFieldBeans = new ArrayList<NumberFormatFieldBean>();
        try {


            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //set get data values to list
            formatFieldBeans = formatPersistance.getNumberFormatCodeList(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return formatFieldBeans;
    }

    /**
     * getNumberFormatCodeDetails(String formCode)
     * @return
     * @throws Exception 
     */
    public NumberFormatFieldBean getSelectedNumberFormatCode(String formCode) throws Exception {

        NumberFormatFieldBean formatFieldBean = new NumberFormatFieldBean();
        try {


            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //set get data values to list
            formatFieldBean = formatPersistance.getSelectedNumberFormatCode(CMSCon, formCode);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return formatFieldBean;
    }

    /**
     * getCardTypeDetails
     * @return
     * @throws Exception 
     */
    public List<CardTypeMgtBean> getCardTypeDetails() throws Exception {

        List<CardTypeMgtBean> formatFieldBeans = new ArrayList<CardTypeMgtBean>();
        try {


            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //set get data values to list
            formatFieldBeans = formatPersistance.getCardTypes(CMSCon);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return formatFieldBeans;
    }

    /**
     * getSelectedNumberFormatFields
     * @return
     * @throws Exception 
     */
    public List<NumberFormatBean> getSelectedNumberFormatFields(String formatCode) throws Exception {

        List<NumberFormatBean> formatBeans = new ArrayList<NumberFormatBean>();
        try {


            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            //set get data values to list
            formatBeans = formatPersistance.getSelectedNumberFormatFields(CMSCon, formatCode);


            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            DBConnection.dbConnectionClose(CMSCon);
        }
        return formatBeans;
    }

    /**
     * insertNumberFormatFieldRecord
     * @param systemAuditBean
     * @param numFormatFieldBean
     * @param numFormatBeanList
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int insertNumberFormatFieldRecord(SystemAuditBean systemAuditBean, NumberGenerationFormatBean numFormatFieldBean, List<NumberFormatBean> numFormatBeanList, String numFormatCode, String assignBin[]) throws SQLException, Exception {
        try {
            int row = -1;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            formatPersistance = new NumberGeneFormatPersistance();

            row = formatPersistance.insertNumberFormatFieldRecord(CMSCon, numFormatFieldBean, numFormatBeanList, numFormatCode, assignBin);


            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            return row;

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

    }

    /**
     * updateNumberFormatCodeDetail
     * @param systemAuditBean
     * @param numFormatFieldBean
     * @param numFormatBeanList
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int updateNumberFormatCodeDetail(SystemAuditBean systemAuditBean, NumberGenerationFormatBean numFormatFieldBean, List<NumberFormatBean> numFormatBeanList, String numFormatCode, String assignBin[]) throws SQLException, Exception {
        try {
            int row = -1;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            formatPersistance = new NumberGeneFormatPersistance();

            row = formatPersistance.updateNumberFormatCodeDetail(CMSCon, numFormatFieldBean, numFormatBeanList, numFormatCode, assignBin);


            sysAuditManager = new SystemAuditManager();
             sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            return row;

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

    }

    /**
     * 
     * @param systemAuditBean
     * @param numFormatFieldBean
     * @param numFormatBeanList
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public int deleteNumberFormatCodeDetail(SystemAuditBean systemAuditBean, String formatCode) throws SQLException, Exception {
        try {
            int row = -1;

            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            formatPersistance = new NumberGeneFormatPersistance();

            row = formatPersistance.deleteNumberFormatCodeDetail(CMSCon, formatCode);

            sysAuditManager = new SystemAuditManager();
            sysAuditManager.insertAudit(systemAuditBean, CMSCon);
            CMSCon.commit();
            return row;

        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
    }

    public HashMap<String, String> getBinToCardType(String cardType) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            NotAssignBinList = formatPersistance.getBinToCardType(CMSCon, cardType);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return NotAssignBinList;
    }

    /**
     * to retrieve the BINs which are not assigned to the given number format code
     * @param cardType
     * @param numFormatCode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getNotAssignBinList(String cardType, String numFormatCode) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            NotAssignBinList = formatPersistance.getNotAssignBinList(CMSCon, cardType, numFormatCode);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return NotAssignBinList;
    }

    /**
     * to retrieve the BINs which are assigned to the given number format code
     * @param numFormatCode
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getAssignBinList(String numFormatCode) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            AssignBinList = formatPersistance.getAssignBinList(CMSCon, numFormatCode);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return AssignBinList;
    }
    
    public HashMap<String, String> getAssignBinListOnCardTypeReset(String numFormatCode) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            preAssignBinList = formatPersistance.getAssignBinListOnCardTypeReset(CMSCon, numFormatCode);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return preAssignBinList;
    }


    /**
     * to retrieve the BINs for the given card type
     * @param cardType
     * @return
     * @throws Exception 
     */
    public HashMap<String, String> getCardProductBins(String cardType) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            cardProcuctBinList = formatPersistance.getCardProductBins(CMSCon, cardType);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return cardProcuctBinList;
    }
    
    public HashMap<String, String> getSeqNoForUsedBINs(String numFormatCode) throws Exception {
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);
            UsedBinList = formatPersistance.getSeqNoForUsedBINs(CMSCon, numFormatCode);

            CMSCon.commit();
        } catch (Exception ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (SQLException sqx) {
                throw sqx;
            }
        } finally {
            if (CMSCon != null) {
                try {
                    CMSCon.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }
        return UsedBinList;
    }

    public HashMap<String, String> getProductionModes() throws Exception {

        HashMap<String, String> prodModes;

        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            prodModes = formatPersistance.getProductionModes(CMSCon);

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            CMSCon.close();
        }
        return prodModes;
    }

    public int getSequenceNumber(String formatCode) throws Exception {
        String sequenceNo = "";
        int sequenceNoLength;
        try {
            formatPersistance = new NumberGeneFormatPersistance();
            CMSCon = DBConnection.getConnection();
            CMSCon.setAutoCommit(false);

            sequenceNo = formatPersistance.getSequenceNumber(CMSCon, formatCode);
            sequenceNoLength = sequenceNo.length();

            CMSCon.commit();

        } catch (SQLException ex) {
            try {
                CMSCon.rollback();
                throw ex;
            } catch (Exception e) {
                CMSCon.rollback();
                throw ex;
            }
        } finally {
            CMSCon.close();
        }
        return sequenceNoLength;
    }
}
