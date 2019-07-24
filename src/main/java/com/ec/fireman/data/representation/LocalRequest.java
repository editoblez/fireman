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

  public Local getLocal() {
    return local;
  }

  public void setLocal(Local local) {
    this.local = local;
  }

  public PermissionRequest getPermissionRequest() {
    return permissionRequest;
  }

  public void setPermissionRequest(PermissionRequest permissionRequest) {
    this.permissionRequest = permissionRequest;
  }
}
