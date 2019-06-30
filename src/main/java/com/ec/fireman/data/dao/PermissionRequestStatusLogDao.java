package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.PermissionRequestStatusLog;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class PermissionRequestStatusLogDao extends GenericDaoImpl<PermissionRequestStatusLog> {
  @PostConstruct
  public void init() {
    setClazz(PermissionRequestStatusLog.class);
  }
}
