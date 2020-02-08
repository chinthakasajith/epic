package com.epic.cms.backoffice.eodlettergeneration.businesslogic;

import com.epic.cms.backoffice.eodlettergeneration.bean.CardBean;
import com.epic.cms.admin.controlpanel.sysusermgt.businesslogic.SystemAuditManager;
import com.epic.cms.backoffice.eodlettergeneration.bean.CardRenewalBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterFormatBean;
import com.epic.cms.backoffice.eodlettergeneration.bean.LetterTemplateBean;
import com.epic.cms.backoffice.eodlettergeneration.persistance.GenerateLetterPersistance;
import com.epic.cms.system.util.config.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class GenerateLetterManager {

    private Connection cmsCon;
    private SystemAuditManager sysAuditManager;
    private GenerateLetterManager genLetterMgr;
    private GenerateLetterPersistance genLetterPersistance;

    public CardBean getAddress1() throws SQLException, Exception {
        try {
            CardBean address1 = null;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            address1 = genLetterPersistance.getAddress1(cmsCon);
            cmsCon.commit();

            return address1;
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

    public LetterTemplateBean getTemplateFormat(String processCategoryCode) throws SQLException, Exception {
        try {
            LetterTemplateBean lettetTemplateBean = null;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            lettetTemplateBean = genLetterPersistance.getTemplateFormat(cmsCon, processCategoryCode);
            cmsCon.commit();

            return lettetTemplateBean;
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

    public List<String> getTableValues(String fieldName) throws SQLException, Exception {
        try {
            List<String> tableVal = null;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            tableVal = genLetterPersistance.getTableValues(cmsCon, fieldName);
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

    public String getDynamicValue(String tableName, String fieldName, String processCategoryCode) throws SQLException, Exception {
        try {
            String dynamicValues = null;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            dynamicValues = genLetterPersistance.getDynamicValue(cmsCon, tableName, fieldName, processCategoryCode);
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

    public LetterFormatBean getLetterFormat(String formatCode) throws SQLException, Exception {
        try {
            LetterFormatBean letterFormatBean = null;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            letterFormatBean = genLetterPersistance.getLetterFormat(cmsCon, formatCode);
            cmsCon.commit();

            return letterFormatBean;

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

    public int updateTable(String cardNumber) throws SQLException, Exception {
        try {
            int rowCount = -1;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = genLetterPersistance.updateTable(cmsCon, cardNumber);
            cmsCon.commit();

            return rowCount;

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

    public int updateAllLetterGeneratedCards(String cardNumbers) throws SQLException, Exception {
        try {
            int rowCount = -1;
            genLetterPersistance = new GenerateLetterPersistance();
            cmsCon = DBConnection.getConnection();
            cmsCon.setAutoCommit(false);

            rowCount = genLetterPersistance.updateAllLetterGeneratedCards(cmsCon, cardNumbers);
            cmsCon.commit();

            return rowCount;

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
}
