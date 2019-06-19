package com.ec.fireman.data.representation;

import lombok.Data;

@Data
public class User {
  private final String userId;
  private final String rol;

  public User(String userId, String rol) {
    this.userId = userId;
    this.rol = rol;
  }
}
