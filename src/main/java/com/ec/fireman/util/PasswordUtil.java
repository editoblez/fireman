package com.ec.fireman.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Random;

public class PasswordUtil {
  private static final String ALPHABET = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
  private static final int GENERATED_PASSWORD_SIZE = 5;

  public static String encrypt(String input) {
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("MD5");
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (digest == null) {
      return input;
    }

    digest.update(input.getBytes(StandardCharsets.UTF_8));
    byte[] encodedBytes = Base64.encodeBase64(digest.digest());
    return new String(encodedBytes);
  }

  public static String generatePassword() {
    StringBuilder result = new StringBuilder();
    Random r = new Random(Calendar.getInstance().getTimeInMillis());
    for (int i = 0; i < GENERATED_PASSWORD_SIZE; i++) {
      result.append(ALPHABET.charAt(r.nextInt(ALPHABET.length())));
    }
    return result.toString();
  }

  public static void main(String[] args) {
    System.out.println(encrypt("123"));
  }
}
