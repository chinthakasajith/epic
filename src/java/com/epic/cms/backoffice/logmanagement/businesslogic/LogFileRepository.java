/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.backoffice.logmanagement.businesslogic;

import com.epic.cms.backoffice.logmanagement.bean.LogFile;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ruwan_e
 */
public class LogFileRepository {

    private static String LOG_FILE_DIR;

    public LogFileRepository(){
        String windowsPath = "C:\\CMS\\Logs";
        String linuxPath = "/root/CMS/Logs";

        String osType = getOS_Type();
        if (osType.equals("WINDOWS")) {
            LOG_FILE_DIR = windowsPath;
        } else if (osType.equals("LINUX")) {
            LOG_FILE_DIR = linuxPath;
        }
//        LOG_FILE_DIR = "C:\\CMS\\Logs";
    }
//    private FileTreeModel fileTree;
    public List<String> getAllCategories() {
        File file = new File(LOG_FILE_DIR + File.separator + "info");
        String[] directories = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);
    }

    public void visitAllDirsAndFiles(File dir) {
        process(dir);

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                visitAllDirsAndFiles(new File(dir, children[i]));
            }
        }
    }

    private static void process(File dir) {
        dir.getAbsolutePath();
    }

    List<String> getAllTypes() {
        File file = new File(LOG_FILE_DIR);
        String[] directories = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);

    }

    public static List<LogFile> getLogFiles(String type, String category) throws IOException {
        return getLogFiles(new File(LOG_FILE_DIR + File.separator + type + File.separator + category));

    }

    public static List<LogFile> getAllLogFiles() throws IOException {
        return getLogFiles(new File(LOG_FILE_DIR));
    }

    private static List<LogFile> getLogFiles(File directory) throws IOException {
        final List<LogFile> logFiles = new ArrayList<LogFile>();
        new FileTraversal() {

            @Override
            public void onFile(final File f) {
                logFiles.add(new LogFile(f));
            }
        }.traverse(directory);

        return logFiles;
    }

    List<String> getCategoriesByType(String type) {
        File file = new File(LOG_FILE_DIR + File.separator + type);
        String[] directories = file.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return dir.isDirectory();
            }
        });
        return Arrays.asList(directories);
    }
    
    private static String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else {
            // For LINUX
            if (osName.contains("linux")) {
                osType = "LINUX";
            } else {
            }
        }


        return osType;
    }
}
