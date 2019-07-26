package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.entities.PermissionRequest;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Named
@ViewScoped
public class PermissionRequestReportBean implements Serializable {

  private static final long serialVersionUID = -615739965461013974L;

  @Inject
  private PermissionRequestDao permissionRequestDao;

  private List requests;
  private String inspector;
  private PermissionRequest selectedRequest;

  @PostConstruct
  public void init() {
    this.refreshReport();
    selectedRequest = new PermissionRequest();
  }

  public void refreshReport() {
    if (inspector != null && !inspector.trim().isEmpty()) {
      requests = permissionRequestDao.findAllByInspector(inspector);
    } else {
      requests = permissionRequestDao.findAll();
    }
    log.info("Permissions length: " + (requests != null ? requests.size() : 0));
  }

  public void clearData() {
    selectedRequest = new PermissionRequest();
    inspector = "";
  }

}
