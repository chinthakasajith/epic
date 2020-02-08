/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.logmanagement.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author ruwan_e
 */
public class LogFile {

    private String logFileCategory;
//    private String path;
    private LogFileType type; //ERROR or INFO
    private File logFile;
    
    //for JSON
    private long length;
    private String name;
    private Date date;

    public String getName() {
        return name;
    }


    public LogFile(File logFile) {
        this.logFile = logFile;
        logFileCategory = logFile.getParentFile().getName();
        type = LogFileType.valueOf(logFile.getParentFile().getParentFile().getName().toUpperCase());
        length = logFile.length();
        name= logFile.getName();
        date= new Date(logFile.lastModified());
    }
    
    public LogFile(String pathname) {
        this(new File(pathname));
    }
    
    public Date getDate() {
        return date;
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public String getLogFileCategory() {
        return logFileCategory;
    }

    public void setLogFileCategory(String logFileCategory) {
        this.logFileCategory = logFileCategory;
    }

    public String getPath() {
        return logFile.getAbsolutePath();
    }

    public LogFileType getType() {
        return type;
    }

    public void setType(LogFileType type) {
        this.type = type;
    }
    
    
    public long getLength() {
        return length;
    }

    public String getContent() {
        String content = "";
        BufferedReader br = null;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(logFile.getAbsolutePath()));

            while ((sCurrentLine = br.readLine()) != null) {
                content = content + sCurrentLine + '\n';
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return content;

    }
}
