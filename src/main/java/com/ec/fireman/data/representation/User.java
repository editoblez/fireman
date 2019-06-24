package com.ec.fireman.data.representation;

import com.ec.fireman.data.entities.RoleTypes;
import lombok.Data;

@Data
public class User {
  private final String userId;
  private final RoleTypes role;

  public User(String userId, RoleTypes role) {
    this.userId = userId;
    this.role = role;
  }
}
