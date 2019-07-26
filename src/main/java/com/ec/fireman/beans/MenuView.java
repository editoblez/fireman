package com.ec.fireman.beans;

import com.ec.fireman.beans.representation.MenuItem;
import com.ec.fireman.data.entities.RoleTypes;
import com.ec.fireman.data.representation.User;
import com.ec.fireman.util.SessionUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.awt.*;
import java.io.Serializable;

@Data

@Log4j2
@Named
@SessionScoped
public class MenuView implements Serializable {
  private User loggedUser;
  private MenuItem home;
  private MenuItem service;
  private MenuItem requirement;
  private MenuItem user;
  private MenuItem local;
  private MenuItem inspector;
  private MenuItem counter;
  private MenuItem permissionRequestReport;

  @PostConstruct
  public void init() {
    loggedUser = SessionUtils.retrieveLoggedUser();
    assert loggedUser != null;
    RoleTypes roleType = loggedUser.getRole();
    home = new MenuItem("Inicio", PageNameConstants.HOME_PAGE, true);
    service = new MenuItem("Servicio", PageNameConstants.SERVICE_ADMIN_PAGE, roleType == RoleTypes.ADMIN);
    requirement = new MenuItem("Requerimiento", PageNameConstants.REQUIREMENT_ADMIN_PAGE, roleType == RoleTypes.ADMIN);
    user = new MenuItem("Usuario", PageNameConstants.USER_ADMIN_PAGE, roleType == RoleTypes.ADMIN);
    local = new MenuItem("Local", PageNameConstants.LOCAL_CLIENT_PAGE, roleType == RoleTypes.CLIENT);
    inspector = new MenuItem("Inspector", PageNameConstants.INSPECTOR_PAGE, roleType == RoleTypes.INSPECTOR);
    counter = new MenuItem("Counter", PageNameConstants.COUNTER_PAGE, roleType == RoleTypes.ECONOMIC);
    permissionRequestReport = new MenuItem("Reporte Solicitudes Permiso", PageNameConstants.PERMISSION_REQUEST_REPORT_PAGE, roleType == RoleTypes.ECONOMIC);
  }
}
