package fiveware.utilities.time;

import java.time.LocalDate;
import java.time.LocalTime;

public class Time {
   public static String currentTime(boolean seconds, String dividers) {
      return getHour() + dividers + getMinute() + (seconds ? "." + getSecond() : "");
   }

   public static String getHour() {
      return (10 > LocalTime.now().getHour() ? "0" : "") + LocalTime.now().getHour();
   }

   public static String getMinute() {
      return (10 > LocalTime.now().getMinute() ? "0" : "") + LocalTime.now().getMinute();
   }

   public static String getSecond() {
      return (10 > LocalTime.now().getSecond() ? "0" : "") + LocalTime.now().getSecond();
   }

   public static String currentDate(boolean fullYear, String dividers) {
      return getDay() + dividers + getMonth() + dividers + getYear(fullYear);
   }

   public static String getYear(boolean full) {
      String year = String.valueOf(LocalDate.now().getYear());
      return full ? year : year.substring(year.length() - 2, year.length());
   }

   public static String getMonth() {
      return (10 > LocalDate.now().getMonthValue() ? "0" : "") + LocalDate.now().getMonthValue();
   }

   public static String getDay() {
      return (10 > LocalDate.now().getDayOfMonth() ? "0" : "") + LocalDate.now().getDayOfMonth();
   }
}
