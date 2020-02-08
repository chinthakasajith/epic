package com.epic.cms.backoffice.filemgt.persistance;

import com.epic.cms.backoffice.filemgt.bean.FileInfoBean;
import com.epic.cms.backoffice.filemgt.bean.FileTypeBean;
import com.epic.cms.system.util.session.SessionUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeevan
 */
public class FileTypePersistance {

    ResultSet rs = null;
    private List<FileTypeBean> fileTypeList = null;
    private List<FileInfoBean> fileTypeList2 = null;
    private FileTypeBean fileType = null;
    private SessionUser sessionUser = null;

    public List<FileTypeBean> getAllFileType(Connection cmsCon) throws Exception {
        PreparedStatement ps = null;
        String query;

//        query = "SELECT FILETYPECODE, DESCRIPTION, FILENAMEPRIFIX, FILENAMEPOSTFIX,"
//                + "FILEEXTENTION, LASTUPDTEDUSER, LASTUPDATEDTIME, CREATTIME"
//                + "FROM INCOMINGFILETYPE";

        query = "SELECT IV.FILETYPECODE, IV.DESCRIPTION, IV.FILENAMEPRIFIX, IV.FILENAMEPOSTFIX, IV.FILEEXTENTION,"
                + "IV.LASTUPDTEDUSER, IV.LASTUPDATEDTIME, IV.CREATTIME, IV.CHANNELTYPE,"
                + " CT.DESCRIPTION AS CARDNAME, CD.DESCRIPTION AS DOMAINNAME, "
                + " CT.CARDTYPECODE, CD.DOMAINID"
                + " FROM INCOMINGFILETYPE IV, CARDTYPE CT, CARDDOMAIN CD"
                + " WHERE IV.CARDDOMAIN=CD.DOMAINID"
                + " AND IV.CARDTYPE=CT.CARDTYPECODE";

        try {

            ps = cmsCon.prepareStatement(query);
            rs = ps.executeQuery();

            fileTypeList = new ArrayList<FileTypeBean>();
            while (rs.next()) {
                FileTypeBean tempFileType = new FileTypeBean();

                tempFileType.setFileTypeCode(rs.getString("FILETYPECODE"));
                tempFileType.setDescription(rs.getString("DESCRIPTION"));
                tempFileType.setFileNamePrefix(rs.getString("FILENAMEPRIFIX"));
                tempFileType.setFileNamePostfix(rs.getString("FILENAMEPOSTFIX"));
                tempFileType.setFileExtension(rs.getString("FILEEXTENTION"));
                tempFileType.setLastUpdatedUser(rs.getString("LASTUPDTEDUSER"));
                tempFileType.setLastUpdatedTime(rs.getTimestamp("LASTUPDATEDTIME"));
                tempFileType.setCreatedTime(rs.getTimestamp("CREATTIME"));

                tempFileType.setCardTypeCode(rs.getString("CARDTYPECODE"));
                tempFileType.setCardTypeDescription(rs.getString("CARDNAME"));

                tempFileType.setDomainId(rs.getString("DOMAINID"));
                tempFileType.setDomainDescription(rs.getString("DOMAINNAME"));

                tempFileType.setChannelType(rs.getString("CHANNELTYPE"));
//                tempFileType.setChannelDescription(rs.getString("CHANNELNAME"));

                fileTypeList.add(tempFileType);
            }
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return fileTypeList;
    }

    public List<FileInfoBean> getAllFileTypesBin(Connection cmsCon) throws Exception {
        PreparedStatement ps = null;
        String query = null;

        query = "SELECT IV.FILEID, IV.FILENAME, IV.UPLOADTIME, IV.STATUS,ST.DESCRIPTION AS STATUSDES "
                + "FROM INCOMINGVISABINFILE IV,STATUS ST WHERE ST.STATUSCODE = IV.STATUS";

        ps = cmsCon.prepareStatement(query);
        rs = ps.executeQuery();

        try {
            fileTypeList2 = new ArrayList<FileInfoBean>();
            while (rs.next()) {
                FileInfoBean fileInfoBean = new FileInfoBean();
                fileInfoBean.setFileId(rs.getString("FILEID"));
                fileInfoBean.setFileName(rs.getString("FILENAME"));
                fileInfoBean.setUploadedTime(rs.getString("UPLOADTIME"));
                fileInfoBean.setStatus(rs.getString("STATUSDES"));

                fileTypeList2.add(fileInfoBean);
            }
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return fileTypeList2;
    }

    public FileTypeBean findByFileTypeCode(Connection con, String fileTypeCode) throws Exception {
        PreparedStatement findByCode = null;
        try {

            findByCode = con.prepareStatement("SELECT FT.FILETYPECODE,FT.FILENAMEPRIFIX,FT.FILENAMEPOSTFIX,FT.FILEEXTENTION,FT.LASTUPDTEDUSER"
                    + " FROM INCOMINGFILETYPE FT  "
                    + "WHERE FT.FILETYPECODE=?");
            findByCode.setString(1, fileTypeCode);
            rs = findByCode.executeQuery();

            while (rs.next()) {
                fileType = new FileTypeBean();
                fileType.setFileTypeCode(rs.getString("FILETYPECODE"));
                fileType.setFileExtension(rs.getString("FILEEXTENTION"));
                fileType.setFileNamePrefix(rs.getString("FILENAMEPRIFIX"));
                fileType.setFileNamePostfix(rs.getString("FILENAMEPOSTFIX"));
                fileType.setLastUpdatedUser(rs.getString("LASTUPDTEDUSER"));

            }

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (findByCode != null) {
                try {
                    findByCode.close();
                    rs.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return fileType;
    }

    public boolean isRecordAvailable(Connection con, String fileName) throws Exception {
        PreparedStatement ps = null;
        FileTypeBean bean = null;
        boolean status = false;
        int x = 0;

        try {

            String query = "SELECT COUNT(*) AS COUNT FROM INCOMINGVISABINFILE WHERE FILENAME=?";
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

    public int addNewFileType(Connection cmsCon, FileTypeBean bean) throws Exception {
        int i = -1;
        PreparedStatement ps = null;

        try {
//            String query = "INSERT INTO INCOMINGFILETYPE(FILETYPECODE, DESCRIPTION, FILENAMEPRIFIX, FILENAMEPOSTFIX,"
//                    + " FILEEXTENTION, LASTUPDTEDUSER, LASTUPDATEDTIME, CREATTIME) VALUES(?,?,?,?,?,?,SYSDATE,SYSDATE)";
            
             String query = "INSERT INTO INCOMINGFILETYPE(FILETYPECODE, DESCRIPTION, FILENAMEPRIFIX, FILENAMEPOSTFIX,"
                    + " FILEEXTENTION, LASTUPDTEDUSER, LASTUPDATEDTIME, CREATTIME, CHANNELTYPE, CARDTYPE, CARDDOMAIN)"
                    + " VALUES(?,?,?,?,?,?,SYSDATE,SYSDATE,?,?,?)";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, bean.getFileTypeCode());
            ps.setString(2, bean.getDescription());
            ps.setString(3, bean.getFileNamePrefix());
            ps.setString(4, bean.getFileNamePostfix());
            ps.setString(5, bean.getFileExtension());
//            ps.setString(6, bean.getLastUpdatedUser());
            ps.setString(6, bean.getLastUpdatedUser());
            
            ps.setString(7, bean.getChannelType());
            ps.setString(8, bean.getCardTypeCode());
            ps.setString(9, bean.getDomainId());

            i = ps.executeUpdate();
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
        return i;
    }

    public FileTypeBean viewSelectedFiles(Connection cmsCon, String id) throws Exception {
        FileTypeBean bean = new FileTypeBean();
        PreparedStatement ps = null;

        try {

//            String query = "SELECT IFT.FILETYPECODE,IFT.DESCRIPTION, IFT.FILENAMEPRIFIX, IFT.FILENAMEPOSTFIX, IFT.FILEEXTENTION,"
//                    + " IFT.LASTUPDTEDUSER, IFT.LASTUPDATEDTIME, IFT.CREATTIME FROM INCOMINGFILETYPE IFT"
//                    + " WHERE IFT.FILETYPECODE=?";

            String query = "SELECT IV.FILETYPECODE, IV.DESCRIPTION, IV.FILENAMEPRIFIX, IV.FILENAMEPOSTFIX, IV.FILEEXTENTION,"
                    + " IV.LASTUPDTEDUSER, IV.LASTUPDATEDTIME, IV.CREATTIME,"
                    + " CT.DESCRIPTION AS CARDNAME, CD.DESCRIPTION AS DOMAINNAME, "
                    + " CT.CARDTYPECODE, CD.DOMAINID, IV.CHANNELTYPE"
                    + " FROM INCOMINGFILETYPE IV, CARDTYPE CT, CARDDOMAIN CD "
                    + " WHERE IV.CARDDOMAIN=CD.DOMAINID"
                    + " AND IV.CARDTYPE=CT.CARDTYPECODE"
                    + " AND IV.FILETYPECODE=?";

            ps = cmsCon.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                bean.setFileTypeCode(rs.getString("FILETYPECODE"));
                bean.setDescription(rs.getString("DESCRIPTION"));
                bean.setFileNamePrefix(rs.getString("FILENAMEPRIFIX"));
                bean.setFileNamePostfix(rs.getString("FILENAMEPOSTFIX"));
                bean.setFileExtension(rs.getString("FILEEXTENTION"));
                bean.setLastUpdatedUser(rs.getString("LASTUPDTEDUSER"));

                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setCardTypeDescription(rs.getString("CARDNAME"));

                bean.setDomainId(rs.getString("DOMAINID"));
                bean.setDomainDescription(rs.getString("DOMAINNAME"));

                bean.setChannelType(rs.getString("CHANNELTYPE"));
//                bean.setChannelDescription(rs.getString("CHANNELNAME"));

            }
            return bean;

        } catch (SQLException ex) {
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

    public int updateFileType(Connection cmsCon, FileTypeBean bean) throws SQLException, Exception {
        int rowCount = -1;
        PreparedStatement ps = null;

        try {

//            String query = "UPDATE INCOMINGFILETYPE SET DESCRIPTION=?, FILENAMEPRIFIX=?, FILENAMEPOSTFIX=?, FILEEXTENTION=? WHERE FILETYPECODE=?";
            String query = "UPDATE INCOMINGFILETYPE SET DESCRIPTION=?, FILENAMEPRIFIX=?, FILENAMEPOSTFIX=?, FILEEXTENTION=?, CHANNELTYPE=?, CARDTYPE=?, CARDDOMAIN=?  WHERE FILETYPECODE=? ";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, bean.getDescription());
            ps.setString(2, bean.getFileNamePrefix());
            ps.setString(3, bean.getFileNamePostfix());
            ps.setString(4, bean.getFileExtension());
            ps.setString(5, bean.getChannelType());
            ps.setString(6, bean.getCardTypeCode());
            ps.setString(7, bean.getDomainId());
            ps.setString(8, bean.getFileTypeCode());

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

    public int deleteFileType(Connection cmsCon, String id) throws Exception {
        int i = -1;
        PreparedStatement ps = null;

        try {
            String query = "DELETE FROM INCOMINGFILETYPE WHERE FILETYPECODE=?";

            ps = cmsCon.prepareStatement(query);

            ps.setString(1, id);

            i = ps.executeUpdate();

        } catch (SQLException ex) {
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

    public List<FileTypeBean> getAllCardTypeDetails(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;

        query = "SELECT CARDTYPECODE, DESCRIPTION FROM CARDTYPE ORDER BY DESCRIPTION";

        try {

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            fileTypeList = new ArrayList<FileTypeBean>();
            while (rs.next()) {
                FileTypeBean bean = new FileTypeBean();
                bean.setCardTypeCode(rs.getString("CARDTYPECODE"));
                bean.setCardTypeDescription(rs.getString("DESCRIPTION"));

                fileTypeList.add(bean);
            }

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return fileTypeList;
    }

    public List<FileTypeBean> getCardDomainDetails(Connection CMSCon) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;

        query = "SELECT DOMAINID, DESCRIPTION FROM CARDDOMAIN ORDER BY DESCRIPTION";

        try {

            ps = CMSCon.prepareStatement(query);
            rs = ps.executeQuery();

            fileTypeList = new ArrayList<FileTypeBean>();
            while (rs.next()) {
                FileTypeBean bean = new FileTypeBean();
                bean.setDomainId(rs.getString("DOMAINID"));
                bean.setDomainDescription(rs.getString("DESCRIPTION"));

                fileTypeList.add(bean);
            }

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException ex) {
                    throw ex;
                }
            }
        }
        return fileTypeList;
    }

    public List<FileTypeBean> getSendingChannel(Connection CMSOnline) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;

//        query = "SELECT CHANNELTYPE, DESCRIPTION FROM CHANNELTYPE ORDER BY DESCRIPTION";
        query = "SELECT CHANNELID, CHANNELNAME FROM ECMS_ONLINE_CHANNELS ORDER BY CHANNELNAME";

        try {

            ps = CMSOnline.prepareStatement(query);
            rs = ps.executeQuery();

            fileTypeList = new ArrayList<FileTypeBean>();
            while (rs.next()) {
                FileTypeBean bean = new FileTypeBean();
                bean.setChannelType(rs.getString("CHANNELID"));
                bean.setChannelDescription(rs.getString("CHANNELNAME"));

                fileTypeList.add(bean);
            }

        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        return fileTypeList;
    }

    public String getChannelTypeDescription(Connection CMSOnline, String channelType) throws SQLException, Exception {
        PreparedStatement ps = null;
        String query;
        String channelTypeDescription = null;
        
        query = "SELECT CHANNELNAME FROM ECMS_ONLINE_CHANNELS WHERE CHANNELID=?";
        
        try {
            
            ps = CMSOnline.prepareStatement(query);
           
            ps.setString(1, channelType);
            rs = ps.executeQuery();
            
            while(rs.next()){
                
                channelTypeDescription = rs.getString("CHANNELNAME");
                        
            }
            
            return channelTypeDescription;
            
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                    rs.close();
                } catch (SQLException sqlEx) {
                    throw sqlEx;
                }
            }
        }
        
    }


   
}