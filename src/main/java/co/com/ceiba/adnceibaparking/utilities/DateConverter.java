package co.com.ceiba.adnceibaparking.utilities;

import co.com.ceiba.adnceibaparking.Models.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class DateConverter {

    private DateConverter() { }

    public static String getCurrentDate() {
        Date date = new Date();
        String stringDateFormat = Constants.DATE_FORMAT;
        DateFormat dateFormat = new SimpleDateFormat(stringDateFormat);

        return dateFormat.format(date);
    }

    public static int getCurrentDayOfWeek() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return  localCalendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String convertDateToString(Date date) {
        String stringDateFormat = Constants.DATE_FORMAT;
        DateFormat dateFormat = new SimpleDateFormat(stringDateFormat);
        return dateFormat.format(date);
    }

    public static Date convertStringToDate(String date) throws java.text.ParseException{
        Date dateParsed = null;
        String stringDateFormat = Constants.DATE_FORMAT;
        DateFormat dateFormat = new SimpleDateFormat(stringDateFormat);
        dateParsed   = dateFormat.parse(date);
        return dateParsed;
    }
}
