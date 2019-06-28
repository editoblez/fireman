package com.ec.fireman.data.entities;

import java.util.Arrays;

public enum MimeTypes {
  
  DOC("application/msword", ".doc"), 
  XLS("application/excel", ".xls"), 
  DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document",".docx"), 
  XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"), 
  PDF("application/pdf", ".pdf"), 
  JPG("image/jpeg", ".jpg"), 
  JPEG("image/jpeg", ".jpeg"), 
  PNG("image/png", ".png"), 
  TXT("text/plain", ".txt"), 
  CSV("text/csv", ".csv"),
  HTML("text/html", ".html"), 
  ZIP("application/x-compressed", ".zip"), 
  RAR("application/x-rar-compressed", ".rar");

  private String mimeType;
  private String suffix;

  private MimeTypes(String mimeType, String suffix) {
    this.mimeType = mimeType;
    this.suffix = suffix;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  public static MimeTypes findBySuffix(final String suffix) {
    return Arrays.stream(values()).filter(value -> value.getSuffix().equals(suffix)).findFirst().orElse(null);
  }

}
