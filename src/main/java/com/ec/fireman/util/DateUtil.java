package com.ec.fireman.util;

import java.text.SimpleDateFormat;

public class DateUtil {
  private static String pattern = "yyyy-MM-dd HH:mm:ss";
  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  public static String formatDateToString(long date) {
    return simpleDateFormat.format(date);
  }
}
