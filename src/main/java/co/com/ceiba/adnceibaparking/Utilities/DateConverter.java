package co.com.ceiba.adnceibaparking.Utilities;

import co.com.ceiba.adnceibaparking.Models.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter {

    public static String getCurrentDateAndTime() {
        Date date = new Date();
        String stringDateFormat = Constants.DATE_FORMAT_ddMMyyy;
        DateFormat dateFormat = new SimpleDateFormat(stringDateFormat);

        return dateFormat.format(date);
    }

    public static int getCurrentDayOfWeek() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return  localCalendar.get(Calendar.DAY_OF_WEEK);
    }
}
