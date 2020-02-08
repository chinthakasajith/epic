/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.logmanagement.businesslogic;


import com.epic.cms.backoffice.logmanagement.bean.LogFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ruwan_e
 */
public class LogFileManager {
    private LogFileRepository logFileRepository;
    
    public LogFileManager(){
        logFileRepository=new LogFileRepository();
    }
    
    public List<String> getAllCategories() {
        return logFileRepository.getAllCategories();
    }

    public List<String> getAllTypes() {
        return logFileRepository.getAllTypes();
    }

    public List<String> getCategoriesByType(String type) {
        return logFileRepository.getCategoriesByType(type);
    }

    List<LogFile> getLogFiles(String type, String category) throws IOException {
        return LogFileRepository.getLogFiles(type, category);
    }
}
