package com.ec.fireman.data.representation;

import com.ec.fireman.data.entities.Local;
import com.ec.fireman.data.entities.PermissionRequest;

import lombok.Data;

@Data
public class LocalRequest {

  private Local local;
  private PermissionRequest permissionRequest;

  public LocalRequest(Local local, PermissionRequest permissionRequest) {
    super();
    this.local = local;
    this.permissionRequest = permissionRequest;
  }

}
