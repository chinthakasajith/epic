package com.epic.cms.backoffice.filemgt.persistance;

import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.cpmm.cardembossing.bean.CommonFilePathBean;
import com.epic.cms.system.util.datetime.SystemDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class FileInfoPersistance {

    ResultSet rs = null;
    private CommonFilePathBean commonFilePathBean = null;
    private List<FileInfoBean> fileInfoList = null;
    Connection cmsCon;

    public int insertSeperateFileInfo(Connection con, FileInfoBean fileInfoBean) throws Exception {
        int resultId = -1;
        String fileType = null;
        String newFileId = null;
        PreparedStatement insertSt = null;
        String dateId = null;

        try {

            fileType = fileInfoBean.getFileType();

            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
            String date = formatter.format(Calendar.getInstance().getTime());
            dateId = this.getDateId(date);

            if (fileType.equals("VBASE")) {
                insertSt = con.prepareStatement("INSERT INTO INCOMINGVISABINFILE (FILEID,FILENAME,STATUS,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATETIME, FILETYPE) VALUES (?,?,?,?,SYSDATE,SYSDATE,?)");

                newFileId = "VB" + dateId;
            }
            if (fileType.equals("VBIN")) {
                insertSt = con.prepareStatement("INSERT INTO INCOMINGVISABINFILE (FILEID,FILENAME,STATUS,LASTUPDATEDUSER,LASTUPDATEDDATE,CREATETIME, FILETYPE) VALUES (?,?,?,?,SYSDATE,SYSDATE,?)");

                newFileId = "VB" + dateId;
            }

            insertSt.setString(1, newFileId);
            insertSt.setString(2, fileInfoBean.getFileName());
            insertSt.setString(3, fileInfoBean.getStatus());
            insertSt.setString(4, fileInfoBean.getLastUpdatedUser());
            insertSt.setString(5, fileInfoBean.getFileType());

            resultId = insertSt.executeUpdate();
            insertSt.close();


            return resultId;

        } catch (Exception ex) {
            throw ex;
        } finally {
            insertSt.close();
        }



    }

//    private boolean isFileExists(Connection con, String fileType) throws Exception {
//        boolean isExists = false;
//        PreparedStatement getAllSta = null;
//
//        try {
//            getAllSta = con.prepareStatement("select FILENAME from INCOMINGVISABINFILE where filename=?");
//            getAllSta.setString(1, fileType);
//
//            rs = getAllSta.executeQuery();
//
//            while (rs.next()) {
//                isExists = true;
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//            rs.close();
//            getAllSta.close();
//        }
//        return isExists;
//    }
    public List findByFileType(Connection con, String fileType) throws Exception {
        List filePath = new ArrayList();
        PreparedStatement findByCode = null;

        try {

            findByCode = con.prepareStatement("SELECT FILEPATH,BACKUPPATH FROM INCOMINGFILEINFO WHERE FILETYPE=?");
            findByCode.setString(1, fileType);

            rs = findByCode.executeQuery();

            while (rs.next()) {
                filePath.add(rs.getString("FILEPATH"));
                String backUpPath = rs.getString("BACKUPPATH");

                filePath.add(backUpPath);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            findByCode.close();
            rs.close();
        }
        return filePath;
    }

    public CommonFilePathBean getFilePath(Connection con, String osType) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = con.prepareStatement("SELECT CF.SCANNEDAPPLICATION,CF.SCANNEDDOCUMENT,CF.EMBOSSFILE, "
                    + "CF.EODFILE,CF.STATEMENTFILE,CF.EMVFILE,CF.ENCODEFILE,CF.MODFILE,CF.LETTERS,CF.INCOMINGFILE "
                    + "FROM COMMONFILEPATH CF WHERE CF.OSTYPE=?");
            ps.setString(1, osType);
            rs = ps.executeQuery();

            while (rs.next()) {
                commonFilePathBean = new CommonFilePathBean();
                commonFilePathBean.setScanApplication(rs.getString("SCANNEDAPPLICATION"));
                commonFilePathBean.setScandocument(rs.getString("SCANNEDDOCUMENT"));
                commonFilePathBean.setEmbossFile(rs.getString("EMBOSSFILE"));
                commonFilePathBean.setEodFile(rs.getString("EODFILE"));
                commonFilePathBean.setStatement(rs.getString("STATEMENTFILE"));
                commonFilePathBean.setEmvFile(rs.getString("EMVFILE"));
                commonFilePathBean.setEncodefile(rs.getString("ENCODEFILE"));
                commonFilePathBean.setModFile(rs.getString("MODFILE"));
                commonFilePathBean.setLetters(rs.getString("LETTERS"));
                commonFilePathBean.setIncomingFile(rs.getString("INCOMINGFILE"));
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            ps.close();
            rs.close();
        }
        return commonFilePathBean;
    }

    private String getDateId(String date) throws Exception {
        String dateId = null;
        PreparedStatement ps = null;
        FileInfoBean bean = null;

        try {

//            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
//            dateNow = formatter.format(Calendar.getInstance().getTime());

            SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
            dateId = formatter.format(Calendar.getInstance().getTime());
//            String dateId = formatter.parse(date);

        } catch (Exception ex) {
            throw ex;
        }
        return dateId;
    }

    public List<FileInfoBean> getAllFileTypesInfo(Connection cmsCon) throws Exception {
        PreparedStatement ps = null;
        String query = null;

        query = "SELECT FILEINFOCODE, FILETYPE, DESCRIPTION, FILEPATH, BACKUPPATH FROM INCOMINGFILEINFO";

        try {
            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            fileInfoList = new ArrayList<FileInfoBean>();

            while (rs.next()) {
                FileInfoBean bean = new FileInfoBean();
                bean.setFileId(rs.getString("FILEINFOCODE"));
                bean.setFileType(rs.getString("FILETYPE"));
                bean.setFileName(rs.getString("DESCRIPTION"));
//                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setFilePath(rs.getString("FILEPATH"));
                bean.setBackupPath(rs.getString("BACKUPPATH"));

                fileInfoList.add(bean);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return fileInfoList;
    }
    
    public int addNewFileInfo(Connection cmsCon, FileInfoBean bean) throws SQLException, Exception {
        int i = -1;
        PreparedStatement ps = null;
        
        try {
            
            String query = "INSERT INTO INCOMINGFILEINFO (FILEINFOCODE, FILETYPE, DESCRIPTION, FILEPATH, BACKUPPATH) VALUES(?, ?, ?, ?, ?)";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, bean.getFileId());
            ps.setString(2, bean.getFileType());
            ps.setString(3, bean.getFileName());
            ps.setString(4, bean.getFilePath());
            ps.setString(5, bean.getBackupPath());
            
            i = ps.executeUpdate();
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return i;
    }
    
    public FileInfoBean viewSelectedFiles(Connection cmsCon, String id) throws Exception {
        FileInfoBean bean = new FileInfoBean();
        PreparedStatement ps = null;
        
        try {
            
            String query = "SELECT FILEINFOCODE, FILETYPE, DESCRIPTION, FILEPATH, BACKUPPATH FROM INCOMINGFILEINFO WHERE FILEINFOCODE=?";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                bean.setFileId(rs.getString("FILEINFOCODE"));
                bean.setFileType(rs.getString("FILETYPE"));
                bean.setFileName(rs.getString("DESCRIPTION"));
                bean.setFilePath(rs.getString("FILEPATH"));
                bean.setBackupPath(rs.getString("BACKUPPATH"));
            }
            return bean;
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    ps.close();
                } catch (SQLException sqlex) {
                    throw sqlex;
                }
            }
        }
    }
    
     public int updateFileInfo(Connection cmsCon, FileInfoBean bean) throws SQLException, Exception {
        int rowCount = -1;
        PreparedStatement ps = null;
        
        try {
            
            String query = "UPDATE INCOMINGFILEINFO SET FILETYPE=?, DESCRIPTION=?, FILEPATH=?, BACKUPPATH=? WHERE FILEINFOCODE=?";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, bean.getFileType());
            ps.setString(2, bean.getFileName());
            ps.setString(3, bean.getFilePath());
            ps.setString(4, bean.getBackupPath());
            ps.setString(5, bean.getFileId());
            
            rowCount = ps.executeUpdate();
            
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException sqlex) {
                    throw sqlex;
                }
            }
        }
        return rowCount;
    }
     
      public int deleteFileInfo(Connection cmsCon, String id) throws Exception {
        int i = -1;
        PreparedStatement ps = null;
        
        try {
            String query = "DELETE FROM INCOMINGFILEINFO WHERE FILEINFOCODE=?";
            
            ps = cmsCon.prepareStatement(query);
            
            ps.setString(1, id);
            
            i = ps.executeUpdate();
            
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        return i;
    }

      
     public boolean isFileAvailable(Connection con, String fileName) throws Exception {
        PreparedStatement ps = null;
        FileTypeBean bean = null;
        boolean status = false;
        int x = 0;

        try {

            String query = "SELECT COUNT(*) AS COUNT FROM INCOMINGFILEINFO WHERE FILETYPE=?";
            ps = con.prepareStatement(query);

            ps.setString(1, fileName);

            rs = ps.executeQuery();

            while (rs.next()) {
                x = rs.getInt("COUNT");
            }

            if (x <= 0) {
                status = false;
            } else {
                status = true;
            }

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
        return status;
    }
}
