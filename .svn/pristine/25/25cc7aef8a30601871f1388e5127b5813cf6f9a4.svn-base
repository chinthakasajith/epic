/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.eodlettergeneration.businesslogic;

import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFieldDetailsBean;
import com.epic.cms.backoffice.eodlettergeneration.persistance.LetterGenerationPersistance;
import com.epic.cms.backoffice.eodlettergeneration.service.LetterBodyTransformService;
import com.epic.cms.system.util.config.DBConnection;
import com.epic.cms.templatemgt.lettertamplate.bean.LetterBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author chinthaka_r
 */
public class LetterGenerationManager {

    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;
    private LetterGenerationPersistance letterGenPersistance;
    LetterBodyTransformService letterBodyTransformService;
    private List<LetterDetailsBean> searchedletterList;

    //get parameter values to fill letter template
    public LetterFieldDetailsBean getLetterParamValues(String letterRefNum) throws Exception {
        try {
            LetterFieldDetailsBean letterParamBean = null;
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            letterParamBean = letterGenPersistance.getLetterParamValues(cmsCon, letterRefNum);
            cmsCon.commit();
            return letterParamBean;

        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }

    }

    //get selected letter template details
    public LetterBean viewSelectedLetterConf(String tmpCode) throws Exception {
        try {
            LetterBean letterConfBean = null;
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            letterConfBean = letterGenPersistance.viewSelectedLetterConf(cmsCon, tmpCode);
            cmsCon.commit();
            return letterConfBean;

        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    //get dinamic values
    public String getDynamicValue(String tableName, String fieldName,String idColumnName, String identificationNum) throws SQLException, Exception {
        try {
            String dynamicValues = null;
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            dynamicValues = letterGenPersistance.getDynamicValue(cmsCon, tableName, fieldName,idColumnName, identificationNum);
            cmsCon.commit();

            return dynamicValues;

        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    public String generateLetter(LetterBean letterBean, String delimeter, Map<String, String> parameters) throws Exception {
        letterBodyTransformService = new LetterBodyTransformService();
        String letterMessageBody = null;

        //set the message body
        if (delimeter != null && delimeter != "" && parameters != null) {

            String parameterAddedMessageBody = letterBodyTransformService.transformLetterBodyTokenToParamer(letterBean.getBody(), delimeter, parameters);
            letterMessageBody = parameterAddedMessageBody;

        } else {

            letterMessageBody = letterBean.getBody();

        }
        return letterMessageBody;
    }

    //save generated letters in LETTERDETAILS table
    public int saveGeneratedLetters(Map<String, String> letterList) throws Exception {
        int count = 0;
        try {
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            count = letterGenPersistance.saveGeneratedLetters(cmsCon, letterList);
            cmsCon.commit();
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return count;

    }
    //get all generated letters details

    public List<LetterDetailsBean> getAllLetterDetails() throws Exception {
        try {
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            searchedletterList = letterGenPersistance.getAllLetterDetails(cmsCon);
            cmsCon.commit();
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return searchedletterList;
    }

    //store details about letters have to be generated
    public int saveLetterDetailsHaveToBeGenerated(LetterDetailsBean bean) throws Exception {
        int i = -1;
        try {
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            String uniqueRefId = this.generateRefNum();

            i = letterGenPersistance.saveLetterDetailsHaveToBeGenerated(cmsCon, bean, uniqueRefId);

            cmsCon.commit();
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return 1;
    }

    //get tmpCode from refNumber
    public List<String> getTmpCodeAndIdFromRefNumber(String refNum) throws Exception {
        List<String> tmpCodAndId = null;
        try {
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            tmpCodAndId = letterGenPersistance.getTemplateCodeAndIdFromLetterRefNumber(cmsCon, refNum);
            cmsCon.commit();
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return tmpCodAndId;
    }

    //get all field detailsNames from letterFieldDetails table
    public List<String> getAllFieldNamesFromLetterFieldDetails() throws Exception {
        List<String> fieldNames = null;
        try {
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);
            fieldNames = letterGenPersistance.getAllFieldNamesFromLetterFieldDetails(cmsCon);
            cmsCon.commit();
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return fieldNames;
    }

    public List<String> getTableValues(String fieldName) throws SQLException, Exception {
        try {
            List<String> tableVal = null;
            letterGenPersistance = new LetterGenerationPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            tableVal = letterGenPersistance.getTableValues(cmsCon, fieldName);
            cmsCon.commit();

            return tableVal;
        } catch (SQLException sqlEx) {
            try {
                cmsCon.rollback();
                throw sqlEx;
            } catch (SQLException e) {
                throw e;
            }
        } catch (Exception ex) {
            try {
                cmsCon.rollback();
                throw ex;
            } catch (Exception e) {
                throw e;
            }
        } finally {
            if (cmsCon != null) {
                try {
                    cmsCon.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
    }

    //generate sequence number for refNum
    public String generateRefNum() throws Exception {
        String uniqueRefId = null;
        try {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyMMdd");
            String today = ft.format(dNow);
            int random = this.getRandomNumberInRange(100000, 999999);
            uniqueRefId = today + String.valueOf(random);
        } catch (Exception ex) {
            throw ex;
        }
        return uniqueRefId;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
