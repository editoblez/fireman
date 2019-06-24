package com.ec.fireman.util;

public class UriUtil {

  public static String removeStaringSlash(String uri) {
    if (uri.startsWith("/")) {
      return uri.substring(1);
    }
    return uri;
  }
}
