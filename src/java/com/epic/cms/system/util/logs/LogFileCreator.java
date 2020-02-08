/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.logs;

/**
 *
 * @author janaka_h
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * @author Thilina Saranga Gajanayake
 *
 */
public class LogFileCreator {

    /**
     * This method writes given message to an information log file in given path.
     */
    public static void writeInfoToLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_RECON";
        BufferedWriter bw = null;



//		if(Configurations.PATH_LOGS_INFOR == null){
//			File file = new File("Init/Infor");
//			if(!file.exists())
//				file.mkdirs();
//			filename ="Init/Infor/" + filename + "_Infor.txt";
//		}else{
//			File file = new File(Configurations.PATH_LOGS_INFOR);
//			if(!file.exists())
//				file.mkdirs();
//			filename = Configurations.PATH_LOGS_INFOR + "/" + filename + "_Infor.txt";
//		}

        msg = line + getTime() + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writInforTologs()");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method writes given exceptions to an error log file in given path.
     */
    public static void writeErrorToLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/common";
        String linuxPath = "/root/CMS/Logs/error/common";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + "_Error.txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe2);
                }
            }
        }
    }
    public static void writeErrorToNumberGenLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/number genaration";
        String linuxPath = "/root/CMS/Logs/error/number genaration";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + "_Error.txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe2);
                }
            }
        }
    }
    
    
    
     // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method writes given exceptions to an error log file in given path.
     */
    public static synchronized  void writeErrorToCreditScoreLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/Credit score";
        String linuxPath = "/root/CMS/Logs/error/Credit score";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToApplicationProcessingLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToApplicationProcessingLog()" + ioe2);
                }
            }
        }
    }
    
    
      // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method writes given exceptions to an error log file in given path.
     */
    public static synchronized  void writeErrorToCardPersonalizationLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/Card personalization";
        String linuxPath = "/root/CMS/Logs/error/Card personalization";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + "_Error.txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToCardPersonalizationLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToCardPersonalizationLog()" + ioe2);
                }
            }
        }
    }
    
    
    
      // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method writes given exceptions to an error log file in given path.
     */
    public static synchronized  void writeErrorToMerchantterminalLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/Merchant Terminal";
        String linuxPath = "/root/CMS/Logs/error/Merchant Terminal";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + "_Error.txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToMerchantterminalLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writeErrorToMerchantterminalLog()" + ioe2);
                }
            }
        }
    }
    
    
    // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method writes given exceptions to an error log file in given path.
     */
    public static void writePinGenerationErrorToLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/Pin generation";
        String linuxPath = "/root/CMS/Logs/error/Pin generation";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe2);
                }
            }
        }
    }

    
        // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * pin mailer error log
     */
    public static void writePinMailerErrorToLog(Throwable aThrowable) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String msg = "";
        String windowsPath = "C://CMS/Logs/error/Pin mailer";
        String linuxPath = "/root/CMS/Logs/error/Pin mailer";
        String path = "";
        String osType = "";


        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }


        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";


        msg = line + getTime() + ":    " + getStackTrace(aThrowable);

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    System.out.println("Problem when writing to log file in LogFileCreator.writErrorToLog()" + ioe2);
                }
            }
        }
    }

    
    // ---------------------------------------------------------------------------------------------------------------------------------
    /**
     *  Pin Generation Info Log
     */
    public static void writePinGenerationInfoToLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String osType = "";
        String path = "";

        String windowsPath = "C://CMS/Logs/info/Pin generation";
        String linuxPath = "/root/CMS/Logs/info/Pin generation";

        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";

        msg = line + getTime() + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writePinGenerationInfoToLog()");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }

     // ---------------------------------------------------------------------------------------------------------------------------------
    /**
     *  Pin Generation Info Log
     */
    public static void writePinMailerInfoToLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String osType = "";
        String path = "";

        String windowsPath = "C://CMS/Logs/info/Pin mailer";
        String linuxPath = "/root/CMS/Logs/info/Pin mailer";

        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";

        msg = line + getTime() + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writePinGenerationInfoToLog()");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    
      // ---------------------------------------------------------------------------------------------------------------------------------
    /**
     *  Credit score Info Log
     */
    public static void writeCreditScoreInfoToLog(String msg) {

        String line = "\n_______________________________________________________________________________________\n";
        String filename = getFileName() + "_CMS";
        BufferedWriter bw = null;
        String osType = "";
        String path = "";

        String windowsPath = "C://CMS/Logs/info/Credit score";
        String linuxPath = "/root/CMS/Logs/info/Credit score";

        osType = LogFileCreator.getOS_Type();
        if (osType.equals("WINDOWS")) {
            path = windowsPath;
        } else if (osType.equals("LINUX")) {
            path = linuxPath;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        filename = path + "/" + filename + ".txt";

        msg = line + getTime() + "\n" + msg;

        try {
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            System.out.println("Problem when writing to log file in LogFileCreator.writePinGenerationInfoToLog()");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------------------	
    /**
     * This method is used to get the file name including the date 
     */
    private static String getFileName() {

        GregorianCalendar now = new GregorianCalendar();
        now.getTime();
        int intCurrentYear = now.get(Calendar.YEAR);
        int intCurrentMonth = now.get(Calendar.MONTH) + 1;
        int intCurrentDay = now.get(Calendar.DAY_OF_MONTH);

        String m = "";
        String d = "";

        m = intCurrentMonth + "";
        d = intCurrentDay + "";

        if (intCurrentMonth < 10) {
            m = "0" + intCurrentMonth + "";
        }
        if (intCurrentDay < 10) {
            d = "0" + intCurrentDay + "";
        }

        String file = intCurrentYear + "" + m + d;
        return file;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method is used to derive time
     */
    private static String getTime() {

        GregorianCalendar now = new GregorianCalendar();
        return now.getTime().toString();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method is used to get the stack trace of error 
     */
    private static String getStackTrace(Throwable aThrowable) {
        String re = "";
        Writer result = null;
        PrintWriter printWriter = null;
        try {
            result = new StringWriter();
            printWriter = new PrintWriter(result);

            aThrowable.printStackTrace(printWriter);
            re = result.toString();
            result.close();
            printWriter.close();

        } catch (Exception e) {
            System.out.println("Error when getting error description getStackTrace()" + e);
        } finally {

            try {
                if (result != null) {
                    result.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Error when getting error description getStackTrace()" + e);
            }
        }
        return re;
    }

    public static String getOS_Type() {

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
