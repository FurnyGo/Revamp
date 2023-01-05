// 
// Decompiled by Procyon v0.5.36
// 

package revamp.utilities.impl.time;

import java.time.LocalDate;
import java.time.LocalTime;

public class Time
{
    public static String currentTime(final boolean seconds, final String dividers) {
        return String.valueOf(getHour()) + dividers + getMinute() + (seconds ? ("." + getSecond()) : "");
    }
    
    public static String getHour() {
        if (10 > LocalTime.now().getHour()) {
            return "0" + LocalTime.now().getHour();
        }
        return new StringBuilder().append(LocalTime.now().getHour()).toString();
    }
    
    public static String getMinute() {
        if (10 > LocalTime.now().getMinute()) {
            return "0" + LocalTime.now().getMinute();
        }
        return new StringBuilder().append(LocalTime.now().getMinute()).toString();
    }
    
    public static String getSecond() {
        if (10 > LocalTime.now().getSecond()) {
            return "0" + LocalTime.now().getSecond();
        }
        return new StringBuilder().append(LocalTime.now().getSecond()).toString();
    }
    
    public static String currentDate(final String dividers, final boolean fullYear) {
        return String.valueOf(getDay()) + dividers + getMonth() + dividers + getYear(fullYear);
    }
    
    public static String getYear(final boolean full) {
        final String str = new StringBuilder(String.valueOf(LocalDate.now().getYear())).toString();
        final String year = str.substring(str.length() - 2, str.length());
        if (full) {
            return str;
        }
        return year;
    }
    
    public static String getMonth() {
        if (10 > LocalDate.now().getMonthValue()) {
            return "0" + LocalDate.now().getMonthValue();
        }
        return new StringBuilder().append(LocalDate.now().getMonthValue()).toString();
    }
    
    public static String getDay() {
        if (10 > LocalDate.now().getDayOfMonth()) {
            return "0" + LocalDate.now().getDayOfMonth();
        }
        return new StringBuilder().append(LocalDate.now().getDayOfMonth()).toString();
    }
}
