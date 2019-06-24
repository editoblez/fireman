package com.ec.fireman.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Base64Util {

  public static String inputStreamToBase64(InputStream inputStream) {
    try {
      byte[] bytes = IOUtils.toByteArray(inputStream);
      return Base64.getEncoder().encodeToString(bytes);
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return null;
  }
}
