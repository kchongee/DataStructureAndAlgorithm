package UtilityClasses;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil
{
    private final static DateTimeFormatter APP_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final static DateTimeFormatter APP_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeUtil(){}


    // region 001: convert something to Date or Time class
    public static LocalTime sqlTimeToLocalTime(Object sqlTime)
    {
        Time time = (Time) sqlTime;
        return time.toLocalTime();
    }


    public static LocalTime stringObjToLocalTime(Object timeObj)
    {

        String timeString = padHourWithZero((String)timeObj);


        // Debug
        // System.out.println(timeString);


        return LocalTime.parse(timeString);
    }

    public static LocalDate stringObjToLocalDate(Object dateObj)
    {
        String dateString = (String) dateObj;
        String[] splitted = dateString.split("/");
        String processedDate = 
                
                String.format("%02d",Integer.parseInt(splitted[0])) + '/' + 
                String.format("%02d",Integer.parseInt(splitted[1])) + '/' +
                splitted[2];
        
        return LocalDate.parse(processedDate, APP_DATE_FORMAT);
    }
    // endregion


    // region 002 : Convert Date or Time class to string
    public static String localTimeToString(LocalTime time){
        return time.format(APP_TIME_FORMAT);
    }

    public static String localDateToString(LocalDate date){
        return date.format(APP_DATE_FORMAT);
    }
    // endregion


    // region 003 : get current Date or Time (string or class)
    public static String strTimeNow() {
        return LocalTime.now().format(APP_TIME_FORMAT);
    }

    public static String strDateNow(){
        return LocalDate.now().format(APP_DATE_FORMAT);
    }

    public static LocalTime localTimeNow(){
        return LocalTime.parse(strTimeNow());
    }

    public static LocalDate localDateNow(){
        return LocalDate.now();
    }
    // endregion


    // region 004 : utility
    private static String padHourWithZero(String time)
    {
        int indexOfFirstColon = time.indexOf(':');
        String substring = time.substring(0,indexOfFirstColon);
        Integer val= Integer.parseInt(substring);

        // Debug
        // System.out.println(val);

        String zeroPadded = String.format("%02d",val);
        return zeroPadded + time.substring(indexOfFirstColon);
    }
}

class DateTimeUtilTester{
    public static void main(String[] args) {
        DateTimeUtil.stringObjToLocalTime("09:51:33");
    }
}
