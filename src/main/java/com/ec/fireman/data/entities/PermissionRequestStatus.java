package com.ec.fireman.data.entities;

public enum PermissionRequestStatus {
  TO_REQUEST("Por Solicitar"), REQUESTED("Solicitado"),

  IN_PROGRESS("En proceso de inspección"),

  REJECTED("Rechazado"), TO_PAIED("Pendiente de pago"),

  PERMISSION_ISSUED("Permiso emitido"),

  TO_EXPIRE("Por caducar"), EXPIRED("Expirado");

  private String value;

  PermissionRequestStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
