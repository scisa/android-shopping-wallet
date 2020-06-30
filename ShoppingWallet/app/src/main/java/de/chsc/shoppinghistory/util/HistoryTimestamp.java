package de.chsc.shoppinghistory.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryTimestamp extends Timestamp {
    private long milliseconds;

    public HistoryTimestamp(long time) {
        super(time);
        this.milliseconds = time;
    }

    public void setTimeStampFromYearMonthDay(int year, int month, int dayOfMonth){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        this.setMilliseconds(cal.getTimeInMillis());
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getShortDate(){
        Date date = new Date(this.milliseconds);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getMilliseconds());
        return cal.get(Calendar.YEAR);
    }

    public int getCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getMilliseconds());
        return cal.get(Calendar.MONTH);
    }

    public String getCurrentMonthName(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getMilliseconds());
        return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
    }

    public int getCurrentDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getMilliseconds());
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public String getHistoryTimeStamp(){
        return this.getCurrentMonthName() + " " + this.getCurrentYear();
    }

    public void subtractOneMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.getMilliseconds());
        calendar.add(Calendar.MONTH, -1);
        this.setMilliseconds(calendar.getTimeInMillis());

    }

    public void addOneMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.getMilliseconds());
        calendar.add(Calendar.MONTH, 1);
        this.setMilliseconds(calendar.getTimeInMillis());
    }
}
