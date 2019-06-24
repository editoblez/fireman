package com.ec.fireman.data.entities;

public enum RoleTypes {
  ADMIN("Administrador"), CLIENT("Cliente"), INSPECTOR("Inspector"), ECONOMIC("Contador");

  private String value;

  RoleTypes(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
