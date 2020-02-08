/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.cms.system.util.datetime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author janaka
 */
public class DateUtil {

    public String convert(String textDate) {

////        Date d=null;
        String expireDate = null;
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");

////     String textDate = "2001-01-04";
        try {

            Date date = sdfInput.parse(textDate);

            expireDate = sdfOutput.format(date);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return expireDate;

    }

    public static long daysBetween(Date startDate, Date endDate) {
        
        Calendar startDateCal = Calendar.getInstance();
        Calendar endDateCal = Calendar.getInstance();

        startDateCal.setTime(startDate);
        endDateCal.setTime(endDate);
        
        
        Calendar date = (Calendar) startDateCal.clone();
        long daysBetween = -1;
        while (date.before(endDateCal)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
    
    
    public static String convertTimestampToStringDate(Timestamp timestamp) {
        String formattedDate="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        formattedDate = dateFormat.format(timestamp);     
        return formattedDate;
    }

}
