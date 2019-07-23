package com.ec.fireman.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
  private static String pattern = "yyyy-MM-dd HH:mm:ss";
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
  private static String patternYear = "yyyy";
  private static SimpleDateFormat simpleDateFormatYear = new SimpleDateFormat(patternYear);

  public static String formatDateToString(long date) {
    return simpleDateFormat.format(date);
  }

  public static String getYearFromDate(long date) {
    return simpleDateFormatYear.format(date);
  }

  public static int extractYear(long expire) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(expire);
    return calendar.get(Calendar.YEAR);
  }
}
